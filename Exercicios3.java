/*
HTTP Client em Java: O Automatizador de Relatórios da Citadela
Fazer pedidos um a um no Bruno para saber quem está vivo ou morto no universo de Rick & Morty é trabalho de estagiário!
Vamos usar o Java para analisar os primeiros 20 cidadãos do universo automaticamente.
Modifiquem o código base em grupo para cumprir estes 4 objetivos:

3.
    Alerta de Segurança: Ameaça Alienígena 
    A Citadela precisa de monitorizar riscos biológicos. Se o vosso programa detetar um cidadão que seja da espécie Alien e que esteja Morto, deve imprimir um alerta imediato na consola:
    [PERIGO] Um Alien foi encontrado morto com o ID X!
*/

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
 
public class Exercicios3 {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
 
        for (int i = 1; i <= 20; i++) {
            String url = "https://rickandmortyapi.com/api/character/" + i;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
 
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().contains("\"species\":\"Alien\"") && response.body().contains("\"status\":\"Dead\"")){
                System.out.println("[PERIGO] Um Alien foi encontrado morto com o ID " + i + " !");
            }
        }
    }
}
