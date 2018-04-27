package ie.ul.o.daysaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ie.ul.o.daysaver.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton pp;
    private boolean  goOn;
    private TextView username;
    protected TextView welcome_to;
    private TextView email;
    String randomWelcomeMsg;
    private final Context context=this;
    private boolean LOGGED;
    String mail,name,img="";
    Drawable image;
    private long currenttime;
   // private String img;
    private View headerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore mFireStore=FirebaseFirestore.getInstance();
    private DatabaseReference myRefrence;
    private TextView emailView,Dayprogress;
    RoundImage roundImage;
    private String UID;
    private UserInformation userInfo;
    int THEME_COLOUR_PRIMARY;
    int THEME_COLOUR_SECONDARY;
    int THEME=0;
    int THEME_COLOUR_DARK;
    private ImageView hbot_main;
    private Button aqua,midnight,lava,forest;
    private String dayProgress[];
    private  Menu menu2;


    private LinearLayout main_header,nav_header;
    private ScrollView main_activity_view;
    private Toolbar msin_toolbar;
    private RelativeLayout fullscreen;
    private RecyclerView eventViwer;
    private ArrayList<ArrayList<String>> AllEvents=new ArrayList<>();

private Utils utils=new Utils(this);
    private int ID=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            //set the transition
            Transition ts = new Explode();
            ts.setDuration(5000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }
        super.onCreate(savedInstanceState);
        AllEvents.add(new ArrayList<>());//Name/Title
        AllEvents.add(new ArrayList<>());//Description
        AllEvents.add(new ArrayList<>());//Start Time
        AllEvents.add(new ArrayList<>());//End Time
        AllEvents.add(new ArrayList<>());//Color in String
        LayoutInflater inflaterV=getLayoutInflater();
        headerView=inflaterV.inflate(R.layout.nav_header_main,null);
        nav_header= headerView.findViewById(R.id.navi_header);
        ThemeSwither.onActivityCreateSetTheme(this,nav_header);
        setContentView(R.layout.activity_main);
         dayProgress=new String[4];//=getResources().getStringArray(R.array.DayComments);
        pb=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar24);
      setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
               startActivity(new Intent(context,calenderActivity.class));
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRefrence=firebaseDatabase.getReference();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);




        menu2=navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
        LayoutInflater inflater1=getLayoutInflater();
        //View tailView=inflater.inflate(R.layout.activity_main_drawer,null);



        navigationView.addHeaderView(headerView);
        Dayprogress= findViewById(R.id.dayProgress);
        // navigationView.addView(tailView);
        username= headerView.findViewById(R.id.user);
        hbot_main= findViewById(R.id.hBot_main);
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.color_dialog,null);
        aqua= dialogView.findViewById(R.id.theme_aqua);
        lava= dialogView.findViewById(R.id.theme_lava);
        midnight= dialogView.findViewById(R.id.theme_midnight);
        forest= dialogView.findViewById(R.id.theme_forest);
        main_header= findViewById(R.id.main_header);
       // main_activity_view=(ScrollView)findViewById(R.id.main_activity_view);
        nav_header= headerView.findViewById(R.id.navi_header);
        fullscreen= findViewById(R.id.fullScreen);
        msin_toolbar= findViewById(R.id.toolbar24);
        emailView= headerView.findViewById(R.id.email);
       //pp=(ImageButton) headerView.findViewById(R.id.PROFILEPICTUREBTN);
        eventViwer=findViewById(R.id.mainRV);
      initViews();

        image=getDrawable(R.mipmap.ic_launcher_round);



        email= findViewById(R.id.email);
        welcome_to= findViewById(R.id.welcomeTo);

        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog=dialogBuilder.create();

        lava.setOnClickListener(v->{changeTheme(v);THEME=1;});
        aqua.setOnClickListener(v->{changeTheme(v);THEME=3; });
        forest.setOnClickListener(v->{changeTheme(v);THEME=2; });
        midnight.setOnClickListener(v->{changeTheme(v);THEME=0;});
        loadUserInformation();



        THEME_COLOUR_SECONDARY=getResources().getColor(R.color.aqua_secondary);
        //getWindow().setNavigationBarColor(THEME_COLOUR_PRIMARY);
       // getWindow().setStatusBarColor(THEME_COLOUR_SECONDARY);
       // fullscreen.setBackgroundColor(THEME_COLOUR_PRIMARY);
       // main_header.setBackgroundColor(THEME_COLOUR_SECONDARY);
       // main_activity_view.setBackgroundColor(THEME_COLOUR_PRIMARY);
        ////System.out.println()(THEME);



        //msin_toolbar.setBackgroundColor(getResources().getColor(R.color.holo_teal));

       // hbot_main.setBackground(getResources().getDrawable(R.drawable.hbot_wave_200px));
        alertDialog.dismiss();




        hbot_main.setOnLongClickListener(e->{ alertDialog.show();return true;});if(FirebaseAuth.getInstance().getCurrentUser()!=null)LoadEventsForToday();
        Thread t = new Thread() {

            @Override

            public void run() {

                try {

                    while (!isInterrupted()) {

                        Thread.sleep(1000);

                        runOnUiThread(() -> {
                           /* for(int i=0;i<AllEvents.get(0).size();i++) {
                                if (new SimpleDateFormat("HH:mm").format(System.currentTimeMillis()).equalsIgnoreCase(AllEvents.get(2).get(i))) {
                                    while (!(new SimpleDateFormat("HH:mm").format(System.currentTimeMillis()).equalsIgnoreCase(AllEvents.get(3).get(i)))) {
                                        startAnimation(MainAdapter.itView, true);

                                    }
                                }
                                else //System.out.println()(AllEvents.get(3).get(i)+" ,NO II CANT ");
                            }*/

                            TextView tdate = findViewById(R.id.main_date);
                            TextView ttime = findViewById(R.id.main_time);

                            long date = System.currentTimeMillis();
                            Date c = new Date();
                            long nowTime = c.getTime() % (24 * 60 * 60 * 1000L);
                            //////System.out.println()("Time: " + nowTime + "\nDayTime: 86400000 \nProgress: " + (nowTime * 100) / 86400000);
                            int rst=(int)(99-((nowTime * 100) / 82800000));
                           // ////System.out.println()(rst);
                            Dayprogress.setText(getString(R.string.progress,+rst+"% "));
                            goOn = false;
                            currenttime=nowTime;
                            ProgressBar daySeek = findViewById(R.id.dayscale);
                            if (daySeek != null && tdate != null && ttime != null) {
                                daySeek.setProgress((int) ((nowTime * 100) / 82800000));


                                if (nowTime < 21600000 && nowTime >= 3600000) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(61, 39, 79)));
                                    main_header.setBackgroundColor(Color.rgb(61, 39, 79));
                                } else if (nowTime < 28800000 && nowTime >= 21600000) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(134, 188, 205)));
                                    main_header.setBackgroundColor(Color.rgb(134, 188, 205));
                                } else if (nowTime < 36000000 && nowTime >= 28800000) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(134, 188, 200)));
                                    main_header.setBackgroundColor(Color.rgb(134, 188, 200));
                                } else if (nowTime < 43200000 && nowTime >= 36000000) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(134, 205, 174)));
                                    main_header.setBackgroundColor(Color.rgb(134, 205, 174));
                                } else if (nowTime < 46800000 && nowTime >= 43200000) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(138, 241, 197)));
                                    main_header.setBackgroundColor(Color.rgb(138, 241, 197));
                                } else if (nowTime < 5.04e+7 && nowTime >= 46800000) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(215, 241, 138)));
                                    main_header.setBackgroundColor(Color.rgb(215, 241, 138));
                                } else if (nowTime < 5.4e+7 && nowTime >= 5.04e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(242, 233, 92)));
                                    main_header.setBackgroundColor(Color.rgb(242, 233, 92));
                                } else if (nowTime < 5.76e+7 && nowTime >= 5.4e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 195, 0)));
                                    main_header.setBackgroundColor(Color.rgb(255, 195, 0));
                                } else if (nowTime < 6.12e+7 && nowTime >= 5.76e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 144, 2)));
                                    main_header.setBackgroundColor(Color.rgb(255, 144, 2));
                                } else if (nowTime < 6.48e+7 && nowTime >= 6.12e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 87, 51)));
                                    main_header.setBackgroundColor(Color.rgb(255, 87, 51));
                                } else if (nowTime < 6.84e+7 && nowTime >= 6.48e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 144, 26)));
                                    main_header.setBackgroundColor(Color.rgb(255, 144, 26));
                                } else if (nowTime < 7.2e+7 && nowTime >= 6.84e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(242, 233, 92)));
                                    main_header.setBackgroundColor(Color.rgb(242, 233, 92));
                                } else if (nowTime < 7.56e+7 && nowTime >= 7.2e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(138, 241, 197)));
                                    main_header.setBackgroundColor(Color.rgb(138, 241, 197));
                                } else if (nowTime < 7.92e+7 && nowTime >= 7.56e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(19, 115, 125)));
                                    main_header.setBackgroundColor(Color.rgb(19, 115, 125));
                                } else if (nowTime < 8.28e+7 && nowTime >= 7.92e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(60, 82, 104)));
                                    main_header.setBackgroundColor(Color.rgb(60, 82, 104));
                                } else if (nowTime >= 8.28e+7) {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(Color.rgb(49, 54, 84)));
                                    main_header.setBackgroundColor(Color.rgb(49, 54, 84));
                                } else {
                                    daySeek.setProgressTintList(ColorStateList.valueOf(R.attr.colorAccent));
                                    main_header.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                    ////System.out.println()("haha");
                                }



                                updateAlarm(nowTime);
                                SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd yyyy ");
                                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm a");

                                String dateString = sdf.format(date);
                                String timeString = sdf1.format(date);

                                //  //System.out.print(dateString);
                                // //System.out.print(timeString);

                                tdate.setText(dateString);
                                ttime.setText(timeString);

                            }
                        });

                    }

                } catch (InterruptedException e) {

                }

            }

        };

        t.start();
        int[] imageResources = new int[]{
                // R.drawable.female_runner_painted,
                R.drawable.timer,
                R.drawable.notes,
                R.drawable.calculator,
                R.drawable.quick_event,
                R.drawable.bs_logo
        };
        int[] colorResourses=new int[]{
                R.color.gym_primaryColor,
                R.color.social_primaryColor,
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.cardview_dark_background

        };
        BoomMenuButton bmb = findViewById(R.id.bmb2);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            if(index==0)
                            {max=1;
                              showTimerMenu(1);}
                            else  if(index==1)
                                startActivity(new Intent(MainActivity.this,createNote.class));
                            else  if(index==2){}
                               // startActivity(new Intent(MainActivity.this,QuickAddActivity.class));
                            else  if(index==3)
                                startActivity(new Intent(MainActivity.this,QuickAddActivity.class));
                            else  if(index==4)
                                startActivity(new Intent(MainActivity.this,BudgetShopper.class));


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
                    .isRound(true);
            bmb.addBuilder(builder);
        }





        //email.setText(mail);
        //pp.setBackground(image);
        ////System.out.println()("Username "+welcome_to.getText().toString()+"\nEmail "+email);
    }ArrayList<Long>ev=new ArrayList<>();

    private void updateAlarm(long nowTime) {
        String date=new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
        String time=new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
      //  Log.w("If Loop","timesToSetAlerts: "+timesToSetAlerts.isEmpty());
        if(!(timesToSetAlerts.isEmpty()&&timesToSetAlerts2.isEmpty()&&iconsList.isEmpty()&&eventInfo.isEmpty()))
        {


            for(int i=0;i<timesToSetAlerts.size();i++)
            {
               /* try {
                    //Log.e("For Loop","timesToSetAlerts: "+timesToSetAlerts.get(i)+"\nNow Time: "+(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()));
                    //Log.w("For Loop","timesToSetAlerts2: "+timesToSetAlerts2.get(i)+"\nNow Time: "+(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()));

                } catch (ParseException e) {
                    e.printStackTrace();
                };*/
                try {
                    if(timesToSetAlerts.get(i)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        setNotification("","",eventInfo.get(0).get(i),"...",eventInfo.get(1).get(i),iconsList.get(i));
                      //  setAlarm(timesToSetAlerts.get(i),NOTIF_ID,false,0);
                    }
                    else  if(timesToSetAlerts.get(i)-(15*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        eventInfo.get(1).set(i,"Event Starting in 15 mins");

                        setNotification("","",eventInfo.get(0).get(i),"",eventInfo.get(1).get(i),iconsList.get(i));

                    }
                    else  if(timesToSetAlerts.get(i)-(10*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        eventInfo.get(1).set(i,"Event Starting in 10 mins");
                        setNotification("","",eventInfo.get(0).get(i),"",eventInfo.get(1).get(i),iconsList.get(i));

                    }
                    else  if(timesToSetAlerts.get(i)-(5*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        eventInfo.get(1).set(i,"Event Starting in 5 mins");

                        setNotification("","",eventInfo.get(0).get(i),"",eventInfo.get(1).get(i),iconsList.get(i));

                    }

                    else  if(timesToSetAlerts2.get(i)-(15*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        eventInfo.get(2).set(i,"Event ending in 15 mins");

                        setNotification("","",eventInfo.get(2).get(i),"",eventInfo.get(3).get(i),iconsList.get(i));

                    }
                    else  if(timesToSetAlerts2.get(i)-(10*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        eventInfo.get(2).set(i,"Event ending in 10 mins");
                        setNotification("","",eventInfo.get(2).get(i),"",eventInfo.get(3).get(i),iconsList.get(i));

                    }
                    else  if(timesToSetAlerts2.get(i)-(5*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        eventInfo.get(2).set(i,"Event ending in 5 mins");

                        setNotification("","",eventInfo.get(2).get(i),"",eventInfo.get(3).get(i),iconsList.get(i));

                    }
                    else  if(timesToSetAlerts2.get(i)-(1*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        eventInfo.get(2).set(i,"Event ending in 1 mins");

                        setNotification("","",eventInfo.get(2).get(i),"",eventInfo.get(3).get(i),iconsList.get(i));

                    }
                    else  if(timesToSetAlerts2.get(i)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        eventInfo.get(2).set(i,"Event is Over!!");

                        setNotification("","",eventInfo.get(2).get(i),"",eventInfo.get(3).get(i),iconsList.get(i));
                        MainAdapter.itView.setAlpha(0.4f);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showTimerMenu(double dur) {
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        LayoutInflater inflater =LayoutInflater.from(context);//getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timer_dialog, null);
        dialogBuilder.setView(dialogView);


        pbb=dialogView.findViewById(R.id.progressBar2);
        timer=dialogView.findViewById(R.id.timerText);
        startTime=dialogView.findViewById(R.id.startTimer);
        resetTime=dialogView.findViewById(R.id.resetTimer);
        stopb=dialogView.findViewById(R.id.stopTimer);
        plusOne=dialogView.findViewById(R.id.plusOne);
        plusOne.setOnClickListener(e->{
            mTimeLeftInMillis=mTimeLeftInMillis+(60*1000);
            if(mCountDownTimer!=null){
                mCountDownTimer.cancel();
                startTimer(2.0);
            }
            else  resetTimer(max);
        });



        anim=startAnimation2(timer);
        nM=dialogView.findViewById(R.id.notifyMe);

        RelativeLayout bg=dialogView.findViewById(R.id.bg);
        AlarmReceiver.STUDYMSG="Study Completed!";
        AlarmReceiver.STUDYHEADING="Heads Up";
        nM.setOnClickListener(e->{
            if (notify) {
                Toast.makeText(context,"Alarm Off",Toast.LENGTH_SHORT).show();
                nM.setBackground(context.getDrawable(android.R.drawable.ic_lock_silent_mode_off));
                notify=false;

            } else {
                Toast.makeText(context,"Alarm on",Toast.LENGTH_SHORT).show();
                nM.setBackground(context.getDrawable(android.R.drawable.ic_lock_silent_mode));
                notify=true;
                //disableNotification();
            }
        });
        setProgressNotification(R.drawable.timer,"",0,"");

        startTime.setOnClickListener(e->{
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer(dur);
            }
        });
        resetTime.setOnClickListener(e->{
            resetTimer(max);
        });

        updateCountDownText(mTimeLeftInMillis);
        stopb.setOnClickListener(e->{pauseTimer();startPopUP(timer);});
        android.support.v7.app.AlertDialog adialog=dialogBuilder.create();
        adialog.show();

    }

    boolean isNotificationActive=false;
    int notificationId=24;
    public Spinner type;
    public final String NOTIFICATION_ID="24H_SP";

    private void setUpNofication(String title,String text,int icon) {
        title="Started Timer";
        text="Study Time";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_001");
        Intent ii = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(title);
        bigText.setBigContentTitle(text);
        bigText.setSummaryText("");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(NOTIF_ID, mBuilder.build());  nM.setBackground(context.getDrawable(android.R.drawable.ic_lock_silent_mode_off));
        notify=false;


    }
    public AnimatorSet startAnimation2(View v)
    {

        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("alpha",1f,0f));

        grow.setDuration(600);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("alpha",0f,1f));
        grow.setRepeatMode(ObjectAnimator.RESTART);
        grow.setRepeatCount(ObjectAnimator.INFINITE);
        AnimatorSet animatorSet2=new AnimatorSet();
        animatorSet2.playSequentially(grow,shrink);
        animatorSet2.setDuration(1000);

        return  animatorSet2;
    }
    public void alarmer(Context context,String title)
    {
        MediaPlayer alarmRinging=new MediaPlayer();
        try {
            alarmRinging.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        } catch (IOException e) {
            e.printStackTrace();
        }
        final AudioManager audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if(audioManager.getStreamVolume(AudioManager.STREAM_ALARM)!=0){
            alarmRinging.setAudioStreamType(AudioManager.STREAM_ALARM);
            alarmRinging.setLooping(true);
            //alarm.prepare();
            //TODO:Start Alarm

        }
        try {
            alarmRinging.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        alarmRinging.start();
        //Log.e("Alarmer","Alarm playing: "+alarmRinging.isPlaying());
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage("Time's Up!").setTitle(title);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(alarmRinging!=null&&alarmRinging.isPlaying()){
                    alarmRinging.stop();alarmRinging.reset();alarmRinging.release();
                }

            }
        });


        builder.setNegativeButton("Snooze +5min", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                MAXTIME=5*60*1000;
                max=MAXTIME;
                resetTimer(MAXTIME);
                adialog.dismiss();




            }
        });
        builder.setPositiveButton(R.string.stop, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                //adialog.dismiss();
                adialog.dismiss();




            }
        });
        android.support.v7.app.AlertDialog adialog=builder.create();
        adialog.show();

    }

    private void disableNotification() {

        if(isNotificationActive){
            notificationManager.cancel(notificationId);
        }
    }
    private Button restore;
    private EditText newDur;
    private long newD;
    private final String p1="(([0-9]+:[0-9]+:[0-9]+)|([0-9]+:[0-9]+)|([0-9]+))";
    private final String p2="(([0-9]+:[0-9]+)+|([0-9]+))";
    private final String p3="([0-9]+)";
    private String t="";
    private void startPopUP(View v) {

        View popupContentView = LayoutInflater.from(context).inflate(R.layout.changetimer, null);


        restore=popupContentView.findViewById(R.id.button14);
        newDur=popupContentView.findViewById(R.id.newDur);
        type=popupContentView.findViewById(R.id.timeType);


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t=adapterView.getSelectedItem().toString();
                newDur.setHint("Enter Duration in "+type.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        newDur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence))
                {
                    newDur.setError(context.getString(R.string.emptyField));
                    restore.setEnabled(false);
                }
                else if(t.equalsIgnoreCase("Hours")&&!(charSequence.toString().matches(p1)))
                {
                    newDur.setError(context.getString(R.string.hF));
                    restore.setEnabled(false);
                }
                else if(t.equalsIgnoreCase("Minutes")&&!(charSequence.toString().matches(p2)))
                {
                    newDur.setError(context.getString(R.string.mf));
                    restore.setEnabled(false);
                }
                else if(t.equalsIgnoreCase("Seconds")&&!(charSequence.toString().matches(p3)))
                {
                    newDur.setError(context.getString(R.string.sf));
                    restore.setEnabled(false);
                }
                else{
                    newDur.setError(null);
                    restore.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(popupContentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_sign_out);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(v,4,-10);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.update();
        restore.setOnClickListener(e->{
            long temp=mTimeLeftInMillis;
            int hr,min,sec;
            String tempA[];
            String s=newDur.getText().toString();
            popupWindow.dismiss();
            if(t.equalsIgnoreCase("Hours"))
            {
                if(s.contains(":")||s.contains("\\."))
                {
                    s=s.replaceAll("\\.",":");
                    tempA=s.split(":");
                    if(tempA.length==1)
                    {
                        hr=Integer.parseInt(tempA[0]);
                        temp=(long)(hr*60*60*1000);

                    }
                    if(tempA.length==2)
                    {
                        hr=Integer.parseInt(tempA[0]);
                        min=Integer.parseInt(tempA[1]);
                        temp=((long)(hr*60*60*1000))+((long)(min*60*1000));

                    }
                    else if(tempA.length==3)
                    {
                        hr=Integer.parseInt(tempA[0]);
                        min=Integer.parseInt(tempA[1]);
                        sec=Integer.parseInt(tempA[2]);
                        temp=((long)(hr*60*60*1000))+((long)(min*60*1000))+((long)(sec*1000));

                    }


                }
                else if(TextUtils.isEmpty(s))
                {
                    temp=mTimeLeftInMillis;
                }
                else{
                    hr=Integer.parseInt(s);
                    temp=(long)(hr*60*60*1000);

                }
            }
            else if(t.equalsIgnoreCase("Minutes"))
            {
                if(s.contains(":")||s.contains("\\."))
                {
                    s=s.replaceAll("\\.",":");
                    tempA=s.split(":");
                    if(tempA.length==1)
                    {
                        min=Integer.parseInt(tempA[0]);
                        temp=(long)(min*60*1000);

                    }
                    else if(tempA.length==2)
                    {

                        min=Integer.parseInt(tempA[0]);
                        sec=Integer.parseInt(tempA[1]);
                        temp=((long)(min*60*1000))+((long)(sec*1000));

                    }


                }
                else{
                    min=Integer.parseInt(s);
                    temp=(long)(min*60*1000);

                }
            }
            else if(t.equalsIgnoreCase("Seconds"))
            {

                sec=Integer.parseInt(s);
                temp=(long)(sec*1000);
            }
            mTimeLeftInMillis=temp;
            max=temp;
            MAXTIME=max;
            resetTimer(temp);
        });

    }

    private  long max=1;
    private long mTimeLeftInMillis=max;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private Button startTime,resetTime,stopb;
    ImageButton nM;
    private  TextView timer;
    private ProgressBar pbb;
    private TextView plusOne;

    private void startTimer(Double dur) {
        ////System.out.println()("£££ "+mTimeLeftInMillis);
        anim.removeAllListeners();
        anim.cancel();
        timer.setAlpha(1f);
        setProgressNotification(R.drawable.timer,"",0,"");
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText(mTimeLeftInMillis);


            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                pbb.setProgress(0);
                startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_play));
                startTime.setVisibility(View.INVISIBLE);
                resetTime.setVisibility(View.VISIBLE);
                //TODO: start Alarmer;
                if(notify==true)
                {
                    alarmer(context,"it's over!");
                    notification.setContentText("Time's Up!");
                    notificationManager.notify(NOTIF_ID, notification.build());
                    // setUpNofication("24H.SP","Time's Up",R.drawable.study_small);
                    setNotification("Timer","Time's up!","Your timer is Done!","","",R.drawable.timer);
                    // notify=false;
                    //TODO: send Notification;
                }
                else{
                    Toast.makeText(context,"Time's Up!",Toast.LENGTH_SHORT).show();
                    setNotification("Timer","Time's up!","Your timer is Done!","","",R.drawable.timer);
                    //notify=true;
                }

            }
        }.start();

        mTimerRunning = true;
        startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_pause));
        resetTime.setVisibility(View.INVISIBLE);
    }
    private boolean notify=true;
    private void pauseTimer() {
        if(mCountDownTimer!=null)
            mCountDownTimer.cancel();
        mTimerRunning = false;
        pbb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        anim.start();
        startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_play));
        resetTime.setVisibility(View.VISIBLE);
    }
    private MediaPlayer ringAlarm() throws IOException {
        MediaPlayer alarm=new MediaPlayer();

        return  alarm;
    }
    private AnimatorSet anim;
    private void resetTimer(long ms) {

        timer.setAlpha(1f);
        mTimeLeftInMillis=MAXTIME;
        updateCountDownText(ms);

        anim.removeAllListeners();
        anim.cancel();

        resetTime.setVisibility(View.INVISIBLE);
        startTime.setVisibility(View.VISIBLE);
    }
    private  long MAXTIME=max;
    private int NOTIF_ID=0;
    private void updateCountDownText(long dur) {
        max=dur;


        int rst = (int) (99 - (((MAXTIME-mTimeLeftInMillis) * 100) / MAXTIME));
        pbb.setProgress((rst));
        ////System.out.println()(rst);
        //  //System.out.println()(milliUntilFinish);

        int hr = (int) (TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis) % 24);
        int min = (int) (TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis) % 60);
        int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis) % 60);
        ////System.out.println()(hr+":"+min+":"+sec);
        timer.setText(hr + ":" + min + ":" + sec);
        if (rst >= 50) {
            pbb.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

        } else if (rst >= 30 && rst < 50) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pbb.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.lava_primary)));
            }
        } else if (rst >= 10 && rst < 80) {
            pbb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        String tl="0";
        String temp[]=null;
        if(timer.getText().toString().contains(":"))
        {
           temp= timer.getText().toString().split(":");
        }
        assert temp != null;
        if(Integer.parseInt(temp[0])>0)
        {
                tl=temp[0]+" hour(s)";
                if(Integer.parseInt(temp[1])>0)
                {

                        tl+=temp[1]+" min(s)";
                        if(Integer.parseInt(temp[2])>0)
                        tl+=temp[2]+" sec(s)";


            }
        }
        else  if(Integer.parseInt(temp[1])>0)
        {

                tl+=temp[1]+" min(s)";
                if(Integer.parseInt(temp[2])>0)
                    tl+=temp[2]+" sec(s)";


        }
        else  if(Integer.parseInt(temp[2])>0)
        {
                tl+=temp[2]+" sec(s)";


        }

        notification.setContentText(""+tl+" left");
        notification.setProgress(100, pbb.getProgress(),false);
       notification.setOnlyAlertOnce(true);
        notificationManager.notify(NOTIF_ID, notification.build());


    }

    private void startAnimation(View v,boolean start)
    {



        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",2f),
                PropertyValuesHolder.ofFloat("scaleX",2f));

        grow.setDuration(200);

        grow.setRepeatMode(ObjectAnimator.REVERSE);
        grow.setRepeatCount(ObjectAnimator.INFINITE);
        if(start==true)
            grow.start();
        else grow.end();
    }
    private void initViews(){

        eventViwer.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        eventViwer.setLayoutManager(layoutManager);
    }
    private ArrayList<WorkoutPlan> wp=new ArrayList<>();
    private ArrayList<Social>sl=new ArrayList<>();
    private ArrayList<Study>st=new ArrayList<>();
    private ArrayList<QuickAdd>qua=new ArrayList<>();
    private ProgressDialog pb;
    private MainAdapter mainAdapter;
    //private Context context=this;
    public void LoadEventsForToday()
    {
        String today="";
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
        LoadMainEvents(sdf.format(System.currentTimeMillis()));



    }
    ArrayList<Long>timesToSetAlerts=new ArrayList<>();
    ArrayList<Long>timesToSetAlerts2=new ArrayList<>();
   public static  ArrayList<Long>ST=new ArrayList<>();
    public static ArrayList<Long>ET=new ArrayList<>();
    ArrayList<ArrayList<String>>eventInfo=new ArrayList<>();
    public static ArrayList<ArrayList<String>>EVENTS=new ArrayList<>();
    ArrayList<Integer>iconsList=new ArrayList<>();
    public static ArrayList<Integer>ICL=new ArrayList<>();
    public void LoadMainEvents(String date)
    {
        eventInfo.add(new ArrayList<>());
        eventInfo.add(new ArrayList<>());
        eventInfo.add(new ArrayList<>());
        eventInfo.add(new ArrayList<>());
        pb.setMessage(getString(R.string.wait5));
        pb.show();
        System.out.printf(date);
        mFireStore.collection(UID)
                .whereEqualTo("date", date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CE> eventList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                //System.out.println()("{{"+doc.getData().values());
                                //System.out.println()("-->"+doc.getData().values().contains("SOCIAL"));
                                if(doc.getData().values().contains("SOCIAL"))
                                {
                                    Social e=doc.toObject(Social.class);
                                    sl.add(e);
                                    try {
                                        e.setStartAt(new SimpleDateFormat("dd/MM/yyyy").parse(e.getDate()).getTime()+new SimpleDateFormat("HH:mm").parse(e.getStartTime()).getTime());

                                        e.setEndAt(new SimpleDateFormat("dd/MM/yyyy").parse(e.getDate()).getTime()+new SimpleDateFormat("HH:mm").parse(e.getEndTime()).getTime());
                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                    timesToSetAlerts.add(e.getStartAt());
                                    timesToSetAlerts2.add(e.getEndAt());
                                    Log.d("Social",e.getStartAt()+"");
                                    eventInfo.get(0).add("Event: "+e.getType()+" Has Started!");
                                    eventInfo.get(1).add(e.getDiscription());
                                    eventInfo.get(2).add("Event: "+e.getType());
                                    eventInfo.get(3).add("Round up!");
                                    iconsList.add(R.drawable.ref5);



                                }
                                else if(doc.getData().values().contains("GYM"))
                                {
                                    WorkoutPlan e=doc.toObject(WorkoutPlan.class);
                                    wp.add(e);
                                    Log.d("STUDY",e.getStartAt()+"");
                                    try {
                                        e.setStartAt(new SimpleDateFormat("dd/MM/yyyy").parse(e.getDate()).getTime()+new SimpleDateFormat("HH:mm").parse(e.getStartTime()).getTime());

                                        e.setEndAt(new SimpleDateFormat("dd/MM/yyyy").parse(e.getDate()).getTime()+new SimpleDateFormat("HH:mm").parse(e.getEndTime()).getTime());
                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                    timesToSetAlerts.add(e.getStartAt());
                                    timesToSetAlerts2.add(e.getEndAt());
                                    eventInfo.get(0).add("Event: "+e.getName()+" Has Started!");
                                    eventInfo.get(1).add("Click to begin");
                                    eventInfo.get(2).add("Event: "+e.getName());
                                    eventInfo.get(3).add("Round up!");
                                    iconsList.add(R.drawable.female_runner_painted);



                                }
                                else if(doc.getData().values().contains("STUDY"))
                                {
                                    Study e=doc.toObject(Study.class);
                                    st.add(e);
                                    String str="";
                                    for(Subjects s:e.getSubjects())
                                        str+="• "+s.getName()+"\n";
                                    Log.d("STUDY",e.getStartAt()+"");
                                    try {
                                        e.setStartAt(new SimpleDateFormat("dd/MM/yyyy").parse(e.getDate()).getTime()+new SimpleDateFormat("HH:mm").parse("12:00").getTime());

                                        e.setEndAt(new SimpleDateFormat("dd/MM/yyyy").parse(e.getDate()).getTime()+new SimpleDateFormat("HH:mm").parse("00:00").getTime());
                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                    }
                                    timesToSetAlerts.add(e.getStartAt());
                                    timesToSetAlerts2.add(e.getEndAt());
                                    eventInfo.get(0).add("Event: "+e.getName()+" Has Started!");
                                    eventInfo.get(1).add("You have to study: "+str);
                                    eventInfo.get(2).add("Event: "+e.getName());
                                    eventInfo.get(3).add("Round up!");
                                    iconsList.add(R.drawable.studying_gal);




                                }
                                else if(doc.getData().values().contains("QUICKADD"))
                                {
                                    QuickAdd e=doc.toObject(QuickAdd.class);
                                    qua.add(e);
                                    timesToSetAlerts.add(e.getStartAt());
                                    timesToSetAlerts2.add(e.getEndAt());
                                    eventInfo.get(0).add("Event: "+e.getTitle()+" Has Started!");
                                    eventInfo.get(1).add(e.getDescription());
                                    eventInfo.get(2).add("Event: "+e.getTitle());
                                    eventInfo.get(3).add("Round up!");
                                    iconsList.add(R.drawable.quick_event);



                                }

                            }
                            //System.out.println()(wp+"\n"+sl+"\n"+st);
                            for(WorkoutPlan w: wp) {
                                if(!w.getName().equalsIgnoreCase("unknown"))
                                {AllEvents.get(0).add(w.getName());
                                //  for(Workout ww:w.getWorkouts())
                                if(w.getWorkouts()!=null)
                                AllEvents.get(1).add(w.getWorkouts().toString());
                                else
                                    AllEvents.get(1).add(w.getDiscription());
                                AllEvents.get(2).add(w.getStartTime());
                                AllEvents.get(3).add(w.getEndTime());
                                AllEvents.get(4).add(Color.RED + "");
                                 try {
                                        ev.add(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(w.getStartTime()).getTime());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        ev.add(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(w.getEndTime()).getTime());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                            }
                            }
                            for(Social s:sl)
                            {
                                AllEvents.get(0).add(s.getHeading());
                                AllEvents.get(1).add(s.getDiscription());
                                AllEvents.get(2).add(s.startTime);
                                AllEvents.get(3).add(s.endTime);
                                AllEvents.get(4).add(Color.MAGENTA+"");
                                wp.add(new WorkoutPlan());
                                try {
                                    ev.add(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(s.startTime).getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    ev.add(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(s.endTime).getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            //Description and stuff...
                            for(Study s:st)
                            {
                                AllEvents.get(0).add("Study");
                                AllEvents.get(1).add(s.getDiscription());
                                AllEvents.get(2).add("Undefined");
                                AllEvents.get(3).add(s.getDuration()+"");
                                AllEvents.get(4).add(Color.YELLOW+"");
                                wp.add(new WorkoutPlan());
                            }
                            //System.out.println()(qua);
                            for(QuickAdd s:qua)
                            {
                                //System.out.println()("!!!!"+s);
                                AllEvents.get(0).add("Quick Add");
                                AllEvents.get(1).add(s.getDescription());
                                AllEvents.get(2).add(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(s.getStartTime()));
                                AllEvents.get(3).add(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(s.getEndTime()));
                                AllEvents.get(4).add(getColor(android.R.color.holo_blue_light)+"");
                                ev.add(s.getStartTime());
                                ev.add(s.getEndTime());
                                wp.add(new WorkoutPlan());
                            }
                            //System.out.println()(AllEvents);
                            if(context!=null)
                            {
                                mainAdapter=new MainAdapter(context,AllEvents,wp);
                                eventViwer.setAdapter(mainAdapter);
                            }
                          try{pb.dismiss();}catch (Exception e){}
                          //  Toast.makeText(context, "Events Loaded Success", Toast.LENGTH_SHORT).show();
                            EVENTS.addAll(eventInfo);
                            ST.addAll(timesToSetAlerts);
                            ET.addAll(timesToSetAlerts2);
                            ICL.addAll(iconsList);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                          try{pb.dismiss();}catch (Exception e){}
                        }
                    }
                });
        mFireStore.collection("Private_workouts")
                .whereEqualTo("uid", UID)
                .whereEqualTo("date", date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CE> eventList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                //System.out.println()("-->"+doc.getData().values().contains("SOCIAL"));
                                if(doc.getData().values().contains("GYM"))
                                {
                                    WorkoutPlan e=doc.toObject(WorkoutPlan.class);
                                    wp.add(e);


                                }
                            }
                            //System.out.println()(wp+"\n"+sl+"\n"+st);
                            for(WorkoutPlan w: wp) {
                                AllEvents.get(0).add(w.getName());
                                //  for(Workout ww:w.getWorkouts())
                                if(w.getWorkouts()!=null)
                                    AllEvents.get(1).add(w.getWorkouts().toString());
                                else
                                    AllEvents.get(1).add(w.getDiscription());
                                AllEvents.get(2).add(w.getStartTime());
                                AllEvents.get(3).add(w.getEndTime());
                                AllEvents.get(4).add(Color.RED + "");
                                try {
                                    ev.add(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(w.getStartTime()).getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    ev.add(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(w.getEndTime()).getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
        mFireStore.collection("Public_workouts")
                .whereEqualTo("uid", UID)
                .whereEqualTo("date", date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CE> eventList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                //System.out.println()("-->"+doc.getData().values().contains("SOCIAL"));
                                if(doc.getData().values().contains("GYM"))
                                {
                                    WorkoutPlan e=doc.toObject(WorkoutPlan.class);
                                    wp.add(e);


                                }
                            }
                            //System.out.println()(wp+"\n"+sl+"\n"+st);
                            for(WorkoutPlan w: wp) {
                                AllEvents.get(0).add(w.getName());
                                //  for(Workout ww:w.getWorkouts())
                                if(w.getWorkouts()!=null)
                                    AllEvents.get(1).add(w.getWorkouts().toString());
                                else
                                    AllEvents.get(1).add(w.getDiscription());
                                AllEvents.get(2).add(w.getStartTime());
                                AllEvents.get(3).add(w.getEndTime());
                                AllEvents.get(4).add(Color.RED + "");
                                try {
                                    ev.add(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(w.getStartTime()).getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    ev.add(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(w.getEndTime()).getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });



    }
public void startAlarm(long start)
{
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    Intent intent = new Intent(this,AlarmReceiver.class);

    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    alarmManager.set(AlarmManager.RTC_WAKEUP, start, pendingIntent);

}
    public void changeTheme(View v)
    {
        switch(v.getId())
        {
            case R.id.theme_aqua: case R.id.theme4:
                ThemeSwither.changeToTheme(this,ThemeSwither.AQUA);
                break;
            case R.id.theme_forest:case R.id.theme3:
                ThemeSwither.changeToTheme(this,ThemeSwither.FOREST);
                break;
            case R.id.theme_lava:case R.id.theme2:
                ThemeSwither.changeToTheme(this,ThemeSwither.LAVA);
                break;
            case R.id.theme_midnight:case R.id.theme1:
                ThemeSwither.changeToTheme(this,ThemeSwither.MIDNIGHT);
                break;

        }
    }
    public String getEmojiByUnicode(int unicode)
    {
        ////System.out.println()(unicode);
       String s="";
       try{
        s=new String(Character.toChars(unicode));
       }catch (IllegalArgumentException e){
           ////System.out.println()(e.getCause());
       }
       //System.out.print(s);
       return s;
    }
    public  String pickRandMsg(String usr)
    {
        String[]wel=getResources().getStringArray(R.array.Welcomes);
        ArrayList<String>welcomes=new ArrayList<>();
        ArrayList<String>temp=new ArrayList<>();
        for (String s:wel)
        {
            welcomes.add(s);

        }
        //int x="0x11F601";
        //////System.out.println()(x);
        String str="";
        welcomes.add(getString(R.string.w1,usr));
        welcomes.add(getString(R.string.w2,usr));
        welcomes.add(getString(R.string.w3,usr));
        welcomes.add(getString(R.string.w4,usr));
        welcomes.add(getString(R.string.missme,usr));
        dayProgress[0]=getString(R.string.dm,usr);
        dayProgress[1]=getString(R.string.dm2,usr);
        dayProgress[2]=getString(R.string.dm3,usr);
        dayProgress[3]=getString(R.string.dm4,usr);
        for (String s:welcomes)
        {
            ////System.out.println()(s);
            if(s.contains(getString(R.string.happyEm)))
            {
               str=s.replace(getString(R.string.happyEm),getEmojiByUnicode(0x11F601));
                        ////System.out.println()(str);
                temp.add(str);
            }
            else if(s.contains(getString(R.string.sadEm)))
            {
                str=s.replace(getString(R.string.sadEm),getEmojiByUnicode(0x11F612));
                ////System.out.println()(str);
                temp.add(str);
            }
            else if(s.contains(getString(R.string.cockyem)))
            {
               str= s.replace(getString(R.string.cockyem),getEmojiByUnicode(0x11F60C));
                ////System.out.println()(str);
                temp.add(str);
            }
            else if(s.contains(getString(R.string.wantingEm)))
            {
                str=s.replace(getString(R.string.wantingEm),getEmojiByUnicode(0x11F612));
                ////System.out.println()(str);
                temp.add(str);
            }
            else if(s.contains(getString(R.string.panicEm)))
            {
                str=s.replace(getString(R.string.panicEm),getEmojiByUnicode(0x11F304));
                ////System.out.println()(str);
                temp.add(str);
            }
            else
                temp.add(s);
        }
        int rand=(int)(Math.random()*7);
        ////System.out.println()(welcomes);
        return welcomes.get(rand);
    }
    public Bitmap covertLinkToImg(String imgUrl)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.hbot_wave_lava_sml);
        if(imgUrl!=null) {
            try {
                URL url = new URL(imgUrl);
                int SDK_INT = android.os.Build.VERSION.SDK_INT;

                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    //your codes here

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
    public void signOutDialog(MenuItem item)
    {
        View popupContentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.sign_out, null);
       Button yes,no;
       yes= popupContentView.findViewById(R.id.signout_confirmBtn);
       no= popupContentView.findViewById(R.id.signout_cancelBtn);

        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(popupContentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_sign_out);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(headerView,1,1);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupWindow.update();
        yes.setOnClickListener(e-> {
                    FirebaseAuth.getInstance().signOut();
                    popupWindow.dismiss();
                    startActivity(new Intent(context, LoginActivity.class));
                });
        no.setOnClickListener(e->{
            popupWindow.dismiss();
        });
        // Show popup window offset 1,1 to smsBtton.
       // popupWindow.showAsDropDown(, 1, 1);



    }
    public void signOutDialog()
    {
        View popupContentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.sign_out, null);
        Button yes,no;
        yes= popupContentView.findViewById(R.id.signout_confirmBtn);
        no= popupContentView.findViewById(R.id.signout_cancelBtn);

        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(popupContentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_sign_out);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(headerView,1,1);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupWindow.update();
        yes.setOnClickListener(e-> {
            FirebaseAuth.getInstance().signOut();
            popupWindow.dismiss();
            startActivity(new Intent(context, LoginActivity.class));
        });
        no.setOnClickListener(e->{
            popupWindow.dismiss();
        });
        // Show popup window offset 1,1 to smsBtton.
        // popupWindow.showAsDropDown(, 1, 1);



    }



    public void onLogin()
    {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer!=null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.theme1) {
            ThemeSwither.changeToTheme(this,ThemeSwither.MIDNIGHT);
         //   headerView.setBackground(getResources().getDrawable(R.drawable.midnight_header));

            return true;
        }
        else if (id == R.id.theme2) {

            ThemeSwither.changeToTheme(this,ThemeSwither.LAVA);
//            headerView.setBackground(getResources().getDrawable(R.drawable.lava_header));

            return true;
        }
        else  if (id == R.id.theme3) {

            ThemeSwither.changeToTheme(this,ThemeSwither.FOREST);
           // headerView.setBackground(getResources().getDrawable(R.drawable.forest_header));


            return true;
        }
        else if  (id == R.id.theme4) {

            ThemeSwither.changeToTheme(this,ThemeSwither.AQUA);
          //  headerView.setBackground(getResources().getDrawable(R.drawable.aqua_header));

            return true;
        }
        else if  (id == R.id.theme5) {

            ThemeSwither.changeToTheme(this,ThemeSwither.CLASSIC);
            //  headerView.setBackground(getResources().getDrawable(R.drawable.aqua_header));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_gym) {
            startActivity(new Intent(this,GymActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_social) {
            startActivity(new Intent(this,socialActivity.class));

        } else if (id == R.id.tutorial) {
            startActivity(new Intent(this,VideoListActivity.class));


        } else if (id == R.id.nav_study) {
            startActivity(new Intent(this,StudyActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this,QuickAddActivity.class));

        } else if (id == R.id.nav_send) {
            startActivity(new Intent(this,createNote.class));
        }
        else if (id == R.id.calc) {
            //startActivity(new Intent(this,StudyActivity.class));
        } else if (id == R.id.timer) {
            showTimerMenu(1);
        }
        else if (id == R.id.nav_login) {
            if(LOGGED==false)
            {
                startActivity(new Intent(this, RegisterActivity.class));
                item.setTitle(getString(R.string.login));
                item.setIcon(getDrawable(android.R.drawable.ic_secure));
            }
            else
            {
                signOutDialog(item);
                item.setTitle(getString(R.string.logout));
                item.setIcon(getDrawable(android.R.drawable.ic_partial_secure));
            }
        }
        else if (id == R.id.nav_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUserDetails(String name, String email, String img)
    {
        this.name=name;
        this.mail=email;
        this.img=img;
    }
    public void setUserDetails(String name, String email, Drawable img)
    {
        this.name=name;
        this.mail=email;
        this.image=img;
    }

    public void onProfileClicked(View view) {

       Intent aI=null;
        //user.setText(name);
        name=username.getText().toString();

        //System.out.print(name+"vs"+getString(R.string.guest));
       if(!LOGGED)
      {
            //aI=new Intent("ie.ul.o.daysaver.GymActivity");
        //  aI=new Intent("ie.ul.o.daysaver.socialActivity");
          startActivity(new Intent("ie.ul.o.daysaver.LoginActivity"));
          //aI=new Intent("ie.ul.o.daysaver.FullscreenActivity");
        }
        else {
            signOutDialog();
      }

    }
    private void loadUserInformation()
    {

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user==null)
        {
            UID="sW2tsQFfVcU11VfG4ulc38pMLSU2";
            LOGGED=false;

        }else{
        UID=user.getUid();
        //System.out.printf("UserEmail: %s\nUserID: %s\n UserImage: %s\n",user.getEmail(),user.getUid(),user.getPhotoUrl());
        String photoUrl,displayname;
        if(UID!=null)
        {
            myRefrence=firebaseDatabase.getReference("Users/"+UID+"/UserInformation");

            myRefrence.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInfo1=dataSnapshot.getValue(UserInformation.class);

               if(userInfo1!=null) {LOGGED=true;
                   Log.d("MainActivity","User: \n"+userInfo1.toString());
                   name = (userInfo1.getFirstName() + " \u00B7 " + userInfo1.getLastName());
                   userInfo = userInfo1;
                   ////System.out.println()(userInfo + "\nUserInfo2\n" + userInfo1);
                   dayProgress[0]=getString(R.string.dm,userInfo.getUsername());
                   dayProgress[1]=getString(R.string.dm2,userInfo.getUsername());
                   dayProgress[2]=getString(R.string.dm3,userInfo.getUsername());
                   dayProgress[3]=getString(R.string.dm4,userInfo.getUsername());

                   if (currenttime <4.32e+7  && currenttime >= 32400000) {
                       welcome_to.setText(dayProgress[(int)(Math.random()*1)]);
                   }
                   else if (currenttime <84600000  && currenttime >= 75600000) {
                       welcome_to.setText(dayProgress[(int)((Math.random()*2)+(2))]);
                   }
                   else
                       welcome_to.setText(pickRandMsg(userInfo.getUsername()));


                   username.setText(name);
                   emailView.setText(userInfo.getEmail());
                  // img=userInfo.getImage();
                   RoundImage ri=new RoundImage(covertLinkToImg(userInfo.getImage()));

                   ////System.out.println()("This is my profile Picture..."+userInfo.getImage());
                   if(pp!=null)
                   {
                       ////System.out.println()("Profile Icon is not null***: "+pp.getId());
                              }
                   else{
                       ////System.out.println()("ProfileIcon is not assigned at this time...");
                       /**Assigning profile icon*/
                        //TODO:Fix headerBackground and profile pic ture low priority.
                       pp= headerView.findViewById(R.id.PROFILEPICTUREBTN);

                       try{
                           ////System.out.println()(nav_header.getId());
                           ////System.out.println()("HeaderView ID: "+headerView.getId());
                           ////System.out.println()("assigned ID: "+pp);
                           pp.setImageDrawable(ri);

                       }catch (NullPointerException e){ ////System.out.println()("A Few objects are empty \n"+e.getLocalizedMessage());
                           }
                   }
                   //System.out.println()("IMAGE: "+img);
               }else{LOGGED=false;
                    userInfo1=new UserInformation();
                    userInfo1.setFirstName("Guest");
                    userInfo1.setLastName("");
                    userInfo1.setUsername("Guest");
                    userInfo1.setAge(100);
                    userInfo1.setEmail("ojdevs@gmail.com");
                    userInfo1.setImage(getResources().getDrawable(R.drawable.hbot).toString());
                   name = (userInfo1.getFirstName() + " \u00B7 " + userInfo1.getLastName());
                   userInfo = userInfo1;
                   ////System.out.println()(userInfo + "\nUserInfo2\n" + userInfo1);
                   welcome_to.setText(getString(R.string.welcomeUser, userInfo.getUsername()));
                   username.setText(name);
                   emailView.setText(userInfo.getEmail());
                   if(pp!=null)
                  pp.setImageBitmap(createRoundedImageFromDrawable(covertLinkToImg(userInfo.getImage())));


               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });//////System.out.println()(">>>"+userInfo.getUsername());
       /* if(!(img==null||img.equals("")))
        {
            //ImageView iv=createRoundedImageFromDrawable(pp);
            photoUrl=img;
            ////System.out.println()("IMG: "+img);
            //loadImage(img,pp,60,60);
        }else
            loadImage("gs://hours-1a681.appspot.com/profile_pic/hbot_wave.png",pp,60,60);
*/
        if(user.getDisplayName()!=null)
        {
            displayname=user.getDisplayName();
                  }

        }
        }
        ////System.out.println()("Looged? "+LOGGED);
    }
    public Bitmap createRoundedImageFromDrawable(Bitmap img)
    {


        RoundImage roundImage=new RoundImage(img);

        return roundImage.getBitmap();
    }
    public void loadImage(String url,ImageView imgContainer,int w,int h)
    {
        ////System.out.println()(">>>>>"+url);
        Picasso.get()
                .load(url)
                .resize(w,h)
                .centerCrop()
                .into(imgContainer);
    }
    NotificationCompat.Builder notification;
    NotificationManager notificationManager;

    public void setProgressNotification(int ic, String title,int progress,String msg)
    {
        notification =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);


        notification.setContentIntent(pendingIntent);
        notification.setProgress(100,progress,false);
        notification.setSmallIcon(ic);
        notification.setContentTitle(title);
        notification.setContentText(msg);
        notification.setPriority(Notification.PRIORITY_MAX);
        notification.setAutoCancel(false);

      notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

       // notificationManager.notify(0, notification.build());


}
    public void setNotification(String heading,String msg,String BT,String sumText, String BCT,int ic)
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(BT);
        bigText.setBigContentTitle(BCT);
        bigText.setSummaryText(sumText);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(ic);
        mBuilder.setContentTitle(heading);
        mBuilder.setContentText(msg);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }



    public void setAlarm(final long timeinMillis, final int REQUEST_CODE, final boolean setRepeating, final long repeatAfter) {

        long alertTime=timeinMillis;
        //System.out.println()(alertTime);

        Intent alertIntent=new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, alertIntent, 0);



        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeinMillis, pendingIntent);
        if (setRepeating == true && repeatAfter >= 60000){ alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeinMillis, repeatAfter, pendingIntent);    }
    }

    public void noConMsg()
    {
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.no_connection).setTitle(R.string.no_network);


        builder.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                finishAndRemoveTask();
                adialog.dismiss();
            }
        });
        builder.setIcon(android.R.drawable.stat_notify_error);

        android.support.v7.app.AlertDialog adialog=builder.create();

        adialog.show();

    }


    @Override
    public void onStart() {

        super.onStart();
        if(utils.isNetworkAvailable()){
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));


            /**get user details*/
        }}
        else
        {
            main_header.setBackgroundColor(Color.RED);
            noConMsg();
        }
        ////System.out.println()(userInfo+"<");
      //  loadUserInformation();





      //  ////System.out.println()("->>"+getEmail());
    }
}
