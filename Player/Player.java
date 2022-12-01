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
            this.hp = HP.getInstance().hp_small;
        else
            this.hp = HP.getInstance().hp_large;
    }

    public int getHp() {
        return hp;
    }

    public void gotHit(Ship ship){
        this.hp -= 1;
        for (Ship value : ships) {
            if (ship == value)
                value.gotHit();
        }
    }


}
