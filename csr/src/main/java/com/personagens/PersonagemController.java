package com.personagens;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/personagem")
public class PersonagemController {

    @Inject
    private PersonagemService service;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarPersonagem(Personagem personagem) {

        Personagem novaPersonagem =
                service.criarPersonagem(personagem);

        Map<String, Object> dadosPersonagem =
                criarMapaPersonagem(novaPersonagem);

        Map<String, Object> resposta =
                new LinkedHashMap<>();

        resposta.put(
                "mensagem",
                "Personagem criada com sucesso."
        );

        resposta.put(
                "personagem",
                dadosPersonagem
        );

        return Response
                .status(Response.Status.CREATED)
                .entity(resposta)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> listarPersonagens() {

        List<Personagem> personagens =
                service.listarPersonagens();

        return personagens.stream()
                .map(this::criarMapaPersonagem)
                .toList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterPersonagemPorId(
            @PathParam("id") int id) {

        Personagem personagem =
                service.procurarPorId(id);

        if (personagem != null) {

            return Response
                    .ok(criarMapaPersonagem(personagem))
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity("Personagem não encontrada.")
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarPersonagem(
            @PathParam("id") int id,
            Personagem dadosAtualizados) {

        Personagem personagem =
                service.editarPersonagem(
                        id,
                        dadosAtualizados
                );

        if (personagem != null) {

            return Response
                    .ok(criarMapaPersonagem(personagem))
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity("Personagem não encontrada.")
                .build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarParcialmente(
            @PathParam("id") int id,
            Personagem dadosAtualizados) {

        Personagem personagem =
                service.atualizarParcialmente(
                        id,
                        dadosAtualizados
                );

        if (personagem != null) {

            return Response
                    .ok(criarMapaPersonagem(personagem))
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity("Personagem não encontrada.")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response apagarPersonagem(
            @PathParam("id") int id) {

        boolean removida =
                service.apagarPorId(id);

        if (removida) {

            return Response
                    .ok("Personagem eliminada com sucesso.")
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity("Personagem não encontrada.")
                .build();
    }

    private Map<String, Object> criarMapaPersonagem(
            Personagem personagem) {

        Map<String, Object> dados =
                new LinkedHashMap<>();

        dados.put("id", personagem.getId());
        dados.put("nome", personagem.getNome());
        dados.put("especie", personagem.getEspecie());
        dados.put(
                "comidaFavorita",
                personagem.getComidaFavorita()
        );

        return dados;
    }
}