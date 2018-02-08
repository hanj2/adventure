package Adventure2;

/**
 * Direction has the direction name and the name of the room the direction is point to.
 */
public class Direction {
    private String directionName;
    private String room;

    public String getDirectionName(){
        return directionName;
    }
    public String getRoom(){
        return room;
    }

    public void setDirectionName(String directionName){
        this.directionName = directionName;
    }
    public void setRoom(String room){
        this.room = room;
    }

    //method to compare two Directions
    public boolean equals(Direction other){
        if ( !directionName.equals(other.directionName) || !room.equals(other.room)) {
            return false;
        }
        return true;
    }
}
