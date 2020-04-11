package com.base.baseApp.controllers

import com.base.baseApp.models.AuthenticationRequest
import com.base.baseApp.models.AuthenticatoinResponse
import com.base.baseApp.models.dto.UserDto
import com.base.baseApp.services.MyUserDetailsService
import com.base.baseApp.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import sun.net.httpserver.HttpServerImpl
import javax.validation.Valid

@RestController
class AuthenticationController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userDetailsService: MyUserDetailsService

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @RequestMapping("/authenticate", method = [RequestMethod.POST])
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<AuthenticatoinResponse>{
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
            )
        } catch (e: BadCredentialsException){
            throw Exception("bad credentials")
        }

        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)

        val jwt = jwtUtil.generateToken(userDetails)

        return ResponseEntity.ok(AuthenticatoinResponse(jwt))
    }

    @RequestMapping("/registration", method = [RequestMethod.POST])
    fun registrationEndPoint(@RequestBody @Valid accountDto: UserDto, errors: Errors): String {

        if (errors.hasErrors()){
            return errors.allErrors.toString()
        }
        return accountDto.toString()
    }
}

