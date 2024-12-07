package com.example.unsecuredseguros.controller

import com.example.unsecuredseguros.exceptions.ValidationException
import com.example.unsecuredseguros.model.Seguro
import com.example.unsecuredseguros.service.SeguroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/seguros")
class SeguroController {

    @Autowired
    private lateinit var seguroService: SeguroService

    @GetMapping("/seguros")
    fun getAllSeguros(): ResponseEntity<List<Seguro>> {
        val seguros = seguroService.getAllSeguros()

        return if (seguros.isNotEmpty()) {
            ResponseEntity(seguros, HttpStatus.OK)
        } else {
            ResponseEntity(emptyList(), HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping("/{idSeguro}")
    fun getSeguroById(
        @PathVariable idSeguro: String?
    ): ResponseEntity<Seguro>? {

        if (idSeguro.isNullOrBlank()) {
            throw ValidationException("idSeguro can not be blank or null")
        }

        val seguro = seguroService.getSeguroById(idSeguro)

        if (seguro == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        return ResponseEntity(seguro, HttpStatus.OK)
    }

    @PostMapping("/seguros")
    fun createSeguro(
        @RequestBody seguro: Seguro?
    ): ResponseEntity<Seguro> {
        if (seguro == null) {
            throw ValidationException("seguro can not be null")
        }

        try {
            val seguroCreated = seguroService.createSeguro(seguro)
            return ResponseEntity(seguroCreated, HttpStatus.CREATED)
        } catch (e: Exception) {
            throw ValidationException("Seguro can not be created ${e.message}")
        }
    }

    @PostMapping("/seguros/{idSeguro}")
    fun updateSeguroById(
        @PathVariable idSeguro: Int?,
        @RequestBody seguro: Seguro?
    ): ResponseEntity<Seguro> {
        if (idSeguro == null) {
            throw ValidationException("idSeguro can not be null")
        }

        if (seguro == null) {
            throw ValidationException("seguro can not be null")
        }

        val verificeteSeguro = seguroService.getSeguroById(idSeguro.toString())

        if (verificeteSeguro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        try {
            val seguroUpdated = seguroService.updateSeguro(seguro)
            return ResponseEntity(seguroUpdated, HttpStatus.OK)
        } catch (e: Exception) {
            throw ValidationException("Seguro can not be updated ${e.message}")
        }
    }

    @DeleteMapping("/seguros/{idSeguro}")
    fun deleteSeguroById(
        @PathVariable idSeguro: Int
    ): ResponseEntity<Seguro> {
        if (idSeguro == null) {
            throw ValidationException("idSeguro can not be null")
        }

        val verificateSeguro = seguroService.getSeguroById(idSeguro.toString())
        if (verificateSeguro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        seguroService.deleteSeguro(idSeguro)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}

//      Para getSeguroById
//
//        if (idSeguro == null) {
//            return ResponseEntity<Any?>("Id $idSeguro can not be null", HttpStatus.BAD_REQUEST)
//        }
//
//        val seguro = seguroService.getSeguroById(idSeguro)
//
//        // return ResponseEntity.ok(seguro)
//        return ResponseEntity<Any?>(seguro, HttpStatus.OK)