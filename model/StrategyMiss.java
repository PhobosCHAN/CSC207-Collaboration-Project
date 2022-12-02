package model;

import Player.Player;
import javafx.scene.paint.Color;

import java.util.Random;

public class StrategyMiss implements Strategy{
    @Override
    public int[] execute(Player player, Board board, int x, int y) {
        int[] value = new int[3];
        value[1] = x;
        value[2] = y;
        Random random = new Random();
        int x1 = random.nextInt(10);
        int y1 = random.nextInt(10);
        Board.Cell cell = board.getCell(x1, y1);
        if (cell.wasShot) {
            execute(player, board, x1, y1);
            return value;
        }
        if(cell.shoot(player)) {
            value[0] = 1;
        }
        value[1] = x1;
        value[2] = y1;
        return value;
    }
}
