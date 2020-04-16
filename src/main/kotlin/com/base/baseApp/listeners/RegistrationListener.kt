package com.base.baseApp.listeners

import com.base.baseApp.models.User
import com.base.baseApp.utils.email.EmailServiceImp
import com.base.baseApp.services.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.*

data class OnRegistrationCompleteEvent(
    val appUrl: String,
    val locale: Locale,
    val user: User
): ApplicationEvent(user)

@Component
class RegistrationListener : ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private lateinit var myUserDetailsService: MyUserDetailsService

    @Autowired
    private lateinit var mailSender: EmailServiceImp

    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user: User = event.user
        val token = UUID.randomUUID().toString()
        myUserDetailsService.createVerificationToken(user, token)
        val confirmationUrl: String = event.appUrl + "/regitrationConfirm?token=" + token
        val message = "asdfasdf "

        mailSender.sendSimpleMessage(
            to=user.email,
            subject = "Registration Confirmation",
            text = "$message\r\nhttp://localhost:8080$confirmationUrl"
        )
    }
}