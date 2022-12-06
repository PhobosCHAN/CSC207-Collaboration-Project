package ship;

import model.BattlePoint;

/** An cruiser ship.
 */
public class Cruiser implements Ship {
    BattlePoint[] body;
    public boolean vertical = true;

    private int health;
    public Cruiser(){
        this.health = 4;
    }
    public Cruiser(BattlePoint[] body, boolean vertical){
        this.body = body;
        this.health = 4;
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
        body = new BattlePoint[4];
        this.vertical = vert;
        for(int i = 0; i < 4; i++){
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
