package ship;

import model.BattlePoint;

/**
 * A ShipFactory class that creates a ship based on the given size.
 */
public class ShipFactory {

    /**
     * getShip function that returns a ship object based on the provided size
     * @param size the size of the ship
     * @param body a list of BattlePoints of the ship
     * @param vertical a boolean for if the ship is vertical or not
     * @return a ship object.
     */
    public Ship getShip(int size, BattlePoint[] body, boolean vertical){
        if (size == 5){
            return new Carrier(body, vertical);
        } else if ( size == 4) {
            return new Cruiser(body, vertical);

        }
        else{
            return new Destroyer(body, vertical);
        }
    }
}
