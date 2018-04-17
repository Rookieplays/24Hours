package ie.ul.o.daysaver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ollie on 26/03/2018.
 */

public class shoppingListAdapter extends RecyclerView.Adapter<shoppingListAdapter.ViewHolder> {
    private String[] mDataset,mDescSets;
    private Context context;
    static ArrayList<Item>ITEMLISTS;
    private  LayoutInflater inflater;
    private ArrayList<Item> itms=new ArrayList<>();

    public static Collection<? extends Item> getLists() {
        return ITEMLISTS;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
       // public ImageView mImageView;
        public TextView aTextView;
        public RelativeLayout viewBackground, viewForeground;
        public ViewHolder(View v)
        {
            super(v);
            mTextView=v.findViewById(R.id.itemText);
            viewBackground = v.findViewById(R.id.v_background);
            viewForeground = v.findViewById(R.id.v_foreground);
           // aTextView=(TextView)v.findViewById(R.id.wrkDesc);
           // mImageView=(ImageView)v.findViewById(R.id.wrkIcon);

        }
    }
    public shoppingListAdapter(Context context, ArrayList<Item>myDataset)
    {
        this.context=context;
        this.itms=myDataset;
        ITEMLISTS=itms;

        inflater=LayoutInflater.from(context);
        if(myDataset.size()==0)
        {
            mDataset=new String[]{"Empty List"};
            mDescSets=new String[]{"There was a Problem loading list..."};

        }
        else{
            mDataset=new String[myDataset.size()];
            mDescSets=new String[myDataset.size()];

            for(int i=0;i<myDataset.size();i++)
            {
                mDataset[i]=myDataset.get(i).getItemName();
                mDescSets[i]="Entered item: "+myDataset.get(i).getItemName()+"\nAmount: "+myDataset.get(i).getAmount()+"\nPrice: "+myDataset.get(i).getPrice();

            }
            System.out.println("£$TG£%GG%$^UJ%J%Y "+mDataset[0]);
        }



    }
    public void infodialog(String msg,String title)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(msg).setTitle(title);

        AlertDialog adialog=builder.create();
        adialog.show();


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.itemlists,parent,false);
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
       // holder.aTextView.setText(mDescSets[position]);
        holder.mTextView.setOnLongClickListener(e->{
            infodialog(mDescSets[position], holder.mTextView.getText().toString());
            return true;});
    }
    public void removeItem(int position) {
        itms.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Item w, int position) {
        itms.add(position, w);
        // notify item added by position
        notifyItemInserted(position);
    }
    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }

}
