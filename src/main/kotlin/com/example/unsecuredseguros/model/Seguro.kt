package com.example.unsecuredseguros.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "seguros")
data class Seguro(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSeguro", nullable = false)
    val idSeguro: Int,

    @Column(nullable = false)
    val nif: String,

    @Column(nullable = false, length = 100)
    val nombre: String,

    @Column(nullable = false, length = 100)
    val ape1: String,

    @Column(length = 100)
    val ape2: String?,

    @Column(nullable = false)
    val edad: Int,

    @Column(name = "num_hijos", nullable = false)
    var numHijos: Int,

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    val fechaCreacion: LocalDateTime,

    @Column(nullable = false, length = 10)
    var sexo: String,

    @Column(nullable = false)
    val casado: Boolean = false,

    @Column(nullable = false)
    val embarazada: Boolean = false
)