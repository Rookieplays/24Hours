package ie.ul.o.daysaver;

/**
 * Created by Ali on 02/04/2018.
 */

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
class FileManager
{
    /*
        saves newList loads lists
        saves items to lists loads items
        saves users that have access to lists
        when a new list is created a file called List_owners.txt should be created
        this file should hold all the existing lists and people who created it and that can acces it
        with the first user being the owner.
    */
    private String filename,owner,listname,status;
    public int index, indexOfList;
    private ArrayList<ArrayList<String>>list_Owners=new ArrayList<ArrayList<String>>();
    private ArrayList<Item>shoppingLists=new ArrayList<Item>();
    private Context context;
    private String pathname;


    public FileManager(Context context)throws IOException
    {
        this.context=context;
        pathname= context.getFilesDir().getPath() + "/";
        this.filename=pathname+"ShoppingLists/unknown"+(this.index+1)+".txt";
        this.index=0;
        this.status="public";
    }
    public FileManager(String listname,Context context )throws IOException
    {
        this.context=context;
        this.listname=listname;
        pathname= context.getFilesDir().getPath() + "/";
        this.filename=pathname+"ShoppingLists/"+listname+".txt";
        this.status=status;
        this.index=0;
    }
    public void setOwner(String owner)
    {
        this.owner=owner;
    }
    public void changeFilename()
    {
        this.filename=pathname+"ShoppingLists/"+listname+".txt";
    }
    public void setFilename(String filename)
    {
        this.filename=pathname+"ShoppingLists/"+filename+".txt";
    }
    public void setListname(String listname)
    {
        this.listname=listname+"("+getOwner()+")";
    }
    public String getOwner()
    {
        return owner;
    }
    public String getListname()
    {
        return listname;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status=status;
    }
    public void loadList_Owners()throws IOException
    {
        String temp[];
        Scanner fileReader;
        list_Owners.clear();
        for (int i=0;i<4 ;i++ )//creates three arraylist 0,1,2
            list_Owners.add(new ArrayList<String>());

        File file=new File(pathname+"ShoppingLists/List_tracker.txt");
        if(file.exists())
        {
            fileReader=new Scanner(file);
            while(fileReader.hasNext())
            {
                temp=fileReader.nextLine().split("->");
                for (int i=0; i<list_Owners.size();i++ )
                {
                    list_Owners.get(i).add(temp[i]);
                }
            }fileReader.close();
        }
        else savetoList_Owners(pathname+"Filename->owner->public->created on\n");

    }
    public void loadShoppingLists()throws IOException
    {

        Scanner fileReader;
        //creates three arraylist 0,1,2
        shoppingLists.clear();
        String[] temp;
        File file=new File(filename);//System.out.println()("->>>>"+filename);
        if(file.exists())
        {
            fileReader=new Scanner(file);
            while(fileReader.hasNext())
            {
                temp=fileReader.nextLine().split(":");
                shoppingLists.add(new Item( temp[0], Double.parseDouble(temp[1]),Integer.parseInt(temp[2])));
            }
            fileReader.close();
        }
        else savetoList(new Item("Items",0.00,0));
    }
    public void savetoList_Owners(String info)throws IOException
    {
        FileWriter writer=new FileWriter(pathname+"ShoppingLists/List_tracker.txt",true);
        writer.write(info);
        writer.close();

    }
    public void savetoList(Item item)throws IOException
    {
        FileWriter writer=new FileWriter(filename,true);
        writer.write(item.toString()+"\n");
        writer.close();
    }
    public void saveList(ArrayList<Item>items)throws IOException
    {
        int i=0;
        PrintWriter write=new PrintWriter(filename);
        //items.get(indexOfItem).setAmount(items.get(indexOfItem).getAmount()+1);
        while(i < items.size())
        {
            write.println(items.get(i)); //overriding the files previous information with updated information
            i++;
        }write.close();

    }
    public void removeItem(int indexofItem)throws IOException
    {
        if(indexofItem!=-1)
        {
            int i=0;
            PrintWriter write=new PrintWriter(filename);

            //System.out.println()(indexofItem);
            shoppingLists.set(indexofItem,null);
            while(i < shoppingLists.size())
            {
                if(shoppingLists.get(i)!=null)
                    write.println(shoppingLists.get(i));
                //else
                //recycledLists(shoppingLists.get(i).toString()); //overriding the files previous information with updated information
                i++;
            }write.close();
        }
    }
    public void recycledLists(String info)throws IOException
    {
        FileWriter writer=new FileWriter(pathname+"ShoppingLists/Recycle/"+filename,true);
        writer.write(info);
        writer.close();

    }
    public void saveList(ArrayList<Item>items,int indexOfItem,Item it)throws IOException
    {
        int i=0;
        PrintWriter write=new PrintWriter(filename);//System.out.println()(indexOfItem);
        items.set(indexOfItem,new Item(items.get(indexOfItem).getItemName(),items.get(indexOfItem).getPrice(),items.get(indexOfItem).getAmount()+it.getAmount()));
        while(i < items.size())
        {
            write.println(items.get(i)); //overriding the files previous information with updated information
            i++;
        }write.close();

    }
    public boolean createNewList()throws IOException
    {
        //load list_Owners
        int indexOfList=list_Owners.get(0).indexOf(this.listname);
        if(indexOfList==-1)
        {
            savetoList_Owners(this.listname+"-"+owner+"-"+status+"\n");
            return true;
        }
        else{

            if(!(list_Owners.get(1).get(indexOfList).equals(owner)))
            {savetoList_Owners(this.listname+"-"+owner+"-"+status+"\n");return true;}
            else return false;

        }



    }
    public ArrayList<ArrayList<String>> getList_Owners()
    {

        return list_Owners;
    }
    public ArrayList<Item> getShoppingLists()
    {
        //System.out.println()("ARRGGGGHH"+shoppingLists);
        return shoppingLists;
    }
    public boolean doesListExist()
    {
        return list_Owners.get(0).contains(this.listname);
    }
    public boolean isPublic()
    {
        //System.out.println()("->>"+this.listname);
        indexOfList=list_Owners.get(0).indexOf(this.listname);
        if(list_Owners.get(2).get(indexOfList).equals("public"))
        {setStatus("public");return true;}
        else {setStatus("private");return false;}
    }
    public boolean isPrivate()
    {
        //System.out.println()("->>"+this.listname);
        indexOfList=list_Owners.get(0).indexOf(this.listname);
        if(list_Owners.get(2).get(indexOfList).equals("private"))
        {setStatus("private");return true;}
        else {setStatus("public");return false;}
    }
    public boolean saveItems(Item item,String user )throws IOException
    {
        //int indexOfList=list_Owners.get(1).indexOf(user);
        //System.out.println()(item);//System.out.println()("->"+isPublic());
        ////System.out.println()(shoppingLists.contains(item));
        if(isPublic())
        {
            //System.out.println()("saving..");

            //System.out.println()(item);
            savetoList(item);
            return true;
        }
        else
        {	//System.out.println()(user);
            if(list_Owners.get(1).get(indexOfList).equals(user))
            {
                //System.out.println()(shoppingLists);
                //System.out.println()("saving..");
                savetoList(item);
                return true;

            }else return false;

        }
    }
    public boolean saveItems(ArrayList<Item> item,String user,int indexOfItem,Item i)throws IOException
    {
        //int indexOfList=list_Owners.get(1).indexOf(user);
        //System.out.println()(item);//System.out.println()("->"+isPublic());
        if(isPublic())
        {

            //System.out.println()(item);
            saveList(item,indexOfItem,i);
            return true;
        }
        else
        {
            //System.out.println()(getStatus());
            if(list_Owners.get(1).get(indexOfList).equals(user))
            {saveList(item,indexOfItem,i);return true;}
            else return false;
        }
    }

}