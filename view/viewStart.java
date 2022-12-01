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
import javafx.stage.Stage;


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
    Button settingsButton, gameStartButton, loadButton;

    private ListView<String> gameModes;

    int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;

    public viewStart(Stage stage){
        this.stage = stage;
        initUI();
    }

    private void initUI() {
        this.width = 100;
        this.height = 100;
        this.borderPane = new BorderPane();

        borderPane.setTop(welcome());
        HBox hbox = setupH();
        borderPane.setBottom(hbox);

        BorderPane root = new BorderPane();
        Image img = new Image("/Images/ship.png");
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
        root.setPrefSize(700, 600);
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

    public GridPane welcome(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // Category in column 2, row 1
        Text title1 = new Text("Welcome to");
        title1.setFill(Color.ORANGE);
        title1.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        grid.add(title1, 1, 0);

        // Title in column 3, row 1
        Text title2 = new Text("BATTLESHIP");
        title2.setFill(Color.ORANGE);
        title2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 60));
        grid.add(title2, 2, 0);

        // Subtitle in columns 2-3, row 2
        Text small = new Text("Choose your game mode:");
        small.setFont(Font.font("Arial", FontWeight.LIGHT, 25));
        small.setFill(Color.BLACK);
        grid.add(small, 1, 1, 2, 1);
        // Checkbox added into the grid pane.
        grid.add(setupV(), 1, 1, 2, 15);
        return grid;
    }

    public HBox setupH(){
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

        settingsButton = new Button("SETTINGS");
        settingsButton.setId("setting");
        settingsButton.setPrefSize(150, 50);
        settingsButton.setFont(new Font(12));
        settingsButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        loadButton = new Button("LOAD GAME");
        loadButton.setId("load");
        loadButton.setPrefSize(150, 50);
        loadButton.setFont(new Font(12));
        loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        hbox.getChildren().addAll(gameStartButton, loadButton, settingsButton);
        return hbox;
    }

    public VBox setupV(){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setId("Game Mode Select:");
        choiceBox.getItems().add("Normal Game (5 v 5)");
        choiceBox.getItems().add("Rush Game (3 v 3)");
        choiceBox.setValue("Normal Game (5 v 5)"); // Default game mode

        VBox screen = new VBox(10);
        screen.setPadding(new Insets(0, 10 , 20, 300));
        screen.getChildren().addAll(choiceBox);
        choiceBox.setOnAction((event) -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            System.out.println("   ChoiceBox.getValue(): " + choiceBox.getValue());
        });
        return screen;
    }
}