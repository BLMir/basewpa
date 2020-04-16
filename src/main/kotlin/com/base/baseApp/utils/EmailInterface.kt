package com.base.baseApp.utils

import org.springframework.mail.SimpleMailMessage


interface EmailInterface {

    fun sendSimpleMessage(to: String, subject: String, text: String)

    fun sendSimpleMessageUsingTemplate(to: String,subject: String,template: SimpleMailMessage,vararg templateArgs: String)
}