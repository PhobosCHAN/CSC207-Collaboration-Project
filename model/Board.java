package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Player.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Cell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ship.Ship;
import Player.Computer;

import view.viewGame;

/**
 * Represents a Board class for BattleShip
 */
public class Board extends Parent {
    viewGame game;
    private VBox rows = new VBox();
    private boolean enemy = false;
    public int ships = 5;

    public int grid;

    /**
     * A constructor for the board class
     * @param game an object of viewGame
     * @param enemy is true if the board refers to an enemy's board
     * @param choice is 10 if the game is in normal mode and 7 if the game is in fast mode
     * @param handler an object for the event handler
     */
    public Board(viewGame game,boolean enemy, int choice, EventHandler<? super MouseEvent> handler) {
        this.game = game;
        if (choice == 10){
            this.grid = 10;
        }
        else{
            this.grid = 7;
        }
        this.enemy = enemy;
        for (int y = 0; y < this.grid; y++) {
            HBox row = new HBox();
            for (int x = 0; x < this.grid; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    /**
     * A function to place a ship on the board. Returns true if the ship is successfully placed
     * but false if there is already a ship int the desired position on the board or if the
     * position is outside the board.
     * @param ship An object representing the ship to be placed
     * @param x the x coordinate for the desired position
     * @param y the y coordinate for the desired position
     * @return true if the placement is successful and false otherwise
     */
    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.getBody().length;

            if (ship.getVertical()) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = getCell(x, i);
                    cell.ship = ship;
                    if (!enemy) {
                        cell.setFill(Color.GRAY);
                        cell.setStroke(Color.BLACK);
                    }
                }
            }
            else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = getCell(i, y);
                    cell.ship = ship;
                    if (!enemy) {
                        cell.setFill(Color.GRAY);
                        cell.setStroke(Color.BLACK);
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Getter for the cell at position x,y
     * @param x x coordinate of the cell on the board
     * @param y y coordinate for thr cell on the board
     * @return a cell an position x,y
     */
    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    /**
     * A getter for the neighbours of the cell
     * @param x x coordinate of the cell
     * @param y y coordinate of the cell
     * @return a list of cells that are adjacent to the cell at given x and y
     */
    public Cell[] getNeighbors(int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Cell> neighbors = new ArrayList<Cell>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }

    /**
     * A helper function for placeShip. Returns true is the ship can be placed at position x,y on the board
     * @param ship an object representing a ship
     * @param x the x coordinate for the desired position
     * @param y the y coordinate for the desired position
     * @return true if the placement can be successful and false otherwise
     */
    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.getBody().length;

        if (ship.getVertical()) {
            for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i))
                    return false;

                Cell cell = getCell(x, i);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getNeighbors(x, i)) {
                    if (!isValidPoint(x, i))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }
        else {
            for (int i = x; i < x + length; i++) {
                if (!isValidPoint(i, y))
                    return false;

                Cell cell = getCell(i, y);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getNeighbors(i, y)) {
                    if (!isValidPoint(i, y))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if a given point is valid on the board or not
     * @param point an object representing a point on the board
     * @return true if the point is valid and false otherwise
     */
    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    /**
     * A helper function for the above function that checks if the given x and y are within the board
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     * @return true if x and y are on the board and false otherwise
     */
    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < this.grid && y >= 0 && y < this.grid ;
    }

    public class Cell extends Rectangle {
        /**
         * A class representing a cell. A subclass of the Rectangle class in javafx.
         */
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;

        private Board board;

        /**
         * A constructor for a cell
         * @param x x coordinate of the cell
         * @param y y coordinate of the cell
         * @param board the board which contains the cell
         */
        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTBLUE);
            setStroke(Color.BLUE);
        }

        /**
         * A function that allows player to shoot the cell
         * @param player An object of Player class representing the player that is shooting the cell
         * @return true if there was a ship present on that cell and false otherwise
         */
        public boolean shoot(Player player) {
            if (player.getClass() == Computer.class){
                try{ TimeUnit.MILLISECONDS.sleep(100000000);}
                catch(InterruptedException ignored){}
            }
            wasShot = true;
            setFill(Color.BLACK);
            System.out.println("shot: "+ this.x + " " + this.y);
            if (ship != null) {
                ship.hit();
                setFill(Color.RED);
                player.gotHit();
                game.deductHp(wasShot, game.grid, grid);
                if (!ship.isAlive()) {
                    board.ships--;
                }
                return true;
            }

            return false;
        }
    }
}