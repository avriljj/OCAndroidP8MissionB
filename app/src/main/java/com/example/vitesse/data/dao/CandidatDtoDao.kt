package com.example.vitesse.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vitesse.data.entity.CandidatDto

@Dao
interface CandidatDtoDao {
    @Insert
    suspend fun insertCandidate(exercise: CandidatDto): Long


    @Query("SELECT * FROM CandidatDto")
    fun getAllCandidatesLive(): LiveData<List<CandidatDto>>

    @Query("SELECT * FROM CandidatDto")
    suspend fun getAllCandidates(): List<CandidatDto>


    @Query("DELETE FROM CandidatDto WHERE id = :id")
    suspend fun deleteCandidateById(id: Long)

    @Query("DELETE FROM CandidatDto")
    suspend fun deleteAllCandidates()

    @Query("SELECT * FROM CandidatDto WHERE isFav = 1")
    suspend fun getFavoriteCandidates(): List<CandidatDto>

    @Query("SELECT * FROM CandidatDto WHERE isFav = 1")
    fun getFavoriteCandidatesLive(): LiveData<List<CandidatDto>>


}