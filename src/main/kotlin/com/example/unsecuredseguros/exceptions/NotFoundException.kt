package com.example.unsecuredseguros.exceptions

class NotFoundException(mensaje: String) : Exception("Not found exception (404). $mensaje") {
}