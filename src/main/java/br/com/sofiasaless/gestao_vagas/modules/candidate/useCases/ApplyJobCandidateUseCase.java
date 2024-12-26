package br.com.sofiasaless.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.sofiasaless.gestao_vagas.exceptions.JobNotFoundException;
import br.com.sofiasaless.gestao_vagas.exceptions.UserNotFoundException;
import br.com.sofiasaless.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplyJobCandidateUseCase {
    
    private final CandidateRepository candidateRepository;

    private final JobRepository jobRepository;

    // id do candidato
    // id da vaga
    public void execute (UUID idCandidate, UUID idJob) {
        // validar se o candidato existe
        var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // validar se a vaga existe
        var job = this.jobRepository.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        // candidato se inscrever na vaga

    }
}
