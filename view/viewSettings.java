package view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image ;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 *                       Settings
 *
 *       Voice Over For Accessibility: On or Off
 *
 *                      Save Game
 *
 */

public class viewSettings{
    Stage stage;
    BorderPane borderPane;
    Button saveButton;
    public viewSettings(Stage stage){
        this.stage = stage;
        initUI();
    }

    private void initUI() {
        this.borderPane = new BorderPane();

        borderPane.setTop(settings());
        VBox vbox = setupV();
        borderPane.setCenter(vbox);
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
        this.stage.setTitle("SETTINGS");
        // Display the Stage
        this.stage.show();
        //borderPane.setCenter(vbox);
    }

    public GridPane settings(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // Title in column 3, row 1
        Text title2 = new Text("Voice Over For Accessibility:");
        title2.setFill(Color.ORANGE);
        title2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 60));
        grid.add(title2, 2, 0);
        return grid;
    }

    public VBox setupV(){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setId("volume");
        choiceBox.getItems().add("ON");
        choiceBox.getItems().add("OFF");
        choiceBox.setValue("ON"); // Default game mode

        saveButton = new Button("SAVE GAME");
        saveButton.setId("save");
        saveButton.setPrefSize(150, 50);
        saveButton.setFont(new Font(12));
        saveButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");


        VBox screen = new VBox(10);
        screen.setPadding(new Insets(0, 10 , 20, 300));
        screen.getChildren().addAll(choiceBox, saveButton);
        choiceBox.setOnAction((event) -> {
            int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
        });
        saveButton.setOnAction(e->{
            saveDialog();
        });
        return screen;
    }

    public void saveDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("I UNDERSTAND", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setGraphic(saveStatement());
        dialog.getDialogPane().setPadding(new Insets(1, 1, 1, 100));
        dialog.getDialogPane().setMaxSize(1, 2);
        dialog.showAndWait();
    }

    public VBox saveStatement(){
        Text title1 = new Text("PLACEMENT PHASE:");
        title1.setFill(Color.RED);
        title1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
        VBox vbox = new VBox(10, title1);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}
