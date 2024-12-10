package org.gestionalimentos.Repositories;

import org.gestionalimentos.Entities.Recipiente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipienteRepository extends JpaRepository<Recipiente, Long> {

    Page<Recipiente> findById(Long id, Pageable pageable);

}
