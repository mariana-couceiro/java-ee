/* 
HTTP Client em Java: O Automatizador de Relatórios da Citadela
Fazer pedidos um a um no Bruno para saber quem está vivo ou morto no universo de Rick & Morty é trabalho de estagiário!
Vamos usar o Java para analisar os primeiros 20 cidadãos do universo automaticamente.
Modifiquem o código base em grupo para cumprir estes 4 objetivos:
 
1.
    O Varredor de Portais (O Loop)
    O vosso programa deve analisar automaticamente os primeiros 20 cidadãos do universo. Criem um ciclo que faça o código Java disparar 20 pedidos HTTP seguidos, alterando o ID no fim do URL de forma dinâmica (do ID 1 ao 20).
*/

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
 
public class Exercicios1 {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
 
        for (int i = 1; i <= 20; i++) {
            String url = "https://rickandmortyapi.com/api/character/" + i;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
 
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
    }
}
