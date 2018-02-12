package Adventure2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Player in the game.
 * The player has a name, an array of items, an attack value, a defense value, a health value;
 * a level and an experience value which are both 0 at first;
 * a list of taken items and a list of dropped items.
 */
public class Player {
    private static final Double TO_LEVEL1_REQUIREMENT = 25.0;
    private static final Double TO_LEVEL2_REQUIREMENT = 50.0;
    private String name;
    private Item[] items;
    private Double attack;
    private Double defense;
    public Double health;
    public Integer level;
    public Boolean isInDuel = false;
    public Double experience = 0.0;
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
        if (items == null || items.length == 0){
            return itemsOfPlayer;
        }
        itemsOfPlayer.addAll(Arrays.asList(this.items));
        itemsOfPlayer.addAll(takenItems);
        itemsOfPlayer.removeAll(droppedItems);
        return itemsOfPlayer;
    }

    //a method to check if an item is on the player's hand
    public boolean isItemInHand(String itemName) throws IllegalArgumentException{
        if (getCurrentItemsOfPlayer().isEmpty()){
            return false;
        }
        for (Item item : getCurrentItemsOfPlayer()){
            if (item.getName().equalsIgnoreCase(itemName)){
                return true;
            }
        }
        return false;
    }

    //print the player's information
    public void printPlayerInfo(){
        System.out.println("Player Information:");
        System.out.println("Level: " + this.level);
        System.out.println("Attack: " + this.attack);
        System.out.println("Defence: " + this.defense);
        System.out.println("Health: " + this.health);
    }

    // a helper function to add gained experience value to the player
    // return the new experience value
    //2 and 20? I have no idea with those magic numbers; it's the formula in the requirement
    public double getNewExperience(Monster monster){
        double gainedExperience = ((monster.getAttack() + monster.getDefense())/ 2 + monster.health)* 20;
        return gainedExperience;
    }

    /**
     * the method to attack a monster
     * @param monster monster
     * @return if the player wins, return true; if the player loses, return false; if the player dies, exit(1)
     */
    public boolean attack(Monster monster, Room room) throws IllegalArgumentException{
        Double damage = this.attack - monster.getDefense();
        Double expectedHealth = monster.health - damage;
        if (expectedHealth < 0){
            this.experience += getNewExperience(monster);
            monster.health = expectedHealth;
            room.defeatedMonsters.add(monster);
            this.tryLevelUp();
            return true;
        }
        monster.health = expectedHealth;
        damage = monster.getAttack() - this.defense;
        this.health -= damage;
        if (this.health < 0){
            System.out.println(this.name + " ,you are killed by " + monster.getName());
            System.exit(1);
        }
        return false;
    }

    //attack with an item
    public boolean attackWithItem(Monster monster, Room room, String itemName) throws IllegalArgumentException {
        if ( !isItemInHand(itemName)){
            System.out.println("I can't attack with " + itemName);
            return false;
        }
        Item item = getMapOfItems().get(itemName);
        Double damage = this.getAttack() + item.getDamage() - monster.getDefense();
        Double expectedHealth = monster.health - damage;
        if (expectedHealth < 0){
            experience = experience + getNewExperience(monster);
            monster.health = expectedHealth;
            room.defeatedMonsters.add(monster);
            tryLevelUp();
            return true;
        }
        monster.health = expectedHealth;
        damage = monster.getAttack() - this.defense;
        this.health -= damage;
        if (this.health < 0){
            System.out.println(this.name + " ,you are killed by " + monster.getName());
            System.exit(1);
        }
        return false;
    }

    //the method to disengage, the play should exit the duel.
    // return the damage add on both the player and the monster
    public double disengage(Monster monster, Room room) throws IllegalArgumentException{
        isInDuel = false;
        double damage = this.attack - monster.getDefense();
        this.health -= damage;
        Double expectedHealth = monster.health - damage;
        if (this.health < 0){
            System.out.println(this.name + " ,you are killed by " + monster.getName());
            System.exit(1);
        }
        if (expectedHealth < 0){
            this.getNewExperience(monster);
            monster.health = expectedHealth;
            room.defeatedMonsters.add(monster);
            tryLevelUp();
            //regain the health points they lost
            this.health += damage;
        }
        return damage;
    }

    //a helper function to get the levelUp-required experience value
    public Double toLevelRequirement(Integer toLevel){
        if(toLevel == 1){
            return TO_LEVEL1_REQUIREMENT;
        }
        if (toLevel == 2){
            return TO_LEVEL2_REQUIREMENT;
        }
        return (toLevelRequirement(toLevel - 1) + toLevelRequirement(toLevel - 2)) * 1.1;
    }

    //a helper function to see if the player can level up, if can, level up and return true; else: return false
    public boolean tryLevelUp(){
        if (experience >= toLevelRequirement(level + 1)){
            level += 1;
            attack *= 1.5;
            defense *= 1.5;
            health *= 1.3;
            return true;
        }else{
            return false;
        }
    }

    //a helper function to get the map of items of Player
    public HashMap<String, Item> getMapOfItems(){
        HashMap<String, Item> mapOfItems = new HashMap<>();
        if (getCurrentItemsOfPlayer() == null || getCurrentItemsOfPlayer().isEmpty()){
            return null;
        }
        for (Item item : getCurrentItemsOfPlayer()){
            mapOfItems.put(item.getName(), item);
        }
        return mapOfItems;
    }
}
