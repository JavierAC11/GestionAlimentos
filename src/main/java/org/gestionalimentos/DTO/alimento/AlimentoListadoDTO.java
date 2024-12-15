package org.gestionalimentos.DTO.alimento;

import lombok.Data;

/**
 * DTO que representa un alimento en un formato más compacto para su listado.
 */
@Data
public class AlimentoListadoDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String estado;
    private String fechaCaducidad;
}
