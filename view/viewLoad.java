package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image ;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Main;
/**
 *
 *                      Choice Box:
 *                      -
 *                      -
 *                      -
 *                     Load Game
 *
 */

public class viewLoad{
    private Main main;
    private Label selectGameLabel;
    private Stage stage;
    private BorderPane borderPane;

    private Button loadButton;

    private ListView<String> gamesList;
    public viewLoad(Stage stage){
        this.stage = stage;
        this.borderPane = new BorderPane();
        selectGameLabel = new Label(String.format("Currently playing: Default Game"));
        gamesList = new ListView<>(); //list of tetris.boards

        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        selectGameLabel.setId("CurrentBoard"); // DO NOT MODIFY ID

        gamesList.setId("BoardsList");  // DO NOT MODIFY ID
        gamesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        getFiles(gamesList); //get files for file selector

        loadButton = new Button("LOAD GAME");
        loadButton.setId("ChangeBoard"); // DO NOT MODIFY ID

        //on selection, do something
        loadButton.setOnAction(e -> {
            try {
                selectGame(selectGameLabel, gamesList);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox selectBoardBox = new VBox(10, selectGameLabel, gamesList, loadButton);

        // Default styles which can be modified
        gamesList.setPrefHeight(100);

        selectGameLabel.setStyle("-fx-text-fill: #e8e6e3");
        selectGameLabel.setFont(new Font(16));

        loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        loadButton.setPrefSize(200, 50);
        loadButton.setFont(new Font(16));

        selectBoardBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(selectBoardBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    /**
     * Populate the listView with all the .SER files in the boards directory
     *
     * @param listView ListView to update
     */
    private void getFiles(ListView<String> listView) {
        File folder = new File("games");
        List<String> files = new ArrayList<String>();

        // collects all the files in boards folder
        for (File file: Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(".ser")) {
                files.add(file.getName());
            }
        }

        listView.getItems().setAll(files);
    }


    /**
     * Select and load the board file selected in the boardsList and update selectBoardLabel with the name of the new Board file
     *
     * @param selectGameLabel a message Label to update which board is currently selected
     * @param boardsList a ListView populated with battleship games to load.
     */
    private void selectGame(Label selectGameLabel, ListView<String> boardsList) throws IOException {
        String board = boardsList.getSelectionModel().getSelectedItems().get(0);
        selectGameLabel.setText(board);
        Main main2 = loadBoard("boards/" + board);
        main.setEnemyBoard(main2.getEnemyBoard());
        main.setPlayerBoard(main2.getPlayerBoardBoard());
    }

    /**
     * Load the board from a file
     *
     * @param boardFile file to load
     * @return loaded a board
     */
    public Main loadBoard(String boardFile) throws IOException {
        System.out.println("boardFile: " + boardFile);
        // Reading the object from a file
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(boardFile);
            in = new ObjectInputStream(file);
            return (Main) in.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
            file.close();
        }
    }
}