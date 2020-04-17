package com.base.baseApp.utils.email

interface EmailService {
    fun sendSimpleMessage(to: String, subject: String, text: String)
}