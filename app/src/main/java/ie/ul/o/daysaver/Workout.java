package ie.ul.o.daysaver;

/**
 * Created by Ollie on 26/02/2018.
 */

public class Workout
{
    private String muscle_Group="none",name="none",info="none",img="",level="none";
    private int sets=0,reps=0;
    private String Discription="";

    public Workout(String muscle_group, String name, String info, String img, String level) {
        this.muscle_Group = muscle_group;
        this.name = name;
        this.info = info;
        this.img = img;
        this.level = level;
    }

    public String getDiscription()
    {
        this.Discription="Workout Name: "+getName()+
                "\nMuscle Group: "+getMuscle_Group()+
                "\nSets: "+getSets()+
                "\nReps: "+getReps()+
                "\nInfo: "+getInfo()+
                "\nImage: "+getImg();
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Workout(String muscle_Group, String name, String info, String img, int sets, int reps) {
        this.muscle_Group = muscle_Group;
        this.name = name;
        this.info = info;
        this.img = img;
        this.level = level;
        this.sets = sets;
        this.reps = reps;
    }


    @Override
    public String toString() {
       return  getDiscription();
    }

    public Workout() {

    }

    public String getImg() {
        return img;
    }

    public String getInfo() {
        return info;
    }

    public String getLevel() {
        return level;
    }

    public String getMuscle_Group() {
        return muscle_Group;
    }

    public String getName() {
        return name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setMuscle_Group(String muscle_Group) {
        this.muscle_Group = muscle_Group;
    }

    public void setName(String name) {
        this.name = name;
    }
}
