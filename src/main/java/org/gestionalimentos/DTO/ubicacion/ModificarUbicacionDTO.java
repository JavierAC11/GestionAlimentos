package org.gestionalimentos.DTO.ubicacion;

import lombok.Data;

@Data
public class ModificarUbicacionDTO {

    private String nombre;
    private String descripcion;
    private String tipoUbicacion;
    private Integer capacidad;

}
