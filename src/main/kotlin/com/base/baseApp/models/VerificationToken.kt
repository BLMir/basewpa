package com.base.baseApp.models

import java.sql.Timestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne


@Entity
class VerificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,
    private val token: String,
    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User,
    var expiryDate: Date? = null
) {

    init {
        expiryDate = calculateExpiryDate(EXPIRATION)
    }

    companion object {
        private const val EXPIRATION = 60 * 24
    }

    fun calculateExpiryDate(expiryTimeInMinutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = Timestamp(cal.time.time)
        cal.add(Calendar.MINUTE, expiryTimeInMinutes)
        return Date(cal.time.time)
    } // standard constructors, getters and setters


}