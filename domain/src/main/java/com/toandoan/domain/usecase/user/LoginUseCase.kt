package com.toandoan.domain.usecase.user

import com.toandoan.domain.model.User
import com.toandoan.domain.repository.UserRepository
import com.toandoan.domain.usecase.UseCase

class LoginUseCase(
    private val emailValidator: Validator,
    private val passwordValidator: Validator,
    private val repository: UserRepository
) : UseCase<LoginUseCase.Param, User> {

    // insert logic code here
    override fun execute(parram: Param): User {
        try {
            val isValidData = isValidEmailAndPassword(
                parram.email,
                parram.password
            )
            if (isValidData) {
                return repository.login(parram.email, parram.password)
            }
        } catch (error: Error) {
            throw error
        }
        throw UnknownError("Undefined error")
    }

    private fun isValidEmailAndPassword(email: String, password: String): Boolean {
        var isValidEmail = false
        var isValidPassword = false
        emailValidator.isValidate(email, {
            isValidEmail = true
        }) {
            throw it
        }
        passwordValidator.isValidate(password, {
            isValidPassword = true
        }, {
            throw it
        })
        return isValidEmail && isValidPassword
    }

    data class Param(val email: String, val password: String)
}



