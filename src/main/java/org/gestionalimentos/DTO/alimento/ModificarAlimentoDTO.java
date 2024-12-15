package org.gestionalimentos.DTO.alimento;

import lombok.Data;

/**
 * Clase que representa el objeto de transferencia de datos (DTO) utilizado para modificar los detalles de un alimento existente.
 */
@Data
public class ModificarAlimentoDTO {

    private String nombre;
    private String tipo;
    private String estado;
    private String fechaCaducidad;
}
