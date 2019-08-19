package com.toandoan.data.repository.user

import com.toandoan.domain.model.User
import com.toandoan.domain.repository.UserRepository

class UserRepositoryImpl constructor(
    private val remote: UserRepository
) : UserRepository {

    override fun login(email: String, password: String) = remote.login(email, password)
}