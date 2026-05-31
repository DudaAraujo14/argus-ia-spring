# PROMPTS.md - Assistente IA Argus

## Objetivo da IA

O **Assistente IA Argus** apoia brigadistas e coordenadores em duas tarefas principais:

1. **Geração automática de relatórios técnicos de ocorrência**.
2. **Consulta a procedimentos operacionais usando RAG com base interna de conhecimento**.

A IA não substitui treinamento profissional, decisão operacional em campo ou orientação oficial de autoridades competentes.  
O objetivo do assistente é apoiar a documentação, padronizar respostas consultivas e reduzir esforço burocrático em registros de ocorrência.

A solução utiliza **Spring AI integrado ao Ollama**, executando um modelo generativo local (`llama3.2:1b`), sem uso de OpenAI, sem API key externa e sem cobrança por requisição.

---

## Modelo e parâmetros utilizados

### Integração

```txt
Spring Boot + Spring AI + Ollama
```

### Modelo local

```txt
llama3.2:1b
```

### Configuração no `application.properties`

```properties
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=llama3.2:1b
spring.ai.ollama.chat.options.temperature=0.2
```

### Justificativa dos parâmetros

- **Modelo `llama3.2:1b`**: escolhido por ser leve, executável localmente e adequado para demonstração acadêmica funcional.
- **Temperature `0.2`**: reduz a aleatoriedade das respostas, favorecendo linguagem mais objetiva, técnica e consistente.
- **Execução local via Ollama**: evita exposição de chaves externas, custos por chamada e dependência de serviços pagos.

---

## Prompt 1 - Geração de relatório técnico

### Caso de uso

O brigadista informa dados estruturados de uma ocorrência de incêndio florestal.  
A IA deve gerar um relatório formal em linguagem técnica, clara, objetiva e impessoal.

Esse prompt é usado no endpoint:

```http
POST /api/v1/ia/gerar-relatorio
```

### Template de prompt de sistema

```txt
Você é um redator técnico do sistema Argus, especializado em relatórios
de ocorrências ambientais e incêndios florestais.

Sua função é transformar dados estruturados em um relatório formal.

Regras obrigatórias:
- Não ensine o brigadista a combater incêndio.
- Não substitua decisão operacional em campo.
- Não invente dados que não foram informados.
- Use linguagem técnica, objetiva e impessoal.
- Escreva em português do Brasil.
- Organize a resposta em seções.
```

### Template de prompt do usuário

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
  "localizacao": "Parque Nacional da Chapada dos Veadeiros - GO",
  "tipoVegetacao": "Cerrado com vegetação seca",
  "tamanhoEstimado": "Aproximadamente 12 hectares",
  "acoesTomadas": "Isolamento da área, combate direto com abafadores e acionamento de equipe de apoio",
  "recursosUtilizados": "Abafadores, bomba costal, caminhão-pipa e rádio comunicador",
  "numeroBrigadistas": 8,
  "nivelRisco": "Alto"
}
```

### Saída esperada

Um relatório técnico formal, estruturado e pronto para revisão, registro interno ou exportação futura.

A resposta deve:

- usar tom formal;
- separar o texto em seções;
- não inventar dados;
- não orientar combate ao fogo;
- não substituir decisão técnica de campo.

---

## Prompt 2 - Consulta a procedimentos com RAG

### Caso de uso

O brigadista faz uma pergunta sobre procedimentos operacionais, comunicação, registro, segurança ou documentação.  
A aplicação recupera contexto em uma base interna de conhecimento e envia esse contexto ao modelo generativo local.

Esse prompt é usado no endpoint:

```http
POST /api/v1/ia/consultar
```

### Template de prompt de sistema

```txt
Você é um assistente de procedimentos do sistema Argus,
voltado para apoio documental a brigadistas e coordenadores.

Use apenas o contexto fornecido para responder.
Se o contexto não tiver informação suficiente, diga claramente
que não há dados suficientes na base carregada.

Regras obrigatórias:
- Não invente protocolos.
- Não ensine técnicas de combate ao fogo.
- Não substitua treinamento profissional.
- Não substitua decisão operacional em campo.
- Responda em português do Brasil.
- Seja objetivo e seguro.
```

### Template de prompt do usuário

```txt
Contexto recuperado da base de conhecimento:

{contexto}

Pergunta do usuário:

{pergunta}
```

### Entrada esperada

```json
{
  "pergunta": "Qual procedimento em caso de ocorrência com vítima?"
}
```

### Saída esperada

Uma resposta clara, objetiva e com indicação da fonte utilizada.

A resposta deve:

- usar apenas o contexto recuperado;
- informar quando não houver dados suficientes;
- evitar inventar protocolos;
- reforçar que a IA atua como apoio documental;
- não ensinar técnicas de combate ao fogo.

---

## Base interna de conhecimento

A base interna de conhecimento é utilizada pelo `RagService` para recuperar contexto antes da resposta do modelo generativo.

### Temas contemplados

- Ocorrência com vítima.
- Atuação em terra indígena.
- Verificação e higienização de EPI.
- Evacuação de comunidade próxima.
- Resposta para ausência de contexto suficiente.

### Exemplo de contexto recuperado

```txt
Procedimento sobre ocorrência com vítima:
Em caso de ocorrência com vítima, a equipe deve priorizar a segurança da área,
acionar imediatamente o serviço médico de emergência e comunicar a Defesa Civil
ou órgão responsável. O relatório deve registrar horário, localização, condição
observada e providências tomadas.
```

---

## Estratégia de segurança do prompt

Os prompts foram construídos para evitar riscos comuns em aplicações de IA:

- Não posicionar a IA como substituta do brigadista.
- Não orientar tecnicamente o combate ao fogo.
- Não inventar dados, protocolos ou normas.
- Não responder fora da base de contexto quando a pergunta for consultiva.
- Não expor chave externa, pois o modelo roda localmente via Ollama.
- Manter a chamada ao modelo sempre no backend.

---

## Limitações declaradas

- A IA não substitui o julgamento técnico do brigadista.
- A IA não substitui treinamento profissional.
- A IA não substitui protocolos oficiais completos.
- A IA não deve tomar decisões críticas em campo.
- A IA não ensina técnicas de combate ao fogo.
- A IA atua como apoio documental, consultivo e burocrático.
- A base interna deve ser expandida conforme novos documentos oficiais forem incorporados.
- O modelo local pode gerar respostas mais simples que modelos externos maiores, por isso os prompts são restritivos e objetivos.

---

## Evoluções futuras

A solução pode evoluir com:

- Inclusão de documentos oficiais em PDF.
- Chunking automático dos documentos.
- Embeddings.
- Vector store.
- Histórico de perguntas e respostas.
- Exportação de relatórios em PDF.
- Integração com banco Oracle.
- Integração com aplicativo mobile.
- Ampliação da base por bioma, órgão e tipo de ocorrência.
- Testes automatizados para validar qualidade das respostas.

---

## Resumo técnico

| Item | Definição |
|---|---|
| Tipo de IA | IA Generativa com RAG |
| Framework | Spring AI |
| Modelo | llama3.2:1b |
| Execução | Ollama local |
| API externa paga | Não utiliza |
| Chave OpenAI | Não utiliza |
| Principal uso | Relatórios técnicos e consulta documental |
| Segurança | Prompts restritivos e limites declarados |