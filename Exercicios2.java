/*HTTP Client em Java: O Automatizador de Relatórios da Citadela
Fazer pedidos um a um no Bruno para saber quem está vivo ou morto no universo de Rick & Morty é trabalho de estagiário!
Vamos usar o Java para analisar os primeiros 20 cidadãos do universo automaticamente.
Modifiquem o código base em grupo para cumprir estes 4 objetivos: 
2.    
    O Censo Demográfico (Lógica de Contagem) 
    Queremos estatísticas reais. O programa deve analisar o texto de cada resposta (JSON) e contar quantos cidadãos estão vivos e quantos estão mortos.
    No final do programa (fora do loop), imprimam o relatório final na consola:
    => CENSO: Detetados X personagens VIVOS e Y personagens MORTOS nos primeiros 20 registos.
*/

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
 
public class Exercicios2 {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        int nAlive = 0, nDead = 0, nUnknown = 0;
 
        for (int i = 1; i <= 20; i++) {
            String url = "https://rickandmortyapi.com/api/character/" + i;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
 
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().contains("\"status\":\"Alive\"")) {
                nAlive++;
            }
           
            if (response.body().contains("\"status\":\"Dead\"")) {
                nDead++;
            }
 
            if (response.body().contains("\"status\":\"unknown\"")) {
                nUnknown++;
            }
        }
 
        System.out.println("CENSO: Detetados " + nAlive + " personagens VIVOS, " + nDead + " personagens MORTOS e " + nUnknown + " personagens DESCONHECIDOS nos primeiros 20 registos");
    }
}
