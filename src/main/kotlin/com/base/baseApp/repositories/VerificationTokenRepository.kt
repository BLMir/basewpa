package com.base.baseApp.repositories

import com.base.baseApp.models.User
import com.base.baseApp.models.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository

interface VerificationTokenRepository: JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): VerificationToken
    fun findByUser(user: User): VerificationToken
}