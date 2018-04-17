package ie.ul.o.daysaver;

import java.util.ArrayList;

/**
 * Created by Ollie on 05/04/2018.
 */

public class Shopping {
    private ArrayList<ShoppingList>shoppingLists=new ArrayList<>();

    public Shopping(ArrayList<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public Shopping() {
        shoppingLists.add(new ShoppingList());
    }

    public ArrayList<ShoppingList> getShoppingLists() {

        return shoppingLists;
    }

    public void setShoppingLists(ArrayList<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }
}
