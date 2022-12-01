package ship;

import model.BattlePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/** An immutable representation of a Ship in a particular rotation.
 *  Each ship is defined by the points that make up its body.
 */

public class Ship {
    String type;
    int size;
    BattlePoint[] body; // y and x values that make up the body of this ship.
    int hp;
    Ship next; //to store the next clockwise rotation

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
        this.next = null;
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

    public BattlePoint[] getBody(){
        return this.body;
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

    public void gotHit(){
        this.hp -= 1;
    }

    /**
     * Returns a pre-computed ship that is 90 degrees clockwise
     * rotated. Fast because the ship is pre-computed.
     * This only works on pieces set up by Rotation(), and otherwise
     * just returns null.
     *
     * @return the next rotation of the given ship
     */
    public Ship fastRotation() {
        return next;
    }

    /**
     * Given the "first" root rotation of a piece, computes all
     * the other rotations and links them all together
     * in a circular list. The list should loop back to the root as soon
     * as possible.
     * Return the root ship.
     *
     * @param ship the default rotation for a ship
     *
     * @return a ship that is a linked list containing all rotations for the ship
     */
    public static Ship makeFastRotations(Ship ship) {
        Ship ship1 = ship; //Temporary variable to store the value of root
        for(int i = 0; i <= 3; i++){
            ship.next = ship.Rotation();
            ship = ship.next;
        }
        ship = ship1;
        return ship;
    }

    /**
     * Returns a new ship that is 90 degrees clockwise
     * rotated from the receiver.
     *
     * @return the next rotation of the given ship
     */
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

    /**
     * Given a string of x,y pairs (e.g. "0 0 0 1 0 2 1 0"), parses
     * the points into a Ship array.
     *
     * @param string input of x,y pairs
     *
     * @return an array of parsed points
     */
    private static BattlePoint[] parsePoints(String string) {
        List<BattlePoint> points = new ArrayList<BattlePoint>();
        StringTokenizer tok = new StringTokenizer(string);
        try {
            while(tok.hasMoreTokens()) {
                int x = Integer.parseInt(tok.nextToken());
                int y = Integer.parseInt(tok.nextToken());

                points.add(new BattlePoint(x, y));
            }
        }
        catch (NumberFormatException e) {
            throw new RuntimeException("Could not parse x,y string:" + string);
        }
        // Make an array out of the collection
        BattlePoint[] array = points.toArray(new BattlePoint[0]);
        return array;
    }
}
