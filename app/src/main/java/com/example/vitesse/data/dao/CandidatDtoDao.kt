package com.example.vitesse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vitesse.data.entity.CandidatDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidatDtoDao {
    @Insert
    suspend fun insertCandidat(exercise: CandidatDto): Long


    @Query("SELECT * FROM candidat")
    suspend fun getAllCandidats(): List<CandidatDto>


    @Query("DELETE FROM candidat WHERE id = :id")
    suspend fun deleteCandidatById(id: Long)

    @Query("SELECT * FROM candidat WHERE isFav = 1")
    suspend fun getFavoriteCandidats(): List<CandidatDto>


}