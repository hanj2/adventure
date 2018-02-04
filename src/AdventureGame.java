import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdventureGame {
    private final static String URL_OF_DEFAULT = "https://courses.engr.illinois.edu/cs126/sp2018/adventure/siebel.json";
    private final static String EXIT_COMMAND1 = "quit";
    private final static String EXIT_COMMAND2 = "exit";
    private final static String LIST_COMMAND = "list";
    private final static String GO_COMMAND = "go";
    private final static String TAKE_COMMAND = "take";
    private final static String DROP_COMMAND = "drop";

    public String currentRoomName;
    private ArrayList<String> currentCarriedItems = new ArrayList<>();

    /**
     * get the current room
     * @param layout the layout the game is using
     * @return the current room
     */
    public Room getCurrentRoom(Layout layout){
        return layout.getRoomByName(currentRoomName);
    }
    /**
     * method to complain invalid input
     * @param inValidInput input that is not a command
     */
    public void complain(String inValidInput){
        System.out.println("I don't understand " + "'" + inValidInput + "'");
    }
    /**
     * a function that list all the carrying items
     */
    public void list(){
        StringBuilder carryingList = new StringBuilder();
        if (currentCarriedItems.size() == 0){
            carryingList.append("nothing");
        } else {
            for (int i = 0; i < currentCarriedItems.size(); i++) {
                if (i == 0) {
                    carryingList.append(currentCarriedItems.get(i));
                } else {
                    carryingList.append(" ," + currentCarriedItems.get(i));
                }
            }
        }
        System.out.println("You are carrying " + carryingList);
    }
    /**
     * a method to move to the next room, change the static variable currentRoomName
     * @param direction input direction
     */
    public void move(String direction, Layout layout){
        boolean isValidDirection = false;
        Room current = getCurrentRoom(layout);
        Direction[] directions = current.getDirections();
        for (Direction validDirection : directions){
            if (validDirection.getDirectionName().equalsIgnoreCase(direction)){
                isValidDirection = true;
                break;
            }
        }
        if (isValidDirection) {
            currentRoomName = current.toNextRoom(direction);
        } else {
            System.out.println("I can't go " + direction);
        }
    }
    /**
     * method to carry an item
     * @param itemInput item to carry
     */
    public void carry(String itemInput, Layout layout){
        Room current = getCurrentRoom(layout);
        boolean canCarry = false;
//        ArrayList<String> restItems = current.restItems(currentCarriedItems);
        ArrayList<String> currentItems = current.getCurrentItems();
        if ( !currentItems.isEmpty()){
            for (String currentItem : currentItems){
                if (currentItem.equalsIgnoreCase(itemInput)){
                    canCarry = true;
                }
            }
        }
        if (canCarry) {
            currentCarriedItems.add(itemInput);
            current.takenItems.add(itemInput);
        } else {
            System.out.println("I can't carry " + itemInput);
        }
    }
    /**
     * method to drop an item
     * @param itemInput ietm to drop
     */
    public void drop(String itemInput, Layout layout){
        boolean canDrop = false;
        Room current = getCurrentRoom(layout);
        if ( !currentCarriedItems.isEmpty()) {
            for (String item : currentCarriedItems) {
                if (item.equalsIgnoreCase(itemInput)) {
                    canDrop = true;
                    break;
                }
            }
        }
        if (canDrop) {
            currentCarriedItems.remove(itemInput);
            current.droppedItems.add(itemInput);
        } else {
            System.out.println("I can't drop " + itemInput);
        }
    }

    /**
     * a method to count the number of words in an input line
     * @param line input
     * @return number
     */
    public int countInput(String line){
        if (line.equals("")){
            return 0;
        }
        String[] words = line.split(" ");
        return words.length;
    }
    /**
     * a function that reads the first word of the reader's input line
     * @param line the whole line of input
     * @return the first word of the whole line
     */
    public String readStartingWord(String line){
        String[] words = line.split(" ");
        return words[0];
    }
    /**
     * a function that reads the index word of the reader's input line
     * @param line the whole line of input
     * @return the first word of the whole line
     */
    public String readIndexWord(String line, int index){
        String[] words = line.split(" ");
        return words[index];
    }
    /**
     * method to treat the userInput
     * @param input the input
     */
    public void read(String input, Layout layout){
        int inputLength = countInput(input);
        if (inputLength == 0){
            System.out.println("Please enter again: ");
        }else if (inputLength == 1){
            if (input.equalsIgnoreCase(EXIT_COMMAND1) || input.equalsIgnoreCase(EXIT_COMMAND2)){
                System.exit(0);
            }else if (input.equalsIgnoreCase(LIST_COMMAND)){
                list();
            }else {
                complain(input);
            }
        }else {
            String start = readStartingWord(input);
            String second = readIndexWord(input,1);
            if (start.equalsIgnoreCase(GO_COMMAND)){
                move(second, layout);
            }else if (start.equalsIgnoreCase(TAKE_COMMAND)){
                carry(second, layout);
            }else if (start.equalsIgnoreCase(DROP_COMMAND)){
                drop(second, layout);
            }else {
                complain(input);
            }
        }
    }
    // the main method to start a new game, every adventure should be a new one.
    public static void main(String[] args) throws IOException, InterruptedException {
        AdventureGame adventure = new AdventureGame();
        String JsonText = LoadFromURL.loadSourceCode(URL_OF_DEFAULT);
        Layout layout = LoadFromURL.getLayoutFromJson(JsonText);
        Scanner scanner = new Scanner(System.in);
        adventure.currentRoomName = layout.getStartingRoomName();
        String endingRoomName = layout.getEndingRoomName();
        while(true) {
            Room current = adventure.getCurrentRoom(layout);
            layout.printCurrentDescription(adventure.currentRoomName);
            if (adventure.currentRoomName.equals(endingRoomName)){
                break;
            }
            current.printItemsInRoom(adventure.currentCarriedItems);
            current.printDirectionFromRoom();
            adventure.read(scanner.nextLine(),layout);
        }
        scanner.close();
    }
}