# Argus IA Spring

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring AI](https://img.shields.io/badge/Spring%20AI-Generative%20AI-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Ollama](https://img.shields.io/badge/Ollama-Local%20LLM-000000?style=for-the-badge&logo=ollama&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Git](https://img.shields.io/badge/Git-Versionamento-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-Reposit%C3%B3rio-181717?style=for-the-badge&logo=github&logoColor=white)
![Status](https://img.shields.io/badge/Status-Projeto%20Funcional-success?style=for-the-badge)

Assistente de Inteligência Artificial do projeto **Argus**, desenvolvido para a **Global Solution 2026/1 - FIAP**.

O Argus é uma solução voltada ao apoio operacional de brigadistas e coordenadores no combate a incêndios florestais. A proposta conecta dados, automação, APIs e Inteligência Artificial para melhorar a documentação de ocorrências, padronizar relatórios técnicos e apoiar consultas a procedimentos operacionais.

Este módulo utiliza **Spring Boot**, **Spring AI** e **Ollama** para executar um modelo generativo local, sem uso de OpenAI, sem chave de API externa e sem cobrança por requisição.

---

## Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Contexto da Global Solution](#contexto-da-global-solution)
- [Objetivo da Inteligência Artificial](#objetivo-da-inteligência-artificial)
- [Funcionalidades](#funcionalidades)
- [Arquitetura da Solução](#arquitetura-da-solução)
- [Fluxo de Funcionamento](#fluxo-de-funcionamento)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Como Executar o Projeto](#como-executar-o-projeto)
- [Configuração do Ollama](#configuração-do-ollama)
- [Documentação dos Prompts](#documentação-dos-prompts)
- [Base de Conhecimento RAG](#base-de-conhecimento-rag)
- [Limitações da Solução](#limitações-da-solução)
- [Evoluções Futuras](#evoluções-futuras)
- [Critérios Atendidos na Entrega de IA](#critérios-atendidos-na-entrega-de-ia)
- [Integrantes](#integrantes)
- [Vídeo de Demonstração](#vídeo-de-demonstração)
- [Status do Projeto](#status-do-projeto)
- [Licença](#licença)

---

## Sobre o Projeto

O **Argus IA Spring** é o módulo de Inteligência Artificial do ecossistema Argus.

Dentro da solução completa, este serviço é responsável por:

1. Receber dados estruturados de uma ocorrência ambiental.
2. Gerar um relatório técnico em linguagem formal.
3. Receber perguntas sobre procedimentos operacionais.
4. Recuperar contexto em uma base interna de conhecimento.
5. Utilizar um modelo generativo local via Spring AI e Ollama.
6. Retornar respostas orientativas para apoio documental.

A IA atua como apoio à documentação e consulta protocolar, sem substituir o conhecimento técnico, o treinamento profissional ou a decisão operacional dos brigadistas.

---

## Contexto da Global Solution

A Global Solution 2026/1 propõe soluções conectadas à economia espacial, uso de dados orbitais, monitoramento climático, prevenção de desastres e aplicação de tecnologia para problemas reais na Terra.

O Argus se encaixa nesse tema ao propor uma plataforma de apoio ao combate a incêndios florestais com uso de dados, automação e IA. A solução considera o uso de focos de calor detectados por satélites e transforma esses dados em informação útil para operação em campo, documentação e tomada de decisão.

Este repositório representa especificamente o módulo de **Disruptive Architectures: IoT, IoB & Generative IA**, com foco em **IA Generativa** e **RAG**.

---

## Objetivo da Inteligência Artificial

A IA do Argus tem como objetivo apoiar tarefas documentais e consultivas de brigadistas e coordenadores.

A solução não foi criada para ensinar o brigadista a combater incêndios. O público-alvo já possui treinamento técnico. A IA foi posicionada para reduzir esforço burocrático, padronizar relatórios e auxiliar na consulta de procedimentos.

### Objetivos principais

- Gerar relatórios técnicos formais a partir de dados estruturados.
- Apoiar o registro de ocorrências ambientais.
- Reduzir o tempo gasto na redação de documentos.
- Fornecer respostas orientativas baseadas em uma base interna de conhecimento.
- Usar IA generativa local por meio do Ollama.
- Evitar exposição de chaves externas de API.
- Manter o processamento da IA no backend.
- Declarar limites de uso da IA de forma transparente.

---

## Funcionalidades

### 1. Health Check

Endpoint simples para verificar se a API está em execução.

```http
GET /api/v1/ia/health
```

Retorno esperado:

```txt
Assistente IA Argus rodando com sucesso!
```

---

### 2. Gerador Automático de Relatório Técnico

Recebe dados estruturados de uma ocorrência e utiliza IA generativa local para produzir um relatório técnico formal.

Exemplo de dados recebidos:

- Localização da ocorrência.
- Tipo de vegetação.
- Tamanho estimado da área atingida.
- Ações tomadas.
- Recursos utilizados.
- Número de brigadistas.
- Nível de risco.

A saída é um texto técnico estruturado, pronto para revisão, documentação ou exportação futura.

---

### 3. Consulta a Procedimentos com RAG

Recebe uma pergunta do usuário, recupera contexto em uma base interna de procedimentos e utiliza o modelo local para gerar uma resposta orientativa.

Exemplos de perguntas:

- Qual procedimento em caso de ocorrência com vítima?
- Como proceder em área de terra indígena?
- O que fazer com EPIs após o combate?
- Quando considerar evacuação?

A resposta sempre informa a fonte utilizada e reforça que o assistente não substitui a decisão operacional.

---

## Arquitetura da Solução

A arquitetura foi organizada em camadas para separar responsabilidades e facilitar manutenção, testes e evolução futura.

```txt
+--------------------------------------------------------------+
|                       Cliente / Usuário                      |
|                                                              |
|        Swagger UI / App Mobile / Frontend / API Gateway      |
+-------------------------------+------------------------------+
                                |
                                | HTTP REST / JSON
                                v
+--------------------------------------------------------------+
|                        Camada de Entrada                     |
|                                                              |
|  HealthController                                            |
|  IaController                                                |
|                                                              |
|  Responsável por expor os endpoints REST, receber            |
|  requisições, validar payloads e devolver respostas          |
|  padronizadas em JSON.                                       |
+-------------------------------+------------------------------+
                                |
                                v
+--------------------------------------------------------------+
|                       Camada de Aplicação                    |
|                                                              |
|  IaService                                                   |
|                                                              |
|  Responsável por orquestrar os casos de uso da IA:           |
|  geração de relatório técnico e consulta a procedimentos.    |
+-------------------------------+------------------------------+
                                |
                +---------------+----------------+
                |                                |
                v                                v
+-------------------------------+  +-------------------------------+
|     Geração de Relatório      |  |          RagService          |
|                               |  |                               |
|  Monta o prompt técnico com   |  |  Recupera contexto da base    |
|  dados estruturados da        |  |  interna de procedimentos.    |
|  ocorrência.                  |  |                               |
+---------------+---------------+  +---------------+---------------+
                |                                  |
                +---------------+------------------+
                                |
                                v
+--------------------------------------------------------------+
|                    Spring AI ChatClient                      |
|                                                              |
|  Camada responsável por integrar a aplicação Java com o       |
|  modelo generativo local executado via Ollama.                |
+-------------------------------+------------------------------+
                                |
                                v
+--------------------------------------------------------------+
|                         Ollama Local                         |
|                                                              |
|  Modelo: llama3.2:1b                                          |
|  Execução local, sem chave de API externa e sem OpenAI.       |
+-------------------------------+------------------------------+
                                |
                                v
+--------------------------------------------------------------+
|                       Camada de Resposta                     |
|                                                              |
|  GerarRelatorioResponse                                      |
|  ConsultaResponse                                            |
|                                                              |
|  Retorna JSON com relatório, resposta e fonte utilizada.     |
+--------------------------------------------------------------+
```

---

## Visão Técnica em Camadas

### Camada Controller

Responsável por receber chamadas HTTP e expor os endpoints da aplicação.

Arquivos principais:

```txt
HealthController.java
IaController.java
```

### Camada Service

Responsável pelas regras de negócio e orquestração dos casos de uso.

Arquivo principal:

```txt
IaService.java
```

### Camada DTO

Responsável por representar os dados de entrada e saída da API.

Arquivos principais:

```txt
GerarRelatorioRequest.java
GerarRelatorioResponse.java
ConsultaRequest.java
ConsultaResponse.java
```

### Camada RAG

Responsável pela recuperação de contexto a partir de uma base interna de conhecimento.

Arquivo principal:

```txt
RagService.java
```

### Camada de Configuração

Responsável pelas configurações gerais da API e da documentação OpenAPI.

Arquivo principal:

```txt
OpenApiConfig.java
```

---

## Fluxo de Funcionamento

### Fluxo 1 - Geração de Relatório

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
IaService monta o prompt técnico
        |
        v
Spring AI envia o prompt para o Ollama
        |
        v
Ollama gera o relatório com modelo local
        |
        v
API retorna GerarRelatorioResponse
        |
        v
Usuário recebe relatório formal
```

### Fluxo 2 - Consulta RAG

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
RagService recupera contexto da base interna
        |
        v
IaService monta prompt com contexto + pergunta
        |
        v
Spring AI envia o prompt para o Ollama
        |
        v
Ollama gera resposta orientativa
        |
        v
API retorna ConsultaResponse com resposta e fonte
```

---

## Tecnologias Utilizadas

| Ícone | Tecnologia | Uso no Projeto |
|---|---|---|
| ![Java](https://img.shields.io/badge/-Java%2017-ED8B00?style=flat&logo=openjdk&logoColor=white) | Java 17 | Linguagem principal da aplicação |
| ![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white) | Spring Boot | Framework principal da API |
| ![Spring](https://img.shields.io/badge/-Spring%20Web-6DB33F?style=flat&logo=spring&logoColor=white) | Spring Web MVC | Criação dos endpoints REST |
| ![Validation](https://img.shields.io/badge/-Validation-6DB33F?style=flat&logo=spring&logoColor=white) | Spring Validation | Validação dos dados de entrada |
| ![Spring AI](https://img.shields.io/badge/-Spring%20AI-6DB33F?style=flat&logo=spring&logoColor=white) | Spring AI | Integração com modelo generativo local |
| ![Ollama](https://img.shields.io/badge/-Ollama-000000?style=flat&logo=ollama&logoColor=white) | Ollama | Execução local do modelo de linguagem |
| ![Llama](https://img.shields.io/badge/-llama3.2:1b-000000?style=flat&logo=meta&logoColor=white) | llama3.2:1b | Modelo generativo utilizado localmente |
| ![Swagger](https://img.shields.io/badge/-Swagger/OpenAPI-85EA2D?style=flat&logo=swagger&logoColor=black) | Swagger/OpenAPI | Documentação e teste dos endpoints |
| ![Maven](https://img.shields.io/badge/-Maven-C71A36?style=flat&logo=apachemaven&logoColor=white) | Maven | Gerenciamento de dependências e build |
| ![Git](https://img.shields.io/badge/-Git-F05032?style=flat&logo=git&logoColor=white) | Git | Versionamento do código |
| ![GitHub](https://img.shields.io/badge/-GitHub-181717?style=flat&logo=github&logoColor=white) | GitHub | Hospedagem do repositório |

---

## Estrutura do Projeto

```txt
argus-ia-spring
├── examples
│   ├── consultar-procedimento-request-01.json
│   ├── consultar-procedimento-request-02.json
│   ├── consultar-procedimento-request-03.json
│   ├── gerar-relatorio-request-01.json
│   ├── gerar-relatorio-request-02.json
│   └── gerar-relatorio-request-03.json
├── src
│   └── main
│       ├── java
│       │   └── br
│       │       └── com
│       │           └── argus
│       │               └── ia
│       │                   ├── config
│       │                   │   └── OpenApiConfig.java
│       │                   ├── controller
│       │                   │   ├── HealthController.java
│       │                   │   └── IaController.java
│       │                   ├── dto
│       │                   │   ├── ConsultaRequest.java
│       │                   │   ├── ConsultaResponse.java
│       │                   │   ├── GerarRelatorioRequest.java
│       │                   │   └── GerarRelatorioResponse.java
│       │                   ├── exception
│       │                   │   ├── ErroResponse.java
│       │                   │   └── GlobalExceptionHandler.java
│       │                   ├── rag
│       │                   │   └── RagService.java
│       │                   ├── service
│       │                   │   └── IaService.java
│       │                   └── ArgusIaSpringApplication.java
│       └── resources
│           └── application.properties
├── PROMPTS.md
├── README.md
├── ROTEIRO_VIDEO.md
└── pom.xml
```

---

## Endpoints da API

A documentação interativa da API pode ser acessada em:

```txt
http://localhost:8080/swagger-ui/index.html
```

---

### Health Check

```http
GET /api/v1/ia/health
```

#### Resposta

```txt
Assistente IA Argus rodando com sucesso!
```

---

### Gerar Relatório Técnico

```http
POST /api/v1/ia/gerar-relatorio
```

#### Request Body

```json
{
  "localizacao": "Parque Nacional da Chapada dos Veadeiros - GO",
  "tipoVegetacao": "Cerrado com vegetação seca",
  "tamanhoEstimado": "Aproximadamente 12 hectares",
  "acoesTomadas": "Isolamento da área, combate direto com abafadores e acionamento de equipe de apoio",
  "recursosUtilizados": "Abafadores, bomba costal, caminhão-pipa e rádio comunicador",
  "numeroBrigadistas": 8,
  "nivelRisco": "Alto"
}
```

#### Response Body

```json
{
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
  "resposta": "Resposta do Assistente IA Argus...",
  "fonte": "Base interna de procedimentos Argus + Spring AI Ollama"
}
```

---

## Como Executar o Projeto

### Pré-requisitos

Antes de executar, verifique se possui instalado:

- Java 17
- Maven
- Git
- IntelliJ IDEA ou outra IDE Java
- Ollama instalado
- Modelo `llama3.2:1b` baixado no Ollama

---

### Instalar e preparar o Ollama

Verifique se o Ollama está instalado:

```bash
ollama --version
```

Baixe o modelo utilizado pelo projeto:

```bash
ollama pull llama3.2:1b
```

Teste o modelo localmente:

```bash
ollama run llama3.2:1b
```

Para sair do chat do Ollama:

```txt
/bye
```

Verifique se o modelo foi instalado:

```bash
ollama list
```

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

### Configurar `application.properties`

O projeto usa Ollama local. Não é necessário configurar chave da OpenAI.

Arquivo:

```txt
src/main/resources/application.properties
```

Configuração esperada:

```properties
spring.application.name=argus-ia-spring
server.port=8080

spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=llama3.2:1b
spring.ai.ollama.chat.options.temperature=0.2
```

---

### Executar com Maven

```bash
mvn spring-boot:run
```

---

### Executar pelo IntelliJ

1. Abra o projeto no IntelliJ.
2. Aguarde o Maven carregar as dependências.
3. Confirme que o Ollama está instalado e com o modelo `llama3.2:1b`.
4. Abra a classe:

```txt
ArgusIaSpringApplication.java
```

5. Clique em **Run**.
6. Acesse:

```txt
http://localhost:8080/api/v1/ia/health
```

7. Acesse o Swagger:

```txt
http://localhost:8080/swagger-ui/index.html
```

---

## Configuração da IA Local

A aplicação não utiliza OpenAI, ChatGPT API ou qualquer chave externa paga.

A integração com IA generativa é feita por meio de:

```txt
Spring AI + Ollama + llama3.2:1b
```

Essa abordagem permite:

- executar o modelo localmente;
- evitar exposição de API key;
- evitar cobrança por chamadas externas;
- manter a chamada ao modelo dentro do backend;
- demonstrar uso real de IA generativa no projeto.

---

## Documentação dos Prompts

Os prompts utilizados e planejados para a solução estão documentados no arquivo:

```txt
PROMPTS.md
```

Esse arquivo descreve:

- objetivo da IA;
- prompt de geração de relatório;
- prompt de consulta RAG;
- comportamento esperado;
- parâmetros utilizados;
- limitações declaradas.

---

## Base de Conhecimento RAG

A versão atual utiliza uma base interna de conhecimento em memória, implementada no `RagService`.

Temas contemplados:

- ocorrência com vítima;
- entrada ou atuação em terra indígena;
- verificação de EPI;
- evacuação;
- ausência de contexto suficiente.

Essa abordagem demonstra o conceito de RAG de forma objetiva: antes de responder, a aplicação recupera um contexto relevante da base interna e envia esse contexto ao modelo generativo local.

---

## Limitações da Solução

A solução possui limites declarados para evitar uso indevido da IA.

- A IA não substitui treinamento profissional.
- A IA não substitui decisão operacional em campo.
- A IA não ensina técnicas de combate ao fogo.
- A IA não deve inventar protocolos.
- A IA não substitui documentos oficiais completos.
- A IA atua como apoio documental e burocrático.
- A base interna deve ser expandida conforme novos documentos e protocolos forem incorporados.

---

## Evoluções Futuras

Possíveis melhorias para próximas versões:

- Inclusão de documentos oficiais em PDF.
- Implementação de chunking automático.
- Geração de embeddings.
- Uso de vector store.
- Exportação do relatório em PDF.
- Integração com banco Oracle.
- Integração com aplicativo mobile.
- Registro histórico de consultas.
- Autenticação com JWT.
- Testes automatizados para controllers e services.
- Dashboard para acompanhamento das consultas e relatórios gerados.
- Ampliação da base de conhecimento por bioma, órgão e tipo de ocorrência.

---

## Critérios Atendidos na Entrega de IA

| Critério | Como foi atendido |
|---|---|
| Aplicação de IA Generativa | Integração com Spring AI e Ollama para geração de respostas com modelo local |
| Caso de uso real | Apoio documental a brigadistas e coordenadores em ocorrências ambientais |
| API funcional | Endpoints REST com Spring Boot |
| Interface de teste | Swagger/OpenAPI |
| RAG | Recuperação de contexto em base interna de procedimentos |
| Documentação de prompts | Arquivo PROMPTS.md |
| Documentação do projeto | README.md |
| Demonstração | Endpoints testáveis via Swagger |
| Limites da IA | Declarados no README e PROMPTS.md |
| Segurança de chave | Não utiliza API key externa no projeto |

---

## Integrantes

| Nome | RM | Turma |
|---|---|---|
| Maria Eduarda | Adicionar RM | Adicionar turma |
| Integrante 2 | Adicionar RM | Adicionar turma |
| Integrante 3 | Adicionar RM | Adicionar turma |

---

## Vídeo de Demonstração

Link do vídeo no YouTube:

```txt
Adicionar link do vídeo aqui
```

Sugestão para o vídeo:

1. Apresentar o README.
2. Explicar o objetivo da IA.
3. Mostrar a arquitetura.
4. Mostrar que o projeto utiliza Spring AI com Ollama.
5. Abrir o Swagger.
6. Testar o endpoint `/api/v1/ia/health`.
7. Testar o endpoint `/api/v1/ia/gerar-relatorio`.
8. Testar o endpoint `/api/v1/ia/consultar`.
9. Finalizar explicando as limitações e futuras melhorias.

---

## Repositório

```txt
https://github.com/DudaAraujo14/argus-ia-spring
```

---

## Status do Projeto

Projeto funcional.

Funcionalidades implementadas:

- Health check.
- Gerador de relatório técnico com IA generativa local.
- Consulta RAG com base interna de conhecimento.
- Integração com Spring AI e Ollama.
- Swagger para testes.
- Documentação de prompts.
- Tratamento global de erros.
- Exemplos de requisições JSON.
- Roteiro de vídeo.
- Estrutura organizada em camadas.
- README profissional para entrega.

---

## Licença

Projeto acadêmico desenvolvido para fins educacionais no contexto da Global Solution 2026/1 - FIAP.