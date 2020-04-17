package com.base.baseApp.repositories

import com.base.baseApp.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Int> {
    fun findUserByEmail(email: String) : Optional<User>
}