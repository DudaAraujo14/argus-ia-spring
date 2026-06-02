package br.com.argus.ia.service;

import br.com.argus.ia.model.RelatorioOcorrencia;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] gerarPdfRelatorio(RelatorioOcorrencia relatorio) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font tituloFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font subtituloFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font textoFont = new Font(Font.HELVETICA, 11, Font.NORMAL);

            adicionarTitulo(document, tituloFont);
            adicionarIdentificacao(document, relatorio, subtituloFont, textoFont);
            adicionarDadosOcorrencia(document, relatorio, subtituloFont, textoFont);
            adicionarRelatorioTecnico(document, relatorio, subtituloFont, textoFont);
            adicionarObservacao(document, textoFont);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception exception) {
            throw new IllegalStateException("Erro ao gerar PDF do relatório.", exception);
        }
    }

    private void adicionarTitulo(Document document, Font tituloFont) throws Exception {
        Paragraph titulo = new Paragraph("RELATÓRIO TÉCNICO DE OCORRÊNCIA - ARGUS", tituloFont);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        titulo.setSpacingAfter(18);
        document.add(titulo);
    }

    private void adicionarIdentificacao(
            Document document,
            RelatorioOcorrencia relatorio,
            Font subtituloFont,
            Font textoFont
    ) throws Exception {
        document.add(new Paragraph("1. Identificação do relatório", subtituloFont));
        document.add(new Paragraph("ID do relatório: " + relatorio.getId(), textoFont));
        document.add(new Paragraph("Data de criação: " + formatarData(relatorio), textoFont));
        document.add(new Paragraph(" "));
    }

    private void adicionarDadosOcorrencia(
            Document document,
            RelatorioOcorrencia relatorio,
            Font subtituloFont,
            Font textoFont
    ) throws Exception {
        document.add(new Paragraph("2. Dados da ocorrência", subtituloFont));
        document.add(new Paragraph("Localização: " + relatorio.getLocalizacao(), textoFont));
        document.add(new Paragraph("Tipo de vegetação: " + relatorio.getTipoVegetacao(), textoFont));
        document.add(new Paragraph("Tamanho estimado: " + relatorio.getTamanhoEstimado(), textoFont));
        document.add(new Paragraph("Nível de risco: " + relatorio.getNivelRisco(), textoFont));
        document.add(new Paragraph("Número de brigadistas: " + relatorio.getNumeroBrigadistas(), textoFont));
        document.add(new Paragraph("Recursos utilizados: " + relatorio.getRecursosUtilizados(), textoFont));
        document.add(new Paragraph("Ações tomadas: " + relatorio.getAcoesTomadas(), textoFont));
        document.add(new Paragraph(" "));
    }

    private void adicionarRelatorioTecnico(
            Document document,
            RelatorioOcorrencia relatorio,
            Font subtituloFont,
            Font textoFont
    ) throws Exception {
        document.add(new Paragraph("3. Relatório técnico", subtituloFont));
        document.add(new Paragraph(" "));

        String textoRelatorio = relatorio.getRelatorio();

        if (textoRelatorio == null || textoRelatorio.isBlank()) {
            document.add(new Paragraph("Relatório não disponível.", textoFont));
            return;
        }

        String[] linhas = textoRelatorio.split("\\n");

        for (String linha : linhas) {
            if (linha == null || linha.isBlank()) {
                document.add(new Paragraph(" "));
            } else if (linha.matches("^\\d+\\..*")) {
                Paragraph secao = new Paragraph(linha, subtituloFont);
                secao.setSpacingBefore(8);
                secao.setSpacingAfter(4);
                document.add(secao);
            } else {
                Paragraph paragrafo = new Paragraph(new Phrase(linha, textoFont));
                paragrafo.setSpacingAfter(4);
                document.add(paragrafo);
            }
        }
    }

    private void adicionarObservacao(Document document, Font textoFont) throws Exception {
        document.add(new Paragraph(" "));
        document.add(new Paragraph(
                "Observação: este relatório foi gerado para fins de documentação técnica e apoio operacional. " +
                        "A IA não substitui o treinamento profissional nem a decisão operacional em campo.",
                textoFont
        ));
    }

    private String formatarData(RelatorioOcorrencia relatorio) {
        if (relatorio.getCriadoEm() == null) {
            return "Data não disponível";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return relatorio.getCriadoEm().format(formatter);
    }
}