package br.com.sofiasaless.gestao_vagas.modules.candidate.useCases;

import org.springframework.stereotype.Service;

import br.com.sofiasaless.gestao_vagas.exceptions.UserFoundException;
import br.com.sofiasaless.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.sofiasaless.gestao_vagas.modules.candidate.CandidateRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase {

    private final CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).ifPresent((user) -> {
            throw new UserFoundException(); 
        });
        
        return this.candidateRepository.save(candidateEntity);
    }
}
