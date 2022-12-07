package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image ;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import model.Main;

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
    viewGame game;
    Main main;
    Stage stage;
    BorderPane borderPane;
    Button gameStartButton, loadButton;

    int choice; // Let 10 represent game mode (5v5) and 7 represent game mode (3v3)
    int choice2; // Let 1 represent voiceover being on and 2 represent voiceover being of

    /**
     * The constructor for viewStart.
     * @param stage - Takes the current stage of the game.
     * @param main - Takes the current main of the game.
     */
    public viewStart(Stage stage, Main main){
        this.game = new viewGame(stage, main);
        this.main = main;
        this.stage = stage;
        this.choice = 10;
        this.choice2 = 1;
        initUI();
    }

    /**
     * A method to show all the javafx designs
     */
    private void initUI() {
        this.borderPane = new BorderPane();

        borderPane.setTop(welcome());
        HBox hbox = setupH();
        borderPane.setBottom(hbox);

        BorderPane root = new BorderPane();
        Image img = new Image("/Images/sea.png");
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
        //borderPane.setStyle("-fx-background-color: #2f4f4f;");
        root.setCenter(borderPane);
        // Set the Size of the VBox
        root.setPrefSize(900, 538);
        // Set the Style-properties of the BorderPane
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        Scene scene = new Scene(root);
        // Add the scene to the Stage
        this.stage.setScene(scene);
        // Set the title of the Stage (Window of the Game)
        this.stage.setTitle("SOC BATTLESHIP GAME");
        // Display the Stage
        this.stage.show();
        //borderPane.setCenter(vbox);
    }

    /**
     * A gridpane created to show the welcome screen.
     */
    private GridPane welcome(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // Category in column 2, row 1
        Text title1 = new Text("Welcome to");
        title1.setFill(Color.ORANGE);
        title1.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        grid.add(title1, 1, 0);

        // Title in column 3, row 1
        Text title2 = new Text("BATTLESHIP");
        title2.setFill(Color.ORANGE);
        title2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 70));
        grid.add(title2, 2, 0);

        // Subtitle in columns 2-3, row 2
        Text small = new Text("Choose your game mode:");
        small.setFont(Font.font("Arial", FontWeight.LIGHT, 25));
        small.setFill(Color.BLACK);
        grid.add(small, 1, 2, 2, 2);

        Text small2 = new Text("Accessibility Voice Over: ");
        small2.setFont(Font.font("Arial", FontWeight.LIGHT, 25));
        small2.setFill(Color.BLACK);
        grid.add(small2, 1, 5, 2, 5);
        // Checkbox added into the grid pane.
        grid.add(setupV(), 1, 1, 2, 10);
        return grid;
    }

    /**
     * A HBox created to show all the titles and buttons.
     */
    private HBox setupH(){
        HBox hbox = new HBox();
        //v is for the top adjustment, v2 is the bottom adjustment
        hbox.setPadding(new Insets(10, 20, 10, 20));
        hbox.setSpacing(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #336699;");

        gameStartButton = new Button("GAME START");
        gameStartButton.setId("start");
        gameStartButton.setPrefSize(150, 50);
        gameStartButton.setFont(new Font(12));
        gameStartButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        loadButton = new Button("LOAD GAME");
        loadButton.setId("load");
        loadButton.setPrefSize(150, 50);
        loadButton.setFont(new Font(12));
        loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        gameStartButton.setOnAction(e ->{
            Scene scene = new Scene(main.createContent(this.choice, this.choice2, this.stage));
            this.stage.setTitle("Playing Battleship");
            this.stage.setScene(scene);
            this.stage.setResizable(false);
            this.stage.show();
            if (this.choice == 10){
                game.instructions1(5);
                this.stage.requestFocus();
            }
            else if (this.choice == 7){
                game.instructions1(3);
                this.stage.requestFocus();
            }
        });

        loadButton.setOnAction(e ->{
            viewLoad load =  new viewLoad(this.stage);

        });

        hbox.getChildren().addAll(gameStartButton, loadButton);
        return hbox;
    }

    /**
     * A VBox created to show the choice boxes and buttons.
     */
    private VBox setupV(){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setId("Game Mode Select:");
        choiceBox.getItems().add("Normal Game (5 v 5)");
        choiceBox.getItems().add("Rush Game (3 v 3)");
        choiceBox.setValue("Normal Game (5 v 5)"); // Default game mode

        ChoiceBox<String> accessibilityBox = new ChoiceBox<>();
        accessibilityBox.setId("voiceover");
        accessibilityBox.getItems().add("ON");
        accessibilityBox.getItems().add("OFF");
        accessibilityBox.setValue("ON"); // Default game mode

        VBox screen = new VBox(10);
        screen.setSpacing(28);
        screen.setPadding(new Insets(10, 15 , 30, 300));
        screen.getChildren().addAll(choiceBox, accessibilityBox);
        choiceBox.setOnAction((event) -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            if (selectedIndex == 0){
                this.choice = 10;
            }
            else if(selectedIndex == 1){
                this.choice = 7;
            }
        });
        accessibilityBox.setOnAction((event) -> {
            int selectedChoice = accessibilityBox.getSelectionModel().getSelectedIndex();
            Object selectedOption = accessibilityBox.getSelectionModel().getSelectedItem();
            if (selectedChoice == 0){
                this.choice2 = 1;
            }
            else if(selectedChoice == 1){
                this.choice2 = 0;
            }
        });
        return screen;
    }
}