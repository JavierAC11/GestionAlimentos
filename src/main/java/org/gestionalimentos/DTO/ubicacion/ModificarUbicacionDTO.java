package org.gestionalimentos.DTO.ubicacion;

import lombok.Data;

/**
 * Clase que representa el objeto de transferencia de datos (DTO) utilizado para modificar una ubicación existente en el sistema.
 */
@Data
public class ModificarUbicacionDTO {

    private String nombre;
    private String descripcion;
    private String tipoUbicacion;
    private Integer capacidad;

}
