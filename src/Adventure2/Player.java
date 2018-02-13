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
    public ArrayList<String> visitedRoutine = new ArrayList<>();

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
        System.out.println("Experience: " + this.experience);
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
        System.out.println("You attack "+ monster.getName() + " with a damage of " + damage + "!!" );
        Double expectedHealth = monster.health - damage;
        if (expectedHealth < 0){
            System.out.println("Congratulations! You have defeated " + monster.getName());
            System.out.println("You gained " +  getNewExperience(monster) + " experience points!!");
            this.experience += getNewExperience(monster);
            monster.health = expectedHealth;
            room.defeatedMonsters.add(monster);
            this.tryLevelUp();
            isInDuel = false;
            return true;
        }
        monster.health = expectedHealth;
        damage = monster.getAttack() - this.defense;
        System.out.println(monster.getName() +" attack you with a damage of " + damage + "!!" );
        this.health -= damage;
        if (this.health < 0){
            System.out.println(this.name + " ,you are killed by " + monster.getName());
            System.exit(1);
            isInDuel = false;
        }
        System.out.println("Have another try with " + monster.getName() + "!");
        return false;
    }

    //attack with an item
    public boolean attackWithItem(Monster monster, Room room, String itemName) throws IllegalArgumentException {
        itemName = itemName.replace(" " ,"");
        if ( !isItemInHand(itemName)){
            System.out.println("I can't attack with " + itemName);
            isInDuel = false;
            return false;
        }
        Item item = getMapOfItems().get(itemName);
        Double damage = this.getAttack() + item.getDamage() - monster.getDefense();
        Double expectedHealth = monster.health - damage;
        System.out.println("You attack "+ monster.getName() + " with a damage of " + damage + "!!" );
        if (expectedHealth < 0){
            System.out.println("Congratulations! You have defeated " + monster.getName());
            experience = experience + getNewExperience(monster);
            System.out.println("You gained " +  getNewExperience(monster) + " experience points!!");
            monster.health = expectedHealth;
            room.defeatedMonsters.add(monster);
            tryLevelUp();
            isInDuel = false;
            return true;
        }
        monster.health = expectedHealth;
        damage = monster.getAttack() - this.defense;
        this.health -= damage;
        System.out.println(monster.getName() +" attack you with a damage of " + damage + "!!" );
        if (this.health < 0){
            System.out.println(this.name + ", you are killed by " + monster.getName());
            System.exit(1);
            isInDuel = false;
        }
        System.out.println("Have another try with " + monster.getName() + "!");
        return false;
    }

    //the method to disengage, the play should exit the duel.
    // return the damage add on both the player and the monster
    public double disengage(Monster monster, Room room) throws IllegalArgumentException{
        isInDuel = false;
        double damage = this.attack - monster.getDefense();
        System.out.println("You get a damage of " + damage + "!!" );
        Double expectedHealth = monster.health - damage;
        System.out.println(monster.getName() + " gets a damage of " + damage + "!");
        this.health -= damage;
        if (this.health < 0){
            System.out.println(this.name + " ,you are killed by " + monster.getName());
            System.exit(1);
        }
        if (expectedHealth < 0){
            System.out.println("Congratulations! "+ monster.getName() + " is DEAD!");
            this.getNewExperience(monster);
            monster.health = expectedHealth;
            room.defeatedMonsters.add(monster);
            tryLevelUp();
        }
        isInDuel = false;
        System.out.println("You disengage with the duel with " + monster.getName());
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
            System.out.println("Now you get one level up!");
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

    // a function that list all the carrying items
    public void list(){
        StringBuilder carryingList = new StringBuilder();
        if (getCurrentItemsOfPlayer().size() == 0){
            carryingList.append("nothing");
        } else {
            for (int i = 0; i < getCurrentItemsOfPlayer().size(); i++) {
                if (i == 0) {
                    carryingList.append(getCurrentItemsOfPlayer().get(i).getName());
                } else {
                    carryingList.append(" ," + getCurrentItemsOfPlayer().get(i).getName());
                }
            }
        }
        System.out.println("You are carrying " + carryingList);
    }

    // method to carry an item in the room ,
    // if there are still monsters in the room then the item cannot be taken
    public boolean carry(String itemInput, Layout layout, Room current){
        if(!current.getCurrentMonsters(layout).isEmpty()){
            System.out.println("There are still monsters here; I can't take that.");
            return false;
        }
        boolean canCarry = false;
        ArrayList<Item> currentItems = current.getCurrentItems();
        Item takenItem = null;
        if ( !currentItems.isEmpty()){
            for (Item currentItem : currentItems){
                if (currentItem.getName().equalsIgnoreCase(itemInput)){
                    canCarry = true;
                    takenItem = currentItem;
                }
            }
        }
        if (takenItem!= null) {
            takenItems.add(takenItem);
            current.takenItems.add(takenItem);
        } else {
            System.out.println("I can't carry " + itemInput);
        }
        return canCarry;
    }

     // method to drop an item
    public boolean drop(String itemInput, Room current){
        boolean canDrop = false;
        Item droppedItem = null;
        if (!getCurrentItemsOfPlayer().isEmpty()) {
            for (Item item : getCurrentItemsOfPlayer()) {
                if (item.getName().equalsIgnoreCase(itemInput)) {
                    canDrop = true;
                    droppedItem = item;
                    break;
                }
            }
        }
        if (canDrop && droppedItem != null) {
            droppedItems.add(droppedItem);
            current.droppedItems.add(droppedItem);
        } else {
            System.out.println("I can't drop " + itemInput);
        }
        return canDrop;
    }

    //method to show the routine of all rooms the player have visited since the adventure begins
    public String getRoutine(){
        if (visitedRoutine.isEmpty()){
            return ("You are still in the starting room!");
        }
        StringBuilder routine = new StringBuilder("Routine: ");
        for (int i = 0; i < visitedRoutine.size()-1; i++){
            routine.append(visitedRoutine.get(i) + " --> ");
        }
        routine.append(visitedRoutine.get(visitedRoutine.size()-1) + ".");
        return routine.toString();
    }

}
