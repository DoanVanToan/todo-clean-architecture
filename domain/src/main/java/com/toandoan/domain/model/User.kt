package com.toandoan.domain.model

import java.util.*

data class User(
    val id: String,
    val name: String,
    val gender: Int,
    val dateOfBirth: Date,
    val email: String
) : DomainModel()