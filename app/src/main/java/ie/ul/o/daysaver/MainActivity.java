package ie.ul.o.daysaver;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ie.ul.o.daysaver.utils.*;

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
        nav_header=(LinearLayout)headerView.findViewById(R.id.navi_header);
        ThemeSwither.onActivityCreateSetTheme(this,nav_header);
        setContentView(R.layout.activity_main);
         dayProgress=new String[4];//=getResources().getStringArray(R.array.DayComments);
        pb=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);




        menu2=navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
        LayoutInflater inflater1=getLayoutInflater();
        //View tailView=inflater.inflate(R.layout.activity_main_drawer,null);



        navigationView.addHeaderView(headerView);
        Dayprogress=(TextView)findViewById(R.id.dayProgress);
        // navigationView.addView(tailView);
        username= (TextView)headerView.findViewById(R.id.user);
        hbot_main=(ImageView)findViewById(R.id.hBot_main);
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.color_dialog,null);
        aqua=(Button)dialogView.findViewById(R.id.theme_aqua);
        lava=(Button)dialogView.findViewById(R.id.theme_lava);
        midnight=(Button)dialogView.findViewById(R.id.theme_midnight);
        forest=(Button)dialogView.findViewById(R.id.theme_forest);
        main_header=(LinearLayout)findViewById(R.id.main_header);
       // main_activity_view=(ScrollView)findViewById(R.id.main_activity_view);
        nav_header=(LinearLayout)headerView.findViewById(R.id.navi_header);
        fullscreen=(RelativeLayout)findViewById(R.id.fullScreen);
        msin_toolbar=(Toolbar)findViewById(R.id.toolbar);
        emailView=(TextView)headerView.findViewById(R.id.email);
       //pp=(ImageButton) headerView.findViewById(R.id.PROFILEPICTUREBTN);
        eventViwer=findViewById(R.id.mainRV);
      initViews();

        image=getDrawable(R.mipmap.ic_launcher_round);



        email=(TextView)findViewById(R.id.email);
        welcome_to=(TextView)findViewById(R.id.welcomeTo);

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
        //System.out.println(THEME);



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
                                else System.out.println(AllEvents.get(3).get(i)+" ,NO II CANT ");
                            }*/

                            TextView tdate = (TextView) findViewById(R.id.main_date);
                            TextView ttime = (TextView) findViewById(R.id.main_time);

                            long date = System.currentTimeMillis();
                            Date c = new Date();
                            long nowTime = c.getTime() % (24 * 60 * 60 * 1000L);
                            ////System.out.println("Time: " + nowTime + "\nDayTime: 86400000 \nProgress: " + (nowTime * 100) / 86400000);
                            int rst=(int)(100-((nowTime * 100) / 86400000));
                           // //System.out.println(rst);
                            Dayprogress.setText(getString(R.string.progress,+rst+"% "));
                            goOn = false;
                            currenttime=nowTime;
                            ProgressBar daySeek = (ProgressBar) findViewById(R.id.dayscale);
                            if (daySeek != null && tdate != null && ttime != null) {
                                daySeek.setProgress((int) ((nowTime * 100) / 86400000));


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
                                    //System.out.println("haha");
                                }




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



        //email.setText(mail);
        //pp.setBackground(image);
        //System.out.println("Username "+welcome_to.getText().toString()+"\nEmail "+email);
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
    public void LoadMainEvents(String date)
    {
        pb.setMessage(getString(R.string.wait5,username.getText().toString()));
        pb.show();
        System.out.printf(date);
        mFireStore.collection(UID)
                .whereEqualTo("date", date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CE> eventList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                System.out.println("-->"+doc.getData().values().contains("SOCIAL"));
                                if(doc.getData().values().contains("SOCIAL"))
                                {
                                    Social e=doc.toObject(Social.class);
                                    sl.add(e);

                                }
                                else if(doc.getData().values().contains("GYM"))
                                {
                                    WorkoutPlan e=doc.toObject(WorkoutPlan.class);
                                    wp.add(e);


                                }
                                else if(doc.getData().values().contains("STUDY"))
                                {
                                    Study e=doc.toObject(Study.class);
                                    st.add(e);


                                }
                                else if(doc.getData().values().contains("QUICKADD"))
                                {
                                    QuickAdd e=doc.toObject(QuickAdd.class);
                                    qua.add(e);


                                }

                            }
                            System.out.println(wp+"\n"+sl+"\n"+st);
                            for(WorkoutPlan w: wp) {
                                AllEvents.get(0).add(w.getName());
                                //  for(Workout ww:w.getWorkouts())
                                if(w.getWorkouts()!=null)
                                AllEvents.get(1).add(w.getWorkouts().toString());
                                else
                                    AllEvents.get(1).add("No Info");
                                AllEvents.get(2).add(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(w.getDay()));
                                AllEvents.get(3).add(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(w.getD_O_C()));
                                AllEvents.get(4).add(Color.RED + "");
                            }
                            for(Social s:sl)
                            {
                                AllEvents.get(0).add(s.getHeading());
                                AllEvents.get(1).add(s.getDiscription());
                                AllEvents.get(2).add(s.startTime);
                                AllEvents.get(3).add(s.endTime);
                                AllEvents.get(4).add(Color.MAGENTA+"");
                            }
                            //Description and stuff...
                            for(Study s:st)
                            {
                                AllEvents.get(0).add("Study");
                                AllEvents.get(1).add(s.getDiscription());
                                AllEvents.get(2).add("Undefined");
                                AllEvents.get(3).add(s.getDuration()+"");
                                AllEvents.get(4).add(Color.YELLOW+"");
                            }
                            for(QuickAdd s:qua)
                            {
                                AllEvents.get(0).add("Quick Add");
                                AllEvents.get(1).add(s.getDescription());
                                AllEvents.get(2).add(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(s.getStartTime()));
                                AllEvents.get(3).add(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(s.getEndTime()));
                                AllEvents.get(4).add(getColor(android.R.color.holo_blue_light)+"");
                            }
                            System.out.println(AllEvents);
                            if(context!=null)
                            {
                                mainAdapter=new MainAdapter(context,AllEvents);
                                eventViwer.setAdapter(mainAdapter);
                            }
                            pb.dismiss();
                          //  Toast.makeText(context, "Events Loaded Success", Toast.LENGTH_SHORT).show();


                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        }
                    }
                });




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
        //System.out.println(unicode);
       String s="";
       try{
        s=new String(Character.toChars(unicode));
       }catch (IllegalArgumentException e){
           //System.out.println(e.getCause());
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
        ////System.out.println(x);
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
            //System.out.println(s);
            if(s.contains(getString(R.string.happyEm)))
            {
               str=s.replace(getString(R.string.happyEm),getEmojiByUnicode(0x11F601));
                        //System.out.println(str);
                temp.add(str);
            }
            else if(s.contains(getString(R.string.sadEm)))
            {
                str=s.replace(getString(R.string.sadEm),getEmojiByUnicode(0x11F612));
                //System.out.println(str);
                temp.add(str);
            }
            else if(s.contains(getString(R.string.cockyem)))
            {
               str= s.replace(getString(R.string.cockyem),getEmojiByUnicode(0x11F60C));
                //System.out.println(str);
                temp.add(str);
            }
            else if(s.contains(getString(R.string.wantingEm)))
            {
                str=s.replace(getString(R.string.wantingEm),getEmojiByUnicode(0x11F612));
                //System.out.println(str);
                temp.add(str);
            }
            else if(s.contains(getString(R.string.panicEm)))
            {
                str=s.replace(getString(R.string.panicEm),getEmojiByUnicode(0x11F304));
                //System.out.println(str);
                temp.add(str);
            }
            else
                temp.add(s);
        }
        int rand=(int)(Math.random()*7);
        //System.out.println(welcomes);
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
       yes=(Button)popupContentView.findViewById(R.id.signout_confirmBtn);
       no=(Button)popupContentView.findViewById(R.id.signout_cancelBtn);

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
        yes=(Button)popupContentView.findViewById(R.id.signout_confirmBtn);
        no=(Button)popupContentView.findViewById(R.id.signout_cancelBtn);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        } else if (id == R.id.nav_timetable) {


        } else if (id == R.id.nav_study) {
            startActivity(new Intent(this,StudyActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                   //System.out.println(userInfo + "\nUserInfo2\n" + userInfo1);
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

                   //System.out.println("This is my profile Picture..."+userInfo.getImage());
                   if(pp!=null)
                   {
                       //System.out.println("Profile Icon is not null***: "+pp.getId());
                              }
                   else{
                       //System.out.println("ProfileIcon is not assigned at this time...");
                       /**Assigning profile icon*/
                        //TODO:Fix headerBackground and profile pic ture low priority.
                       pp= headerView.findViewById(R.id.PROFILEPICTUREBTN);

                       try{
                           //System.out.println(nav_header.getId());
                           //System.out.println("HeaderView ID: "+headerView.getId());
                           //System.out.println("assigned ID: "+pp);
                           pp.setImageDrawable(ri);

                       }catch (NullPointerException e){ //System.out.println("A Few objects are empty \n"+e.getLocalizedMessage());
                           }
                   }
                   System.out.println("IMAGE: "+img);
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
                   //System.out.println(userInfo + "\nUserInfo2\n" + userInfo1);
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
       });////System.out.println(">>>"+userInfo.getUsername());
       /* if(!(img==null||img.equals("")))
        {
            //ImageView iv=createRoundedImageFromDrawable(pp);
            photoUrl=img;
            //System.out.println("IMG: "+img);
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
        //System.out.println("Looged? "+LOGGED);
    }
    public Bitmap createRoundedImageFromDrawable(Bitmap img)
    {


        RoundImage roundImage=new RoundImage(img);

        return roundImage.getBitmap();
    }
    public void loadImage(String url,ImageView imgContainer,int w,int h)
    {
        //System.out.println(">>>>>"+url);
        Picasso.get()
                .load(url)
                .resize(w,h)
                .centerCrop()
                .into(imgContainer);
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
        //System.out.println(userInfo+"<");
      //  loadUserInformation();





      //  //System.out.println("->>"+getEmail());
    }
}
