package ie.ul.o.daysaver;

/**
 * Created by Ollie on 28/03/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private ArrayList<WorkoutPlan> mArrayList;
    private ArrayList<WorkoutPlan> mFilteredList;
    private ArrayList<UserInformation> uArrayList;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView workoutName,dateCreated,username;
        private ImageView profileImg;
       // private Button useBtn;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            workoutName = (TextView)view.findViewById(R.id.workoutname);
            dateCreated = (TextView)view.findViewById(R.id.createdby);
            username = (TextView)view.findViewById(R.id.community_nam);
            profileImg=(ImageView)view.findViewById(R.id.community_pic);



        }

        @Override
        public void onClick(View view) {

        }
    }


    public DataAdapter(ArrayList<WorkoutPlan> arrayList,ArrayList<UserInformation>userList,Context context) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        uArrayList=userList;
        this.context=context;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MM yyyy ", Locale.getDefault());
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd MM yyyy ", Locale.getDefault());
    SimpleDateFormat byMonth = new SimpleDateFormat("MMM", Locale.getDefault());
    SimpleDateFormat byDay = new SimpleDateFormat("E", Locale.getDefault());





    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.workoutName.setText(mFilteredList.get(i).getName());
        viewHolder.dateCreated.setText( sdf.format(mFilteredList.get(i).getD_O_C()));
        System.out.println("Amount "+i);
       if(uArrayList.size()>i){
           System.out.println("Amount "+i);
            viewHolder.username.setText(uArrayList.get(i).getUsername());
            viewHolder.profileImg.setImageDrawable(new RoundImage(covertLinkToImg(uArrayList.get(i).getImage())));
           viewHolder.itemView.setOnClickListener(e->{
               System.out.print("**Click**\n Retrivimng details...\n"+"Context: "+context+"\nUserInformation: "+uArrayList.get(i).getUsername()+"\nWorkout: "+mFilteredList.get(i).getWorkout());
            alertDialogCreator(uArrayList.get(i),mFilteredList.get(0));
           });
           }
        else{
           if(uArrayList.size()>0) {
               viewHolder.username.setText(uArrayList.get(0).getUsername());
               viewHolder.profileImg.setImageDrawable(new RoundImage(covertLinkToImg(uArrayList.get(0).getImage())));
           }
        }




    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<WorkoutPlan> filteredList = new ArrayList<>();

                    for (WorkoutPlan wp : mArrayList ) {

                        if (wp.getName().toLowerCase().contains(charString) ||
                                sdf.format(wp.getD_O_C()).toLowerCase().contains(charString) ||
                                sdf1.format(wp.getD_O_C()).toLowerCase().contains(charString)||
                                byMonth.format(wp.getD_O_C()).toLowerCase().contains(charString)||
                     //by day for
                                byDay.format(wp.getDay()).toLowerCase().contains(charString)||checkMatchFrName(charString))
                               {

                            filteredList.add(wp);
                        }else
                        {

                        }/** After filter by is made fore more option*/
                       /* for(UserInformation  ui: uArrayList)
                        {
                            if (ui.getUsername().toLowerCase().contains(charString) ||
                                    ui.getEmail().toLowerCase().contains(charString) ||
                                    ui.getFirstName().toLowerCase().contains(charString) ||
                                    ui.getLastName().toLowerCase().contains(charString) ||
                                    ui.getGender().toLowerCase().contains(charString) ||)

                            // ui.getLocation().toLowerCase().contains(charString) ||)
                            {
                                filteredList.add(ui);
                            }

                        }*/
                    }


                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            private boolean checkMatchFrName(String charString)
            {
                for(UserInformation  ui: uArrayList)
                {
                    if (ui.getUsername().toLowerCase().contains(charString) ||
                            ui.getEmail().toLowerCase().contains(charString) ||
                            ui.getFirstName().toLowerCase().contains(charString) ||
                            ui.getLastName().toLowerCase().contains(charString) ||
                            ui.getGender().toLowerCase().contains(charString))return true;
                    /**A future feature*/else return  false;


                }
                return false;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<WorkoutPlan>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }//TODO: import my round image usage  and my method changelongtodate?
    public Bitmap covertLinkToImg(String imgUrl)
    {

        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.gym_small);
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
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    private ArrayList<ArrayList<String>>workoutLists;
    private ArrayList<Bitmap>imgs;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    /***get the context from the CustomView Class, */
    /**The method gets displays the detail of the workout and the user no real interaction yet...maybe later*/
    public void alertDialogCreator( UserInformation userInfo, WorkoutPlan wplan)
    {
        final String uname=userInfo.getUsername();
        final String img=userInfo.getImage();

        final ArrayList<Workout>exercises=wplan.getWorkout();

        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
      LayoutInflater inflater =LayoutInflater.from(context);//getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.aboutworkout_plan, null);
        dialogBuilder.setView(dialogView);

        workoutLists=new ArrayList<ArrayList<String>>();
        workoutLists.add(new ArrayList<String>());
        workoutLists.add(new ArrayList<String>());
        imgs=new ArrayList<Bitmap>();



        /**create Alert Dialog objects..*/
        ImageView pic=dialogView.findViewById(R.id.pIcon);
        TextView name=dialogView.findViewById(R.id.pName);
        RecyclerView rview=dialogView.findViewById(R.id.pRV);
        Button useBtn=dialogView.findViewById(R.id.useplan);
        android.support.v7.app.AlertDialog alertDialog=dialogBuilder.create();

        useBtn.setOnClickListener(e->{
            FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .add(wplan)
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
                            Log.w("LLL", "Error adding event documtent", e);
                           /* Toast.makeText(getActivity(),
                                    "Event document could not be added",
                                    Toast.LENGTH_SHORT).show();*/
                        }
                    });


        alertDialog.dismiss();
        });

        mLayoutManager = new LinearLayoutManager(context);
        rview.setLayoutManager(mLayoutManager);

        name.setText(uname);
        pic.setImageDrawable(new RoundImage(covertLinkToImg(img)));

        try{
       for(Workout w:exercises)
       {
           workoutLists.get(0).add(w.getName());
           workoutLists.get(1).add(w.getInfo());
           imgs.add(covertLinkToImg(w.getImg()));
       }
        }catch (NullPointerException n){workoutLists.get(0).add(new Workout().getName());workoutLists.get(1).add(new Workout().getInfo());}

        if(context!=null)
        {
            mAdapter=new MyAdapter(context,workoutLists,imgs);
            rview.setAdapter(mAdapter);
        }


       alertDialog.show();


    }
}


