package com.example.vitesse.domain.usecase

import com.example.vitesse.data.repository.CandidatRepository
import com.example.vitesse.domain.model.Candidat
import javax.inject.Inject

class AddNewCandidatUseCase @Inject constructor(private val candidatRepository: CandidatRepository) {
    suspend fun execute(candidat: Candidat) {
        candidatRepository.addCandidat(candidat)
    }
}