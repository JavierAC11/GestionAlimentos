package org.gestionalimentos.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
/**
 * Entidad que representa un Alimento en la base de datos.
 * Esta clase mapea la tabla 'Alimento' en la base de datos, que contiene información
 * sobre los alimentos almacenados en el sistema.
 */
@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nombre;
    private String tipo;
    private String estado;
    private LocalDate fechaCaducidad;
}
