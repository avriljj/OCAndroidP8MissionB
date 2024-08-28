package com.example.vitesse.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.data.entity.CandidatDto
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddCandidateViewModel(application: Application) : AndroidViewModel(application) {

    // Get the database instance using the application context and viewModelScope
    private val database = AppDatabase.getDatabase(application, viewModelScope)

    fun addCandidate(
        name: String,
        surname: String,
        phone: String,
        email: String,
        birthDate: Date,
        salary: Int,
        note: String,
        isFav: Boolean
    ) {
        viewModelScope.launch {
            val candidate = CandidatDto(
                name = name,
                surname = surname,
                phone = phone,
                email = email,
                birthDate = birthDate,
                salary = salary,
                note = note,
                isFav = isFav
            )
            database.candidatDtoDao().insertCandidate(candidate)
        }
    }
}
