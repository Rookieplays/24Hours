package ie.ul.o.daysaver;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class DefaultGymplan extends AppCompatActivity {
    /**Variables*/
    private List<Workout>list,chestList,backList,bicepsList,tricepsList,calvesList,GlutesList,quadsList,shoulderList,ticepsList;
    private String TAG="DefaulGymplan";
    private int difficultLvl=1;
    private String version;
    public final String[] sets=new String[4],reps=new String[4];

    /**Firebase*/
   protected FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected DatabaseReference myRefrence;
    FirebaseFirestore mFireStore;
    private SeekBar seekbar;
    private TextView d_beginner,d_intermidiate,d_advanced,wv_muscleGroup;
    private LinearLayout defaultSettingd,workout_view;
    private Button seeV1,seeV2,seeV3,close;
    private CheckBox  low_reps,mid_reps,mid_high_reps,high_reps,low_sets,mid_sets,mid_high_sets,high_sets;


    private RadioButton v1,v2,v3;


    /**Widgets*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_gymplan);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRefrence=firebaseDatabase.getReference();

        chestList=new ArrayList<>();
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
            // String workoutNAme= String.valueOf();



    }

    public void showUpdateDialog(String muscleGroup)
    {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater  inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.default_gymplan,null);
        dialogBuilder.setView(dialogView);

        final TextView editText=(TextView)findViewById(R.id.DefaultView_heading);
        final Button saveBtn=(Button)findViewById(R.id.saveBtn);
        seekbar=(SeekBar)findViewById(R.id.seekBar);
        d_beginner=(TextView)findViewById(R.id.d_beginner);
        d_intermidiate=(TextView)findViewById(R.id.d_intermidiate);
        d_advanced=(TextView)findViewById(R.id.d_advanced);
        seeV1=(Button)findViewById(R.id.seeV1);
        seeV2=(Button)findViewById(R.id.seeV2);
        seeV3=(Button)findViewById(R.id.seeV3);
        close=(Button)findViewById(R.id.seeV3);

        low_reps=(CheckBox) findViewById(R.id.low_reps);
        mid_reps=(CheckBox) findViewById(R.id.mid_reps);
        mid_high_reps=(CheckBox) findViewById(R.id.mid2_reps);
        high_reps=(CheckBox) findViewById(R.id.high_reps);
        low_sets=(CheckBox) findViewById(R.id.low_sets);
        mid_sets=(CheckBox) findViewById(R.id.mid_sets);
        mid_high_sets=(CheckBox) findViewById(R.id.mid2_sets);
        high_sets=(CheckBox) findViewById(R.id.high_sets);

        if(low_reps.isChecked())reps[0]=low_reps.getText().toString();
        else reps[0]="";
        if(mid_reps.isChecked())reps[1]=mid_reps.getText().toString();
        else reps[1]="";
        if(mid_high_reps.isChecked())reps[2]=mid_high_reps.getText().toString();
        else reps[2]="";
        if(high_reps.isChecked())reps[3]=high_reps.getText().toString();
        else reps[3]="";

        if(low_sets.isChecked())sets[0]=low_sets.getText().toString();
        else sets[0]="";
        if(mid_sets.isChecked())sets[1]=mid_sets.getText().toString();
        else sets[1]="";
        if(mid_high_sets.isChecked())sets[2]=mid_high_sets.getText().toString();
        else sets[2]="";
        if(high_sets.isChecked())sets[3]=high_sets.getText().toString();
        else sets[3]="";

        wv_muscleGroup=(TextView)findViewById(R.id.wv_muscleGroup);

        workout_view=(LinearLayout)findViewById(R.id.WorkoutList_view);
        defaultSettingd=(LinearLayout)findViewById(R.id.Default_settings);

        seeV1.setOnClickListener(e->{showWorkoutList(muscleGroup,1);});
        seeV2.setOnClickListener(e->{showWorkoutList(muscleGroup,2);});
        seeV3.setOnClickListener(e->{showWorkoutList(muscleGroup,3);});

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(seekbar.getProgress()<=4){seekbar.setBackgroundColor(Color.GREEN);d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);}
                else if(seekbar.getProgress()<4||seekbar.getProgress()<=8){seekbar.setBackgroundColor(Color.rgb(255,165,0));d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);}
                else if(seekbar.getProgress()<8){seekbar.setBackgroundColor(Color.RED);d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);}

                difficultLvl=seekbar.getProgress();
                System.out.println("The Current Lvl is: "+seekbar.getProgress()+" or "+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        saveBtn.setEnabled(enableSave());
        dialogBuilder.setTitle(muscleGroup);
    }
    public void showWorkoutList(String musclegroup,int version)
    {

        workout_view.setVisibility(View.VISIBLE);
        defaultSettingd.setVisibility(View.GONE);
        wv_muscleGroup.setText(musclegroup);
        close.setOnClickListener(e->{
            workout_view.setVisibility(View.GONE);
            defaultSettingd.setVisibility(View.VISIBLE);
        });

    }
    public boolean enableSave()
    {
        /**Only Enable Save if  radioButtons and checkButtons are null.*/
        if(version!=null||version.equals(""))
        {
            if(low_reps.isChecked()||mid_reps.isChecked()||mid_high_reps.isChecked()||high_reps.isChecked())
            {
                if(low_sets.isChecked()||mid_sets.isChecked()||mid_high_sets.isChecked()||high_sets.isChecked())
                {
                    return true;
                }
                else {Log.d(TAG,"Sets not selected");toastMessage("No Sets Selected");return false;}
            }
            else {Log.d(TAG,"Reps not selected");toastMessage("No Reps Selected");return false;}
        }else {Log.d(TAG,"Version not selected");toastMessage("No Version Selected");return false;}
    }


    private void toastMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void showData(DataSnapshot dataSnapshot) {
        System.out.println("Getting data....");
        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            Workout workout=new Workout();
           String workoutNAme= String.valueOf(ds.child("EXERCISES").child("CHEST").getValue());
    System.out.println(workoutNAme);
            workout.setName(ds.child("EXERCISES").child("CHEST").child(workoutNAme).getValue(Workout.class).getName());
            chestList.add(workout);
        }
    }
    public  List<Workout> getWorkouts()
    {
        System.out.println("Changing value of switch.... to off");
        myRefrence.child("EXERCISES").setValue("Switch","off");
        return list;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");
                showData(dataSnapshot);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });

    }
}
