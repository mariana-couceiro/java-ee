package com.personagens;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
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

    // CREATE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarPersonagem(Personagem personagem) {

        Personagem novaPersonagem =
                service.criarPersonagem(personagem);

        return Response
                .status(Response.Status.CREATED)
                .entity(novaPersonagem)
                .build();
    }

    // READ - listar todas
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Personagem> listarPersonagens() {

        return service.listarPersonagens();
    }

    // READ - procurar pelo nome
    @GET
    @Path("/{nome}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterPersonagemPorNome(
            @PathParam("nome") String nome) {

        Personagem personagem =
                service.procurarPorNome(nome);

        if (personagem != null) {

            return Response
                    .ok(personagem)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity("Personagem não encontrada.")
                .build();
    }

    // UPDATE
    @PUT
    @Path("/{nome}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarPersonagem(
            @PathParam("nome") String nome,
            Personagem dadosAtualizados) {

        Personagem personagem =
                service.editarPersonagem(
                        nome,
                        dadosAtualizados
                );

        if (personagem != null) {

            return Response
                    .ok(personagem)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity("Personagem não encontrada.")
                .build();
    }

    // DELETE
    @DELETE
    @Path("/{nome}")
    public Response apagarPersonagem(
            @PathParam("nome") String nome) {

        boolean removida =
                service.apagarPorNome(nome);

        if (removida) {

            return Response
                    .ok("Personagem eliminada com sucesso.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity("Personagem não encontrada.")
                .build();
    }
}