package ie.ul.o.daysaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**75% working few kinks left*/
public class createWorkout extends GymActivity implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String TAG = "♂";
    private static  List<Workout> WORKOUTZ ;
    private EditText workoutName;
    private RadioGroup daytype;
    private RadioButton r1;
    private Spinner occurance;
    private TimePicker timePicker;
    private DatePickerDialog datePickerDialog;
    private Switch switcher;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout ll,timeContainer;
    private ArrayList<Long> startTimes,endTimes;
    private ArrayList<ArrayList<String>>exercises=new ArrayList<>();
    private ArrayList<Bitmap>imgs=new ArrayList<>();
    private List<Workout>workoutList=new ArrayList<>();
    private AdapterA myAdapter;
    private TextView dateView;
    private Button settimeBtn;
    ImageButton hideBtn;
    boolean hide=false;
    private Date date;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    private String occ="Once",occures="Only On";
    private String workoutname="Unkonwn"+simpleDateFormat.format(System.currentTimeMillis()),day="Mon",dd,dayTime="18:00",status="private";
    private EditText duration;
    private TextInputLayout enterDay;
    private String dur="1.30";
    private long startTime=System.currentTimeMillis(), endtime=System.currentTimeMillis()+3600000;
    private String MUSCLEGROUP;
    private Button addNewWrkout;
    LinearLayout hideBox;
    private String DEBUG_TAG;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    String UID=firebaseAuth.getCurrentUser().getUid();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

    private FrameLayout frameLayout;
    private ArrayList<Workout>wL;
    private Button completeBtn;
    public static boolean addWorkout;
    private ImageButton helpBtn;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        exercises.add(new ArrayList<String>());
        exercises.add(new ArrayList<String>());
        exercises.add(new ArrayList<String>());//sets
        exercises.add(new ArrayList<String>());//reps
        startTimes=new ArrayList<>();
        endTimes=new ArrayList<>();
        imgs=new ArrayList<>();
        wL=new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
       // refreshListOfWorkouts();
        frameLayout=findViewById(R.id.frameLayout);
         hideBox=findViewById(R.id.hideBox);
        Toolbar tool = findViewById(R.id.ctb);
        setSupportActionBar(tool);
        workoutName=findViewById(R.id.newPlanText);
        daytype=findViewById(R.id.days_rBtn);
        occurance=findViewById(R.id.occurance_ofDay);
        switcher=findViewById(R.id.switch1);
        dateView=findViewById(R.id.date_Result);
        enterDay=findViewById(R.id.enterDay);
        helpBtn=findViewById(R.id.help);
        addNewWrkout=findViewById(R.id.addWorkoutBTN);
        completeBtn=findViewById(R.id.CompleteBtn);
        completeBtn.setEnabled(false);
        completeBtn.setAlpha(0.4f);
        completeBtn.setOnClickListener(e->{

             for(int i=0;i<startTimes.size();i++)
                //System.out.println()(new WorkoutPlan(workoutname,startTimes.get(i),wL,endTimes.get(i),UID, "GYM", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startTimes.get(i)),new SimpleDateFormat("HH:mm",Locale.getDefault()).format(startTimes.get(i)),new SimpleDateFormat("HH:mm",Locale.getDefault()).format(endTimes.get(i))));
            saveNewWorkoutPlan(); finish();workoutName.getText().clear();enterDay.getEditText().getText().clear();switcher.setChecked(true);

        });

        settimeBtn=findViewById(R.id.setTimebn);
        hideBtn=findViewById(R.id.hideBtn);
        ll=findViewById(R.id.wholeField);//heading_layout
        timeContainer=findViewById(R.id.timecontainer);
        duration=findViewById(R.id.time_spent);


        collectDataP1();
        //System.out.println()("workout name: +"+workoutName+"\nStart time: "+simpleDateFormat.format(startTime)+"\nEndTime: "+simpleDateFormat.format(convertDurationToTimeDone(startTime))+"\n");
        hideBox.setOnTouchListener((view, event) -> {
            int action = MotionEventCompat.getActionMasked(event);

            switch(action) {
                case (MotionEvent.ACTION_UP) :{
                    Log.d(DEBUG_TAG,"Action was UP");
                    if(hide==false)
                    {
                        hide=true;
                        if(timeContainer.getVisibility()!=View.GONE){

                            ObjectAnimator rotate=ObjectAnimator.ofFloat(hideBtn,"rotation",+180f,0F);
                            rotate.setDuration(350);
                            AnimatorSet ranimatorSet=new AnimatorSet();
                            ranimatorSet.playTogether(rotate);
                            ranimatorSet.start();

                            //System.out.println()("hide");
                            ObjectAnimator animatorY=ObjectAnimator.ofFloat(ll,"y",-555f);
                            animatorY.setDuration(350);
                            AnimatorSet animatorSet=new AnimatorSet();
                            animatorSet.playTogether(animatorY);
                            animatorSet.start();

                        }
                        else if(occurance.getVisibility()!=View.GONE)
                        {
                            ObjectAnimator rotate=ObjectAnimator.ofFloat(hideBtn,"rotation",+180f,0F);
                            rotate.setDuration(350);
                            AnimatorSet ranimatorSet=new AnimatorSet();
                            ranimatorSet.playTogether(rotate);
                            ranimatorSet.start();

                            //System.out.println()("hide");
                            ObjectAnimator animatorY=ObjectAnimator.ofFloat(ll,"y",-720);
                            animatorY.setDuration(350);
                            AnimatorSet animatorSet=new AnimatorSet();
                            animatorSet.playTogether(animatorY);
                            animatorSet.start();

                        }else {


                            ObjectAnimator rotate = ObjectAnimator.ofFloat(hideBtn, "rotation", +180f, 0F);
                            rotate.setDuration(350);
                            AnimatorSet ranimatorSet = new AnimatorSet();
                            ranimatorSet.playTogether(rotate);
                            ranimatorSet.start();

                            //System.out.println()("hide");
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(ll, "y", -380f);
                            animatorY.setDuration(350);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(animatorY);
                            animatorSet.start();
                        }


                    }

                }
                return true;

                case (MotionEvent.ACTION_DOWN) : {
                    Log.d(DEBUG_TAG, "Action was UP");
                    if (hide == true) {
                        //System.out.println()("Show");
                        hide = false;
                        if (timeContainer.getVisibility() != View.GONE) {

                            ObjectAnimator rotate = ObjectAnimator.ofFloat(hideBtn, "rotation", +0f, 180F);
                            rotate.setDuration(350);
                            AnimatorSet ranimatorSet = new AnimatorSet();
                            ranimatorSet.playTogether(rotate);
                            ranimatorSet.start();

                            //System.out.println()("hide");
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(ll, "y", 0f);
                            animatorY.setDuration(350);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(animatorY);
                            animatorSet.start();
                            hideBtn.setRotation(0);
                        } else if (occurance.getVisibility() != View.GONE) {
                            ObjectAnimator rotate = ObjectAnimator.ofFloat(hideBtn, "rotation", +0f, 180F);
                            rotate.setDuration(350);
                            AnimatorSet ranimatorSet = new AnimatorSet();
                            ranimatorSet.playTogether(rotate);
                            ranimatorSet.start();

                            //System.out.println()("hide");
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(ll, "y", 0f);
                            animatorY.setDuration(350);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(animatorY);
                            animatorSet.start();
                            hideBtn.setRotation(0);
                        } else {


                            ObjectAnimator rotate = ObjectAnimator.ofFloat(hideBtn, "rotation", +0f, 180F);
                            rotate.setDuration(350);
                            AnimatorSet ranimatorSet = new AnimatorSet();
                            ranimatorSet.playTogether(rotate);
                            ranimatorSet.start();

                            //System.out.println()("hide");
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(ll, "y", 0);
                            animatorY.setDuration(350);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(animatorY);
                            animatorSet.start();
                        }
                    }
                }return true;

            }

            return true;

        });
        helpBtn.setOnClickListener(e->{alertBox();});

        hideBtn.setOnClickListener(e->{
            if(hide==false)
            {
                hide=true;
                if(timeContainer.getVisibility()!=View.GONE){

                    ObjectAnimator rotate=ObjectAnimator.ofFloat(hideBtn,"rotation",+180f,0F);
                    rotate.setDuration(350);
                    AnimatorSet ranimatorSet=new AnimatorSet();
                    ranimatorSet.playTogether(rotate);
                    ranimatorSet.start();

                    //System.out.println()("hide");
                    ObjectAnimator animatorY=ObjectAnimator.ofFloat(ll,"y",-555f);
                    animatorY.setDuration(350);
                    AnimatorSet animatorSet=new AnimatorSet();
                    animatorSet.playTogether(animatorY);
                    animatorSet.start();

                }
                else if(occurance.getVisibility()!=View.GONE)
                {
                    ObjectAnimator rotate=ObjectAnimator.ofFloat(hideBtn,"rotation",+180f,0F);
                    rotate.setDuration(350);
                    AnimatorSet ranimatorSet=new AnimatorSet();
                    ranimatorSet.playTogether(rotate);
                    ranimatorSet.start();

                    //System.out.println()("hide");
                    ObjectAnimator animatorY=ObjectAnimator.ofFloat(ll,"y",-720);
                    animatorY.setDuration(350);
                    AnimatorSet animatorSet=new AnimatorSet();
                    animatorSet.playTogether(animatorY);
                    animatorSet.start();

                }else {


                    ObjectAnimator rotate = ObjectAnimator.ofFloat(hideBtn, "rotation", +180f, 0F);
                    rotate.setDuration(350);
                    AnimatorSet ranimatorSet = new AnimatorSet();
                    ranimatorSet.playTogether(rotate);
                    ranimatorSet.start();

                    //System.out.println()("hide");
                    ObjectAnimator animatorY = ObjectAnimator.ofFloat(ll, "y", -380f);
                    animatorY.setDuration(350);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(animatorY);
                    animatorSet.start();
                }

              /*  Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        hideBtn.animate().rotationBy(180).withEndAction(this).setDuration(800).setInterpolator(new LinearInterpolator()).start();
                    }
                };

                hideBtn.animate().rotationBy(180).withEndAction(runnable).setDuration(800).setInterpolator(new LinearInterpolator()).start();
               */ //ll.setVisibility(View.GONE);

            }
            else
            {
                //System.out.println()("Show");
                hide=false;
                if(timeContainer.getVisibility()!=View.GONE){

                    ObjectAnimator rotate=ObjectAnimator.ofFloat(hideBtn,"rotation",+0f,180F);
                    rotate.setDuration(350);
                    AnimatorSet ranimatorSet=new AnimatorSet();
                    ranimatorSet.playTogether(rotate);
                    ranimatorSet.start();

                    //System.out.println()("hide");
                    ObjectAnimator animatorY=ObjectAnimator.ofFloat(ll,"y",0f);
                    animatorY.setDuration(350);
                    AnimatorSet animatorSet=new AnimatorSet();
                    animatorSet.playTogether(animatorY);
                    animatorSet.start();
                    hideBtn.setRotation(0);
                }
                else if(occurance.getVisibility()!=View.GONE)
                {
                    ObjectAnimator rotate=ObjectAnimator.ofFloat(hideBtn,"rotation",+0f,180F);
                    rotate.setDuration(350);
                    AnimatorSet ranimatorSet=new AnimatorSet();
                    ranimatorSet.playTogether(rotate);
                    ranimatorSet.start();

                    //System.out.println()("hide");
                    ObjectAnimator animatorY=ObjectAnimator.ofFloat(ll,"y",0f);
                    animatorY.setDuration(350);
                    AnimatorSet animatorSet=new AnimatorSet();
                    animatorSet.playTogether(animatorY);
                    animatorSet.start();
                    hideBtn.setRotation(0);
                }else {


                    ObjectAnimator rotate = ObjectAnimator.ofFloat(hideBtn, "rotation", +0f, 180F);
                    rotate.setDuration(350);
                    AnimatorSet ranimatorSet = new AnimatorSet();
                    ranimatorSet.playTogether(rotate);
                    ranimatorSet.start();

                    //System.out.println()("hide");
                    ObjectAnimator animatorY = ObjectAnimator.ofFloat(ll, "y", 0);
                    animatorY.setDuration(350);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(animatorY);
                    animatorSet.start();
                }

            }

        });
        setUpListeners();


        recyclerView=findViewById(R.id.listOfMadeWorkout); initViews();
      //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //System.out.println()("Create workoutsSays.../."+AddNewWorkout.getOldWorkouts());
        myAdapter = new AdapterA(this, workoutList);

        recyclerView.setAdapter(myAdapter);
       // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        // making http call and fetching menu json
        prepareList();
    }

    private void prepareList() {
        if(AddNewWorkout.getOldWorkouts()!=null) {
            //workoutList.clear();

           workoutList.addAll(AddNewWorkout.getOldWorkouts());
            myAdapter.notifyDataSetChanged();
          //  AddNewWorkout.setWORKOUTS(workoutList);
            WORKOUTZ=workoutList;

        }//refreshListOfWorkouts();
            //System.out.println()("-------------------------------------->" + workoutList);
        wL.addAll(workoutList);

    }


    @Override

    protected void onSaveInstanceState(Bundle state) {

        super.onSaveInstanceState(state);

        state.putSerializable("starttime", startTime);
        state.putSerializable("workoutName",workoutname);
        state.putSerializable("endTime",endtime);


    }



    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterA.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = workoutList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final Workout deletedItem = workoutList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            myAdapter.removeItem(viewHolder.getAdapterPosition());
           // workoutList.remove(viewHolder.getAdapterPosition());
            AddNewWorkout.setWORKOUTS(workoutList);

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(frameLayout, name + " removed from plan!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    myAdapter.restoreItem(deletedItem, deletedIndex);
                  //  workoutList.add(deletedIndex,deletedItem);
                    AddNewWorkout.setWORKOUTS(workoutList);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

            //  new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        }
    }
WorkoutPlan event;
    public void saveNewWorkoutPlan()
    {
        if(!startTimes.isEmpty()) {
            event = new WorkoutPlan(workoutname, startTimes.get(0), wL, endTimes.get(0), UID, "GYM", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startTimes.get(0)), new SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTimes.get(0)), new SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTimes.get(0)));

            if (status.equalsIgnoreCase("public")) {

                firebaseFirestore.collection("Public_workouts")
                        .add(event)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                           /* Toast.makeText(getActivity(),
                                    "Event document has been added",
                                    Toast.LENGTH_SHORT).show();*/
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding event documtent", e);
                           /* Toast.makeText(getActivity(),
                                    "Event document could not be added",
                                    Toast.LENGTH_SHORT).show();*/
                            }
                        });

            } else {
                //System.out.println()("Status: " + status);
                firebaseFirestore.collection("Private_workouts")
                        .add(event)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                           /* Toast.makeText(getActivity(),
                                    "Event document has been added",
                                    Toast.LENGTH_SHORT).show();*/
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding event documtent", e);
                           /* Toast.makeText(getActivity(),
                                    "Event document could not be added",
                                    Toast.LENGTH_SHORT).show();*/
                            }
                        });
            }
            try {
                for (int i = 0; i < startTimes.size(); i++)

                {
                    List<Workout> workouttoSave = new ArrayList<>();
                    //System.out.println()("*****" + wL);
                    //System.out.println()("++++++**" + workoutList);


                    event = new WorkoutPlan(workoutname, startTimes.get(i), wL, endTimes.get(i), UID, "GYM", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startTimes.get(i)), new SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTimes.get(i)), new SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTimes.get(i)));
                    firebaseFirestore.collection(UID)
                            .add(event)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                           /* Toast.makeText(getActivity(),
                                    "Event document has been added",
                                    Toast.LENGTH_SHORT).show();*/
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding event documtent", e);
                           /* Toast.makeText(getActivity(),
                                    "Event document could not be added",
                                    Toast.LENGTH_SHORT).show();*/
                                }
                            });
                }
            } catch (NullPointerException n) {
                Log.w(TAG, n.getMessage());
            }
        }else
            Toast.makeText(context,"oops Didnt save",Toast.LENGTH_SHORT).show();

    }
    private final Context context=this;
    public void alertBox()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.info1, null);
        builder.setView(dialogView);
        Button yes,no;
        yes= dialogView.findViewById(R.id.button6);
        //System.out.println()("Here");
        AlertDialog adialog=builder.create();

        adialog.show();

        yes.setOnClickListener(e->{adialog.dismiss();});


    }
    public void showPopUpBox()
    {
        View popupContentView = LayoutInflater.from(context).inflate(R.layout.info1, null);
        Button yes,no;
        yes= popupContentView.findViewById(R.id.button6);
        //System.out.println()("Here");


        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(popupContentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_sign_out);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(addNewWrkout,0,0);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupWindow.update();
        yes.setOnClickListener(e-> {

            popupWindow.dismiss();

        });

        // Show popup window offset 1,1 to smsBtton.
        // popupWindow.showAsDropDown(, 1, 1);



    }


       // setHasOptionsMenu(true)}

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public void onStart() {
        super.onStart();
        //System.out.println()("clicked? "+addWorkout);
        //System.out.println()("\n\n\nstart..............");

        if(addWorkout==false) {
            hideBox.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
            new CountDownTimer(2000,1000) {

                @RequiresApi(api = Build.VERSION_CODES.M)
                public void onTick(long milliUntilFinish) {
                    //System.out.println()(milliUntilFinish);

                }

                public void onFinish() {
                   alertBox();
                    //System.out.println()("Finished");

                }
            }.start();


        }
        else{
            hideBox.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
        }
        addNewWrkout.setOnClickListener(e->{
            addWorkout=true;
            completeBtn.setEnabled(true);
            completeBtn.setAlpha(1f);
            startActivity(new Intent(this,AddNewWorkout.class));


        });

    }
    public Bitmap covertLinkToImg(String imgUrl)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.hbot_gym_sml);
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

    //TODO: Disperse weekly
    //TODO: Disperse Mothly
    public long convertDurationToTimeDone(long Timestrt)
    {

        //System.out.println()("Timstrt**"+dur+"*+Timestrt+\n"+((long)(Double.parseDouble(dur))*3600000)+Timestrt);
        return (long)(Double.parseDouble(dur)*3600000)+Timestrt;
    }

    public void collectDataP1()
    {


        /**Dayy*/

    }
    private long converttoLong(String t)
    {
        //System.out.println()(t);
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm",Locale.getDefault());
        try {
            d=sdf.parse(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println()("*****"+d.getTime());
        return  d.getTime();
    }

    private void initViews(){

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.creator, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case  R.id.save_id:
            {if(completeBtn.isEnabled())saveWorkout();completeBtn.setAlpha(1f);}
            break;
            case R.id.exit_id: {
                finish();
                startActivity(new Intent(getApplicationContext(),GymActivity.class));
            }break;


        }
        return true;
    }

    private void saveWorkout() {
    }


    private boolean validateTimeInput(String time) {

        String pattern="(?:[01][0-9]|2[0-3]):([0-5][0-9]):[0-5][0-9]";
        String pattern3="([0-9]:([0-9]|([0-5][0-9])):([0-9]|([0-5][0-9])))";
        String pattern4="(([0-9]:[0]:[0])|([0-9]:[0-5][0-9])|([0-9]:[0]))";
        String pattern2="(?:[01][0-9]|2[0-3]):([0-5][0-9])";
        //System.out.println()(time);
        return time.matches(pattern) || (time.matches(pattern2)) ||
                time.matches(pattern3) || time.matches(pattern4);



    }


    public long getDaysRB(View view) {


        int radioID=daytype.getCheckedRadioButtonId();//2131362481
        RadioButton singleButton= findViewById(radioID);

        switch (radioID) {
            case R.id.radioButton: {
                //System.out.println()("Enabling dropdown selection..");
                occurance.setVisibility(View.VISIBLE);
                enterDay.setVisibility(View.VISIBLE);
                timeContainer.setVisibility(View.GONE);
                occurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //System.out.println()("OCC: " + occurance.getSelectedItem().toString());
                        occ = occurance.getSelectedItem().toString();
                        if (occ.equalsIgnoreCase("Once"))
                            occures = "Only on ";
                        else if (occ.equalsIgnoreCase("Daily"))
                            occures = "Every Day on";
                        else if (occ.equalsIgnoreCase("Monthly"))
                            occures = "Every Month on";
                        else occures = "Only on";

                        dateView.setText(occures + " " + day + " ");


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                /**Validate enter day*/


            }
            break;
            case R.id.radioButton2: {
                //System.out.println()("Getting.. th Date picker");
                occurance.setVisibility(View.GONE);
                enterDay.setVisibility(View.GONE);
                timeContainer.setVisibility(View.VISIBLE);
                datePickerDialog.show();

            }break;

        }


        //System.out.println()(radioID);
        return 0;
    }

    public void setUpListeners() {

        /**Workout name*/
        workoutName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                workoutname=workoutName.getText().toString();
                completeBtn.setEnabled(true);
                completeBtn.setAlpha(1f);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        /**Listener For Day*/
        enterDay.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String p1 = "MON|MONDAY|M", p2 = "TUES|TUESDAY|TU", p3 = "WED|WED|W", p4 = "THURS|THUR|TH|THURSDAY", p5 = "FRI|FRIDAY|F", p6 = "SAT|SATURDAY|ST", p7 = "SUN,SUNDAY,SN";
                String  patternX="0.10|0.20|0.30|0.40|0.50|1|1.10|1.20|1.30|1.40|1.50|2.00";
                String input = enterDay.getEditText().getText().toString().toUpperCase().replaceAll("\\s", "");
                ArrayList<ArrayList<String>> temp = new ArrayList<>();

                temp.add(new ArrayList<>());
                temp.add(new ArrayList<>()); temp.add(new ArrayList<>());
                if (input.matches(pattern)) {
                   convertListToLongs(temp); enterDay.setError(null);
                    stop = true;
                    if (input.matches(p1)) {for (String s : getAllMondaysInAyear(2018, i + 1, "Monday")) {
                        temp.get(0).add(s);
                        temp.get(1).add(dayTime);
                        temp.get(2).add(dur);

                    }
                       convertListToLongs(temp); enterDay.setError(null);
                        day = "Monday";
                        dateView.setText(occures + " " + day + " ");
                        dd = occ + " " + day + " ";
                    } else if (input.matches(p2)) {for (String s : getAllMondaysInAyear(2018, i + 1, "Tuesday")) {
                        temp.get(0).add(s);
                        temp.get(1).add(dayTime);
                        temp.get(2).add(dur);

                    }
                       convertListToLongs(temp); enterDay.setError(null);
                        day = "Tuesday";
                        dateView.setText(occures + " " + day + " ");
                        dd = occ + " " + day + " ";
                    } else if (input.matches(p3)) {for (String s : getAllMondaysInAyear(2018, i + 1, "Wednesday")) {
                        temp.get(0).add(s);  temp.get(1).add(dayTime);
                        temp.get(2).add(dur);

                    }
                       convertListToLongs(temp); enterDay.setError(null);
                        day = "Wednesday";
                        dateView.setText(occures + " " + day + " ");
                        dd = occ + " " + day + " ";
                    } else if (input.matches(p4)) {for (String s : getAllMondaysInAyear(2018, i + 1, "Thursday")) {
                        temp.get(0).add(s);  temp.get(1).add(dayTime);
                        temp.get(2).add(dur);

                    }
                       convertListToLongs(temp); enterDay.setError(null);
                        day = "Thursday";
                        dateView.setText(occures + " " + day + " ");
                        dd = occ + " " + day + " ";
                    } else if (input.matches(p5)) {for (String s : getAllMondaysInAyear(2018, i + 1, "Friday")) {
                        temp.get(0).add(s);  temp.get(1).add(dayTime);
                        temp.get(2).add(dur);

                    }
                       convertListToLongs(temp); enterDay.setError(null);
                        day = "Friday";
                        dateView.setText(occures + " " + day + " ");
                        dd = occ + " " + day + " ";
                    } else if (input.matches(p6)) {for (String s : getAllMondaysInAyear(2018, i + 1, "Saturday")) {
                        temp.get(0).add(s);  temp.get(1).add(dayTime);
                        temp.get(2).add(dur);

                    }
                       convertListToLongs(temp); enterDay.setError(null);
                        day = "Saturday";
                        dateView.setText(occures + " " + day + " ");
                        dd = occ + " " + day + " ";
                    } else if (input.matches(p7)) {for (String s : getAllMondaysInAyear(2018, i + 1, "Sunday" +
                            "")) {
                        temp.get(0).add(s);  temp.get(1).add(dayTime);
                        temp.get(2).add(dur);

                    }
                       convertListToLongs(temp); enterDay.setError(null);
                        day = "Sunday";
                        dateView.setText(occures + " " + day + " ");
                        dd = occ + " " + day + " ";
                    }
                    if (enterDay.getEditText().getText().toString().contains("@")) {
                        String[] time;
                        time = enterDay.getEditText().getText().toString().split("@");
                        if (time.length > 1)
                            if (validateTimeInput(time[1].trim().replaceAll("\\s",""))) {
                               convertListToLongs(temp); enterDay.setError(null);
                                dayTime = time[1].trim().replaceAll("\\s","");
                                dateView.setText(occures + " " + day + " @ " + dayTime);
                                for (String s : temp.get(0)) {
                                    temp.get(1).add(dayTime);
                                    temp.get(0).add(s);
                                    temp.get(2).add(dur);


                                }convertListToLongs(temp);
                                if(time.length>2)
                                {
                                    if(time[2].trim().replaceAll("\\s","").matches(patternX))
                                    {
                                        for (String s : temp.get(0)) {
                                            temp.get(0).add(s);  temp.get(1).add(dayTime);
                                            temp.get(2).add(dur);
                                            //convertListToLongs(temp);

                                        }
                                       convertListToLongs(temp); enterDay.setError(null);
                                        
                                        dur=time[2].trim().replaceAll("\\s","");
                                    }
                                    else enterDay.setError(getString(R.string.acceptableDur));
                                }
                            } else
                                enterDay.setError(getString(R.string.timeFormat));


                    }
                } else {
                    enterDay.setError(getString(R.string.error_for_incorrect_day));
                    stop = false;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
               /* if(occ.equalsIgnoreCase("Once"))
                {
                    storeInfo("once");
                    //System.out.println()("workout name:  "+workoutname+"\nstartTime: "+simpleDateFormat.format(dayToLong(day,converttoLong(dayTime))+"End Time: "+ convertDurationToTimeDone(dayToLong(day,converttoLong(dayTime)))));
                }*/

            }
        });
        
        /**Listener for Date*/
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
         datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        try {
                            Date d = simpleDateFormat.parse("01/01/2018");
                            view.setMinDate(d.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        day = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        //System.out.println()(day);
                        //dateView.setText(day + " " + dayTime);
                      //  dayTimeToLong(day+" "+dayTime);
                        startTime=dayTimeToLong(day+" "+dayTime);
                        endtime=getEndTime( dayTimeToLong(day+" "+dayTime),dur);
                        startTimes.add(startTime);
                        endTimes.add(endtime);
                        dateView.setText("On "+simpleDateFormat.format(startTime)+"| To: "+simpleDateFormat.format(endtime));
                    }
                }, mYear, mMonth, mDay);


        settimeBtn.setOnClickListener(e -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {


                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            dayTime = hourOfDay + ":" + minute;
                            //System.out.println()(dayTime);
                            //dateView.setText(day + " " + dayTime);
                            //dayTimeToLong(day+" "+dayTime);
                            startTime=dayTimeToLong(day+" "+dayTime);
                            endtime=getEndTime( dayTimeToLong(day+" "+dayTime),dur);
                            dateView.setText("On "+simpleDateFormat.format(startTime)+"| To: "+simpleDateFormat.format(endtime));

                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });
        duration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String  pattern="0.10|0.20|0.30|0.40|0.50|1|1.10|1.20|1.30|1.40|1.50|2.00";
                if(duration.getText().toString().matches(pattern))
                {
                    dur=duration.getText().toString();
                    startTime=dayTimeToLong(day+" "+dayTime);
                    endtime=getEndTime( dayTimeToLong(day+" "+dayTime),dur);
                    dateView.setText("On "+simpleDateFormat.format(startTime)+"| To: "+simpleDateFormat.format(endtime));
                }else duration.setError(getString(R.string.acceptableDur));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**Listener for Status*/

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        switcher.setThumbTintList(ColorStateList.valueOf(getColor(R.color.forest_controlhighlight)));
                        switcher.setTrackTintList(ColorStateList.valueOf(getColor(R.color.forest_mid)));
                        switcher.setText(getString(R.string.Public));
                        status=switcher.getText().toString();
                    }

                }else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        switcher.setThumbTintList(ColorStateList.valueOf(getColor(R.color.lava_theme_y)));
                        switcher.setTrackTintList(ColorStateList.valueOf(getColor(R.color.save_red)));

                        switcher.setText(getString(R.string.Private));
                        status=switcher.getText().toString();
                    }
                }
                //System.out.println()("Status is "+status);
            }
        });




    }
    public void getTimes(){

    }
    public void convertListToLongs(ArrayList<ArrayList<String>> temp)
    {
        startTimes.clear();endTimes.clear();
        if(temp.size()>=1)
        for(int i=0;i<temp.get(0).size();i++)
        {
            startTimes.add(dayTimeToLong(temp.get(0).get(i)+" "+temp.get(1).get(i)));
            endTimes.add(getEndTime(dayTimeToLong(temp.get(0).get(i)+" "+temp.get(1).get(i)),temp.get(2).get(i)));
        } 
    }
    

    private long getEndTime(long l, String dur) {
        long ms=(long)(Double.parseDouble(dur)*3600000);
        //ms=ms+480000;
        //System.out.println()(ms+"+"+l+"="+simpleDateFormat.format(l+ms));
        return l+ms;
    }

    public Date dateFormat(String dte)
    {
        String myDate =dte;
        //System.out.println()("MyDate: "+myDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.getDefault());
        Date d=null;
        try {
            d = sdf.parse(dte);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println()("Date is: "+d);


        return  d;
    }
    public long dayTimeToLong(String dateT)
    {
        String part1,p2,p3,parts="";
        if(dateT.contains("/")) {
            String[] splits = dateT.split("/");
            //System.out.println()(splits[0].length());
            if (splits[0].length() != 2) {
                part1 = "0" + splits[0];
            } else part1 = splits[0];
            if (splits[1].length() != 2) {
                p2 = "0" + splits[1];
            } else p2 = splits[1];
            /**if (splits[2].length() != 4) {
                p3 = "0" + splits[2];
            } else p3 = splits[2];*/
            parts = part1 + "/" + p2 + "/" + splits[2];
        }
        else
        {
            parts=dateT;
        }
        //System.out.println()(parts);

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.setTime(dateFormat(parts));

        //System.out.println()(c.getTimeInMillis()+"<------>"+f.format(c.getTimeInMillis()));
        return c.getTimeInMillis();
    }
    public List<String> getAllMondaysInAyear(int year, int day, String x) {


        int days = 0, monthNumber = 1, mondays = 0;

        // Converting String to uppercase

        if (x.equalsIgnoreCase("monday"))
            day = 2;
        else if (x.equalsIgnoreCase("tuesday"))
            day = 3;
        else if (x.equalsIgnoreCase("Wednesday"))
            day = 4;
        else if (x.equalsIgnoreCase("thursday"))
            day = 5;
        else if (x.equalsIgnoreCase("friday"))
            day = 6;
        else if (x.equalsIgnoreCase("saturday"))
            day = 7;
        else if (x.equalsIgnoreCase("sunday"))
            day = 1;

        int dayAndMonthNumber[];
        String months[] = new String[]{
                "JAN", "FEB", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEP", "OCTOBER", "NOV", "DEC"
        };

        final String pattern = "dd/MM/yyyy";
        List<String> dates = new ArrayList<>();
        Date date = null;

        String myDate = null;

        DateFormat format = new SimpleDateFormat(pattern);
        for (int m = 0; m < months.length; m++) {


            dayAndMonthNumber = getNumberofDaysAndMonthNumberByMonthName(months[m], year + "");

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

            //System.out.println()("Number of x : " + mondays);


        }
        return dates;
    }

    private int[] getNumberofDaysAndMonthNumberByMonthName(String monthName, String year) {


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


    private boolean isLeapYear(String year) {


        boolean result = false;

        // Converting String to int.

        int myYear = Integer.parseInt(year);

        if ((myYear % 4 == 0 && myYear % 100 != 0) || myYear % 400 == 0) {

            result = true;

        }

        return result;

    }


    private void storeInfo(String increments) {
    }

    public long dayToLong(String dayditText,long timelong)
    {
        String td;
        long dateInLong=System.currentTimeMillis();


        SimpleDateFormat sdf = new SimpleDateFormat("E",Locale.getDefault());
        td=sdf.format(dateInLong);
        //System.out.println()("Today is "+td);

        String dayz[][]={{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"},{"Mon","Tue","Wed","Thur"+"Fri"+"Sat"+"Sun"}};
       /** for(int i=0;i<dayz.length;i++)
        {
            for(int j=0;j<dayz.length;j++)
            {
                if(td.equalsIgnoreCase(dayz[i][j]&&dayditText.equals(dayz[]))
                {
                    dateInLong=c.getTimeInMillis();
                }
            }
        }**/
        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }

        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }

        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }


        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }



        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }



        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis();
        }



        return dateInLong+timelong;
    }

    private int mYear, mMonth, mDay, mHour, mMinute;

    private final String pattern="MON|TUES|TUE|WED|WEDNESDAY|THURS|THUR|THURSDAY|FRIDAY|FRI|SAT|SATURDAY|SUNDAY|SUN|MONDAY|TUESDAY|M|TU|TH|W|F|ST|SN|" +
            "MON@.*?|TUES@.*?|TUE@.*?|WED@.*?|WEDNESDAY@.*?|THURS@.*?|THUR@.*?|THURSDAY@.*?|FRIDAY@.*?|FRI@.*?|SAT@.*?|SATURDAY@.*?|SUNDAY@.*?|SUN@.*?|MONDAY@.*?|TUESDAY@.*?|M@.*?|TU@.*?|TH@.*?|W@.*?|F@.*?|ST@.*?|SN@.*?|";
    private boolean stop;

    public static List<Workout> getList() {
        return WORKOUTZ;
    }

    public static void SETWORKOUTS(List workoutsChosen) {
     WORKOUTZ=workoutsChosen;
    }
}
