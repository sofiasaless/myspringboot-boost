package br.com.sofiasaless.gestao_vagas.modules.company.useCases;

import org.springframework.stereotype.Service;

import br.com.sofiasaless.gestao_vagas.exceptions.UserFoundException;
import br.com.sofiasaless.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCompanyUseCase {
    
    private final CompanyRepository companyRepository;
    
    public CompanyEntity execute (CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail()).ifPresent((company) -> {
            throw new UserFoundException();
        });

        return this.companyRepository.save(companyEntity);
    }
}
