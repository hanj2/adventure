import com.example.AdventureGame;
import com.example.Layout;
import com.example.Load;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdventureGameTest {
    private final static String URL_OF_DEFAULT = "https://courses.engr.illinois.edu/cs126/sp2018/adventure/siebel.json";
    private final static String JSON_FILE = Load.getLoaclFileContent("Default.json");
    private AdventureGame adventure;
    private Layout layout;

    @Before
    public void setUp(){
        adventure = new AdventureGame();
        Gson gson = new Gson();
        layout = gson.fromJson(JSON_FILE, Layout.class);
    }
    @Test
    public void testGetCurrentRoom(){
        String expected  = "SiebelEastHallway";
        adventure.currentRoomName = expected;
        assertEquals(expected, adventure.getCurrentRoom(layout).getName());
    }
    @Test
    public void testMove(){
        String expected = "SiebelEntry";
        adventure.currentRoomName =  "SiebelEastHallway";
        adventure.move("WeSt", layout);
        assertEquals(expected, adventure.currentRoomName);
    }
    @Test
    public void testCarry(){
        String expected = "bagel";
        String expectedFistItem = "coffee";
        adventure.currentRoomName = "SiebelEastHallway";
        adventure.carry(expected, layout);
        assertEquals(expected, adventure.currentCarriedItems.get(0));
        assertEquals(expectedFistItem, layout.getRoomByName("SiebelEastHallway").getCurrentItems().get(0));
    }
    @Test
    public void testDrop(){
        adventure.currentRoomName = "SiebelEastHallway";
        assertEquals(0, layout.getRoomByName("Siebel1314").getCurrentItems().size());
        String expected  = "coffee";
        adventure.carry("coffee", layout);
        adventure.move("South", layout);
        adventure.drop("coffee",layout);
        assertEquals(expected, layout.getRoomByName("Siebel1314").getCurrentItems().get(0));
    }
    @Test
    public void testCountInput(){
        int expected = 3;
        assertEquals(expected, adventure.countInput("la la la"));
        assertEquals(0, adventure.countInput("\n"));
    }
    @Test
    public void testReadStartWord(){
        String expected = "Hello";
        assertEquals(expected, adventure.readStartingWord("Hello World"));
    }
    @Test
    public void testSkipStartWord(){
        String expected = "world hello humans";
        assertEquals(expected, adventure.skipStartingWord("Hello world hello humans"));
    }

}