package com.base.baseApp.controllers

import com.base.baseApp.listeners.OnRegistrationCompleteEvent
import com.base.baseApp.models.AuthenticationRequest
import com.base.baseApp.models.AuthenticatoinResponse
import com.base.baseApp.models.dto.UserDto
import com.base.baseApp.services.MyUserDetailsService
import com.base.baseApp.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("auth")
class AuthenticationController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userDetailsService: MyUserDetailsService

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<AuthenticatoinResponse> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
            )
        } catch (e: BadCredentialsException) {
            throw Exception("bad credentials")
        }

        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt = jwtUtil.generateToken(userDetails)

        return ResponseEntity.ok(AuthenticatoinResponse(jwt))
    }

    @PostMapping("/registration")
    fun registrationEndPoint(@RequestBody @Valid accountDto: UserDto, errors: Errors, request: WebRequest): String {
        try {
            val userRegister = userDetailsService.registerNewAccount(userDto = accountDto)

            eventPublisher.publishEvent(OnRegistrationCompleteEvent(request.contextPath, request.locale, userRegister))

        } catch (e: Exception) {
            throw e
        }
        return accountDto.toString()
    }

    @GetMapping("/registrationConfirm")
    fun confirmRegistration(request: WebRequest, @RequestParam("token") token: String): String {
        val verificationToken = userDetailsService.getVerificationToken(token)

        var message = "Verificatoin Ok"

        if (verificationToken == null) {
            message = "invalid token"
        }

        val user = verificationToken!!.user
        val cal = Calendar.getInstance()

        if ((verificationToken!!.expiryDate!!.time - cal.time.time) <= 0) {
            message = "token expired"
        }


        userDetailsService.saveRegisteredUser(user.copy(enabled = true))

        return message
    }
}

