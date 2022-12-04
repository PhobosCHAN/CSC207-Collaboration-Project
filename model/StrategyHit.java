package model;
import java.util.Arrays;
import java.util.Random;
import Player.Player;
import javafx.scene.paint.Color;

public class StrategyHit implements Strategy{

    @Override
    public int[] execute(Player player, Board board, int x, int y) {
        int[] value = new int[3];
        Random random = new Random();

        int x1 = random.nextInt(Math.max(0, x-1), Math.min(9, x+1) + 1);
        int y1 = random.nextInt(Math.max(0, y-1), Math.min(9, y+1) + 1);
        if (Arrays.stream(board.getNeighbors(x, y)).allMatch(n -> n.wasShot)){
            StrategyMiss strategyMiss = new StrategyMiss();
            value = strategyMiss.execute(player, board, x, y);
            return value;
        }

        Board.Cell cell = board.getCell(x1, y1);
        if (cell.wasShot) {
            return value;
        }
        if(cell.shoot(player)) {
            value[0] = 1;
        }
        else{
            value[0] = 2;
        }
        value[1] = x1;
        value[2] = y1;
        return value;
    }

}
