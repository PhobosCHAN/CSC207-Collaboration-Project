package view;
import javafx.scene.control.Button;

import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.BattleModel;

/**
 * Battleship game UI
 *
 * WELCOME TO
 *          BATTLESHIP!
 *
 * Game Mode Select: Drop down box
 *
 *             SETTINGS
 *            GAME START
 *
 */
public class viewStart {
    Stage stage;
    Canvas canvas;
    BorderPane borderPane;

    Button settingsButton, gameStartButton;

    Label welcomeLabel, gameLabel;

    private ListView<String> gameModes;

    int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;

    public viewStart(Stage stage){
        this.stage = stage;
        initUI();
    }

    private void initUI() {
        this.stage.setTitle("SOC BATTLESHIP GAME");
        this.width = 100;
        this.height = 100;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #2f4f4f;"); //Colour:

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        // He
        //gc = canvas.getGraphicsContext2D();

        //labels
        welcomeLabel.setId("WelcomeLabel");
        gameLabel.setId("GameLabel");

        welcomeLabel.setText("WELCOME TO");
        welcomeLabel.setMinWidth(250);
        welcomeLabel.setFont(new Font(20));
        welcomeLabel.setStyle("-fx-text-fill: #e8e6e3");

        final ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton pilotButtonHuman = new RadioButton("Human");
        pilotButtonHuman.setToggleGroup(toggleGroup);
        pilotButtonHuman.setSelected(true);
        pilotButtonHuman.setUserData(Color.SALMON);
        pilotButtonHuman.setFont(new Font(16));
        pilotButtonHuman.setStyle("-fx-text-fill: #e8e6e3");

        RadioButton pilotButtonComputer = new RadioButton("Computer (Default)");
        pilotButtonComputer.setToggleGroup(toggleGroup);
        pilotButtonComputer.setUserData(Color.SALMON);
        pilotButtonComputer.setFont(new Font(16));
        pilotButtonComputer.setStyle("-fx-text-fill: #e8e6e3");

        gameLabel.setText("BATTLESHIP");
        gameLabel.setFont(new Font(20));
        gameLabel.setStyle("-fx-text-fill: #e8e6e3");

        //add buttons
        gameStartButton = new Button("GAME START");
        gameStartButton.setId("Game Start");
        gameStartButton.setPrefSize(150, 50);
        gameStartButton.setFont(new Font(12));
        gameStartButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        settingsButton = new Button("SETTINGS");
        settingsButton.setId("Settings");
        settingsButton.setPrefSize(150, 50);
        settingsButton.setFont(new Font(12));
        settingsButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }
}
