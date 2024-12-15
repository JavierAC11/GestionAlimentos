package org.gestionalimentos.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

/**
 * Entidad que representa la Existencia de un alimento en un lugar específico dentro del sistema.
 * Esta clase mapea la tabla 'Existencia' en la base de datos, que contiene información
 * sobre las existencias de alimentos en diferentes ubicaciones.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Existencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private LocalDate fechaEntrada;


    @ManyToOne
    @JoinColumn(name = "alimento_id")
    @NotNull
    private Alimento alimento;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    @NotNull
    private Ubicacion ubicacion;

}
