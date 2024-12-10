package org.gestionalimentos.DTO.recipiente;

import lombok.Data;
import org.gestionalimentos.Entities.Alimento;

import java.util.List;

@Data
public class RecipienteDetalleDTO {
    private Long id;
    private String tamanio;
    private Long seccionId;
    private String seccionNombre;
    private List<Alimento> alimentos;

}
