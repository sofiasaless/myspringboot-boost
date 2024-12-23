package br.com.sofiasaless.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.sofiasaless.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    // metodo responsavel pela filtragem após a autenticação de um usuário
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(null); // setando o contexto como nulo, pois pode vir com algum lixo

        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/company") || request.getRequestURI().startsWith("/job")) {
            // se o header for diferente de null, ele deve vir com um token, esse token pode ou nao ser válido
            if (header != null) {
                var subjectToken = this.jwtProvider.validateToken(header); // o subject desse token carrega o uuid da entidade company
                if (subjectToken.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("company_id", subjectToken);
                
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken, null, Collections.emptyList());
                
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }


        filterChain.doFilter(request, response);
    }

    
}