package org.gestionalimentos.Entities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Entity;

import java.util.List;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Recipiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String tamanio;

    @ManyToOne
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    @OneToMany(mappedBy = "recipiente", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Alimento> alimentos;
}
