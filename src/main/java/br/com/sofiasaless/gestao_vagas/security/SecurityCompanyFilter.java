package br.com.sofiasaless.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class SecurityCompanyFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    // metodo responsavel pela filtragem após a autenticação de um usuário
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // SecurityContextHolder.getContext().setAuthentication(null); // setando o contexto como nulo, pois pode vir com algum lixo

        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/company") || request.getRequestURI().startsWith("/job")) {
            // se o header for diferente de null, ele deve vir com um token, esse token pode ou nao ser válido
            if (header != null) {
                var token = this.jwtProvider.validateToken(header); // o subject desse token carrega o uuid da entidade company
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                // mapenado roles
                var roles = token.getClaim("roles").asList(Object.class);
                var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.toString().toUpperCase())).toList();

                request.setAttribute("company_id", token.getSubject());
                
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);
                
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }


        filterChain.doFilter(request, response);
    }

    
}