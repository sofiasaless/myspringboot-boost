package br.com.sofiasaless.gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final SecurityFilter securityFilter;

    @Bean // indica que o metodo ta sendo redefinido um objeto ja escrito por padrao
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        // primeiro é necessário desabilitar o csrf
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {

                // para cadastrar candidato e empresa
                auth.requestMatchers("/candidate/").permitAll()
                    .requestMatchers("/company/").permitAll()
                    .requestMatchers("/auth/company").permitAll()
                ;

                // para as demais rotas é necessário estar autenticado
                auth.anyRequest().authenticated();

            })

            // criando filtro para as rotas, verificando se o token do usuario permite ele ter acesso a rotas protegidas
            .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
