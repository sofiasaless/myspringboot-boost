package br.com.sofiasaless.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.sofiasaless.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.sofiasaless.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.sofiasaless.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileCandidateUseCase {
    
    private final CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });

        return ProfileCandidateResponseDTO.builder()
            .description(candidate.getDescription())
            .email(candidate.getEmail())
            .username(candidate.getUsername())
            .id(candidate.getId())
            .name(candidate.getName())
            .build()
        ;
    }
}
