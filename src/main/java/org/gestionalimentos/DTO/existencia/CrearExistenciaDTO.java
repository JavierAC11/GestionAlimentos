package org.gestionalimentos.DTO.existencia;

import lombok.Data;

/**
 * Clase que representa el objeto de transferencia de datos (DTO) utilizado para crear una nueva existencia de alimento.
 */
@Data
public class CrearExistenciaDTO {
    private Integer cantidad;
    private Long alimento;
    private Long ubicacion;
}
