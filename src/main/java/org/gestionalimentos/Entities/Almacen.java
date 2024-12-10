package org.beer.gestionalimentos.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@ToString
@Builder
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nombre;
    private String temperatura;

    @OneToMany(mappedBy = "almacen", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Seccion> Secciones;

}
