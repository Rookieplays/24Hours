package ie.ul.o.daysaver;

import java.text.SimpleDateFormat;

/**
 * Created by Ollie on 02/04/2018.
 */

class Item
{
    private String itemName="UNKNOWN";
    private double price=0.0;
    private int amount=0;
    private String description="";
    private String timeCreated=new SimpleDateFormat("dd/MMM/yyyy HH:mm").format(System.currentTimeMillis());
    public Item()
    {
        itemName="unknown";
        price=0.00;
        amount=0;
        timeCreated="";
    }
    public Item(String itemName,double price,int amount)
    {
        this.itemName=itemName;
        this.price=price;
        this.amount=amount;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Item(String itemName, double price, int amount, String timeCreated)
    {
        this.itemName=itemName;
        this.price=price;
        this.amount=amount;
        this.timeCreated=timeCreated;
    }

    public Item(String itemName, double price, int amount, String description, String timeCreated) {
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.timeCreated = timeCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName()
    {
        return itemName;
    }
    public void setItemName(String itemName)
    {
        this.itemName=itemName;
    }
    public double getPrice()
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price=price;
    }
    public int getAmount()
    {
        return amount;
    }
    public void setAmount(int amount)
    {
        this.amount=amount;
    }
    public static Item addNewItem(String lineFromText)
    {
        String[] temp = lineFromText.split(":");
        return new Item( temp[0], Double.parseDouble(temp[1]),Integer.parseInt(temp[2]) );
    }
    public String toString()
    {
        return itemName+":"+price+":"+amount;
    }
}