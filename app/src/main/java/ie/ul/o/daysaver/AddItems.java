package ie.ul.o.daysaver;

/**
 * Created by ollie on 02/04/2018.
 */

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
class AddItems
{
    private ArrayList<Item>items;
    private FileManager man;
    private String creator,listName,status;
    private Context context;
    private  static  Context contxt;

    public AddItems(String creator,String listName,Context context)throws IOException
    {
        System.out.println("I've entered AddItems");
        this.context=context;
        contxt=context;

        this.creator=creator;
        this.listName=listName;
        man=new FileManager(listName,context);
        man.setOwner(this.creator);
        man.setListname(this.listName);
        man.changeFilename();
        man.loadShoppingLists();
        items=new ArrayList<Item>();
        items=man.getShoppingLists();
        System.out.println("All items->"+items);

    }

    public AddItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Item> getNewItem()throws IOException
    {

        boolean duplicate=false;
        ArrayList<Item>newItems=new ArrayList<>();
        for(Item i:items) {
            System.out.println("List found");
            System.out.println("There are " + items.size() + " in there");
            System.out.println("This is your item -> " + i);
            System.out.println("This is All the items->" + items);
            int indexOfItem = 0;
            ArrayList<String> temp = new ArrayList<String>();
            for (Item it : items) {
                temp.add(it.getItemName());
            }
            indexOfItem = temp.indexOf(i.getItemName());
            System.out.println("Item entered is at: " + indexOfItem);
            System.out.println(temp);
            if (temp.contains(i.getItemName())) {
                System.out.println("your item is duplicated");
                //items.add(it);
                duplicate = true;
            } else items.add(i);

            System.out.println("This All the items now-> " + items);
            if (duplicate == true) {
                i.setAmount(items.get(indexOfItem).getAmount() + i.getAmount());
            }
            newItems.add(i);
        }return newItems;


    }


}