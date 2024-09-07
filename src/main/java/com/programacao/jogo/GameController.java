package com.programacao.jogo;

import com.programacao.jogo.dto.CartaDTO;
import com.programacao.jogo.dto.TabuleiroDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Label playerName;
    @FXML
    private Label playerPoints;
    @FXML
    private GridPane gridPane;


    @FXML
    protected void saveButton() {
        MainApplication.jogo.salvar();
    }

    @FXML
    protected void jogada(MouseEvent event) {
        try {
            ImageView imageView = (ImageView) event.getSource();
            if (MainApplication.jogo.getTabuleiro().getUltimaCarta() != null && !MainApplication.jogo.getTabuleiro().getUltimaCarta().isVirada()) {
                revertLastCard(imageView, MainApplication.jogo.getTabuleiro().getUltimaCarta());
                MainApplication.jogo.getTabuleiro().setPenultimaCarta(null);
                MainApplication.jogo.getTabuleiro().setUltimaCarta(null);
            }

            CartaDTO carta = MainApplication.jogo.jogada(imageView, Integer.parseInt(imageView.getId().substring(4).split("x")[1]), Integer.parseInt(imageView.getId().substring(4).split("x")[0]));
            if (carta == null) return;

            this.initialize(null, null);
            Boolean encerrado = MainApplication.jogo.verificarSeEncerraJogo();
            if (encerrado) MainApplication.mudarTela("results");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void revertLastCard(ImageView imageView, CartaDTO carta) {
        TabuleiroDTO tabuleiroDTO = MainApplication.jogo.getTabuleiro();
        gridPane.getChildren().stream().forEach(node -> {
            ImageView children = (ImageView) node;
            String id = children.getId();
            int col = Integer.parseInt(id.substring(4).split("x")[1]);
            int linha = Integer.parseInt(id.substring(4).split("x")[0]);
            CartaDTO cartaDTO = tabuleiroDTO.getCartas()[col][linha];

            if (!cartaDTO.getValor().equals(tabuleiroDTO.getUltimaCarta().getValor()) && !cartaDTO.getValor().equals(tabuleiroDTO.getPenultimaCarta().getValor()))
                return;

            cartaDTO.setVirada(false);
            tabuleiroDTO.getCartas()[col][linha] = cartaDTO;
            MainApplication.jogo.changeCardPicture(imageView.getImage().getUrl(),
                    children, "background.jpg");

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TabuleiroDTO tabuleiroDTO = MainApplication.jogo.getTabuleiro();
        if (playerName == null || playerPoints == null || tabuleiroDTO == null) return;
        playerName.setText(tabuleiroDTO.getJogadores().get(tabuleiroDTO.getIdxJogadorAtual()).getNome());
        playerPoints.setText(String.valueOf(tabuleiroDTO.getJogadores().get(tabuleiroDTO.getIdxJogadorAtual()).getPontos()));
        if (url != null)
            this.virarCartasSalvas();
    }

    private void virarCartasSalvas() {
        TabuleiroDTO tabuleiroDTO = MainApplication.jogo.getTabuleiro();
        gridPane.getChildren().stream().forEach(node -> {
            ImageView children = (ImageView) node;
            String id = children.getId();
            int col = Integer.parseInt(id.substring(4).split("x")[1]);
            int linha = Integer.parseInt(id.substring(4).split("x")[0]);
            CartaDTO cartaDTO = tabuleiroDTO.getCartas()[col][linha];
            if (!cartaDTO.isVirada() && (tabuleiroDTO.getUltimaCarta() == null || !tabuleiroDTO.getUltimaCarta().getCodigo().equals(cartaDTO.getCodigo())))
                return;
            MainApplication.jogo.changeCardPicture(children.getImage().getUrl(), children, cartaDTO.getValor() + ".jpg");
        });
    }
}