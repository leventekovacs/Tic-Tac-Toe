package com.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameManager extends Application {

    @Override
    public void start(Stage primarystage) throws IOException {
        primarystage.setTitle("Tic-Tac-Toe");

        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("gameView.fxml"));
        Scene root = new Scene(fxmlLoader.load(), 400, 400);
        primarystage.setScene(root);

        primarystage.show();
    }

    public void startGame(String[] args) {
        launch(args);
    }
}
