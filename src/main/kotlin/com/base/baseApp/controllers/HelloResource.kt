package com.base.baseApp.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class HelloResource {

    @RequestMapping("/hello")
    fun hello(): String{
        return "hello"
    }
}