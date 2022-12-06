package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Player.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ship.Ship;
import Player.Computer;
import view.viewGame;


public class Board extends Parent{
    private VBox rows = new VBox();
    private boolean enemy = false;
    public int ships = 5;

    public int grid;
    public viewGame game;

    public Board(viewGame game, boolean enemy, int choice, EventHandler<? super MouseEvent> handler) {
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

    public Board(int choice, String shot){
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
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

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

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

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

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < this.grid && y >= 0 && y < this.grid ;
    }

    public class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;

        private Board board;

        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTBLUE);
            setStroke(Color.BLUE);
        }

        public boolean shoot(Player player) {

            wasShot = true;
            setFill(Color.BLACK);
            System.out.println("shot: "+ this.x + " " + this.y);
            if (ship != null) {
                ship.hit();
                setFill(Color.RED);
                player.gotHit();
                if (!ship.isAlive()) {
                    board.ships--;
                }
                return true;
            }

            return false;
        }
    }

    @Override
    public String toString() {
        String res = null;
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                res += getCell(i, j).wasShot;
                res+= " ";
            }
        }
        return res;
    }
}