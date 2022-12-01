package ship;

import model.BattlePoint;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;

/** An aircraft carrier ship that is the only ship with height as 2 since is spans over two rows.
 */
public class Carrier implements Ship{
    private BattlePoint[] body;

    public Carrier(BattlePoint[] body){
        this.body = body;
    }
    public int getHP(){
        return 5;
    }

    @Override
    public BattlePoint[] getBody() {
        return this.body;
    }
}
