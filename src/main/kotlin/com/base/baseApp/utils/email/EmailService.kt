package com.base.baseApp.utils.email

import javax.security.auth.Subject

interface EmailService {
    fun sendSimpleMessage(to: String, subject: String, text: String)
}