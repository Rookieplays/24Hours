package ie.ul.o.daysaver;

/**
 * Created by Ollie on 28/03/2018.
 */

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private ArrayList<WorkoutPlan> mArrayList;
    private ArrayList<WorkoutPlan> mFilteredList;
    private ArrayList<UserInformation> uArrayList;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private Context context;
    private String dayTime;
    private int mHour,mMinute;
    private String day;
    private DatePickerDialog datePickerDialog;

    public void sortInAlphabeticalOrder(char order) {
        ArrayList<WorkoutPlan>temp=new ArrayList<>();
        temp=mFilteredList;

        String tempStr="";
        pd.show();
        if(order=='a')//ascending
        {
            for(int i=0; i<temp.size();i++)
            {

                for(int j=i+1; j<temp.size(); j++)
                {
                    //System.out.println()(j+")"+temp.get(i).getName().compareToIgnoreCase(temp.get(j).getName())+"?< 0 ("+temp.get(i).getName()+" is being compared to "+ temp.get(j).getName()+")");
                    if(temp.get(j).getName().compareToIgnoreCase(temp.get(i).getName()) < 0)
                    {

                        ////System.out.println()(temp);
                        tempStr=temp.get(j).getName();


                        temp.get(j).setName(temp.get(i).getName());
                        ////System.out.println()(list);
                        temp.get(i).setName(tempStr);

                    }
                }
            }mFilteredList=temp;
        }
        else if(order=='z')//descending
        {
            for(int i=0; i<temp.size();i++)
            {

                for(int j=i+1; j<temp.size(); j++)
                {
                    //System.out.println()(j+")"+temp.get(i).getName().compareToIgnoreCase(temp.get(j).getName())+"?< 0 ("+temp.get(i).getName()+" is being compared to "+ temp.get(j).getName()+")");
                    if(temp.get(j).getName().compareToIgnoreCase(temp.get(i).getName()) > 0)
                    {

                        ////System.out.println()(temp);
                        tempStr=temp.get(j).getName();


                        temp.get(j).setName(temp.get(i).getName());
                        ////System.out.println()(list);
                        temp.get(i).setName(tempStr);

                    }
                }
            }mFilteredList=temp;
        }else mFilteredList=mFilteredList;
        pd.dismiss();
    }
    private ProgressDialog pd;

    public void filterByDay()
    {
        pd.show();
        ArrayList<WorkoutPlan>temp=new ArrayList<>();
        for(WorkoutPlan i:mFilteredList)
        {
            if(new SimpleDateFormat("E",Locale.getDefault()).format(i.getDay()).equalsIgnoreCase("Mon")){
                temp.add(i);
            }
        }
        for(WorkoutPlan i:mFilteredList)
        {
            if(new SimpleDateFormat("E",Locale.getDefault()).format(i.getDay()).equalsIgnoreCase("Tue")){
                temp.add(i);
            }
        }
        for(WorkoutPlan i:mFilteredList)
        {
            if(new SimpleDateFormat("E",Locale.getDefault()).format(i.getDay()).equalsIgnoreCase("Wed")){
                temp.add(i);
            }
        }
        for(WorkoutPlan i:mFilteredList)
        {
            if(new SimpleDateFormat("E",Locale.getDefault()).format(i.getDay()).equalsIgnoreCase("Thur")){
                temp.add(i);
            }
        }
        for(WorkoutPlan i:mFilteredList)
        {
            if(new SimpleDateFormat("E",Locale.getDefault()).format(i.getDay()).equalsIgnoreCase("Fri")){
                temp.add(i);
            }
        }
        for(WorkoutPlan i:mFilteredList)
        {
            if(new SimpleDateFormat("E",Locale.getDefault()).format(i.getDay()).equalsIgnoreCase("Sat")){
                temp.add(i);
            }
        }
        for(WorkoutPlan i:mFilteredList)
        {
            if(new SimpleDateFormat("E",Locale.getDefault()).format(i.getDay()).equalsIgnoreCase("Sun")){
                temp.add(i);
            }
        }

        mFilteredList=temp;
        pd.dismiss();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView workoutName,dateCreated,username;
        private ImageView profileImg;
       // private Button useBtn;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            workoutName = view.findViewById(R.id.workoutname);
            dateCreated = view.findViewById(R.id.createdby);
            username = view.findViewById(R.id.community_nam);
            profileImg= view.findViewById(R.id.community_pic);



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
        pd=new ProgressDialog(context);
        pd.setMessage("Loading...");
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
        //System.out.println()("Amount "+i);
       if(uArrayList.size()>i){
           //System.out.println()("Amount "+i);
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

                        if (wp.getName().toLowerCase().contains(charString.toLowerCase())||checkMatchFrName(charString))
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
                    /**A future feature*/
                    return ui.getUsername().toLowerCase().contains(charString.toLowerCase()) ||
                            ui.getEmail().toLowerCase().contains(charString.toLowerCase()) ||
                            ui.getFirstName().toLowerCase().contains(charString.toLowerCase()) ||
                            ui.getLastName().toLowerCase().contains(charString.toLowerCase()) ||
                            ui.getGender().toLowerCase().contains(charString.toLowerCase());


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
        //System.out.println()(imgUrl);
        URL url = null;
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.gym_small);
        if(imgUrl!=null) {
            try {try {
                url = new URL(imgUrl);
            } catch (MalformedURLException e) {
                //System.out.println()("The URL is not valid.");
                //System.out.println()(e.getMessage());
            }


                int SDK_INT = android.os.Build.VERSION.SDK_INT;

                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    assert url != null;
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
            settings(wplan,useBtn,alertDialog);
       // alertDialog.dismiss();
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
    public void settings(WorkoutPlan wp, View v, android.support.v7.app.AlertDialog dialog)
    {
        View popupContentView = LayoutInflater.from(context).inflate(R.layout.use_plan, null);
        Button yes,no,date,time1,time2;
        yes= popupContentView.findViewById(R.id.button10);
        no= popupContentView.findViewById(R.id.signout_cancelBtn);
        date=popupContentView.findViewById(R.id.button7);
        time1=popupContentView.findViewById(R.id.button8);
        time2=popupContentView.findViewById(R.id.button9);
        TextView dateView=popupContentView.findViewById(R.id.textView42);
        TextView stime=popupContentView.findViewById(R.id.textView43);
        TextView etime=popupContentView.findViewById(R.id.textView44);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        date.setOnClickListener(e->{datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        try {
                            Date d = new SimpleDateFormat("dd/mm/yyyy").parse("01/01/2018");
                            view.setMinDate(d.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        day = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        //System.out.println()(day);
                        //dateView.setText(day + " " + dayTime);
                        //  dayTimeToLong(day+" "+dayTime);
                        dateView.setText(day);

                    }
                }, mYear, mMonth, mDay);datePickerDialog.show();});

        time1.setOnClickListener(e->{TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        dayTime = hourOfDay + ":" + minute;
                        //System.out.println()(dayTime);
                        //dateView.setText(day + " " + dayTime);
                        //dayTimeToLong(day+" "+dayTime);

                        stime.setText(dayTime);


                    }
                }, mHour, mMinute, false);
            timePickerDialog.show();});

        time2.setOnClickListener(e->{timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    final static int RQS_1 = 1;
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        dayTime = hourOfDay + ":" + minute;
                        //System.out.println()(dayTime);

                        etime.setText(dayTime);

                    }
                }, mHour, mMinute, false);
            timePickerDialog.show();});






        stime.setText(wp.getStartTime());
        etime.setText(wp.getEndTime());
        //System.out.println()("@@wp.getDate()");
        dateView.setText(wp.getDate());



        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(popupContentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_sign_out);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(v,1,-1);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupWindow.update();
        yes.setOnClickListener(e-> {
            wp.setDate(dateView.getText().toString().trim());
            wp.setStartTime(stime.getText().toString().trim());
            wp.setEndTime(etime.getText().toString().trim());
            FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .add(wp)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(context,"Add "+wp.getName()+" To Your Calendar",Toast.LENGTH_SHORT).show();

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
            dialog.dismiss();
            popupWindow.dismiss();

        });

        // Show popup window offset 1,1 to smsBtton.
        // popupWindow.showAsDropDown(, 1, 1);



    }
    private int mYear,mMonth,mDay;
    TimePickerDialog timePickerDialog;
    private Long convertFromTimeToLong(String date) {
        long milliseconds = System.currentTimeMillis();
        //System.out.println()("system Time: " + milliseconds);
        SimpleDateFormat f = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
            //System.out.println()("Date Time: " + milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
}


