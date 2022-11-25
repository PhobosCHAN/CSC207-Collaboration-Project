package ship;

import model.BattlePoint;

/** An immutable representation of a Ship in a particular rotation.
 *  Each ship is defined by the points that make up its body.
 */

public class Ship {
    String type;
    int size;
    BattlePoint[] body; // y and x values that make up the body of this ship.
    int hp;

    /**
     * Defines a new ship given the type and size.
     * Sets
     * Note that this constructor should define the piece's "lowestYVals". For each x value
     * in the body of a piece, the lowestYVals will contain the lowest y value for that x in the body.
     * This will become useful when computing where the piece will land on the board!!
     */
    public Ship(String type, int size) {
        this.type = type;
        this.size = size;
        this.setBody();
        this.hp = this.body.length;
    }

    public Ship(BattlePoint[] body) {
        this.body = body;

    }

    public void setBody() {
        body = new BattlePoint[this.getSize()];
        for(int i = 0; i < this.getSize(); i++){
            BattlePoint battlePoint = new BattlePoint(0, i);
            body[i] = battlePoint;
        }
    }

    public int getSize(){
        return this.size;
    }

    public String getType(){

        return this.type;
    }

    public int getHp(){
        return this.hp;
    }

    public Ship Rotation() {
        int shift = 0;//local variable to make sure there are no negative coordinates
        BattlePoint[] newBody = new BattlePoint[this.body.length];
        for(int i = 0; i < this.body.length; i++){
            BattlePoint newPoint = new BattlePoint( -1 * -1 * this.body[i].getY(), -1 * this.body[i].getX());
            //finding minimum Y value to shift all Y values up by that amount so there are no negative coordinates
            if(shift > newPoint.getY()){
                shift = newPoint.getY();
            }
            newBody[i] = newPoint;
        }
        shift *= -1;
        for (BattlePoint battlePoint : newBody) {
            battlePoint.y += shift;
        }
        return new Ship(newBody);
    }

}
