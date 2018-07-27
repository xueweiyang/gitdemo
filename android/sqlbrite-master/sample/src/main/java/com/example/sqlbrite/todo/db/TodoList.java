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
package com.example.sqlbrite.todo.db;

import android.content.ContentValues;
import android.os.Parcelable;
import com.google.auto.value.AutoValue;

// Note: normally I wouldn't prefix table classes but I didn't want 'List' to be overloaded.
@AutoValue
public abstract class TodoList implements Parcelable {
  public static final String TABLE = "todo_list";

  public static final String ID = "_id";
  public static final String NAME = "name";
  public static final String ARCHIVED = "archived";

  public abstract long id();
  public abstract String name();
  public abstract boolean archived();

  public static final class Builder {
    private final ContentValues values = new ContentValues();

    public Builder id(long id) {
      values.put(ID, id);
      return this;
    }

    public Builder name(String name) {
      values.put(NAME, name);
      return this;
    }

    public Builder archived(boolean archived) {
      values.put(ARCHIVED, archived);
      return this;
    }

    public ContentValues build() {
      return values; // TODO defensive copy?
    }
  }
}
