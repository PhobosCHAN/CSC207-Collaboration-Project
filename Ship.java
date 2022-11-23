
public class Ship {
    String type;
    int size;
    int hp;

    public Ship(String type, int size) {
        this.type = type;
        this.size = size;
    }

    public int getSize(){
        return this.size;
    }

    public String getType(){

        return this.type;
    }
}
