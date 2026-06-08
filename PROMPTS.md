# PROMPTS.md - Assistente IA Argus

## Objetivo da IA

O **Assistente IA Argus** apoia brigadistas, coordenadores e responsáveis técnicos em tarefas documentais relacionadas a ocorrências ambientais e incêndios florestais.

O sistema possui duas funções principais:

1. **Geração automática de relatórios técnicos de ocorrência**.
2. **Consulta a procedimentos operacionais usando RAG com base interna de conhecimento**.

A IA não substitui treinamento profissional, protocolos oficiais, decisão operacional em campo, avaliação técnica ou atuação de órgãos competentes.

O objetivo do assistente é apoiar a documentação, padronizar respostas consultivas, reduzir esforço burocrático em registros de ocorrência e auxiliar na recuperação de orientações presentes na base interna do sistema.

A solução utiliza **Spring Boot**, integração com **Groq API** em nuvem, modelo Llama via API, base RAG interna, persistência em **Oracle**, documentação via **Swagger/OpenAPI**, exportação em **PDF**, deploy em **Azure Web App** e pipeline no **Azure DevOps**.

---

## Modelo e parâmetros utilizados

### Integração

```txt
Spring Boot + Groq API + RAG interno
```

### Modelo em nuvem

```txt
llama-3.1-8b-instant
```

### Provedor de IA

```txt
Groq API
```

### Configuração no `application.properties`

```properties
# ===============================
# GROQ API / IA EM NUVEM
# ===============================
groq.api.key=${GROQ_API_KEY}
groq.api.url=https://api.groq.com/openai/v1/chat/completions
groq.api.model=llama-3.1-8b-instant
```

### Justificativa dos parâmetros

* **Groq API**: escolhida por permitir uso de IA em nuvem sem depender de modelo local.
* **Modelo Llama via Groq**: utilizado para gerar respostas rápidas e compatíveis com o contexto recuperado pela base RAG.
* **Temperature `0.1`**: reduz a aleatoriedade das respostas, favorecendo linguagem técnica, objetiva e consistente.
* **Max tokens `350`**: limita o tamanho da resposta para manter objetividade e evitar respostas excessivamente longas.
* **Variável `GROQ_API_KEY`**: configurada fora do código, por variável de ambiente local e na Azure, evitando exposição de chave sensível no repositório.

---

## Prompt 1 - Geração de relatório técnico

### Caso de uso

O usuário informa dados estruturados de uma ocorrência de incêndio florestal.
A aplicação gera um relatório técnico formal com base nos dados recebidos.

Esse recurso é utilizado no endpoint de geração de relatório:

```http
POST /api/v1/ia/gerar-relatorio
```

O relatório gerado também pode ser salvo no banco Oracle, reemitido posteriormente e exportado em PDF.

### Objetivo do prompt

Transformar dados estruturados da ocorrência em um relatório formal, claro, objetivo e impessoal.

### Template conceitual do prompt de sistema

```txt
Você é um redator técnico do sistema Argus, especializado em relatórios
de ocorrências ambientais e incêndios florestais.

Sua função é transformar dados estruturados em um relatório formal.

Regras obrigatórias:
- Não ensine técnicas de combate ao fogo.
- Não substitua decisão operacional em campo.
- Não invente dados que não foram informados.
- Use linguagem técnica, objetiva e impessoal.
- Escreva em português do Brasil.
- Organize a resposta em seções.
- Registre apenas informações recebidas ou derivadas diretamente dos dados informados.
```

### Template conceitual do prompt do usuário

```txt
Gere um relatório técnico formal de ocorrência de incêndio florestal com base nos dados abaixo.

Dados estruturados da ocorrência:

- Localização: {localizacao}
- Tipo de vegetação: {tipoVegetacao}
- Tamanho estimado da área atingida: {tamanhoEstimado}
- Ações tomadas: {acoesTomadas}
- Recursos utilizados: {recursosUtilizados}
- Número de brigadistas envolvidos: {numeroBrigadistas}
- Nível de risco: {nivelRisco}

Estruture o relatório com:
1. Identificação da ocorrência
2. Caracterização da área
3. Recursos empregados
4. Ações registradas
5. Avaliação do nível de risco
6. Considerações finais

Não invente informações além das fornecidas.
```

### Entrada esperada

```json
{
  "localizacao": "Parque Estadual da Serra Verde - Setor Norte",
  "tipoVegetacao": "Mata Atlântica com vegetação densa e seca",
  "tamanhoEstimado": "Aproximadamente 2 hectares",
  "acoesTomadas": "Isolamento preventivo da área, acionamento da Defesa Civil e registro fotográfico da ocorrência",
  "recursosUtilizados": "Viatura de apoio, rádio comunicador, GPS e kit de primeiros socorros",
  "numeroBrigadistas": 4,
  "nivelRisco": "Alto"
}
```

### Saída esperada

Um relatório técnico formal, estruturado e pronto para registro, consulta, reemissão ou exportação em PDF.

A resposta deve:

* usar tom formal;
* separar o texto em seções;
* utilizar os dados estruturados recebidos;
* não inventar data, órgão, fonte ou informação não enviada;
* não orientar técnicas de combate ao fogo;
* não substituir decisão técnica de campo.

---

## Prompt 2 - Consulta a procedimentos com RAG

### Caso de uso

O usuário faz uma pergunta sobre procedimentos, comunicação, registro, segurança, documentação, risco, recursos, PDF, reemissão, API, Azure ou funcionamento do sistema.

A aplicação recupera um contexto na base interna implementada no `RagService` e envia esse contexto para a IA em nuvem via `GroqService`.

Esse prompt é utilizado no endpoint:

```http
POST /api/v1/ia/consultar
```

### Template de prompt de sistema

```txt
Você é um assistente de procedimentos do sistema Argus,
voltado para apoio documental a brigadistas e coordenadores.

Use apenas o contexto fornecido para responder.

Regras obrigatórias:
- Não invente protocolos.
- Não invente órgãos, normas, fontes ou referências.
- Não ensine técnicas de combate ao fogo.
- Não substitua treinamento profissional.
- Não substitua decisão operacional em campo.
- Responda em português do Brasil.
- Seja objetivo, técnico e seguro.
- Não use markdown.
- Não use asteriscos.
```

### Template de prompt do usuário

```txt
Contexto recuperado da base de conhecimento:

{contexto}

Pergunta do usuário:

{pergunta}

Responda apenas com base no contexto recuperado.
Se o contexto indicar ausência de informação suficiente, informe isso claramente.
```

### Entrada esperada

```json
{
  "pergunta": "Qual procedimento em caso de ocorrência com vítima?"
}
```

### Saída esperada

```json
{
  "resposta": "Em caso de ocorrência com vítima, a equipe deve priorizar a segurança da área, acionar imediatamente o serviço médico de emergência e comunicar a Defesa Civil ou órgão responsável. O relatório deve registrar horário, localização, condição observada e providências tomadas.",
  "fonte": "Base interna de procedimentos Argus + Groq API"
}
```

A resposta deve:

* usar apenas o contexto recuperado;
* informar quando não houver dados suficientes;
* evitar inventar protocolos, órgãos, normas ou fontes;
* reforçar que a IA atua como apoio documental;
* não ensinar técnicas de combate ao fogo;
* manter tom técnico, seguro e objetivo.

---

## Base interna de conhecimento

A base interna de conhecimento é utilizada pelo `RagService` para recuperar contexto antes da resposta da IA.

A implementação atual utiliza uma base RAG em memória, com múltiplos blocos de contexto para ampliar a capacidade de resposta do assistente.

### Temas contemplados

* Ocorrência com vítima.
* Comunicação à Defesa Civil ou órgão responsável.
* Elaboração de relatório técnico.
* Isolamento e segurança da área.
* Ocorrência de incêndio ou foco de fogo.
* Classificação do nível de risco.
* Caracterização da vegetação.
* Registro de recursos utilizados.
* Exportação de relatório em PDF.
* Reemissão de relatório.
* Persistência de dados no Oracle.
* Uso da base interna de conhecimento.
* Uso da IA em nuvem via Groq API.
* Testes da API pelo Swagger.
* Deploy na Azure.
* Pipeline no Azure DevOps.
* Ausência de informação suficiente.

### Exemplo de contexto recuperado - ocorrência com vítima

```txt
Procedimento sobre ocorrência com vítima:

Em caso de ocorrência com vítima, a equipe deve priorizar a segurança da área,
acionar imediatamente o serviço médico de emergência e comunicar a Defesa Civil
ou órgão responsável.

O relatório deve registrar horário, localização, condição observada da vítima,
riscos presentes no local, recursos mobilizados, equipe envolvida e providências tomadas.

A equipe não deve executar procedimentos médicos para os quais não possua treinamento.
A atuação do Argus IA é documental e consultiva, não substituindo atendimento profissional,
protocolos oficiais ou decisão operacional em campo.
```

### Exemplo de contexto recuperado - comunicação à Defesa Civil

```txt
Procedimento sobre comunicação à Defesa Civil ou órgão responsável:

A Defesa Civil ou o órgão responsável deve ser comunicado quando a ocorrência apresentar
risco à segurança de pessoas, possibilidade de propagação do incêndio, presença de vítima,
necessidade de isolamento da área, impacto ambiental relevante ou necessidade de apoio operacional.

A comunicação deve informar localização, horário de identificação, tipo de ocorrência,
tipo de vegetação ou área atingida, existência de vítimas, riscos observados, recursos já mobilizados
e providências tomadas.

O acionamento do órgão responsável não substitui os protocolos oficiais, a avaliação técnica em campo
nem a decisão operacional dos profissionais responsáveis.
```

### Exemplo de contexto recuperado - ausência de informação

```txt
Não foi encontrado contexto suficiente na base de procedimentos carregada.

A resposta deve informar que não há dados suficientes para responder com segurança e orientar consulta
aos manuais oficiais, protocolos institucionais ou órgãos responsáveis.

A IA não deve inventar protocolos, órgãos, normas, fontes, dados técnicos ou decisões operacionais.
```

---

## Estratégia de segurança do prompt

Os prompts foram construídos para evitar riscos comuns em aplicações de IA aplicadas a contexto operacional.

As principais regras de segurança são:

* Não posicionar a IA como substituta do brigadista.
* Não substituir decisão operacional em campo.
* Não substituir treinamento profissional.
* Não ensinar técnicas diretas de combate ao fogo.
* Não inventar dados, protocolos, órgãos, normas, fontes ou referências.
* Não responder fora do contexto quando a pergunta exigir base técnica.
* Informar quando não houver dados suficientes na base.
* Manter as chaves externas protegidas em variáveis de ambiente.
* Manter a chamada ao modelo sempre no backend.
* Registrar falhas da IA em logs para diagnóstico.
* Usar fallback controlado quando a IA externa estiver indisponível.

---

## Fallback em caso de falha da IA

Caso a Groq API esteja indisponível, a chave esteja incorreta ou ocorra erro externo, o sistema retorna uma resposta de fallback.

O fallback usa o contexto recuperado pelo `RagService` e informa que a consulta ao serviço de IA não pôde ser concluída no momento.

### Exemplo de fallback

```txt
Resposta do Assistente IA Argus:

A consulta ao serviço de IA não pôde ser concluída no momento.
Com base na base de procedimentos carregada, segue a orientação disponível:

{contexto}

Observação: este assistente apoia a consulta documental e não substitui a decisão operacional do brigadista ou coordenador responsável.
```

### Fonte esperada no fallback

```txt
Fallback por indisponibilidade do serviço de IA
```

---

## Limitações declaradas

* A IA não substitui o julgamento técnico do brigadista.
* A IA não substitui treinamento profissional.
* A IA não substitui protocolos oficiais completos.
* A IA não deve tomar decisões críticas em campo.
* A IA não ensina técnicas diretas de combate ao fogo.
* A IA responde apenas com base no contexto recuperado.
* A IA pode informar ausência de dados quando a base não possuir informação suficiente.
* A API externa pode sofrer indisponibilidade, limite de uso ou falha de autenticação.
* A chave da Groq deve ser mantida fora do código e configurada via variável de ambiente.

---

## Fluxo 1 - Geração de Relatório

```txt
Usuário envia dados da ocorrência
        |
        v
POST /api/v1/ia/gerar-relatorio
        |
        v
IaController valida o JSON
        |
        v
IaService monta o relatório técnico estruturado
        |
        v
RelatorioOcorrenciaRepository salva o registro no Oracle
        |
        v
API retorna GerarRelatorioResponse com ID e relatório
        |
        v
Usuário recebe relatório formal
```

---

## Fluxo 2 - Consulta RAG com Groq API

```txt
Usuário envia uma pergunta
        |
        v
POST /api/v1/ia/consultar
        |
        v
IaController valida o JSON
        |
        v
IaService chama RagService
        |
        v
RagService busca contexto na base interna
        |
        v
IaService envia contexto e pergunta ao GroqService
        |
        v
GroqService chama a Groq API
        |
        v
Groq API retorna resposta gerada pelo modelo
        |
        v
IaService limpa e organiza a resposta
        |
        v
API retorna ConsultaResponse com resposta e fonte
```

---

## Fluxo 3 - Reemissão de Relatório

```txt
Usuário informa o ID do relatório
        |
        v
Endpoint de reemissão recebe o ID
        |
        v
IaService busca o relatório no Oracle
        |
        v
RelatorioOcorrenciaRepository recupera o registro
        |
        v
API retorna RelatorioReemitidoResponse
        |
        v
Usuário visualiza o relatório salvo anteriormente
```

---

## Fluxo 4 - Exportação em PDF

```txt
Usuário informa o ID do relatório
        |
        v
Endpoint de exportação PDF recebe o ID
        |
        v
IaService busca o relatório no Oracle
        |
        v
PdfService gera o documento com OpenPDF
        |
        v
API retorna arquivo PDF
        |
        v
Usuário baixa ou abre o relatório em PDF
```

---

## Fluxo 5 - Deploy na Azure

```txt
Código é enviado para o GitHub
        |
        v
Azure DevOps Pipeline executa o build Maven
        |
        v
Pipeline publica o artefato da aplicação
        |
        v
Azure Web App recebe o novo deploy
        |
        v
Variáveis de ambiente são lidas pela aplicação
        |
        v
API fica disponível publicamente
```

---

## Tecnologias Utilizadas

| Tecnologia            | Uso no Projeto                                 |
| --------------------- | ---------------------------------------------- |
| Java 17               | Linguagem principal da aplicação               |
| Spring Boot           | Framework principal da API                     |
| Spring Web MVC        | Criação dos endpoints REST                     |
| Spring Validation     | Validação dos dados de entrada                 |
| Spring Data JPA       | Persistência dos relatórios                    |
| Oracle FIAP           | Banco de dados relacional                      |
| Groq API              | Integração com IA generativa em nuvem          |
| Llama via Groq        | Modelo utilizado para consulta com IA          |
| RAG interno           | Recuperação de contexto para respostas seguras |
| OpenPDF               | Geração e exportação de relatórios em PDF      |
| Swagger/OpenAPI       | Documentação e teste dos endpoints             |
| Maven                 | Gerenciamento de dependências e build          |
| Git                   | Versionamento do código                        |
| GitHub                | Hospedagem do repositório                      |
| Azure Web App         | Hospedagem da aplicação                        |
| Azure DevOps Pipeline | Build e deploy automatizados                   |

---

## Estrutura do Projeto

```txt
argus-ia-spring
├── src
│   └── main
│       ├── java
│       │   └── br
│       │       └── com
│       │           └── argus
│       │               └── ia
│       │                   ├── controller
│       │                   │   ├── HealthController.java
│       │                   │   ├── IaController.java
│       │                   │   └── StatusController.java
│       │                   ├── dto
│       │                   │   ├── ConsultaRequest.java
│       │                   │   ├── ConsultaResponse.java
│       │                   │   ├── GerarRelatorioRequest.java
│       │                   │   ├── GerarRelatorioResponse.java
│       │                   │   ├── HealthResponse.java
│       │                   │   └── RelatorioReemitidoResponse.java
│       │                   ├── exception
│       │                   │   ├── ErroResponse.java
│       │                   │   └── GlobalExceptionHandler.java
│       │                   ├── model
│       │                   │   └── RelatorioOcorrencia.java
│       │                   ├── rag
│       │                   │   └── RagService.java
│       │                   ├── repository
│       │                   │   └── RelatorioOcorrenciaRepository.java
│       │                   ├── service
│       │                   │   ├── GroqService.java
│       │                   │   ├── IaService.java
│       │                   │   └── PdfService.java
│       │                   └── ArgusIaSpringApplication.java
│       └── resources
│           └── application.properties
├── AVALIACAO_IA.md
├── HELP.md
├── PROMPTS.md
├── README.md
├── ROTEIRO_VIDEO.md
├── azure-pipelines.yml
├── mvnw
├── mvnw.cmd
└── pom.xml
```

---

## Endpoints da API

A documentação interativa da API pode ser acessada em:

```txt
http://localhost:8080/swagger-ui/index.html
```

Em ambiente publicado:

```txt
https://argus-ia-spring-eddyerg5dkdqbhba.brazilsouth-01.azurewebsites.net/swagger-ui/index.html
```

---

### Health Check

```http
GET /api/v1/ia/health
```

#### Resposta esperada

```json
{
  "status": "ONLINE",
  "mensagem": "Assistente IA Argus rodando com sucesso!"
}
```

---

### Página de Status

```http
GET /status
```

#### Resultado esperado

Página HTML visual exibindo:

```txt
Argus IA
Backend: Spring Boot
IA em Nuvem: Groq API
Banco de Dados: Oracle
Módulo: Groq API + Oracle + RAG
Recursos: Relatório, RAG, Reemissão e PDF
```

---

### Gerar Relatório Técnico

```http
POST /api/v1/ia/gerar-relatorio
```

#### Request Body

```json
{
  "localizacao": "Parque Estadual da Serra Verde - Setor Norte",
  "tipoVegetacao": "Mata Atlântica com vegetação densa e seca",
  "tamanhoEstimado": "Aproximadamente 2 hectares",
  "acoesTomadas": "Isolamento preventivo da área, acionamento da Defesa Civil e registro fotográfico da ocorrência",
  "recursosUtilizados": "Viatura de apoio, rádio comunicador, GPS e kit de primeiros socorros",
  "numeroBrigadistas": 4,
  "nivelRisco": "Alto"
}
```

#### Response Body

```json
{
  "id": 1,
  "relatorio": "RELATÓRIO TÉCNICO DE OCORRÊNCIA - ARGUS..."
}
```

---

### Consultar Procedimento

```http
POST /api/v1/ia/consultar
```

#### Request Body

```json
{
  "pergunta": "Qual procedimento em caso de ocorrência com vítima?"
}
```

#### Response Body

```json
{
  "resposta": "Em caso de ocorrência com vítima, a equipe deve priorizar a segurança da área, acionar imediatamente o serviço médico de emergência e comunicar a Defesa Civil ou órgão responsável. O relatório deve registrar horário, localização, condição observada e providências tomadas.",
  "fonte": "Base interna de procedimentos Argus + Groq API"
}
```

---

### Reemitir Relatório

```http
GET /api/v1/ia/relatorios/{id}
```

#### Resultado esperado

Retorna os dados de um relatório salvo anteriormente no Oracle.

---

### Exportar Relatório em PDF

```http
GET /api/v1/ia/relatorios/{id}/pdf
```

#### Resultado esperado

Retorna o relatório em formato PDF para download ou visualização.

---

## Como Executar o Projeto

### Pré-requisitos

Antes de executar, verifique se possui instalado:

* Java 17
* Maven ou Maven Wrapper
* IntelliJ IDEA ou outra IDE Java
* Git
* Acesso ao banco Oracle FIAP
* Chave da Groq API

---

### Clonar o repositório

```bash
git clone https://github.com/DudaAraujo14/argus-ia-spring.git
```

Entrar na pasta:

```bash
cd argus-ia-spring
```

---

### Configurar variáveis de ambiente locais

No IntelliJ, acesse:

```txt
Run > Edit Configurations > Environment variables
```

Configure:

```txt
ORACLE_DB_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL;ORACLE_DB_USER=rm560944;ORACLE_DB_PASSWORD=sua_senha;GROQ_API_KEY=sua_chave_groq
```

---

### Executar com Maven Wrapper

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Para gerar o pacote:

```powershell
.\mvnw.cmd clean package -DskipTests
```

---

### Executar pelo IntelliJ

1. Abra o projeto no IntelliJ.
2. Aguarde o Maven carregar as dependências.
3. Configure as variáveis de ambiente.
4. Abra a classe:

```txt
ArgusIaSpringApplication.java
```

5. Clique em **Run**.
6. Acesse:

```txt
http://localhost:8080/swagger-ui/index.html
```

---

## Configuração da Chave de IA

A aplicação utiliza **Groq API** para integração com IA generativa em nuvem.

A chave deve ser configurada por variável de ambiente:

```txt
GROQ_API_KEY=sua_chave_groq
```

No `application.properties`, a configuração deve permanecer assim:

```properties
groq.api.key=${GROQ_API_KEY}
groq.api.url=https://api.groq.com/openai/v1/chat/completions
groq.api.model=llama-3.1-8b-instant
```

> Importante: a chave real nunca deve ser colocada diretamente no código, no `application.properties`, no README ou em qualquer arquivo versionado no GitHub.

---

## Configuração na Azure

No Azure Web App, configure as variáveis em:

```txt
Settings > Environment variables
```

Variáveis necessárias:

```txt
ORACLE_DB_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
ORACLE_DB_USER=rm560944
ORACLE_DB_PASSWORD=sua_senha
GROQ_API_KEY=sua_chave_groq
```

Depois de salvar:

```txt
Apply > Save > Restart
```

---

## Documentação dos Prompts

Os prompts e comportamentos esperados da solução estão documentados neste arquivo.

Este documento descreve:

* objetivo da IA;
* integração com Groq API;
* prompt de geração de relatório;
* prompt de consulta RAG;
* comportamento esperado;
* base interna de conhecimento;
* limitações declaradas;
* estratégia de segurança;
* fallback em caso de falha externa;
* endpoints e fluxos principais.

---

## Base RAG atual

A versão atual utiliza uma base de conhecimento em memória, implementada no `RagService`.

Temas contemplados:

* ocorrência com vítima;
* comunicação à Defesa Civil ou órgão responsável;
* elaboração de relatório técnico;
* isolamento e segurança da área;
* ocorrência de incêndio ou foco de fogo;
* classificação do nível de risco;
* caracterização da vegetação;
* registro de recursos utilizados;
* exportação de relatório em PDF;
* reemissão de relatório;
* persistência de dados no Oracle;
* uso da base RAG;
* uso da IA em nuvem via Groq API;
* testes da API;
* deploy na Azure;
* ausência de informação suficiente.

---

## Considerações finais

O Argus IA Spring utiliza uma arquitetura simples e funcional para demonstrar o uso de IA generativa em um contexto documental e consultivo.

A solução combina:

```txt
Spring Boot + Groq API + RAG interno + Oracle + PDF + Swagger + Azure
```

O sistema foi projetado para ser testável, documentado e seguro, mantendo a IA como ferramenta de apoio e não como substituta de profissionais, protocolos oficiais ou decisões operacionais.
