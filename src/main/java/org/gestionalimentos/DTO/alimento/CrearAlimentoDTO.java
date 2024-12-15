package org.gestionalimentos.DTO.alimento;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;

/**
 * Clase que representa el objeto de transferencia de datos (DTO) utilizado para crear un nuevo alimento.
 */
@Data
public class CrearAlimentoDTO {

    @NonNull
    private String nombre;

    @NotFound
    private String tipo;

    private String estado;

    private String fechaCaducidad;
}
