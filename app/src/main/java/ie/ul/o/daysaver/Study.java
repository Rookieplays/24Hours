package ie.ul.o.daysaver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ollie on 11/04/2018.
 */

public class Study {
    private String name;
    private String  day;
    private ArrayList<Subjects>subjects=new ArrayList<>();
    private final String id="STUDY";
    private double duration;
    private String date="";
    private String discription;
    private long startAt,endAt;

    public double getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
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

    public Study(String name, String day, ArrayList<Subjects> subjects, double duration, String date) {

        this.name = name;
        this.day = day;
        this.subjects = subjects;
        this.duration = duration;
        this.date = date;
        try {
            startAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse("12:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse("09:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return discription;
    }

    public Study(String name, String day, ArrayList<Subjects> subjects, double duration) {

        this.name = name;
        this.day = day;
        this.subjects = subjects;
        this.duration=duration;
        try {
            startAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse("12:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse("09:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subjects> subjects) {
        this.subjects = subjects;
    }

    public Study() {
        subjects.add(new Subjects());
    }

    public Study(String name, String day, ArrayList<Subjects> subjects) {

        this.name = name;
        this.day = day;
        this.subjects = subjects;
        try {
            startAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse("12:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endAt=(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm").parse("09:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDiscription() {
        discription="Studying...\n";

                for(Subjects s: getSubjects())
                discription+=s+"\n";

        discription+="For "+duration+" Hours";

        return discription;
    }

    public long getStartAt() {
        return startAt;
    }
}
