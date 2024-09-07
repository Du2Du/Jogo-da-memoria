package com.programacao.jogo.entidades;

import com.programacao.jogo.dto.CartaDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Jogador implements Serializable {

    private String nome;
    private int pontos = 0;

    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }

    public Jogador(String nome, int pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }
}
