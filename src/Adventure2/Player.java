package Adventure2;

import java.util.ArrayList;

public class Player {
    private String name;
    public ArrayList<Item> items;
    public Double attack;
    public Double defense;
    public Double health;
    public Integer level;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
