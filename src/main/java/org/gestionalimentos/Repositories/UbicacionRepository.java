package org.gestionalimentos.Repositories;

import org.gestionalimentos.Entities.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repositorio para la entidad Ubicacion, proporcionando operaciones CRUD básicas sobre la tabla de ubicaciones.
 */
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
}
