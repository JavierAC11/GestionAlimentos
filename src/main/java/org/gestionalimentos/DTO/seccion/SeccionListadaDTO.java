package org.gestionalimentos.DTO.seccion;

import lombok.Data;

import java.util.List;

@Data
public class SeccionListadaDTO {

    private Long id;
    private Integer limite;
    private String nombre;
    private Integer accesibilidad;
    private Long almacenId;
    private List<Long> recipientesId;
}
