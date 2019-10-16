package com.projetofinal.repository;
import com.projetofinal.domain.LogDetalhado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LogDetalhado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogDetalhadoRepository extends JpaRepository<LogDetalhado, Long> {

}
