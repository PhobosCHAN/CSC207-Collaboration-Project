package ship;

import model.BattlePoint;

/** A Destroyer ship.
 */
public class Destroyer implements Ship{
    BattlePoint[] body;
    public Destroyer(BattlePoint[] body){
        this.body = body;
    }
    public int getHP(){
        return 3;
    }

    @Override
    public BattlePoint[] getBody() {
        return this.body;
    }
}
