package com.example.vitesse.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vitesse.converter.Converters
import com.example.vitesse.data.dao.CandidatDtoDao
import com.example.vitesse.data.entity.CandidatDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Locale

@Database(entities = [CandidatDto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun candidatDtoDao(): CandidatDtoDao


    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase( database.candidatDtoDao())
                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "CandidatDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }


        suspend fun populateDatabase(candidatDtoDao: CandidatDtoDao) {


            candidatDtoDao.insertCandidat(
                CandidatDto(
                    name = "jin", surname = "zhao", phone = "", email = "jin@icloud.com",
                    birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1990-01-01"),
                salary = 12 , note = " learning programming", isFav = false
                )
            )
            candidatDtoDao.insertCandidat(
                CandidatDto(
                    name = "alice", surname = "pio", phone = "", email = "alice@icloud.com",
                    birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1990-01-01"),
                    salary = 12 , note = " learning programming", isFav = false
                )
            )
            candidatDtoDao.insertCandidat(
                CandidatDto(
                    name = "lane", surname = "yu", phone = "", email = "lane@icloud.com",
                    birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1990-01-01"),
                    salary = 12 , note = " learning programming", isFav = true
                )
            )
            candidatDtoDao.insertCandidat(
                CandidatDto(
                    name = "lolo", surname = "li", phone = "", email = "lolo@icloud.com",
                    birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1990-01-01"),
                    salary = 12 , note = " learning programming", isFav = true
                )
            )
        }
    }
}