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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.sqlbrite.todo.R;
import com.example.sqlbrite.todo.TodoApp;
import com.example.sqlbrite.todo.db.TodoList;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.squareup.sqlbrite3.BriteDatabase;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import javax.inject.Inject;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_NONE;
import static butterknife.ButterKnife.findById;

public final class NewListFragment extends DialogFragment {
  public static NewListFragment newInstance() {
    return new NewListFragment();
  }

  private final PublishSubject<String> createClicked = PublishSubject.create();

  @Inject BriteDatabase db;

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    TodoApp.getComponent(activity).inject(this);
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Context context = getActivity();
    View view = LayoutInflater.from(context).inflate(R.layout.new_list, null);

    EditText name = findById(view, android.R.id.input);
    Observable.combineLatest(createClicked, RxTextView.textChanges(name),
        new BiFunction<String, CharSequence, String>() {
          @Override public String apply(String ignored, CharSequence text) {
            return text.toString();
          }
        }) //
        .observeOn(Schedulers.io())
        .subscribe(new Consumer<String>() {
          @Override public void accept(String name) {
            db.insert(TodoList.TABLE, CONFLICT_NONE, new TodoList.Builder().name(name).build());
          }
        });

    return new AlertDialog.Builder(context) //
        .setTitle(R.string.new_list)
        .setView(view)
        .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            createClicked.onNext("clicked");
          }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          @Override public void onClick(@NonNull DialogInterface dialog, int which) {
          }
        })
        .create();
  }
}
