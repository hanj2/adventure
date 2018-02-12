package Adventure2Test;

import Adventure2.*;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DuelTest {
    private final static String JSON_FILE = Load.getLocalFileContent("StarWars.json");
    private final static Double ERROR_RANGE = 0.0001;
    private AdventureGame adventure;
    private Layout layout;
    Player player;
    Room room;
    Duel duel;

    @Before
    public void setUp(){
        adventure = new AdventureGame();
        Gson gson = new Gson();
        layout = gson.fromJson(JSON_FILE, Layout.class);
        player = layout.getPlayer();
        adventure.currentRoomName =layout.getStartingRoomName();
        room = adventure.getCurrentRoom(layout);
        duel = new Duel();
    }
    @Test
    public void testGetStatusBarChart(){
        StringBuilder expected = new StringBuilder();
        for (int i = 0; i < 50; i++){
            expected.append("#");
        }
        for (int i = 50; i < 100; i++){
            expected.append("_");
        }
        assertEquals(expected.toString(), duel.getStatusBarChart(0.5));
    }
    @Test
    public void testCountInput(){
        assertEquals(0, duel.countInput(""));
        assertEquals(4,duel.countInput("Hello ALl human beings"));
    }
    @Test
    public void testReadWordByIndex(){
        assertEquals("father", duel.readWordByIndex("I am your father", 3));
        assertEquals(null, duel.readWordByIndex("I am your father", 4));
    }
    @Test
    public void testReadFromIndexWord(){
        assertEquals("am your father", duel.readFromIndexWord("I am your father",1));
        assertEquals("not   true", duel.readFromIndexWord("that's not   true", 1));
    }
    @Test
    public void testReadDuelCommands(){
        Monster monster = room.getCurrentMonsters(layout).get(0);
        assertEquals(Duel.COMPLAIN_TO_USER, duel.readDuelCommands("lalala",monster,room,layout));
        assertEquals(Duel.ATTACK_COMMAND, duel.readDuelCommands("ATTAck",monster,room,layout));
        assertEquals(Duel.ATTACK_WITH_COMMAND, duel.readDuelCommands("ATTAck with lightsaber",monster,room,layout));
        assertEquals(Duel.ATTACK_WITH_COMMAND, duel.readDuelCommands("attack with anakin's lightsaber",monster, room, layout));
    }
}
