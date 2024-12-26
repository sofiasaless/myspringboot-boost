package br.com.sofiasaless.gestao_vagas.modules.candidate.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sofiasaless.gestao_vagas.modules.company.entities.JobEntity;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListAllJobsByFIlterUseCase {
    
    private final JobRepository jobRepository;

    public List<JobEntity> execute (String filter) {
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }

}
