package com.example.fcl.dadademo.provider

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dadaabc.zhuozan.dadaabcstudent.model.VoicePractiseScore
import com.example.fcl.dadademo.sql.BaseDatabaseHelper

class EvaluateScoreDbHelper(helper: SupportSQLiteOpenHelper
) :BaseDatabaseHelper<VoicePractiseScore>(helper) {

    override val primaryKey: String
        get() = "id"
    override val tableName: String
        get() = "evaluateScore"

    override fun fromModel(model: VoicePractiseScore): ContentValues {
        val contentValues=ContentValues(3)
        contentValues.put("id", model.id)
        contentValues.put("currentScore", model.currentScore)
        contentValues.put("scoreEncrypt", model.scoreEncrypt)
        return contentValues
    }

    override fun toModel(cursor: Cursor): VoicePractiseScore {
        return VoicePractiseScore(
            cursor.getInt(cursor.getColumnIndex("id")),
            cursor.getInt(cursor.getColumnIndex("currentScore")),
            cursor.getString(cursor.getColumnIndex("scoreEncrypt"))
        )
    }

    companion object {
        @JvmStatic
    fun createTable(db:SQLiteDatabase) {
            db.execSQL(
                "create table if not exists"+
                    "evaluateScore " + "("+
                    "id "+ "integer primary key" +","+
                    "currentScore "+ "integer"+","+
                    "scoreEncrypt "+ "varchar"+
                    ");"
            )
        }
    }
}