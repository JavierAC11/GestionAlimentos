package org.gestionalimentos.exceptions;

public class AlimentoCaducado extends RuntimeException {
    public AlimentoCaducado(String message) {
        super(message);
    }
}
