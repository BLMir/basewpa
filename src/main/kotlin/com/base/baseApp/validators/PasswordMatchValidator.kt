package com.base.baseApp.validators

import com.base.baseApp.models.dto.UserDto
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordMatchValidator::class])
annotation class PasswordMatch(
    val message: String = "two passwords are not matching",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

class PasswordMatchValidator: ConstraintValidator<PasswordMatch, Any> {

    override fun isValid(userDto: Any, context: ConstraintValidatorContext?): Boolean {
        val user: UserDto = userDto as UserDto

        return user.password == user.matchingPassword
    }
}