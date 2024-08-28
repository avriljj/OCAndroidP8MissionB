package com.example.vitesse.ui.candidat

import android.app.Application
import androidx.lifecycle.*
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.domain.model.Candidat
import kotlinx.coroutines.launch

class FavoriteCandidatesViewModel(application: Application) : AndroidViewModel(application) {

    private val _favorites = MutableLiveData<List<Candidat>>()
    val favorites: LiveData<List<Candidat>> get() = _favorites

    private val database = AppDatabase.getDatabase(application, viewModelScope)

    init {
        refreshCandidates()
    }

    fun refreshCandidates() {
        viewModelScope.launch {
            val favoriteDtos = database.candidatDtoDao().getFavoriteCandidates()
            _favorites.postValue(favoriteDtos.map { Candidat.fromDto(it) })
        }
    }
}
