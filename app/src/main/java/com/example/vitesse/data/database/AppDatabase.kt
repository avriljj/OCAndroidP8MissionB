package com.example.vitesse.data.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
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
                    populateDatabase(database.candidatDtoDao())
                    // Notify that data population is complete
                    dataPopulationComplete.postValue(true)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        var dataPopulationComplete = MutableLiveData<Boolean>()

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
            // Clear existing data to ensure a fresh start
            candidatDtoDao.deleteAllCandidates()

            // Log before inserting
            Log.d("AppDatabase", "Starting database population")

            // Insert candidates
            try {
                candidatDtoDao.insertCandidate(
                    CandidatDto(
                        name = "Jin", surname = "Zhao", phone = "", email = "jin@icloud.com",
                        birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1990-01-01"),
                        salary = 12, note = "Learning programming", isFav = false
                    )
                )
                candidatDtoDao.insertCandidate(
                    CandidatDto(
                        name = "Alice", surname = "Pio", phone = "", email = "alice@icloud.com",
                        birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1990-01-01"),
                        salary = 12, note = "Learning programming", isFav = false
                    )
                )
                candidatDtoDao.insertCandidate(
                    CandidatDto(
                        name = "Lane", surname = "Yu", phone = "", email = "lane@icloud.com",
                        birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1990-01-01"),
                        salary = 12, note = "Learning programming", isFav = true
                    )
                )
                candidatDtoDao.insertCandidate(
                    CandidatDto(
                        name = "Lolo", surname = "Li", phone = "", email = "lolo@icloud.com",
                        birthDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1990-01-01"),
                        salary = 12, note = "Learning programming", isFav = true
                    )
                )

                // Log after inserting
                val allCandidates = candidatDtoDao.getAllCandidates()
                Log.d("AppDatabase", "Number of candidates inserted: ${allCandidates.size}")

            } catch (e: Exception) {
                Log.e("AppDatabase", "Error inserting candidates", e)
            }
        }
    }
}
