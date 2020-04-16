package com.base.baseApp.models

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(nullable = false)
    val email: String,
    val firstName: String,
    val lastName: String,
    val passWord: String,
    val enabled: Boolean = false,
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    val roles: List<Rol> = emptyList()
)

@Entity
@Table(name = "users_rol")
data class Rol(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(nullable = false)
    val email: String,
    val rol: String,
    @ManyToOne
    @JoinColumn(name= "email", nullable = false, insertable = false, updatable = false)
    val user: User
)
