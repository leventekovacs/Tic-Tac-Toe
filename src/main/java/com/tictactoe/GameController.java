package com.tictactoe;

import com.tictactoe.component.Cell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private GridPane gameBoard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cell[][] cells = new Cell[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
                gameBoard.add(cells[i][j],i,j);
            }
        }
    }
}