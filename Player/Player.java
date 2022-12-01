package Player;

import model.BattleBoard;
import model.BattlePoint;
import model.HP;
import ship.Ship;

public class Player {

    Ship[] ships;
    int hp;


    public Player(Ship[] ships, boolean fastMode){
        this.ships = ships;
        if(fastMode)
            hp = HP.getInstance().hp_small;
        else
            hp = HP.getInstance().hp_large;
    }


}
