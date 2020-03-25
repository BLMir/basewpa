package com.base.baseApp

import com.base.baseApp.repositories.UserRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = [UserRepository::class])
class BaseAppApplication

fun main(args: Array<String>) {
	runApplication<BaseAppApplication>(*args)
}
