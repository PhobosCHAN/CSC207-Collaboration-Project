package model;

import Player.Player;
import javafx.scene.paint.Color;

import java.util.Random;

public class StrategyMiss implements Strategy{
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
