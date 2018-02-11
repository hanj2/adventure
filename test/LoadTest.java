import com.example.AdventureGame;
import com.example.Layout;
import com.example.Load;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LoadTest {
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
    public void testLoadSourceCode() throws IOException {
        String expected = JSON_FILE.replaceAll("\\s+", "");
        assertEquals(expected, Load.loadSourceCode(URL_OF_DEFAULT).replaceAll("\\s+",""));
    }
    @Test
    public void testGetLayoutFromJson(){
        String expectedStart = "MatthewsStreet";
        String expectedEnd = "Siebel1314";
        int expectedRoomNum = 8;
        assertEquals(expectedStart, layout.getStartingRoomName());
        assertEquals(expectedEnd, layout.getEndingRoomName());
        assertEquals(expectedRoomNum, layout.getRooms().length);
    }

}