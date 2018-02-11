import com.example.AdventureGame;
import com.example.Layout;
import com.example.Load;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LayoutTest {
    private final static String URL_OF_DEFAULT = "https://courses.engr.illinois.edu/cs126/sp2018/adventure/siebel.json";
    private final static String URL_OF_CIRCULAR_JSON = "https://courses.engr.illinois.edu/cs126/sp2018/adventure/circular.json";
    private final static String JSON_FILE = Load.getLoaclFileContent("Default.json");
    private AdventureGame adventure;
    private Layout layout;

    @Before
    public void setUp()throws IOException {
        adventure = new AdventureGame();
        Gson gson = new Gson();
        layout = gson.fromJson(Load.loadSourceCode(URL_OF_DEFAULT), Layout.class);
    }
    @Test
    public void testSearchStartingRoom(){
        String expected = layout.getStartingRoomName();
        assertEquals(expected, layout.searchStartingRoom().getName());
    }
    @Test
    public void testSearchEndingRoom(){
        String expected = layout.getEndingRoomName();
        assertEquals(expected, layout.searchEndingRoom().getName());
    }
    @Test
    public void testRooms(){
        boolean expected = true;
        Layout test = Load.getLayoutFromJson(JSON_FILE);
        if (test.getRooms() != null && layout.getRooms()!= null
                && test.getRooms().length == layout.getRooms().length){
            for (int i = 0; i < layout.getRooms().length; i++){
                if (test.getRooms()[i].equals(layout.getRooms()[i])){
                    expected = false;
                }
            }
        }
        assertTrue(expected);
    }
    @Test
    public void testGetRoomByName(){
        String expected ="AcmOffice";
        assertEquals(expected, layout.getRoomByName(expected).getName());
    }
    @Test
    public void testIsMapValid(){
        assertTrue(layout.isMapValid(layout.getStartingRoomName()));
    }
    @Test
    public void testIsFloorPlanValid() throws IOException {
        assertTrue(layout.isFloorPlanValid());
        Gson gson = new Gson();
        layout = gson.fromJson(Load.loadSourceCode(URL_OF_CIRCULAR_JSON), Layout.class);
        assertTrue(!layout.isFloorPlanValid());
    }
}