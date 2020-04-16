package com.base.baseApp.services

import com.base.baseApp.exceptions.UserAlreadyExist
import com.base.baseApp.models.MyUserDetails
import com.base.baseApp.models.Rol
import com.base.baseApp.models.User
import com.base.baseApp.models.VerificationToken
import com.base.baseApp.models.dto.UserDto
import com.base.baseApp.repositories.RolRepository
import com.base.baseApp.repositories.UserRepository
import com.base.baseApp.repositories.VerificationTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*



@Service
class MyUserDetailsService: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var rolRepository: RolRepository

    @Autowired
    lateinit var tokenRepository: VerificationTokenRepository

    @Autowired
    lateinit var byBCryptPasswordEncoder: BCryptPasswordEncoder

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
            passWord = byBCryptPasswordEncoder.encode(userDto.password),
            lastName = userDto.lastName,
            firstName = userDto.firstName
        )

        userRepository.save(userRegistered)

        val rol = rolRepository.save(Rol(
            rol = "ROLE_USER", email = userDto.email, user = userRegistered
        ))

        return userRegistered.copy(roles = listOf(rol))

    }

    fun getUser(verificationToken: String): User {
        return tokenRepository.findByToken(verificationToken).user
    }

    fun getVerificationToken(VerificationToken: String): VerificationToken? {
        return tokenRepository.findByToken(VerificationToken)
    }

    fun saveRegisteredUser(user: User) {
        userRepository.save(user)
    }

    fun createVerificationToken(user: User, token: String) {
        val myToken = VerificationToken(token = token, user = user)
        tokenRepository.save(myToken)
    }

    fun generateNewVerificationToken(existingVerificationToken: String?): VerificationToken {
        var vToken = tokenRepository.findByToken(existingVerificationToken!!)
        vToken.updateToken(UUID.randomUUID()
            .toString())
        vToken = tokenRepository.save(vToken)
        return vToken
    }

    private fun emailExist(email: String) =userRepository.findUserByEmail(email).isPresent
}


