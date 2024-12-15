package org.gestionalimentos.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
/**
 * Entidad que representa una Ubicación dentro del sistema.
 * Esta clase mapea la tabla 'Ubicacion' en la base de datos, que contiene información
 * sobre los lugares o áreas donde se almacenan los alimentos en el sistema.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nombre;
    private String descripcion;
    private String tipoUbicacion;
    private Integer capacidad;


}
