package com.personagens;

import java.util.ArrayList;
import java.util.List;

public class PersonagemRepository {

    private static final List<Personagem> personagens = new ArrayList<>();

    public void adicionar(Personagem personagem) {
        personagens.add(personagem);
    }

    public List<Personagem> listarTodas() {
        return personagens;
    }

    public Personagem procurarPorNome(String nome) {

        for (Personagem personagem : personagens) {
            if (personagem.getNome().equalsIgnoreCase(nome)) {
                return personagem;
            }
        }

        return null;
    }

    public Personagem editarPorNome(String nome, Personagem dadosAtualizados) {

        for (Personagem personagem : personagens) {

            if (personagem.getNome().equalsIgnoreCase(nome)) {

                personagem.setNome(dadosAtualizados.getNome());
                personagem.setEspecie(dadosAtualizados.getEspecie());
                personagem.setComidaFavorita(dadosAtualizados.getComidaFavorita());

                return personagem;
            }
        }

        return null;
    }

    public boolean apagarPorNome(String nome) {
        return personagens.removeIf(
            personagem -> personagem.getNome().equalsIgnoreCase(nome)
        );
    }
}
