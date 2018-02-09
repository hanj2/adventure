package Adventure2;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name;
    private Item[] items;
    public Double attack;
    public Double defense;
    public Double health;
    public Integer level;
    public ArrayList<Item> takenItems = new ArrayList<>();
    public ArrayList<Item> droppedItems = new ArrayList<>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Item[] getItems() {
        return items;
    }
    public void setItems(Item[] items) {
        this.items = items;
    }

    public ArrayList<Item> getCurrentItemsOfPlayer(){
        ArrayList<Item> itemsOfPlayer = new ArrayList<>();
        if (items != null) {
            itemsOfPlayer.add((Item) Arrays.asList(this.items));
        }
        itemsOfPlayer.addAll(takenItems);
        itemsOfPlayer.removeAll(droppedItems);
        return itemsOfPlayer;
    }
    public void printPlayerInfo(){
        System.out.println("Player Information:");
        System.out.println("Level: " + this.level);
        System.out.println("Attack: " + this.attack);
        System.out.println("Defence: " + this.defense);
        System.out.println("Health: " + this.health);
    }

}
