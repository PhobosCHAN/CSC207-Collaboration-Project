package model;
import java.util.Random;
import Player.Player;
import javafx.scene.paint.Color;

public class StrategyHit implements Strategy{

    @Override
    public int[] execute(Player player, Board board, int x, int y) {
        int[] value = new int[3];
        int x1 = 0;
        int y1 = 0;
        value[1] = x;
        value[2] = y;
        Random random = new Random();
        if((x-1) >= 0 && (x+1) < 10 && (y-1) >= 0 && (y+1) < 10){
            x1 = random.nextInt(x-1, x+1);
            y1 = random.nextInt(y - 1, y +  1);
        }
        else if(x == 0){
            x1 = random.nextInt(0, x+1);
            y1 = random.nextInt(y - 1, y +  1);
        }
        else if(x == 9){
            x1 = random.nextInt(x - 1, 9);
            y1 = random.nextInt(y - 1, y +  1);
        }
        else if(y == 0){
            x1 = random.nextInt(x - 1, x+1);
            y1 = random.nextInt(0, y +  1);
        }
        else if(y == 9){
            x1 = random.nextInt(x - 1, x + 1);
            y1 = random.nextInt(y - 1, 9);
        }
        Board.Cell cell = board.getCell(x1, y1);
        if (cell.wasShot) {
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
