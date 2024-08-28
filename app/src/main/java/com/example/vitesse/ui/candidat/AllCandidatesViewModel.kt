package com.example.vitesse.ui.candidat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.domain.model.Candidat
import kotlinx.coroutines.launch

class AllCandidatesViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application, viewModelScope)

    // LiveData directly tied to the database query
    val candidates: LiveData<List<Candidat>> = database.candidatDtoDao().getAllCandidatesLive().map { candidateDtos ->
        candidateDtos.map { Candidat.fromDto(it) }
    }


}
