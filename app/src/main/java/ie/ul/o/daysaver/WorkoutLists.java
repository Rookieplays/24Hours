package ie.ul.o.daysaver;

/**
 * Created by Ollie on 26/03/2018.
 */

public class WorkoutLists {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public WorkoutLists(String name, String info, String level, String imgUrl) {
        this.name = name;
        this.info = info;
        this.level = level;
        this.imgUrl = imgUrl;
    }

    private String name;
    private String info;
    private String level;
    private String imgUrl;

}
