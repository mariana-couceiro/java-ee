package com.personagens;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/personagem")
public class PersonagemResource {

    private static final List<Personagem> personagens = new ArrayList<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarPersonagem(Personagem personagem) {
        personagens.add(personagem);

        return Response
                .status(Response.Status.CREATED)
                .entity(personagem)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Personagem> listarPersonagens() {
        return personagens;
    }
}