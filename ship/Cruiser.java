package ship;

import model.BattlePoint;

/** An cruiser ship.
 */
public class Cruiser implements Ship {
    BattlePoint[] body;
    public Cruiser(BattlePoint[] body){
        this.body = body;
    }
    public int getHP(){
        return 4;
    }

    @Override
    public BattlePoint[] getBody() {
        return this.body;
    }
}
