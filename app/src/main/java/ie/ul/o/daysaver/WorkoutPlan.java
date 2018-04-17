package ie.ul.o.daysaver;

import java.util.ArrayList;

/**
 * Created by Ollie on 28/03/2018.
 */

public class WorkoutPlan {
    private String name="unknown";
    private long Day=System.currentTimeMillis();

    private ArrayList<Workout> Workouts=new ArrayList<>();
    private long D_O_C=System.currentTimeMillis();
    private String UID="Hbot";
    private String id="public";
    private String Discription;
    private String date="";


    public void setWorkout(ArrayList<Workout> Workouts) {
        this.Workouts = Workouts;
    }

    public WorkoutPlan(String name, long Day, ArrayList<Workout> Workouts, long d_O_C, String authurId, String id,String date) {

        this.name = name;
        this.Day = Day;
        this.Workouts = Workouts;
        D_O_C = d_O_C;
        UID = authurId;
    this.date=date;
        this.id = id;
    }

    public ArrayList<Workout> getWorkouts() {
        return Workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        Workouts = workouts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Workout> getWorkout() {
        return Workouts;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String authurId) {
        UID = authurId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDay() {
        return Day;
    }

    public long getD_O_C() {
        return D_O_C;
    }

    public void setD_O_C(long d_O_C) {
        D_O_C = d_O_C;
    }

    public void setDay(long Day) {
        this.Day = Day;
    }


    @Override
    public String toString() {
        return getDiscription();
    }
    public String getDiscription()
    {
        this.Discription="Workout Name: "+getName();
        return Discription;
    }

    public WorkoutPlan() {
        Workouts.add(new Workout());

    }
}
