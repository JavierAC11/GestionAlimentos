package org.gestionalimentos.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

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
