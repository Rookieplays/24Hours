package ie.ul.o.daysaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Ali on 13/04/2018.
 */

public class slideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public slideAdapter(Context context)
    {
        this.context=context;

    }
    public int [] slide_layout=
            {

                    R.drawable.female_runner_painted,
                    R.drawable.studying_gal,
                    R.drawable.ref5,
                    R.drawable.quick_event,
                    android.R.drawable.ic_menu_my_calendar,
                    android.R.drawable.ic_media_play
            };
    public String[] slide_headings=
            {
                    "24H.G",
                    "24H.SP",
                    "24H.S",
                    "24H.QA",
                    "24H.C",
                    "24H.TUT"
            };
    public String[] slide_description=
            {
                    context != null ? context.getString(R.string.gymDesc) : "Hey There, This is 24H.GM A.K.A Gym. Ever wanted to create a workout plan for your daily/weekly trip to the Gym?\n" +
                            "Here you have the ability to choose our default Gym plan our create one of you're very own. YOu can also View Workout Plans Made by the Community.",
                    context != null ? context.getString(R.string.study) : "24H.SP allows you to create a study plan for any day of the week, fast and simple.",
                    context != null ? context.getString(R.string.socialDesc) : "Hi again, 24H.S is A Social organizer, where you create events for common social activities,\n You want to set up a plan to go to the Cinema well you can, or How about create a Budget for your grocceries, 24H.B$ allows you to just that",
                    context != null ? context.getString(R.string.quickevent) : "Dear Students, Good news you\'re able to create a Timetable for for classes ☺, No more confused about what classes you have and when. Not saying that you do.",
                    context != null ? context.getString(R.string.Calendar) : "View All Created events Here",
                    context != null ? context.getString(R.string.tutorialDesc) : "So that\'s it, Confused well visit our Tutorial segment for help"

            };

    @Override
    public int getCount() {
        amountOfPages=slide_headings.length;
        return slide_headings.length;
    }
    public void startAnimation3(View v,char type)
    {

        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1.2f),
                PropertyValuesHolder.ofFloat("scaleX",1.2f));

        grow.setDuration(1000);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1f),
                PropertyValuesHolder.ofFloat("scaleX",1f));
        grow.setRepeatMode(ObjectAnimator.RESTART);
        grow.setRepeatCount(ObjectAnimator.INFINITE);
        shrink.setDuration(1000);

        ObjectAnimator rotate=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("rotationX",35f),
                PropertyValuesHolder.ofFloat("rotationX",0f));
        shrink.setDuration(500);

        AnimatorSet animatorSet2=new AnimatorSet();
        animatorSet2.playSequentially(grow,shrink,rotate);
        animatorSet2.setDuration(1500);

        animatorSet2.start();
    }
    static  int amountOfPages;
    public static int getPages()
    {
        return amountOfPages;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int pos)
    {
        layoutInflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.slide_layout_main,container,false);
        ImageView slideImageView=v.findViewById(R.id.slide_image);
        TextView slideHeading=v.findViewById(R.id.slide_headings);
        TextView slideDesc=v.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_layout[pos]);
        slideHeading.setText(slide_headings[pos]);
        slideDesc.setText(slide_description[pos]);
        if(pos==5) {
            startAnimation3(slideImageView, '0');
            slideImageView.setOnClickListener(e->{context.startActivity(new Intent(context,VideoListActivity.class));});
        }
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((RelativeLayout)object);
    }
}
