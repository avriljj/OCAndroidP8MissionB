package com.example.vitesse.data.repository

import com.example.vitesse.data.dao.CandidatDtoDao
import com.example.vitesse.domain.model.Candidat
import kotlinx.coroutines.flow.first

class CandidatRepository(private val candidatDao: CandidatDtoDao) {

    suspend fun getAllCandidats(): List<Candidat> {
        return candidatDao.getAllCandidats()
            .first() // Collect the first emission of the Flow
            .map { Candidat.fromDto(it) } // Convert every DTO in Exercise
    }


    suspend fun addCandidat(candidat: Candidat) {
        candidatDao.insertCandidat(candidat.toDto())
    }

    suspend fun deleteExercise(candidat: Candidat) {
        candidat.id?.let {
            candidatDao.deleteCandidatById(
                id = candidat.id,
            )
        }
    }
}