package com.example.unsecuredseguros.controller

import com.example.unsecuredseguros.exceptions.ValidationException
import com.example.unsecuredseguros.model.AsistenciaMedica
import com.example.unsecuredseguros.service.AsistenciaMedicaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/asistencias")
class AsistenciaMedicaController {

    @Autowired
    private lateinit var asistenciaMedicaService: AsistenciaMedicaService

    @GetMapping("/asistencias")
    fun getAllAsistencias(): ResponseEntity<List<AsistenciaMedica>> {
        val asistencias = asistenciaMedicaService.getAllAsistencias()

        return if (asistencias.isNotEmpty()){
            ResponseEntity(asistencias, HttpStatus.OK)
        } else {
            ResponseEntity(emptyList(), HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping("/asistencias/{idAsistenciaMedica}")
    fun getAsistenciaById(
        @PathVariable idAsistenciaMedica: String?
    ): ResponseEntity<AsistenciaMedica> {

        if (idAsistenciaMedica == null){
            throw ValidationException("El id no puede ser null.")
        }

        val asistencia = asistenciaMedicaService.getAsistenciaById(idAsistenciaMedica)

        if (asistencia == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        return ResponseEntity(asistencia, HttpStatus.OK)
    }

    @PostMapping("/seguros/{id}/asistencias")
    fun createAsistencia(
        @RequestBody asistenciaMedica: AsistenciaMedica?
    ): ResponseEntity<AsistenciaMedica> {
        if (asistenciaMedica == null){
            throw ValidationException("Asistencia can not be null.")
        }

        try {
            val asistenciaCreated = asistenciaMedicaService.createAsistencia(asistenciaMedica)
            return ResponseEntity(asistenciaCreated, HttpStatus.CREATED)
        } catch (e: Exception) {
            throw ValidationException("Asistencia can not be created ${e.message}.")
        }
    }

    @PutMapping("/asistencias/{idAsistenciaMedica}")
    fun updateAsistenciaById(
        @PathVariable idAsistenciaMedica: Int?,
        @RequestBody asistenciaMedica: AsistenciaMedica?
    ): ResponseEntity<AsistenciaMedica> {
        if (idAsistenciaMedica == null){
            throw ValidationException("idAsistenciaMedica can not be null.")
        }

        if (asistenciaMedica == null){
            throw ValidationException("Asistencia can not be null.")
        }

        val verificatedAsistencia = asistenciaMedicaService.getAsistenciaById(idAsistenciaMedica.toString())
        if (verificatedAsistencia == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        try {
            val asistenciaUpdated = asistenciaMedicaService.updateAsistencia(verificatedAsistencia)
            return ResponseEntity(asistenciaUpdated, HttpStatus.OK)
        } catch (e: Exception) {
            throw ValidationException("Asistencia can not be updated ${e.message}.")
        }
    }

    @DeleteMapping("/asistencias/{idAsistenciaMedica}")
    fun deleteAsistenciaById(
        @PathVariable idAsistenciaMedica: Int?
    ): ResponseEntity<String> {
        if (idAsistenciaMedica == null){
            throw ValidationException("idAsistenciaMedica can not be null.")
        }

        val verificatedAsistencia = asistenciaMedicaService.getAsistenciaById(idAsistenciaMedica.toString())
        if (verificatedAsistencia == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        asistenciaMedicaService.deleteAsistenciaById(idAsistenciaMedica.toString())
        return ResponseEntity(HttpStatus.OK)
    }
}