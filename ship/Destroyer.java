package ship;

import model.BattlePoint;

/** A Destroyer ship.
 */
public class Destroyer implements Ship{
    BattlePoint[] body;
    public boolean vertical = true;

    private int health;
    public Destroyer(BattlePoint[] body, boolean vertical){
        this.body = body;
        this.health = 3;
        this.vertical = vertical;
    }
    public int getHP(){
        return this.health;
    }

    @Override
    public BattlePoint[] getBody() {
        return this.body;
    }

    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }
    public boolean getVertical(){
        return this.vertical;
    }
    public void setBody(boolean vert, int x, int y) {
        body = new BattlePoint[3];
        for(int i = 0; i < 3; i++){
            if(vert) {
                BattlePoint battlePoint = new BattlePoint(x, y);
                body[i] = battlePoint;
                y--;
            }
            else{
                BattlePoint battlePoint = new BattlePoint(x, y);
                body[i] = battlePoint;
                x++;
            }
        }
    }

    @Override
    public void setVertical(boolean vert) {
        this.vertical = vert;
    }
}
