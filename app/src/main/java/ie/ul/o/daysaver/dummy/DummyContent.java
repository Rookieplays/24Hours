package ie.ul.o.daysaver.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add Video Links.
        addItem(new DummyItem("1","Login, Registration and Setup in 24H",
                "https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Flrs.mp4?alt=media&token=7fa1a8a3-300a-4a9a-98ae-4fd8fb65b1fc","Login • If You are Registered with us,You will be directed to your Main Screen otherwise if you\'ve forgotten Your Password click sign in and you would be return to this page, then click on the forgot password link.\n\n\n" +
                "    Register • Click on the new members link to sign in. fill in the field and Hey presto! that\'s it \n\n" +
                "    Setup • Enter Username, Click the icon to set profile pictures, ! make sure your file isn\'t too big aim for 100kb or lower, click next fill in the gap and click done, read through the setup page, be patient and you're ready to Start"
                ));
        addItem(new DummyItem("2", "Themes in 24H",
                "https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fthemes.mp4?alt=media&token=93697823-fde1-4234-8421-cbcc67865349","Themes • click the top left side corner menu for a dropdown of option ar hold Hbot for different themes"
                ));
        addItem(new DummyItem("3", "How to use Gym in 24H",
                "https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fgym.mp4?alt=media&token=f01737b7-de1f-4419-bed1-5631fc79aa55","Gym •\n" +
                "    \nDefault: select a body type, select a muscle group, hit save or change setting to your prefrence, hold the top for default option or tap to select all. When you are done hit finshed or if you need to change anything hit reset.\n" +
                "    \nCustom: select one of your own workout or one from the community, you can add it to your calender if you want. Add your own plan by hitting the add Plan button. reload to see changes and Boom you're Done!"));
        addItem(new DummyItem("4","Custom Gym Tips","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fgymtips.mp4?alt=media&token=54d7f3e1-4320-4ea1-ab46-b22bb8eb1844","Watch the Video!"));
        addItem(new DummyItem("5","How to use Social in 24H","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fsocial.mp4?alt=media&token=62bb71a0-0da3-4ef9-8077-58a744bb5138","Select one of the provided Templates or Create Your Own via the circular Button Thingy, the rest is simple"));
        addItem(new DummyItem("6","How to Create a Custom Social event.","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2FcustomS.mp4?alt=media&token=564c6862-e46f-4277-a035-ca07d847689a","enjoy!"));
        addItem(new DummyItem("7","Budget $hopper Tips","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2FbsTips.mp4?alt=media&token=0f4606bc-54ff-4553-b7a4-2d664c2d2554","enjoy!"));

        addItem(new DummyItem("8","How to use Study in 24H","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fstudy.mp4?alt=media&token=23c13f02-a883-4fc9-a806-6a9b76ea1e7c","Enter Your Subjects, select subjects to study hit the CHECK MARK AFTER EVERY DAY to save, the  blue button to save and close"));
        addItem(new DummyItem("9","How to use Quick note in 24H","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fqplan.mp4?alt=media&token=eced8d01-8592-422f-82fe-106ee78a5d1d",""));
        addItem(new DummyItem("10","How to use Notes in 24H","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fnotes.mp4?alt=media&token=9ff727b5-5eee-4a1f-a76f-86d74104ade7","Watch the Video!"));
        addItem(new DummyItem("11","How to use Calendar in 24H","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fcalendar.mp4?alt=media&token=8b8894bb-3fdc-4d35-8018-82dd1257b02c","enjoy!"));
        addItem(new DummyItem("12","Home • 24H","https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/tutorial_vids%2Fmain.mp4?alt=media&token=f62a2bed-3a98-49cf-95ec-9a8224b65015","enjoy!"));

    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        String temp[]=new String[ITEMS.size()];
        for(int i=0;i<temp.length;i++)
        {
            temp[i]=ITEMS.get(i).getVideo_name();
        }
        return new DummyItem(String.valueOf(position), temp[position], makeDetails(position),"");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String video_name;
        public final String video_url;
        public final String video_desc;

        public DummyItem(String id, String video_name, String video_url, String video_desc){
            this.id = id;
            this.video_name = video_name;
            this.video_url = video_url;
            this.video_desc=video_desc;
        }

        public String getId() {
            return id;
        }

        public String getVideo_name() {
            return video_name;
        }

        public String getVideo_url() {
            return video_url;
        }

        public String getVideo_desc() {
            return video_desc;
        }

        @Override
        public String toString() {
            return video_name;
        }
    }
}
