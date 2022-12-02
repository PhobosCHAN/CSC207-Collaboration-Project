package model;

import Player.Player;

public interface Strategy {
    public int[] execute(Player player, Board board, int x, int y);
}
