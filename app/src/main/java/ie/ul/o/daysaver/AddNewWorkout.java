package ie.ul.o.daysaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddNewWorkout extends createWorkout {
    private Button b_arms, b_shoulder, b__core, b_quads, b_calves, b_chest, b_return, b_arms2, b_shoulder2, b__core2, b_quads2, b_calves2, b_chest2, b_back;
    private RelativeLayout muscleGroupPage,ex_chooser;
    private ImageButton b_back1;
    private TextView sizeOfList;
    private TextView workoutcounter;
    private Button saveWorkouts;
    private RecyclerView rView;
    private ListView lView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager llManager;
    private Button   b_arms3,  b_arms4;
    private List<Workout> list, backList, bicepsList, tricepsList, calvesList, GlutesList, quadsList, shoulderList, armsList, coreList;
    private List<Workout> chestList;
    private  String TAG="THIS is AddNewWorkout Speaking-->☺";
    protected FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected DatabaseReference myRefrence;
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    private selectWorkoutAdapter mAdapter;

    private ArrayList<ArrayList<String>> myDataset= new ArrayList<>();
    protected ArrayList<Workout> ChosenworkoutLists=new ArrayList<>();
    private ArrayList<Bitmap>imgSets=new ArrayList<>();
    protected static List<Workout>WORKOUTS;
    private WorkoutPlan madePlan;
    private static String MUSCLEGROUP ;
    private Context context=this;
    private ProgressBar pb;
    private List WorkoutsChosen;
   // private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_dialog);
        pd=new ProgressDialog(this);
        pd.setMessage(getString(R.string.wait2));
        myDataset.add(new ArrayList<>());
        myDataset.add(new ArrayList<>());
        imgSets=new ArrayList<>();
        ChosenworkoutLists=new ArrayList<>();
        b__core = findViewById(R.id.b_core1);
        b_arms = findViewById(R.id.b_arms);
        b_back= findViewById(R.id.b_back);
        b_calves = findViewById(R.id.b_calves1);
        b_chest = findViewById(R.id.b_chest);
        b_quads = findViewById(R.id.b_quads);
        b_shoulder = findViewById(R.id.b_shoulder);
        b__core2 = findViewById(R.id.b_core);
        b_arms3 = findViewById(R.id.b_arms1);
        b_arms4 = findViewById(R.id.b_arms3);
        b_arms2 = findViewById(R.id.b_arms2);
        b_calves2 = findViewById(R.id.b_calves);
        b_chest2 = findViewById(R.id.b_chest1);
        b_quads2 = findViewById(R.id.b_quads2);
        b_shoulder2 = findViewById(R.id.b_shoulder1);

        muscleGroupPage=findViewById(R.id.muscleSelectionpage);
        ex_chooser=findViewById(R.id.ex_chooser_page);
        b_back1=findViewById(R.id.b_back1);
        workoutcounter=findViewById(R.id.workoutCounter);
        sizeOfList=findViewById(R.id.sized);
        saveWorkouts=findViewById(R.id.saveWorkout);
        saveWorkouts.setOnClickListener(e->{finish();
       // createWorkout.SETWORKOUTS(WorkoutsChosen);
            createWorkout.addWorkout=true;
            //TODO:this is a temp solution
            finish();finish();startActivity(new Intent(this,createWorkout.class));
        });
        //lView=findViewById(R.id.lView);
        ex_chooser.setVisibility(View.GONE);
        muscleGroupPage.setVisibility(View.VISIBLE);
        pb=findViewById(R.id.pb3);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRefrence = firebaseDatabase.getReference();


        chestList = new ArrayList<>();
        backList = new ArrayList<>();
        bicepsList = new ArrayList<>();
        tricepsList = new ArrayList<>();
        calvesList = new ArrayList<>();
        GlutesList = new ArrayList<>();
        quadsList = new ArrayList<>();
        shoulderList = new ArrayList<>();
        armsList = new ArrayList<>();
        coreList = new ArrayList<>();



     //   showWorkoutList(,1);


        animateButtonsIn();
        b_shoulder.setOnClickListener(e -> {
          b_shoulder.setText("LOADING...");  b_shoulder.setTextColor(Color.RED);
         // startAnimation(b_shoulder).start();
         startWorkoutActivity("SHOULDER");
        });
        b_arms.setOnClickListener(e -> {
           b_arms.setText("LOADING...");
          b_arms.setTextColor(Color.RED);
          startWorkoutActivity("ARMS");
        });
        b_arms2.setOnClickListener(e -> {
            b_arms.setText("LOADING...");
          startWorkoutActivity("ARMS");
        });
        b_arms3.setOnClickListener(e -> {
            b_arms.setText("LOADING...");
          startWorkoutActivity("ARMS");
        });
        b_arms4.setOnClickListener(e -> {
            b_arms.setText("LOADING...");
          startWorkoutActivity("ARMS");
        });
        b_quads.setOnClickListener(e -> {
            //startAnimation(b_quads).start();

            b_quads2.setText("LOADING...");
          b_quads2.setTextColor(Color.RED);
            startWorkoutActivity("QUADS");
        });
        b_chest.setOnClickListener(e -> {

            b_chest.setText("LOADING...");
            //startAnimation(b_chest).start();
          startWorkoutActivity("CHEST");
          b_chest.setTextColor(Color.RED);
        });
        b_calves.setOnClickListener(e -> {
            b_calves.setText("LOADING...");
            b_calves.setTextColor(Color.RED);
          startWorkoutActivity("CALVES");
        });
        b__core.setOnClickListener(e -> {
            b__core.setText("LOADING...");
            b__core.setTextColor(Color.RED);
          startWorkoutActivity("CORE");
        });
        b_back.setOnClickListener(e -> {
            b_back1.setBackgroundColor(Color.RED);
          startWorkoutActivity("BACK");
        });
        b_back1.setOnClickListener(e -> {

            b_back1.setBackgroundColor(Color.RED);
          startWorkoutActivity("BACKg");
        });

        b_shoulder2.setOnClickListener(e -> {
            b_shoulder.setText("LOADING...");
            b_shoulder.setTextColor(Color.RED);
          startWorkoutActivity("SHOULDER");
        });
        b_arms2.setOnClickListener(e -> {
          //  waiter().show();
            b_arms.setTextColor(Color.RED);
          startWorkoutActivity("ARMS");
        });
        b_quads2.setOnClickListener(e -> {
            b_quads2.setText("LOADING...");
            b_quads2.setTextColor(Color.RED);
          startWorkoutActivity("QUADS");
        });
        b_chest2.setOnClickListener(e -> {
            b_chest.setText("LOADING...");
            b_chest.setTextColor(Color.RED);
          startWorkoutActivity("CHEST");
        });
        b_calves2.setOnClickListener(e -> {
            b_calves.setText("LOADING...");
          startWorkoutActivity("CALVES");
        });
        b__core2.setOnClickListener(e -> {
            b__core.setText("LOADING...");
            b__core.setTextColor(Color.RED);

          startWorkoutActivity("CORE");
        });
      /**Low Priority Error */ /* try {
            WORKOUTS = createWorkout.getList();
            sizeOfList.setText(WORKOUTS.size() + "");
        }catch(NullPointerException n){Log.w("☺",n.getMessage());}// ChosenworkoutLists.add(new Workout());
       */// WORKOUTS=ChosenworkoutLists;


    }
    private ObjectAnimator startAnimation(View v)
    {



        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",2f),
                PropertyValuesHolder.ofFloat("scaleX",2f));

        grow.setDuration(200);

        grow.setRepeatMode(ObjectAnimator.REVERSE);
        grow.setRepeatCount(10);

      return grow;
    }

    public AlertDialog waiter()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Please wait...");
        ////System.out.println()()("Waiter here___________________________");
        AlertDialog dialog=dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        return dialog;

    }
    private ProgressDialog pd;
    public void loadChestWorkouts() {

        pb.setVisibility(View.VISIBLE);
       //pd.show();

        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("CHEST");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ////System.out.println()()(ds);
                    Workout workout=ds.getValue(Workout.class);
                    chestList.add(workout);
                    ////System.out.println()()(workout.getName());
                }

                viewerSetup(MUSCLEGROUP);
               // waiter().dismiss();pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void loadShoulderWorkouts() {
        pb.setVisibility(View.VISIBLE);
       //pd.show();
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("SHOULDER");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ////System.out.println()()(ds);
                    Workout workout=ds.getValue(Workout.class);
                    shoulderList.add(workout);
                    //////System.out.println()()(workout.getName());
                }

                viewerSetup("Shoulder");
               // waiter().dismiss();pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadBackWorkouts() {
        pb.setVisibility(View.VISIBLE);
       //pd.show();
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("BACK");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ////System.out.println()()(ds);
                    Workout workout=ds.getValue(Workout.class);
                    backList.add(workout);
                    //////System.out.println()()(workout.getName());
                }

                viewerSetup(MUSCLEGROUP);
               // waiter().dismiss();pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadARMSWorkouts() {
        pb.setVisibility(View.VISIBLE);
       //pd.show();
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("Triceps");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ////System.out.println()()(ds);
                    Workout workout=ds.getValue(Workout.class);
                    armsList.add(workout);
                    ////System.out.println()()(workout.getName());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference db1=firebaseDatabase.getReference("EXERCISES").child("BICEPS");
        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ////System.out.println()()(ds);
                    Workout workout=ds.getValue(Workout.class);
                    armsList.add(workout);
                    ////System.out.println()()(workout.getName());
                }

                viewerSetup("Arms");
               // waiter().dismiss();pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadCOREWorkouts() {
        pb.setVisibility(View.VISIBLE);
        //pd.show();
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("CORE");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ////System.out.println()()(ds);
                    Workout workout=ds.getValue(Workout.class);
                    coreList.add(workout);
                    //////System.out.println()()(workout.getName());
                }

                viewerSetup(MUSCLEGROUP);
               // waiter().dismiss();pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadGlutsWorkouts() {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ////System.out.println()()("onDataChanged Database....");
                Log.d(TAG, "onDatachanged, Added Information to database");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Workout workout = new Workout();
                    String workoutNAme = String.valueOf(ds.child("EXERCISES").child("GLUTES").getValue());
                    ////System.out.println()()(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("GLUTES").child(workoutNAme).getValue(Workout.class).getName());
                    GlutesList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "The read failed" + databaseError);
            }
        });
    }

    public void loadQuadsWorkouts() {
      //b.setVisibility(View.VISIBLE);pd.show();
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("QUADS");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ////System.out.println()()(ds);
                    Workout workout=ds.getValue(Workout.class);
                    quadsList.add(workout);
                    //////System.out.println()()(workout.getName());
                }

                viewerSetup("Quads");
               // waiter().dismiss();pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadCalvesWorkouts() {
      //pd.show();
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("Calves");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ////System.out.println()()(ds);
                    Workout workout=ds.getValue(Workout.class);
                    calvesList.add(workout);
                    //////System.out.println()()(workout.getName());
                }

                viewerSetup(MUSCLEGROUP);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void startWorkoutActivity(String muscleGroup) {//TODO: create workout Activity
        ////System.out.println()()("SWA++++++++++++++++++++++++++");
        ////System.out.println()()(muscleGroup);
        /*Intent intent=new Intent(this,DefaultGymplan.class);
        startActivity(intent);*/
        //pd.show();
       // pb.setVisibility(View.VISIBLE);
        MUSCLEGROUP = muscleGroup;



        //startActivity(new Intent("ie.ul.o.daysaver.DefaultGymplan"));
        showCreationCenter(muscleGroup);



    }
    protected static List<Workout> listOfWorkouts(Activity activity)
    {

        activity.finish();

        return WORKOUTS;
    }

    Activity activityX;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        addWorkout=false;
        startActivity(new Intent(this,createWorkout.class));
    }

    private void showCreationCenter(String muscleGroup) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_exercise_dialog, null);
        dialogBuilder.setView(dialogView);
        RelativeLayout e_chooser=dialogView.findViewById(R.id.ex_chooser_page);
        RelativeLayout musclegp=dialogView.findViewById(R.id.muscleSelectionpage);
        rView=dialogView.findViewById(R.id.rView);
        showWorkoutList(muscleGroup,1);
        e_chooser.setVisibility(View.VISIBLE);
        musclegp.setVisibility(View.GONE);
        gridLayoutManager = new GridLayoutManager(this,2);
        rView.setLayoutManager(gridLayoutManager);

        Button save=dialogView.findViewById(R.id.workoutsChoosen);

        AlertDialog dialog=dialogBuilder.create();

        save.setOnClickListener(e->{
            addWorkout=false;
            ////System.out.println()()(WORKOUTS+" Before SelectWorkourAdapter");
            WORKOUTS=selectWorkoutAdapter.getWorkoutMade();
            ////System.out.println()()(WORKOUTS+" After SelectWorkourAdapter");
           // WORKOUTS=addTExistingList();
          ////System.out.println()()("There are -->"+WORKOUTS.size()+" so Far...");
          sizeOfList.setText(WORKOUTS.size()+"");
            dialog.dismiss();
        });
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


    }
    public static List<Workout> getOldWorkouts() {
       // ////System.out.println()()("+++++"+workoutFull);
        return WORKOUTS;
    }
    public static void setWORKOUTS(List<Workout>W){
        ////System.out.println()()("///////////"+W);
        WORKOUTS=W;
    }
    public  List addTExistingList()
    {
        List<Workout>temp=new ArrayList<>();
        for (Workout w:WORKOUTS)
        {
            if(!ChosenworkoutLists.contains(w))
            ChosenworkoutLists.add(w);
        }
        temp=ChosenworkoutLists;
        return temp;

    }
    public List<Workout> createVersions(int ListID)
    {



        List<Workout> temp=new ArrayList<>();
        List<Workout>dump=new ArrayList<>();
        Collections.shuffle(chestList);
        Collections.shuffle(backList);
        Collections.shuffle(armsList);
        Collections.shuffle(quadsList);
        Collections.shuffle(calvesList);
        Collections.shuffle(coreList);
        //////System.out.println()()("***"+chestList);

        if(ListID==1)//chest
            for (Workout w: chestList)
                // ////System.out.println()()(w.getName()+" ");
                temp.add(w);
            // ////System.out.println()()("****"+temp);

        else if(ListID==2)//back
            for (Workout w: backList)
                temp.add(w);
        else if(ListID==3)//arms
            for (Workout w: armsList)
                temp.add(w);
        else if(ListID==4)//quads
            for (Workout w: quadsList)temp.add(w);
        else if(ListID==5)//calves
            for (Workout w: calvesList)temp.add(w);
        else if(ListID==6)//core
            for (Workout w: coreList)   temp.add(w);
        else if(ListID==7)//core
            for (Workout w: shoulderList)   temp.add(w);
        ////System.out.println()()("~~~"+temp);
        return temp;


    }
    public void viewerSetup(String musclegroup) {


        ////System.out.println()()("Viewr_setup: MG:"+musclegroup);
        myDataset.get(0).clear();
        myDataset.get(1).clear();
        imgSets.clear();
        //  updateListview();
        WorkoutsChosen=new ArrayList();
        workoutcounter.setText(getString(R.string.chooseExercise));

        List<Workout> temp=null;


        ////System.out.println()()("**"+musclegroup);
        if(musclegroup.equalsIgnoreCase("Chest")) {
            temp=createVersions(1);
            ////System.out.println()()("**"+temp);
            if(temp.isEmpty()==false){
                for (Workout w: temp) {
                    myDataset.get(0).add(w.getName());
                    myDataset.get(1).add(w.getInfo());
                    imgSets.add(covertLinkToImg(w.getImg()));

                }
            }
            else
            {myDataset.get(0).add("Empty List");
                myDataset.get(1).add("An Error Occured");
                imgSets.add(covertLinkToImg("https://firebasestorage.googleapis.com/v0/b/hours-1a681.appspot.com/o/profile_pic%2F1521967324478.jpg?alt=media&token=4f5bdc8a-2b47-4ed6-849c-9852c69f5883"));
            }
        }
        else if(musclegroup.equalsIgnoreCase("Back")) {
            temp=createVersions(2);

            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }

        }
        else if(musclegroup.equalsIgnoreCase("Arms")) {
            temp=createVersions(3);

            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        else if(musclegroup.equalsIgnoreCase("Quads")) {
            temp=createVersions(4);

            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        else if(musclegroup.equalsIgnoreCase("Calves")) {
            temp=createVersions(5);

            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        else if(musclegroup.equalsIgnoreCase("Core")) {
            temp=createVersions(6);

            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        else if(musclegroup.equalsIgnoreCase("Shoulder")) {
            temp=createVersions(7);

            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }



        rView.setAdapter(new selectWorkoutAdapter(this,musclegroup,myDataset,imgSets));
        b_shoulder.setText(getString(R.string.shoulder));
        b_arms.setText(getString(R.string.arms));
        b_quads.setText(getString(R.string.quads));
        b_chest.setText(getString(R.string.chest));
        b_calves.setText(getString(R.string.calf));
        b_back.setText(getString(R.string.back));

        ////System.out.println()()("There is "+WorkoutsChosen.size()+" Workouts");

    }
    public void showWorkoutList(String mg, int version) {

        chestList.clear();
        backList.clear();
        shoulderList.clear();
        armsList.clear();
        quadsList.clear();
        calvesList.clear();
        coreList.clear();
        ////System.out.println()()("Showing List: " + mg + "\nVersion: " + version);
        if(mg.equalsIgnoreCase("chest"))
            loadChestWorkouts();
        else  if(mg.equalsIgnoreCase("arms"))
            loadARMSWorkouts();
        else  if(mg.equalsIgnoreCase("back1"))
            loadBackWorkouts();
        else  if(mg.equalsIgnoreCase("calves"))
            loadCalvesWorkouts();
        else  if(mg.equalsIgnoreCase("shoulder"))
            loadShoulderWorkouts();
        else  if(mg.equalsIgnoreCase("quads"))
            loadQuadsWorkouts();
        else  if(mg.equalsIgnoreCase("core"))
            loadCOREWorkouts();
        ////System.out.println()()("VERSion "+version);





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
    public void animateButtonsIn()
    {



        ObjectAnimator animatorX=ObjectAnimator.ofFloat(b_shoulder,"x",80);
        animatorX.setDuration(500);
        ObjectAnimator animator1=ObjectAnimator.ofFloat(b__core2,"x",80);
        animatorX.setDuration(500);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(b_quads2,"x",80);
        animatorX.setDuration(500);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animatorX,animator1,animator2);
        animatorSet.start();
    }
    public void animateButtonsOut(String animType,Button button,float posX,float posY,long millis)
    {



        ObjectAnimator animatorX=ObjectAnimator.ofFloat(button,"x",posX);
        animatorX.setDuration(millis);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();
    }

    public void showconfirmationBox2()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteDialog).setTitle(R.string.beforeThat);

        builder.setPositiveButton(R.string.resetAll, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                //eraseOldData();

            }
        });
        builder.setNegativeButton(R.string.nope, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                adialog.dismiss();

            }
        });
        AlertDialog adialog=builder.create();
        adialog.show();

    }

}
