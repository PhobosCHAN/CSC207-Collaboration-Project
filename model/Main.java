package model;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

import static java.lang.Thread.sleep;

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

    private String winner;

    Player human;
    Player computer;

    private Random random = new Random();

    public model.Board getEnemyBoard(){
        return this.enemyBoard;
    }

    public model.Board getPlayerBoardBoard(){
        return this.playerBoard;
    }

    public void setEnemyBoard(model.Board board){
        this.enemyBoard = board;
    }

    public void setPlayerBoard(model.Board board){
        this.playerBoard = board;
    }

    public void populatePlayerShips(int choice){
        this.choice = choice;
        currentShipIndex = 0;
        ComputerCurrentIndex = 0;
        ShipFactory shipFactory = new ShipFactory();
        if (choice == 10){
            Ship ship1 = shipFactory.getShip(5, new BattlePoint[0], true);
            Ship ship2 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship3 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship4 = shipFactory.getShip(3, new BattlePoint[0], true);
            Ship ship5 = shipFactory.getShip(3, new BattlePoint[0], true);
            Ship ship6 = shipFactory.getShip(5, new BattlePoint[0], true);
            Ship ship7 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship8 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship9 = shipFactory.getShip(3, new BattlePoint[0], true);
            Ship ship10 = shipFactory.getShip(3, new BattlePoint[0], true);
            shipsHuman = new Ship[5];
            shipsHuman[0] = ship1;
            shipsHuman[1] = ship2;
            shipsHuman[2] = ship3;
            shipsHuman[3] = ship4;
            shipsHuman[4] = ship5;
            shipsComputer = new Ship[5];
            shipsComputer[0] = ship6;
            shipsComputer[1] = ship7;
            shipsComputer[2] = ship8;
            shipsComputer[3] = ship9;
            shipsComputer[4] = ship10;
        } else if (choice == 7) { // Configured: For the 3v3 game mode.
            Ship ship3 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship4 = shipFactory.getShip(3, new BattlePoint[0], true);
            Ship ship5 = shipFactory.getShip(3, new BattlePoint[0], true);
            Ship ship8 = shipFactory.getShip(4, new BattlePoint[0], true);
            Ship ship9 = shipFactory.getShip(3, new BattlePoint[0], true);
            Ship ship10 = shipFactory.getShip(3, new BattlePoint[0], true);
            shipsHuman = new Ship[3];
            shipsHuman[0] = ship3;
            shipsHuman[1] = ship4;
            shipsHuman[2] = ship5;
            shipsComputer = new Ship[3];
            shipsComputer[0] = ship8;
            shipsComputer[1] = ship9;
            shipsComputer[2] = ship10;
        }
    }

    public Parent createContent(int choice, Stage stage) {
        this.stage = stage;
        this.populatePlayerShips(choice);
        human = new Player("human", shipsHuman, this.choice == 7);
        computer = new Player("computer", shipsComputer, this.choice == 7);
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 700);
        //add a new node here to show ships hp
        root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));
        enemyBoard = new Board(true, choice, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;
            this.human.setTotalShots();
            enemyTurn = !cell.shoot(computer);
            if(!enemyTurn)
                this.human.setHits();
            if (computer.getHp() == 0) {
                viewSummary summary = new viewSummary(this.stage, 1, this, this.choice);
                winner = "Human";
            }
            if (enemyTurn){
                try {
                    enemyMove();
                } catch (InterruptedException ignored) {
                }
            }
        });
        playerBoard = new Board(false, choice, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            boolean vert = event.getButton() == MouseButton.PRIMARY;
            shipsHuman[currentShipIndex].setVertical(vert);
            shipsHuman[currentShipIndex].setBody(vert, cell.x, cell.y);
            if (playerBoard.placeShip(shipsHuman[currentShipIndex], cell.x, cell.y)) {
                if ((currentShipIndex + 1) == 5 & choice == 10) {
                    startGame();
                }
                // Configured: For the 3v3 game mode.
                else if ((currentShipIndex + 1) == 3 & choice == 7){
                    startGame();
                }
                currentShipIndex++;
            }
        });root.setCenter(layout());
        return root;
    }
    public GridPane layout(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        Text text = null;
        Text text2 = null;
        if (choice == 10){
            text = new Text("    0         1         2         3         4         5         6         7        8        9");
            text2 = new Text("    0         1         2         3         4         5         6         7        8        9");
        } else if (choice == 7) {
            text = new Text("    0         1         2         3         4         5         6");
            text2 = new Text("    0         1         2         3         4         5         6");
        }
        VBox vbox = new VBox(10, text, enemyBoard, text2, playerBoard);
        VBox part1 = null;
        VBox part2 = null;
        if (choice == 10){
            part1 = new VBox(15, new Text("  "), new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"), new Text("7"), new Text("8"), new Text("9"));
            part2 = new VBox(15, new Text("  "), new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"), new Text("7"), new Text("8"), new Text("9"));
        } else if (choice == 7) {
            part1 = new VBox(15, new Text("  "), new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"));
            part2 = new VBox(15, new Text("  "), new Text("0"), new Text("1"),new Text("2"),new Text("3"),new Text("4"),new Text("5"),
                    new Text("6"));
        }
        VBox vbox2 = new VBox(5, part1, new Text(""), part2);
        HBox hbox = new HBox(10, vbox2, vbox);
        grid.add(hbox, 15, 0);
        return grid;
    }

    private void enemyMove() throws InterruptedException {
        while (enemyTurn) {
            if (!playerBoard.getCell(lastX, lastY).getFill().equals(Color.RED)){
                StrategyMiss strategy = new StrategyMiss();
                int[] val = strategy.execute(human, playerBoard, lastX, lastY);
                if(val[0] == 0){
                    continue;}
                else if( val[0] == 1){
                    lastX = val[1];
                    lastY = val[2];
                    computer.setHits();
                }
                else{
                    lastX = val[1];
                    lastY = val[2];
                    enemyTurn = false;
                }
                this.computer.setTotalShots();
            }
            else{
                StrategyHit strategy = new StrategyHit();
                int[] val = strategy.execute(human, playerBoard, lastX, lastY);
                if(val[0] == 0){
                    continue;}
                else if( val[0] == 1){
                    lastX = val[1];
                    lastY = val[2];
                    computer.setHits();
                }
                else{
                    lastX = val[1];
                    lastY = val[2];
                    enemyTurn = false;
                }
                this.computer.setTotalShots();
            }
            if (human.getHp() == 0) {
                viewSummary summary = new viewSummary(this.stage, 2, this, this.choice); // Let 1 represent Player and 2 represent Computer
            }
        }
    }

    private void startGame() {
        // place enemy ships
        // Configured: For the 3v3 game mode. From < 5 to < shipsComputer.length
        while(ComputerCurrentIndex < shipsComputer.length){
            int x = random.nextInt(choice);
            int y = random.nextInt(choice);
            boolean vert = Math.random() < 0.5;
            shipsComputer[ComputerCurrentIndex].setVertical(vert);
            shipsComputer[ComputerCurrentIndex].setBody(vert, x, y);
            if (enemyBoard.placeShip(shipsComputer[ComputerCurrentIndex], x, y)) {
                ComputerCurrentIndex++;
            }
        }
        running = true;
    }

    public double getHumanAccuracy(){
        return Math.round((double)this.human.getHits()/this.human.getTotalShots() * 100);
    }

    public double getComputerAccuracy(){
        return Math.round((double)this.computer.getHits()/this.computer.getTotalShots() * 100);
    }

    public String getGameMode(){
        if(this.choice == 10)
            return "Normal Mode";
        return "Fast Mode";
    }

    public String getWinner(){
        if(winner.equals("Human"))
            return "Human";
        return "Computer";
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        viewStart start = new viewStart(primaryStage, this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}