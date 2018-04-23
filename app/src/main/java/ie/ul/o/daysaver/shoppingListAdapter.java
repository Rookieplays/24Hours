package ie.ul.o.daysaver;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
        public TextView amountView;
        public ViewHolder(View v)
        {
            super(v);
            amountView=v.findViewById(R.id.amountView);
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
                itms.get(i).setDescription(mDescSets[i]);
            }
            System.out.println("£$TG£%GG%$^UJ%J%Y "+mDataset[0]);
        }



    }

    public void infodialog(String msg,String title,int pos)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(msg).setTitle(title);
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editfield(pos);
            }
        });
        AlertDialog adialog=builder.create();
        adialog.show();


    }
    private void editfield(int pos) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);//getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_item, null);
        dialogBuilder.setView(dialogView);

        EditText name = dialogView.findViewById(R.id.editText3);
        TextView amount = dialogView.findViewById(R.id.textView45);
        SeekBar amountBar = dialogView.findViewById(R.id.sb);
        EditText price = dialogView.findViewById(R.id.editText2);
        Button confirm=dialogView.findViewById(R.id.button11);
        System.out.println(itms.get(pos).getItemName());
        name.setText(itms.get(pos).getItemName());
        amount.setText(itms.get(pos).getAmount()+"");
        amountBar.setProgress(Integer.parseInt(amount.getText().toString()));
        price.setText(itms.get(pos).getAmount()+"");

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    name.setError("Enter a Name please"); confirm.setEnabled(false);

                }
               else{
                    confirm.setEnabled(true);
                    name.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    price.setError("Enter a Price please");
                    confirm.setEnabled(false);
                } else {
                    confirm.setEnabled(true);
                    price.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        amountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                amount.setText(amountBar.getProgress()+"");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        AlertDialog dialog=dialogBuilder.create();
        dialog.show();
        confirm.setOnClickListener(e->{
            itms.get(pos).setAmount(Integer.parseInt(amount.getText().toString()));
            itms.get(pos).setPrice(Double.parseDouble(price.getText().toString()));
            itms.get(pos).setItemName(name.getText().toString());
            notifyDataSetChanged();
            dialog.dismiss();

        });




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
        holder.mTextView.setText(itms.get(position).getItemName());
        holder.amountView.setText("x"+itms.get(position).getAmount());
       // holder.aTextView.setText(mDescSets[position]);
        holder.itemView.setOnLongClickListener(e->{
            infodialog(itms.get(position).getDescription(), holder.mTextView.getText().toString(),position);
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
        return itms.size();
    }

}
