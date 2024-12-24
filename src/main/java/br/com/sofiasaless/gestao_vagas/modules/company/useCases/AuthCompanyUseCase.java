package br.com.sofiasaless.gestao_vagas.modules.company.useCases;


import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.sofiasaless.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.sofiasaless.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.sofiasaless.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute (AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
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

        // se as senhas derem match, sera gerado o token de autenticação/autorização
        Algorithm algorithm = Algorithm.HMAC256(secretKey); // passar uma secret que ninguem tenha acesso
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        
        var token = JWT.create()
            .withIssuer("upbusiness") // issuer da geração do token
            .withExpiresAt(expiresIn)
            .withSubject(company.getId().toString()) // passando uma informação unica da entidade
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm) // passando o algoritmo de criação do token
        ;

        return AuthCompanyResponseDTO.builder().access_token(token).expires_in(expiresIn.toEpochMilli()).build();

    }
}
