package org.gestionalimentos.DTO.alimento;

import lombok.Data;

@Data
public class ModificarAlimentoDTO {

    private String nombre;
    private String tipo;
    private String estado;
    private String fechaCaducidad;

}
