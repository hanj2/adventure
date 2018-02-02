import java.io.IOException;
import java.util.Scanner;

public class AdventureGame {
    private final static String URL_OF_DEFAULT = "https://courses.engr.illinois.edu/cs126/sp2018/adventure/siebel.json";
    private final static int PAUSE_TIME_TO_SLEEP = 77;
    public static String currentRoom;

    public static void printVerbatim(String input) throws InterruptedException {
        for (int i = 0; i < input.length() - 1; i++ ){
            System.out.print(input.charAt(i));
            Thread.sleep(PAUSE_TIME_TO_SLEEP);
        }
        int last = input.length() - 1;
        System.out.println(input.charAt(last));
    }
    public static void printDescription(String currentRoom) throws InterruptedException {
        printVerbatim(currentRoom);


    }

    public static void toTheNextRoom(){
        Scanner scanner = new Scanner(System.in);


    }




    public static void main(String[] args) throws IOException, InterruptedException {
        String JsonText = LoadFromURL.loadSourceCode(URL_OF_DEFAULT);
        Layout layout = LoadFromURL.getLayoutFromJson(JsonText);
        String startingRoomName = layout.getStartingRoomName();
        currentRoom = startingRoomName;

        Room startingRoom = layout.getRoomByName(startingRoomName);
        String startDescription = startingRoom.getDescription();
//        printVerbatim(startDescription);
//        startingRoom.printItemsInRoom();
//        startingRoom.printDirectionFromRoom();
        Room test = layout.getRoomByName("SiebelEastHallway");
        test.printDirectionFromRoom();



    }
}