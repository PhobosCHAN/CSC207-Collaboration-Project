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

    public Ship(String type, int size) {
        this.type = type;
        this.size = size;
        this.setBody();
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
}
