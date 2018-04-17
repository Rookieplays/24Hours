package ie.ul.o.daysaver;

/**
 * Created by Ollie on 11/04/2018.
 */

public class Subjects {
    private String name="unknown";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subjects(String name) {

        this.name = name;
    }

    public Subjects() {
    }

    @Override
    public String toString() {
        return "Subject: "+name;
    }
}
