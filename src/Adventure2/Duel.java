package Adventure2;

/**
 * Duel class stored duel methods and duel commands.
 */
public class Duel {
    private static final String ATTACK_COMMAND = "attack";
    private static final String ATTACK_WITH_COMMAND = "with";
    private static final String DISENGAGE_COMMAND = "disengage";
    private static final String STATUS_COMMAND = "status";
    private static final int PERCENTAGE_INT = 100;
    private static final Double TO_LEVEL1_REQUIREMENT = 25.0;
    private static final Double TO_LEVEL2_REQUIREMENT = 50.0;

    /**
     * the method to attack a monster
     * @param player player
     * @param monster monster
     * @return if the player wins, return true; if the player loses, return false; if the player dies, exit(1)
     */
    public boolean attack(Player player, Monster monster, Room room){
        boolean hasPlayerWon = false;
        Double damage = player.getAttack() - monster.getDefense();
        monster.health -= damage;
        if (monster.health < 0){
            hasPlayerWon = true;
            room.defeatedMonsters.add(monster);
            player.getNewExperience(monster);
            return hasPlayerWon;

        }
        damage = monster.getAttack() - player.getDefense();
        player.health -= damage;
        if (player.health < 0){
            System.exit(1);
        }
        return hasPlayerWon;
    }

    //attack with an item
    public boolean attackWithItem(Player player, Monster monster, Room room, Item item){
        boolean hasPlayerWon = false;
        Double damage = player.getAttack() + item.getDamage() - monster.getDefense();
        monster.health -= damage;
        if (monster.health < 0){
            hasPlayerWon = true;
            room.defeatedMonsters.add(monster);
            player.getNewExperience(monster);
            return hasPlayerWon;

        }
        damage = monster.getAttack() - player.getDefense();
        player.health -= damage;
        if (player.health < 0){
            System.exit(1);
        }
        return hasPlayerWon;
    }

    //the method to disengage, the play should exit the duel.
    // return the damage add on both the player and the monster
    public double disengage(Player player, Monster monster, Room room){
        player.isInDuel =false;
        double damage = player.getAttack() - monster.getDefense();
        player.health -= damage;
        monster.health -= damage;
        if (monster.health < 0){
            room.defeatedMonsters.add(monster);
            player.getNewExperience(monster);
        }
        return damage;
    }

    //a helper function to get status bar chart
    public String getStatusBarChart(Double health){
        StringBuilder statusBar = new StringBuilder();
        int statusInInt = (int) Math.ceil(health * PERCENTAGE_INT);
        for (int i = 0; i < PERCENTAGE_INT; i++){
            if (i < statusInInt){
                statusBar.append("#");
            }else {
                statusBar.append("_");
            }
        }
      return statusBar.toString();
    }

    //the method to show the player and the monsters' statues in bar charts
    public void showStatus(Player player, Monster monster){
        String playerBar = getStatusBarChart(player.health);
        String monsterBar = getStatusBarChart(monster.health);
        System.out.println("Player: " + playerBar);
        System.out.println("Monster: " + monsterBar);
    }

    //a helper function to get the levelUp-required experience value
    public Double toLevelRequirement(Double toLevel){
        if(toLevel == 1){
            return TO_LEVEL1_REQUIREMENT;
        }
        if (toLevel == 2){
            return TO_LEVEL2_REQUIREMENT;
        }
        return (toLevelRequirement(toLevel - 1) + toLevelRequirement(toLevel - 2)) * 1.1;
    }






}
