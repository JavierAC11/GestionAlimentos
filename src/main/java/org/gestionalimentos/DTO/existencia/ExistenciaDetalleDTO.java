package org.gestionalimentos.DTO.existencia;

import lombok.Data;

@Data
public class ExistenciaDetalleDTO {
    private Long id;
    private Integer cantidad;
    private String alimento;
    private String ubicacion;
    private String fechaEntrada;
}
