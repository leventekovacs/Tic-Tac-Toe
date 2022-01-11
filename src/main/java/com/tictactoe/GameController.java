package com.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private VBox container;

    @FXML
    private Pane markSelectorPane;

    @FXML
    private GridPane gameBoard;

    @FXML
    private Label playerScoresLabel;

    @FXML
    private Label aiScoresLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private Button newGameButton;

    private String playerMark;

    private String aiMark;

    private boolean playerTurn;

    private Cell[][] cells;

    private String winner;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.setOpacity(0.1);
        container.setDisable(true);
        newGameButton.setDisable(true);
        newGameButton.setVisible(false);
        resultLabel.setVisible(false);

        cells = new Cell[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
                gameBoard.add(cells[i][j],j,i);
            }
        }

        //Game engine
        gameBoard.setOnMouseClicked( event -> {
            if( !(event.getTarget() instanceof Cell playerSelectedCell)){
                return;
            }

            //Player move
            playerStep(playerSelectedCell);

            if(isWinner() || isBoardFull()) {
                gameOver();
                return;
            }

            //AI move
            if(isBoardFull()) return;
            Cell aiSelectedCell = aiSelectCell();
            aiStep(aiSelectedCell);

            if(isWinner() || isBoardFull()) {
                gameOver();
            }
        });
    }

    private void playerStep(Cell cell) {
        if( cell.isMarked() || !playerTurn) {
            return;
        }
        cell.writeContent(playerMark);
        cell.getContent().setStyle("" +
            "-fx-font-size: 72px;" +
            "-fx-text-fill: #60ff00;");
        cell.setMarked(true);
        playerTurn = false;
    }

    private void aiStep(Cell cell) {
        if(playerTurn) return;
        cell.writeContent(aiMark);
        cell.getContent().setStyle("" +
            "-fx-font-size: 72px;" +
            "-fx-text-fill: #ff004d;");
        cell.setMarked(true);
        playerTurn = true;
    }

    private Cell aiSelectRandomCell() {
        int colIndex = (int) (Math.random() * 3);
        int rowIndex = (int) (Math.random() * 3);
        Cell selectedCell = cells[rowIndex][colIndex];
        if(selectedCell.isMarked()) {
            return aiSelectRandomCell();
        } else {
            return cells[rowIndex][colIndex];
        }
    }

    //Returns the cell that prevent player win or win the game
    // If returns null that means there is not winning step
    private Cell aiSelectCell() {
        Cell randomStep = aiSelectRandomCell();
        Cell preventialStep = null;
        Cell winningStep;

        Cell[] row = new Cell[3];
        Cell[] column = new Cell[3];
        Cell[] diagonal = new Cell[3];
        Cell[] diagonal2 = new Cell[3];

        for (int i = 0; i < 3; i++) {
            //check rows
            row[0] = cells[i][0];
            row[1] = cells[i][1];
            row[2] = cells[i][2];
            if(isOneStepWin(row,playerMark)) {
                preventialStep = findEmptyCell(row);
            }
            if(isOneStepWin(row,aiMark)) {
                winningStep = findEmptyCell(row);
                return winningStep;
            }
            //check columns
            column[0] = cells[0][i];
            column[1] = cells[1][i];
            column[2] = cells[2][i];
            if(isOneStepWin(column,playerMark)) {
                preventialStep = findEmptyCell(column);
            }
            if(isOneStepWin(column,aiMark)) {
                winningStep = findEmptyCell(column);
                return winningStep;
            }
        }
        //check diagonal lines
        diagonal[0] = cells[0][0];
        diagonal[1] = cells[1][1];
        diagonal[2] = cells[2][2];
        if(isOneStepWin(diagonal,playerMark)) {
            preventialStep = findEmptyCell(diagonal);
        }
        if(isOneStepWin(diagonal,aiMark)) {
            winningStep = findEmptyCell(diagonal);
            return winningStep;
        }
        diagonal2[0] = cells[0][2];
        diagonal2[1] = cells[1][1];
        diagonal2[2] = cells[2][0];
        if(isOneStepWin(diagonal2,playerMark)) {
            preventialStep = findEmptyCell(diagonal2);
        }
        if(isOneStepWin(diagonal2,aiMark)) {
            winningStep = findEmptyCell(diagonal2);
            return winningStep;
        }

        if( !(preventialStep == null)) {
            return preventialStep;
        }

        return randomStep;
    }

    private boolean isOneStepWin(Cell[] combination, String mark) {
        int ownedCells = 0;
        int emptyCells = 0;

        for (Cell cell : combination) {
            if(cell.readContent().equals(mark)) {
                ownedCells++;
            }
            if(!cell.isMarked()) {
                emptyCells++;
            }
        }
        return ownedCells == 2 && emptyCells == 1;
    }

    //find the empty cell among 2 same marked cell
    private Cell findEmptyCell(Cell[] combination) {
        for (Cell cell : combination) {
            if(!cell.isMarked()) {
                return cell;
            }
        }
        return null;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(!cells[i][j].isMarked()) {
                    return false;
                }
            }
        }
        return true;
    }

    //Side effect: initialize winner variable if there is winner
    private boolean isWinner() {
        for (int i = 0; i < 3; i++) {
            //horizontal
            if(!cells[i][0].readContent().equals("") &&
                cells[i][0].readContent().equals(cells[i][1].readContent()) &&
                cells[i][1].readContent().equals(cells[i][2].readContent()))
            {
                winner = cells[i][0].readContent();
                return true;
            }
            //vertical
            if(!cells[0][i].readContent().equals("") &&
                cells[0][i].readContent().equals(cells[1][i].readContent()) &&
                cells[1][i].readContent().equals(cells[2][i].readContent()))
            {
                winner = cells[0][i].readContent();
                return true;
            }
        }
        //diagonal
        if(!cells[0][0].readContent().equals("") &&
            cells[0][0].readContent().equals(cells[1][1].readContent()) &&
            cells[1][1].readContent().equals(cells[2][2].readContent()))
        {
            winner = cells[0][0].readContent();
            return true;
        }
        //diagonal 2
        if(!cells[0][2].readContent().equals("") &&
            cells[0][2].readContent().equals(cells[1][1].readContent()) &&
            cells[1][1].readContent().equals(cells[2][0].readContent()))
        {
            winner = cells[0][2].readContent();
            return true;
        }
        return false;
    }

    private void gameOver() {
        if(winner == null) {
            gameBoard.setDisable(true);
            gameBoard.setOpacity(0.2);
            newGameButton.setDisable(false);
            newGameButton.setVisible(true);
            resultLabel.setVisible(true);
            resultLabel.setText("DRAW");
            resultLabel.setStyle("-fx-text-fill: #bebebe;");
            return;
        }

        //Increase scores
        if(winner.equals(playerMark)) {
            int playerScores = Integer.parseInt(playerScoresLabel.getText());
            String labelContent = ++playerScores + "";
            playerScoresLabel.setText(labelContent);
            resultLabel.setVisible(true);
            resultLabel.setText("  WIN");
            resultLabel.setStyle("-fx-text-fill: #60ff00;");
        } else {
            int aiScores = Integer.parseInt(aiScoresLabel.getText());
            String labelContent = ++aiScores + "";
            aiScoresLabel.setText(labelContent);
            resultLabel.setVisible(true);
            resultLabel.setText(" LOSE");
            resultLabel.setStyle("-fx-text-fill: #ff004d;");
        }
        gameBoard.setDisable(true);
        gameBoard.setOpacity(0.2);
        newGameButton.setDisable(false);
        newGameButton.setVisible(true);
    }

    @FXML
    private void newGame() {
        gameBoard.setDisable(false);
        gameBoard.setOpacity(1);
        cells = new Cell[3][3];
        gameBoard.getChildren().clear();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
                gameBoard.add(cells[i][j],j,i);
            }
        }
        winner = null;
        markSelectorPane.setDisable(false);
        markSelectorPane.setVisible(true);
        container.setDisable(true);
        container.setOpacity(0.1);
        resultLabel.setVisible(false);
        newGameButton.setDisable(true);
        newGameButton.setVisible(false);
    }

    @FXML
    private void exitGame() {
        GameManager.exitGame();
    }

    @FXML
    private void selectXMark() {
        selectMark("X");
    }

    @FXML
    private void selectOMark() {
        selectMark("O");
        //first move when the AI starts the game
        Cell aiSelectedCell = aiSelectCell();
        aiStep(aiSelectedCell);
        aiSelectedCell.setMarked(true);
        playerTurn = true;
    }

    private void selectMark(String mark) {
        playerMark = mark;
        if(mark.equals("X")) {
            aiMark = "O";
            playerTurn = true;
        } else {
            aiMark = "X";
            playerTurn = false;
        }
        markSelectorPane.setDisable(true);
        markSelectorPane.setVisible(false);
        container.setOpacity(1);
        container.setDisable(false);
    }
}