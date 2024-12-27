package br.com.sofiasaless.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    
    public static String objectToJSON(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID idCompany, String secret) {
        // se as senhas derem match, sera gerado o token de autenticação/autorização
        Algorithm algorithm = Algorithm.HMAC256(secret); // passar uma secret que ninguem tenha acesso
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        
        var token = JWT.create()
            .withIssuer("upbusiness") // issuer da geração do token
            .withExpiresAt(expiresIn)
            .withSubject(idCompany.toString()) // passando uma informação unica da entidade
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm) // passando o algoritmo de criação do token
        ;

        return token;
    }


}
