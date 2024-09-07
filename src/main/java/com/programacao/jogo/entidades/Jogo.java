package com.programacao.jogo.entidades;

import com.programacao.jogo.dto.CartaDTO;
import com.programacao.jogo.dto.TabuleiroDTO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jogo {

    private List<Jogador> jogadores = new ArrayList<Jogador>();
    private TabuleiroDTO tabuleiro;

    public void inicializarTabuleiro() {
        try {
            ObjectInputStream input = new ObjectInputStream(Files.newInputStream(Paths.get("tabuleiro.json")));
            if (input != null) {
                TabuleiroDTO tabuleiroDTO = (TabuleiroDTO) input.readObject();
                setJogadores(tabuleiroDTO.getJogadores());
                setTabuleiro(tabuleiroDTO);
            } else
                setTabuleiro(TabuleiroDTO.builder().jogadores(this.jogadores).save(false).cartas(this.randomCartas()).build());
        } catch (Exception e) {
            e.printStackTrace();
            setTabuleiro(TabuleiroDTO.builder().jogadores(this.jogadores).save(false).cartas(this.randomCartas()).build());
        }
    }


    // Algoritmo para randomizar as cartas e adiciona-las na matriz
    public CartaDTO[][] randomCartas() {
        ArrayList<String> cartas = new ArrayList<>(Arrays.asList("cardForce", "cardForce", "catchUp", "catchUp", "chum", "chum", "earthShutdown", "earthShutdown",
                "gorem", "gorem", "hotWind", "hotWind", "legendsOfWater", "legendsOfWater", "lowEnergy", "lowEnergy"));
        Collections.shuffle(cartas);
        CartaDTO[][] cartasMatriz = new CartaDTO[4][4];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cartasMatriz[i][j] = CartaDTO.builder().codigo(count).virada(false).valor(cartas.get(count)).build();
                count++;
            }
        }
        return cartasMatriz;
    }

    public void inserirJogador(String nome) {
        Jogador jogador = new Jogador(nome, 0);
        jogadores.add(jogador);
    }


    public CartaDTO jogada(ImageView imageView, int coluna, int linha) {
        CartaDTO cartaSelecionada = getTabuleiro().getCartas()[coluna][linha];
        if (cartaSelecionada.isVirada()) return null;

        changeCardPicture(imageView.getImage().getUrl(), imageView, cartaSelecionada.getValor() + ".jpg");

        if (getTabuleiro().getUltimaCarta() == null) {
            cartaSelecionada.setVirada(true);
            getTabuleiro().setUltimaCarta(cartaSelecionada);
            return cartaSelecionada;
        }

        Boolean acertou = this.verificarSeAcertou(getTabuleiro(), cartaSelecionada);
        if (acertou) {
            cartaSelecionada.setVirada(true);
            getTabuleiro().setUltimaCarta(null);
            getTabuleiro().setPenultimaCarta(null);
        } else {
            getTabuleiro().setPenultimaCarta(getTabuleiro().getUltimaCarta());
            getTabuleiro().setUltimaCarta(cartaSelecionada);
        }
        return cartaSelecionada;
    }


    public void changeCardPicture(String baseURL, ImageView target, String cardURL) {
        int fimUrl = baseURL.lastIndexOf("/");
        String path = baseURL.substring(0, fimUrl + 1);
        target.setImage(new Image(path + cardURL));
    }


    public void esconderUltimaCarta() {
        if (getTabuleiro().getUltimaCarta() == null) return;
        Boolean stopFor = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                CartaDTO cartaDTO = getTabuleiro().getCartas()[i][j];
                if (cartaDTO.getCodigo().equals(getTabuleiro().getUltimaCarta().getCodigo())) {
                    stopFor = true;
                    cartaDTO.setVirada(false);
                    break;
                }
            }
            if (stopFor) break;
        }
    }

    public boolean verificarSeAcertou(TabuleiroDTO tabuleiroDTO, CartaDTO carta) {
        if (!tabuleiroDTO.getUltimaCarta().getValor().equals(carta.getValor())) {
            int playerIdx = tabuleiroDTO.getIdxJogadorAtual() + 1;
            if ((playerIdx + 1) <= tabuleiroDTO.getJogadores().size())
                tabuleiroDTO.setIdxJogadorAtual(playerIdx);
            else tabuleiroDTO.setIdxJogadorAtual(0);
            return false;
        }
        tabuleiroDTO.setUltimaCarta(null);
        tabuleiroDTO.getJogadores().get(tabuleiroDTO.getIdxJogadorAtual()).adicionarPontos(1);
        return true;
    }

    public Boolean verificarSeEncerraJogo() {
        Boolean encerrado = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                encerrado = getTabuleiro().getCartas()[i][j].isVirada();
                if (!encerrado) break;
            }
            if (!encerrado) break;
        }
        getTabuleiro().setJogoEncerrado(encerrado);
        if (getTabuleiro().isJogoEncerrado())
            this.deleteFile();
        return encerrado;
    }

    public void deleteFile() {
        try {
            File file = new File("tabuleiro.json");
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salvar() {
        try {
            FileOutputStream file = new FileOutputStream("tabuleiro.json");
            ObjectOutputStream oos = new ObjectOutputStream(file);
            getTabuleiro().setSave(true);
            oos.writeObject(getTabuleiro());
            oos.close();
            file.close();
        } catch (Exception e) {
            System.out.println("Erro ao salvar.");
            e.printStackTrace();
        }
    }
}
