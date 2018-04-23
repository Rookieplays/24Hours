package ie.ul.o.daysaver;

/**
 * Created by Ollie on 02/04/2018.
 */

import android.content.Context;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
class CreateList
{
    private String listName="",creator,status;
    private long date;
    private LocalTime time;
    private ArrayList<ArrayList<String>>lists;
    private String userAcess;
    private FileManager man;
    private Context context;
    public CreateList(String creator,Context context)throws IOException
    {
        this.context=context;
        //System.out.println()("Ive entered CreateLists");
        this.creator=creator;
        this.status="public";
        this.listName="unknown";
        date=new Date().getTime();

        //System.out.println()(creator+" Made a List:"+listName+". This list is "+status+" was Created on "+date+" at "+time);

        man=new FileManager(context);
        man.setOwner(this.creator);
        man.setStatus(this.status);
        man.setListname(this.listName);
        man.changeFilename();
        man.loadList_Owners();
        man.loadShoppingLists();

        lists=man.getList_Owners();
        //man.savetoList_Owners(this.name);

    }
    public CreateList(String listName,String creator,String status,Context context)throws IOException
    {
        //System.out.println()("Ive entered CreateLists");
        this.context=context;
        this.creator=creator;
        this.listName=listName;
        this.status=status;
        date=new Date().getTime();


        man=new FileManager(this.listName,context);

        man.setOwner(this.creator);
        man.setStatus(this.status);
        man.setListname(this.listName);
        man.changeFilename();
        man.loadList_Owners();
        man.loadShoppingLists();

        lists=man.getList_Owners();

    }
    //new list is added it the user doesn't alredy have a list by that name

    public boolean createNewList()throws IOException
    {
        //load list_Owners
        int indexOfList=lists.get(0).indexOf(man.getListname());//System.out.println()("==="+man.getListname());
        if(indexOfList==-1)
        {

            man.savetoList_Owners(this.listName+"("+this.creator+")"+"->"+this.creator+"->"+this.status+"->"+this.date+" at "+time+"\n");
            //System.out.println()(creator+" Made a List:"+listName+". This list is "+status+" was Created on "+date+" at "+time);

            return true;
        }
        else{

            if(!(lists.get(1).get(indexOfList).equals(this.creator)))
            {
                man.savetoList_Owners(this.listName+"("+this.creator+")"+"->"+this.creator+"->"+this.status+"->"+this.date+" at "+time+"\n");
                //System.out.println()(creator+" Made a List:"+listName+". This list is "+status+" was Created on "+date+" at "+time);

                return true;
            }
            else {
                //System.out.println()(creator+" alredy made the List:"+listName+". This list is "+status+" was Created on "+lists.get(3).get(indexOfList));

                return false;
            }

        }
    }
    public String getName()throws IOException
    {
        return listName;
    }
    public String getListDetails()
    {
        return listName+" is "+status+" and was Created on "+date+" at "+time;
    }

}

