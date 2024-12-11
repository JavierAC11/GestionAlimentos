package org.gestionalimentos.DTO.alimento;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;

@Data
public class CrearAlimentoDTO {

    @NonNull
    private String nombre;

    @NotFound
    private String tipo;
    private String estado;
    private String fechaCaducidad;
}
