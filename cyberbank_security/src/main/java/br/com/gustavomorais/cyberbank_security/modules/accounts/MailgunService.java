package br.com.gustavomorais.cyberbank_security.modules.accounts;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class MailgunService {

     // dominio API vai Mailgun aqui
     // Chave API Mailgun vai aqui
    

    public void enviarEmailContaAprovada(
            String para, String nome, String agencia, int conta, String senha, String token) {
        try {
            String mensagem = "Olá " + nome + ",\n\n" +
                    "Parabéns! Sua conta foi aprovada.\n\n" +
                    "Agência: " + agencia + "\n" +
                    "Conta: " + conta + "\n" +
                    "Senha: " + senha + "\n" +
                    "Token de Segurança: " + token + "\n\n" +
                    "Obrigado!";

            String form = "from=" + URLEncoder.encode("Bradesco <no-reply@" + DOMAIN + ">", StandardCharsets.UTF_8)
                    + "&to=" + URLEncoder.encode(para, StandardCharsets.UTF_8)
                    + "&subject=" + URLEncoder.encode("Conta aprovada", StandardCharsets.UTF_8)
                    + "&text=" + URLEncoder.encode(mensagem, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.mailgun.net/v3/" + DOMAIN + "/messages"))
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("api:" + API_KEY).getBytes()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("E-mail enviado para " + para);

        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void enviarEmailVerificacaoTransacao(String para, String nome, String codigo, int valor) {
    try {
        String assunto = "Verificação de segurança — Confirmação de Transação";
        String mensagem = "Olá " + nome + ",\n\n" +
                "Detectamos uma tentativa de transação com valor de R$ " + valor + ".\n\n" +
                "Por medidas de segurança, precisamos que você confirme a operação.\n\n" +
                "Seu código de verificação é:\n\n" +
                codigo + "\n\n" +
                "Copie esse código e insira no aplicativo para prosseguir com a transação.\n\n" +
                "Se você não realizou essa solicitação, ignore este e-mail.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Bradesco Digital";

        String form = "from=" + URLEncoder.encode("Bradesco <no-reply@" + DOMAIN + ">", StandardCharsets.UTF_8)
                + "&to=" + URLEncoder.encode(para, StandardCharsets.UTF_8)
                + "&subject=" + URLEncoder.encode(assunto, StandardCharsets.UTF_8)
                + "&text=" + URLEncoder.encode(mensagem, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.mailgun.net/v3/" + DOMAIN + "/messages"))
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString(("api:" + API_KEY).getBytes()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("📩 E-mail de verificação de transação enviado para " + para);

    } catch (Exception e) {
        System.err.println("❌ Erro ao enviar e-mail de verificação: " + e.getMessage());
        e.printStackTrace();
    }
}




}
