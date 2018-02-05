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
    private final static String STAY_COMMAND = "stay";

    public String currentRoomName;
    public ArrayList<String> currentCarriedItems = new ArrayList<>();

    /**
     * get the current room
     * @param layout the layout the game is using
     * @return the current room
     */
    public Room getCurrentRoom(Layout layout){
        return layout.getRoomByName(currentRoomName);
    }
    /**
     * method to complain invalid input if the user type in an invalid command
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
        if (line.equals("") || line.equals("\n")){
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
        if (words.length == 0){
            return null;
        }
        return words[0];
    }
    /**
     * a function that skips the first word of the reader's input line
     * @param line the whole line of input
     * @return the substring without the first word and the fist empty space
     */
    public String skipStartingWord(String line){
        String[] words = line.split(" ");
        if (words.length <= 1){
            return null;
        }else{
            int secondWordIndex = words[0].length();
            return line.substring(secondWordIndex + 1);
        }
    }
    /**
     * a case insensitive method to treat the userInput
     * if the input is just a "\n", let the user to enter again.
     *
     * the cases input line has just one word:
     * if it is "quit" or "exit", stop the program;
     * if it is "list", list all the items the user is carrying
     * if it is "stay", do nothing but tell the user don't be lazy
     * else, complain the invalid input
     *
     * the cases input line has more than one word:
     * if it starts with a "go", check the rest words, move to the next room if the direction input is valid
     * if it starts with a "take", check the rest words, carry the item if it is in the current room,
     * the current items in the room should be changed
     * if it starts with a "drop", check the rest words, drop the item if it is in the current carried items list
     * else, complain the invalid input
     *
     * @param input the input
     */
    public void read(String input, Layout layout){
        int inputLength = countInput(input);
        if (inputLength == 0){
            System.out.println("Please enter again: ");
        }else if (inputLength == 1){
            if (input.equalsIgnoreCase(EXIT_COMMAND1) || input.equalsIgnoreCase(EXIT_COMMAND2)){
                System.exit(0);
            }else if (input.equalsIgnoreCase(LIST_COMMAND)) {
                list();
            }else if (input.equalsIgnoreCase(STAY_COMMAND)){
                System.out.println("You are supposed to finished your journey! Don't be lazy!");
            }else {
                complain(input);
            }
        }else {
            String start = readStartingWord(input);
            //String second = readIndexWord(input,1);
            String skipStart = skipStartingWord(input);
            if (start.equalsIgnoreCase(GO_COMMAND)){
                move(skipStart, layout);
            }else if (start.equalsIgnoreCase(TAKE_COMMAND)){
                carry(skipStart, layout);
            }else if (start.equalsIgnoreCase(DROP_COMMAND)){
                drop(skipStart, layout);
            }else {
                complain(input);
            }
        }
    }

    // the main method to start a new game, every adventure should be a new one.
    public static void main(String[] args) throws IOException, InterruptedException {
        AdventureGame adventure = new AdventureGame();
        Scanner scanner = new Scanner(System.in);
        StringBuilder path = new StringBuilder();
        String filename;
        String JsonText;

        //get the alternative file path from the user
        // if the user didn't choose an alternative one, just use the default layout
        if (args.length > 0){
            for (int i = 0; i < args.length - 1; i++) {
                path.append(args[i]);
            }
            filename = args[args.length - 1];
            JsonText = Load.getFileFromPath(path.toString(), filename);
            System.out.println("You have successfully chosen the local file.");
        }else {
            JsonText = Load.loadSourceCode(URL_OF_DEFAULT);
        }

        //if the input is a bad file, ask the user if he/her want to play the game with the default layout
        //the input must be either 0:YES or 1:NO
        //if the user choose yes, the program will just load the default layout; if no, the program will stop.
        if (JsonText == null){
            System.out.println("Do you want to use the default layout in your adventure game?");
            System.out.println("Please enter one number: 0.YES; 1.NO.");
            String input;
            do {
                input = scanner.nextLine();
                if (input.equals("0")){
                    JsonText = Load.loadSourceCode(URL_OF_DEFAULT);
                }else if(input.equals("1")){
                    System.exit(0);
                }else {
                    System.out.println("Please Enter either 0 or 1!");
                }
            }while ( !input.equals("0") && !input.equals("1"));
        }

        //get the layout of the game from the json file, start from the starting room
        //ensure the layout map is valid, which means there is a way from the starting room to the ending room
        Layout layout = Load.getLayoutFromJson(JsonText);
        if (!layout.isMapValid(layout.getStartingRoomName())){
            System.out.println("The layout JSON is not valid." +
                    "The endingRoom cannot be reached from the starting room.");
        }

        adventure.currentRoomName = layout.getStartingRoomName();
        String endingRoomName = layout.getEndingRoomName();

        //The game will continue until the user types the exit_command or enters the ending room
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
