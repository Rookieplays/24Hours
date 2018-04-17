package ie.ul.o.daysaver;

import java.util.ArrayList;

/**
 * Created by Ollie on 28/03/2018.
 */

public class Gym {
    private ArrayList<WorkoutPlan>  customWorkout=new ArrayList<>();
    private boolean defaultMade;



    public Gym() {
        customWorkout.add(new WorkoutPlan());
    }

    public ArrayList<WorkoutPlan> getCustomWorkout() {
        return customWorkout;
    }

    public void setCustomWorkout(ArrayList<WorkoutPlan> customWorkout) {
        this.customWorkout = customWorkout;
    }

    public Gym(ArrayList<WorkoutPlan> customWorkout, boolean defaultMade) {
        customWorkout.add(new WorkoutPlan());
        this.customWorkout = customWorkout;
        this.defaultMade = defaultMade;
    }



    @Override
    public String toString() {
        return "Gym{" +
                "customWorkout=" + customWorkout +
                ", defaultMade=" + defaultMade +
                '}';
    }

    public boolean isDefaultMade() {
        return defaultMade;
    }

    public void setDefaultMade(boolean defaultMade) {
        this.defaultMade = defaultMade;
    }
}
