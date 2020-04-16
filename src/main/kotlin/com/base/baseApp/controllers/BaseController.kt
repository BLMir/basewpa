package com.base.baseApp.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class BaseController {

    @GetMapping("/api/healths")
    fun index() = Health.UP.status

    @GetMapping("/")
    fun home() = "<h1> Welcome </h1>"

    @GetMapping("/user")
    fun user() = "<h1> Welcome User </h1>"

    @GetMapping("/admin")
    fun admin() = "<h1> Welcome Admin </h1>"
}

data class Health(val status: String) {
    companion object {
        val UP = Health("UP")
    }
}