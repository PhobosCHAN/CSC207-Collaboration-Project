package model;

import Player.Player;
import javafx.scene.paint.Color;

import java.util.Random;

public class StrategyMiss implements Strategy{

    /**
     * Implements the Strategy interface for when the previous computer move was a miss
     * @param player an object of the player class that represents the computer
     * @param board an object for the human's board
     * @param x x coordinate of the last move
     * @param y y coordinate of the last move
     * @return an integer array whose 0th index tells us if this move has led to a hit or miss, 1st
     * index tells us the x coordinate of the move and 2nd index tells us the yth coordinate of the move
     */
    @Override
    public int[] execute(Player player, Board board, int x, int y) {
        int[] value = new int[3];
        Random random = new Random();

        int x1 = random.nextInt(board.grid);
        int y1 = random.nextInt(board.grid);
        Board.Cell cell = board.getCell(x1, y1);
        if (cell.wasShot) {
            return value;
        }
        if(cell.shoot(player)) {
            value[0] = 1;
        }
        else{value[0] = 2;
        }
        value[1] = x1;
        value[2] = y1;

        return value;
    }
}
