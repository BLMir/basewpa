package com.base.baseApp

import com.base.baseApp.models.User
import com.base.baseApp.services.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*

@Component
class RegistrationListener : ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private lateinit var myUserDetailsService: MyUserDetailsService

    @Autowired
    private lateinit var mailSender: JavaMailSender

    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user: User = event.user
        val token = UUID.randomUUID().toString()
        myUserDetailsService.createVerificationToken(user, token)
        val recipientAddress: String = user.email
        val subject = "Registration Confirmation"
        val confirmationUrl: String = event.appUrl + "/regitrationConfirm.html?token=" + token
        val message = "asdfasdf "
        val email = SimpleMailMessage()
        email.setTo(recipientAddress)
        email.setSubject(subject)
        email.setText("$message\r\nhttp://localhost:8080$confirmationUrl")
        mailSender.send(email)
    }
}