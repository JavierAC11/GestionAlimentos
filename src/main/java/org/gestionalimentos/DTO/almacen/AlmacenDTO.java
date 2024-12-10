package org.gestionalimentos.DTO.almacen;

import lombok.Data;
import org.gestionalimentos.Entities.Seccion;

import java.util.List;

@Data
public class AlmacenDTO {
    private Long id;
    private String nombre;
    private String temperatura;
    private List<Seccion> secciones;

}
