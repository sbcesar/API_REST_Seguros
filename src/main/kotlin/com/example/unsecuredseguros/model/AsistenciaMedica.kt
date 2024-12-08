package com.example.unsecuredseguros.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "asistencias")
data class AsistenciaMedica(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia_medica", nullable = false)
    val idAsistenciaMedica: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seguro", nullable = false)
    val seguro: Seguro, // Relación con Seguro

    @Column(name = "breve_descripcion", nullable = false, length = 255)
    val breveDescripcion: String,

    @Column(nullable = false, length = 255)
    val lugar: String,

    @Column(nullable = false)
    val explicacion: String,

    @Column(nullable = false, length = 100)
    val tipoAsistencia: String,

    @Column(nullable = false)
    val fecha: LocalDate,

    @Column(nullable = false)
    val hora: LocalTime,

    @Column(nullable = false)
    val importe: Double
)
