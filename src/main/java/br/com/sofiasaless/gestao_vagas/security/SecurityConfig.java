package br.com.sofiasaless.gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity // para habilitar as anotações de PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final SecurityCompanyFilter securityCompanyFilter;

    private final SecurityCandidateFilter securityCandidateFilter;

    private final static String[] SWAGGER_LIST = {
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
    };

    @Bean // indica que o metodo ta sendo redefinido um objeto ja escrito por padrao
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        // primeiro é necessário desabilitar o csrf
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {

                // rotas e suas permissões
                auth.requestMatchers("/candidate/").permitAll()
                    .requestMatchers("/company/").permitAll()
                    .requestMatchers("/company/auth").permitAll()
                    .requestMatchers("/candidate/auth").permitAll()
                    .requestMatchers(SWAGGER_LIST).permitAll()
                ;
                // as rotas que não foram citadas acima estão todas protegidas, ou seja, para acessá-las é necessário estar autenticado e ter autorização

                // para as demais rotas é necessário estar autenticado
                auth.anyRequest().authenticated();

            })

            // criando filtro para as rotas, verificando se o token do usuario permite ele ter acesso a rotas protegidas
            .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
            .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
