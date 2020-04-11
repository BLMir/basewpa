package com.base.baseApp.models.dto

import com.base.baseApp.validators.PasswordMatch
import com.base.baseApp.validators.ValidEmail
import com.sun.istack.NotNull
import javax.validation.constraints.NotEmpty

@PasswordMatch
data class UserDto(
    @field:NotNull
    @field:NotEmpty
    val firstName: String,

    @field:NotNull
    @field:NotEmpty
    val lastName: String,

    @field:NotNull
    @field:NotEmpty
    val password: String,

    @field:NotEmpty
    @field:NotNull
    val matchingPassword: String,

    @field:NotNull
    @field:NotEmpty
    @field:ValidEmail
    val email: String
)