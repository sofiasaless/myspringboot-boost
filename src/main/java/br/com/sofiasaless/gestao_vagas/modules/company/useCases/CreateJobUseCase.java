package br.com.sofiasaless.gestao_vagas.modules.company.useCases;

import org.springframework.stereotype.Service;

import br.com.sofiasaless.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.sofiasaless.gestao_vagas.modules.company.entities.JobEntity;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateJobUseCase {
    
    private final JobRepository jobRepository;

    private final CompanyRepository companyRepository;

    public JobEntity execute (JobEntity jobEntity) {
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFoundException();
        });
        return this.jobRepository.save(jobEntity);
    }
}
