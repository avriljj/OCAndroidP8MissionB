package com.example.vitesse.domain.model

import com.example.vitesse.data.entity.CandidatDto
import java.util.Date

class Candidat(
    val id: Long?= null,
    var name: String,
    var surname: String,
    var phone: String,
    var email: String,
    var birthDate: Date?,
    var salary: Int,
    var note: String,
    var isFav: Boolean
){

    fun toDto(): CandidatDto {
        return CandidatDto(
            id = this.id,
            name = this.name,
            surname = this.surname,
            phone = this.phone,
            email = this.email,
            birthDate = this.birthDate,
            salary = this.salary,
            note = this.note,
            isFav = this.isFav
        )
    }


    companion object {
        fun fromDto(dto: CandidatDto): Candidat {
            return Candidat(
                id = dto.id,
                name = dto.name,
                surname = dto.surname,
                phone = dto.phone,
                email = dto.email,
                birthDate = dto.birthDate,
                salary = dto.salary,
                note = dto.note,
                isFav = dto.isFav
            )
        }
    }


}