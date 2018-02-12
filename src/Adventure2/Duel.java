package Adventure2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Duel class stored duel methods and duel commands.
 */
public class Duel {
    public static final String ATTACK_COMMAND = "attack";
    public static final String ATTACK_WITH_COMMAND = "with";
    public static final String DISENGAGE_COMMAND = "disengage";
    public static final String STATUS_COMMAND = "status";
    public static final String COMPLAIN_TO_USER = "INVALID";
    private static final int ATTACK_WITH_COMMAND_LENGTH = 3;
    private static final int PERCENTAGE_INT = 100;

    //a helper function to get status bar chart
    public String getStatusBarChart(Double health) {
        StringBuilder statusBar = new StringBuilder();
        int statusInInt = (int) Math.ceil(health * PERCENTAGE_INT);
        for (int i = 0; i < PERCENTAGE_INT; i++) {
            if (i < statusInInt) {
                statusBar.append("#");
            } else {
                statusBar.append("_");
            }
        }
        return statusBar.toString();
    }
    //the method to show the player and the monsters' statues in bar charts
    public void showStatus(Player player, Monster monster) {
        String playerBar = getStatusBarChart(player.health);
        String monsterBar = getStatusBarChart(monster.health);
        System.out.println("Player: " + playerBar);
        System.out.println("Monster: " + monsterBar);
    }
    //a method to count the number of words in an input line (must be separated by single space)
    public int countInput(String line) {
        if (line == null || line.equals("") || line.equals("\n")) {
            return 0;
        }
        String[] words = line.split(" ");
        return words.length;
    }
    //helper function to read the starting word, index is the index of array of words
    public String readWordByIndex(String line, int index) {
        if (line == null || index < 0) {
            return null;
        }
        String[] words = line.split(" ");
        if (words.length == 0) {
            return null;
        }
        if (index >= words.length) {
            return null;
        }
        return words[index].replaceAll(" ", "");
    }
    /**
     * a function that read from a specific word of the reader's input line
     *
     * @param line the whole line of input
     * @return the substring without the first several words and the fist empty space
     */
    public String readFromIndexWord(String line, int index) {
        if (line == null || index < 0) {
            return null;
        }
        String[] words = line.split(" ");
        if (words.length == 0) {
            return null;
        } else {
            int wordsLength = 0;
            for (int i = 0; i < index; i++) {
                wordsLength += words[i].length();
            }
            return line.substring(wordsLength + 1);
        }
    }

    // method to complain invalid input if the user type in an invalid command
    public void complain(String inValidInput){
        System.out.println("I don't understand " + "'" + inValidInput + "'");
    }

    //a method to read all commands while in duel
    public String readDuelCommands(String command, Monster monster, Room room, Layout layout) {
        Player player = layout.getPlayer();
        if (countInput(command) == 1) {
            switch (command.toLowerCase()) {
                case AdventureGame.EXIT_COMMAND1:
                    System.exit(0);
                    return AdventureGame.EXIT_COMMAND1;
                case AdventureGame.EXIT_COMMAND2:
                    System.exit(0);
                    return AdventureGame.EXIT_COMMAND2;
                case ATTACK_COMMAND:
                    player.attack(monster, room);
                    return ATTACK_COMMAND;
                case DISENGAGE_COMMAND:
                    player.disengage(monster, room);
                    return DISENGAGE_COMMAND;
                case STATUS_COMMAND:
                    showStatus(player, monster);
                    return STATUS_COMMAND;
                case AdventureGame.PLAY_INFO_COMMAND:
                    player.printPlayerInfo();
                    return AdventureGame.PLAY_INFO_COMMAND;
                case AdventureGame.LIST_COMMAND:
                    player.list();
                    return AdventureGame.LIST_COMMAND;
            }
            return COMPLAIN_TO_USER;

        } else if (countInput(command) >= ATTACK_WITH_COMMAND_LENGTH) {
            String first = readWordByIndex(command,0);
            String second = readWordByIndex(command,1);
            String itemName = readFromIndexWord(command, ATTACK_WITH_COMMAND_LENGTH - 1);
            if (first.equalsIgnoreCase(ATTACK_COMMAND) && second.equalsIgnoreCase(ATTACK_WITH_COMMAND)){
                player.attackWithItem(monster,room,itemName);
                return ATTACK_WITH_COMMAND;
            }else {
                complain(command);
                return COMPLAIN_TO_USER;
            }
        } else {
            complain(command);
            return COMPLAIN_TO_USER;
        }
    }

    // the whole duel method
    public boolean duel(Room current, Layout layout, String monsterName){
        Player player = layout.getPlayer();
        ArrayList<Monster> monstersInRoom = current.getCurrentMonsters(layout);
        boolean canDuel = false;
        Monster monster = null;
        if (monstersInRoom != null && !monstersInRoom.isEmpty()) {
            for (Monster monsterInRoom : monstersInRoom){
                if (monsterInRoom.getName().equalsIgnoreCase(monsterName)){
                    monster = monsterInRoom;
                    canDuel = true;
                }
            }
        }
        if (canDuel) {
            layout.getPlayer().isInDuel = true;
            System.out.println("Now you are in duel with " + monsterName);
        }else {
            System.out.println("I can't duel " + monsterName);
        }
        while (player.isInDuel){
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            readDuelCommands(command,monster,current,layout);
        }
        return player.isInDuel;
    }
}
