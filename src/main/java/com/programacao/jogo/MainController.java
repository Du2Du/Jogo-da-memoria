package com.programacao.jogo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainController {

    @FXML
    private TextField playerName;
    @FXML
    private Button initializeGameButton;
    @FXML
    private Button addPlayerButton;

    @FXML
    protected void addPlayer() {
        if (playerName != null && playerName.getText() != null) {
            MainApplication.jogo.inserirJogador(playerName.getText());
            playerName.setText("");
        }
        MainApplication.mudarTela("addPlayer");
    }

    @FXML
    protected void startButtonEvent() {
        try {
            ObjectInputStream input = new ObjectInputStream(Files.newInputStream(Paths.get("tabuleiro.json")));
            if (input != null) {
                MainApplication.jogo.inicializarTabuleiro();
                MainApplication.mudarTela("game");
            } else addPlayer();
        } catch (Exception e) {
            e.printStackTrace();
            addPlayer();
        }
    }

    @FXML
    protected void toggleDisableButtons() {
        if (addPlayerButton == null || initializeGameButton == null) return;
        Boolean disable = playerName == null || playerName.getText() == null || "".equals(playerName.getText().trim());
        addPlayerButton.setDisable(disable);
        initializeGameButton.setDisable(disable);
    }


    @FXML
    protected void initializeGame() {
        MainApplication.jogo.inserirJogador(playerName.getText());
        MainApplication.jogo.inicializarTabuleiro();
        MainApplication.mudarTela("game");
    }


}