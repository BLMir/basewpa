package com.base.baseApp.models

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MyUserDetails(private val user: User) : UserDetails {

    @Override
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.roles.map { SimpleGrantedAuthority(it.rol) } as MutableList
    }

    @Override
    override fun isEnabled(): Boolean {
        return true
    }

    @Override
    override fun getUsername(): String {
        return user.email
    }

    @Override
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @Override
    override fun getPassword(): String {
        return user.passWord
    }

    @Override
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @Override
    override fun isAccountNonLocked(): Boolean {
        return true
    }
}