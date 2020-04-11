package com.base.baseApp.services

import com.base.baseApp.MyUserDetails
import com.base.baseApp.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Override
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findUserByEmail(email)
        user.orElseGet{
            throw UsernameNotFoundException("not found by user name $email")
        }


        return MyUserDetails(user.get())
    }
}