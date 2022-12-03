package model;

import java.awt.*;
import java.util.Random;
import view.viewSummary;

import view.viewStart;
import Player.Player;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import model.Board.Cell;
import ship.Ship;
import ship.ShipFactory;

public class Main extends Application {

    Stage stage;
    private boolean running = false;
    private model.Board enemyBoard, playerBoard;

    private Ship[] shipsHuman;
    private Ship[] shipsComputer;

    private int currentShipIndex;
    private int ComputerCurrentIndex;

    private int lastX, lastY;

    private int choice;

    private boolean enemyTurn = false;

    Player human;
    Player computer;

    private Random random = new Random();

    public void populatePlayerShips(int choice){
        this.choice = choice;
        currentShipIndex = 0;
        ComputerCurrentIndex = 0;
        ShipFactory shipFactory = new ShipFactory();
        if (choice == 1){
            Ship ship1 = shipFactory.getShip(5, new BattlePoint[0], true);
            Ship ship2 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship3 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship4 = shipFactory.getShip(3, new BattlePoint[0], true);
            Ship ship5 = shipFactory.getShip(3, new BattlePoint[0], true);
            shipsHuman = new Ship[5];
            shipsHuman[0] = ship1;
            shipsHuman[1] = ship2;
            shipsHuman[2] = ship3;
            shipsHuman[3] = ship4;
            shipsHuman[4] = ship5;
            shipsComputer = new Ship[5];
            shipsComputer[0] = ship1;
            shipsComputer[1] = ship2;
            shipsComputer[2] = ship3;
            shipsComputer[3] = ship4;
            shipsComputer[4] = ship5;
        } else if (choice == 2) { // Configured: For the 3v3 game mode.
            Ship ship3 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship4 = shipFactory.getShip(3, new BattlePoint[0], true);
            Ship ship5 = shipFactory.getShip(3, new BattlePoint[0], true);
            shipsHuman = new Ship[3];
            shipsHuman[0] = ship3;
            shipsHuman[1] = ship4;
            shipsHuman[2] = ship5;
            shipsComputer = new Ship[3];
            shipsComputer[0] = ship3;
            shipsComputer[1] = ship4;
            shipsComputer[2] = ship5;
        }
    }

    public Parent createContent(int choice, Stage stage) {
        this.stage = stage;
        this.populatePlayerShips(choice);
        human = new Player(shipsHuman, false);
        computer = new Player(shipsComputer, false);
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 700);
        //add a new node here to show ships hp
        root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));

        enemyBoard = new Board(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.shoot(computer);

            if (enemyBoard.ships == 0) {
                viewSummary summary = new viewSummary(this.stage, 1, this, this.choice);
                System.out.println("YOU WIN");
                //System.exit(0);
            }
            if (enemyTurn){
                enemyMove();}
        });
        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            boolean vert = event.getButton() == MouseButton.PRIMARY;
            shipsHuman[currentShipIndex].setVertical(vert);
            shipsHuman[currentShipIndex].setBody(vert, cell.x, cell.y);
            if (playerBoard.placeShip(shipsHuman[currentShipIndex], cell.x, cell.y)) {
                if ((currentShipIndex + 1) == 5 & choice == 1) {
                    startGame();
                }
                // Configured: For the 3v3 game mode.
                else if ((currentShipIndex + 1) == 3 & choice == 2){
                    startGame();
                }
                currentShipIndex++;
            }

        });

        VBox vbox = new VBox(50, enemyBoard, playerBoard);
        vbox.setAlignment(Pos.CENTER);

        root.setCenter(vbox);

        return root;
    }

    private void enemyMove() {
        while (enemyTurn) {

            if (!playerBoard.getCell(lastX, lastY).getFill().equals(Color.RED)){
                StrategyMiss strategy = new StrategyMiss();
                int[] val = strategy.execute(human, playerBoard, lastX, lastY);
                if(val[0] == 0)
                    enemyTurn = false;
                else
                    enemyTurn = true;
                lastX = val[1];
                lastY = val[2];
                System.out.println(lastX + " " + lastY);
            }
            else{
                StrategyHit strategy = new StrategyHit();
                int[] val = strategy.execute(human, playerBoard, lastX, lastY);
                if(val[0] == 0)
                    enemyTurn = false;
                else
                    enemyTurn = true;
                lastX = val[1];
                lastY = val[2];
            }
            if (playerBoard.ships == 0) {
                viewSummary summary = new viewSummary(this.stage, 2, this, this.choice); // Let 1 represent Player and 2 represent Computer
                System.out.println("YOU LOSE");
                //System.exit(0);
            }

        }
    }

    private void startGame() {
        // place enemy ships
        // Configured: For the 3v3 game mode. From < 5 to < shipsComputer.length
        while(ComputerCurrentIndex < shipsComputer.length){
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            boolean vert = Math.random() < 0.5;
            shipsComputer[ComputerCurrentIndex].setVertical(vert);
            shipsComputer[ComputerCurrentIndex].setBody(vert, x, y);
            if (enemyBoard.placeShip(shipsComputer[ComputerCurrentIndex], x, y)) {
                ComputerCurrentIndex++;
            }
        }

        running = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        viewStart start = new viewStart(primaryStage, this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}