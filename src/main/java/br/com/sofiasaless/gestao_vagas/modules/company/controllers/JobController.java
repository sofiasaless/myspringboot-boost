package br.com.sofiasaless.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sofiasaless.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.sofiasaless.gestao_vagas.modules.company.entities.JobEntity;
import br.com.sofiasaless.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobController {
    
    private final CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create (@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        // setando o company id que vai vim pelo header do auth, não mais do json
        var companyId = request.getAttribute("company_id");

        var jobEntity = JobEntity.builder()
            .description(createJobDTO.getDescription())
            .companyId(UUID.fromString(companyId.toString()))
            .benefits(createJobDTO.getBenefits())
            .level(createJobDTO.getLevel())
            .build()
        ;

        return this.createJobUseCase.execute(jobEntity);
    }

}
