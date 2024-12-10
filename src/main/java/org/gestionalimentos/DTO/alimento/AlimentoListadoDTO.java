package org.gestionalimentos.DTO.alimento;

import lombok.Data;
import org.gestionalimentos.Enums.CategoriaSelect;
import org.gestionalimentos.Enums.EstadoSelect;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@Data
public class AlimentoListadoDTO {
    private Long id;
    private String nombre;
    private Boolean perecedero;
    private Boolean abierto;
    private Integer tamanio;
    private LocalDate fechaCaducidad;
    private CategoriaSelect categoria;
    private EstadoSelect estado;
    private Long idRecipiente;
}
