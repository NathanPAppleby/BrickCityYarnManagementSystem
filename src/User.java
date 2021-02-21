import java.util.ArrayList;

/**
 * @author Nathan Appleby
 * @author Zackary Wake
 * @author Ella Cronk
 */
public class User {
    // The username of the user, unique
    private String name;

    // The ID of the user, unique
    private int ID;

    //Nickname
    private String nickname;

    private ArrayList<Yarn> yarnList = new ArrayList<>();

    private ArrayList<Project> projectList = new ArrayList<>();

    private ArrayList<Yarn> shoppingCart = new ArrayList<>();

    /**
     *
     * @param name the user's name
     * @param ID the user's ID
     */
    public User(String name, int ID, String nickname)
    {
        this.nickname = nickname;
        this.name = name;
        this.ID = ID;
    }

    /**
     * A function to get the user's name
     * @return the user's name
     */
    public String getName(){
        return name;
    }

    /**
     * A function to get the user's ID
     * @return the user's ID
     */
    public int getID(){
        return ID;
    }

    /**
     * A function to get the user's nickname
     * @return the user's nickname
     */
    public String getNickname(){ return nickname; }

    public ArrayList<Yarn> getYarnList(){ return yarnList; }

    public void addYarnList(Yarn yarn) {
        yarnList.add(yarn);
    }

    public void removeYarnList(Yarn yarn) {
        yarnList.remove(yarn);
    }

    public void addProjectList(Project project) {
        projectList.add(project);
    }

    public void removeProjectList(Project project) {
        projectList.remove(project);
    }

    public ArrayList<Project> getProjectList() {
        return projectList;
    }

    public boolean hasYarn(Yarn yarn) {
        boolean hasYarn = false;
        for (Yarn y : yarnList) {
            if ((y.getWeight() == yarn.getWeight()) &&
                    (y.getBrand().equals(yarn.getBrand())) && (y.getColor().equals(yarn.getColor()))) {
                hasYarn = true;
            }
        }
        return hasYarn;
    }

    public boolean hasYarnInCart(Yarn yarn){
        boolean hasYarn = false;
        for (Yarn y : shoppingCart) {
            if ((y.getWeight() == yarn.getWeight()) &&
                    (y.getBrand().equals(yarn.getBrand())) && (y.getColor().equals(yarn.getColor()))) {
                hasYarn = true;
            }
        }
        return hasYarn;
    }

    public Yarn getYarn(Yarn yarn) {
        for (Yarn y : yarnList) {
            if ((y.getWeight() == yarn.getWeight()) &&
                    (y.getBrand().equals(yarn.getBrand())) && (y.getColor().equals(yarn.getColor()))) {
                return y;
            }
        }
        return null;
    }

    public Yarn getYarnFromCart(Yarn yarn){
        for (Yarn y : shoppingCart) {
            if ((y.getWeight() == yarn.getWeight()) &&
                    (y.getBrand().equals(yarn.getBrand())) && (y.getColor().equals(yarn.getColor()))) {
                return y;
            }
        }
        return null;
    }

    public ArrayList<Yarn> getShoppingCart(){ return shoppingCart; }

    public void cartAdd(Yarn yarn){ shoppingCart.add(yarn); }

    public void cartRemove(Yarn yarn){ shoppingCart.remove(yarn); }

}
