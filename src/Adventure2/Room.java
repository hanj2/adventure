package Adventure2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * a class Room that has the name of room, the description of the room,
 * the items in the room, and the direction it points to.
 */
@SuppressWarnings("ALL")
public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private Item[] items;
    public ArrayList<Item> takenItems = new ArrayList<>();
    public ArrayList<Item> droppedItems = new ArrayList<>();
    private String[] monstersInRoom;
    public boolean isVisited = false;
    public ArrayList<Monster> defeatedMonsters = new ArrayList<>();

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public Direction[] getDirections(){
        return directions;
    }
    public Item[] getItems() {
        return items;
    }
    public String[] getNamesOfMonstersInRoom() {
        return monstersInRoom;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setDirections(Direction[] directions){
        this.directions = directions;
    }
    public void setItems(Item[] items) {
        this.items = items;
    }
    public void setMonstersInRoom(String[] monstersInRoom) {
        this.monstersInRoom = monstersInRoom;
    }

    // method to compare two rooms
    public boolean equals(Room other){
        if ( !name.equals(other.name)){
            return false;
        }
        if(directions.length != other.directions.length){
            return false;
        }
        if (directions.length == 0){
            return true;
        }
        for (int i = 0; i < directions.length; i++){
            if (directions[i].equals(other.directions[i])){
                return false;
            }
        }
        return true;
    }
    /**
     *  a method return the next room the user want to go
     * @param direction the direction the user chose
     * @return name of the next room from the input direction, else null
     */
    public String toNextRoom(String direction){
        if (direction.equalsIgnoreCase("quit")
            || direction.equalsIgnoreCase("exit")){
            System.exit(0);
        }
        Direction[] directions = this.getDirections();
        for (Direction validDirection : directions){
            if (direction.equalsIgnoreCase(validDirection.getDirectionName())){
                return validDirection.getRoom();
            }
        }
        return null;
    }
    /**
     * a method to get the current items in the roomm
     * current items = original items + dropped items -taken items
     * @return the current items
     */
    public ArrayList<Item> getCurrentItems() {
        ArrayList<Item> currentItems = new ArrayList<>();
        if(items != null) {
            currentItems.addAll(Arrays.asList(items));
        }
        currentItems.addAll(droppedItems);
        currentItems.removeAll(takenItems);
        return currentItems;
    }

    // a method to print the current items in the room
    public int showItemsInRoom(){
        int itemNum = 0;
        StringBuilder currentItems = new StringBuilder();
        ArrayList<Item> items = getCurrentItems();
        if (items == null || items.isEmpty()){
            currentItems.append("nothing");
        } else {
            itemNum = items.size();
            for (int i = 0; i < items.size(); i++){
                if (i == 0){
                    currentItems.append(items.get(i).getName());
                }else {
                    currentItems.append(", " + items.get(i).getName());
                }
            }
        }
        System.out.println("This room contains " + currentItems);
        return itemNum;
    }

    // a method to get current monsters
    public ArrayList<Monster> getCurrentMonsters(Layout layout) {
        ArrayList<Monster> currentMonsters = new ArrayList<>();
        currentMonsters.addAll(getMonsterList(layout));
        currentMonsters.removeAll(defeatedMonsters);
        return currentMonsters;
    }

    //a method to show all monsters in the room
    public int showMonstersInRoom(Layout layout){
        int monsterNum = 0;
        StringBuilder monsters = new StringBuilder();
        if (getCurrentMonsters(layout).isEmpty()){
            monsters.append("no monsters");
        } else {
            monsterNum = getCurrentMonsters(layout).size();
            for (int i = 0; i < monsterNum; i++){
                if (i == 0){
                    monsters.append(getCurrentMonsters(layout).get(i));
                }else {
                    monsters.append(", " + getCurrentMonsters(layout).get(i));
                }
            }
        }
        System.out.println("This room contains " + monsters);
        return monsterNum;
    }

     // a method print all directions from the room
    public void printDirectionFromRoom(){
        StringBuilder directionNames = new StringBuilder();
        for (int i = 0; i < directions.length; i++){
            String directionName = directions[i].getDirectionName();
            if (i == 0){
                directionNames.append(directionName);
            }else if (i == directions.length - 1){
                directionNames.append(", or " + directionName);
            }else{
                directionNames.append(", " + directionName);
            }
        }
        System.out.println("From here, you can go: " + directionNames);
    }

    //a helper function to get the map of items
    public HashMap<String, Item> getMapOfItems(){
        HashMap<String, Item> mapOfItems = new HashMap<>();
        for (Item item : getCurrentItems()){
            mapOfItems.put(item.getName(), item);
        }
        return mapOfItems;
    }

    //a method to get the map of monsters
    public HashMap<String, Monster> getMapOfMonsters(Layout layout){
        HashMap<String, Monster> mapOfItems = new HashMap<>();
        for (Monster monster : getCurrentMonsters(layout)){
            mapOfItems.put(monster.getName(), monster);
        }
        return mapOfItems;
    }


    // a helper function to get the monsterList in the room
    public ArrayList<Monster> getMonsterList(Layout layout){
        ArrayList<Monster> monsters = new ArrayList<>();
        if (getCurrentMonsters(layout).isEmpty()){
            return monsters;
        }
        for (String monster : monstersInRoom){
            monsters.add(layout.mapOfMonsters().get(monster));
        }
        return monsters;
    }

    //a helper function to check whether all monsters in room are defeated
    public boolean areAllMonstersDefeated(Layout layout){
        boolean areAllDefeated = true;
        if (monstersInRoom == null || monstersInRoom.length == 0){
            return areAllDefeated;
        }
        for (Monster monster : getMonsterList(layout)){
            if (monster.health > 0){
                areAllDefeated = false;
            }
        }
        return areAllDefeated;
    }

    // a helper function to check is the monster in the room and not get defeated
    public boolean isMonsterInRoom(String monsterName, Layout layout){
        if (getCurrentMonsters(layout).isEmpty()){
            return false;
        }
        for (Monster currentMonster : getCurrentMonsters(layout)){
            if(currentMonster.getName().equalsIgnoreCase(monsterName)){
                return true;
            }
        }
        return false;
    }

}
