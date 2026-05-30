package br.com.argus.ia.rag;

import org.springframework.stereotype.Service;

@Service
public class RagService {

    public String buscarContexto(String pergunta) {
        String perguntaNormalizada = pergunta.toLowerCase();

        if (perguntaNormalizada.contains("vítima") || perguntaNormalizada.contains("vitima")) {
            return """
                    Procedimento sobre ocorrência com vítima:
                    Em caso de ocorrência com vítima, a equipe deve priorizar a segurança da área,
                    acionar imediatamente o serviço médico de emergência e comunicar a Defesa Civil
                    ou órgão responsável. O relatório deve registrar horário, localização, condição
                    observada e providências tomadas.
                    """;
        }

        if (perguntaNormalizada.contains("terra indígena") || perguntaNormalizada.contains("indigena")) {
            return """
                    Procedimento sobre atuação em terra indígena:
                    A entrada em território indígena para combate ou apoio operacional deve respeitar
                    protocolos institucionais, comunicação com órgãos competentes e autorização das
                    lideranças ou autoridades responsáveis, quando aplicável.
                    """;
        }

        if (perguntaNormalizada.contains("epi") || perguntaNormalizada.contains("equipamento")) {
            return """
                    Procedimento sobre EPI:
                    Após o combate, os equipamentos de proteção individual devem ser verificados,
                    higienizados e separados caso apresentem dano, contaminação ou perda de eficiência.
                    O registro deve indicar os EPIs utilizados e qualquer necessidade de substituição.
                    """;
        }

        if (perguntaNormalizada.contains("evacuação") || perguntaNormalizada.contains("evacuacao")) {
            return """
                    Procedimento sobre evacuação:
                    A evacuação deve ser considerada quando houver risco direto à vida, avanço rápido
                    do fogo, presença de fumaça intensa, dificuldade de acesso ou proximidade de
                    comunidades vulneráveis. A decisão deve envolver coordenação com Defesa Civil
                    e autoridades locais.
                    """;
        }

        return """
                Não foi encontrado contexto suficiente na base de procedimentos carregada.
                A resposta deve informar que não há dados suficientes e orientar consulta aos manuais oficiais.
                """;
    }
}