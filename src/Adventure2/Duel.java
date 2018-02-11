package Adventure2;

import java.util.Scanner;

/**
 * Duel class stored duel methods and duel commands.
 */
public class Duel {
    private static final String ATTACK_COMMAND = "attack";
    private static final String ATTACK_WITH_COMMAND = "with";
    private static final String DISENGAGE_COMMAND = "disengage";
    private static final String STATUS_COMMAND = "status";
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

    //a method to count the number of words in an input line
    public int countInput(String line) {
        if (line.equals("") || line.equals("\n")) {
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

    //a method to read all commands while in duel
    public void readDuelCommands(String command, Monster monster, AdventureGame adventure, Layout layout) {
        Player player = layout.getPlayer();
        Room room = adventure.getCurrentRoom(layout);
        if (countInput(command) == 1) {
            switch (command.toLowerCase()) {
                case AdventureGame.EXIT_COMMAND1:
                    System.exit(0);
                case AdventureGame.EXIT_COMMAND2:
                    System.exit(0);
                case ATTACK_COMMAND:
                    break;
                case DISENGAGE_COMMAND:
                    player.disengage(monster, room);
                    break;
                case STATUS_COMMAND:
                    showStatus(player, monster);
                    break;
                case AdventureGame.PLAY_INFO_COMMAND:
                    player.printPlayerInfo();
                    break;
                case AdventureGame.LIST_COMMAND:
                    adventure.list();
                    break;
            }
        } else if (countInput(command) == ATTACK_WITH_COMMAND_LENGTH) {
            String first = readWordByIndex(command,0);
            String second = readWordByIndex(command,1);
            String itemName = readFromIndexWord(command, ATTACK_WITH_COMMAND_LENGTH - 1);
            if (first.equalsIgnoreCase(ATTACK_COMMAND) && second.equalsIgnoreCase(ATTACK_WITH_COMMAND)){
                player.attackWithItem(monster,room,itemName);
            }
        } else {
            adventure.complain(command);
        }
    }

    // the whole duel method
    public boolean duel(AdventureGame adventure, Layout layout, String input){
        Scanner scanner = new Scanner(System.in);
        Room current = adventure.getCurrentRoom(layout);
        Player player = layout.getPlayer();
        String monsterName = readFromIndexWord(input, 1);
        Monster monster = layout.mapOfMonsters().get(monsterName);
        if (monster == null){
            System.out.println("I can't duel" + monsterName);
            return false;
        }
        while (player.isInDuel){
            String command = scanner.nextLine();
            readDuelCommands(command,monster,adventure,layout);
        }
        return true;
    }
}
