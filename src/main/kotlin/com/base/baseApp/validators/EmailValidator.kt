package com.base.baseApp.validators

import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailValidator::class])
annotation class ValidEmail(
    val message: String = "email not valid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

class EmailValidator: ConstraintValidator<ValidEmail,String> {

    private lateinit var pattern: Pattern
    private lateinit var matcher: Matcher
    companion object{
        const val EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$"
    }

    override fun isValid(email: String, context: ConstraintValidatorContext?): Boolean {
        return (validateEmail(email))
    }

    override fun initialize(p0: ValidEmail?) {}

    private fun validateEmail(email: String): Boolean {
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)

        return matcher.matches()
    }

}