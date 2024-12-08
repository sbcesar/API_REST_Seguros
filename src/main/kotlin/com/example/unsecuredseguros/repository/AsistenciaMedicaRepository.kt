package com.example.unsecuredseguros.repository

import com.example.unsecuredseguros.model.AsistenciaMedica
import org.springframework.data.jpa.repository.JpaRepository

interface AsistenciaMedicaRepository : JpaRepository<AsistenciaMedica, Long> {
}