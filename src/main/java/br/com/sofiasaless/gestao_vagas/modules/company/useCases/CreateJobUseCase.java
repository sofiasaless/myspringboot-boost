package br.com.sofiasaless.gestao_vagas.modules.company.useCases;

import org.springframework.stereotype.Service;

import br.com.sofiasaless.gestao_vagas.modules.company.entities.JobEntity;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateJobUseCase {
    
    private final JobRepository jobRepository;

    public JobEntity execute (JobEntity jobEntity) {
        return this.jobRepository.save(jobEntity);
    }
}
