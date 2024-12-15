package org.gestionalimentos.DTO.existencia;

import lombok.Data;

/**
 * Clase que representa el objeto de transferencia de datos (DTO) utilizado para modificar la información de una existencia de alimento.
 */
@Data
public class ModificarExistenciaDTO {
    private Integer cantidad;
    private Long alimento;
    private Long ubicacion;
    private String fechaEntrada;
}
