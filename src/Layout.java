/**
 * Layout the game environment with a starting room, an ending room, and an array of Room objects.
 */
public class Layout{
    private final static int PAUSE_TIME_TO_SLEEP = 20;
    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;

    public void setStartingRoom(String startingRoom){
        this.startingRoom = startingRoom;
    }
    public void setEndingRoom(String endingRoom){
        this.endingRoom = endingRoom;
    }
    public void setRooms (Room[] rooms){
        this.rooms = rooms;
    }
    public String getStartingRoomName(){
        return startingRoom;
    }
    public String getEndingRoomName(){
        return endingRoom;
    }
    public Room[] getRooms(){
        return rooms;
    }

    /**
     * method get the startingRoom Object
     * @return the entire starting Room
     */
    public Room searchStartingRoom(){
        for (Room room : rooms){
            if (room.getName().equals(startingRoom)){
                return room;
            }
        }
        return null;
    }
    /**
     * method get the endingRoom Object
     * @return the entire ending Room
     */
    public Room searchEndingRoom(){
        for (Room room : rooms){
            if (room.getName().equals(endingRoom)){
                return room;
            }
        }
        return null;
    }
    /**
     * search room by name, so that Room can be matched with name
     * @param roomName name
     * @return Room
     */
    public Room getRoomByName(String roomName){
        for (Room room : rooms){
            if (room.getName().equalsIgnoreCase(roomName)){
                return room;
            }
        }
        return null;
    }
    /**
     * helper function to print string verbatim
     * @param input input String
     * @throws InterruptedException
     */
    public void printVerbatim(String input) throws InterruptedException {
        for (int i = 0; i < input.length() - 1; i++ ){
            System.out.print(input.charAt(i));
            Thread.sleep(PAUSE_TIME_TO_SLEEP);
        }
        int last = input.length() - 1;
        System.out.println(input.charAt(last));
    }
    /**
     * a method to print the description of the current room
     * @param currentRoomName name of the current room
     * @throws InterruptedException
     */
    public void printCurrentDescription(String currentRoomName) throws InterruptedException {
        Room current  = getRoomByName(currentRoomName);
        printVerbatim(current.getDescription());
        if (currentRoomName.equals(startingRoom)){
            System.out.println("Your journey begins here");
        }
        if (currentRoomName.equals(endingRoom)){
            System.out.println("You have reached your final destination");
        }
    }

    /**
     * a method to test whether the starting room is to the ending Room
     * @param start the starting room to search for
     * @return whether the layout is a valid map
     */
    public boolean isMapValid(String start){
        boolean isValid = false;
        Room current = getRoomByName(start);
        current.isVisited = true;
        Direction[] directions = current.getDirections();
        for (Direction direction : directions){
            String next = direction.getRoom();
            if (next.equals(endingRoom)){
                return true;
            }else if (!getRoomByName(next).isVisited){
                isValid = isMapValid(next);
            }
        }
        return isValid;
    }
}
