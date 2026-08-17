package com.personagens;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersonagemService {

    @Inject
    private PersonagemRepository repository;

    public Personagem criarPersonagem(Personagem personagem) {

        if (personagem.getComidaFavorita() == null ||
                personagem.getComidaFavorita().isBlank()) {

            personagem.setComidaFavorita("Pizza");
        }

        repository.adicionar(personagem);

        return personagem;
    }

    public List<Personagem> listarPersonagens() {
        return repository.listarTodas();
    }

    public Personagem procurarPorNome(String nome) {
        return repository.procurarPorNome(nome);
    }

    public Personagem editarPersonagem(
            String nome,
            Personagem dadosAtualizados) {

        if (dadosAtualizados.getComidaFavorita() == null ||
                dadosAtualizados.getComidaFavorita().isBlank()) {

            dadosAtualizados.setComidaFavorita("Pizza");
        }

        return repository.editarPorNome(nome, dadosAtualizados);
    }

    public boolean apagarPorNome(String nome) {
        return repository.apagarPorNome(nome);
    }
}