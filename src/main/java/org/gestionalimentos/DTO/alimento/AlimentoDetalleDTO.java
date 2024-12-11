package org.gestionalimentos.DTO.alimento;

import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Data
public class AlimentoDetalleDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String estado;
    private String fechaCaducidad;

}
