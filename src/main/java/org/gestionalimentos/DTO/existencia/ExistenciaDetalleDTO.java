package org.gestionalimentos.DTO.existencia;

import lombok.Data;

/**
 * Clase que representa el objeto de transferencia de datos (DTO) con los detalles de una existencia de alimento.
 */
@Data
public class ExistenciaDetalleDTO {
    private Long id;
    private Integer cantidad;
    private String alimento;
    private String ubicacion;
    private String fechaEntrada;
}
