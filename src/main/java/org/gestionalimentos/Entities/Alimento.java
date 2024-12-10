package org.beer.gestionalimentos.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private Boolean perecedero;
    private Boolean abierto;
    private Integer tamanio;
    private LocalDate fechaCaducidad;

    private CategoriaSelect categoria;
    private EstadoSelect estado;


    @ManyToOne
    @JoinColumn(name = "recipiente_id")
    private Recipiente recipiente;
}
