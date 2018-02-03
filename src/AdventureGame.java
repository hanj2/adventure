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

    private static String currentRoomName;
    private static ArrayList<String> currentCarriedItems = new ArrayList<>();

    /**
     * method to complain invalid input
     * @param inValidInput input that is not a command
     */
    public static void complain(String inValidInput){
        System.out.println("I don't understand " + "'" + inValidInput + "'");
    }
    /**
     * a function that list all the carrying items
     */
    public static void list(){
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
        System.out.println("You are carrying " + currentCarriedItems);
    }
    /**
     * a method to move to the next room, change the static variable currentRoomName
     * @param direction input direction
     */
    public static void move(String direction, Layout layout){
        boolean isValidDirection = false;
        Room current = layout.getRoomByName(currentRoomName);
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
    public static void carry(String itemInput, Layout layout){
        Room current = layout.getRoomByName(currentRoomName);
        boolean canCarry = false;
        ArrayList<String> restItems = current.restItems(currentCarriedItems);
        if ( !restItems.isEmpty()){
            for (String restItem : restItems){
                if (restItem.equalsIgnoreCase(itemInput)){
                    canCarry = true;
                }
            }
        }
        if (canCarry) {
            currentCarriedItems.add(itemInput);
        } else {
            System.out.println("I can take " + itemInput);
        }
    }
    /**
     * method to drop an item
     * @param itemInput ietm to drop
     */
    public static void drop(String itemInput){
        boolean canDrop = false;
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
        } else {
            System.out.println("I can't drop " + itemInput);
        }
    }
    /**
     * a function that reads the first word of the reader's input line
     * @param line the whole line of input
     * @return the first word of the whole line
     */
    public static String readStartingWord(String line){
        String[] words = line.split(" ");
        return words[0];
    }
    /**
     * a function that reads the index word of the reader's input line
     * @param line the whole line of input
     * @return the first word of the whole line
     */
    public static String readIndexWord(String line, int index){
        String[] words = line.split(" ");
        return words[index];
    }
    /**
     * method to treat the userInput
     * @param input the input
     */
    public static void read(String input, Layout layout){
        if (input.equalsIgnoreCase(EXIT_COMMAND1) || input.equalsIgnoreCase(EXIT_COMMAND2)){
            System.exit(0);
        }
        String start = readStartingWord(input);
        if (start.equalsIgnoreCase(LIST_COMMAND)){
            list();
        }
        String second = readIndexWord(input,1);
        if (start.equalsIgnoreCase(GO_COMMAND)){
            move(input,layout);
        }
        if (start.equalsIgnoreCase(TAKE_COMMAND)){
            carry(second,layout);
        }
        if (start.equalsIgnoreCase(DROP_COMMAND)){
            drop(second);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String JsonText = LoadFromURL.loadSourceCode(URL_OF_DEFAULT);
        Layout layout = LoadFromURL.getLayoutFromJson(JsonText);

        Room current;
        String startingRoomName = layout.getStartingRoomName();
        String endingRoomName = layout.getEndingRoomName();
        currentRoomName = startingRoomName;


        while(!currentRoomName.equals(endingRoomName)){
            current = layout.getRoomByName(currentRoomName);
            layout.printCurrentDescription(currentRoomName);
            current.printItemsInRoom(currentCarriedItems);
            if (current.restItems(currentCarriedItems).size()!=0 ) {
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();
                if (userInput.equals(LIST_COMMAND)) {
                    list();
                }

            }

        }





//        printVerbatim(startDescription);

//        startingRoom.printDirectionFromRoom();
//        Room test = layout.getRoomByName("SiebelEastHallway");
//        test.printDirectionFromRoom();



    }
}