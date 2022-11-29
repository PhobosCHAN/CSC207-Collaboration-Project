package model;

import ship.Ship;

import java.io.Serializable;
import java.util.Arrays;

    /** Represents a Board class for BattleShip.
     */
    public class BattleBoard implements Serializable {
        public static int size; //board height and width
        protected boolean[][] battleGrid; //board grid

        //error types (to be returned by the place function)
        public static final int ADD_OK = 0;
        public static final int ADD_OUT_BOUNDS = 1;
        public static final int ADD_BAD = 2;

        /**
         * Constructor for an empty board of the given width and height measured in blocks.
         *
         * @param size  size
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
         * valid width/height area always return true (as we can't place anything there).
         *
         * @param x grid position, x
         * @param y grid position, y
         *
         * @return true if the given block at x,y is filled, else false
         */
        public boolean getGrid(int x, int y) {
            return x >= size || x < 0 || y >= size || y < 0 || battleGrid[x][y];
        }

        /**
         * Attempts to add the body of a piece to the board. Copies the piece blocks into the board grid.
         * Returns ADD_OK for a regular placement, or ADD_ROW_FILLED
         * for a regular placement that causes at least one row to be filled.
         *
         * Error cases:
         * A placement may fail in two ways. First, if part of the piece may fall out
         * of bounds of the board, ADD_OUT_BOUNDS is returned.
         * Or the placement may collide with existing blocks in the grid
         * in which case ADD_BAD is returned.
         * In both error cases, the board may be left in an invalid
         * state. The client can use undo(), to recover the valid, pre-place state.
         *
         * @param ship ship to place
         * @param x placement position, x
         * @param y placement position, y
         *
         * @return static int that defines result of placement
         */
        public int placePiece(Ship ship, int x, int y) {

            //changing coordinates of the piece to align with the coordinates of the board
            Ship modifiedShip = modifyShip(piece, x, y);

            if((x >= this.width || x < 0) || (y >= this.height || y < 0)){
                this.committed = false;
                return ADD_OUT_BOUNDS;
            }
            for(int i = 0; i < modifiedPiece.getBody().length; i++){
                if(modifiedPiece.getBody()[i].x >= this.width || modifiedPiece.getBody()[i].x < 0 ||
                        modifiedPiece.getBody()[i].y >= this.height || modifiedPiece.getBody()[i].y < 0) {
                    this.committed = false;
                    return ADD_OUT_BOUNDS;
                }
            }

            for(int i = 0; i < modifiedPiece.getBody().length; i++){
                if(this.colCounts[modifiedPiece.getBody()[i].x] != 0  && modifiedPiece.getBody()[i].y < this.colCounts[modifiedPiece.getBody()[i].x]) {
                    this.committed = false;
                    return ADD_BAD;
                }
            }
            //Filling in grids in the TetrisGrid
            for(int i = 0; i < modifiedPiece.getBody().length; i++){
                this.tetrisGrid[modifiedPiece.getBody()[i].x][modifiedPiece.getBody()[i].y] = true;
            }
            //Correcting the row and col counts
            this.makeHeightAndWidthArrays();

            //Backing up everything and commit the state of the board
            this.backupGrid();
            this.commit();
            //Checking if a row is filled
            for (int rowCount : this.rowCounts) {
                if (rowCount == this.width)
                    return ADD_ROW_FILLED;
            }
            return ADD_OK;
        }

        /**
         * A helper function for place ship that allows us to modify the position of the ship without altering the
         *  original ship so that the points correspond to the coordinates on the BattleShip Board.
         *
         *
         *  @param ship ship to place
         * @param x placement position, x
         * @param y placement position, y
         *
         * return A modified Ship whos points allign with the points on the board.
         */
        private Ship modifyShip(Ship ship, int x, int y) {
            BattlePoint[] modifiedBody = new BattlePoint[ship.getBody().length];
            for(int i = 0; i < ship.getBody().length; i++){
                BattlePoint modifiedPoint = new BattlePoint(ship.getBody()[i].x + x, ship.getBody()[i].y + y);
                modifiedBody[i] = modifiedPoint;
            }
            return new Ship(modifiedBody);
        }

        /**
         * Deletes rows that are filled all the way across, moving
         * things above down. Returns the number of rows cleared.
         *
         * @return number of rows cleared (useful for scoring)
         */
        public int clearRows() {
            int count = 0;
            for (int rowCount : this.rowCounts) {
                if (rowCount == 10) {
                    count++;
                }
            }
            //Moving rows Down
            for(int i = 0; i < this.rowCounts.length; i++){
                if(rowCounts[i] == 10){
                    this.moveRowsDown(i);
                    i = -1;
                }
            }
            return count;
        }

        /**
         * Moves the rows that are above the index row down one space
         */

        public void moveRowsDown(int row){
            for(int i = row; i <  this.rowCounts.length - 1; i++){
                rowCounts[i] = rowCounts[i + 1];
            }
            //Check if top row is the one that's full and make it all 0
            if(row == this.height - 1)
                rowCounts[row] = 0;
            //Decreasing each column count by 1 since one row has been cleared
            for(int i = 0; i < this.colCounts.length; i++){
                this.colCounts[i] -= 1;
            }
        }

        /**
         * Reverts the board to its state before up to one call to placePiece() and one to clearRows();
         * If the conditions for undo() are not met, such as calling undo() twice in a row, then the second undo() does nothing.
         * See the overview docs.
         */
        public void undo() {
            if (committed == true) return;  //a committed board cannot be undone!

            if (backupGrid == null) throw new RuntimeException("No source for backup!");  //a board with no backup source cannot be undone!

            //make a copy!!
            for (int i = 0; i < backupGrid.length; i++) {
                System.arraycopy(backupGrid[i], 0, tetrisGrid[i], 0, backupGrid[i].length);
            }

            //copy row and column tallies as well.
            System.arraycopy(backupRowCounts, 0, rowCounts, 0, backupRowCounts.length);
            System.arraycopy(backupColCounts, 0, colCounts, 0, backupColCounts.length);

            committed = true; //no going backwards now!
        }

        /**
         * Copy the backup grid into the grid that defines the board (to support undo)
         */
        private void backupGrid() {
            //make a copy!!
            for (int i = 0; i < tetrisGrid.length; i++) {
                System.arraycopy(tetrisGrid[i], 0, backupGrid[i], 0, tetrisGrid[i].length);
            }
            //copy row and column tallies as well.
            System.arraycopy(rowCounts, 0, backupRowCounts, 0, rowCounts.length);
            System.arraycopy(colCounts, 0, backupColCounts, 0, colCounts.length);
        }

        /**
         * Puts the board in the 'committed' state.
         */
        public void commit() {
            committed = true;
        }

        /**
         * Fills heightsOfCols[] and widthOfRows[].  Useful helper to support clearing rows and placing pieces.
         */
        private void makeHeightAndWidthArrays() {

            Arrays.fill(colCounts, 0);
            Arrays.fill(rowCounts, 0);

            for (int x = 0; x < tetrisGrid.length; x++) {
                for (int y = 0; y < tetrisGrid[x].length; y++) {
                    if (tetrisGrid[x][y]) { //means is not an empty cell
                        colCounts[x] = y + 1; //these tallies can be useful when clearing rows or placing pieces
                        rowCounts[y]++;
                    }
                }
            }
        }

        /**
         * Print the board
         *
         * @return a string representation of the board (useful for debugging)
         */
        public String toString() {
            StringBuilder buff = new StringBuilder();
            for (int y = height-1; y>=0; y--) {
                buff.append('|');
                for (int x=0; x<width; x++) {
                    if (getGrid(x,y)) buff.append('+');
                    else buff.append(' ');
                }
                buff.append("|\n");
            }
            for (int x=0; x<width+2; x++) buff.append('-');
            return(buff.toString());
        }


    }



}
