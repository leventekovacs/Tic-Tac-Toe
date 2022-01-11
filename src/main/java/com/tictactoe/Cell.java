package com.tictactoe;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Cell extends StackPane {

    private final Label content;
    private boolean marked = false;

    public Cell() {
        content = new Label();
        content.setDisable(true);
        content.setOpacity(1);
        getChildren().add(content);
        setStyle("-fx-border-color: #bebebe;");
    }

    public Label getContent() {
        return content;
    }

    public String readContent() {
        return content.getText();
    }

    public void writeContent(String content) {
        this.content.setText(content);
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
