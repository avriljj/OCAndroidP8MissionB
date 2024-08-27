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


    @Query("SELECT * FROM CandidatDto")
    suspend fun getAllCandidats(): List<CandidatDto>


    @Query("DELETE FROM CandidatDto WHERE id = :id")
    suspend fun deleteCandidatById(id: Long)

    @Query("DELETE FROM CandidatDto")
    suspend fun deleteAllCandidats()

    @Query("SELECT * FROM CandidatDto WHERE isFav = 1")
    suspend fun getFavoriteCandidats(): List<CandidatDto>


}