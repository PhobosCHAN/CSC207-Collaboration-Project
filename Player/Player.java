package Player;

import model.BattlePoint;
import model.HP;
import ship.Ship;

public class Player {

    Ship[] ships;
    int hp;
    int totalShots;
    int hits;
    String type;


    public Player(String type, Ship[] ships, boolean fastMode){
        this.type = type;
        this.ships = ships;
        if(fastMode)
            this.hp = HP.getInstance().hp_small;
        else
            this.hp = HP.getInstance().hp_large;
        this.totalShots = 0;
        this.hits = 0;
    }

    public void setTotalShots(){
        this.totalShots++;
    }

    public void setHits(){
        this.hits++;
    }

    public int getHits(){
        return this.hits;
    }

    public int getTotalShots(){
        return this.totalShots;
    }
    public int getHp() {
        return hp;
    }

    public void gotHit(){
        this.hp -= 1;
    }

    public String getShips(){
        String res = null;
        for (Ship i: this.ships){
            res += String.valueOf(i.getBody()[0].x) + " " + String.valueOf(i.getBody()[0].y) + " " + String.valueOf(i.getBody()[i.getBody().length - 1].x) + " " + String.valueOf(i.getBody()[i.getBody().length - 1].y + " ");
            if (i.getVertical()){
                res += 1;
            }
            else{res += 0;}
            res += " " + i.getBody().length + ".";
        }
        return res;
    }
    public void setShips(Ship[] ship){
        ships = ship;
    }

}
