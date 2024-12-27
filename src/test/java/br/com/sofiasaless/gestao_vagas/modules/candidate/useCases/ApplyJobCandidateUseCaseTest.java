package br.com.sofiasaless.gestao_vagas.modules.candidate.useCases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.sofiasaless.gestao_vagas.exceptions.JobNotFoundException;
import br.com.sofiasaless.gestao_vagas.exceptions.UserNotFoundException;
import br.com.sofiasaless.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.sofiasaless.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {
    
    // o injectmock é apenas para a classe que vai testada
    @InjectMocks // para fazer a injeção de dependência com o mock
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    // os mocks são apenas para as classes que estão dentro da classe do injectmocks
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found () {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            // quero garantir que a exceção que for lançada aqui sera do tipo usernotfound
            assertTrue(e instanceof UserNotFoundException);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with not not found")
    public void should_not_be_able_to_apply_job_with_job_not_found() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
        
        // quando fizer a busca pelo id passado, retornar qualquer coisa
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            assertTrue(e instanceof JobNotFoundException);
        }
    }

}
