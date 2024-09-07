package com.programacao.jogo;

import com.programacao.jogo.entidades.Jogador;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ResultsController implements Initializable {

    @FXML
    private Label winnerName;

    @FXML
    private Label winnerPoints;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (winnerName == null || winnerPoints == null) return;

        Optional<Integer> maiorPontos = MainApplication.jogo.getJogadores().stream()
                .map(Jogador::getPontos)
                .max(Integer::compare);

        if (maiorPontos.isPresent()) {
            List<Jogador> vencedores = MainApplication.jogo.getJogadores().stream()
                    .filter(j -> j.getPontos() == maiorPontos.get())
                    .collect(Collectors.toList());

            if (vencedores == null || vencedores.isEmpty()) return;

            winnerName.setText(vencedores.size() > 1 ? "EMPATE!" : vencedores.get(0).getNome());
            winnerPoints.setText(String.valueOf(vencedores.get(0).getPontos()));
        }
    }

}