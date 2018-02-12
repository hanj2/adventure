package Adventure2Test;

import Adventure2.*;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {
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
    public void testLayOut(){
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
    @Test
    public void testGetCurrentItemsOfPlayer(){
        assertEquals(2, player.getItems().length);
        assertEquals(2, player.getCurrentItemsOfPlayer().size());
        assertEquals("lightsaber", player.getCurrentItemsOfPlayer().get(0).getName());
        assertEquals("examletOf173", player.getCurrentItemsOfPlayer().get(1).getName());
    }
    @Test
    public void testGetNewExperience(){
        Monster monster = layout.getMonsters()[0];
        assertEquals("sandman",monster.getName());
        Double expectedExperience = ((monster.getAttack() + monster.getDefense())/ 2 + monster.health)* 20;
        assertEquals(7.00, player.getNewExperience(monster),ERROR_RANGE);
    }
    @Test
    public void testToLevelRequirement(){
        assertEquals(25, player.toLevelRequirement(1), ERROR_RANGE);
        assertEquals(50, player.toLevelRequirement(2), ERROR_RANGE);
        assertEquals(82.5, player.toLevelRequirement(3), ERROR_RANGE);
        assertEquals(145.75, player.toLevelRequirement(4), ERROR_RANGE);
    }
    @Test
    public void testPlayerAttack(){
        Monster monster = current.getCurrentMonsters(layout).get(0);
        assertTrue(player.attack(monster,current));
        assertEquals("sandman", current.defeatedMonsters.get(0).getName());
        assertEquals(1, current.defeatedMonsters.size());
        assertFalse(player.tryLevelUp());
        assertEquals(7.0 ,player.experience,ERROR_RANGE);
    }
    @Test
    public void testPlayerAttackWith(){
        Room room = layout.getRoomByName("Naboo");
        Monster monster = room.getCurrentMonsters(layout).get(0);
        assertEquals("Palpatine", monster.getName());
        assertTrue(player.attackWithItem (monster,room,"lightsaber"));
        assertEquals(-0.7, monster.health , ERROR_RANGE);
        assertEquals(13, player.experience, ERROR_RANGE);
    }

}
