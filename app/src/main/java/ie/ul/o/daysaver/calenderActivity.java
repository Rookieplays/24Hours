package ie.ul.o.daysaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class calenderActivity extends MainActivity {
    CompactCalendarView calendarView;
    //List<Event> eventList;
    ArrayList<WorkoutPlan> wp=new ArrayList<>();
    ArrayList<Social>socialArrayList=new ArrayList<>();
    ArrayList<Study>std=new ArrayList<>();
    ArrayList<QuickAdd>qas=new ArrayList<>();
    List<CE>eventsList;
    private Calendar calendar;
    private Context context=this;
    private TextView dayNotice;
    private SimpleDateFormat dateFormat=new SimpleDateFormat("MMM- yyyy", Locale.getDefault());
    private LinearLayout eventView;
    private FirebaseFirestore mFireStore;
    private final String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);
        mFireStore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_calender);
        dayNotice=(TextView)findViewById(R.id.dayNotice);
        calendarView=(CompactCalendarView) findViewById(R.id.mainCalendar);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.calendarToolBar);
        setSupportActionBar(toolbar2);
       // toolbar2.setTitle(null);
        eventView=(LinearLayout)findViewById(R.id.eventView);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams vLine = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        progressBar=findViewById(R.id.progbar);
        progressDialog=new ProgressDialog(this);
        int[] imageResources = new int[]{
               // R.drawable.female_runner_painted,
                R.drawable.male_curls,
               // R.drawable.gym,
                R.drawable.social,
               /* R.drawable.social_award,
                R.drawable.social_birthday,
                R.drawable.social_cinema,
                R.drawable.social_date,
                R.drawable.social_club,
                R.drawable.social_dring,
                R.drawable.social_gaming,
                R.drawable.social_sleepover,
                R.drawable.social_sport,
                */R.drawable.quick_event,
                R.drawable.hbot_wave,
                R.drawable.studying_gal,
                R.drawable.chill,
                R.drawable.timetable,


        };
        int[] colorResourses=new int[]{
                R.color.gym_primaryColor,
                R.color.social_primaryColor,
                R.color.colorPrimary,
                R.color.meeting_primaryColor,
                R.color.study_primaryColor,
                R.color.chill_color,
                R.color.time_primaryColor,
        };

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            if(index==0)
                                startActivity(new Intent(calenderActivity.this,GymActivity.class));
                            else  if(index==1)
                                startActivity(new Intent(calenderActivity.this,socialActivity.class));
                            else  if(index==2)
                                startActivity(new Intent(calenderActivity.this,QuickAddActivity.class));
                            else  if(index==3)
                                startActivity(new Intent(calenderActivity.this,getttingStartedActivity.class));
                            else if(index==4)
                                startActivity(new Intent(calenderActivity.this,StudyActivity.class));
                            Toast.makeText(calenderActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    }).rotateImage(true)
                    .shadowEffect(true).shadowOffsetX(20).shadowOffsetY(0).shadowRadius(Util.dp2px(20)).shadowCornerRadius(Util.dp2px(20)).shadowColor(Color.parseColor("#ee000000"))
                    .normalImageRes(imageResources[i])
                    .normalImageDrawable(getResources().getDrawable(imageResources[i],null))
                   /* .highlightedImageRes(R.drawable.social_small)
                    .highlightedImageDrawable(getResources().getDrawable(R.drawable.social_small,null))
                    .unableImageRes(R.drawable.study_small)
                    .unableImageDrawable(getResources().getDrawable(R.drawable.social_small,null))
                    */.imageRect(new Rect(Util.dp2px(10), Util.dp2px(10), Util.dp2px(70), Util.dp2px(70)))
                    .imagePadding(new Rect(0, 0, 0, 0))
                    // Whether the boom-button should have a ripple effect.
                    .rippleEffect(true)
                    // The color of boom-button when it is at normal-state.
                    .normalColor(colorResourses[i])
                    // The resource of color of boom-button when it is at normal-state.
                    .normalColorRes(colorResourses[i])
                    // The color of boom-button when it is at highlighted-state.
                    .highlightedColor(Color.BLUE)
                    // The resource of color of boom-button when it is at highlighted-state.
                    .highlightedColorRes(R.color.aqua_primary)
                    // The color of boom-button when it is at unable-state.
                    .unableColor(colorResourses[i])
                    // The resource of color of boom-button when it is at unable-state.
                    .unableColorRes(colorResourses[i])
                    // The color of boom-button when it is just a piece.
                    .pieceColor(colorResourses[i])
                    // The resource of color of boom-button when it is just a piece.
                    .pieceColorRes(colorResourses[i])
                    // Whether the boom-button is unable, default value is false.
                    .unable(false)
                    // The radius of boom-button, in pixel.
                    .buttonRadius(Util.dp2px(40))
                    // Set the corner-radius of button.
                    .buttonCornerRadius(Util.dp2px(20))
                    // Whether the button is a circle shape.
                    .isRound(false);
            bmb.addBuilder(builder);
        }






            calendarView.setUseThreeLetterAbbreviation(true);
        calendarView.addEvent(setEvent("25/03/2018","GYM",new WorkoutPlan(),null,null,null));
      //  calendarView.addEvent(setEvent("30/03/2018","SOCIAL","BASKETBALL\nFrom: 12:00\nTo: 1:00"));
        getAll(2018);
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                //Typeface type = Typeface.createFromAsset(getAssets(),"fonts/aldrich.ttf");


                List<Event> events = calendarView.getEvents(dateClicked);
                Button eButton[] =new Button[events.size()];
                eventView.removeAllViews();


                Log.d("C", "Day was clicked: " + dateClicked + " with events " + events);
               if(events.size()>0)
               {
                   dayNotice.setText(getString(R.string.eventsFound,events.size()+""));
                   dayNotice.setTextColor(android.R.attr.textColorPrimary);

                   ViewPager hLine[]=new ViewPager[eButton.length];


                    for (int i=0;i<events.size();i++)
                    {

                        eButton[i]=new Button(context);
                        hLine[i]=new ViewPager(context);
                        eButton[i].setText(events.get(i).getData().toString());
                       // eButton[i].setTextColor(android.R.attr.textColorSecondary);
                        eButton[i].setBackgroundColor(events.get(i).getColor());
                        eButton[i].setAlpha(0.7F);
                        eButton[i].setTextColor(getResources().getColor(R.color.mainscreen_dark));
                        eventView.addView(eButton[i],lp);
                    }
               }
               else{
                   dayNotice.setText(getString(R.string.noEvents));
                   dayNotice.setTextColor(Color.GRAY);

               }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d("C", "Month was scrolled to: " + firstDayOfNewMonth);toolbar2.setTitle(dateFormat.format(firstDayOfNewMonth));
            }
        });


        /**Set Event*/

    }
    public void getAll(int year) {

        for (int i = 0; i < 7; i++) {
            for (String s : getAllMondaysInAyear(year, i + 1)) {
                LoadEvents(s);

            }

        }

    }
    public static List<String>  getAllMondaysInAyear(int year,int day)
    {


        int days = 0, monthNumber = 1, mondays = 0;

        // Converting String to uppercase



        int dayAndMonthNumber[];
        String months[]=new String[]{
                "JAN","FEB","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEP","OCTOBER","NOV","DEC"
        };

        List<String> dates = new ArrayList<>();
        Date date = null;

        String myDate = null;

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        for (int m=0;m<months.length ;m++ ) {


            dayAndMonthNumber = getNumberofDaysAndMonthNumberByMonthName(months[m], year+"");

            days = dayAndMonthNumber[0];

            monthNumber = dayAndMonthNumber[1];

            // Converting String to int.



            // Creating Calendar class instance.

            Calendar cal = Calendar.getInstance();

            // Adding given month and year in Calendar.

            cal.set(Calendar.MONTH, monthNumber);

            cal.set(Calendar.YEAR, year);


            for (int i = 1; i <= days; i++) {



                // Adding day of month in Calendar.

                cal.set(Calendar.DAY_OF_MONTH, i);

					/*

					 * cal.get(Calendar.DAY_OF_WEEK) = 1 (Sunday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 2 (Monday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 3 (Tuesday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 4 (Wednesday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 5 (Thursday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 6 (Friday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 7 (Saturday)

					 */

                if (cal.get(Calendar.DAY_OF_WEEK) == day) {

                    date = cal.getTime();

                    myDate = format.format(date);

                    dates.add(myDate);

                    mondays++;



                }

            }

            System.out.println("Number of x : " + mondays);




        }
        return dates;
    }





    private static int[] getNumberofDaysAndMonthNumberByMonthName(String monthName, String year) {



        int dayAndMonthNumber[] = new int[2];

        int days = 0;

        int monthNumber = 0;

        switch (monthName) {

            case "JANUARY":

            case "JAN":


                days = 31;

                monthNumber = 0;

                break;

            case "FEBRUARY":

            case "FEB":

                if (isLeapYear(year)) {

                    days = 29;

                } else {

                    days = 28;

                }

                monthNumber = 1;

                break;

            case "MARCH":

            case "MAR":

                days = 31;

                monthNumber = 2;

                break;

            case "APRIL":

            case "APR":

                days = 30;

                monthNumber = 3;

                break;

            case "MAY":

                days = 31;

                monthNumber = 4;

                break;

            case "JUNE":

                days = 30;

                monthNumber = 5;

                break;

            case "JULY":

                days = 31;

                monthNumber = 6;

                break;

            case "AUGUST":

            case "AUG":

                days = 31;

                monthNumber = 7;

                break;

            case "SEPTEMBER":

            case "SEP":

                days = 30;

                monthNumber = 8;

                break;

            case "OCTOBER":

            case "OCT":

                days = 31;

                monthNumber = 9;

                break;

            case "NOVEMBER":

            case "NOV":

                days = 30;

                monthNumber = 10;

                break;

            case "DECEMBER":

            case "DEC":

                days = 31;

                monthNumber = 11;

                break;

        }

        dayAndMonthNumber[0] = days;

        dayAndMonthNumber[1] = monthNumber;

        return dayAndMonthNumber;

    }

private ProgressDialog progressDialog;

    private static boolean isLeapYear(String year) {



        boolean result = false;

        // Converting String to int.

        int myYear = Integer.parseInt(year);

        if ((myYear % 4 == 0 && myYear % 100 != 0) || myYear % 400 == 0) {

            result = true;

        }

        return result;

    }

  private void LoadEvents(String date)
  {
      System.out.println("DDD"+date);

      progressBar.setVisibility(View.VISIBLE);
      mFireStore.collection(UID)
              .whereEqualTo("date", date)
              .get()
              .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (task.isSuccessful()) {
                          List<CE> eventList = new ArrayList<>();

                          for(DocumentSnapshot doc : task.getResult()){
                              System.out.println("--**>"+doc.getData().values().contains("QUICKADD"));
                              if(doc.getData().values().contains("SOCIAL"))
                              {
                                  Social e=doc.toObject(Social.class);
                                  socialArrayList.add(e);
                                  calendarView.addEvent(setEvent(date,e.getId(),null,e,null,null));
                              }
                              else if(doc.getData().values().contains("GYM"))
                              {
                                  WorkoutPlan e=doc.toObject(WorkoutPlan.class);
                                  wp.add(e);
                                  calendarView.addEvent(setEvent(date,e.getId(),e,null,null,null));

                              }
                              else if(doc.getData().values().contains("STUDY"))
                              {
                                  Study e=doc.toObject(Study.class);
                                  std.add(e);
                                  calendarView.addEvent(setEvent(date,e.getId(),null,null,e,null));

                              }
                              else if(doc.getData().values().contains("QUICKADD"))
                              {
                                  System.out.println("HiTTTER");
                                  QuickAdd e=doc.toObject(QuickAdd.class);
                                  qas.add(e);
                                  System.out.println("<<<"+qas);

                                  calendarView.addEvent(setEvent(date,e.getId(),null,null,null,e));

                              }
                            //  CE e = doc.toObject(CE.class);
                              //System.out.println(e);
                              //e.setType(doc.getString("type"));
                              //eventList.add(e);
                             // calendarView.addEvent(setEvent(date,e.getType(),e.getType()));
                          }
                          progressBar.setVisibility(View.GONE);
                         // Toast.makeText(context, "Events Loaded Success", Toast.LENGTH_SHORT).show();
                            /*EventsRecyclerViewAdapter recyclerViewAdapter = new
                                    EventsRecyclerViewAdapter(eventList,
                                    getActivity(), firestoreDB);
                            eventsRecyclerView.setAdapter(recyclerViewAdapter);*/

                      } else {
                          Log.d("TAG", "Error getting documents: ", task.getException());
                        //  Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                          progressBar.setVisibility(View.GONE);
                      }
                  }
              });
      mFireStore.collection("Public_workouts")
              .whereEqualTo("uid", UID)
              .whereEqualTo("date", date)
              .get()
              .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (task.isSuccessful()) {
                          List<CE> eventList = new ArrayList<>();

                          for(DocumentSnapshot doc : task.getResult()){
                              System.out.println("-->"+doc.getData().values().contains("QUICKADD"));

                               if(doc.getData().values().contains("GYM"))
                              {
                                  WorkoutPlan e=doc.toObject(WorkoutPlan.class);
                                  wp.add(e);
                                  calendarView.addEvent(setEvent(date,e.getId(),e,null,null,null));

                              }


                              //  CE e = doc.toObject(CE.class);
                              //System.out.println(e);
                              //e.setType(doc.getString("type"));
                              //eventList.add(e);
                              // calendarView.addEvent(setEvent(date,e.getType(),e.getType()));
                          }
                          progressBar.setVisibility(View.GONE);
                          //Toast.makeText(context, "Events Loaded Success", Toast.LENGTH_SHORT).show();
                            /*EventsRecyclerViewAdapter recyclerViewAdapter = new
                                    EventsRecyclerViewAdapter(eventList,
                                    getActivity(), firestoreDB);
                            eventsRecyclerView.setAdapter(recyclerViewAdapter);*/

                      } else {
                          Log.d("TAG", "Error getting documents: ", task.getException());
                         // Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                          progressBar.setVisibility(View.GONE);
                      }
                  }
              });

      mFireStore.collection("Private_workouts")
              .whereEqualTo("uid", UID)
              .whereEqualTo("date", date)
              .get()
              .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (task.isSuccessful()) {
                          List<CE> eventList = new ArrayList<>();

                          for(DocumentSnapshot doc : task.getResult()){
                              System.out.println("-->"+doc.getData().values().contains("QUICKADD"));

                              if(doc.getData().values().contains("GYM"))
                              {
                                  WorkoutPlan e=doc.toObject(WorkoutPlan.class);
                                  wp.add(e);
                                  calendarView.addEvent(setEvent(date,e.getId(),e,null,null,null));

                              }


                              //  CE e = doc.toObject(CE.class);
                              //System.out.println(e);
                              //e.setType(doc.getString("type"));
                              //eventList.add(e);
                              // calendarView.addEvent(setEvent(date,e.getType(),e.getType()));
                          }
                          progressBar.setVisibility(View.GONE);
                         // Toast.makeText(context, "Events Loaded Success", Toast.LENGTH_SHORT).show();
                            /*EventsRecyclerViewAdapter recyclerViewAdapter = new
                                    EventsRecyclerViewAdapter(eventList,
                                    getActivity(), firestoreDB);
                            eventsRecyclerView.setAdapter(recyclerViewAdapter);*/

                      } else {
                          Log.d("TAG", "Error getting documents: ", task.getException());
                         // Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                          progressBar.setVisibility(View.GONE);
                      }
                  }
              });


  }
    public Event setEvent(String date,String type,WorkoutPlan e1,Social e2,Study e3,QuickAdd e4)
    {
       // Social s=(Social)EventName;
       // System.out.println("++"+s.getDiscription());
        //Workout w=new Workout();
       int color=Color.GREEN;
        if(type.contains("GYM"))
        {color=Color.RED;}
        else  if(type.contains("SOCIAL"))
        {color=Color.MAGENTA;}
        else if(type.contains("STUDY"))
        {color=Color.YELLOW;}
        else if(type.contains("TIMETABLE"))
        {color=Color.BLUE;}
        else if(type.contains("MEETING"))
        {color=Color.GREEN;}
        else if(type.contains("QUICKADD"))
        {color=Color.BLUE;}
        else {color=R.attr.colorAccent;}
        System.out.println(e1);
        Event ev=null;

        if(e1!=null)
        {
            System.out.println("Setting Workout Event: "+e1);
            ev=new Event(color,ConvertFromDateToLong(date),e1);
        }
        else if(e2!=null){
            System.out.println("Setting Social Event: "+e2);
            ev=new Event(color,ConvertFromDateToLong(date),e2);
        }
        else if(e3!=null){
            System.out.println("Setting Study Event: "+e3);
            ev=new Event(color,ConvertFromDateToLong(date),e3);
        }
        else if(e4!=null){
            System.out.println("Setting qUICKADD Event: "+e4);
            ev=new Event(color,ConvertFromDateToLong(date),e4);
        }
        else
        {
            ev=new Event(color,ConvertFromDateToLong("20/04/2001"),new CE());
        }
        return ev;
    }
    private Long ConvertFromDateToLong(String date)
    {
        long milliseconds=System.currentTimeMillis();
        System.out.println("system Time: "+milliseconds);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();



            System.out.println("Date Time: "+milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
    public void onStart(Bundle savedInstanceState)
    {

    }

}
