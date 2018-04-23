package ie.ul.o.daysaver;

import java.util.ArrayList;

/**
 * Created by Ali on 18/04/2018.
 */

public class Notes {
   private ArrayList<SubNotes>notes=new ArrayList<>();

    public Notes() {
        notes.add(new SubNotes());
    }

    public Notes(ArrayList<SubNotes> notes) {
        this.notes = notes;

    }

    public ArrayList<SubNotes> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<SubNotes> notes) {
        this.notes = notes;
    }
}
