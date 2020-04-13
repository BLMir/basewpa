package com.base.baseApp

import com.base.baseApp.models.User
import org.springframework.context.ApplicationEvent
import java.util.*


data class OnRegistrationCompleteEvent(
    val appUrl: String,
    val locale: Locale,
    val user: User
): ApplicationEvent(user)