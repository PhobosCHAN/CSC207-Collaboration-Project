
package ship;

import model.BattlePoint;

/** An immutable representation of a Ship in a particular rotation.
 *  Each ship is defined by the points that make up its body.
 */

public interface Ship {

    /**
     * Interface for a type of Ship.
     * Implements the Factory Design Pattern.
     */
    public int getHP();
    public BattlePoint[] getBody();
    public void hit();

    public boolean isAlive();
    public boolean getVertical();
    public void setBody(boolean vert, int x, int y);

    void setVertical(boolean vert);

}
