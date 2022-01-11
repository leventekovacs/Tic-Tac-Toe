package com.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class GameManager extends Application {

    private static Stage primarystage;

    @Override
    public void start(Stage stage) throws IOException {
        primarystage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("gameView.fxml"));
        Scene root = new Scene(fxmlLoader.load(), 501, 600);
        String ccsPath = Objects.requireNonNull(TicTacToe.class.getResource("tic-tac-toe.css")).toExternalForm();
        root.getStylesheets().add(ccsPath);
        primarystage.setTitle("Tic-Tac-Toe");
        primarystage.setResizable(false);
        primarystage.setScene(root);
        primarystage.show();
    }

    public static void startGame(String[] args) {
        launch(args);
    }

    public static void exitGame() {
        primarystage.close();
    }
}
