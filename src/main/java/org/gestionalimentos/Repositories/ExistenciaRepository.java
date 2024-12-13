package org.gestionalimentos.Repositories;

import org.gestionalimentos.Entities.Existencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExistenciaRepository extends JpaRepository<Existencia, Long> {

    Page<Existencia> findByUbicacionId(Long ubicacionId, Pageable pageable);
}

