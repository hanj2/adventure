package Adventure2;

/**
 * Monster in the game.
 * a monster has a name, attack value, defense value and health value
 * if the health value is not positive, then the monster is dead
 */
public class Monster {
    private String name;
    private Double attack;
    private Double defense;
    public Double health;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getAttack() {
        return attack;
    }
    public void setAttack(Double attack) {
        this.attack = attack;
    }
    public Double getDefense() {
        return defense;
    }
    public void setDefense(Double defense) {
        this.defense = defense;
    }
}
