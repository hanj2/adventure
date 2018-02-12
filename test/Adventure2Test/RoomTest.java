package Adventure2Test;

import Adventure2.*;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoomTest {
    private final static String JSON_FILE = Load.getLocalFileContent("StarWars.json");
    private final static Double ERROR_RANGE = 0.0001;
    private AdventureGame adventure;
    private Layout layout;
    Player player;
    Room current;

    @Before
    public void setUp(){
        adventure = new AdventureGame();
        Gson gson = new Gson();
        layout = gson.fromJson(JSON_FILE, Layout.class);
        player = layout.getPlayer();
        adventure.currentRoomName =layout.getStartingRoomName();
        current = adventure.getCurrentRoom(layout);
    }
    @Test
    public void testGetCurrentItems(){
        assertEquals(1, current.getCurrentItems().size());
        assertEquals("sand", current.getCurrentItems().get(0).getName());
        assertEquals(1, current.showItemsInRoom());
    }
    @Test
    public void testCurrentMonsters(){
        assertEquals("sandman",current.getCurrentMonsters(layout).get(0).getName());
        assertEquals(1, current.showMonstersInRoom(layout));
        assertEquals(0, current.showDefeatedMonsters());
    }
}
