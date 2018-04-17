package ie.ul.o.daysaver;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;

/**
 * Created by Ollie on 22/03/2018.
 */

public class ThemeSwither {

    private static int cTheme;
    public final static int MIDNIGHT = 0;
    public final static int AQUA = 1;
    public final static int FOREST = 2;
    public final static int LAVA = 3;
    public final static int CLASSIC = 4;

    public static void changeToTheme(Activity activity, int theme) {
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));

    }
    public static void onActivityCreateSetTheme(Activity activity, LinearLayout headerView)
    {
        switch (cTheme)
        {
            default:
            case MIDNIGHT:
            {
                headerView.setBackground(activity.getResources().getDrawable(R.drawable.midnight_header));

                activity.setTheme(R.style.AppTheme_Midnight);
            }
                break;
            case AQUA:
            {
                headerView.setBackground(activity.getResources().getDrawable(R.drawable.aqua_header));
                activity.setTheme(R.style.AppTheme_Aqua);
            }
                break;
            case FOREST:
            {
                headerView.setBackground(activity.getResources().getDrawable(R.drawable.forest_header));
                activity.setTheme(R.style.AppTheme_Forest);
            }
                break;
            case LAVA:
            {
                headerView.setBackground(activity.getResources().getDrawable(R.drawable.lava_header));
                activity.setTheme(R.style.AppTheme_Lava);
            }
                break;
            case CLASSIC:
            {
                headerView.setBackgroundColor(activity.getResources().getColor(R.color.classic_secondary));
                activity.setTheme(R.style.AppTheme_Classic);
            }
                break;
        }
    }
}

