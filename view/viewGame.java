package view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image ;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import model.Main;
import model.Board;
import ship.Ship;

import java.util.ArrayList;

/**
 * Contains all the neccessary views to present through Java FX.
 *
 * Instructions
 */

public class viewGame{
    Main main;
    Stage stage;
    Button placementButton, shootingButton, saveButton, settingsButton;

    public GridPane grid;

    private Ship[] ships;
    public viewGame(Stage stage, Main main){
        this.main = main;
        this.stage = stage;
        this.ships = main.getShipsHuman();
    }

    public void instructions1(int ships){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("I UNDERSTAND", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setGraphic(placementIns(ships));
        dialog.getDialogPane().setPadding(new Insets(1, 1, 1, 100));
        dialog.getDialogPane().setMaxSize(1, 2);
        dialog.showAndWait();
    }

    public void instructions2(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("I UNDERSTAND", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setGraphic(shootingIns());
        dialog.getDialogPane().setPadding(new Insets(1, 1, 1, 100));
        dialog.getDialogPane().setMaxSize(1, 2);
        dialog.showAndWait();
    }


    public VBox placementIns(int ships){
        Text title1 = new Text("PLACEMENT PHASE:");
        title1.setFill(Color.RED);
        title1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
        Text title2 = new Text("RIGHT CLICK to place horizontally.");
        title2.setFill(Color.BLACK);
        title2.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Image image = new Image("horizontal.png");
        Text title3 = new Text("LEFT CLICK to place vertically.");
        title3.setFill(Color.BLACK);
        title3.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Image image2 = new Image("vertical.png");
        Text count = new Text("You can place " + ships + " ships!");
        count.setFill(Color.BLACK);
        count.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        VBox vbox = new VBox(10, title1, title2, new ImageView(image), title3, new ImageView(image2), count);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public VBox shootingIns(){
        Text title1 = new Text("SHOOTING PHASE:");
        title1.setFill(Color.RED);
        title1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
        Text title2 = new Text("Click on the opposite grid (Top Grid) to shoot the enemy's ship");
        title2.setFill(Color.BLACK);
        title2.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Image image = new Image("miss.png");
        Text title3 = new Text("Means MISS");
        title3.setFill(Color.BLACK);
        title3.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Image image2 = new Image("hit.png");
        Text title4 = new Text("Means HIT (You are allowed to shoot again until you miss)");
        title4.setFill(Color.BLACK);
        title4.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        VBox vbox = new VBox(10, title1, title2, new ImageView(image), title3, new ImageView(image2), title4);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public VBox leftButtons(int choice){
        Text title1 = new Text("INSTRUCTIONS");
        title1.setFill(Color.GREEN);
        title1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 25));

        placementButton = new Button("How to place ships?");
        placementButton.setId("placement");
        placementButton.setPrefSize(180, 50);
        placementButton.setFont(new Font(12));
        placementButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        shootingButton = new Button("How to shoot ships?");
        shootingButton.setId("save");
        shootingButton.setPrefSize(180, 50);
        shootingButton.setFont(new Font(12));
        shootingButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        VBox vbox = new VBox(10, title1, placementButton, shootingButton);
        placementButton.setOnAction(e->{
            if (choice == 10){
                instructions1(5);
                this.stage.requestFocus();
            }
            else if (choice == 7){
                instructions1(3);
                this.stage.requestFocus();
            }
        });
        shootingButton.setOnAction(e->{
            if (choice == 10){
                instructions2();
                this.stage.requestFocus();
            }
            else if (choice == 7){
                instructions2();
                this.stage.requestFocus();
            }
        });
        String border = """
            
                -fx-background-color: BURLYWOOD;
                """;
        vbox.setStyle(border);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public VBox rightInteractive(int choice){
        this.grid = createBoard(choice);
        Text title1 = new Text("HP BAR");
        title1.setFill(Color.GREEN);
        title1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 25));
        // There is a safe button, settings button and HP counter
        saveButton = new Button("SAVE");
        saveButton.setId("save");
        saveButton.setPrefSize(180, 50);
        saveButton.setFont(new Font(12));
        saveButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        settingsButton = new Button("SETTINGS");
        settingsButton.setId("settings");
        settingsButton.setPrefSize(180, 50);
        settingsButton.setFont(new Font(12));
        settingsButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        saveButton.setOnAction(e->{
        });
        settingsButton.setOnAction(e->{
        });
        String border = """
                -fx-border-color: red;
                -fx-border-insets: 1;
                -fx-border-width: 3;
                -fx-border-style: dashed;
                """;
        VBox hp = new VBox(this.grid); // add interactive hp bar in the bracket.
        hp.setStyle(border);
        VBox vbox = new VBox(10,title1, hp,saveButton,settingsButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 2, 1, 10));
        return vbox;
    }
    public GridPane createBoard(int choice) {
        int grid = 9;
        if (choice == 10){
            grid = 9;
        }
        else{
            grid = 5;
        }
        GridPane hpBoard = new GridPane();
        hpBoard.setPrefSize(20, 20);

        for (int y = 0; y < grid; y++) {
            for (int x = 0; x < 6; x++) {
                Rectangle name = new Rectangle(65, 30);
                name.setFill(Color.BURLYWOOD);
                name.setStroke(Color.BLACK);
                Rectangle tile = new Rectangle(39, 30);
                tile.setFill(Color.BURLYWOOD);
                tile.setStroke(Color.BLACK);
                if (x == 0 & y == 0 & choice == 10){
                    Text text = new Text("Carrier");
                    text.setFont(Font.font(15));
                    hpBoard.add(new StackPane(name, text), x, y);
                }
                else if (x == 0 & y == 2 & choice == 10){
                    Text text2 = new Text("Cruiser");
                    text2.setFont(Font.font(15));
                    hpBoard.add(new StackPane(name, text2), x, y);
                }
                else if (x == 0 & y == 4 & choice == 10){
                    Text text3 = new Text("Cruiser");
                    text3.setFont(Font.font(15));
                    hpBoard.add(new StackPane(name, text3), x, y);
                }
                else if (x == 0 & y == 6 & choice == 10){
                    Text text4 = new Text("Destroyer");
                    text4.setFont(Font.font(15));
                    hpBoard.add(new StackPane(name, text4), x, y);
                }
                else if (x == 0 & y == 8 & choice == 10){
                    Text text5 = new Text("Destroyer");
                    text5.setFont(Font.font(15));
                    hpBoard.add(new StackPane(name, text5), x, y);
                }
                else if (x == 0 & y == 0 & choice == 7){
                    Text text3 = new Text("Cruiser");
                    text3.setFont(Font.font(15));
                    hpBoard.add(new StackPane(name, text3), x, y);
                }
                else if (x == 0 & y == 2 & choice == 7){
                    Text text4 = new Text("Destroyer");
                    text4.setFont(Font.font(15));
                    hpBoard.add(new StackPane(name, text4), x, y);
                }
                else if (x == 0 & y == 4 & choice == 7){
                    Text text5 = new Text("Destroyer");
                    text5.setFont(Font.font(15));
                    hpBoard.add(new StackPane(name, text5), x, y);
                }
                else{
                    hpBoard.add(new StackPane(tile, new Text("")), x, y);
                }
            }
        }
        painthp(hpBoard, choice);
        return hpBoard;
    }

    public void painthp(GridPane grid, int choice) {
        for (int y = 0; y < grid.getColumnCount(); y++) {
            for (int x = 0; x < grid.getRowCount(); x++){
                if (choice == 10){
                    if (x == 0 & y > 0 ){
                        getChildByRowColumn(grid, x, y).getChildren().get(0).setStyle("-fx-fill: grey;");
                    }
                    if (x == 2 & y > 0 & y < 5 ){
                        getChildByRowColumn(grid, x, y).getChildren().get(0).setStyle("-fx-fill: grey;");
                    }
                    if (x == 4 & y > 0 & y < 5){
                        getChildByRowColumn(grid, x, y).getChildren().get(0).setStyle("-fx-fill: grey;");
                    }
                    if (x == 6 & y > 0 & y < 4){
                        getChildByRowColumn(grid, x, y).getChildren().get(0).setStyle("-fx-fill: grey;");
                    }
                    if (x == 8 & y > 0 & y < 4){
                        getChildByRowColumn(grid, x, y).getChildren().get(0).setStyle("-fx-fill: grey;");
                    }
                }
                if (choice == 7){
                    if (x == 0 & y > 0 & y < 5){
                        getChildByRowColumn(grid, x, y).getChildren().get(0).setStyle("-fx-fill: grey;");
                    }
                    if (x == 2 & y > 0 & y < 4){
                        getChildByRowColumn(grid, x, y).getChildren().get(0).setStyle("-fx-fill: grey;");
                    }
                    if (x == 4 & y > 0 & y < 4){
                        getChildByRowColumn(grid, x, y).getChildren().get(0).setStyle("-fx-fill: grey;");
                    }
                }
            }
        }
    }

    public void deductHp(boolean shot, GridPane grid, int choice) {
        for (int ship = 0; ship < this.main.getShipsHuman().length; ship += 1) {
            if (choice == 10) {
                if (shot & this.main.getShipsHuman()[ship].getHP() < 5 & ship == 0) {
                    System.out.println("This" + this.main.getShipsHuman()[ship].getHP());
                    getChildByRowColumn(grid, 0, (5 - this.main.getShipsHuman()[ship].getHP())).getChildren().get(0).setStyle("-fx-fill: red;");
                }
                if (shot & this.main.getShipsHuman()[ship].getHP() < 4 & ship == 1) {
                    getChildByRowColumn(grid, 2, (4 - this.main.getShipsHuman()[ship].getHP())).getChildren().get(0).setStyle("-fx-fill: red;");
                }
                if (shot & this.main.getShipsHuman()[ship].getHP() < 4 & ship == 2) {
                    getChildByRowColumn(grid, 4, (4 - this.main.getShipsHuman()[ship].getHP())).getChildren().get(0).setStyle("-fx-fill: red;");
                }
                if (shot & this.main.getShipsHuman()[ship].getHP() < 3 & ship == 3) {
                    getChildByRowColumn(grid, 6, (3 - this.main.getShipsHuman()[ship].getHP())).getChildren().get(0).setStyle("-fx-fill: red;");
                }
                if (shot & this.main.getShipsHuman()[ship].getHP() < 3 & ship == 4) {
                    getChildByRowColumn(grid, 8, (3 - this.main.getShipsHuman()[ship].getHP())).getChildren().get(0).setStyle("-fx-fill: red;");
                }
            }
            if (choice == 7) {
                if (shot & this.main.getShipsHuman()[ship].getHP() < 4 & ship == 0) {
                    getChildByRowColumn(grid, 0, (4 - this.main.getShipsHuman()[ship].getHP())).getChildren().get(0).setStyle("-fx-fill: red;");
                }
                if (shot & this.main.getShipsHuman()[ship].getHP() < 3 & ship == 1) {
                    getChildByRowColumn(grid, 2, (3 - this.main.getShipsHuman()[ship].getHP())).getChildren().get(0).setStyle("-fx-fill: red;");
                }
                if (shot & this.main.getShipsHuman()[ship].getHP() < 3 & ship == 2) {
                    getChildByRowColumn(grid, 4, (3 - this.main.getShipsHuman()[ship].getHP())).getChildren().get(0).setStyle("-fx-fill: red;");
                }
            }
        }
    }
    public static StackPane getChildByRowColumn(GridPane gridPane, int x, int y){

        for(final Node node : gridPane.getChildren()){
            if (GridPane.getRowIndex(node) == null) continue ;
            if(GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) return (StackPane) node;
        }
        return null;
    }

    public GridPane layout(int choice){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        HBox hbox1 = null;
        HBox hbox2 = null;
        if (choice == 10){
            hbox1 = new HBox(23,  new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"), new Text("7"), new Text("8"), new Text("9"));
            hbox2 = new HBox(23,  new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"), new Text("7"), new Text("8"), new Text("9"));
            hbox1.setPadding(new Insets(10));
            hbox2.setPadding(new Insets(10));
        } else if (choice == 7) {
            hbox1 = new HBox(23,  new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"));
            hbox2 = new HBox(23,  new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"));
            hbox1.setPadding(new Insets(10));
            hbox2.setPadding(new Insets(10));
        }
        VBox vbox = new VBox(0, hbox1, main.getEnemyBoard(), hbox2, main.getPlayerBoardBoard());
        VBox part1 = null;
        VBox part2 = null;
        if (choice == 10){
            part1 = new VBox(15, new Text("  "), new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"), new Text("7"), new Text("8"), new Text("9"));
            part2 = new VBox(15, new Text("  "), new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"), new Text("7"), new Text("8"), new Text("9"));
        } else if (choice == 7) {
            part1 = new VBox(14.5, new Text("  "), new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"));
            part2 = new VBox(15, new Text(" "), new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"));
        }
        VBox vbox2 = new VBox(5, part1, new Text(" "),part2);
        vbox2.setPadding(new Insets(15, 1, 5, 1));
        HBox hbox = new HBox(10, vbox2, vbox);
        grid.setAlignment(Pos.CENTER);
        grid.add(hbox, 1, 0);
        return grid;
    }
}