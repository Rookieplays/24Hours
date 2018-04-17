package ie.ul.o.daysaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ollie on 26/03/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset,mDescSets;
    private Bitmap[] mImgSets;
    private  LayoutInflater inflater;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public ImageView mImageView;
        public TextView aTextView;
        public ViewHolder(View v)
        {
            super(v);
            mTextView=(TextView)v.findViewById(R.id.wrkout_title);
            aTextView=(TextView)v.findViewById(R.id.wrkDesc);
            mImageView=(ImageView)v.findViewById(R.id.wrkIcon);

        }
    }
    public MyAdapter(Context context, ArrayList<ArrayList<String>>myDataset, ArrayList<Bitmap>images)
    {

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
        System.out.println("£$TG£%GG%$^UJ%J%Y "+mDataset[0]);
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.gym_workout_row,parent,false);
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
        holder.mTextView.setText(mDataset[position]);
        holder.mImageView.setImageBitmap(mImgSets[position]);
        holder.aTextView.setText(mDescSets[position]);
    }
    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }

}
