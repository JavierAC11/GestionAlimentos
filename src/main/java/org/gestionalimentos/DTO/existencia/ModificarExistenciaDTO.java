package org.gestionalimentos.DTO.existencia;

import lombok.Data;

@Data
public class ModificarExistenciaDTO {
    private Integer cantidad;
    private Long alimento;
    private Long ubicacion;
    private String fechaEntrada;
}
