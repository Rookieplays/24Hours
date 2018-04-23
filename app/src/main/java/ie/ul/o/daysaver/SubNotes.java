package ie.ul.o.daysaver;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Ali on 18/04/2018.
 */

public class SubNotes {
    private String title="UNKNOWN_"+System.currentTimeMillis(),notes;
    private final String dateCreated=new SimpleDateFormat("E, dd/MM/yy • HH:mm a", Locale.getDefault()).format(System.currentTimeMillis());

    public SubNotes(String title, String notes) {
        this.title = title;
        this.notes = notes;
    }

    public SubNotes() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
