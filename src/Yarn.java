import java.util.ArrayList;

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
    private int weight;

    // The amount of yarn;
    private int amount;

    public Yarn(String color, String brand, int weight, int amount){
        this.color = color;
        this.brand = brand;
        this.weight = weight;
        this.amount = amount;
    }

    public ArrayList<Yarn> yarnList = new ArrayList<Yarn>();

    /**
     * Adds yarn to database/list
     * @param yarn the yarn object, should already be created/have all info stored
     */
    public void addYarn(Yarn yarn){
        yarnList.add(yarn);
    }

    /**
     * Removes yarn from database/list
     * @param yarn the yarn object
     */
    public void removeYarn(Yarn yarn){
        yarnList.remove(yarn);
    }

    /**
     * Adds an amount of this kind of yarn
     * @param amount the amount to add
     */
    public void addYarnAmount(int amount){
        this.amount += amount;
    }

    /**
     * Removes an amount of this kind of yarn
     * @param amount the amount to remove
     */
    public void removeYarnAmount(int amount){
        this.amount -= amount;
    }

    public ArrayList<Yarn> getYarnList() { return yarnList; }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    public int getWeight() {
        return weight;
    }

    public int getAmount() {
        return amount;
    }

    public String toString(){
        return brand + " in color " + color + ", weight " + weight + ", amount " + amount + "\n";
    }
}
