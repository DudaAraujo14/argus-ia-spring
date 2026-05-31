package br.com.argus.ia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI argusIaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Argus IA API")
                        .version("1.0.0")
                        .description("""
                                API de Inteligência Artificial do projeto Argus.

                                Esta API oferece funcionalidades de apoio documental para brigadistas e coordenadores,
                                incluindo geração de relatórios técnicos de ocorrências e consulta a procedimentos
                                operacionais por meio de uma abordagem RAG.

                                A IA atua como apoio burocrático e consultivo, sem substituir treinamento profissional
                                ou decisão operacional em campo.
                                """)
                        .contact(new Contact()
                                .name("Equipe Argus - FIAP Global Solution 2026/1")
                                .url("https://github.com/DudaAraujo14/argus-ia-spring")));
    }
}