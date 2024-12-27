package br.com.sofiasaless.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.sofiasaless.gestao_vagas.exceptions.JobNotFoundException;
import br.com.sofiasaless.gestao_vagas.exceptions.UserNotFoundException;
import br.com.sofiasaless.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.sofiasaless.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.sofiasaless.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplyJobCandidateUseCase {
    
    private final CandidateRepository candidateRepository;

    private final JobRepository jobRepository;

    private final ApplyJobRepository applyJobRepository;

    // id do candidato
    // id da vaga
    public ApplyJobEntity execute (UUID idCandidate, UUID idJob) {
        // validar se o candidato existe
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // validar se a vaga existe
        this.jobRepository.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        // candidato se inscrever na vaga
        var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)        
        .build();
        applyJobRepository.save(applyJob);
        return applyJob;
    }
}
