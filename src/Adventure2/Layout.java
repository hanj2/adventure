package Adventure2;

import java.util.HashMap;

/**
 * com.example.Layout the game environment with a starting room, an ending room, and an array of com.example.Room objects.
 */
public class Layout {
    private final static int PAUSE_TIME_TO_SLEEP = 20;
    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;
    private Player player;
    private Monster[] monsters;

    public String getStartingRoomName(){
        return startingRoom;
    }
    public String getEndingRoomName(){
        return endingRoom;
    }
    public Room[] getRooms(){
        return rooms;
    }
    public Player getPlayer() {
        return player;
    }
    public Monster[] getMonsters() {
        return monsters;
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
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setMonsters(Monster[] monsters) {
        this.monsters = monsters;
    }

    /**
     * method get the startingRoom Object
     * @return the entire starting com.example.Room
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
     * @return the entire ending com.example.Room
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
     * search room by name, so that com.example.Room can be matched with name
     * @param roomName name
     * @return com.example.Room
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
     * @throws InterruptedException throw the exception
     */
    private void printVerbatim(String input) throws InterruptedException {
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
     * @throws InterruptedException no interruption
     */
    public void printCurrentDescription(String currentRoomName) throws InterruptedException, NullPointerException {
        Room current  = getRoomByName(currentRoomName);
        if (current == null){
            return;
        }
        printVerbatim(current.getDescription());
        if (current.visitedTimes > 0 & current.visitedTimes !=1 ){
            printVerbatim("You have visited " + currentRoomName + " " + current.visitedTimes + " times!");
        }
        if (current.visitedTimes ==1 ){
            printVerbatim("You have visited " + currentRoomName + " once!");
        }
        if (currentRoomName.equals(startingRoom)){
            printVerbatim("Your journey begins here");
        }
        if (currentRoomName.equals(endingRoom)){
            printVerbatim("You have reached your final destination");
        }
    }

    /**
     * a method to test whether the starting room is to the ending com.example.Room
     * @param start the starting room to search for
     * @return whether the layout is a valid map
     */
    public boolean isMapValid(String start) throws  NullPointerException{
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
    //a helper function to see if a room is to another room
    private boolean isFloorPlanValidWrapper(String start, String end){
        boolean isValid = false;
        Room current = getRoomByName(start);
        if (current == null){
            return isValid;
        }
        current.isVisited = true;
        Direction[] directions = current.getDirections();
        for (Direction direction : directions){
            String next = direction.getRoom();
            if (next.equals(end)){
                return true;
            }else if (!getRoomByName(next).isVisited){
                isValid = isMapValid(next);
            }
        }
        return isValid;
    }
    /**
     * check whether for every room in the layout, if you can get from room A to B, then you can get from B to A
     * @return whether the layout is floor plan valid
     *  if there is a pair of rooms that is not a floor plan (with a exclusive or testing), return false
     */
    public boolean isFloorPlanValid() throws NullPointerException{
        boolean isValid = true;
        for (int i = 0; i < this.rooms.length; i++){
            for (int j = 0; j < this.rooms.length; j++){
                if (i != j ){
                    String start = rooms[i].getName();
                    String end = rooms[j].getName();
                    if (isFloorPlanValidWrapper(start,end) ^ isFloorPlanValidWrapper(end,start)){
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }
    //create a monster map to connect names and monster
    public HashMap<String, Monster> mapOfMonsters() throws NullPointerException {
        HashMap<String, Monster> mapOfMonsters = new HashMap<>();
        if (monsters == null || monsters.length == 0){
            return mapOfMonsters;
        }
        for (Monster monster: monsters){
            mapOfMonsters.put(monster.getName(), monster);
        }
        return mapOfMonsters;
    }

}
