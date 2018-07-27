/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sqlbrite.todo.ui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.sqlbrite.todo.R;
import com.example.sqlbrite.todo.TodoApp;
import com.example.sqlbrite.todo.db.Db;
import com.example.sqlbrite.todo.db.TodoItem;
import com.example.sqlbrite.todo.db.TodoList;
import com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.squareup.sqlbrite3.BriteDatabase;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_NONE;
import static android.support.v4.view.MenuItemCompat.SHOW_AS_ACTION_IF_ROOM;
import static android.support.v4.view.MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT;
import static com.squareup.sqlbrite3.SqlBrite.Query;

public final class ItemsFragment extends Fragment {
  private static final String KEY_LIST_ID = "list_id";
  private static final String LIST_QUERY = "SELECT * FROM "
      + TodoItem.TABLE
      + " WHERE "
      + TodoItem.LIST_ID
      + " = ? ORDER BY "
      + TodoItem.COMPLETE
      + " ASC";
  private static final String COUNT_QUERY = "SELECT COUNT(*) FROM "
      + TodoItem.TABLE
      + " WHERE "
      + TodoItem.COMPLETE
      + " = "
      + Db.BOOLEAN_FALSE
      + " AND "
      + TodoItem.LIST_ID
      + " = ?";
  private static final String TITLE_QUERY =
      "SELECT " + TodoList.NAME + " FROM " + TodoList.TABLE + " WHERE " + TodoList.ID + " = ?";

  public interface Listener {
    void onNewItemClicked(long listId);
  }

  public static ItemsFragment newInstance(long listId) {
    Bundle arguments = new Bundle();
    arguments.putLong(KEY_LIST_ID, listId);

    ItemsFragment fragment = new ItemsFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  @Inject BriteDatabase db;

  @BindView(android.R.id.list) ListView listView;
  @BindView(android.R.id.empty) View emptyView;

  private Listener listener;
  private ItemsAdapter adapter;
  private CompositeDisposable disposables;

  private long getListId() {
    return getArguments().getLong(KEY_LIST_ID);
  }

  @Override public void onAttach(Activity activity) {
    if (!(activity instanceof Listener)) {
      throw new IllegalStateException("Activity must implement fragment Listener.");
    }

    super.onAttach(activity);
    TodoApp.getComponent(activity).inject(this);
    setHasOptionsMenu(true);

    listener = (Listener) activity;
    adapter = new ItemsAdapter(activity);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);

    MenuItem item = menu.add(R.string.new_item)
        .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
          @Override public boolean onMenuItemClick(MenuItem item) {
            listener.onNewItemClicked(getListId());
            return true;
          }
        });
    MenuItemCompat.setShowAsAction(item, SHOW_AS_ACTION_IF_ROOM | SHOW_AS_ACTION_WITH_TEXT);
  }

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.items, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    listView.setEmptyView(emptyView);
    listView.setAdapter(adapter);

    RxAdapterView.itemClickEvents(listView) //
        .observeOn(Schedulers.io())
        .subscribe(new Consumer<AdapterViewItemClickEvent>() {
          @Override public void accept(AdapterViewItemClickEvent event) {
            boolean newValue = !adapter.getItem(event.position()).complete();
            db.update(TodoItem.TABLE, CONFLICT_NONE,
                new TodoItem.Builder().complete(newValue).build(), TodoItem.ID + " = ?",
                String.valueOf(event.id()));
          }
        });
  }

  @Override public void onResume() {
    super.onResume();
    String listId = String.valueOf(getListId());

    disposables = new CompositeDisposable();

    Observable<Integer> itemCount = db.createQuery(TodoItem.TABLE, COUNT_QUERY, listId) //
        .map(new Function<Query, Integer>() {
          @Override public Integer apply(Query query) {
            Cursor cursor = query.run();
            try {
              if (!cursor.moveToNext()) {
                throw new AssertionError("No rows");
              }
              return cursor.getInt(0);
            } finally {
              cursor.close();
            }
          }
        });
    Observable<String> listName =
        db.createQuery(TodoList.TABLE, TITLE_QUERY, listId).map(new Function<Query, String>() {
          @Override public String apply(Query query) {
            Cursor cursor = query.run();
            try {
              if (!cursor.moveToNext()) {
                throw new AssertionError("No rows");
              }
              return cursor.getString(0);
            } finally {
              cursor.close();
            }
          }
        });
    disposables.add(
        Observable.combineLatest(listName, itemCount, new BiFunction<String, Integer, String>() {
          @Override public String apply(String listName, Integer itemCount) {
            return listName + " (" + itemCount + ")";
          }
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
              @Override public void accept(String title) throws Exception {
                getActivity().setTitle(title);
              }
            }));

    disposables.add(db.createQuery(TodoItem.TABLE, LIST_QUERY, listId)
        .mapToList(TodoItem.MAPPER)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(adapter));
  }

  @Override public void onPause() {
    super.onPause();
    disposables.dispose();
  }
}
