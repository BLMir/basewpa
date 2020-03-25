package com.base.baseApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var userDetailsService: MyUserDetailsService

    @Override
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/user").hasAnyRole("USER", "ADMIN")
            .antMatchers("/").permitAll()
            .and().formLogin()

    }

    @Override
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)

//        auth.jdbcAuthentication().dataSource(dataSource)
//            .usersByUsernameQuery("SELECT username, password, enabled " +
//                "from users " +
//                "where username = ? ")
//            .authoritiesByUsernameQuery("SELECT username, authority " +
//                "from authorities " +
//                "where username = ? ")

    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder{
        return NoOpPasswordEncoder.getInstance()
    }
}