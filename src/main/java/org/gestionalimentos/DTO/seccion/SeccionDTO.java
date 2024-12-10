package org.gestionalimentos.DTO.seccion;

import lombok.Data;

@Data
public class SeccionDTO {
    private Long id;
    private Integer limite;
    private String nombre;
    private Integer accesibilidad;
    private Long almacenId;
    private Long[] recipientesId;
}
