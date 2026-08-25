package com.personagens;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PersonagemService {

    @Inject
    private PersonagemRepository repository;

    @Transactional
    public Personagem criarPersonagem(
            Personagem personagem) {

        if (personagem.getComidaFavorita() == null ||
                personagem.getComidaFavorita().isBlank()) {

            personagem.setComidaFavorita("Pizza");
        }

        repository.adicionar(personagem);

        return personagem;
    }

    public List<Personagem> listarPersonagens() {

        return repository.listarTodos();
    }

    public Personagem procurarPorId(int id) {

        return repository.procurarPorId(id);
    }

    @Transactional
    public Personagem editarPersonagem(
            int id,
            Personagem dadosAtualizados) {

        if (dadosAtualizados.getComidaFavorita() == null ||
                dadosAtualizados.getComidaFavorita().isBlank()) {

            dadosAtualizados.setComidaFavorita("Pizza");
        }

        return repository.editarPorId(
                id,
                dadosAtualizados
        );
    }

    @Transactional
    public Personagem atualizarParcialmente(
            int id,
            Personagem dadosAtualizados) {

        return repository.atualizarParcialmente(
                id,
                dadosAtualizados
        );
    }

    @Transactional
    public boolean apagarPorId(int id) {

        return repository.apagarPorId(id);
    }
}