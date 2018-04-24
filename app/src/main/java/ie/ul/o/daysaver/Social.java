package ie.ul.o.daysaver;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ollie on 14/03/2018.
 */

public class Social {
    public String heading="";
    public String location="";
    public String date="";
    public String startTime="";
    public String endTime="";
    public String val6="";
    public String val7="";
    public String val8="";
    private String type="";
    private String Discription;
    private ArrayList<ShoppingList> obj;
    private  String id="SOCIAL";
    public  long startAt,endAt;

    public Social(String heading, String location, String date, String startTime, String endTime, String val6, String val7, String val8, String type, ArrayList<ShoppingList> obj, String occurance,String id) {
        this.heading = heading;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        this.type = type;
        this.obj=obj;
        this.occurance = occurance;
        this.id=id;
        try {
            Log.d("Socail","Date: "+date);
            this.startAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(startTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            this.endAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(endTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public long getEndAt() {
        return endAt;
    }

    public void setEndAt(long endAt) {
        this.endAt = endAt;
    }

    public String getId() {
        return id;
    }

    public ArrayList<ShoppingList> getObj() {
        return obj;
    }

    public void setObj(ArrayList<ShoppingList> obj) {
        this.obj = obj;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Social(String heading, String location, String date, String startTime, String endTime, String val6, String val7, String val8, String type, String occurance) {
        this.heading = heading;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        this.type = type;
        this.occurance = occurance;
        try {  Log.d("Socail","Date: "+date);
            startAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(startTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(endTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDiscription() {
        this.Discription="Social event: "+getType()+
                "\nTitle: "+getHeading()+
                "\nLocation: "+getLocation()+
                "\nDate: "+getDate()+
                "\nStart Time: "+getStartTime()+
                "\nEnd Time: "+getEndTime()+
                "\nNotes: "+getVal8();
        if(getVal6()!=null&&! TextUtils.isEmpty(getVal6()))
        this.Discription+="\nInvitation Front Image Link: "+getVal6();
        if(getVal7()!=null&&! TextUtils.isEmpty(getVal7())) {
            if(getType().equalsIgnoreCase("Gaming"))
                this.Discription += "\nGames To Play: " + getVal7();
            else
            this.Discription += "\nInvite BACK Image Link: " + getVal7();
            if(getType().equalsIgnoreCase("Shopping")||getType().equalsIgnoreCase("Sleep-over"))
                this.Discription+=obj;
        }


        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOccurance() {
        return occurance;
    }

    public void setOccurance(String occurance) {
        this.occurance = occurance;
    }

    public Social(String heading, String location, String date, String startTime, String endTime, String val6, String val7, String val8, String occurance) {

        this.heading = heading;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        this.occurance = occurance;
        try {  Log.d("Socail","Date: "+date);
            startAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(startTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(endTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String occurance;

    @Override
    public String toString() {
        return getDiscription();
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVal6() {
        return val6;
    }

    public void setVal6(String val6) {
        this.val6 = val6;
    }

    public String getVal7() {
        return val7;
    }

    public void setVal7(String val7) {
        this.val7 = val7;
    }

    public String getVal8() {
        return val8;
    }

    public void setVal8(String val8) {
        this.val8 = val8;
    }

    public Social() {

    }


    public Social(String heading, String location, String date, String startTime, String endTime, String val6, String val7, String val8) {

        this.heading = heading;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
        try {  Log.d("Socail","Date: "+date);
            startAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(startTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(endTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Social(String heading, String location, String date, String startTime, String endTime) {

        this.heading = heading;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        try {  Log.d("Socail","Date: "+date);
            startAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(startTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse(endTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
