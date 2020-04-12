package com.base.baseApp.services

import com.base.baseApp.MyUserDetails
import com.base.baseApp.models.Rol
import com.base.baseApp.models.User
import com.base.baseApp.models.dto.UserDto
import com.base.baseApp.repositories.RolRepository
import com.base.baseApp.repositories.UserRepository
import com.base.exceptions.UserAlreadyExist
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var rolRepository: RolRepository

    @Override
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findUserByEmail(email)
        user.orElseGet{
            throw UsernameNotFoundException("not found by user name $email")
        }


        return MyUserDetails(user.get())
    }

    fun registerNewAccount(userDto: UserDto): User {
        if (emailExist(userDto.email)){
            throw UserAlreadyExist("this email is already in our system")
        }

        val userRegistered = User(
            email = userDto.email,
            passWord = userDto.password,
            lastName = userDto.lastName,
            firstName = userDto.firstName,
            active = true
        )
        userRepository.save(userRegistered)

        val rol = rolRepository.save(Rol(
            rol = "ROLE_USER", email = userDto.email
        ))

        return userRegistered.copy(roles = listOf(rol))


    }

    private fun emailExist(email: String) =userRepository.findUserByEmail(email) != null

}


