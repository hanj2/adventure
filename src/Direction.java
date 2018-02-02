/**
 * Direction has the direction name and the name of the room the direction is point to.
 */
public class Direction{
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

}
