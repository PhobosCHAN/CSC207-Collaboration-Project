package ship;

import model.BattlePoint;

import javax.naming.directory.BasicAttribute;
import javax.swing.*;

public class ShipFactory {

    public Ship getShip(int size, BattlePoint[] body){
        if (size == 5){
            return new Carrier(body);
        } else if ( size == 4) {
            return new Cruiser(body);

        }
        else{
            return new Destroyer(body);
        }
    }
}
