package br.com.sofiasaless.gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean // indica que o metodo ta sendo redefinido um objeto ja escrito por padrao
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        // primeiro é necessário desabilitar o csrf
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

}
