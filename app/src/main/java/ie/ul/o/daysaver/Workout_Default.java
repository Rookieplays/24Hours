package ie.ul.o.daysaver;

/**
 * Created by Ali on 03/03/2018.
 */

public class Workout_Default {
    private String Level;
    private boolean Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday;
    private boolean low_Reps,mid_Reps,mid_high_Reps,high_Reps,low_Sets,mid_Sets,mid_high_Sets,high_sets;
    private boolean version1,version2,version3;
    private String muscle_group;

    @Override
    public String toString() {
        return "Workout_Default{\n" +
                "Level='" + Level + '\'' +
                ",\nMonday=" + Monday +
                ",\nTuesday=" + Tuesday +
                ",\nWednesday=" + Wednesday +
                ",\nThursday=" + Thursday +
                ",\nFriday=" + Friday +
                ",\nSaturday=" + Saturday +
                ",\nSunday=" + Sunday +
                ",\nlow_Reps=" + low_Reps +
                ",\nmid_Reps=" + mid_Reps +
                ",\nmid_high_Reps=" + mid_high_Reps +
                ",\nhigh_Reps=" + high_Reps +
                ",\nlow_Sets=" + low_Sets +
                ",\nmid_Sets=" + mid_Sets +
                ",\nmid_high_Sets=" + mid_high_Sets +
                ",\nhigh_sets=" + high_sets +
                ",\nversion1=" + version1 +
                ",\nversion2=" + version2 +
                ",\nversion3=" + version3 +
                ",\nmuscle_group='" + muscle_group + '\'' +
                '}';
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public boolean isMonday() {
        return Monday;
    }

    public void setMonday(boolean monday) {
        Monday = monday;
    }

    public boolean isTuesday() {
        return Tuesday;
    }

    public void setTuesday(boolean tuesday) {
        Tuesday = tuesday;
    }

    public boolean isWednesday() {
        return Wednesday;
    }

    public void setWednesday(boolean wednesday) {
        Wednesday = wednesday;
    }

    public boolean isThursday() {
        return Thursday;
    }

    public void setThursday(boolean thursday) {
        Thursday = thursday;
    }

    public boolean isFriday() {
        return Friday;
    }

    public void setFriday(boolean friday) {
        Friday = friday;
    }

    public boolean isSaturday() {
        return Saturday;
    }

    public void setSaturday(boolean saturday) {
        Saturday = saturday;
    }

    public boolean isSunday() {
        return Sunday;
    }

    public void setSunday(boolean sunday) {
        Sunday = sunday;
    }

    public boolean isLow_Reps() {
        return low_Reps;
    }

    public void setLow_Reps(boolean low_Reps) {
        this.low_Reps = low_Reps;
    }

    public boolean isMid_Reps() {
        return mid_Reps;
    }

    public void setMid_Reps(boolean mid_Reps) {
        this.mid_Reps = mid_Reps;
    }

    public boolean isMid_high_Reps() {
        return mid_high_Reps;
    }

    public void setMid_high_Reps(boolean mid_high_Reps) {
        this.mid_high_Reps = mid_high_Reps;
    }

    public boolean isHigh_Reps() {
        return high_Reps;
    }

    public void setHigh_Reps(boolean high_Reps) {
        this.high_Reps = high_Reps;
    }

    public boolean isLow_Sets() {
        return low_Sets;
    }

    public void setLow_Sets(boolean low_Sets) {
        this.low_Sets = low_Sets;
    }

    public boolean isMid_Sets() {
        return mid_Sets;
    }

    public void setMid_Sets(boolean mid_Sets) {
        this.mid_Sets = mid_Sets;
    }

    public boolean isMid_high_Sets() {
        return mid_high_Sets;
    }

    public void setMid_high_Sets(boolean mid_high_Sets) {
        this.mid_high_Sets = mid_high_Sets;
    }

    public boolean isHigh_sets() {
        return high_sets;
    }

    public void setHigh_sets(boolean high_sets) {
        this.high_sets = high_sets;
    }

    public boolean isVersion1() {
        return version1;
    }

    public void setVersion1(boolean version1) {
        this.version1 = version1;
    }

    public boolean isVersion2() {
        return version2;
    }

    public void setVersion2(boolean version2) {
        this.version2 = version2;
    }

    public boolean isVersion3() {
        return version3;
    }

    public void setVersion3(boolean version3) {
        this.version3 = version3;
    }

    public String getMuscle_group() {
        return muscle_group;
    }

    public void setMuscle_group(String muscle_group) {
        this.muscle_group = muscle_group;
    }

    public Workout_Default(String level,
                           boolean monday,
                           boolean tuesday,
                           boolean wednesday,
                           boolean thursday,
                           boolean friday,
                           boolean saturday,
                           boolean sunday,
                           boolean low_Reps,
                           boolean mid_Reps,
                           boolean mid_high_Reps,
                           boolean high_Reps,
                           boolean low_Sets,
                           boolean mid_Sets,
                           boolean mid_high_Sets,
                           boolean high_sets,
                           boolean version1,
                           boolean version2,
                           boolean version3,
                           String muscle_group) {

        Level = level;
        Monday = monday;
        Tuesday = tuesday;
        Wednesday = wednesday;
        Thursday = thursday;
        Friday = friday;
        Saturday = saturday;
        Sunday = sunday;
        this.low_Reps = low_Reps;
        this.mid_Reps = mid_Reps;
        this.mid_high_Reps = mid_high_Reps;
        this.high_Reps = high_Reps;
        this.low_Sets = low_Sets;
        this.mid_Sets = mid_Sets;
        this.mid_high_Sets = mid_high_Sets;
        this.high_sets = high_sets;
        this.version1 = version1;
        this.version2=version2;
        this.version3=version3;
        this.muscle_group=muscle_group;
    }
}
