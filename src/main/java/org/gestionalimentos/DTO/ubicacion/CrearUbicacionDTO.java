package org.gestionalimentos.DTO.ubicacion;

import lombok.Data;

@Data
public class CrearUbicacionDTO {

    private String descripcion;
    private String nombre;
    private String tipoUbicacion;
    private Integer capacidad;

}
