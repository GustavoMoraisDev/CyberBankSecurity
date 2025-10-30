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
}
