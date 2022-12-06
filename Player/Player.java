package Player;

import model.BattlePoint;
import model.HP;
import ship.Ship;

public class Player {

    /**
     * Represents a Player class for BattleShip.
     */

    Ship[] ships;
    int hp;
    int totalShots;
    int hits;
    String type;

    /**
     * Constructor for an empty board of the given width and height measured in blocks.
     *
     * @param type the type of player, i.e, human or computer
     * @param ships a list of ships for the player
     * @param fastMode is true if the game is being played in fastmode.
     */
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

    /**
     * Setter to count the total shots taken by the player.
     */
    public void setTotalShots(){
        this.totalShots++;
    }

    /**
     * Setter to count the hits by the player.
     */
    public void setHits(){
        this.hits++;
    }

    /**
     * Getter for the total hits by the player.
     * @return the count of hits
     */
    public int getHits(){
        return this.hits;
    }

    /**
     * Getter for the total shots taken by the player.
     * @return the count of total shots taken.
     */
    public int getTotalShots(){
        return this.totalShots;
    }

    /**
     * Returns the player's hp.
     */
    public int getHp() {
        return hp;
    }

    /**
     * Deducts the players hp by 1 if their ship gets hit by the opponent.
     */
    public void gotHit(){
        this.hp -= 1;
    }

    public String getShips(){
        String res = null;
        for (Ship i: this.ships){
            res += String.valueOf(i.getBody()[0].x) + " " + String.valueOf(i.getBody()[0].y) + " " + String.valueOf(i.getBody()[i.getBody().length - 1].x) + " " + String.valueOf(i.getBody()[i.getBody().length - 1].y + " ");
            if (i.getVertical()){
                res += 0;
            }
            else{res += 1;}
            res += " " + i.getBody().length + ".";
        }
        return res;
    }
    public void setShips(Ship[] ship){
        ships = ship;
    }

}
