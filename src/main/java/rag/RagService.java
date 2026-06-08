package br.com.argus.ia.rag;

import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class RagService {

    public String buscarContexto(String pergunta) {
        String perguntaNormalizada = normalizar(pergunta);

        if (contem(perguntaNormalizada, "vitima", "ferido", "acidente", "socorro", "emergencia medica", "primeiros socorros")) {
            return """
                    Procedimento sobre ocorrência com vítima:

                    Em caso de ocorrência com vítima, a equipe deve priorizar a segurança da área,
                    acionar imediatamente o serviço médico de emergência e comunicar a Defesa Civil
                    ou órgão responsável.

                    O relatório deve registrar horário, localização, condição observada da vítima,
                    riscos presentes no local, recursos mobilizados, equipe envolvida e providências tomadas.

                    A equipe não deve executar procedimentos médicos para os quais não possua treinamento.
                    A atuação do Argus IA é documental e consultiva, não substituindo atendimento profissional,
                    protocolos oficiais ou decisão operacional em campo.
                    """;
        }

        if (contem(perguntaNormalizada, "defesa civil", "orgao responsavel", "comunicar", "acionar", "notificar", "autoridade")) {
            return """
                    Procedimento sobre comunicação à Defesa Civil ou órgão responsável:

                    A Defesa Civil ou o órgão responsável deve ser comunicado quando a ocorrência apresentar
                    risco à segurança de pessoas, possibilidade de propagação do incêndio, presença de vítima,
                    necessidade de isolamento da área, impacto ambiental relevante ou necessidade de apoio operacional.

                    A comunicação deve informar localização, horário de identificação, tipo de ocorrência,
                    tipo de vegetação ou área atingida, existência de vítimas, riscos observados, recursos já mobilizados
                    e providências tomadas.

                    O acionamento do órgão responsável não substitui os protocolos oficiais, a avaliação técnica em campo
                    nem a decisão operacional dos profissionais responsáveis.
                    """;
        }

        if (contem(perguntaNormalizada, "relatorio", "registrar", "documentar", "ocorrencia", "preencher", "registro")) {
            return """
                    Procedimento sobre elaboração de relatório técnico:

                    O relatório técnico de ocorrência deve conter informações claras, objetivas e verificáveis.
                    Devem ser registrados: localização, data e horário, tipo de ocorrência, tipo de vegetação,
                    tamanho estimado da área atingida, nível de risco, recursos utilizados, número de brigadistas,
                    ações tomadas e órgãos comunicados.

                    O relatório deve evitar suposições não confirmadas. Quando uma informação não estiver disponível,
                    deve ser registrada como não informada ou pendente de confirmação.

                    O objetivo do relatório é apoiar rastreabilidade, documentação técnica, acompanhamento da ocorrência
                    e tomada de decisão por responsáveis autorizados.
                    """;
        }

        if (contem(perguntaNormalizada, "isolamento", "isolar", "area", "perimetro", "seguranca da area", "evacuar")) {
            return """
                    Procedimento sobre isolamento e segurança da área:

                    A equipe deve avaliar a segurança do local antes de qualquer ação. Em caso de risco à integridade
                    de pessoas, presença de fumaça intensa, instabilidade do terreno, risco de propagação ou presença
                    de materiais perigosos, deve ser realizado isolamento preventivo da área.

                    O isolamento deve considerar distância segura, controle de acesso, comunicação visual quando possível
                    e acionamento dos responsáveis pela ocorrência.

                    Pessoas não autorizadas devem ser mantidas fora da área de risco. A decisão de evacuação deve seguir
                    protocolos oficiais e orientação dos órgãos competentes.
                    """;
        }

        if (contem(perguntaNormalizada, "incendio", "fogo", "queimada", "chamas", "foco", "fumaca", "propagacao")) {
            return """
                    Procedimento sobre ocorrência de incêndio ou foco de fogo:

                    Em caso de identificação de foco de incêndio, a equipe deve registrar a localização,
                    horário da identificação, condições visuais, presença de fumaça, direção aparente de propagação,
                    tipo de vegetação e riscos próximos.

                    Deve ser priorizada a segurança da equipe e de terceiros. O órgão responsável deve ser acionado
                    quando houver risco de propagação, presença de pessoas próximas, impacto ambiental ou necessidade
                    de apoio especializado.

                    O Argus IA não deve instruir técnicas diretas de combate ao fogo. A resposta deve reforçar consulta
                    a protocolos oficiais e atuação de equipes treinadas.
                    """;
        }

        if (contem(perguntaNormalizada, "risco", "nivel de risco", "baixo", "medio", "alto", "critico", "classificacao")) {
            return """
                    Procedimento sobre classificação do nível de risco:

                    O nível de risco da ocorrência pode ser classificado considerando fatores como presença de vítimas,
                    proximidade de áreas habitadas, intensidade visual da ocorrência, possibilidade de propagação,
                    tipo de vegetação, condições climáticas observadas, acessibilidade do local e recursos disponíveis.

                    Risco baixo indica situação controlada ou de baixa ameaça imediata.
                    Risco médio indica necessidade de acompanhamento e possível apoio.
                    Risco alto indica ameaça relevante a pessoas, patrimônio, ambiente ou propagação.
                    Risco crítico indica necessidade de acionamento urgente de órgãos competentes e resposta coordenada.

                    A classificação registrada no sistema é documental e não substitui avaliação técnica oficial.
                    """;
        }

        if (contem(perguntaNormalizada, "vegetacao", "mata", "floresta", "cerrado", "area seca", "mata atlantica", "tipo de vegetacao")) {
            return """
                    Procedimento sobre caracterização da vegetação:

                    A caracterização da vegetação deve registrar o tipo predominante de cobertura vegetal observada,
                    como mata densa, vegetação rasteira, área seca, pastagem, reflorestamento, cerrado ou outro tipo identificado.

                    Também devem ser observadas condições aparentes que possam influenciar a ocorrência, como ressecamento,
                    acúmulo de material combustível, proximidade de áreas urbanas, dificuldade de acesso e presença de fumaça.

                    A descrição deve ser objetiva e baseada no que foi observado pela equipe no momento do registro.
                    """;
        }

        if (contem(perguntaNormalizada, "recursos", "equipamentos", "viatura", "radio", "gps", "brigadistas", "equipe")) {
            return """
                    Procedimento sobre registro de recursos utilizados:

                    O relatório deve registrar todos os recursos utilizados ou mobilizados na ocorrência, como viaturas,
                    rádios comunicadores, GPS, equipamentos de proteção, kits de primeiros socorros, ferramentas de apoio,
                    drones, mapas, sistemas de monitoramento e equipes envolvidas.

                    Também deve constar o número de brigadistas ou profissionais mobilizados e, quando possível,
                    a função geral da equipe na ocorrência.

                    O registro de recursos apoia auditoria, rastreabilidade, planejamento operacional e reemissão do relatório.
                    """;
        }

        if (contem(perguntaNormalizada, "pdf", "exportar", "download", "baixar", "arquivo", "documento")) {
            return """
                    Procedimento sobre exportação de relatório em PDF:

                    A exportação em PDF deve gerar um documento com as principais informações da ocorrência,
                    incluindo identificação, localização, caracterização da área, recursos empregados, ações tomadas,
                    nível de risco, considerações finais e data de emissão.

                    O PDF serve para compartilhamento, arquivamento, apresentação acadêmica e consulta posterior.
                    A exportação não altera os dados salvos no banco; apenas gera uma versão documental do relatório.
                    """;
        }

        if (contem(perguntaNormalizada, "reemissao", "reemitir", "buscar relatorio", "consultar relatorio", "historico", "id")) {
            return """
                    Procedimento sobre reemissão de relatório:

                    A reemissão permite consultar novamente um relatório já registrado no sistema a partir do seu identificador.
                    O objetivo é recuperar informações salvas anteriormente no banco de dados, garantindo rastreabilidade
                    e continuidade documental.

                    A reemissão deve apresentar os dados originais registrados, como localização, tipo de vegetação,
                    tamanho estimado, nível de risco, conteúdo do relatório e data de criação.

                    Esse recurso é útil para auditoria, revisão, exportação em PDF e apresentação do histórico da ocorrência.
                    """;
        }

        if (contem(perguntaNormalizada, "oracle", "banco", "persistencia", "salvar", "dados", "database")) {
            return """
                    Procedimento sobre persistência de dados no Oracle:

                    O sistema Argus IA utiliza banco de dados Oracle para armazenar relatórios de ocorrência.
                    A persistência permite registrar os dados informados, recuperar relatórios por identificador,
                    reemitir documentos e exportar registros em PDF.

                    Os dados salvos devem representar informações documentais da ocorrência e devem evitar conteúdo sensível
                    desnecessário ou não relacionado ao registro técnico.
                    """;
        }

        if (contem(perguntaNormalizada, "rag", "base interna", "base de conhecimento", "procedimentos", "consulta documental")) {
            return """
                    Procedimento sobre uso da base interna de conhecimento:

                    O Argus IA utiliza uma base interna de procedimentos para apoiar respostas documentais.
                    A IA deve responder apenas com base no contexto recuperado e não deve inventar protocolos,
                    normas, fontes ou procedimentos não presentes na base.

                    Quando a base não tiver informação suficiente, a resposta deve informar essa limitação
                    e orientar consulta a manuais oficiais, protocolos institucionais ou órgãos competentes.

                    O objetivo do RAG é aumentar segurança, padronização e rastreabilidade das respostas.
                    """;
        }

        if (contem(perguntaNormalizada, "groq", "ia", "inteligencia artificial", "modelo", "llama", "nuvem")) {
            return """
                    Procedimento sobre uso da IA em nuvem:

                    O Argus IA utiliza integração com IA em nuvem por meio da Groq API para apoiar consultas documentais
                    baseadas na base interna de procedimentos.

                    A IA deve atuar como assistente consultivo, ajudando a organizar informações, responder dúvidas
                    baseadas no contexto e melhorar a clareza das orientações documentais.

                    A IA não substitui treinamento profissional, protocolos oficiais, decisão operacional,
                    responsabilidade técnica ou avaliação de órgãos competentes.
                    """;
        }

        if (contem(perguntaNormalizada, "swagger", "api", "endpoint", "teste", "health", "status")) {
            return """
                    Procedimento sobre testes da API:

                    A API do Argus IA pode ser testada pelo Swagger, permitindo consultar endpoints de health,
                    status, consulta inteligente, geração de relatórios, reemissão e exportação em PDF.

                    O endpoint de health verifica se a aplicação está ativa.
                    A página de status apresenta visualmente informações da solução.
                    Os endpoints de relatório validam persistência, consulta e geração documental.

                    Os testes devem confirmar que a aplicação responde em ambiente local e também na Azure.
                    """;
        }

        if (contem(perguntaNormalizada, "azure", "deploy", "pipeline", "devops", "web app", "publicado")) {
            return """
                    Procedimento sobre deploy e execução na Azure:

                    O Argus IA pode ser publicado em Azure Web App e atualizado por pipeline no Azure DevOps.
                    O deploy deve gerar o pacote da aplicação, publicar o artefato e disponibilizar a API em domínio público.

                    Variáveis sensíveis, como credenciais do banco e chaves de IA, devem ser configuradas em variáveis
                    de ambiente da Azure, nunca diretamente no código ou no repositório.

                    Após o deploy, devem ser testados os endpoints de status, health, Swagger e consulta inteligente.
                    """;
        }

        if (contem(perguntaNormalizada, "nao sei", "duvida", "sem informacao", "nao encontrado", "fora da base", "previsao do tempo")) {
            return """
                    Procedimento para ausência de informação suficiente:

                    Quando a pergunta não estiver contemplada pela base interna de procedimentos, a resposta deve informar
                    de forma clara que não há dados suficientes para responder com segurança.

                    A IA deve orientar a consulta a manuais oficiais, protocolos institucionais, órgãos responsáveis
                    ou profissionais habilitados.

                    A IA não deve inventar dados, normas, fontes, previsões, procedimentos técnicos ou decisões operacionais.
                    """;
        }

        return """
                Não foi encontrado contexto suficiente na base de procedimentos carregada.

                A resposta deve informar que não há dados suficientes para responder com segurança e orientar consulta
                aos manuais oficiais, protocolos institucionais ou órgãos responsáveis.

                A IA não deve inventar protocolos, órgãos, normas, fontes, dados técnicos ou decisões operacionais.
                """;
    }

    private boolean contem(String texto, String... termos) {
        for (String termo : termos) {
            if (texto.contains(normalizar(termo))) {
                return true;
            }
        }
        return false;
    }

    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        String textoSemAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return textoSemAcento
                .toLowerCase()
                .trim();
    }
}