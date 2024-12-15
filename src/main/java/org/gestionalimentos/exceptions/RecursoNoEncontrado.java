package org.gestionalimentos.exceptions;
/**
 * Excepción personalizada que se lanza cuando un recurso solicitado no se encuentra en la base de datos.
 * Esta excepción extiende RuntimeException y se utiliza para indicar que el recurso no existe o no está disponible.
 * Se puede capturar y manejar en un controlador o en un manejador global de excepciones.
 */

public class RecursoNoEncontrado extends RuntimeException {
    public RecursoNoEncontrado(String mensaje) {
        super(mensaje);
    }
}
