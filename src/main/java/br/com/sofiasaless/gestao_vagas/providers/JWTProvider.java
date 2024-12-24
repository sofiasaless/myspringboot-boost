package br.com.sofiasaless.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service // serviço responsável pela validação dos tokens que vão ser mandados pelos usuários
public class JWTProvider {
    
    @Value("${security.token.secret}")
    private String secretKey;

    // aqui se o token for válido ele vai retornar o subject, que foi definido como o uuid da entidade company
    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");
        
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            var tokenDecoded = JWT.require(algorithm)
            .build()
            .verify(token);
            return tokenDecoded;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
        
    }
}
