package model;

public class HP {

    private static HP instance = null;
    public int hp_large = 20;
    public int hp_small = 10;
    private HP() {}

    public static synchronized HP getInstance(){
        if(instance == null){
            instance = new HP();
        }

        return instance;
    }
}
