package com.personagens;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class PersonagemRepository {

    @PersistenceContext
    private EntityManager em;

    
    public void adicionar(Personagem personagem) {
        em.persist(personagem);
    }

    public List<Personagem> listarTodos() {
        return em.createQuery(
                "SELECT p FROM Personagem p",
                Personagem.class
        ).getResultList();
    }

    public Personagem procurarPorId(int id) {
        return em.find(Personagem.class, id);
    }

    public Personagem editarPorId(
            int id,
            Personagem dadosAtualizados) {

        Personagem personagem =
                em.find(Personagem.class, id);

        if (personagem == null) {
            return null;
        }

        personagem.setNome(
                dadosAtualizados.getNome()
        );

        personagem.setEspecie(
                dadosAtualizados.getEspecie()
        );

        personagem.setComidaFavorita(
                dadosAtualizados.getComidaFavorita()
        );

        return personagem;
    }

    // UPDATE PARCIAL - PATCH
    public Personagem atualizarParcialmente(
            int id,
            Personagem dadosAtualizados) {

        Personagem personagem =
                em.find(Personagem.class, id);

        if (personagem == null) {
            return null;
        }

        if (dadosAtualizados.getNome() != null) {
            personagem.setNome(
                    dadosAtualizados.getNome()
            );
        }

        if (dadosAtualizados.getEspecie() != null) {
            personagem.setEspecie(
                    dadosAtualizados.getEspecie()
            );
        }

        if (dadosAtualizados.getComidaFavorita() != null) {
            personagem.setComidaFavorita(
                    dadosAtualizados.getComidaFavorita()
            );
        }

        return personagem;
    }

    public boolean apagarPorId(int id) {

        Personagem personagem =
                em.find(Personagem.class, id);

        if (personagem == null) {
            return false;
        }

        em.remove(personagem);

        return true;
    }
}