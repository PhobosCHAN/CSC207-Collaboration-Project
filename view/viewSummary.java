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

import model.Board;
import model.Main;

/**
 * Happens right after the last ship of a player is hit, this pop up will show:
 *  - which player won
 *  - how many shots fired
 *  - a close button (be lead to the leading page), a play again button (leads back to the original front page)
 *  - game-mode label
 *  -
 *
 *
 *   Game Mode =  ************
 *   Winner: PLAYER 1
 *   Player's Accuracy For The Game:
 *   Computer's Accuracy For The Game:
 *
 *   Ships Lost by Player 1:
 *   - Cruiser x 2
 *   - Destroyer x 2
 *   - ..............
 *          CLOSE
 *        PLAY AGAIN
 */
public class viewSummary {
    Main main;
    Image image;
    Stage stage;
    BorderPane borderPane;
    Button closeButton, playAgainButton, proceedButton;
    private int choice;
    private int winner;
    private int choice2;

    public viewSummary(Stage stage, int winner, Main main, int choice, int choice2){
        this.stage = stage;
        this.winner = winner;
        this.main = main;
        this.choice = choice;
        this.choice2 = choice2;
        initUI();
    }

    private void initUI() {
        this.borderPane = new BorderPane();

        proceedButton = new Button("PROCEED");
        proceedButton.setId("proceed");
        proceedButton.setPrefSize(150, 50);
        proceedButton.setFont(new Font(12));
        proceedButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox b1 = new HBox(20,proceedButton);
        b1.setPadding(new Insets(20, 20, 20, 20));
        b1.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(b1);
        BorderPane root = new BorderPane();
        if (winner == 1){
            this.image = new Image("win.png");
        }
        else{
            this.image = new Image("lose.png");
        }
        BackgroundImage bImg = new BackgroundImage(this.image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        proceedButton.setOnAction(e ->{
            stats();
        });
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
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
        this.stage.setTitle("CREDITS PAGE");
        // Display the Stage
        this.stage.show();
        //borderPane.setCenter(vbox);
    }

    public void stats(){
        //BorderPane stats = new BorderPane();
        this.borderPane.setCenter(summary());
        this.borderPane.setBottom(setupV());
        BorderPane root2 = new BorderPane();
        //root2.getChildren().add(stats);
        Image img = new Image("ship.png");
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root2.setBackground(bGround);
        root2.setCenter(borderPane);
        // Set the Size of the VBox
        root2.setPrefSize(900, 538);
        // Set the Style-properties of the BorderPane
        root2.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        Scene scene2 = new Scene(root2);
        this.stage.setScene(scene2);
        // Set the title of the Stage (Window of the Game)
        this.stage.setTitle("SUMMARY PAGE");
        // Display the Stage
        this.stage.show();
        //borderPane.setCenter(vbox);
    }
    public GridPane summary(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        Text title1 = new Text("Game Mode: " + main.getGameMode()); //  wanna add + getGameMode()
        title1.setFill(Color.ORANGE);
        title1.setFont(Font.font("Arial", FontWeight.LIGHT, 30));
        grid.add(title1, 1, 5);

        Text title2 = new Text("Winner: " + main.getWinner()); // want to add which player won
        title2.setFill(Color.ORANGE);
        title2.setFont(Font.font("Arial", FontWeight.LIGHT, 30));
        grid.add(title2, 1, 6);

        // Subtitle in columns 2-3, row 2
        Text small = new Text("Player's Accuracy For The Entire Game: " + main.getHumanAccuracy() + "%") ; //+ calculatePaccuracy
        small.setFont(Font.font("Arial", FontWeight.LIGHT, 30));
        small.setFill(Color.ORANGE);
        grid.add(small, 1, 7);
        Text small2 = new Text("Computer's Accuracy For The Entire Game: " + main.getComputerAccuracy() + "%"); //+ calculateCaccuracy
        small2.setFont(Font.font("Arial", FontWeight.LIGHT, 30));
        small2.setFill(Color.ORANGE);
        grid.add(small2, 1, 8);
        return grid;
    }

    public VBox setupV(){
        closeButton = new Button("CLOSE");
        closeButton.setId("close");
        closeButton.setPrefSize(150, 50);
        closeButton.setFont(new Font(12));
        closeButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.setId("playA");
        playAgainButton.setPrefSize(150, 50);
        playAgainButton.setFont(new Font(12));
        playAgainButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        closeButton.setOnAction(e ->{
            this.main = new Main();
            viewStart start = new viewStart(this.stage, this.main);
        });

        playAgainButton.setOnAction(e ->{
            this.main = new Main();
            Scene scene = new Scene(this.main.createContent(this.choice, this.choice2, this.stage));
            this.stage.setTitle("Playing Battleship");
            this.stage.setScene(scene);
            this.stage.setResizable(false);
            this.stage.show();
        });
        VBox screen = new VBox(10);
        screen.setAlignment(Pos.BOTTOM_CENTER);;
        screen.getChildren().addAll(playAgainButton, closeButton);
        return screen;
    }

}
