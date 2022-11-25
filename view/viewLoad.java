package view;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.BattleModel;

import java.io.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Load File View
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class LoadView {

    private viewGame game;
    private Label chosenBoardLabel;
    private Button chosenBoardButton;
    private ListView<String> gamesList;


    /**
     * Constructor
     *
     * @param game master view
     */
    public LoadView (viewGame game) {
        game.paused = true;
        this.game = game;
        chosenBoardLabel = new Label(String.format("Currently playing: Default Board"));
        gamesList = new ListView<>(); //list of tetris.boards

        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(game.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        chosenBoardLabel.setId("CurrentBoard"); // DO NOT MODIFY ID

        gamesList.setId("BoardsList");  // DO NOT MODIFY ID
        gamesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        getFiles(gamesList); //get files for file selector

        chosenBoardButton = new Button("Change board");
        chosenBoardButton.setId("ChangeBoard"); // DO NOT MODIFY ID

        //on selection, do something
        chosenBoardButton.setOnAction(e -> {
            try {
                selectBoard(chosenBoardLabel, gamesList);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox selectBoardBox = new VBox(10, chosenBoardLabel, gamesList, chosenBoardButton);

        // Default styles which can be modified
        gamesList.setPrefHeight(100);

        chosenBoardLabel.setStyle("-fx-text-fill: #e8e6e3");
        chosenBoardLabel.setFont(new Font(16));

        chosenBoardButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        chosenBoardButton.setPrefSize(200, 50);
        chosenBoardButton.setFont(new Font(16));

        selectBoardBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(selectBoardBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> {
            game.paused = false;
        });
    }

    /**
     * Populate the listView with all the .SER files in the boards directory
     *
     * @param listView ListView to update
     * @return the index in the listView of Stater.ser
     */
    private void getFiles(ListView<String> listView) {
        File directory = new File("Assignment2/boards");
        File [] lst = directory.listFiles();
        ObservableList<String> items = observableArrayList();
        for (File item: lst){
            items.add(item.getName());
        }
        listView.setItems(items);
    }

    /**
     * Select and load the board file selected in the boardsList and update selectBoardLabel with the name of the new Board file
     *
     * @param selectBoardLabel a message Label to update which board is currently selected
     * @param boardsList a ListView populated with tetris.boards to load
     */
    private void selectBoard(Label selectBoardLabel, ListView<String> boardsList) throws IOException {
        ObservableList<String> lst = boardsList.getItems();
        for (int num = 0; num<lst.size(); num+=1){
            if (boardsList.getSelectionModel().getSelectedIndex() == -1){
                game.model = loadBoard("Stack_Overload_Certified-CSC207/boards/" + lst.get(0));
                selectBoardLabel.setId(lst.get(0));
            }
            else{
                System.out.println(lst.get(boardsList.getSelectionModel().getSelectedIndex()));
                game.model = loadBoard("Stack_Overload_Certified-CSC207/boards/" + lst.get(gamesList.getSelectionModel().getSelectedIndex()));
                selectBoardLabel.setId(lst.get(boardsList.getSelectionModel().getSelectedIndex()));
            }
        }
    }

    /**
     * Load the board from a file
     *
     * @param boardFile file to load
     * @return loaded Tetris Model
     */
    public BattleModel loadBoard(String boardFile) throws IOException {
        System.out.println("boardFile: " + boardFile);

        // Reading the object from a file
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(boardFile);
            in = new ObjectInputStream(file);
            return (BattleModel) in.readObject();
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

