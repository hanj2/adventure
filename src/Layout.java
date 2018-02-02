/**
 * Layout the game environment with a starting room, an ending room, and an array of Room objects.
 */
public class Layout{
    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;
    public String getStartingRoom(){
        return startingRoom;
    }
    public String getEndingRoom(){
        return endingRoom;
    }
    public Room[] getRooms(){
        return rooms;
    }
    public void setStartingRoom(String startingRoom){
        this.startingRoom = startingRoom;
    }
    public void setEndingRoom(String endingRoom){
        this.endingRoom = endingRoom;
    }
    public void setRooms (Room[] rooms){
        this.rooms = rooms;
    }
}
