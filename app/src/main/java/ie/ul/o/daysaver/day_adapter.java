package ie.ul.o.daysaver;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ali on 11/04/2018.
 */

  public class day_adapter extends RecyclerView.Adapter<ie.ul.o.daysaver.day_adapter.ViewHolder> {
        private String[] mDataset,mDescSets;
        private Bitmap[] mImgSets;
        private LayoutInflater inflater;
        public ArrayList<Study>studies=new ArrayList<>();
        private Context context; public RecyclerView recyclerView;
        private boolean done=false;



        public static class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView mTextView;
            private Button done;
            private EditText dur;
            private LinearLayout layer1, layer2;

           
            public ViewHolder(View v)
            {
                super(v);
                mTextView= v.findViewById(R.id.dayLabel);
                done=v.findViewById(R.id.doneDay);
                dur=v.findViewById(R.id.dur);
                layer1=v.findViewById(R.id.layer1);//resetLayer
                layer2=v.findViewById(R.id.layer2);//mainLayer

               
              
            }
        }
        private static ArrayList<String>DAYS;
        private ArrayList<String>sub=new ArrayList<>();
        public day_adapter(Context context, ArrayList<String>days, ArrayList<String>subs)
        {
            sub=subs;
            DAYS=days;
            this.context=context;
            inflater=LayoutInflater.from(context);
            if(days.size()==0)
            {
                mDataset=new String[]{"Empty List"};
                //mDescSets=new String[]{"There was a Problem loading list..."};
              //  mImgSets=new Bitmap[]{null};
            }
            else{
                mDataset=new String[days.size()];
                for(int i=0;i<days.size();i++)
                {
                    mDataset[i]=days.get(i);
                    studies.add(new Study("Study Plan For: "+days.get(i),days.get(i),subjects));
                   // mDescSets[i]=days.get(1).get(i);
                   // mImgSets[i]=images.get(i);
                }

            }


        }
        private static ArrayList<Study>studs=new ArrayList<>();
         static ArrayList<Study>GETPLANFOREVERYDAYSELECTED()
        {
            return studs;
        }
    private void startAnimation(View v)
    {

        v.setAlpha(0.3f);

        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",0.5f),
                PropertyValuesHolder.ofFloat("scaleX",0.5f));

        shrink.setDuration(800);

       // shrink.setRepeatMode(ObjectAnimator.REVERSE);
       // shrink.setRepeatCount(ObjectAnimator.INFINITE);
        MediaPlayer ring= MediaPlayer.create(context,R.raw.pop);
        ring.start();
        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });
       // ring.prepareAsync();
            shrink.start();

    }

        @NonNull
        @Override
        public ie.ul.o.daysaver.day_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.day_adapter_res,parent,false);
            recyclerView=view.findViewById(R.id.subjectPicker);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            ie.ul.o.daysaver.day_adapter.ViewHolder holder=new ie.ul.o.daysaver.day_adapter.ViewHolder(view);
            return holder;

        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
        private subjects_for_day sfd;

        @Override
        public void onBindViewHolder(ie.ul.o.daysaver.day_adapter.ViewHolder holder, int position)
        {
            holder.layer1.setVisibility(View.GONE);
            holder.layer2.setVisibility(View.VISIBLE);
            holder.mTextView.setText(mDataset[position]);
            studies.get(position).setDuration(Double.parseDouble(holder.dur.getText().toString()));
            holder.dur.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(TextUtils.isEmpty(holder.dur.getText().toString()))
                    {
                        holder.dur.setError(context.getString(R.string.emptyField));
                        holder.done.setEnabled(false);

                    }
                    else
                    {
                        holder.dur.setError(null);
                        holder.done.setEnabled(true);

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

         sfd=new subjects_for_day(context,sub,holder.mTextView.getText().toString());
         recyclerView.setAdapter(sfd);
            //holder.aTextView.setText(mDescSets[position]);
          /*  if(!studies.isEmpty())
            {
                holder.done.setBackgroundColor(context.getResources().getColor(R.color.forest_dark));
                holder.done.setEnabled(false);
            }
            else
            {
                holder.done.setBackgroundColor(context.getResources().getColor(R.color.save_green));
                holder.done.setEnabled(true);
            }*/
         /* if(done==true)
          {
                  startAnimation2(holder.itemView);
                  done=false;


          }*/

            holder.done.setOnClickListener(e->{
                studies.get(position).setDuration(Double.parseDouble(holder.dur.getText().toString()));
                if(position==0)
                {
                    subjects=subjects_for_day.mSubs;
                }
                else if(position==1)
                {
                    subjects=subjects_for_day.tSubs;
                }
                else if(position==2)
                {
                    subjects=subjects_for_day.wSubs;
                }
                else if(position==3)
                {
                    subjects=subjects_for_day.thSubs;
                }
                else if(position==4)
                {
                    subjects=subjects_for_day.fSubs;
                }
                else if(position==5)
                {
                    subjects=subjects_for_day.sSubs;
                }
                else if(position==6)
                {
                    subjects=subjects_for_day.sunSub;
                }
                //subjects=subjects_for_day.GETDAYSUBJECTS();
                //System.out.println()("These are the subjects...."+subjects);

                studies.get(position).setSubjects(subjects);
                studs=studies;
                //System.out.println()("These is the Study Plan...."+studies);

                    startAnimation(holder.itemView);
                    //subjects_for_day.ViewHolder.mTextView.setEnabled(false);
                   // holder.done.setEnabled(false);
                  //  holder.dur.setEnabled(false);
                    holder.layer1.setVisibility(View.VISIBLE);
                    holder.layer2.setVisibility(View.GONE);
              //  holder.itemView.setEnabled(false);

            });
            holder.layer1.setOnClickListener(e->showconfirmationBox2(holder,position));


        }

    private void startAnimation2(View v) {
        v.setAlpha(1f);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1f),
                PropertyValuesHolder.ofFloat("scaleX",1f));

        shrink.setDuration(800);

        // shrink.setRepeatMode(ObjectAnimator.REVERSE);
        // shrink.setRepeatCount(ObjectAnimator.INFINITE);
        MediaPlayer ring= MediaPlayer.create(context,R.raw.poof);
        ring.start();
        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });

       // ring.prepareAsync();


        shrink.start();

    }

    private ArrayList<Subjects>subjects=new ArrayList<>();
    public void showconfirmationBox2(ie.ul.o.daysaver.day_adapter.ViewHolder holder,int pos)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(R.string.areyouSure4).setTitle(R.string.beforeThat);

        builder.setPositiveButton(R.string.yeah, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
              

                startAnimation2(holder.itemView);
                subjects_for_day.clearListFor(DAYS.get(pos));
                holder.layer2.setVisibility(View.VISIBLE);
                holder.layer1.setVisibility(View.GONE);


                adialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.nope, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                adialog.dismiss();

            }
        });
        AlertDialog adialog=builder.create();
        adialog.show();

    }
        @Override
        public int getItemCount()
        {
            return mDataset.length;
        }

    }


