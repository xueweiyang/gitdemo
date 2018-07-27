package com.example.fcl.dadademo.sql

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.squareup.sqlbrite3.BriteDatabase
import com.squareup.sqlbrite3.SqlBrite
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

abstract class BaseDatabaseHelper<T>(helper: SupportSQLiteOpenHelper) {

    private val briteDatabase: BriteDatabase

    abstract val tableName: String
    abstract val primaryKey: String
    abstract fun toModel(cursor: Cursor): T
    abstract fun fromModel(model: T): ContentValues

    init {
        val sqlBrite = SqlBrite.Builder().build()
        briteDatabase = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io())
    }

    fun insertRecord(t:T){
        briteDatabase.insert(tableName,SQLiteDatabase.CONFLICT_REPLACE,fromModel(t))
    }

    fun insertRecords(list: List<T>) {
        val transaction=briteDatabase.newTransaction()
        try {
            list.forEach {
                insertRecord(it)
            }
            transaction.markSuccessful()
        } finally {
            transaction.end()
        }
    }

    fun deleteRecord(deleteKey:String, deleteValue:String) :Int{
        return briteDatabase.delete(tableName, "$deleteKey = ?", deleteValue)
    }

    fun deleteAllRecords():Int{
        return briteDatabase.delete(tableName, null)
    }

    fun updateRecord(t:T){
        val contentValues=fromModel(t)
        briteDatabase.update(tableName,SQLiteDatabase.CONFLICT_REPLACE,contentValues,
            "$primaryKey = ?", contentValues[primaryKey].toString())
    }

    fun updateRecord(t:T,whereClause: String?,whereArgs: String?){
        if (whereClause.isNullOrEmpty() != whereArgs.isNullOrEmpty()) {
            throw IllegalArgumentException("whereClause and whereArgs should be matched.")
        }
        val contentValues=fromModel(t)
        briteDatabase.update(tableName,SQLiteDatabase.CONFLICT_REPLACE,contentValues,
            "${whereClause ?: primaryKey} = ?", whereArgs ?: contentValues[primaryKey].toString())
    }

    fun loadOneRecord(whereClause: String, whereArgs: String): Observable<T?> {
        val sql = "select * from $tableName where $whereClause = ?"
        return briteDatabase
            .createQuery(tableName, sql, whereArgs)
            .mapToOne { cursor ->
                toModel(cursor)
            }
    }

    fun loadRecords(whereClause: String, whereArgs: String): Observable<List<T?>> {
        val sql = "select * from $tableName where $whereClause = ?"
        return briteDatabase
            .createQuery(tableName, sql, whereArgs)
            .mapToList { cursor ->
                toModel(cursor)
            }
    }

    fun loadAllRecords(): Observable<List<T?>> {
        val sql = "select * from $tableName"
        return briteDatabase
            .createQuery(tableName, sql)
            .mapToList { curosr ->
                toModel(curosr)
            }
    }
}