package ie.ul.o.daysaver;

/**
 * Created by Ali on 17/04/2018.
 */

public class QuickAdd {
    private String id="QUICKADD";
    private String title;
    private String description;
    private long startTime;
    private long endTime;
    private String date;

    public QuickAdd(String id, String title, String description, long startTime, long endTime, String date) {
        this.id = "QUICKADD";
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return
                "description " + description;

    }
}
