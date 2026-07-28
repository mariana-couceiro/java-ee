/*
HTTP Client em Java: O Automatizador de Relatórios da Citadela
Fazer pedidos um a um no Bruno para saber quem está vivo ou morto no universo de Rick & Morty é trabalho de estagiário!
Vamos usar o Java para analisar os primeiros 20 cidadãos do universo automaticamente.
Modifiquem o código base em grupo para cumprir estes 4 objetivos:

4. 
    Se o vosso programa detetar um alien morto, deve iniciar uma investigação após o alerta para descobrir onde ele foi visto pela última vez.
    Isolem o URL do episódio e façam o Java disparar um segundo pedido HTTP para o URL do episódio que acabaram de descobrir.
    Extraiam o nome desse episódio e mostrem o veredicto no ecrã com este formato:
    [ALERTA FORENSE] O último registo do alien morto foi no episódio: '...'.
 */

package com.ex4;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Exercicios4 {

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        for (int i = 1; i <= 20; i++) {

            String url = "https://rickandmortyapi.com/api/character/" + i;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode personagem = mapper.readTree(response.body());

            String species = personagem.get("species").asText();
            String status = personagem.get("status").asText();

            if (species.equals("Alien") && status.equals("Dead")) {

                System.out.println("[PERIGO] Um Alien foi encontrado morto com o ID " + i + "!");

                JsonNode episodios = personagem.get("episode");

                String ultimoEpisodio =
                        episodios.get(episodios.size() - 1).asText();

                HttpRequest requestEpisode = HttpRequest.newBuilder()
                        .uri(URI.create(ultimoEpisodio))
                        .GET()
                        .build();

                HttpResponse<String> responseEpisode =
                        client.send(requestEpisode, HttpResponse.BodyHandlers.ofString());

                JsonNode episodio = mapper.readTree(responseEpisode.body());

                String nomeEpisodio = episodio.get("name").asText();

                System.out.println("[ALERTA FORENSE] O último registo do alien morto foi no episódio: '" + nomeEpisodio + "'.");

            }

        }

    }

}
