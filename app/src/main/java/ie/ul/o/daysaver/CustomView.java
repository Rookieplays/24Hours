package ie.ul.o.daysaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the45
 * {@link CustomView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static  String MUSCLEGROUP;

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;
    private String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private Button addCustomBtn;
    private CardView cardView;
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RoundImage roundImage;
    private ArrayList<WorkoutPlan> gymWorkouts=new ArrayList<>();
    private ArrayList<UserInformation>uinfo=new ArrayList<>();
   private static DataAdapter STATIC_ADAPTER;
   private Button filterBtn, addNewButton;
   private TextView noneFound;
    DatabaseReference ref;
    boolean showAll=true;
    ProgressDialog progressDialog;
    private OnFragmentInteractionListener mListener;

    public CustomView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomView.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomView newInstance(String param1, String param2) {
        CustomView fragment = new CustomView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }
   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.searh_menu,menu);
        super.onCreateOptionsMenu(menu,menuInflater);

        MenuItem search = menu.findItem(R.id.search);
  SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
      search(searchView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    private void buttonChanger()
    {
        filterBtn.setOnClickListener(e->{if(showAll==true){
            filterBtn.setText(getString(R.string.myWorkouts));
            gymWorkouts.clear();
            uinfo.clear();
            loadAllWorkouts();
            showAll=false;
        }
        else{
            filterBtn.setText(getString(R.string.showAll));
            gymWorkouts.clear();
            uinfo.clear();
            loadMyWorkouts();
            showAll=true;
        }});
    }

    private void initViews(){

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void loadAllWorkouts()
    {
        progressDialog.setMessage("Getting All Plans...");
        progressDialog.show();
        gymWorkouts.clear();
        uinfo.clear();
        //TODO: Create a doc for every person that creates a new workout.
        /**create a doc for every workout added containing the @WorkoutPlan class, this method get that data...*/
      fStore.collection("Public_workouts").whereEqualTo("id","public").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
              if (task.isSuccessful()) {
                  List<WorkoutPlan> workoutplans = new ArrayList<>();

                  for (DocumentSnapshot doc : task.getResult()) {
                      System.out.println(doc.getData());
                      WorkoutPlan wp = doc.toObject(WorkoutPlan.class);
                      System.out.println(wp+"***********"+wp.getUID());
                      workoutplans.add(wp);
                  }
                  System.out.println(workoutplans);
                  gymWorkouts.addAll(workoutplans);
                  for(WorkoutPlan wp:gymWorkouts)
                  {
                      ref=firebaseDatabase.getReference("Users").child(wp.getUID()).child("UserInformation");
                      ref.addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(DataSnapshot dataSnapshot) {
                              System.out.println(dataSnapshot.getValue());
                              UserInformation user=dataSnapshot.getValue(UserInformation.class);
                              System.out.println(user);
                              if(user!=null)
                              {
                                  uinfo.add(user);
                              }
                          }

                          @Override
                          public void onCancelled(DatabaseError databaseError) {

                          }
                      });
                      if(uinfo.isEmpty())
                      {

                          uinfo.add(new UserInformation("ojdevs.gmail.com", "Hbot","gs://hours-1a681.appspot.com/hbot_wave_lava_sml.png" ));
                      }
                      firebaseDatabase.getReference("Users");
                      mAdapter = new DataAdapter(gymWorkouts,uinfo,getActivity());
                      recyclerView.setAdapter(mAdapter);
                      STATIC_ADAPTER=mAdapter;


                  }
                  progressDialog.hide();

              }
          }
      });
    }
    public void getWorkouts(int switchTo)
    {
       switch(switchTo){
        case 2: {
            loadMyWorkouts();

        }
        break;
           case 1: {
               loadAllWorkouts();

           }
    }
    }
    public void addWorkoutToPublic(){

        fStore.collection("Public_workouts")
                .add(new WorkoutPlan()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Write to Firestore","Job done ☺\n"+documentReference);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Write to Firebasze:","Job Failed");
            }
        });
    }
    public void loadMyWorkouts()
    {
        progressDialog.setMessage("Getting Your Plans...");
        progressDialog.show();
        gymWorkouts.clear();
        uinfo.clear();
        fStore.collection(UID).whereEqualTo("id","private").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<WorkoutPlan> workoutplans = new ArrayList<>();


                    for (DocumentSnapshot doc : task.getResult()) {
                        System.out.println(doc.getData());
                        WorkoutPlan wp = doc.toObject(WorkoutPlan.class);
                        System.out.println(wp+"***********"+wp.getUID());
                        workoutplans.add(wp);
                    }
                    System.out.println(workoutplans);
                    gymWorkouts.addAll(workoutplans);
                    for(WorkoutPlan wp:gymWorkouts)
                    {
                        ref=firebaseDatabase.getReference("Users").child(UID).child("UserInformation");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                System.out.println(dataSnapshot.getValue());
                                UserInformation user=dataSnapshot.getValue(UserInformation.class);
                                System.out.println(user);
                                if(user!=null)
                                {
                                    uinfo.add(user);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                       /* if(uinfo.isEmpty())
                        {

                            uinfo.add(new UserInformation("ojdevs.gmail.com", "Hbot","gs://hours-1a681.appspot.com/hbot_wave_lava_sml.png" ));
                        }*/
                        firebaseDatabase.getReference("Users");
                        mAdapter = new DataAdapter(gymWorkouts,uinfo,getActivity());
                        recyclerView.setAdapter(mAdapter);
                        STATIC_ADAPTER=mAdapter;


                    }

                }progressDialog.hide();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /**initialize variables*/
    View view=inflater.inflate(R.layout.fragment_custom_view, container, false);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        Toolbar tool = (Toolbar)view.findViewById(R.id.stb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(tool);
        firebaseDatabase=FirebaseDatabase.getInstance();
        gymWorkouts=new ArrayList<WorkoutPlan>();
        recyclerView=view.findViewById(R.id.rv_custom);
        filterBtn=view.findViewById(R.id.filterBtn);
        addNewButton=view.findViewById(R.id.customWorkoutBtn);
        noneFound=view.findViewById(R.id.noResult);
        addNewButton.setOnClickListener(e->{startActivity(new Intent(getContext(),createWorkout.class));});
        progressDialog=new ProgressDialog(getActivity());
        loadAllWorkouts();

        initViews();
        buttonChanger();
        //getWorkouts();

        //addWorkoutToPublic();
       // loadAllWorkouts();




        return view;
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
    private  void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (mAdapter != null) mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}
