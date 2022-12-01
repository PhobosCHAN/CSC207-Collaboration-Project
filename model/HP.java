package model;

public class HP {

    private static HP instance = null;
    /**
     * Starting player HP for the normal mode of Battle Ship where there are ships of sizes: 5, 4, 3, 3, 2
     */
    public int hp_large = 17;
    /**
     * Starting player HP for the fast mode of Battle Ship where there are ships of sizes: 3, 3, 2
     */
    public int hp_small = 8;
    private HP() {}

    public static synchronized HP getInstance(){
        if(instance == null){
            instance = new HP();
        }

        return instance;
    }
}
