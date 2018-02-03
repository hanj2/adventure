import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdventureGame {
    private final static String URL_OF_DEFAULT = "https://courses.engr.illinois.edu/cs126/sp2018/adventure/siebel.json";
    private final static String EXIT_COMMAND1 = "quit";
    private final static String EXIT_COMMAND2 = "exit";
    public static String currentRoomName;
    public static ArrayList<String> currentCarriedItems = new ArrayList<>();


    public static void main(String[] args) throws IOException, InterruptedException {
        String JsonText = LoadFromURL.loadSourceCode(URL_OF_DEFAULT);
        Layout layout = LoadFromURL.getLayoutFromJson(JsonText);
        String startingRoomName = layout.getStartingRoomName();
        currentRoomName = startingRoomName;

        Room test = layout.getRoomByName(startingRoomName);
        test.printItemsInRoom(currentCarriedItems);

        Scanner scanner = new Scanner(System.in);
        String userInput;
        do {
            userInput = scanner.nextLine();






        }while( !userInput.equalsIgnoreCase(EXIT_COMMAND1) || !userInput.equalsIgnoreCase(EXIT_COMMAND2));
        System.exit(0);



//        printVerbatim(startDescription);

//        startingRoom.printDirectionFromRoom();
//        Room test = layout.getRoomByName("SiebelEastHallway");
//        test.printDirectionFromRoom();



    }
}