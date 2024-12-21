package br.com.sofiasaless.gestao_vagas.modules.company.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sofiasaless.gestao_vagas.modules.company.entities.JobEntity;
import br.com.sofiasaless.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobController {
    
    private final CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create (@Valid @RequestBody JobEntity jobEntity) {
        return this.createJobUseCase.execute(jobEntity);
    }

}
