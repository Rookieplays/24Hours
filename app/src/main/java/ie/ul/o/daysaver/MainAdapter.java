package ie.ul.o.daysaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ollie on 26/03/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private String[] mDataset,mDescSets,mStartTime,mEndTime,mColor;
    private Bitmap[] mImgSets;
    private  LayoutInflater inflater;
    public Context context;
    static View itView;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public TextView mStartView;
        public TextView mEndView;
        public CardView cardview;
        public ViewHolder(View v)
        {
            super(v);
            mTextView=(TextView)v.findViewById(R.id.eventName);
            mEndView=(TextView)v.findViewById(R.id.met);
            mStartView=v.findViewById(R.id.mst);
            cardview=v.findViewById(R.id.cardViewmain);

        }
    }
    public MainAdapter(Context context, ArrayList<ArrayList<String>>myDataset)
    {
this.context=context;
        inflater=LayoutInflater.from(context);
        if(myDataset.size()==0)
        {
            mDataset=new String[]{"Empty List"};
            mDescSets=new String[]{"There was a Problem loading list..."};
            mStartTime=new String[]{"00:00"};
            mEndTime=new String[]{"01:00"};
        }
        else{
            mDataset=new String[myDataset.get(0).size()];
            mDescSets=new String[myDataset.get(0).size()];
            mStartTime=new String[myDataset.get(0).size()];
            mEndTime=new String[myDataset.get(0).size()];
            mColor=new String[myDataset.get(0).size()];
            for(int i=0;i<myDataset.get(0).size();i++)
            {
                mDataset[i]=myDataset.get(0).get(i);
                mDescSets[i]=myDataset.get(1).get(i);
                mStartTime[i]=myDataset.get(2).get(i);
                mEndTime[i]=myDataset.get(3).get(i);
                mColor[i]=myDataset.get(4).get(i);
            }
           // System.out.println("£$TG£%GG%$^UJ%J%Y "+mDataset[0]);
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.event_viewer,parent,false);
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
        holder.mStartView.setText("Start Time: "+mStartTime[position]);
        holder.mEndView.setText("End Time: "+mEndTime[position]);
        holder.cardview.setCardBackgroundColor(Integer.parseInt(mColor[position]));
        String temp[];
        if(holder.mTextView.getText().toString().contains("Study"))
        temp=holder.mEndView.getText().toString().split(":");
        else
            temp=new String[]{"2.00"};
        for (String s:temp)
        System.out.println("W☺W"+s);
        holder.itemView.setOnLongClickListener(e->{showconfirmationBox2(context,mDataset[position],mDescSets[position],Double.parseDouble(temp[temp.length-1].trim()));return true;});
        itView=holder.itemView;
        new Thread(new Runnable() {
            @Override
            public void run () {

                if (new SimpleDateFormat("HH:mm").format(System.currentTimeMillis()).equalsIgnoreCase(mStartTime[position])) {
                    while (!(new SimpleDateFormat("HH:mm").format(System.currentTimeMillis()).equalsIgnoreCase(mEndTime[position]))) {
                        startAnimation(holder.itemView, true);

                    }
                }
                else System.out.println(" ,NO II CANT ");
            }
        }).start();

    }
    private void startAnimation(View v,boolean start)
    {



        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",2f),
                PropertyValuesHolder.ofFloat("scaleX",2f));

        grow.setDuration(200);

        grow.setRepeatMode(ObjectAnimator.REVERSE);
        grow.setRepeatCount(ObjectAnimator.INFINITE);
        if(start==true)
            grow.start();
        else grow.end();
    }
    public void showconfirmationBox2(Context context,String title,String msg,double dur)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(msg).setTitle(title);

        builder.setNegativeButton(R.string.thanks, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                adialog.dismiss();

            }
        });
        if(title.equalsIgnoreCase("Study"))
        {
            builder.setPositiveButton(R.string.setTimer, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface adialog, int i) {
                    //adialog.dismiss();
                    showTimerMenu(dur);

                }
            });
        }
        AlertDialog adialog=builder.create();
        adialog.show();

    }

    private void showTimerMenu(double dur) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater =LayoutInflater.from(context);//getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timer_dialog, null);
        dialogBuilder.setView(dialogView);

        ProgressBar pb=dialogView.findViewById(R.id.progressBar2);
        TextView timer=dialogView.findViewById(R.id.timerText);
        Button startTime=dialogView.findViewById(R.id.startTimer);
        Button resetTimer=dialogView.findViewById(R.id.resetTimer);
        Button stopb=dialogView.findViewById(R.id.stopTimer);
        RelativeLayout bg=dialogView.findViewById(R.id.bg);


        startTime.setOnClickListener(e-> {


            System.out.println("***click\nPlay: "+play);
            AnimatorSet as=startAnimation2(timer);
            if (!play) {play=true;
                as.removeAllListeners();
                as.cancel();
                //as.pause();
                startTimer(true,timer,pb,dur,bg);
                stop=true;
                startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_pause));

            } else {play=false;
                as.start();
                stop=false;

                startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_play));

            }
        });
        resetTimer.setOnClickListener(e->{
            timer.setText(0.00+"");
            pb.setProgress(0);
            startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_pause));
            stop=true;
            startTimer(false,timer,pb,dur,bg);



        });
        stopb.setOnClickListener(e->{
            stop=true;
        });
        AlertDialog adialog=dialogBuilder.create();
        adialog.show();

    }
    boolean play=false;
    boolean stop=false;

    private void startTimer(boolean start, TextView tv, ProgressBar pb, Double dur, RelativeLayout bg) {
        long max=(long)(dur*60*60*1000);
        CountDownTimer cdt=new CountDownTimer(max,1000) {

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onTick(long milliUntilFinish) {
                if(!stop) {
                    long nowTime = milliUntilFinish;

                    int rst = (int) (100 - ((nowTime * 100) / max));
                    pb.setProgress((rst));
                    System.out.println(rst);
                    //  System.out.println(milliUntilFinish);

                    int hr = (int) (TimeUnit.MILLISECONDS.toHours(milliUntilFinish) % 24);
                    int min = (int) (TimeUnit.MILLISECONDS.toMinutes(milliUntilFinish) % 60);
                    int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(milliUntilFinish) % 60);
                    //System.out.println(hr+":"+min+":"+sec);
                    tv.setText(hr + ":" + min + ":" + sec);
                    if (rst >= 50) {
                        pb.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

                    } else if (rst >= 30 && rst < 50) {
                        pb.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.lava_primary)));
                    } else if (rst >= 10 && rst < 80) {
                        pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
                    }
                }


            }

            public void onFinish() {
                //start Alarm
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(context, notification);
                r.play();
                AnimatorSet as = startAnimation(tv);
                while (!stop) {
                    bg.setBackgroundColor(Color.RED);
                    as.start();
                    r.play();
                }
                if (stop == true) {
                    as.cancel();
                    r.stop();

                }
            }
        };


        if (start)
        {

            cdt.start();

        }else {
            System.out.println("CANCELinggg");
            cdt.cancel();
        }




    }
    public  AnimatorSet startAnimation(View v)
    {

        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1.5f),
                PropertyValuesHolder.ofFloat("scaleX",1.5f));

        grow.setDuration(1000);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1f),
                PropertyValuesHolder.ofFloat("scaleX",1f));
        grow.setRepeatMode(ObjectAnimator.RESTART);
        grow.setRepeatCount(ObjectAnimator.INFINITE);

        AnimatorSet animatorSet2=new AnimatorSet();
        animatorSet2.playSequentially(grow,shrink);
        animatorSet2.setDuration(1000);
       return animatorSet2;
    }
    public AnimatorSet startAnimation2(View v)
    {

        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("alpha",1f,0f));

        grow.setDuration(600);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("alpha",0f,1f));
        grow.setRepeatMode(ObjectAnimator.RESTART);
        grow.setRepeatCount(ObjectAnimator.INFINITE);
        AnimatorSet animatorSet2=new AnimatorSet();
        animatorSet2.playSequentially(grow,shrink);
        animatorSet2.setDuration(1000);

        return  animatorSet2;
    }

    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }


}
