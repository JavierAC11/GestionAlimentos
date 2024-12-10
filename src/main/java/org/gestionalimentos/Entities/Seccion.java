package org.beer.gestionalimentos.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@ToString
@Builder
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Integer limite;
    private String nombre;
    private Integer accesibilidad;

    @ManyToOne
    @JoinColumn(name = "almacen_id")
    @JsonBackReference
    private Almacen almacen;

    @OneToMany(mappedBy = "recipiente", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Recipiente> recipientes;
}
