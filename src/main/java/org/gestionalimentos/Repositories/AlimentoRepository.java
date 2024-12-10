package org.gestionalimentos.Repositories;


import org.gestionalimentos.Entities.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}
