package br.com.sofiasaless.gestao_vagas.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sofiasaless.gestao_vagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    // query para recuperar vagas a partir de um filtro
    List<JobEntity> findByDescriptionContaining(String description);

}