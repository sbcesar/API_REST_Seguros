package com.example.unsecuredseguros.service

import com.example.unsecuredseguros.exceptions.NotFoundException
import com.example.unsecuredseguros.exceptions.ValidationException
import com.example.unsecuredseguros.model.Seguro
import com.example.unsecuredseguros.repository.SeguroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SeguroService {

    @Autowired
    private lateinit var serviceRepository: SeguroRepository

    fun getAllSeguros(): List<Seguro> {
        return serviceRepository.findAll()
    }

    fun getSeguroById(id: String): Seguro? {
        val idInt: Int = try {
            id.toInt()
        } catch (e: Exception) {
            throw ValidationException("Seguro ID must be integer.")
        }

        val seguro: Seguro? = serviceRepository.findById(idInt.toLong()).orElse(null)

        return seguro ?: throw NotFoundException("Seguro not found.")

        /*
        Variante usando Optional (No lo he usado nunca)

        val optionalSeguro = serviceRepository.findById(idInt.toLong())

        if (optionalSeguro.isPresent) {
            return optionalSeguro.get()
        } else {
            throw NotFoundException("Seguro not found.")
        }
         */

    }

    fun createSeguro(seguro: Seguro): Seguro {
        validateSeguro(seguro)
        return serviceRepository.save(seguro)
    }

    fun updateSeguro(seguro: Seguro): Seguro {
        if (!serviceRepository.existsById(seguro.idSeguro.toLong())) {
            throw NotFoundException("Seguro ${seguro.idSeguro} not found.")
        }

        validateSeguro(seguro)
        return serviceRepository.save(seguro)
    }

    fun deleteSeguro(idSeguro: Int) {
        if (!serviceRepository.existsById(idSeguro.toLong())) {
            throw NotFoundException("Seguro $idSeguro not found.")
        }
        serviceRepository.deleteById(idSeguro.toLong())
    }

    fun validateSeguro(seguro: Seguro) {

        validateNif(seguro.nif)

        if (seguro.nombre.isBlank()) throw ValidationException("El nombre no puede estar vacío.")
        if (seguro.ape1.isBlank()) throw ValidationException("El primer apellido no puede estar vacío.")

        if (seguro.edad <= 0) throw ValidationException("La edad debe ser mayor que 0.")
        if (seguro.edad < 18) throw ValidationException("No es posible ser menor de edad para hacer un seguro.")

        if (seguro.sexo.isBlank()) throw ValidationException("El sexo no puede ser nulo.")

        if (seguro.numHijos < 0) throw ValidationException("El número de hijos no puede ser menor que 0.")

        if (!seguro.casado) {
            seguro.numHijos = 0
        }

        if (seguro.embarazada) {
            seguro.sexo = "Mujer"
        }
    }

    fun validateNif(nif: String) {
        if (nif.length != 9) {
            throw ValidationException("NIF must be 9 digits.")
        }

        val numbers = nif.substring(0,8)
        val validatedNumbers = numbers.all { it.isDigit() }
        val letter = nif[8]

        if (!validatedNumbers) {
            throw ValidationException("First 8 characters of NIF must be digits.")
        }

        if (!letter.isUpperCase() || !letter.isLetter()) {
            throw ValidationException("The last character of NIF must be a letter (upper).")
        }
    }
}