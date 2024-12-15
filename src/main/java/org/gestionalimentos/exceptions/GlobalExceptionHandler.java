package org.gestionalimentos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * Clase encargada de manejar las excepciones globales en la aplicación.
 * Utiliza la anotación @RestControllerAdvice para interceptar excepciones
 * lanzadas por los controladores y devolver una respuesta adecuada al cliente.
 *
 * Se definen manejadores específicos para las excepciones:
 * - RecursoNoEncontrado: Retorna un error 404 (Not Found).
 * - AlimentoCaducado: Retorna un error 400 (Bad Request).
 * - UbicacionCompleta: Retorna un error 400 (Bad Request).
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontrado.class)
    public ResponseEntity<String> handleRecursoNoEncontrado(RecursoNoEncontrado e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

    }

    @ExceptionHandler(AlimentoCaducado.class)
    public ResponseEntity<String> handleAlimentoCaducado(AlimentoCaducado e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UbicacionCompleta.class)
    public ResponseEntity<String> handleUbicacionCompleta(UbicacionCompleta e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
