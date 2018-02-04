import java.util.ArrayList;
import java.util.Arrays;

/**
 * a class Room that has the name of room, the description of the room,
 * the items in the room, and the direction it points to.
 */
public class Room {
    private String name;
    private String description;
    private String[] items;
    private Direction[] directions;
    public ArrayList<String> takenItems = new ArrayList<>();
    public ArrayList<String> droppedItems = new ArrayList<>();

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String[] getItems(){
        return items;
    }
    public Direction[] getDirections(){
        return directions;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setItems(String[] items){
        this.items = items;
    }
    public void setDirections(Direction[] directions){
        this.directions = directions;
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

    public ArrayList<String> getCurrentItems() {
        ArrayList<String> currentItems = new ArrayList<>();
        if(items != null) {
            currentItems.addAll(Arrays.asList(items));
        }
        currentItems.addAll(droppedItems);
        currentItems.removeAll(takenItems);
        return currentItems;
    }

    /**
     * a method to get a currently uncarried items in the room
     * @param carriedItems currently carried item array list
     * @return rest of the items
     */
    public ArrayList<String> restItems(ArrayList<String> carriedItems){
        ArrayList<String> restItems = new ArrayList<String>();
        if ((items == null|| items.length == 0) && droppedItems.isEmpty()){
            return restItems;
        }
        restItems.addAll(Arrays.asList(items));
        restItems.addAll(droppedItems);
        if (carriedItems == null ||carriedItems.isEmpty()) {
            return restItems;
        }
        for (String carriedItem : carriedItems){
            if (restItems.contains(carriedItem)){
                restItems.remove(carriedItem);
            }
        }
        return restItems;
    }
    /**
     * a method to print the rest of the items in the room
     * @param carriedItems items carried by the player
     */
    public void printItemsInRoom(ArrayList<String> carriedItems){
        StringBuilder currentItems = new StringBuilder();
        ArrayList<String> items = getCurrentItems();
        if (items == null || items.isEmpty()){
            currentItems.append("nothing");
        } else {
            for (int i = 0; i < items.size(); i++){
                if (i == 0){
                    currentItems.append(items.get(i));
                }else {
                    currentItems.append(", " + items.get(i));
                }
            }
        }
        System.out.println("This room contains " + currentItems);
    }
    /**
     * a method print all directions from the room
     */
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
}
