package org.gestionalimentos.DTO.ubicacion;

import lombok.Data;
/**
 * Clase que representa el objeto de transferencia de datos (DTO) que contiene los detalles de una ubicación específica en el sistema.
 */
@Data
public class UbicacionDetalleDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String tipoUbicacion;
    private Integer capacidad;

}
