package com.toandoan.domain.model

data class Task(
    val id: Long,
    val title: String,
    val isDone: Boolean
) : DomainModel()