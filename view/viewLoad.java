package view;

import Player.Player;
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
import java.util.Scanner;

import model.BattlePoint;
import model.Board;
import model.Main;
import ship.*;

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
    public Main main;
    private Label selectGameLabel;
    private Stage stage;
    private BorderPane borderPane;

    private Button loadButton;

    private int grid;

    public static String[] raw1;

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
            }Scene scene = new Scene(main.createContent(this.stage));
            this.stage.setScene(scene);
            this.stage.show();
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
        File folder = new File("./view/boards");
        List<String> files = new ArrayList<String>();

        // collects all the files in boards folder
        for (File file: Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(".txt")) {
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

        loadBoard("./view/boards/" + board);
    }

    /**
     * Load the board from a file
     *
     * @param boardFile file to load
     * @return loaded a board
     */
    public void loadBoard(String boardFile) throws IOException {
        System.out.println("boardFile: " + boardFile);
        String raw = "";
        main = new Main();
        try {
            File myObj = new File(boardFile);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                raw += data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        raw1 = raw.split("null");
        grid = Integer.parseInt(raw1[0]);

        String[] cships = raw1[1].split("\\.");
        String[] hships = raw1[2].split("\\.");
        main.choice =grid;
        main.running = true;
        main.choice2 = Integer.parseInt(raw1[5]);
        main.shipsComputer = createShips(cships);
        main.shipsHuman = createShips(hships);
        main.createContent(this.stage);

    }
    public Ship[] createShips(String[] ships){
        if (ships.length == 5){
            Ship[] res = new Ship[5];
            int j = 0;
            for (String i: ships){
                String[] coords = i.split(" ");
                boolean vert = Integer.parseInt(coords[coords.length - 2]) == 1;
                BattlePoint startPoint = new BattlePoint(Math.abs(Integer.parseInt(coords[0])), Math.abs(Integer.parseInt(coords[1])));
                BattlePoint endPoint = new BattlePoint(Math.abs(Integer.parseInt(coords[2])), Math.abs(Integer.parseInt(coords[3])));
                if (Integer.parseInt(coords[coords.length - 1]) == 3){
                    Ship newShip = new Destroyer();
                    newShip.setBody(vert, startPoint.x, startPoint.y);
                    res[j] = newShip;
                }else if (Integer.parseInt(coords[coords.length - 1]) == 4){
                    Ship newShip = new Cruiser();
                    newShip.setBody(vert, startPoint.x,startPoint.y);
                    res[j] = newShip;
                }
                else if (Integer.parseInt(coords[coords.length - 1]) == 5){
                    Ship newShip = new Carrier();
                    newShip.setBody(vert,startPoint.x, startPoint.y);
                    res[j] = newShip;
                }
                j++;
            }return res;
        }
        if (ships.length == 3){
            Ship[] res = new Ship[3];
            int j = 0;
            for (String i: ships){
                String[] coords = i.split(" ");
                boolean vert = Integer.parseInt(coords[coords.length - 2]) == 1;
                BattlePoint startPoint = new BattlePoint(Math.abs(Integer.parseInt(coords[0])), Math.abs(Integer.parseInt(coords[1])));
                BattlePoint endPoint = new BattlePoint(Math.abs(Integer.parseInt(coords[2])), Math.abs(Integer.parseInt(coords[3])));
                if (Integer.parseInt(coords[coords.length - 1]) == 3){
                    Ship newShip = new Destroyer();
                    newShip.setBody(vert, Math.min(startPoint.x, endPoint.x), Math.min(endPoint.y, startPoint.y));
                    res[j] = newShip;
                }else if (Integer.parseInt(coords[coords.length - 1]) == 4){
                    Ship newShip = new Cruiser();
                    newShip.setBody(vert, startPoint.x, startPoint.y);
                    res[j] = newShip;
                }
                else if (Integer.parseInt(coords[coords.length - 1]) == 5){
                    Ship newShip = new Carrier();
                    newShip.setBody(vert,startPoint.x,startPoint.y);
                    res[j] = newShip;
                }
                j++;
            }return res;
        }
        return new Ship[3];
    }

}