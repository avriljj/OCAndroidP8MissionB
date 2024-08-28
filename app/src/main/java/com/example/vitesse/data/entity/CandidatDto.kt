package com.example.vitesse.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "CandidatDto")
data class CandidatDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Auto-generate the primary key
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val birthDate: Date,
    val salary: Int,
    val note: String,
    val isFav: Boolean
)
