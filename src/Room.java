/**
 * a class Room that has the name of room, the description of the room,
 * the items in the room, and the direction it points to.
 */
public class Room {
    private String name;
    private String description;
    private String[] items;
    private Direction[] directions;

    public Room(){

    }

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
}
