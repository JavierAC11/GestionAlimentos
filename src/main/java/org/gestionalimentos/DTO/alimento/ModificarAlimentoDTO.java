package org.gestionalimentos.DTO.alimento;

import lombok.Data;

@Data
public class ModificarAlimentoDTO {

    private String nombre;
    private Boolean perecedero;
    private Boolean abierto;
    private Integer tamanio;
    private String fechaCaducidad;
    private String categoria;
    private String estado;
    private Long recipienteId;

}
