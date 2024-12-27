package br.com.sofiasaless.gestao_vagas.modules.company;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sofiasaless.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.sofiasaless.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()) // aplicando o mock do spring security para repassar tokens de teste
        .build();
    }
    
    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {
        var createJobResult = CreateJobDTO.builder()
        .benefits("BENEFICIOS_TESTES")
        .description("DESCRIPTION_TESTES")
        .level("LEVEL_TESTES")
        .build();


        var result = mvc.perform(
            MockMvcRequestBuilders.post("/company/job/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.objectToJSON(createJobResult))
            .header("Authorization", TestUtils.generateToken(UUID.fromString("ab392d50-670f-49dd-9254-60597d9a1d53"), "UPBUSINESS_2024#"))
        )
        .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("================= RESULTADO =================");
        System.out.println(result);
    }


}
