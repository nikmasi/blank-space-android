package com.example.blankspace.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(
    ZanrEntity::class,
    IzvodjacEntity::class,
    KorisnikEntity::class,
    PesmaEntity::class,
    PredlazeIzvodjacaEntity::class,
    PredlazePesmuEntity::class,
    SobaEntity::class,
    StihoviEntity::class
), version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).addCallback(MyDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                return@synchronized instance
            }
        }
    }

    private class MyDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            Log.d("Room", "Baza je KREIRANA")

            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.roomDao()) }
            }
        }

        suspend fun populateDatabase(roomDao: RoomDao) {
            roomDao.deleteAll()

        }
    }
}

