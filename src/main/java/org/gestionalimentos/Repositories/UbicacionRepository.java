package org.gestionalimentos.Repositories;

import org.gestionalimentos.Entities.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
}
