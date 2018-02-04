import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LayoutTest {
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
    public void testGetRoomByName(){
        String expected ="AcmOffice";
        assertEquals(expected, layout.getRoomByName(expected).getName());
    }
    @Test
    public void testIsMapValid(){
        assertTrue(layout.isMapValid(layout.getStartingRoomName()));
    }
}