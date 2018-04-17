
package ie.ul.o.daysaver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


//import android.support.v7.app.AlertController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DefaultView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefaultView#newInstance} factory method to
 * create an instance of this fragment.
 */

public class DefaultView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String TAG = "GymActivity";
    private final Context context=getContext();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RelativeLayout m_r, endo_r, ecto_r;
    private LinearLayout b_r;
    private ImageButton mesomorphButton, endoMorph, ectomorph;
    private Button m_arms, m_shoulder, m__core, m_quads, m_calves, m_chest, m_return, m_arms2, m_shoulder2, m__core2, m_quads2, m_calves2, m_chest2, m_back2;
    private Button ect_arms, ect_shoulder, ect__core, ect_quads, ect_calves, ect_chest, ect_return, ect_arms2, ect_shoulder2, ect__core2, ect_quads2, ect_calves2, ect_chest2, ect_back2;
    private Button e_arms, e_shoulder, e__core, e_quads, e_calves, e_chest, e_return, e_arms2, e_shoulder2, e__core2, e_quads2, e_calves2, e_chest2, e_back2;
    private CheckBox lossWeight, toneup;
    private String workoutType, workoutType2;
    private int v;
    private List<Workout>version1List,version2List,version3List;
    private RelativeLayout newDefault;
    private boolean isM,isT,isW,isTh,isF,isSat,isSun;
    private TextView progressText;

    private int selectedCount,selectedcount2,sc3;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String MUSCLEGROUP;
    private String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
    private OnFragmentInteractionListener mListener;
    /**
     * Variables
     */
    private int difficultLvl = 1;
    private static String COL_NAME = "Exercises";
    private static String CHEST_DOC_NAME = "Chest";
    private static String DOC_COL_NAME = "num";
    private static String NUM_DOC = "Workouts";
    private static String BACK_DOC_NAME = "Back";
    RecyclerView mainList;
    private List<ArrayList<String>> presets = new ArrayList<ArrayList<String>>();
    private List<Workout> list, backList, bicepsList, tricepsList, calvesList, GlutesList, quadsList, shoulderList, armsList, coreList;
    private List<Workout> chestList;
    private List<Workout> v1List,v2List,v3List;
    private String level="Intermediate";
    private List<String> daysSelected, setsSelected, repsSelected, versionSelected;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private   Workout_Default workout_Default;
    /**
     * Firebase
     */
    protected FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected DatabaseReference myRefrence;
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    /**
     * Widgets
     */
    private LinearLayout mainDefault;
    private SeekBar seekbar;
    private TextView d_beginner, d_intermidiate, d_advanced, wv_muscleGroup, wv_heading, setst_text, reps_text, days_text;
    private LinearLayout defaultSettingd, workout_view,dayView;
    private Button seeV1, seeV2, seeV3, close;
    private CheckBox low_reps, mid_reps, mid_high_reps, high_reps, low_sets, mid_sets, mid_high_sets, high_sets;
    private CheckBox Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
    private CheckBox version1, version2, version3;
    private Spinner spinner;
    private Button saveBtn;
    private RadioGroup d_version;

    private ProgressBar pb;
    private RadioButton v1, v2, v3;
    private boolean alreadyCreated;
    private Button reset,reset1,reset2,fin1,fin2,fin3;
    private Button OverrideBtn;
    private ProgressBar progBar;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_default_view, container, false);
        reset=view.findViewById(R.id.resetAll);
        reset1=view.findViewById(R.id.resetAll2);
        reset2=view.findViewById(R.id.resetAll3);
        OverrideBtn=view.findViewById(R.id.override);
        fin1=view.findViewById(R.id.finishedBtn);
        fin2=view.findViewById(R.id.finishedBtn2);
        fin3=view.findViewById(R.id.finished3);
        progBar=view.findViewById(R.id.gProgBar);

        newDefault=view.findViewById(R.id.NewDefault);
        mainDefault=view.findViewById(R.id.mainDefault);

        mFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        daysSelected = new ArrayList<>();
        repsSelected = new ArrayList<>();
        setsSelected = new ArrayList<>();
        versionSelected = new ArrayList<>();
        m_r = (RelativeLayout) view.findViewById(R.id.mesomorph_view);
        endo_r = (RelativeLayout) view.findViewById(R.id.endomorph_view);
        ecto_r = (RelativeLayout) view.findViewById(R.id.ectomorph_view);
        b_r = (LinearLayout) view.findViewById(R.id.selectABodyType);
        user = firebaseAuth.getCurrentUser();
        b_r.setVisibility(View.VISIBLE);
        endo_r.setVisibility(View.GONE);
        ecto_r.setVisibility(View.GONE);
        m_r.setVisibility(View.GONE);
    progressText=view.findViewById(R.id.prText);
        mesomorphButton = (ImageButton) view.findViewById(R.id.mesomorphImgBtn);
        endoMorph = (ImageButton) view.findViewById(R.id.endomorphImgBtn);
        ectomorph = (ImageButton) view.findViewById(R.id.ectomorphImgBtn);

        m__core = (Button) view.findViewById(R.id.m_core);
        m_arms = (Button) view.findViewById(R.id.m_arms);
        //m_back=(Button)view.findViewById(R.id.m_back);
        m_calves = (Button) view.findViewById(R.id.m_calves);
        m_chest = (Button) view.findViewById(R.id.m_chest);
        m_quads = (Button) view.findViewById(R.id.m_quads);
        m_shoulder = (Button) view.findViewById(R.id.m_shoulder);
        m__core2 = (Button) view.findViewById(R.id.m_core2);
        m_arms2 = (Button) view.findViewById(R.id.m_arms2);
        m_return = (Button) view.findViewById(R.id.m_back);
        m_calves2 = (Button) view.findViewById(R.id.m_calf2);
        m_chest2 = (Button) view.findViewById(R.id.m_chest2);
        m_quads2 = (Button) view.findViewById(R.id.m_quads2);
        m_shoulder2 = (Button) view.findViewById(R.id.m_shoulder2);

        ect__core = (Button) view.findViewById(R.id.e_core);
        ect_arms = (Button) view.findViewById(R.id.e_arm);
        ect_return = (Button) view.findViewById(R.id.e_return);
        ect_calves = (Button) view.findViewById(R.id.e_calves);
        ect_chest = (Button) view.findViewById(R.id.e_chest);
        ect_quads = (Button) view.findViewById(R.id.e_Quads);
        ect_shoulder = (Button) view.findViewById(R.id.e_shoulder);
        ect__core2 = (Button) view.findViewById(R.id.e_core1);
        ect_arms2 = (Button) view.findViewById(R.id.e_arms1);
        //m_back=(Button)view.findViewById(R.id.e_back);
        ect_calves2 = (Button) view.findViewById(R.id.e_calf1);
        ect_chest2 = (Button) view.findViewById(R.id.e_chest1);
        ect_quads2 = (Button) view.findViewById(R.id.e_quads1);
        ect_shoulder2 = (Button) view.findViewById(R.id.e_shoulder1);

        e__core = (Button) view.findViewById(R.id.b_core);
        e_arms = (Button) view.findViewById(R.id.arm);
        e_return = (Button) view.findViewById(R.id.backBtn);
        e_calves = (Button) view.findViewById(R.id.calves);
        e_chest = (Button) view.findViewById(R.id.e_chest);
        //e_quads=(Button)view.findViewById(R.id.Quads);
        e_shoulder = (Button) view.findViewById(R.id.b_shoulder);

        e__core2 = (Button) view.findViewById(R.id.core2);
        e_arms2 = (Button) view.findViewById(R.id.arms2);
        e_return = (Button) view.findViewById(R.id.backBtn);
        e_calves2 = (Button) view.findViewById(R.id.calf2);
        e_chest2 = (Button) view.findViewById(R.id.chest2);
        //e_quads=(Button)view.findViewById(R.id.Quads);
        e_shoulder2 = (Button) view.findViewById(R.id.b_shoulder);

        mesomorphButton.setOnClickListener(e -> goToC());
        endoMorph.setOnClickListener(e -> goToA());
        ectomorph.setOnClickListener(e -> goToB());
        lossWeight = (CheckBox) view.findViewById(R.id.weight_Loss);
        toneup = (CheckBox) view.findViewById(R.id.tone_up);

        /**Check if user already created default workout*/
        checkIfDefaultIsMade();
        /**setting event listeners*/

        m_shoulder.setOnClickListener(e -> {
            startWorkoutActivity("SHOULDER");
        });
        m_arms.setOnClickListener(e -> {
            startWorkoutActivity("ARMS");
        });
        m_quads.setOnClickListener(e -> {
            startWorkoutActivity("QUADS");
        });
        m_chest.setOnClickListener(e -> {
            startWorkoutActivity("CHEST");
        });
        m_calves.setOnClickListener(e -> {
            startWorkoutActivity("CALVES");
        });
        m__core.setOnClickListener(e -> {
            startWorkoutActivity("CORE");
        });
        m_return.setOnClickListener(e -> {
            goBack();
        });
        m_shoulder2.setOnClickListener(e -> {
            startWorkoutActivity("SHOULDER");
        });
        m_arms2.setOnClickListener(e -> {
            startWorkoutActivity("ARMS");
        });
        m_quads2.setOnClickListener(e -> {
            startWorkoutActivity("QUADS");
        });
        m_chest2.setOnClickListener(e -> {
            startWorkoutActivity("CHEST");
        });
        m_calves2.setOnClickListener(e -> {
            startWorkoutActivity("CALVES");
        });
        m__core2.setOnClickListener(e -> {
            startWorkoutActivity("CORE");
        });

        e_shoulder.setOnClickListener(e -> {
            startWorkoutActivity("SHOULDER");
        });
        e_arms.setOnClickListener(e -> {
            startWorkoutActivity("ARMS");
        });
        // e_quads.setOnClickListener(e->{startWorkoutActivity("");});
        e_chest.setOnClickListener(e -> {
            startWorkoutActivity("CHEST");
        });
        e_calves.setOnClickListener(e -> {
            startWorkoutActivity("CALVES");
        });
        e__core.setOnClickListener(e -> {
            startWorkoutActivity("CORE");
        });
        e_return.setOnClickListener(e -> {
            goBack();
        });
        e_shoulder2.setOnClickListener(e -> {
            startWorkoutActivity("SHOULDER");
        });
        e_arms2.setOnClickListener(e -> {
            startWorkoutActivity("ARMS");
        });
        //e_quads2.setOnClickListener(e->{startWorkoutActivity("");});
        e_chest2.setOnClickListener(e -> {
            startWorkoutActivity("CHEST");
        });
        e_calves2.setOnClickListener(e -> {
            startWorkoutActivity("CALVES");
        });
        e__core2.setOnClickListener(e -> {
            startWorkoutActivity("CORE");
        });

        ect_shoulder.setOnClickListener(e -> {
            startWorkoutActivity("SHOULDER");
        });
        ect_arms.setOnClickListener(e -> {
            startWorkoutActivity("ARMS");
        });
        ect_quads.setOnClickListener(e -> {
            startWorkoutActivity("QUADS");
        });
        ect_chest.setOnClickListener(e -> {
            startWorkoutActivity("CHEST");
        });
        ect_calves.setOnClickListener(e -> {
            startWorkoutActivity("CALVES");
        });
        ect__core.setOnClickListener(e -> {
            startWorkoutActivity("CORE");
        });
        ect_return.setOnClickListener(e -> {
            goBack();
        });
        ect_shoulder2.setOnClickListener(e -> {
            startWorkoutActivity("SHOULDER");
        });
        ect_arms2.setOnClickListener(e -> {
            startWorkoutActivity("ARMS");
        });
        ect_quads2.setOnClickListener(e -> {
            startWorkoutActivity("QUADS");
        });
        ect_chest2.setOnClickListener(e -> {
            startWorkoutActivity("CHEST");
        });
        ect_calves2.setOnClickListener(e -> {
            startWorkoutActivity("CALVE");
        });
        ect__core2.setOnClickListener(e -> {
            startWorkoutActivity("CORE");
        });

        lossWeight.setOnClickListener(e -> {
            if (lossWeight.isChecked()) {
                workoutType += "Weight-Loss";
            }
        });
        toneup.setOnClickListener(e -> {
            if (toneup.isChecked()) {
                workoutType += "Tone_up";
            }
        });



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

        OverrideBtn.setOnClickListener(e->{
            showconfirmationBox2();
        });

        System.out.println("Getting Database....");

        System.out.println("Changing value of switch.... to on");
        myRefrence.child("EXERCISES").child("Switch").setValue("on");       // String workoutNAme= String.valueOf();


        fin1.setOnClickListener(e->{
            showconfirmationBox(0);
        });
        fin2.setOnClickListener(e->{
            showconfirmationBox(0);
        });
        fin3.setOnClickListener(e->{
            showconfirmationBox(0);
        });

        reset.setOnClickListener(e->{
            showconfirmationBox(1);
        });
        reset1.setOnClickListener(e->showconfirmationBox(1));
        reset2.setOnClickListener(e->showconfirmationBox(1));

        return view;
    }
    public void checkIfDefaultIsMade()
    {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        UID=user.getUid();
        System.out.println("Users\n"+UID+"\nGYM");
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users/"+UID+"/GYM");
        reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            System.out.println("Datasnpshot: "+dataSnapshot);

          Gym gym=dataSnapshot.getValue(Gym.class);

            System.out.println("Gym: "+gym);
            assert gym != null;
            System.out.println(gym.isDefaultMade());
            alreadyCreated=gym.isDefaultMade();
            System.out.println("alreadyCreated: "+alreadyCreated);
            System.out.println("***********"+alreadyCreated+"******************");
            if(!alreadyCreated)
            {
                newDefault.setVisibility(View.VISIBLE);
                mainDefault.setVisibility(View.GONE);
            }
            else{
                mainDefault.setVisibility(View.VISIBLE);
                newDefault.setVisibility(View.GONE);
            }

        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }

    });




    }

    ArrayList<ArrayList<String>>myDataset;
    ArrayList<Bitmap>imgSets;
    int daysleft=1;
    ArrayList<String> wID=new ArrayList<>();
    public void eraseOldData()
    {
        progBar.setVisibility(View.VISIBLE);
        progressText.setText(progBar.getProgress()+"%");
        mFirestore.collection(UID).whereEqualTo("id","GYM").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<CE> eventList = new ArrayList<>();

                    for(DocumentSnapshot doc : task.getResult())
                    {
                        wID.add(doc.getId());
                    }
                           Log.d("onDataDeletion","Succesfully grabbed data.."+wID);
                    if(wID.isEmpty())
                    {
                        progBar.setVisibility(View.GONE);
                    }
                    else progBar.setVisibility(View.VISIBLE);
                    for (String dociD:wID) {
                        System.out.println(dociD);
                        mFirestore.collection(UID).document(dociD).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG,"Data Deleted");
                                toastMessage("All Data has now reset",Toast.LENGTH_SHORT);
                                progBar.setVisibility(View.GONE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,e.getMessage());
                                progBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }else
                {
                    Log.d("onDataDeletion",task.getException()+"");
                }
            }
        });

    }
    public void showconfirmationBox2()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.deleteDialog).setTitle(R.string.beforeThat);

        builder.setPositiveButton(R.string.resetAll, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                eraseOldData();
                newDefault.setVisibility(View.VISIBLE);
                mainDefault.setVisibility(View.GONE);
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users/"+UID+"/GYM/defaultMade");
                reference.setValue(false);

                adialog.dismiss();
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

    public void showconfirmationBox(int confirmCode)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.areyouSure2).setTitle(R.string.beforeThat);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                if(confirmCode==0)
                getActivity().finish();
                else {eraseOldData();resetSelection();}

                adialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                adialog.dismiss();

            }
        });
        AlertDialog adialog=builder.create();
        adialog.show();

    }

    private void resetSelection() {
        daysSelected.clear();
    }

    public void showconfirmationBox(AlertDialog dialog)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.areyouSure).setTitle(R.string.beforeThat);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface adialog, int i) {
                    saveListener();
                    dialog.dismiss();
                    adialog.dismiss();
                    mainDefault.setVisibility(View.GONE);
                    newDefault.setVisibility(View.VISIBLE);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface adialog, int i) {
                    adialog.dismiss();

                }
            });
             AlertDialog adialog=builder.create();
            adialog.show();

        }
    public void showUpdateDialog(String muscleGroup) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.default_gymplan, null);
        dialogBuilder.setView(dialogView);

        myDataset=new ArrayList<ArrayList<String>>();
        myDataset.add(new ArrayList<String>());
        myDataset.add(new ArrayList<String>());
        imgSets=new ArrayList<Bitmap>();

        pb=(ProgressBar)dialogView.findViewById(R.id.loading_lists);
        pb.setVisibility(View.GONE);
                saveBtn = (Button) dialogView.findViewById(R.id.saveBtn);
        seekbar = (SeekBar) dialogView.findViewById(R.id.seekBar);
        d_beginner = (TextView) dialogView.findViewById(R.id.d_beginner);
        d_intermidiate = (TextView) dialogView.findViewById(R.id.d_intermidiate);
        d_advanced = (TextView) dialogView.findViewById(R.id.d_advanced);
        seeV1 = (Button) dialogView.findViewById(R.id.seeV1);
        seeV2 = (Button) dialogView.findViewById(R.id.seeV2);
        seeV3 = (Button) dialogView.findViewById(R.id.seeV3);
        close = (Button) dialogView.findViewById(R.id.close);
        wv_heading = (TextView) dialogView.findViewById(R.id.DefaultView_heading);
        setst_text = (TextView) dialogView.findViewById(R.id.sets_text);
        reps_text = (TextView) dialogView.findViewById(R.id.d_reps);
        days_text = (TextView) dialogView.findViewById(R.id.days_text);

        //  ImageButton camBt = (ImageButton) getFragmentManager().findFragmentById(R.id.d).getView().findViewById(R.id.b
        mRecyclerView = (RecyclerView)dialogView.findViewById(R.id.maingym_list);


        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        dayView=(LinearLayout)dialogView.findViewById(R.id.dayView);

        low_reps = (CheckBox) dialogView.findViewById(R.id.low_reps);
        mid_reps = (CheckBox) dialogView.findViewById(R.id.mid_reps);
        mid_high_reps = (CheckBox) dialogView.findViewById(R.id.mid2_reps);
        high_reps = (CheckBox) dialogView.findViewById(R.id.high_reps);
        low_sets = (CheckBox) dialogView.findViewById(R.id.low_sets);
        mid_sets = (CheckBox) dialogView.findViewById(R.id.mid_sets);
        mid_high_sets = (CheckBox) dialogView.findViewById(R.id.mid2_sets);
        high_sets = (CheckBox) dialogView.findViewById(R.id.high_sets);


        wv_muscleGroup = (TextView) dialogView.findViewById(R.id.wv_muscleGroup);
        wv_heading.setText(MUSCLEGROUP);

        workout_view = (LinearLayout) dialogView.findViewById(R.id.WorkoutList_view);
        defaultSettingd = (LinearLayout) dialogView.findViewById(R.id.Default_settings);
        //mainList = (RecyclerView) dialogView.findViewById(R.id.mainList);


//        loadCheckBoxLists();


        // loadCheckBoxLists();
        version1 = (CheckBox) dialogView.findViewById(R.id.option1_btn);
        version2 = (CheckBox) dialogView.findViewById(R.id.option2_btn);
        version3 = (CheckBox) dialogView.findViewById(R.id.option3_btn);

        Monday = (CheckBox) dialogView.findViewById(R.id.d_mon);
        Tuesday = (CheckBox) dialogView.findViewById(R.id.d_tues);
        Wednesday = (CheckBox) dialogView.findViewById(R.id.d_wed);
        Thursday = (CheckBox) dialogView.findViewById(R.id.d_thurs);
        Friday = (CheckBox) dialogView.findViewById(R.id.d_fri);
        Saturday = (CheckBox) dialogView.findViewById(R.id.d_sat);
        Sunday = (CheckBox) dialogView.findViewById(R.id.d_sun);

        setDefaultchecks();
        seeV1.setOnClickListener(e -> {
            String muscleGroups = DefaultView.getSelectedMusleGroup();
            showWorkoutList(muscleGroups, 1);
        });
        seeV2.setOnClickListener(e -> {
            String muscleGroups = DefaultView.getSelectedMusleGroup();
            showWorkoutList(muscleGroups, 2);
        });
        seeV3.setOnClickListener(e -> {
            String muscleGroups = DefaultView.getSelectedMusleGroup();
            showWorkoutList(muscleGroups, 3);
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                selectedCount=0;
                if (seekbar.getProgress() <= 3) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        seekBar.setThumbTintList(ColorStateList.valueOf(Color.GREEN));
                    }
                    seekbar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                } else if (seekbar.getProgress() < 4 || seekbar.getProgress() <= 7) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        seekBar.setThumbTintList(ColorStateList.valueOf(Color.rgb(255, 165, 0)));
                    }
                    seekbar.getProgressDrawable().setColorFilter(Color.rgb(255, 165, 0), PorterDuff.Mode.MULTIPLY);
                    d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                } else if (seekbar.getProgress() >= 8) {
                    seekBar.setThumbTintList(ColorStateList.valueOf(Color.RED));
                    seekbar.getProgressDrawable().setColorFilter(Color.rgb(255, 0, 0), PorterDuff.Mode.MULTIPLY);
                    d_beginner.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    d_intermidiate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    d_advanced.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                }

                difficultLvl = seekbar.getProgress();
                System.out.println("The Current Lvl is: " + seekbar.getProgress() + " or " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        d_beginner.setOnClickListener(e -> seekbar.setProgress(3));
        d_intermidiate.setOnClickListener(e -> seekbar.setProgress(7));
        d_advanced.setOnClickListener(e -> seekbar.setProgress(10));
        /**Enable save if for chech events*/
        Monday.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
           if(Monday.isChecked()){
               if(isM){
                   toastMessage(getString(R.string.dayAlreadyChoosen),Toast.LENGTH_SHORT);Monday.setChecked(false);
               }
               toastMessage("Carefull! That's "+(daysleft++)+"/7 days left",Toast.LENGTH_SHORT);
           }
           else {
               toastMessage("You have "+(daysleft--)+"/7 days Left",Toast.LENGTH_SHORT);
           }
        });

        Tuesday.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            if(Tuesday.isChecked()){
                if(isT){
                    toastMessage(getString(R.string.dayAlreadyChoosen),Toast.LENGTH_SHORT);Tuesday.setChecked(false);
                }
                toastMessage("Carefull! That's "+(daysleft++)+"/7 days left",Toast.LENGTH_SHORT);
            }
            else {
                toastMessage("You have "+(daysleft--)+"/7 days Left",Toast.LENGTH_SHORT);
            }
            saveBtn.setEnabled(enableSave());
        });
        Wednesday.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            if(Wednesday.isChecked()){
                if(isW){
                    toastMessage(getString(R.string.dayAlreadyChoosen),Toast.LENGTH_SHORT);Wednesday.setChecked(false);
                }
                toastMessage("Carefull! That's "+(daysleft++)+"/7 days left",Toast.LENGTH_SHORT);
            }
            else {
                toastMessage("You have "+(daysleft--)+"/7 days Left",Toast.LENGTH_SHORT);
            }
            saveBtn.setEnabled(enableSave());
        });
        Thursday.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            if(Thursday.isChecked()){
                if(isTh){
                    toastMessage(getString(R.string.dayAlreadyChoosen),Toast.LENGTH_SHORT);Thursday.setChecked(false);
                }
                toastMessage("Carefull! That's "+(daysleft++)+"/7 days left",Toast.LENGTH_SHORT);
            }
            else {
                toastMessage("You have "+(daysleft--)+"/7 days Left",Toast.LENGTH_SHORT);
            }
            saveBtn.setEnabled(enableSave());
        });

        Friday.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            if(Friday.isChecked()){
                if(isF){
                    toastMessage(getString(R.string.dayAlreadyChoosen),Toast.LENGTH_SHORT);Friday.setChecked(false);
                }
                toastMessage("Carefull! That's "+(daysleft++)+"/7 days left",Toast.LENGTH_SHORT);
            }
            else {
                toastMessage("You have "+(daysleft--)+"/7 days Left",Toast.LENGTH_SHORT);
            }
            saveBtn.setEnabled(enableSave());
        });
        Saturday.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            if(Saturday.isChecked()){
                if(isSat){
                    toastMessage(getString(R.string.dayAlreadyChoosen),Toast.LENGTH_SHORT);Saturday.setChecked(false);
                }
                toastMessage("Carefull! That's "+(daysleft++)+"/7 days left",Toast.LENGTH_SHORT);
            }
            else {
                toastMessage("You have "+(daysleft--)+"/7 days Left",Toast.LENGTH_SHORT);
            }
            saveBtn.setEnabled(enableSave());
        });
        Sunday.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {

                if(Sunday.isChecked()){
                    if(isSun){
                        toastMessage(getString(R.string.dayAlreadyChoosen),Toast.LENGTH_SHORT);Sunday.setChecked(false);
                    }
                    toastMessage("Carefull! That's "+(daysleft++)+"/7 days left",Toast.LENGTH_SHORT);
                }
                else {
                    toastMessage("You have "+(daysleft--)+"/7 days Left",Toast.LENGTH_SHORT);
                }
            saveBtn.setEnabled(enableSave());
        });
        version1.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        version2.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        version3.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        low_reps.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        mid_high_reps.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        mid_reps.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        high_reps.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        low_sets.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        mid_sets.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        mid_high_sets.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        high_sets.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            saveBtn.setEnabled(enableSave());
        });
        /**end*/

        /**Reset Defaut*/
        wv_heading.setOnLongClickListener(view -> {
            seekbar.setProgress(4);
            setDefaultchecks();
            return true;
        });
        wv_heading.setOnClickListener(e -> {
            seekbar.setProgress(4);
            Monday.setChecked(true);
            Tuesday.setChecked(true);
            Wednesday.setChecked(true);
            Thursday.setChecked(true);
            Friday.setChecked(true);
            Saturday.setChecked(true);
            Sunday.setChecked(true);
            version1.setChecked(true);
            version2.setChecked(true);
            version3.setChecked(true);
            low_sets.setChecked(true);
            mid_sets.setChecked(true);
            mid_high_sets.setChecked(true);
            high_sets.setChecked(true);
            low_reps.setChecked(true);
            mid_reps.setChecked(true);
            mid_high_reps.setChecked(true);
            high_reps.setChecked(true);
        });
        updateSwitch("ON");
        // dialogBuilder.setTitle(muscleGroup);
        toastMessage("TAP " + MUSCLEGROUP + " to Select All\nHOLD " + MUSCLEGROUP + " to reset to Default", Toast.LENGTH_LONG);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        saveBtn.setOnClickListener(e -> {
            updateGymDatabase();
            fin1.setVisibility(View.VISIBLE);
            fin2.setVisibility(View.VISIBLE);
            fin3.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
            reset1.setVisibility(View.VISIBLE);
            reset2.setVisibility(View.VISIBLE);

            getDialogInfo();
            disableUsedDays();
           showconfirmationBox(alertDialog);
        });
    }
    public void updateGymDatabase()
    {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        UID=user.getUid();
        System.out.println("Users\n"+UID+"\nGYM");
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users/"+UID+"/GYM").child("defaultMade");
        reference.setValue(true);
    }

    public void setDefaultchecks() {
        try{
            if(MUSCLEGROUP.equalsIgnoreCase("Chest")) {
                Monday.setChecked(true);
         Tuesday.setChecked(false);
         Wednesday.setChecked(false);
         Thursday.setChecked(true);
         Friday.setChecked(false);
         Saturday.setChecked(false);
         Sunday.setChecked(false);
            }
            if(MUSCLEGROUP.equalsIgnoreCase("Shoulder")) {
                Monday.setChecked(false);
                Tuesday.setChecked(false);
                Wednesday.setChecked(true);
                Thursday.setChecked(false);
                Friday.setChecked(false);
                Saturday.setChecked(false);
                Sunday.setChecked(false);
            }
            if(MUSCLEGROUP.equalsIgnoreCase("Arms")) {
                Monday.setChecked(false);
                Tuesday.setChecked(false);
                Wednesday.setChecked(true);
                Thursday.setChecked(false);
                Friday.setChecked(false);
                Saturday.setChecked(false);
                Sunday.setChecked(false);
            }
            if(MUSCLEGROUP.equalsIgnoreCase("Core")) {
                Monday.setChecked(true);
                Tuesday.setChecked(true);
                Wednesday.setChecked(true);
                Thursday.setChecked(true);
                Friday.setChecked(true);
                Saturday.setChecked(false);
                Sunday.setChecked(true);
            }
            if(MUSCLEGROUP.equalsIgnoreCase("quads")) {
                Monday.setChecked(false);
                Tuesday.setChecked(false);
                Wednesday.setChecked(false);
                Thursday.setChecked(false);
                Friday.setChecked(true);
                Saturday.setChecked(false);
                Sunday.setChecked(false);
            }
            if(MUSCLEGROUP.equalsIgnoreCase("Calves")) {
                Monday.setChecked(false);
                Tuesday.setChecked(false);
                Wednesday.setChecked(false);
                Thursday.setChecked(false);
                Friday.setChecked(true);
                Saturday.setChecked(false);
                Sunday.setChecked(false);
            }
            if(MUSCLEGROUP.equalsIgnoreCase("Calves")) {
                Monday.setChecked(false);
                Tuesday.setChecked(true);
                Wednesday.setChecked(false);
                Thursday.setChecked(false);
                Friday.setChecked(false);
                Saturday.setChecked(false);
                Sunday.setChecked(false);
            }

         version1.setChecked(true);
         version2.setChecked(true);
         version3.setChecked(false);
         low_sets.setChecked(true);
         mid_sets.setChecked(true);
         mid_high_sets.setChecked(false);
         high_sets.setChecked(false);
         low_reps.setChecked(true);
         mid_reps.setChecked(true);
         mid_high_reps.setChecked(false);
         high_reps.setChecked(false);}
         catch (NullPointerException e){
             Log.d(TAG,e.getMessage());}
    }

    private boolean updateSwitch(String SWITCH) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("EXERCISES").child("Switch");
        Switcher switched = new Switcher(SWITCH);
        // dR.setValue(switched);
        dR.setValue(switched);
        Log.d(TAG, "Switch changed to: " + SWITCH);
        return true;
    }


    public void fillArray() {

        String[] version = new String[3];
        String[] sets = new String[4], reps = new String[4];
        String[] days = new String[7];
        int daysleft=7;
        if (low_reps.isChecked()) reps[0] = low_reps.getText().toString();
        else reps[0] = "";
        if (mid_reps.isChecked()) reps[1] = mid_reps.getText().toString();
        else reps[1] = "";
        if (mid_high_reps.isChecked()) reps[2] = mid_high_reps.getText().toString();
        else reps[2] = "";
        if (high_reps.isChecked()) reps[3] = high_reps.getText().toString();
        else reps[3] = "";

        if (low_sets.isChecked()) sets[0] = low_sets.getText().toString();
        else sets[0] = "";
        if (mid_sets.isChecked()) sets[1] = mid_sets.getText().toString();
        else sets[1] = "";
        if (mid_high_sets.isChecked()) sets[2] = mid_high_sets.getText().toString();
        else sets[2] = "";
        if (high_sets.isChecked()) sets[3] = high_sets.getText().toString();
        else sets[3] = "";

        if (Monday.isChecked()) {
            days[0] = Monday.getText().toString();

        }

        if (Tuesday.isChecked()) days[1] = Tuesday.getText().toString();
        if (Wednesday.isChecked()) days[2] = Wednesday.getText().toString();
        if (Thursday.isChecked()) days[3] = Thursday.getText().toString();
        if (Friday.isChecked()) days[4] = Friday.getText().toString();
        if (Saturday.isChecked()) days[5] = Saturday.getText().toString();
        if (Sunday.isChecked()) days[6] = Sunday.getText().toString();

        if (version1.isChecked()) {
            version[0] = version1.getText().toString();
            v=1;
        }
        if (version2.isChecked()){
            version[1] = version2.getText().toString();
            v=2;
        }
        if (version3.isChecked()){
            version[2] = version3.getText().toString();
            v=3;
        }

        loadCheckBoxLists(days, sets, reps, version);


    }
    public void getLevel()
    {
        if (difficultLvl <= 3) level = "Beginner";
        else if (difficultLvl > 3 || difficultLvl <= 7) level = "Intermediate";
        else if (difficultLvl > 7) level = "Advanced";
        else level = "Beginner";
    }

    public void getDialogInfo() {
        boolean m = false, t = false, w = false, th = false, f = false, s = false, sd = false, v1 = false, v2 = false, v3 = false, lr = false, mr = false, mhr = false, hr = false, ls = false, ms = false, mhs = false, hs = false;
        if (difficultLvl <= 3) level = "Beginner";
        else if (difficultLvl > 3 || difficultLvl <= 7) level = "Intermediate";
        else if (difficultLvl > 7) level = "Advanced";
        else level = "Beginner";

        fillArray();
        for (String str : daysSelected) {
            if (str.equals("Monday")) m = true;
            if (str.equals("Tuesday")) t = true;
            if (str.equals("Wednesday")) w = true;
            if (str.equals("Thursday")) th = true;
            if (str.equals("Friday")) f = true;
            if (str.equals("Saturday")) s = true;
            if (str.equals("Sunday")) sd = true;

        }
        for (String str : repsSelected) {
            if (str.equals(low_reps.getText())) lr = true;
            if (str.equals(mid_reps.getText())) mr = true;
            if (str.equals(mid_high_reps.getText())) mhr = true;
            if (str.equals(high_reps.getText())) hr = true;

        }
        for (String str : setsSelected) {
            if (str.equals(low_sets.getText())) ls = true;
            if (str.equals(mid_sets.getText())) ms = true;
            if (str.equals(mid_high_sets.getText())) mhs = true;
            if (str.equals(high_sets.getText())) hs = true;

        }
        for (String str : versionSelected) {
            if (str.equals(version1.getText())) v1 = true;
            if (str.equals(version2.getText())) v2 = true;
            if (str.equals(version3.getText())) v3 = true;


        }


    workout_Default = new Workout_Default(level, m, t, w, th, f, s, sd, lr, mr, mhr, hr, ls, ms, mhs, hs, v1, v2, v3, MUSCLEGROUP);
        Log.d(TAG, workout_Default.toString());


    }
    public List<Workout> createVersions(int ListID)
    {
        getLevel();
        double randomValue=0.0;
        final int max=15;
        String lvl="Intermediate";

        List<Workout> temp=new ArrayList<>();
        List<Workout>dump=new ArrayList<>();
         Collections.shuffle(chestList);
        Collections.shuffle(backList);
        Collections.shuffle(armsList);
        Collections.shuffle(quadsList);
        Collections.shuffle(calvesList);
        Collections.shuffle(coreList);
       //System.out.println("***"+chestList);
        String levels[]=new String[]{"I","B","A"};
        int rand=0;
        if(ListID==1)//chest
        {
            for (Workout w: chestList) {
                       // System.out.println(w.getName()+" ");
                if(temp.size()!=max){
                    if(w.getLevel()==null)
                    {

                        w.setLevel(levels[(int)Math.random()*2]);
                    }
                    if(w.getLevel().matches("B|begginer|beginner|BEGGINER|EASY|easy|Easy|b|BEGINNER|Beginner|Begginer"))
                        w.setLevel("Beginner");
                    else if(w.getLevel().matches("I|Intermediate|Intermidiate|INTERMEDIATE|i|INTERMIDIATE|Medium|medium|intermidiate|intermediate"))
                        w.setLevel("Intermediate");
                    else if(w.getLevel().matches("A|Advanced|advanced|Hard|h|a|HARD|ADVANCED|hard"))
                        w.setLevel("Advanced");
                    else w.setLevel("Intermediate");
                    System.out.println(w.getLevel()+" <----level");
                    if(w.getLevel().equals(level)){
                        //System.out.println("Adding w");
                        temp.add(w);
                    }
                    else dump.add(w);
                }
            }
           // System.out.println("****"+temp);
            for(int i=0; i<dump.size();i++)
            {
                if(dump.contains(level))
                    temp.add(dump.get(i));
            }

        }
        else if(ListID==2)//back
        {
            for (Workout w: backList) {

                if(temp.size()!=max){
                    if(w.getLevel()==null)
                    {
                        w.setLevel(levels[(int)Math.random()*2]);
                    }
                    if(w.getLevel().matches("B|begginer|beginner|BEGGINER|EASY|easy|Easy|b|BEGINNER|Beginner|Begginer"))
                        w.setLevel("Beginner");
                    else if(w.getLevel().matches("I|Intermediate|Intermidiate|INTERMEDIATE|i|INTERMIDIATE|Medium|medium|intermidiate|intermediate"))
                        w.setLevel("Intermediate");
                    else if(w.getLevel().matches("A|Advanced|advanced|Hard|h|a|HARD|ADVANCED|hard"))
                        w.setLevel("Advanced");
                    else w.setLevel("Intermediate");
                    if(w.getLevel().equals(level))
                        temp.add(w);
                    else dump.add(w);
                }
            }
            for(int i=0; i<dump.size();i++)
            {
                if(dump.contains(level))
                    temp.add(dump.get(i));
            }
        }
        else if(ListID==3)//arms
        {
            for (Workout w: armsList) {

                if(temp.size()!=max){
                    if(w.getLevel()==null)
                    {
                        w.setLevel(levels[(int)Math.random()*2]);
                    }
                    if(w.getLevel().matches("B|begginer|beginner|BEGGINER|EASY|easy|Easy|b|BEGINNER|Beginner|Begginer"))
                        w.setLevel("Beginner");
                    else if(w.getLevel().matches("I|Intermediate|Intermidiate|INTERMEDIATE|i|INTERMIDIATE|Medium|medium|intermidiate|intermediate"))
                        w.setLevel("Intermediate");
                    else if(w.getLevel().matches("A|Advanced|advanced|Hard|h|a|HARD|ADVANCED|hard"))
                        w.setLevel("Advanced");
                    else w.setLevel("Intermediate");
                    if(w.getLevel().equals(level))
                        temp.add(w);
                    else dump.add(w);
                }
            }
            for(int i=0; i<dump.size();i++)
            {
                if(dump.contains(level))
                    temp.add(dump.get(i));
            }
        }
        else if(ListID==4)//quads
        {
            for (Workout w: quadsList){

                if(temp.size()!=max){
                    if(w.getLevel()==null)
                    {
                        w.setLevel(levels[(int)Math.random()*2]);
                    }
                    if(w.getLevel().matches("B|begginer|beginner|BEGGINER|EASY|easy|Easy|b|BEGINNER|Beginner|Begginer"))
                        w.setLevel("Beginner");
                    else if(w.getLevel().matches("I|Intermediate|Intermidiate|INTERMEDIATE|i|INTERMIDIATE|Medium|medium|intermidiate|intermediate"))
                        w.setLevel("Intermediate");
                    else if(w.getLevel().matches("A|Advanced|advanced|Hard|h|a|HARD|ADVANCED|hard"))
                        w.setLevel("Advanced");
                    else w.setLevel("Intermediate");
                    if(w.getLevel().equals(level))
                        temp.add(w);
                    else dump.add(w);
                }
            }
            for(int i=0; i<dump.size();i++)
            {
                if(dump.contains(level))
                    temp.add(dump.get(i));
            }
        }
        else if(ListID==5)//calves
        {
            for (Workout w: calvesList) {

                if(temp.size()!=max){
                    if(w.getLevel()==null)
                    {
                        w.setLevel(levels[(int)Math.random()*2]);
                    }
                    if(w.getLevel().matches("B|begginer|beginner|BEGGINER|EASY|easy|Easy|b|BEGINNER|Beginner|Begginer"))
                        w.setLevel("Beginner");
                    else if(w.getLevel().matches("I|Intermediate|Intermidiate|INTERMEDIATE|i|INTERMIDIATE|Medium|medium|intermidiate|intermediate"))
                            w.setLevel("Intermediate");
                    else if(w.getLevel().matches("A|Advanced|advanced|Hard|h|a|HARD|ADVANCED|hard"))
                        w.setLevel("Advanced");
                    else w.setLevel("Intermediate");
                    if(w.getLevel().equals(level))
                        temp.add(w);
                    else dump.add(w);
                }
            }
            for(int i=0; i<dump.size();i++)
            {
                if(dump.contains(level))
                    temp.add(dump.get(i));
            }
        }
        else if(ListID==6)//core
        {
            for (Workout w: coreList){

                if(temp.size()!=max){
                    if(w.getLevel()==null)
                    {
                        w.setLevel(levels[(int)Math.random()*2]);
                    }
                    if(w.getLevel().matches("B|begginer|beginner|BEGGINER|EASY|easy|Easy|b|BEGINNER|Beginner|Begginer"))
                        w.setLevel("Beginner");
                    else if(w.getLevel().matches("I|Intermediate|Intermidiate|INTERMEDIATE|i|INTERMIDIATE|Medium|medium|intermidiate|intermediate"))
                        w.setLevel("Intermediate");
                    else if(w.getLevel().matches("A|Advanced|advanced|Hard|h|a|HARD|ADVANCED|hard"))
                        w.setLevel("Advanced");
                    else w.setLevel("Intermediate");
                    if(w.getLevel().equals(level))
                        temp.add(w);
                    else dump.add(w);
                }

            }
            for(int i=0; i<dump.size();i++)
            {
                if(dump.contains(level))
                    temp.add(dump.get(i));
            }
        }
        else if(ListID==7)//core
        {
            for (Workout w: shoulderList){

                if(temp.size()!=max){
                    if(w.getLevel()==null)
                    {
                        w.setLevel(levels[(int)Math.random()*2]);
                    }
                    if(w.getLevel().matches("B|begginer|beginner|BEGGINER|EASY|easy|Easy|b|BEGINNER|Beginner|Begginer"))
                        w.setLevel("Beginner");
                    else if(w.getLevel().matches("I|Intermediate|Intermidiate|INTERMEDIATE|i|INTERMIDIATE|Medium|medium|intermidiate|intermediate"))
                        w.setLevel("Intermediate");
                    else if(w.getLevel().matches("A|Advanced|advanced|Hard|h|a|HARD|ADVANCED|hard"))
                        w.setLevel("Advanced");
                    else w.setLevel("Intermediate");
                    if(w.getLevel().equals(level))
                        temp.add(w);
                    else dump.add(w);
                }

            }
            for(int i=0; i<dump.size();i++)
            {
                if(dump.contains(level))
                    temp.add(dump.get(i));
            }
        }
        System.out.println("~~~"+temp);
        return temp;


    }
    //TODO: need to find a way from stopping the user from selcting the days all already choosen
    public void disableUsedDays()
    {
        if(isDayselected("Monday"))
        {
            isM=true;
        }
        if(isDayselected("Tuesday"))
        {
            isT=true;
        }
         if(isDayselected("Wednesday"))
        {
           isW=true;
        }
         if(isDayselected("Thursday"))
        {
            isTh=true;
        }
         if(isDayselected("Friday"))
        {
           isF=true;
        }
         if(isDayselected("Saturday"))
        {
            isSat=true;
        }
         if(isDayselected("Sunday"))
        {
           isSun=true;
        }


    }

    public void showWorkoutList(String mg, int version) {

        chestList.clear();
        backList.clear();
        shoulderList.clear();
        armsList.clear();
        quadsList.clear();
        calvesList.clear();
        coreList.clear();
        System.out.println("Showing List: " + mg + "\nVersion: " + version);

        loadChestWorkouts();
        loadARMSWorkouts();
        loadBackWorkouts();
        loadCalvesWorkouts();
        loadShoulderWorkouts();
        loadQuadsWorkouts();
        loadCOREWorkouts();
        System.out.println("VERSion "+version);





    }

    public void viewerSetup(String musclegroup,int version) {



        myDataset.get(0).clear();
        myDataset.get(1).clear();
        imgSets.clear();
        workout_view.setVisibility(View.VISIBLE);
        defaultSettingd.setVisibility(View.GONE);

        wv_muscleGroup.setText(musclegroup);

        List<Workout> temp=null;


        System.out.println("**"+musclegroup);
        if(musclegroup.equalsIgnoreCase("Chest")) {
                temp=createVersions(1);
                if(version==1)
                {
                    v1List=temp;
                }
                else if(version==2)
                {
                    v2List=temp;
                 }
                 else if(version==3)
                {
                    v3List=temp;
                }
            System.out.println("**"+temp);
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
            if(version==1)
            {
                v1List=temp;
            }
            else if(version==2)
            {
                v2List=temp;
            }
            else if(version==3)
            {
                v3List=temp;
            }
            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }

        }
        else if(musclegroup.equalsIgnoreCase("Arms")) {
            temp=createVersions(3);
            if(version==1)
            {
                v1List=temp;
            }
            else if(version==2)
            {
                v2List=temp;
            }
            else if(version==3)
            {
                v3List=temp;
            }
            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        else if(musclegroup.equalsIgnoreCase("Quads")) {
            temp=createVersions(4);
            if(version==1)
            {
                v1List=temp;
            }
            else if(version==2)
            {
                v2List=temp;
            }
            else if(version==3)
            {
                v3List=temp;
            }
            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        else if(musclegroup.equalsIgnoreCase("Calves")) {
            temp=createVersions(5);
            if(version==1)
            {
                v1List=temp;
            }
            else if(version==2)
            {
                v2List=temp;
            }
            else if(version==3)
            {
                v3List=temp;
            }
            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        else if(musclegroup.equalsIgnoreCase("Core")) {
            temp=createVersions(6);
            if(version==1)
            {
                v1List=temp;
            }
            else if(version==2)
            {
                v2List=temp;
            }
            else if(version==3)
            {
                v3List=temp;
            }
            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        else if(musclegroup.equalsIgnoreCase("Shoulder")) {
            temp=createVersions(7);
            if(version==1)
            {
                v1List=temp;
            }
            else if(version==2)
            {
                v2List=temp;
            }
            else if(version==3)
            {
                v3List=temp;
            }
            for (Workout w: temp) {
                myDataset.get(0).add(w.getName());
                myDataset.get(1).add(w.getInfo());
                imgSets.add(covertLinkToImg(w.getImg()));

            }
        }
        close.setOnClickListener(e -> {
            workout_view.setVisibility(View.GONE);
            defaultSettingd.setVisibility(View.VISIBLE);
        });
        if(getActivity()!=null)
        {
            mAdapter=new MyAdapter(getActivity(),myDataset,imgSets);
            mRecyclerView.setAdapter(mAdapter);
        }
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

    public void loadCheckBoxLists(String[] days, String[] sets, String[] reps, String[] version) {
        daysSelected.clear();
        setsSelected.clear();
        repsSelected.clear();
        versionSelected.clear();
        for (int i = 0; i < days.length; i++) {
            if (!(days[i] == null || days[i].equals("")))
                daysSelected.add(days[i]);
        }
        for (int i = 0; i < sets.length; i++) {
            if (!(sets[i] == null || sets[i].equals("")))
                setsSelected.add(sets[i]);
        }
        for (int i = 0; i < reps.length; i++) {
            if (!(reps[i] == null || reps[i].equals("")))
                repsSelected.add(reps[i]);
        }
        for (int i = 0; i < version.length; i++) {
            if (!(version[i] == null || version[i].equals("")))
                versionSelected.add(version[i]);
        }
    }

    public void saveListener() {
       // List<Workout>workouttoSave=new ArrayList<>();

        presets.clear();
        getAll(2018);
        for (int i = 0; i < presets.get(0).size(); i++)
         Log.d(TAG, "DATES: " + presets.get(0).get(i) + "\nREPS: " + presets.get(1).get(i) + "\nSETS: " + presets.get(2).get(i) + "\nVERSION: " + presets.get(3).get(i));
        UID = user.getUid();
        CreatedEvents event = null;
        WorkoutPlan plan=null;
        for (int i = 0; i < presets.get(0).size(); i++)

        {
            List<Workout>workouttoSave=new ArrayList<>();

                if (presets.get(3).get(i).equalsIgnoreCase("Version 1.0"))
                    workouttoSave= v1List;
                else if (presets.get(3).get(i).equalsIgnoreCase("Version 2.0"))
                    workouttoSave=v2List;
                else if (presets.get(3).get(i).equalsIgnoreCase("Version 3.0"))
                    workouttoSave=v3List;
                else {
                    workouttoSave=v1List;
                    System.out.println("Saving..." + workouttoSave.get(i).getName());
                }

              System.out.println(v1List);


            plan = new WorkoutPlan("Default GYM - " + MUSCLEGROUP + " - " + presets.get(3).get(i),ConvertFromDateToLong(presets.get(0).get(i)),(ArrayList<Workout>) workouttoSave,System.currentTimeMillis() ,UID,"GYM",presets.get(0).get(i));

            mFirestore.collection(UID)
                    .add(plan)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                           /* Toast.mak5eText(getActivity(),
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
        disableUsedDays();


    }

    private Long ConvertFromDateToLong(String date) {
        long milliseconds = System.currentTimeMillis();
        System.out.println("system Time: " + milliseconds);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
            System.out.println("Date Time: " + milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public void getAll(int year) {
        ArrayList<String> daysImMonths = new ArrayList<String>();
        String versions[] = new String[repsSelected.size()];
        for (int i = 0; i < repsSelected.size(); i++)
            versions[i] = repsSelected.get(i);
        String versions2[] = new String[setsSelected.size()];
        for (int i = 0; i < setsSelected.size(); i++)
            versions2[i] = setsSelected.get(i);
        String version3[] = new String[versionSelected.size()];
        for (int i = 0; i < versionSelected.size(); i++)
            version3[i] = versionSelected.get(i);
        // String versions2[]=new String[]{"3-4","1-2","6-8","8-10"};
        // String version3[]=new String[]{"Version 1.0","Version 2.0","Version 3.0"};
        presets.add(new ArrayList<String>());//Dates
        presets.add(new ArrayList<String>());//Repe
        presets.add(new ArrayList<String>());//Sets
        presets.add(new ArrayList<String>());//workouts
        List<String> temp = new ArrayList<>();

        for (int i = 0; i < daysSelected.size(); i++) {
            for (String s : getAllMondaysInAyear(year, i + 1, daysSelected.get(i))) {
                temp.add(s);

            }

        }
        for (int i = 0; i < temp.size(); i++) {
            presets.get(0).add(temp.get(i));
            presets.get(1).add(selectRandomRepsÀndSets(versions));
            presets.get(2).add(selectRandomRepsÀndSets(versions2));
            presets.get(3).add(selectRandomRepsÀndSets(version3));
        }

    }


    public String selectRandomRepsÀndSets(String[] versions) {
        String v = "";
        /**This method is used to generate random reps and sets to create a fullCalendar of different data*/
        double randomNumbers = Math.random() * 1;
        if (versions.length == 3) {
            if (randomNumbers < 0.50 && randomNumbers >= 0)
                v = versions[0];
            else if (randomNumbers < 0.86 && randomNumbers >= 0.5)
                v = versions[1];
            else {
                v = versions[2];

            }

        } else if (versions.length == 4) {
            if (randomNumbers < 0.50 && randomNumbers >= 0)
                v = versions[0];
            else if (randomNumbers < 0.76 && randomNumbers >= 0.5)
                v = versions[1];
            else if (randomNumbers < 0.95 && randomNumbers >= 0.76) {
                v = versions[2];

            } else v = versions[3];
        } else if (versions.length == 2) {
            if (randomNumbers < 0.50 && randomNumbers >= 0)
                v = versions[0];
            else
                v = versions[1];

        } else if (versions.length == 1) {
            v = versions[0];
        }


        return v;
    }

    public String selectVersion(String[] versions) {
        String v;
    /***/

        double randomNumbers = Math.random() * 1;

        if (randomNumbers < 0.50 && randomNumbers >= 0)
            v = versions[0];
        else if (randomNumbers < 0.86 && randomNumbers >= 0.5)
            v = versions[1];
        else
            v = versions[2];

        return v;
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

            System.out.println("Number of x : " + mondays);


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

    public boolean enableSave() {
        /**Only Enable Save if  radioButtons and checkButtons are null.*/
        if (Monday.isChecked() || Tuesday.isChecked() || Wednesday.isChecked() || Thursday.isChecked() || Friday.isChecked() || Saturday.isChecked() || Sunday.isChecked()) {
            if (version1.isChecked() || version2.isChecked() || version3.isChecked()) {
                if (low_reps.isChecked() || mid_reps.isChecked() || mid_high_reps.isChecked() || high_reps.isChecked()) {
                    if (low_sets.isChecked() || mid_sets.isChecked() || mid_high_sets.isChecked() || high_sets.isChecked()) {
                        saveBtn.setBackgroundColor(Color.parseColor("#07ab46"));
                        return true;
                    } else {
                        Log.d(TAG, "Sets not selected");
                        toastMessage("No Sets Selected", Toast.LENGTH_SHORT);
                        saveBtn.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                        return false;
                    }
                } else {
                    Log.d(TAG, "Reps not selected");
                    toastMessage("No Reps Selected", Toast.LENGTH_SHORT);
                    saveBtn.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                    return false;
                }
            } else {
                Log.d(TAG, "Version not selected");
                toastMessage("No Version Selected", Toast.LENGTH_SHORT);
                saveBtn.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                return false;
            }
        } else {
            Log.d(TAG, "Day not selected");
            toastMessage("No Day Selected", Toast.LENGTH_SHORT);
            saveBtn.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
            return false;
        }
    }

    private boolean isDayselected(String day)
    {
        if(day.equalsIgnoreCase("Monday"))
            return workout_Default.isMonday();
        else   if(day.equalsIgnoreCase("Tuesday"))
            return workout_Default.isTuesday();
        else  if(day.equalsIgnoreCase("Wednesday"))
            return workout_Default.isWednesday();
        else  if(day.equalsIgnoreCase("Thursday"))
            return workout_Default.isThursday();
        else  if(day.equalsIgnoreCase("Friday"))
            return workout_Default.isFriday();
        else  if(day.equalsIgnoreCase("Saturday"))
            return workout_Default.isSaturday();
        else  if(day.equalsIgnoreCase("Sunday"))
            return workout_Default.isSunday();
        else  return false;
    }
    private void toastMessage(String msg, int duration) {
        Toast.makeText(getActivity(), msg, duration).show();
    }

    private void showData(DocumentSnapshot documentSnapshot) {

    }

    private void showData(DataSnapshot dataSnapshot) {
        System.out.println("Getting data....");
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Workout workout = new Workout();
            for (DataSnapshot ds1 : ds.getChildren()) {
                System.out.println(dataSnapshot);
                String workoutNAme = ds1.child("EXERCISES").child("CHEST").getChildren().toString();
                System.out.println(workoutNAme);


                // workout.setName(ds1.child("EXERCISES").child("CHEST").child(workoutNAme).child("name").getValue().toString());
                //chestList.add(workout);
            }
        }
    }

    public List<Workout> getWorkouts() {
        System.out.println("Changing value of switch.... to off");
        myRefrence.child("EXERCISES").setValue("Switch", "off");
        return list;
    }

    @Override
    public void onStart() {
        super.onStart();
        myRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChanged Database....");
                Log.d(TAG, "onDatachanged, Added Information to database");
                //showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "The read failed" + databaseError);
            }
        });

    }


    public void onSetWorkout(View view, String muscleGroup, String bodyType) {

    }

    public void loadChestWorkouts() {

        pb.setVisibility(View.VISIBLE);

        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("CHEST");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    System.out.println(ds);
                    Workout workout=ds.getValue(Workout.class);
                    chestList.add(workout);
                    System.out.println(workout.getName());
                }
                pb.setVisibility(View.GONE);
                viewerSetup(MUSCLEGROUP,v);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void loadShoulderWorkouts() {
        pb.setVisibility(View.VISIBLE);
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("SHOULDER");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    System.out.println(ds);
                    Workout workout=ds.getValue(Workout.class);
                    shoulderList.add(workout);
                    //System.out.println(workout.getName());
                }
                pb.setVisibility(View.GONE);
                viewerSetup("Shoulder",v);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadBackWorkouts() {
        pb.setVisibility(View.VISIBLE);
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("BACK");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    System.out.println(ds);
                    Workout workout=ds.getValue(Workout.class);
                    backList.add(workout);
                    //System.out.println(workout.getName());
                }
                pb.setVisibility(View.GONE);
                viewerSetup(MUSCLEGROUP,v);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadARMSWorkouts() {
        pb.setVisibility(View.VISIBLE);
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("Triceps");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    System.out.println(ds);
                    Workout workout=ds.getValue(Workout.class);
                    armsList.add(workout);
                    System.out.println(workout.getName());
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
                    System.out.println(ds);
                    Workout workout=ds.getValue(Workout.class);
                    armsList.add(workout);
                    System.out.println(workout.getName());
                }
                pb.setVisibility(View.GONE);
                viewerSetup("Arms",v);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadCOREWorkouts() {
        pb.setVisibility(View.VISIBLE);
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("CORE");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    System.out.println(ds);
                    Workout workout=ds.getValue(Workout.class);
                    coreList.add(workout);
                    //System.out.println(workout.getName());
                }
                pb.setVisibility(View.GONE);
                viewerSetup(MUSCLEGROUP,v);
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
                System.out.println("onDataChanged Database....");
                Log.d(TAG, "onDatachanged, Added Information to database");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Workout workout = new Workout();
                    String workoutNAme = String.valueOf(ds.child("EXERCISES").child("GLUTES").getValue());
                    System.out.println(workoutNAme);
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
        pb.setVisibility(View.VISIBLE);
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("QUADS");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    System.out.println(ds);
                    Workout workout=ds.getValue(Workout.class);
                    quadsList.add(workout);
                    //System.out.println(workout.getName());
                }
                pb.setVisibility(View.GONE);
                viewerSetup("Quads",v);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadCalvesWorkouts() {
        pb.setVisibility(View.VISIBLE);
        DatabaseReference db=firebaseDatabase.getReference("EXERCISES").child("Calves");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    System.out.println(ds);
                    Workout workout=ds.getValue(Workout.class);
                    calvesList.add(workout);
                    //System.out.println(workout.getName());
                }
                pb.setVisibility(View.GONE);
                viewerSetup(MUSCLEGROUP,v);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void goBack() {
        b_r.setVisibility(View.VISIBLE);
        endo_r.setVisibility(View.GONE);
        ecto_r.setVisibility(View.GONE);
        m_r.setVisibility(View.GONE);
    }


    public static String getSelectedMusleGroup() {
        return MUSCLEGROUP;
    }

    public void startWorkoutActivity(String muscleGroup) {//TODO: create workout Activity
        System.out.println(muscleGroup);
        /*Intent intent=new Intent(getActivity(),DefaultGymplan.class);
        startActivity(intent);*/
        MUSCLEGROUP = muscleGroup;

        GymActivity gymActivity = new GymActivity();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //startActivity(new Intent("ie.ul.o.daysaver.DefaultGymplan"));
        showUpdateDialog(muscleGroup);
    }

    public DefaultView() {
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public void goToA() {
        b_r.setVisibility(View.GONE);
        endo_r.setVisibility(View.VISIBLE);
        ecto_r.setVisibility(View.GONE);
        m_r.setVisibility(View.GONE);
    }

    public void goToB() {
        b_r.setVisibility(View.GONE);
        endo_r.setVisibility(View.GONE);
        ecto_r.setVisibility(View.VISIBLE);
        m_r.setVisibility(View.GONE);
    }

    public void goToC() {
        b_r.setVisibility(View.GONE);
        endo_r.setVisibility(View.GONE);
        ecto_r.setVisibility(View.GONE);
        m_r.setVisibility(View.VISIBLE);
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