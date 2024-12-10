package org.gestionalimentos.DTO.alimento;

import lombok.Data;
import org.gestionalimentos.Enums.CategoriaSelect;
import org.gestionalimentos.Enums.EstadoSelect;

import java.time.LocalDate;
import java.util.List;

@Data
public class AlimentoDetalleDTO {
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
