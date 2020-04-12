package com.base.baseApp.repositories

import com.base.baseApp.models.Rol
import org.springframework.data.jpa.repository.JpaRepository

interface RolRepository: JpaRepository<Rol, Int> {
}