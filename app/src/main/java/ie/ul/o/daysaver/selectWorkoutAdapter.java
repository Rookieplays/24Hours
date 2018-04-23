package ie.ul.o.daysaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ollie on 26/03/2018.
 */

public class selectWorkoutAdapter extends RecyclerView.Adapter<selectWorkoutAdapter.ViewHolder> {
    private String[] mDataset,mDescSets;
    private Bitmap[] mImgSets;
    private  LayoutInflater inflater;
    private Context context;
    private static List<Workout> workoutFull=new ArrayList<>();
    private List<Workout>wrkouts=new ArrayList<>();
    private Workout wrkout;
    private String MUSCLEGROUP;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public ImageView mImageView;
        public CheckBox aCBView;
        public ImageButton infoBtn;
        public TextInputEditText sets;
        public TextInputEditText reps;

        public ViewHolder(View v)
        {
            super(v);
           //create dialogbox to show info //mTextView=(TextView)v.findViewById(R.id.wrkout_title);
            aCBView=v.findViewById(R.id.wrkout_name);
            mImageView= v.findViewById(R.id.wrkoutImg);
            infoBtn=v.findViewById(R.id.infoBtn);
            sets=v.findViewById(R.id.setsField);
            reps=v.findViewById(R.id.repsField);


        }
    }
    public selectWorkoutAdapter(Context context, String musleGroup,ArrayList<ArrayList<String>>myDataset, ArrayList<Bitmap>images)
    {
        this.context=context;
        MUSCLEGROUP=musleGroup;
        inflater=LayoutInflater.from(context);
        if(myDataset.size()==0||images.size()==0)
        {
            mDataset=new String[]{"Empty List"};
            mDescSets=new String[]{"There was a Problem loading list..."};
            mImgSets=new Bitmap[]{null};
        }
        else{
            mDataset=new String[myDataset.get(0).size()];
            mDescSets=new String[myDataset.get(0).size()];
            mImgSets=new Bitmap[myDataset.get(0).size()];
            for(int i=0;i<myDataset.get(0).size();i++)
            {
                mDataset[i]=(i+1)+". "+myDataset.get(0).get(i);
                mDescSets[i]=myDataset.get(1).get(i);
                mImgSets[i]=images.get(i);
            }
            //System.out.println()("£$TG£%GG%$^UJ%J%Y "+mDataset[0]);
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.gymactivity_list_content,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.aCBView.setText(mDataset[position]);
        holder.mImageView.setImageBitmap(mImgSets[position]);
        holder.infoBtn.setOnClickListener(e->{
            infodialog(mDescSets[position],mDataset[position]);
        });
       // wrkouts=AddNewWorkout.WORKOUTS;
        try{
        if(wrkout!=null)
        {
            for(Workout w:wrkouts)
            {
                //System.out.println()("Workout: "+w.getName()+" VS "+"New: "+ holder.aCBView.getText().toString());
                if(holder.aCBView.getText().toString().equalsIgnoreCase(position+". "+w.getName()))
                    holder.aCBView.setChecked(true);
                else
                    holder.aCBView.setChecked(false);
            }
        }}catch(NullPointerException n){Log.w("••••••╝╣",n.getMessage());}
        holder.aCBView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try{
                    holder.sets.setError(null);
                    if(!TextUtils.isEmpty(holder.sets.getText().toString())&&!TextUtils.isEmpty(holder.reps.getText().toString()))

                wrkout=gatherInfo(mDataset[position],mImgSets[position].toString(),mDescSets[position],Integer.parseInt(holder.sets.getText().toString()),Integer.parseInt(holder.reps.getText().toString()));
                    else {
                        holder.sets.setError(context.getString(R.string.seterror));
                        holder.reps.setError(context.getString(R.string.repserror));

                    }
                if(holder.aCBView.isChecked())
                {
                    //System.out.println()("Chossen workout is: "+ wrkout.getName());
                    String tempSplit[]=wrkout.getName().split("\\.");
                    //System.out.println()(tempSplit[0]+"\n"+tempSplit[1]);
                    wrkout.setName(tempSplit[1].trim());
                    //System.out.println()(wrkout.getName());
                                   wrkouts.add(wrkout);
                                   //System.out.println()(wrkout);

                } else {
                    refreshWorkout(wrkout.getName());
                }
                workoutFull=wrkouts;}
                catch (NullPointerException n){
                    Log.w("Errrrrrrrrrr",n.getMessage());}
            }
        });
        holder.itemView.setOnClickListener(e->{
        System.out.print("**Click**\n Retrivimng details...\n");
        if(holder.aCBView.isChecked())
        holder.aCBView.setChecked(false);
        else holder.aCBView.setChecked(true);
    });

    }
    public static void setWORKOUTS(List<Workout>W){
        //System.out.println()("///////////"+W);
        workoutFull=W;
    }

    public static List getWorkoutMade() {
        //System.out.println()("+++++"+workoutFull);
        return workoutFull;
    }
    public void refreshWorkout(String s)
    {
        //System.out.println()("Checking if: "+s+" is among: "+wrkouts);
        for(Workout w: wrkouts)
            {
                if(w.getName().equals(s))
                {
                    wrkouts.remove(w);

                }


            }

    }

    private Workout gatherInfo(String name, String img, String desc, int set, int rep) {
        //System.out.println()(new Workout(MUSCLEGROUP,name,desc,img,set,rep));
        return new Workout(MUSCLEGROUP,name,desc,img,set,rep);
    }

    public void infodialog(String msg,String title)
    {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setMessage(msg).setTitle(title);

            AlertDialog adialog=builder.create();
            adialog.show();


    }
    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }

}
