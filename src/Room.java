import java.util.ArrayList;

/**
 * a class Room that has the name of room, the description of the room,
 * the items in the room, and the direction it points to.
 */
public class Room {
    private String name;
    private String description;
    private String[] items;
    private Direction[] directions;

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

    /**
     * a method to get a currently uncarried items in the room
     * @param carriedItems currently carried item array list
     * @return rest of the items
     */
    public ArrayList<String> restItems(ArrayList<String> carriedItems){
        ArrayList<String> restItems = new ArrayList<String>();
        if (items.length == 0){
            return restItems;
        }
        for (String item : items){
            restItems.add(item);
        }
        if (carriedItems.size() == 0) {
            return restItems;
        }
        for (String item : restItems) {
            for (String carriedItem : carriedItems){
                if (carriedItem.equals(item)){
                    restItems.remove(item);
                }
            }
        }
        return restItems;
    }

    /**
     * a method to print the rest of the items in the room
     * @param carriedItems items carried by the player
     */
    public void printItemsInRoom(ArrayList<String> carriedItems){
        ArrayList<String> items = this.restItems(carriedItems);
        StringBuilder currentItems = new StringBuilder();
        if (items.size() == 0){
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
