package org.gestionalimentos.exceptions;
/**
 * Excepción que se lanza cuando un alimento ha caducado y no puede ser procesado.
 * Extiende RuntimeException, permitiendo que se maneje como una excepción no verificada.
 */

public class AlimentoCaducado extends RuntimeException {
    public AlimentoCaducado(String message) {
        super(message);
    }
}
