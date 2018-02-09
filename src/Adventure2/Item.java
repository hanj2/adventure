package Adventure2;

/**
 * Item in the game has a name and a damage value.
 */
public class Item {
    private String name;
    private Double damage;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getDamage() {
        return damage;
    }
    public void setDamage(Double damage) {
        this.damage = damage;
    }
}
