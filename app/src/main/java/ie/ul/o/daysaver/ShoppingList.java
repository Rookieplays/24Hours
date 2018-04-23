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
    private final String dateCreated=new SimpleDateFormat("dd/MMM/yy HH:mm", Locale.getDefault()).format(System.currentTimeMillis());
    private String status="public";
    private String uid;
    private String description;
    private String author;

    public ShoppingList(String name, ArrayList<Item> items) {
        this.name = name;
        this.items = items;

    }

    public ShoppingList(String name, ArrayList<Item> items, String status, String uid, String author) {
        this.name = name;
        this.items = items;
        this.status = status;
        this.uid = uid;
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        this.description="List Name: "+name+
                "\n"+"Created On: "+dateCreated+
                "\n"+"Created by: "+author+
                "\n"+"Status: "+status+
                "\n"+"Items: "+items;
        return description;
    }

    public ShoppingList(String name, ArrayList<Item> items, String status, String uid) {
        this.name = name;
        this.items = items;
        this.status = status;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShoppingList(String name, ArrayList<Item> items, String status) {

        this.name = name;
        this.items = items;
        this.status = status;
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



    @Override
    public String toString() {
        return "ShoppingList{" +
                "name='" + name + '\'' +
                ", items=" + items +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
