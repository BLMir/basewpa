package com.base.baseApp.models.dto

import com.base.baseApp.validators.ValidEmail
import com.sun.istack.NotNull
import javax.validation.constraints.NotEmpty

data class UserDto(
    @field:NotNull
    @field:NotEmpty
    private val firstName: String,

    @field:NotNull
    @field:NotEmpty
    private val lastName: String,

    @field:NotNull
    @field:NotEmpty
    private val password: String,

    @field:NotNull
    @field:NotEmpty
    @field:ValidEmail
    private val email: String
)