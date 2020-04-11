package com.base.baseApp.models

data class AuthenticationRequest(val username: String,val password: String)
data class AuthenticatoinResponse(val jwt: String)