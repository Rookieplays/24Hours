package ie.ul.o.daysaver;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 03/03/2018.
 */


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DefaultView_workoutviewer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefaultView_workoutviewer#newInstance} factory method to
 * create an instance of this fragment.
 */

public class DefaultView_workoutviewer extends Fragment {
    private static String TAG="GymActivity";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RelativeLayout m_r,endo_r,ecto_r;
    private LinearLayout b_r;

    private ImageButton mesomorphButton,endoMorph,ectomorph;
    private Button m_arms,m_shoulder,m__core,m_quads,m_calves,m_chest,m_return,m_arms2,m_shoulder2,m__core2,m_quads2,m_calves2,m_chest2,m_back2;
    private Button ect_arms,ect_shoulder,ect__core,ect_quads,ect_calves,ect_chest,ect_return,ect_arms2,ect_shoulder2,ect__core2,ect_quads2,ect_calves2,ect_chest2,ect_back2;
    private Button e_arms,e_shoulder,e__core,e_quads,e_calves,e_chest,e_return,e_arms2,e_shoulder2,e__core2,e_quads2,e_calves2,e_chest2,e_back2;
    private CheckBox lossWeight,toneup;
    private String workoutType,workoutType2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String MUSCLEGROUP;

    private DefaultView.OnFragmentInteractionListener mListener;
    /**Variables*/
    private int difficultLvl=1;
    private String version;
    public final String[] sets=new String[4],reps=new String[4];
    public final String[] days=new String[7];
    private List<Workout> list,chestList,backList,bicepsList,tricepsList,calvesList,GlutesList,quadsList,shoulderList,armsList,coreList;



    /**Firebase*/
    protected FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected DatabaseReference myRefrence;
    FirebaseFirestore mFireStore;
    /**Widgets*/
    private SeekBar seekbar;
    private TextView d_beginner,d_intermidiate,d_advanced,wv_muscleGroup,wv_heading;
    private LinearLayout defaultSettingd,workout_view;
    private Button seeV1,seeV2,seeV3,close;
    private CheckBox low_reps,mid_reps,mid_high_reps,high_reps,low_sets,mid_sets,mid_high_sets,high_sets;
    private CheckBox monday,tues,wed,thurs,fri,sat,sun;
    private Spinner spinner;
    private Button saveBtn;
    private RadioGroup d_version;


    private RadioButton v1,v2,v3;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState)
    {

        View view=inflater.inflate(R.layout.default_gymplan,container,false);
        version="";


        saveBtn=(Button)view.findViewById(R.id.saveBtn);
        seekbar=(SeekBar)view.findViewById(R.id.seekBar);
        d_beginner=(TextView)view.findViewById(R.id.d_beginner);
        d_intermidiate=(TextView)view.findViewById(R.id.d_intermidiate);
        d_advanced=(TextView)view.findViewById(R.id.d_advanced);
        seeV1=(Button)view.findViewById(R.id.seeV1);
        seeV2=(Button)view.findViewById(R.id.seeV2);
        seeV3=(Button)view.findViewById(R.id.seeV3);
        close=(Button)view.findViewById(R.id.seeV3);
        wv_heading=(TextView)view.findViewById(R.id.DefaultView_heading);
        //  ImageButton camBt = (ImageButton) getFragmentManager().findFragmentById(R.id.d).getView().findViewById(R.id.b


        low_reps=(CheckBox)view.findViewById(R.id.low_reps);
            mid_reps=(CheckBox)view.findViewById(R.id.mid_reps);
        mid_high_reps=(CheckBox)view.findViewById(R.id.mid2_reps);
        high_reps=(CheckBox)view.findViewById(R.id.high_reps);
        low_sets=(CheckBox)view.findViewById(R.id.low_sets);
        mid_sets=(CheckBox)view.findViewById(R.id.mid_sets);
        mid_high_sets=(CheckBox)view.findViewById(R.id.mid2_sets);
        high_sets=(CheckBox)view.findViewById(R.id.high_sets);





        wv_muscleGroup=(TextView)view.findViewById(R.id.wv_muscleGroup);


        workout_view=(LinearLayout)view.findViewById(R.id.WorkoutList_view);
        defaultSettingd=(LinearLayout)view.findViewById(R.id.Default_settings);


       
        lossWeight.setOnClickListener(e->{
            if(lossWeight.isChecked())
            {
                workoutType+="Weight-Loss";
            }
        });
        toneup.setOnClickListener(e->{
            if (toneup.isChecked())
            {
                workoutType+="Tone_up";
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRefrence=firebaseDatabase.getReference();

        chestList=new ArrayList<>();
        backList=new ArrayList<>();
        bicepsList=new ArrayList<>();
        tricepsList=new ArrayList<>();
        calvesList=new ArrayList<>();
        GlutesList=new ArrayList<>();
        quadsList=new ArrayList<>();
        shoulderList=new ArrayList<>();
        armsList=new ArrayList<>();
        coreList=new ArrayList<>();



        System.out.println("Getting Database....");

        System.out.println("Changing value of switch.... to on");
        myRefrence.child("EXERCISES").child("Switch").setValue("on");       // String workoutNAme= String.valueOf();






        return view;
    }

    public  void  handleRadioButton(View view)
    {
        //TODO: Get Version
        Log.w("getSelectedRadioButton","initiating method @  line 70_Fullscree...");


        if(v1.isSelected())
            v1.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused));
        else
            v1.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));

        if(v2.isSelected())
            v2.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused));
        else
            v2.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));

        if(v3.isSelected())
            v3.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused));
        else
            v3.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));

        int radioID=d_version.getCheckedRadioButtonId();
        RadioButton singleButton=(RadioButton)view.findViewById(radioID);
        version=singleButton.getText().toString();
        System.out.println(radioID);
    }

    public void showUpdateDialog(String muscleGroup,Context c)
    {

        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(c);
        LayoutInflater inflater = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            inflater = getLayoutInflater();
        }

        final View dialogView=inflater.inflate(R.layout.default_gymplan,null);
        dialogBuilder.setView(dialogView);

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


        seeV1.setOnClickListener(e->{String muscleGroups=DefaultView.getSelectedMusleGroup();showWorkoutList(muscleGroups,1);});
        seeV2.setOnClickListener(e->{String muscleGroups=DefaultView.getSelectedMusleGroup();showWorkoutList(muscleGroups,2);});
        seeV3.setOnClickListener(e->{String muscleGroups=DefaultView.getSelectedMusleGroup();showWorkoutList(muscleGroups,3);});

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(seekbar.getProgress()<=4){seekbar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);}
                else if(seekbar.getProgress()<4||seekbar.getProgress()<=8){seekbar.getProgressDrawable().setColorFilter(Color.rgb(255,165,0), PorterDuff.Mode.MULTIPLY);d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);}
                else if(seekbar.getProgress()<8){seekbar.getProgressDrawable().setColorFilter(Color.rgb(255,0,0), PorterDuff.Mode.MULTIPLY);d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);}

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

        // dialogBuilder.setTitle(muscleGroup);
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
    }
    public void showWorkoutList(String musclegroup,int version)
    {

        workout_view.setVisibility(View.VISIBLE);
        defaultSettingd.setVisibility(View.GONE);
        musclegroup=DefaultView.getSelectedMusleGroup();
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
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
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
    public List<Workout> getWorkouts()
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




    public void onSetWorkout(View view,String muscleGroup,String bodyType)
    {

    }
    public void  loadChestWorkouts()
    {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("CHEST").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("CHEST").child(workoutNAme).getValue(Workout.class).getName());
                    chestList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });
    }
    public void  loadShoulderWorkouts()
    {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("SHOULDER").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("SHOULDER").child(workoutNAme).getValue(Workout.class).getName());
                    shoulderList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });
    }
    public void  loadBackWorkouts()
    {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("BACK").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("BACK").child(workoutNAme).getValue(Workout.class).getName());
                    backList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });
    }
    public void  loadARMSWorkouts()
    {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("BICEPS").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("BICEPS").child(workoutNAme).getValue(Workout.class).getName());
                    bicepsList.add(workout);
                    armsList.add(workout);
                }
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("TRICEPS").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("TRICEPS").child(workoutNAme).getValue(Workout.class).getName());
                    tricepsList.add(workout);
                    armsList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });
    }
    public void loadCOREWorkouts()
    {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("CORE").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("CORE").child(workoutNAme).getValue(Workout.class).getName());
                    coreList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });
    }
    public void loadGlutsWorkouts()
    {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("GLUTES").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("GLUTES").child(workoutNAme).getValue(Workout.class).getName());
                    GlutesList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });
    }
    public void loadQuadsWorkouts()
    {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("QUADS").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("QUADS").child(workoutNAme).getValue(Workout.class).getName());
                    quadsList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });
    }
    public void loadCalvesWorkouts()
    {
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG,"onDatachanged, Added Information to database");

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Workout workout=new Workout();
                    String workoutNAme= String.valueOf(ds.child("EXERCISES").child("CALVES").getValue());
                    System.out.println(workoutNAme);
                    workout.setName(ds.child("EXERCISES").child("CALVES").child(workoutNAme).getValue(Workout.class).getName());
                    calvesList.add(workout);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,"The read failed"+databaseError);
            }
        });
    }

    
    
    public DefaultView_workoutviewer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefaultView.
     */
    // TODO: Rename and change types and number of parameters
    public static DefaultView newInstance(String param1, String param2) {
        DefaultView fragment = new DefaultView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DefaultView.OnFragmentInteractionListener) {
            mListener = (DefaultView.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}


