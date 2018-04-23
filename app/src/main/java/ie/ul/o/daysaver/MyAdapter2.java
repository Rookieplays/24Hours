package ie.ul.o.daysaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ollie on 30/03/2018.
 */

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    private String[] mDataset,mDescSets;
    private Bitmap[] mImgSets;
    private  LayoutInflater inflater;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public ImageView mImageView;
        public TextView aTextView;
        public ImageButton deleteBtn,editBtn;
        public ViewHolder(View v)
        {
            super(v);
            mTextView= v.findViewById(R.id.wrkout_title);
            aTextView= v.findViewById(R.id.wrkDesc);
            mImageView= v.findViewById(R.id.wrkIcon);
           // deleteBtn=v.findViewById(R.id.deleteBtn);
            editBtn=v.findViewById(R.id.editBtn);


        }
    }
    public MyAdapter2(Context context, ArrayList<ArrayList<String>>myDataset, ArrayList<Bitmap>images)
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
            //System.out.println()("£$TG£%GG%$^UJ%J%Y "+mDataset[0]);
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.add_plan_view_list,parent,false);
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
        holder.deleteBtn.setOnClickListener(e->{
            deleteItem(position);
        });
    }
    void deleteItem(int index) {
        ArrayList<String >temp=new ArrayList<>();
        for(int i=0;i<mDataset.length;i++)
        {
                if(mDataset[i]!=null)
                {
                    temp.add(mDataset[i]);
                }
        }
        String []temp2=new String[temp.size()];
        int j=0;
        for(String s:temp)
        {
            temp2[j]=s;j++;
        }
        mDataset=temp2;
        notifyItemRemoved(index);
    }
    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }

}
