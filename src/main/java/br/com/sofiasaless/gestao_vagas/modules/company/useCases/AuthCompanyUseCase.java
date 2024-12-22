package br.com.sofiasaless.gestao_vagas.modules.company.useCases;


import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.sofiasaless.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public String execute (AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        // verificando a existência da compania
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username/password incorrect");
        });

        // verificar a senha
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        // se as senhas não derem match
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // se as senhas derem match
        Algorithm algorithm = Algorithm.HMAC256(secretKey); // passar uma secret que ninguem tenha acesso
        var token = JWT.create()
            .withIssuer("upbusiness") // issuer da geração do token
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .withSubject(company.getId().toString()) // passando uma informação unica da entidade
            .sign(algorithm) // passando o algoritmo de criação do token
        ;

        return token;

    }
}
