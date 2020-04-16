package com.base.baseApp.controllers

import com.base.baseApp.services.MyUserDetailsService
import com.base.baseApp.utils.email.EmailServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("user/")
class UserController {

    @Autowired
    private lateinit var myUserDetailsService: MyUserDetailsService

    @Autowired
    private lateinit var mailSender: EmailServiceImp

    @GetMapping("resendRegistrationConfirm")
    fun resendRegistrationConfirm(request: HttpServletRequest, @RequestParam("token") token: String) {
        val newToken = myUserDetailsService.generateNewVerificationToken(token)

        val user = myUserDetailsService.getUser(newToken.token)

        val appUrl = "http://" + request.serverName +
            ":" + request.serverPort +
            request.contextPath
        val confirmationUrl: String = appUrl + "/auth/registrationConfirm?token=" + newToken.token
        mailSender.sendSimpleMessage(
            to=user.email,
            subject = "Resend Registration Confirmation",
            text = "message \r\n$confirmationUrl"
        )


    }
}