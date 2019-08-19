package com.toandoan.data.remote

import com.toandoan.data.model.user.UserEntity
import com.toandoan.data.model.user.UserEntityMapper
import com.toandoan.domain.model.User
import com.toandoan.domain.repository.UserRepository

class UserRemoteDataSource(
    private val mapper: UserEntityMapper
) : UserRepository {

    override fun login(email: String, password: String): User {
        // fake data from remote.
        if (email == TEST_EMAIL && password == TEST_PASSWORD) {
            return mapper.mapToDomain(UserEntity.dump)
        }
        throw Error("Invalid email or password")
    }

    companion object {
        private const val TEST_EMAIL = "doan.van.toan@sun-asterisk.com"
        private const val TEST_PASSWORD = "123456"
    }
}