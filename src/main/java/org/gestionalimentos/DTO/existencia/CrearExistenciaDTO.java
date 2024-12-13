package org.gestionalimentos.DTO.existencia;

import lombok.Data;

@Data
public class CrearExistenciaDTO {
    private Integer cantidad;
    private Long alimento;
    private Long ubicacion;
}
