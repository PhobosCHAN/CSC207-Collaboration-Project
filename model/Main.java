package model;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import view.viewLoad;
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
import view.viewGame;

import static java.lang.Thread.sleep;

/**
 * A BattleShip Application, in JavaFX
 *
 */
public class Main extends Application {

    public view.viewGame game;
    Stage stage;
    public boolean running = false;
    public model.Board enemyBoard, playerBoard;
    public Ship[] shipsHuman;
    public Ship[] shipsComputer;

    public int currentShipIndex;
    public int ComputerCurrentIndex;

    public int lastX, lastY;

    public int choice;
    public int choice2;

    public boolean enemyTurn = false;

    public String winner;

    public Player human;
    public Player computer;

    public Random random = new Random();

    /**
     * A getter for the list of ships belonging to the human player.
     * @return A list of ships that are on the human's board
     */
    public Ship[] getShipsHuman(){
        return this.shipsHuman;
    }

    /**
     * A getter for the list of ships belonging to the computer player.
     * @return A list of ships that are on the computer's board
     */
    public model.Board getEnemyBoard(){
        return this.enemyBoard;
    }

    /**
     * Getter for the board for human
     * @return a board object representing the human's board
     */
    public model.Board getPlayerBoardBoard(){
        return this.playerBoard;
    }

    /**
     * Setter for the board for computer
     * @param board The board that the computer's board should represent
     */
    public void setEnemyBoard(model.Board board){
        this.enemyBoard = board;
    }

    /**
     * Setter for the board for human
     * @param board The board that the human's board should represent
     */
    public void setPlayerBoard(model.Board board){
        this.playerBoard = board;
    }

    /**
     * A function to create the player ships and assign them to each player.
     * @param choice is 10 if the game is being played in normal mode and 7 if the game is being played in fast mode
     */
    public void populatePlayerShips(int choice, int choice2){
        this.choice = choice;
        this.choice2 = choice2;
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

    /**
     * Creates the board in the UI and deals with the placement on the ship by the human and computer and handles the
     * user moves.
     * Ends the game when either Player reach zero HP.
     * @param choice is 10 if the game is being played in normal mode and 7 if the game is being played in fast mode
     * @param stage an object for Stage for the javafx
     * @return root: an object of the Parent class
     */
    public Parent createContent(int choice, int choice2, Stage stage) {
        this.game = new viewGame(stage, this);
        this.stage = stage;
        this.populatePlayerShips(choice, choice2);
        human = new Player("human", shipsHuman, this.choice == 7);
        computer = new Player("computer", shipsComputer, this.choice == 7);
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 700);
        root.setLeft(game.leftButtons(this.choice));
        root.setRight(game.rightInteractive(choice, choice2));
        enemyBoard = new Board(this.game, true, choice, event -> {
            if (!running)
                return;
            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;
            this.human.setTotalShots();
            enemyTurn = !cell.shoot(computer);
            this.soundProducer(cell.x, cell.y, !enemyTurn);
            if(!enemyTurn) {
                this.human.setHits();
            }
            if (computer.getHp() == 0) {
                viewSummary summary = new viewSummary(this.stage, 1, this, this.choice, this.choice2);
                winner = "Human";
            }
            if (enemyTurn){
                try {
                    enemyMove();
                } catch (InterruptedException ignored) {
                }
            }
        });
        playerBoard = new Board(this.game,false, choice, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            boolean vert = event.getButton() == MouseButton.PRIMARY;
            shipsHuman[currentShipIndex].setVertical(vert);
            shipsHuman[currentShipIndex].setBody(vert, cell.x, cell.y);
            if (playerBoard.placeShip(shipsHuman[currentShipIndex], cell.x, cell.y)) {
                if ((currentShipIndex + 1) == 5 & choice == 10) {
                    game.instructions2();
                    startGame();
                }
                // Configured: For the 3v3 game mode. Ships used = 4, 3, 3
                else if ((currentShipIndex + 1) == 3 & choice == 7){
                    game.instructions2();
                    startGame();
                }
                currentShipIndex++;
            }
        });
        root.setCenter(game.layout(choice));
        //root.setBackground();
        return root;
    }

    public Parent createContent(Stage stage) {
        this.game = new viewGame(stage, this);
        this.stage = stage;
        human = new Player("human", shipsHuman, this.choice == 7);
        computer = new Player("computer", shipsComputer, this.choice == 7);
        human.setShips(this.shipsHuman);
        computer.setShips(this.shipsComputer);
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 700);
        root.setLeft(game.leftButtons(this.choice));
        root.setRight(game.rightInteractive(this.choice, this.choice2));

        enemyBoard = new Board(this.game, true, choice, event -> {
            if (!running)
                return;
            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;
            this.human.setTotalShots();
            enemyTurn = !cell.shoot(computer);
            this.soundProducer(cell.x, cell.y, !enemyTurn);
            if(!enemyTurn)
                this.human.setHits();
            if (computer.getHp() == 0) {
                viewSummary summary = new viewSummary(this.stage, 1, this, this.choice, this.choice2);
                winner = "Human";
            }
            if (enemyTurn){
                try {
                    enemyMove();
                } catch (InterruptedException ignored) {
                }
            }
        });
        playerBoard = new Board(this.game,false, choice, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            boolean vert = event.getButton() == MouseButton.PRIMARY;
            shipsHuman[currentShipIndex].setVertical(vert);
            shipsHuman[currentShipIndex].setBody(vert, cell.x, cell.y);
            if (playerBoard.placeShip(shipsHuman[currentShipIndex], cell.x, cell.y)) {
                if ((currentShipIndex + 1) == 5 & choice == 10) {
                    game.instructions2();
                    startGame();
                }
                // Configured: For the 3v3 game mode. Ships used = 4, 3, 3
                else if ((currentShipIndex + 1) == 3 & choice == 7){
                    game.instructions2();
                    startGame();
                }
                currentShipIndex++;
            }
        });
        for (Ship i: shipsHuman){
            playerBoard.placeShip(i,i.getBody()[0].getX(), i.getBody()[0].getY());
        }
        for (Ship i: shipsComputer){
            enemyBoard.placeShip(i,i.getBody()[0].getX(), i.getBody()[0].getY());
        }

        String[] computershot = viewLoad.raw1[3].split(" ");
        String[] humanshot = viewLoad.raw1[4].split(" ");
        for (int i = 0; i < computershot.length; i++){
            if (Objects.equals(computershot[i], "true")){
                int x = i/choice;
                int y = i%choice;
                enemyBoard.getCell(x,y).shoot(computer);
            }
        }
        for (int i = 0; i < humanshot.length; i++){
            if (Objects.equals(humanshot[i], "true")){
                int x = i/choice;
                int y = i%choice;
                playerBoard.getCell(x,y).shoot(human);
            }
        }
        root.setCenter(game.layout(choice));
        //root.setBackground();
        return root;
    }

    public void soundProducer(int x, int y, boolean hit) {

        LinkedList<Media> sounds = new LinkedList<>();
        String n0 = "num0.wav";
        String n1 = "num1.wav";
        String n2 = "num2.wav";
        String n3 = "num3.wav";
        String n4 = "num4.wav";
        String n5 = "num5.wav";
        String n6 = "num6.wav";
        String n7 = "num7.wav";
        String n8 = "num8.wav";
        String n9 = "num9.wav";
        String n10 = "silence.wav";
        String n11 = "hit.wav";
        String n12 = "miss.wav";
        String[] audios = {n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12};
        String bip = "./Sounds/";

        Media media = new Media(new File(bip.concat(audios[x])).toURI().toString());
        Media media2 = new Media(new File(bip.concat(audios[y])).toURI().toString());
        //Media media3 = new Media(new File(bip.concat("silence.wav")).toURI().toString());  //for 2 sec silence

        if(hit){
            Media media4 = new Media(new File(bip.concat("hit.wav")).toURI().toString());
            sounds.add(media4);
        }
        else{
            Media media5 = new Media(new File(bip.concat("miss.wav")).toURI().toString());
            sounds.add(media5);
        }
        sounds.add(media);
        sounds.add(media2);
        if(this.choice2 == 1)
            this.play(sounds);
    }

    /**
     * Helper function for the sounds producer
     */
    public void play(LinkedList<Media> sounds) {
        if (sounds.isEmpty())
            return;
        MediaPlayer player = new MediaPlayer(sounds.poll());
        player.setOnEndOfMedia(() -> play(sounds));
        player.play();
    }

    /**
     * Handles the computer moves and calls the StrategyHit or Miss depending on the previous move
     * @throws InterruptedException if any
     */
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
                viewSummary summary = new viewSummary(this.stage, 2, this, this.choice, this.choice2); // Let 1 represent Player and 2 represent Computer
            }
        }
    }

    /**
     * Start method.  Places the computer's ships at random positions on the computer's board.
     */
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

    /**
     * Getter for the Human Player's Accuracy for hits
     * @return a double value representing the human's accuracy for hits
     */
    public double getHumanAccuracy(){
        return Double.parseDouble(new DecimalFormat("##.00").format((double)this.human.getHits()/this.human.getTotalShots() * 100));
    }

    /**
     * Getter for the Computer Player's Accuracy for hits
     * @return a double value representing the computer's accuracy for hits
     */
    public double getComputerAccuracy(){
        return Double.parseDouble(new DecimalFormat("##.00").format((double)this.computer.getHits()/this.computer.getTotalShots() * 100));
    }

    /**
     * Getter for the mode the game was played in
     * @return
     */
    public String getGameMode(){
        if(this.choice == 10)
            return "Normal Mode";
        return "Fast Mode";
    }

    /**
     * Getter for the winner of the game
     * @return
     */
    public String getWinner(){
        if(winner == null)
            return "Computer";
        return "Human";
    }

    /**
     * Start method.  Control of application flow is dictated by JavaFX framework
     *
     * @param primaryStage stage upon which to load GUI elements
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        viewStart start = new viewStart(primaryStage, this);
    }

    /**
     * Main method
     *
     * @param args argument, if any
     */
    public static void main(String[] args) {
        launch(args);
    }
}