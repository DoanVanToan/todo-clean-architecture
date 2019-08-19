package com.toandoan.domain.repository

import com.toandoan.domain.model.User

interface UserRepository : Repository {
    fun login(email: String, password: String): User
}