package Player;

import ship.Ship;

/**
 * Subclass to represent a human player.
 */
public class Human extends Player{
    public Human(String type, Ship[] ships, boolean fastMode) {
        super(type, ships, fastMode);
    }
}
