package org.gestionalimentos.exceptions;
/**
 * Excepción personalizada que se lanza cuando una ubicación está llena y no puede almacenar más alimentos.
 * Esta excepción extiende RuntimeException y se utiliza para indicar que la capacidad de la ubicación ha sido alcanzada.
 * Se puede capturar y manejar en un controlador o en un manejador global de excepciones.
 */

public class UbicacionCompleta extends RuntimeException {
    public UbicacionCompleta(String message) {
        super(message);
    }
}
