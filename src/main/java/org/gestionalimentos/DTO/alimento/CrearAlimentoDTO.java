package org.gestionalimentos.DTO.alimento;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;

@Data
public class CrearAlimentoDTO {

    @NonNull
    private String nombre;

    @NotFound
    private Boolean perecedero;
    private Boolean abierto;
    private Integer tamanio;
    private String fechaCaducidad;
    private String categoria;
    private String estado;
    private Long recipienteId;

}
