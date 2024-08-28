package com.example.vitesse.ui.candidat

import android.app.Application
import androidx.lifecycle.*
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.domain.model.Candidat
import kotlinx.coroutines.launch

class AllCandidatesViewModel(application: Application) : AndroidViewModel(application) {

    private val _candidates = MutableLiveData<List<Candidat>>()
    val candidates: LiveData<List<Candidat>> get() = _candidates

    private val database = AppDatabase.getDatabase(application, viewModelScope)

    init {
        refreshCandidates()
    }

    fun refreshCandidates() {
        viewModelScope.launch {
            val candidateDtos = database.candidatDtoDao().getAllCandidates()
            _candidates.postValue(candidateDtos.map { Candidat.fromDto(it) })
        }
    }
}
