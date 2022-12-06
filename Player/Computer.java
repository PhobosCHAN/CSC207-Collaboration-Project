package Player;

import ship.Ship;

/**
 * Subclass to represent the computer.
 */
public class Computer extends Player{
    public Computer(String type, Ship[] ships, boolean fastMode) {
        super(type, ships, fastMode);
    }
}
