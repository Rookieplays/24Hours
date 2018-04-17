package ie.ul.o.daysaver;

/**
 * Created by Ollie on 26/03/2018.
 */

class CreatedEvents {
private String date;
private String type;
private String var1;
private long startTime;
private String var2;
private String id;
private long endTime;
private Object var3;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
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

    public void setVar3(Object var3) {
        this.var3 = var3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CreatedEvents() {
    }

    public CreatedEvents(String date, String type, String var1, long startTime, long endTime, String var2 , String id, Object var3) {

        this.date = date;
        this.type = type;
        this.var1 = var1;
        this.startTime = startTime;
        this.var2 = var2;
        this.endTime = endTime;
        this.var3 = var3;
        this.id=id;
    }
}
