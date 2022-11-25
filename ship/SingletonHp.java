package ship;

/**
 * We want to utilize the Singleton behaviour pattern.
 */
class SingletonHp {
    // Static variable reference of single_instance
    // of type Singleton
    private static SingletonHp instance = null;

    // Declaring a variable of type String
    public Integer hp;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private SingletonHp( Integer hp)
    {
        this.hp = hp;
    }

    // Static method to create instance of Singleton class
    public static SingletonHp getInstance(Integer hp)
    {
        if (instance == null)
            instance = new SingletonHp(hp);
        return instance;
    }

}
