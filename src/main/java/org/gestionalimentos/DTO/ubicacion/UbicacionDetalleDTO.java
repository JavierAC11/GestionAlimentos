package org.gestionalimentos.DTO.ubicacion;

import lombok.Data;

@Data
public class UbicacionDetalleDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String tipoUbicacion;
    private Integer capacidad;

}
