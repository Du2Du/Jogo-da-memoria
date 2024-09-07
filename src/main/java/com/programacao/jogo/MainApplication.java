package com.programacao.jogo;

import com.programacao.jogo.entidades.Jogo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage stage;
    public static Jogo jogo = new Jogo();
    private static Scene mainScene;
    private static Scene addPlayersScene;
    private static Scene resultsScene;
    private static Scene gameScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        primaryStage.setTitle("Jogo da Memória");

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("main.fxml"));
        mainScene = new Scene(fxmlLoader, 320, 240);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void mudarTela(String codigo) {
        try {
            switch (codigo) {
                case "addPlayer":
                    Parent fxmlAddPlayersLoader = FXMLLoader.load(MainApplication.class.getResource("add-player.fxml"));
                    addPlayersScene = new Scene(fxmlAddPlayersLoader, 900, 700);
                    stage.setScene(addPlayersScene);
                    break;
                case "results":
                    Parent fxmlResultsLoader = FXMLLoader.load(MainApplication.class.getResource("results.fxml"));
                    resultsScene = new Scene(fxmlResultsLoader, 320, 240);
                    stage.setScene(resultsScene);
                    break;
                case "game":
                    Parent fxmlGameLoader = FXMLLoader.load(MainApplication.class.getResource("game.fxml"));
                    gameScene = new Scene(fxmlGameLoader, 540, 759);
                    stage.setScene(gameScene);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}