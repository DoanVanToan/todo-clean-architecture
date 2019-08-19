package com.toandoan.domain.usecase.user

import java.util.regex.Pattern

interface Validator {
    fun isValidate(
        email: String,
        onValidate: () -> Unit,
        onError: (error: Error) -> Unit
    )
}

interface EmailValidator : Validator
interface PasswordValidator : Validator

class EmailValidatorImpl : EmailValidator {
    private val EMAIL_REGEX = """[a-zA-Z0-9+._%-]{1,256}@+[a-ZA-Z0-9-]{0,64}.+[a-zA-Z0-9]{0,25}"""
    override fun isValidate(
        email: String,
        onValidate: () -> Unit,
        onError: (error: Error) -> Unit
    ) {
        if (email.isEmpty()) {
            onError(Error("Email must not be empty"))
            return
        }
        val pattern = Pattern.compile(EMAIL_REGEX)
        if (!pattern.matcher(email).matches()) {
            onError(Error("Email is not valid format"))
            return
        }
        onValidate()
    }

}

class PasswordValidatorImpl : PasswordValidator {
    override fun isValidate(
        password: String,
        onValidate: () -> Unit,
        onError: (error: Error) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
