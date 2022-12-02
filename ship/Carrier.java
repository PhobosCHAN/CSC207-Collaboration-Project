package ship;

import model.BattlePoint;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;

/** An aircraft carrier ship that is the only ship with height as 2 since is spans over two rows.
 */
public class Carrier implements Ship{
    private BattlePoint[] body;
    public boolean vertical = true;
    private int health;

    public Carrier(BattlePoint[] body, boolean vertical){
        this.body = body;
        this.health = 5;
        this.vertical = vertical;
    }
    public void setBody(boolean vert, int x, int y) {
        body = new BattlePoint[5];
        for(int i = 0; i < 5; i++){
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

}
