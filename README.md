# Argus IA Spring

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Swagger](https://img.shields.io/badge/API-Swagger/OpenAPI-85EA2D)
![Status](https://img.shields.io/badge/Status-MVP%20Funcional-success)

Assistente de Inteligência Artificial do projeto **Argus**, desenvolvido para a **Global Solution 2026/1 - FIAP**.

O Argus é uma solução voltada ao apoio operacional de brigadistas e coordenadores no combate a incêndios florestais. A proposta conecta o uso de dados, automação, APIs e Inteligência Artificial para melhorar a documentação de ocorrências, padronizar relatórios técnicos e apoiar consultas a procedimentos operacionais.

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
- [Configuração da Chave de IA](#configuração-da-chave-de-ia)
- [Documentação dos Prompts](#documentação-dos-prompts)
- [Base RAG MVP](#base-rag-mvp)
- [Limitações da Solução](#limitações-da-solução)
- [Evoluções Futuras](#evoluções-futuras)
- [Integrantes](#integrantes)
- [Vídeo de Demonstração](#vídeo-de-demonstração)
- [Status do Projeto](#status-do-projeto)

---

## Sobre o Projeto

O **Argus IA Spring** é o módulo de Inteligência Artificial do ecossistema Argus.

Dentro da solução completa, este serviço é responsável por:

1. Receber dados estruturados de uma ocorrência ambiental.
2. Gerar um relatório técnico em linguagem formal.
3. Receber perguntas sobre procedimentos.
4. Buscar contexto em uma base de conhecimento interna.
5. Retornar respostas orientativas para apoio documental.

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
- Fornecer respostas orientativas baseadas em uma base de conhecimento.
- Evitar respostas sem contexto por meio de uma abordagem inspirada em RAG.
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

Recebe dados estruturados de uma ocorrência e gera um relatório formal.

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

### 3. Consulta a Procedimentos com RAG MVP

Recebe uma pergunta do usuário e busca contexto em uma base de conhecimento interna.

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
+-----------------------------------------------------+
|                   Cliente / Usuário                 |
|                                                     |
|  Swagger UI / App Mobile / Frontend / API Gateway   |
+--------------------------+--------------------------+
                           |
                           | HTTP REST / JSON
                           v
+-----------------------------------------------------+
|                 Camada de Entrada                   |
|                                                     |
|  HealthController                                   |
|  IaController                                       |
|                                                     |
|  Responsável por expor os endpoints REST, receber   |
|  requisições, validar payloads e devolver respostas |
|  padronizadas em JSON.                              |
+--------------------------+--------------------------+
                           |
                           v
+-----------------------------------------------------+
|                Camada de Aplicação                  |
|                                                     |
|  IaService                                          |
|                                                     |
|  Responsável por orquestrar os casos de uso da IA:  |
|  geração de relatório técnico e consulta a          |
|  procedimentos.                                     |
+--------------------------+--------------------------+
                           |
              +------------+-------------+
              |                          |
              v                          v
+----------------------------+   +----------------------------+
|    Gerador de Relatório    |   |        RagService          |
|                            |   |                            |
|  Monta um relatório        |   |  Busca contexto na base    |
|  técnico a partir de       |   |  interna de procedimentos  |
|  dados estruturados.       |   |  do MVP.                   |
+-------------+--------------+   +-------------+--------------+
              |                                |
              v                                v
+-----------------------------------------------------+
|                 Camada de Resposta                  |
|                                                     |
|  GerarRelatorioResponse                             |
|  ConsultaResponse                                   |
|                                                     |
|  Retorna JSON com o relatório, resposta e fonte.    |
+-----------------------------------------------------+
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

Responsável por simular a recuperação de contexto a partir de uma base de conhecimento.

Arquivo principal:

```txt
RagService.java
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
IaService monta o relatório técnico
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
RagService busca contexto na base MVP
        |
        v
IaService monta resposta orientativa
        |
        v
API retorna ConsultaResponse com resposta e fonte
```

---

## Tecnologias Utilizadas

| Tecnologia | Uso no Projeto |
|---|---|
| Java 17 | Linguagem principal da aplicação |
| Spring Boot | Framework principal da API |
| Spring Web | Criação dos endpoints REST |
| Spring Validation | Validação dos dados de entrada |
| Spring AI | Preparação para integração com modelo generativo |
| Swagger/OpenAPI | Documentação e teste dos endpoints |
| Maven | Gerenciamento de dependências e build |
| Git | Versionamento do código |
| GitHub | Hospedagem do repositório |

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
│       │                   ├── config
│       │                   ├── controller
│       │                   │   ├── HealthController.java
│       │                   │   └── IaController.java
│       │                   ├── dto
│       │                   │   ├── ConsultaRequest.java
│       │                   │   ├── ConsultaResponse.java
│       │                   │   ├── GerarRelatorioRequest.java
│       │                   │   └── GerarRelatorioResponse.java
│       │                   ├── rag
│       │                   │   └── RagService.java
│       │                   ├── service
│       │                   │   └── IaService.java
│       │                   └── ArgusIaSpringApplication.java
│       └── resources
│           └── application.properties
├── PROMPTS.md
├── README.md
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
  "fonte": "Base interna de procedimentos Argus MVP"
}
```

---

## Como Executar o Projeto

### Pré-requisitos

Antes de executar, verifique se possui instalado:

- Java 17
- Maven
- IntelliJ IDEA ou outra IDE Java
- Git

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

### Executar com Maven

```bash
mvn spring-boot:run
```

---

### Executar pelo IntelliJ

1. Abra o projeto no IntelliJ.
2. Aguarde o Maven carregar as dependências.
3. Abra a classe:

```txt
ArgusIaSpringApplication.java
```

4. Clique em **Run**.
5. Acesse:

```txt
http://localhost:8080/api/v1/ia/health
```

---

## Configuração da Chave de IA

A aplicação possui configuração para integração com Spring AI e OpenAI.

Durante o desenvolvimento, foi configurado um fallback temporário para permitir que a aplicação suba sem uma chave real:

```properties
spring.ai.openai.api-key=${OPENAI_API_KEY:fake-key}
```

Para uso real com modelo generativo, configure a variável de ambiente:

```bash
OPENAI_API_KEY=sua_chave_aqui
```

> Importante: a chave real nunca deve ser colocada diretamente no código ou versionada no GitHub.

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
- limitações declaradas.

---

## Base RAG MVP

A versão atual utiliza uma base de conhecimento simples em memória, implementada no `RagService`.

Temas contemplados no MVP:

- ocorrência com vítima;
- entrada ou atuação em terra indígena;
- verificação de EPI;
- evacuação;
- ausência de contexto suficiente.

Essa abordagem permite demonstrar o conceito de RAG de forma objetiva, com recuperação de contexto antes da geração da resposta.

---

## Limitações da Solução

A solução possui limites declarados para evitar uso indevido da IA.

- A IA não substitui treinamento profissional.
- A IA não substitui decisão operacional em campo.
- A IA não deve inventar protocolos.
- A IA não substitui documentos oficiais completos.
- A IA atua como apoio documental e burocrático.
- A versão atual é um MVP acadêmico com base RAG em memória.

---

## Evoluções Futuras

Possíveis melhorias para próximas versões:

- Integração real com LLM por meio do Spring AI.
- Uso de documentos oficiais em PDF.
- Implementação de chunking automático.
- Geração de embeddings.
- Uso de vector store.
- Exportação do relatório em PDF.
- Integração com banco Oracle.
- Integração com aplicativo mobile.
- Registro histórico de consultas.
- Autenticação com JWT.
- Testes automatizados para controllers e services.

---

## Critérios Atendidos na Entrega de IA

| Critério | Como foi atendido |
|---|---|
| Aplicação de IA Generativa | Serviço de apoio à geração de relatórios e consulta contextual |
| Caso de uso real | Apoio a brigadistas e coordenadores em ocorrências ambientais |
| API funcional | Endpoints REST com Spring Boot |
| Interface de teste | Swagger/OpenAPI |
| RAG | MVP com recuperação de contexto em memória |
| Documentação de prompts | Arquivo PROMPTS.md |
| Documentação do projeto | README.md |
| Demonstração | Endpoints testáveis via Swagger |
| Limites da IA | Declarados no README e PROMPTS.md |

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
4. Abrir o Swagger.
5. Testar o endpoint `/api/v1/ia/health`.
6. Testar o endpoint `/api/v1/ia/gerar-relatorio`.
7. Testar o endpoint `/api/v1/ia/consultar`.
8. Finalizar explicando as limitações e futuras melhorias.

---

## Repositório

```txt
https://github.com/DudaAraujo14/argus-ia-spring
```

---

## Status do Projeto

MVP funcional.

Funcionalidades já implementadas:

- Health check.
- Gerador de relatório técnico.
- Consulta RAG em memória.
- Swagger para testes.
- Documentação de prompts.
- README profissional para entrega.
- Estrutura organizada em camadas.

---

## Licença

Projeto acadêmico desenvolvido para fins educacionais no contexto da Global Solution 2026/1 - FIAP.