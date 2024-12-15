package org.gestionalimentos.DTO.alimento;

import lombok.Data;

/**
 * DTO que representa los detalles de un alimento.
 */
@Data
public class AlimentoDetalleDTO {

    private Long id;
    private String nombre;
    private String tipo;
    private String estado;
    private String fechaCaducidad;

}
