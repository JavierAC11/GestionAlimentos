package org.gestionalimentos.DTO.ubicacion;

import lombok.Data;

/**
 * Clase que representa el objeto de transferencia de datos (DTO) utilizado para crear una nueva ubicación en el sistema.
 */
@Data
public class CrearUbicacionDTO {

    private String descripcion;
    private String nombre;
    private String tipoUbicacion;
    private Integer capacidad;

}
