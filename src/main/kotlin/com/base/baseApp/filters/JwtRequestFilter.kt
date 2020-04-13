package com.base.baseApp.filters

import com.base.baseApp.services.MyUserDetailsService
import com.base.baseApp.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var myUserDetailsService: MyUserDetailsService

    @Override
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        println("@@@@@HERE::::::::")
        val authorizationHeader = request.getHeader("Authorization")

        var username: String? = null
        var jwt: String? = null

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            jwt = authorizationHeader.substring(7)
            username = jwtUtil.extractUsername(jwt)
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
             val userDetails = myUserDetailsService.loadUserByUsername(username)

            if(jwtUtil.validateToken(jwt!!,userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )

                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request,response)
    }

}