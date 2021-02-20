import java.util.ArrayList;

public class ShoppingCart {
    private Project project;

    public ShoppingCart(Project project){
        this.project = project;
    }

    public ArrayList<Project> shoppingCartList = new ArrayList<>();

    public void addToCart(Project p){
        shoppingCartList.add(p);
    }

    public void removeFromCart(Project p){
        shoppingCartList.remove(p);
    }

    public ArrayList<Project> getCart(){
        return shoppingCartList;
    }
}
