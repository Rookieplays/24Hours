package ie.ul.o.daysaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ollie on 26/03/2018.
 */

public class AdapterA extends RecyclerView.Adapter<AdapterA.ViewHolder> {
    private String[] mDataset,mDescSets,mSets,mReps;

    private Context context;
    private List<Workout> workoutList=new ArrayList<>();
    private Bitmap[] mImgSets;
    private  LayoutInflater inflater;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public ImageView mImageView;
        public ImageButton edit, delete;
        public TextView aTextView;
        public TextView setsView;
        public TextView repsView;
        public RelativeLayout viewBackground, viewForeground;
        public ViewHolder(View v)
        {
            super(v);
            mTextView=(TextView)v.findViewById(R.id.wrkout_title2);
            aTextView=(TextView)v.findViewById(R.id.wrkDesc);
            setsView=(TextView)v.findViewById(R.id.setsDisplay);
            repsView=(TextView)v.findViewById(R.id.repsDiaply);
            mImageView=(ImageView)v.findViewById(R.id.wrkIcon2);
            edit=v.findViewById(R.id.editBtn);
           // delete=v.findViewById(R.id.deleteBtn);
            viewBackground = v.findViewById(R.id.view_background);
            viewForeground = v.findViewById(R.id.view_foreground);
        }
    }
    public AdapterA(Context context, List<Workout>workoutList)
    {
            this.workoutList=workoutList;
            this.context=context;
        //inflater=LayoutInflater.from(context);



    }
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_plan_view_list, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mTextView.setText(workoutList.get(position).getName());
        holder.mImageView.setImageBitmap(covertLinkToImg(workoutList.get(position).getImg()));
        holder.setsView.setText(workoutList.get(position).getSets()+"");
        //holder.mImageView.setImageBitmap(mImgSets[position]);
        holder.repsView.setText(workoutList.get(position).getReps()+"");
        holder.edit.setOnClickListener(e->{
           //TODO:dialogbox
        });
    }
    public void removeItem(int position) {
        workoutList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Workout w, int position) {
        workoutList.add(position, w);
        // notify item added by position
        notifyItemInserted(position);
    }
    @Override
    public int getItemCount()
    {
        return workoutList.size();
    }

}
