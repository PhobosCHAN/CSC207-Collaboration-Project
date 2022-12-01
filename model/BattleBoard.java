package model;

import ship.Ship;

import java.io.Serializable;
import java.util.Arrays;

    /** Represents a Board class for BattleShip.
     */
public class BattleBoard implements Serializable {
    private int size; //board height and width
    protected boolean[][] battleGrid; //board grid

    //error types (to be returned by the place function)
    public static final int ADD_OK = 0;
    public static final int ADD_OUT_BOUNDS = 1;
    public static final int ADD_BAD = 2;

    /**
     * Constructor for an empty board of the given width and height measured in blocks.
     *
     * @param size size
     */
    public BattleBoard(int size) {
        this.size = size;
        this.battleGrid = new boolean[size][size];
    }

    /**
     * Helper to fill new game grid with empty values
     */
    public void newGame() {
        for (boolean[] booleans : battleGrid) {
            Arrays.fill(booleans, false);
        }
    }

    /**
     * Getter for board size
     */
    public int getSize() {
        return size;
    }


    /**
     * Returns true if the given block is filled in the board. Blocks outside the
     * valid width/height area always return false (as there are no ships present there).
     *
     * @param x grid position, x
     * @param y grid position, y
     * @return true if the given block at x,y is filled, else false
     */
    public boolean getGrid(int x, int y) {
        if(x >= size || x < 0 || y >= size || y < 0)
            return false;
        return battleGrid[x][y];
    }

    /**
     * Attempts to add the body of a Ship to the board. Copies the ship blocks into the board grid.
     * Returns ADD_OK for a regular placement
     * Error cases:
     * A placement may fail in two ways. First, if part of the ship may fall out
     * of bounds of the board, ADD_OUT_BOUNDS is returned.
     * Or the placement may collide with existing ships in the grid
     * in which case ADD_BAD is returned.
     * In both error cases, the board may be left in an invalid
     * state.
     *
     * @param ship ship to place
     * @param x    placement position, x
     * @param y    placement position, y
     * @return static int that defines result of placement
     */
    public int placeShip(Ship ship, int x, int y) {

        //changing coordinates of the ship to align with the coordinates of the board
        Ship modifiedShip = modifyShip(ship, x, y);
        //Checks if the provided coordinates are out of bounds
        if ((x >= size || x < 0) || (y >= size || y < 0)) {
            return ADD_OUT_BOUNDS;
        }
        //Checks if any part of the ship will be out of bounds if we place in on the given coordinates
        for (int i = 0; i < modifiedShip.getBody().length; i++) {
            if (modifiedShip.getBody()[i].x >= size || modifiedShip.getBody()[i].x < 0 ||
                    modifiedShip.getBody()[i].y >= size || modifiedShip.getBody()[i].y < 0) {
                return ADD_OUT_BOUNDS;
            }
        }
        //Checks if any part of the ship will be placed in a spot where a ship already exists
        for (int i = 0; i < modifiedShip.getBody().length; i++) {
            if (battleGrid[modifiedShip.getBody()[i].x][modifiedShip.getBody()[i].y])
                return ADD_BAD;

        }
        //Filling in grids in the TetrisGrid
        for (int i = 0; i < modifiedShip.getBody().length; i++) {
            this.battleGrid[modifiedShip.getBody()[i].x][modifiedShip.getBody()[i].y] = true;
        }
        return ADD_OK;
    }

    /**
     * A helper function for place ship that allows us to modify the position of the ship without altering the
     * original ship so that the points correspond to the coordinates on the BattleShip Board.
     *
     * @param ship ship to place
     * @param x    placement position, x
     * @param y    placement position, y
     *             <p>
     *             return A modified Ship whose points align with the points on the board.
     */
    private Ship modifyShip(Ship ship, int x, int y) {
        BattlePoint[] modifiedBody = new BattlePoint[ship.getBody().length];
        for (int i = 0; i < ship.getBody().length; i++) {
            BattlePoint modifiedPoint = new BattlePoint(ship.getBody()[i].x + x, ship.getBody()[i].y + y);
            modifiedBody[i] = modifiedPoint;
        }
        return new Ship(modifiedBody);
    }

    /**
     * Print the board
     *
     * @return a string representation of the board (useful for debugging)
     */
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (int y = size - 1; y >= 0; y--) {
            buff.append('|');
            for (int x = 0; x < size; x++) {
                if (getGrid(x, y)) buff.append('+');
                else buff.append(' ');
            }
            buff.append("|\n");
        }
        for (int x = 0; x < size + 2; x++) buff.append('-');
        return (buff.toString());
    }
}
