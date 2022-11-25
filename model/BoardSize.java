package model;

public class BoardSize {
    private static BoardSize instance = null;

    // Declaring a variable of type String
    public int size;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private BoardSize( )
    {
        this.size = 10;
    }

    // Static method to create instance of Singleton class
    public static BoardSize getInstance()
    {
        if (instance == null)
            instance = new BoardSize();
        return instance;
    }
}
