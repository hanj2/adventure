package Adventure2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Player in the game.
 * The player has a name, an array of items, an attack value, a defense value, a health value;
 * a level and an experience value which are both 0 at first;
 * a list of taken items and a list of dropped items.
 */
public class Player {
    private String name;
    private Item[] items;
    private Double attack;
    private Double defense;
    public Double health;
    public Integer level = 0;
    public Boolean isInDuel = false;
    public Integer experience = 0;
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
    public Double getAttack() {
        return attack;
    }
    public void setAttack(Double attack) {
        this.attack = attack;
    }
    public Double getDefense() {
        return defense;
    }
    public void setDefense(Double defense) {
        this.defense = defense;
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
