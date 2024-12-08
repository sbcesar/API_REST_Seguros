package com.example.unsecuredseguros.service

import com.example.unsecuredseguros.exceptions.NotFoundException
import com.example.unsecuredseguros.exceptions.ValidationException
import com.example.unsecuredseguros.model.AsistenciaMedica
import com.example.unsecuredseguros.repository.AsistenciaMedicaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AsistenciaMedicaService {

    @Autowired
    private lateinit var asistenciaMedicaRepository: AsistenciaMedicaRepository

    fun getAllAsistencias(): List<AsistenciaMedica> {
        return asistenciaMedicaRepository.findAll()
    }

    fun getAsistenciaById(id: String): AsistenciaMedica? {
        val idInt = try {
            id.toInt()
        } catch (e: Exception) {
            throw ValidationException("Asistance id must be integer.")
        }

        val asistencia: AsistenciaMedica? = asistenciaMedicaRepository.findById(idInt.toLong()).orElse(null)

        return asistencia ?: throw NotFoundException("Asistencia ID $idInt not found.")
    }

    fun createAsistencia(asistenciaMedica: AsistenciaMedica): AsistenciaMedica {
        validateAsistenciaMedica(asistenciaMedica)
        return asistenciaMedicaRepository.save(asistenciaMedica)
    }

    fun updateAsistencia(asistenciaMedica: AsistenciaMedica): AsistenciaMedica {
        if (!asistenciaMedicaRepository.existsById(asistenciaMedica.idAsistenciaMedica.toLong())) {
            throw NotFoundException("Asistencia Medica de id ${asistenciaMedica.idAsistenciaMedica} no ha sido encontrado")
        }

        validateAsistenciaMedica(asistenciaMedica)
        return asistenciaMedicaRepository.save(asistenciaMedica)
    }

    fun deleteAsistenciaById(id: String) {
        if (!asistenciaMedicaRepository.existsById(id.toLong())) {
            throw NotFoundException("Asistencia Medica de id $id no ha sido encontrado")
        }
        asistenciaMedicaRepository.deleteById(id.toLong())
    }

    fun validateAsistenciaMedica(asistenciaMedica: AsistenciaMedica) {

        if (asistenciaMedica.breveDescripcion.isBlank()) throw ValidationException("El campo breveDescripcion no puede estar vacío.")
        if (asistenciaMedica.lugar.isBlank()) throw ValidationException("El campo lugar no puede estar vacío.")
        if (asistenciaMedica.explicacion.isBlank()) throw ValidationException("El campo explicacion no puede estar vacío.")
        if (asistenciaMedica.tipoAsistencia == null || asistenciaMedica.tipoAsistencia.isBlank()) throw ValidationException("El campo tipoAsistencia no puede ser nulo.")
        if (asistenciaMedica.fecha == null) throw ValidationException("El campo fecha no puede ser nulo.")
        if (asistenciaMedica.hora == null) throw ValidationException("El campo hora no puede ser nulo.")
        if (asistenciaMedica.importe < 0) throw ValidationException("El campo importe debe ser mayor que 0.")
    }
}