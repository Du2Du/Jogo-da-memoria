package com.programacao.jogo.dto;

import com.programacao.jogo.entidades.Jogador;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TabuleiroDTO implements Serializable {
    private List<Jogador> jogadores;
    private int idxJogadorAtual = 0;
    private boolean save = false;
    private boolean jogoEncerrado = false;
    private CartaDTO[][] cartas = new CartaDTO[4][4];
    private CartaDTO ultimaCarta;
    private CartaDTO penultimaCarta;
}
