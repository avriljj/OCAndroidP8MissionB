package com.example.vitesse.ui.candidat

import android.app.Application
import androidx.lifecycle.*
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.domain.model.Candidat
import kotlinx.coroutines.launch

class FavoriteCandidatesViewModel(application: Application) : AndroidViewModel(application) {


    private val database = AppDatabase.getDatabase(application, viewModelScope)

    // LiveData directly tied to the database query
    val favorties: LiveData<List<Candidat>> = database.candidatDtoDao().getFavoriteCandidatesLive().map { candidateDtos ->
        candidateDtos.map { Candidat.fromDto(it) }
    }
}
