package com.example.vitesse.data.repository

import com.example.vitesse.data.dao.CandidatDtoDao
import com.example.vitesse.domain.model.Candidat

class CandidatRepository(private val candidatDao: CandidatDtoDao) {

    suspend fun getAllCandidats(): List<Candidat> {
        return candidatDao.getAllCandidates()
            .map { Candidat.fromDto(it) } // Convert every DTO to Candidat
    }


    suspend fun addCandidat(candidat: Candidat) {
        candidatDao.insertCandidate(candidat.toDto())
    }

    suspend fun deleteExercise(candidat: Candidat) {
        candidat.id?.let {
            candidatDao.deleteCandidateById(
                id = candidat.id,
            )
        }
    }

    suspend fun getFavoriteCandidates(): List<Candidat> {
        return candidatDao.getFavoriteCandidates()
            .map { Candidat.fromDto(it) }
    }
}