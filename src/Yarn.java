/**
 * @author Nathan Appleby
 * @author Zackary Wake
 * @author Ella Cronk
 */

public class Yarn {
    // The color of the yarn
    private String color;

    // The brand of the yarn
    private String brand;

    // The weight of the yarn
    private float weight;

    // The amount of yarn;
    private float amount;

    public Yarn(String color, String brand, float weight, float amount){
        this.color = color;
        this.brand = brand;
        this.weight = weight;
        this.amount = amount;
    }

    /**
     * Adds an amount of this kind of yarn
     * @param amount the amount to add
     */
    public void addYarn(float amount){
        this.amount += amount;
    }

    /**
     * Removes an amount of this kind of yarn
     * @param amount the amount to remove
     */
    public void useYarn(float amount){
        this.amount -= amount;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    public float getWeight() {
        return weight;
    }

    public float getAmount() {
        return amount;
    }
}
