package ie.ul.o.daysaver;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Ollie on 28/03/2018.
 */

public class CE {
    private  String id;
    private String date;
    private long startTime;
    private long endTime;
    private Object var3;
    private String type;
    private String var2;
    private String var1;
    private String Description;

    public CE() {
    }

    public String getDate() {

        return date;
    }
    public String getDescription() {
        this.Description=
                "\nTitle: "+type+
                "\nDate: "+getDate()+
                "\nStart Time: "+new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(getStartTime())+
                "\nEnd Time: "+new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(getEndTime());


        return Description;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Object getVar3() {
        return var3;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVar3(Object var3) {
        this.var3 = var3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public String getId() {
        return id;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public CE(String date, String type, String var1, long startTime, long endTime, String var2 , String id, Object var3) {

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.var3 = var3;
        this.type = type;
        this.var2 = var2;
        this.var1 = var1;
        this.id=id;
    }
}
