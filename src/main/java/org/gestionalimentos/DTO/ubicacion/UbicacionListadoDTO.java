package org.gestionalimentos.DTO.ubicacion;

import lombok.Data;

/**
 * Clase que representa el objeto de transferencia de datos (DTO) que contiene los detalles resumidos de una ubicación.
 * Se utiliza cuando se desea mostrar un listado de ubicaciones con información básica.
 */
@Data
public class UbicacionListadoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String tipoUbicacion;
    private Integer capacidad;

}
