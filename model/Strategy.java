package model;

import Player.Player;

public interface Strategy {
    /**
     * A Strategy interface to decide the strategy for the computer move based on if the previous move
     * was a hit or miss
     * @param player an object of the player class that represents the computer
     * @param board an object for the human's board
     * @param x x coordinate of the last move
     * @param y y coordinate of the last move
     * @return an integer array whose 0th index tells us if this move has led to a hit or miss, 1st
     * index tells us the x coordinate of the move and 2nd index tells us the yth coordinate of the move
     */
    public int[] execute(Player player, Board board, int x, int y);
}
