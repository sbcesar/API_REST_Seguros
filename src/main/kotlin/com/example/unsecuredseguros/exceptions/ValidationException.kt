package com.example.unsecuredseguros.exceptions

class ValidationException(mensaje: String) : Exception("Error en la validación (400). $mensaje") {


}