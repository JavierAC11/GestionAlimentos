package org.gestionalimentos.Repositories;


import org.gestionalimentos.Entities.Alimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

    Page<Alimento> findByFechaCaducidadBefore(LocalDate fechaLimite, Pageable pageable);
}
