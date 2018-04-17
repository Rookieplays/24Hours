package ie.ul.o.daysaver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ollie on 26/03/2018.
 */

public class SListAdapterAlpha extends RecyclerView.Adapter<SListAdapterAlpha.ViewHolder> {
    private String[] mDataset,mDescSets;
    private ArrayList<ShoppingList> shoppingLists=new ArrayList<>();
    private Context context;
    private  LayoutInflater inflater;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        // public ImageView mImageView;
        public TextView aTextView;
        public ViewHolder(View v)
        {
            super(v);
            mTextView=v.findViewById(R.id.shoppingLists);
            // aTextView=(TextView)v.findViewById(R.id.wrkDesc);
            // mImageView=(ImageView)v.findViewById(R.id.wrkIcon);

        }
    }
    public SListAdapterAlpha(Context context, ArrayList<ShoppingList>myDataset)
    {
        this.context=context;
        this.shoppingLists=myDataset;
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
                mDataset[i]=myDataset.get(i).getName();
                for(Item it:myDataset.get(i).getItems())
                mDescSets[i]="Entered item: "+it.getItemName()+" into "+myDataset.get(i).getName()+"\nAmount: "+it.getAmount()+"\nPrice: "+it.getPrice()+"\nOn "+it.getTimeCreated();

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
        View view=inflater.inflate(R.layout.shopping_list_as_created,parent,false);
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
        holder.aTextView.setText(mDescSets[position]);
        //holder.mTextView.setOnLongClickListener(e->{
           // infodialog(mDescSets[position], holder.mTextView.getText().toString());
            //return true;});
    }
    public void removeItem(int position) {
        shoppingLists.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(ShoppingList s, int position) {
        shoppingLists.add(position, s);
        // notify item added by position
        notifyItemInserted(position);
    }
    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }

}
