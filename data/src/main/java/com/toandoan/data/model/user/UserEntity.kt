package com.toandoan.data.model.user

import com.toandoan.data.model.EnityMapper
import com.toandoan.data.model.EnityModel
import com.toandoan.domain.model.User
import java.util.*

data class UserEntity(
    val id: String,
    val name: String,
    val gender: Int,
    val dateOfBirth: Date,
    val email: String
) : EnityModel() {
    companion object {
        val dump = UserEntity(
            id = UUID.randomUUID().toString(),
            name = "Doan Van Toan",
            gender = 1,
            dateOfBirth = Date(26, 6, 1993),
            email = "doan.van.toan@sun-asterisk.com"
        )
    }
}

class UserEntityMapper : EnityMapper<User, UserEntity>() {
    override fun mapToEnity(domainModel: User): UserEntity {
        return UserEntity(
            id = domainModel.id,
            name = domainModel.name,
            gender = domainModel.gender,
            dateOfBirth = domainModel.dateOfBirth,
            email = domainModel.email
        )
    }

    override fun mapToDomain(enityModel: UserEntity): User {
        return User(
            id = enityModel.id,
            name = enityModel.name,
            gender = enityModel.gender,
            dateOfBirth = enityModel.dateOfBirth,
            email = enityModel.email
        )
    }

}