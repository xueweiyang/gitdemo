package com.example.fcl.dadademo.provider

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration
import android.arch.persistence.db.SupportSQLiteOpenHelper.Factory

class DadaSQLiteOpenHelper : SupportSQLiteOpenHelper{

    companion object {
        const val DB_NAME = "DadaDB.db"
    }

    override fun getDatabaseName(): String {
        return DB_NAME
    }

    override fun getWritableDatabase(): SupportSQLiteDatabase {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReadableDatabase(): SupportSQLiteDatabase {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class DadaSQLiteFactory : Factory{
        override fun create(configuration: Configuration?): SupportSQLiteOpenHelper {
            return DadaSQLiteOpenHelper()
        }
    }
}