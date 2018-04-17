package ie.ul.o.daysaver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ollie on 02/04/2018.
 */

public class ShoppingList {
    private String name="UNKNOWN";
    private ArrayList<Item>items=new ArrayList<>();
    private String dateCreated=new SimpleDateFormat("dd/MMM/yy HH:mm", Locale.getDefault()).format(System.currentTimeMillis());

    public ShoppingList(String name, ArrayList<Item> items, String dateCreated) {
        this.name = name;
        this.items = items;
        this.dateCreated = dateCreated;
    }

    public ShoppingList() {
        items.add(new Item());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "name='" + name + '\'' +
                ", items=" + items +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
