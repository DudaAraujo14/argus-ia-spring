package br.com.argus.ia.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Hidden
@RestController
public class StatusController {

    @GetMapping(value = "/status", produces = MediaType.TEXT_HTML_VALUE)
    public String statusPage() {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        String html = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Status - Argus IA</title>

                    <style>
                        * {
                            box-sizing: border-box;
                            margin: 0;
                            padding: 0;
                            font-family: Arial, Helvetica, sans-serif;
                        }

                        body {
                            min-height: 100vh;
                            background:
                                radial-gradient(circle at top left, rgba(46, 204, 113, 0.28), transparent 34%),
                                radial-gradient(circle at bottom right, rgba(22, 160, 133, 0.24), transparent 36%),
                                linear-gradient(135deg, #071f1b, #0f3d34, #071f1b);
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            color: #ffffff;
                            padding: 28px;
                        }

                        .container {
                            width: 100%;
                            max-width: 900px;
                            background: rgba(255, 255, 255, 0.08);
                            border: 1px solid rgba(255, 255, 255, 0.18);
                            border-radius: 28px;
                            padding: 42px;
                            box-shadow: 0 28px 90px rgba(0, 0, 0, 0.42);
                            backdrop-filter: blur(12px);
                        }

                        .topbar {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            gap: 16px;
                            margin-bottom: 34px;
                        }

                        .brand {
                            display: flex;
                            align-items: center;
                            gap: 18px;
                        }

                        .logo {
                            width: 72px;
                            height: 72px;
                            border-radius: 22px;
                            background: linear-gradient(135deg, #2ecc71, #7cffb2);
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            font-size: 36px;
                            box-shadow: 0 16px 34px rgba(46, 204, 113, 0.32);
                        }

                        h1 {
                            font-size: 38px;
                            line-height: 1;
                            margin-bottom: 8px;
                        }

                        .subtitle {
                            color: #cde7df;
                            font-size: 15px;
                            line-height: 1.5;
                        }

                        .status-badge {
                            background: rgba(46, 204, 113, 0.16);
                            color: #7cffb2;
                            border: 1px solid rgba(124, 255, 178, 0.55);
                            padding: 10px 16px;
                            border-radius: 999px;
                            font-weight: 800;
                            letter-spacing: 0.8px;
                            white-space: nowrap;
                        }

                        .grid {
                            display: grid;
                            grid-template-columns: repeat(3, 1fr);
                            gap: 16px;
                            margin-bottom: 24px;
                        }

                        .metric {
                            background: rgba(0, 0, 0, 0.22);
                            border: 1px solid rgba(255, 255, 255, 0.12);
                            border-radius: 18px;
                            padding: 20px;
                        }

                        .metric-label {
                            color: #b9d8d0;
                            font-size: 13px;
                            margin-bottom: 8px;
                        }

                        .metric-value {
                            font-size: 17px;
                            font-weight: 800;
                        }

                        .panel {
                            background: rgba(0, 0, 0, 0.20);
                            border: 1px solid rgba(255, 255, 255, 0.14);
                            border-radius: 22px;
                            padding: 24px;
                        }

                        .row {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            gap: 16px;
                            padding: 15px 0;
                            border-bottom: 1px solid rgba(255, 255, 255, 0.10);
                        }

                        .row:last-child {
                            border-bottom: none;
                        }

                        .label {
                            color: #b9d8d0;
                            font-size: 14px;
                        }

                        .value {
                            font-weight: 700;
                            text-align: right;
                        }

                        .links {
                            display: flex;
                            gap: 12px;
                            flex-wrap: wrap;
                            margin-top: 28px;
                        }

                        a {
                            text-decoration: none;
                            color: #06201a;
                            background: #7cffb2;
                            padding: 13px 18px;
                            border-radius: 14px;
                            font-weight: 800;
                            transition: 0.2s ease;
                        }

                        a:hover {
                            transform: translateY(-2px);
                            background: #ffffff;
                        }

                        .footer {
                            margin-top: 28px;
                            color: #b9d8d0;
                            font-size: 13px;
                            line-height: 1.6;
                        }

                        @media (max-width: 760px) {
                            .topbar {
                                flex-direction: column;
                                align-items: flex-start;
                            }

                            .grid {
                                grid-template-columns: 1fr;
                            }

                            .container {
                                padding: 28px;
                            }

                            h1 {
                                font-size: 30px;
                            }

                            .row {
                                flex-direction: column;
                                align-items: flex-start;
                            }

                            .value {
                                text-align: left;
                            }
                        }
                    </style>
                </head>

                <body>
                    <main class="container">
                        <section class="topbar">
                            <div class="brand">
                                <div class="logo">🔥</div>
                                <div>
                                    <h1>Argus IA</h1>
                                    <p class="subtitle">
                                        Assistente de Inteligência Artificial para documentação técnica,
                                        consulta de procedimentos, reemissão de relatórios e exportação em PDF.
                                    </p>
                                </div>
                            </div>

                            <div class="status-badge">ONLINE</div>
                        </section>

                        <section class="grid">
                            <div class="metric">
                                <div class="metric-label">Backend</div>
                                <div class="metric-value">Spring Boot</div>
                            </div>

                            <div class="metric">
                                <div class="metric-label">IA em Nuvem</div>
                                <div class="metric-value">Groq API</div>
                            </div>

                            <div class="metric">
                                <div class="metric-label">Banco de Dados</div>
                                <div class="metric-value">Oracle</div>
                            </div>
                        </section>

                        <section class="panel">
                            <div class="row">
                                <span class="label">Status da aplicação</span>
                                <span class="value">Serviço em execução</span>
                            </div>

                            <div class="row">
                                <span class="label">Módulo</span>
                                <span class="value">Groq API + Oracle + RAG</span>
                            </div>

                            <div class="row">
                                <span class="label">Recursos disponíveis</span>
                                <span class="value">Relatório, RAG, Reemissão e PDF</span>
                            </div>

                            <div class="row">
                                <span class="label">API</span>
                                <span class="value">REST / JSON / PDF</span>
                            </div>

                            <div class="row">
                                <span class="label">Última verificação</span>
                                <span class="value">{{DATA_HORA}}</span>
                            </div>
                        </section>

                        <section class="links">
                            <a href="/swagger-ui/index.html">Abrir Swagger</a>
                            <a href="/api/v1/ia/health">Health JSON</a>
                            <a href="/v3/api-docs">OpenAPI Docs</a>
                        </section>

                        <p class="footer">
                            O Argus IA atua como uma ferramenta de apoio documental e consultivo
                            para brigadistas e coordenadores, auxiliando na padronização de relatórios
                            e na consulta a procedimentos. A solução não substitui treinamento profissional,
                            protocolos oficiais, avaliação técnica ou decisão operacional em campo.
                        </p>
                    </main>
                </body>
                </html>
                """;

        return html.replace("{{DATA_HORA}}", dataHora);
    }
}