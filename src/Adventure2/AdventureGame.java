package Adventure2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdventureGame {
    private final static String URL_OF_DEFAULT = "https://courses.engr.illinois.edu/cs126/sp2018/adventure/siebel.json";
    //all valid commands
    public final static String EXIT_COMMAND1 = "quit";
    public final static String EXIT_COMMAND2 = "exit";
    public final static String LIST_COMMAND = "list";
    public final static String PLAY_INFO_COMMAND = "playerinfo";
    private final static String GO_COMMAND = "go";
    private final static String TAKE_COMMAND = "take";
    private final static String DROP_COMMAND = "drop";
    private final static String STAY_COMMAND = "stay";
    private final static String DUEL_COMMAND = "duel";

    public boolean isRunning = false;
    public String currentRoomName;
    public ArrayList<String> currentCarriedItems = new ArrayList<>();
    public Layout layout;

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
    public boolean move(String direction, Layout layout){
        boolean moved = false;
        Room current = getCurrentRoom(layout);
        Direction[] directions = current.getDirections();
        for (Direction validDirection : directions){
            if (validDirection.getDirectionName().equalsIgnoreCase(direction)){
                moved = true;
                break;
            }
        }
        if (moved) {
            currentRoomName = current.toNextRoom(direction);
        } else {
            System.out.println("I can't go " + direction);
        }
        return moved;
    }
    /**
     * method to carry an item
     * @param itemInput item to carry
     * @param layout layout of the game
     */
    public boolean carry(String itemInput, Layout layout){
        Room current = getCurrentRoom(layout);
        if(!current.areAllMonstersDefeated(layout)){
            System.out.println("There are still monsters here; I can't take that.");
            return false;
        }
        boolean canCarry = false;
        ArrayList<Item> currentItems = current.getCurrentItems();
        if ( !currentItems.isEmpty()){
            for (Item currentItem : currentItems){
                if (currentItem.getName().equalsIgnoreCase(itemInput)){
                    canCarry = true;
                }
            }
        }
        if (canCarry) {
            currentCarriedItems.add(itemInput);
            current.takenItems.add(current.getMapOfItems().get(itemInput));
        } else {
            System.out.println("I can't carry " + itemInput);
        }
        return canCarry;
    }
    /**
     * method to drop an item
     * @param itemInput item to drop
     * @param layout the layout of the game
     */
    public boolean drop(String itemInput, Layout layout){
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
            current.droppedItems.add(current.getMapOfItems().get(itemInput));
        } else {
            System.out.println("I can't drop " + itemInput);
        }
        return canDrop;
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
     * it should be able to check the first word , the words except the first word, and complain invalid inputs
     * @param input the input
     */
    public void read(String input, Layout layout){
        int inputLength = countInput(input);
        //if the input is just a "\n", let the user to enter again
        if (inputLength == 0){
            System.out.println("Please enter again: ");
        }else if (inputLength == 1){
            // the cases input line has just one word:
            // "quit" or "exit", stop the program; "list", list all the items the user is carrying
            //  "stay", do nothing but tell the user don't be lazy
            if (input.equalsIgnoreCase(EXIT_COMMAND1) || input.equalsIgnoreCase(EXIT_COMMAND2)){
                System.exit(0);
            }else if (input.equalsIgnoreCase(LIST_COMMAND)) {
                list();
            }else if (input.equalsIgnoreCase(STAY_COMMAND)){
                System.out.println("You are supposed to finish your journey! Don't be lazy!");
            }else if (input.equalsIgnoreCase(PLAY_INFO_COMMAND)){
                layout.getPlayer().printPlayerInfo();
            } else {
                complain(input);
            }
            // the cases input line has more than one word:
            // starts with a "go",move to the next room if the direction input is valid
            // starts with a "take", carry the item if it is in the current room
            // starts with a "drop", check the rest words, drop the item if it is in the current carried items list
        }else {
            String start = readStartingWord(input);
            String skipStart = skipStartingWord(input);
            if (start.equalsIgnoreCase(GO_COMMAND)){
                move(skipStart, layout);
            }else if (start.equalsIgnoreCase(TAKE_COMMAND)){
                carry(skipStart, layout);
            }else if (start.equalsIgnoreCase(DROP_COMMAND)){
                drop(skipStart, layout);
            }else if (start.equalsIgnoreCase(DUEL_COMMAND)){
                AdventureGame adventureGame = new AdventureGame();
                layout.getPlayer().isInDuel = true;
                Duel duel = new Duel();
                duel.duel(adventureGame,input);
            } else{
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
            System.out.println("File not found! Do you want to use the default layout in your adventure game?");
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
        adventure.layout = Load.getLayoutFromJson(JsonText);
        if (!adventure.layout.isMapValid(adventure.layout.getStartingRoomName())){
            System.out.println("The layout JSON is not valid." +
                    "The endingRoom cannot be reached from the starting room.");
        }

        //start playing the game
        adventure.currentRoomName = adventure.layout.getStartingRoomName();
        String endingRoomName = adventure.layout.getEndingRoomName();
        adventure.isRunning = true;

        //The game will continue until the user types the exit_command or enters the ending room
        while(adventure.isRunning) {
            Room current = adventure.getCurrentRoom(adventure.layout);
            adventure.layout.printCurrentDescription(adventure.currentRoomName);
            if (adventure.currentRoomName.equals(endingRoomName)){
                break;
            }
            current.showItemsInRoom();
            current.printDirectionFromRoom();
            adventure.read(scanner.nextLine(),adventure.layout);
        }
        scanner.close();
    }

}
