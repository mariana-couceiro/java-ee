package com.census;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/census")
public class CensusServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int offset = 1;
        int limit = 20;
        boolean showAlerts = true;

        String offsetParam = request.getParameter("offset");
        String limitParam = request.getParameter("limit");
        String showAlertsParam = request.getParameter("showAlerts");

        if (offsetParam != null) {
            try {
                offset = Integer.parseInt(offsetParam);
            } catch (NumberFormatException e) {
                enviarErro(
                        response,
                        "O parâmetro 'offset' deve ser um número inteiro.");
                return;
            }
        }

        if (offset < 1) {
            enviarErro(
                    response,
                    "O parâmetro 'offset' deve ser um número inteiro maior ou igual a 1.");
            return;
        }

        if (limitParam != null) {
            try {
                limit = Integer.parseInt(limitParam);
            } catch (NumberFormatException e) {
                enviarErro(
                        response,
                        "O parâmetro 'limit' deve ser um número inteiro entre 1 e 50.");
                return;
            }
        }

        if (limit < 1 || limit > 50) {
            enviarErro(
                    response,
                    "O parâmetro 'limit' deve ser um número inteiro entre 1 e 50.");
            return;
        }


        if (showAlertsParam != null) {
            if (showAlertsParam.equalsIgnoreCase("true")) {
                showAlerts = true;
            } else if (showAlertsParam.equalsIgnoreCase("false")) {
                showAlerts = false;
            } else {
                enviarErro(
                        response,
                        "O parâmetro 'showAlerts' deve ser 'true' ou 'false'.");
                return;
            }
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        StringBuilder html = new StringBuilder();

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        int vivos = 0;
        int mortos = 0;
        int desconhecidos = 0;

        html.append("<!DOCTYPE html>");
        html.append("<html lang='pt'>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<title>Censo Rick & Morty</title>");
        html.append("</head>");
        html.append("<body>");

        html.append("<h1>CENSO RICK & MORTY</h1>");
        html.append("<hr>");


        for (int i = offset; i < offset + limit; i++) {

            String url =
                    "https://rickandmortyapi.com/api/character/" + i;

            HttpRequest httpRequest =
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();

            HttpResponse<String> httpResponse;

            try {
                httpResponse =
                        client.send(
                                httpRequest,
                                HttpResponse.BodyHandlers.ofString());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ServletException(
                        "O pedido HTTP foi interrompido.",
                        e);
            }

            if (httpResponse.statusCode() != 200) {
                continue;
            }

            JsonNode personagem =
                    mapper.readTree(httpResponse.body());

            String status =
                    personagem.get("status").asText();

            String species =
                    personagem.get("species").asText();

            if (status.equals("Alive")) {
                vivos++;
            } else if (status.equals("Dead")) {
                mortos++;
            } else {
                desconhecidos++;
            }

            if (showAlerts
                    && species.equals("Alien")
                    && status.equals("Dead")) {

                html.append("<h3 style='color:red'>");
                html.append("[PERIGO] Um Alien foi encontrado morto com o ID ")
                        .append(i)
                        .append("!");
                html.append("</h3>");

                JsonNode episodios =
                        personagem.get("episode");

                if (episodios != null && episodios.size() > 0) {

                    String ultimoEpisodio =
                            episodios
                                    .get(episodios.size() - 1)
                                    .asText();

                    HttpRequest requestEpisode =
                            HttpRequest.newBuilder()
                                    .uri(URI.create(ultimoEpisodio))
                                    .GET()
                                    .build();

                    HttpResponse<String> responseEpisode;

                    try {
                        responseEpisode =
                                client.send(
                                        requestEpisode,
                                        HttpResponse.BodyHandlers.ofString());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new ServletException(
                                "O pedido ao episódio foi interrompido.",
                                e);
                    }

                    if (responseEpisode.statusCode() == 200) {

                        JsonNode episodio =
                                mapper.readTree(
                                        responseEpisode.body());

                        String nomeEpisodio =
                                episodio.get("name").asText();

                        html.append("<p>");
                        html.append("<b>[ALERTA FORENSE]</b> ");
                        html.append("O último registo do alien morto foi no episódio: <i>")
                                .append(nomeEpisodio)
                                .append("</i>.");
                        html.append("</p>");
                    }
                }

                html.append("<hr>");
            }
        }

        html.append("<h2>Relatório Final</h2>");

        html.append("<p><b>Vivos:</b> ")
                .append(vivos)
                .append("</p>");

        html.append("<p><b>Mortos:</b> ")
                .append(mortos)
                .append("</p>");

        html.append("<p><b>Desconhecidos:</b> ")
                .append(desconhecidos)
                .append("</p>");

        html.append("</body>");
        html.append("</html>");

        out.print(html.toString());
    }

    private void enviarErro(
            HttpServletResponse response,
            String mensagem)
            throws IOException {

        response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST);

        response.setContentType(
                "application/json");

        response.setCharacterEncoding(
                "UTF-8");

        response.getWriter().println("""
                {
                "status": 400,
                "error": "Bad Request",
                "message": "%s"
                }
                """.formatted(mensagem));
    }
}
