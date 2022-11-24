package ship;

import model.BattlePoint;

/** An aircraft carrier ship that is the only ship with height as 2 since is spans over two rows.
 */
public class Carrier extends Ship{

    public Carrier(String type, int size) {
        super(type, size);
        this.setBody();
    }

    @Override
    public void setBody() {
        body = new BattlePoint[this.getSize() * 2];
        for(int i = 0; i < this.getSize(); i++){
            BattlePoint battlePoint = new BattlePoint(0, i);
            body[i] = battlePoint;
        }
        for(int i = 0; i < this.getSize(); i++){
            BattlePoint battlePoint = new BattlePoint(1, i);
            body[i + this.getSize()] = battlePoint;
        }
    }
}
