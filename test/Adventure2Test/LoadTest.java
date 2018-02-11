package Adventure2Test;
import Adventure2.AdventureGame;
import Adventure2.Layout;
import Adventure2.Load;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoadTest {
    private final static String JSON_FILE = Load.getLocalFileContent("StarWars.json");
    private AdventureGame adventure;
    private Layout layout;

    @Before
    public void setUp(){
        adventure = new AdventureGame();
        Gson gson = new Gson();
        layout = gson.fromJson(JSON_FILE, Layout.class);
    }
    @Test
    public void testLoad(){
        String expectedStart = "Tatooine";
        String expectedEnd = "DeathStar";
        String expectedPlayer = "Luke Skywalker";
        int expectedRoomNum = 7;
        int expectedMonstersNum = 6;
        assertEquals(expectedStart, layout.getStartingRoomName());
        assertEquals(expectedEnd, layout.getEndingRoomName());
        assertEquals(expectedRoomNum, layout.getRooms().length);
        assertEquals(expectedPlayer, layout.getPlayer().getName());
        assertEquals(expectedMonstersNum,layout.getMonsters().length);
    }

}
