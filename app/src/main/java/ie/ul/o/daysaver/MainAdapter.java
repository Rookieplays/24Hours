package ie.ul.o.daysaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
    private RecyclerView rview;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public TextView mStartView;
        public TextView mEndView;
        public CardView cardview;
        public ImageView icon;
        public ViewHolder(View v)
        {
            super(v);
            mTextView= v.findViewById(R.id.eventName);
            mEndView= v.findViewById(R.id.met);
            mStartView=v.findViewById(R.id.mst);
            cardview=v.findViewById(R.id.cardViewmain);
            icon=v.findViewById(R.id.apppic);

        }
    }
    public MainAdapter(Context context, ArrayList<ArrayList<String>>myDataset,ArrayList<WorkoutPlan> wps)
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
           // //System.out.println()("£$TG£%GG%$^UJ%J%Y "+mDataset[0]);
        }
        if(!wps.isEmpty())
        {
            wp.addAll(wps);
        }


    }
    ArrayList<WorkoutPlan> wp=new ArrayList<>();

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
    String temp[];
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mTextView.setText(mDataset[position]);
        int s,g,st,q;
        g=R.drawable.gym_small;
        s=R.drawable.social_small;
        st=R.drawable.study_small;
        q=R.drawable.quick_event;
        Bitmap bitmap= null;
        holder.mStartView.setText("Start Time: "+mStartTime[position]);
        holder.mEndView.setText("End Time: "+mEndTime[position]);
        holder.cardview.setCardBackgroundColor(Integer.parseInt(mColor[position]));

        if(holder.mTextView.getText().toString().contains("Study")){
            bitmap= BitmapFactory.decodeResource(context.getResources(),st);
        temp=holder.mEndView.getText().toString().split(":");
        holder.icon.setImageBitmap(new RoundImage(bitmap).getBitmap());
            temp=new String[]{"2.00"};
            holder.itemView.setOnLongClickListener(e->{showconfirmationBox2(context,mDataset[position],mDescSets[position],Double.parseDouble(temp[temp.length-1].trim()));return true;});

        }
        else if(holder.mTextView.getText().toString().contains("Social")){
            bitmap= BitmapFactory.decodeResource(context.getResources(),s);
            holder.icon.setImageBitmap(new RoundImage(bitmap).getBitmap());
            temp=new String[]{"2.00"};
            holder.itemView.setOnLongClickListener(e->{showconfirmationBox2(context,mDataset[position],mDescSets[position],Double.parseDouble(temp[temp.length-1].trim()));return true;});

        }
        else if(holder.mTextView.getText().toString().contains("Quick")){
            bitmap= BitmapFactory.decodeResource(context.getResources(),q);
            holder.icon.setImageBitmap(new RoundImage(bitmap).getBitmap());
            temp=new String[]{"2.00"};
            holder.itemView.setOnLongClickListener(e->{showconfirmationBox2(context,mDataset[position],mDescSets[position],Double.parseDouble(temp[temp.length-1].trim()));return true;});

        }
        else if(holder.mTextView.getText().toString().contains("Gym")){
            bitmap= BitmapFactory.decodeResource(context.getResources(),g);
            holder.icon.setImageBitmap(new RoundImage(bitmap).getBitmap());
            temp=new String[]{"2.00"};

           // GymSchedule(wp.get(0).getName(),wp.get(0).getWorkouts(),0);


        }
        else{
            //System.out.println()(wp+"!!!!!!!!!");
            holder.itemView.setOnLongClickListener(e->{  GymSchedule(wp.get(position).getName(),wp.get(position).getWorkouts(),position);return  true;});

            temp=new String[]{"2.00"};
        }
        for (String str:temp)
        //System.out.println()("W☺W"+str);
       //    if(!holder.mTextView.getText().toString().contains("Gym"))
         itView=holder.itemView;
        new Thread(new Runnable() {
            @Override
            public void run () {

                if (new SimpleDateFormat("HH:mm").format(System.currentTimeMillis()).equalsIgnoreCase(mStartTime[position])) {
                    while (!(new SimpleDateFormat("HH:mm").format(System.currentTimeMillis()).equalsIgnoreCase(mEndTime[position]))) {
                        startAnimation(holder.itemView, true);

                    }
                }

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
                    //adialog.dismiss();s
                    max=(long)(dur*60*60*1000);
                    mTimeLeftInMillis=max;

                    showTimerMenu(dur);

                }
            });
        }
        AlertDialog adialog=builder.create();
        adialog.show();

    }

    private boolean mTimerRunning;
    private Button startTime,resetTime,stopb;
    ImageButton nM;
    private  TextView timer;
    private ProgressBar pb;

    private void showTimerMenu(double dur) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater =LayoutInflater.from(context);//getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timer_dialog, null);
        dialogBuilder.setView(dialogView);

        pb=dialogView.findViewById(R.id.progressBar2);
       timer=dialogView.findViewById(R.id.timerText);
         startTime=dialogView.findViewById(R.id.startTimer);
         resetTime=dialogView.findViewById(R.id.resetTimer);
         stopb=dialogView.findViewById(R.id.stopTimer);
         stopb.setOnClickListener(e->{startPopUP(timer);});
         anim=startAnimation2(timer);
         nM=dialogView.findViewById(R.id.notifyMe);
        RelativeLayout bg=dialogView.findViewById(R.id.bg);
        nM.setOnClickListener(e->{
            if (notify) {
                setUpNofication("24H.SP","Time's Up",R.drawable.study_small);

            } else {
                disableNotification();
            }
        });

        startTime.setOnClickListener(e->{
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer(dur);
            }
        });
        resetTime.setOnClickListener(e->{
            resetTimer(dur);
        });
        updateCountDownText(dur);
        AlertDialog adialog=dialogBuilder.create();
        adialog.show();

    }
    NotificationManager nnotificationManger;
    boolean isNotificationActive=false;
    int notificationId=24;
   public final String NOTIFICATION_ID="24H_SP";
   /*
public void createnotificationChannel()
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = NOTIFICATION_ID;
        String description = NOTIFICATION_ID+"A";
        int importance;
        importance= NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system
        NotificationManager notificationManager = NotificationManager.from(context);
        notificationManager.createNotificationChannel(channel);
    }
}*/
    private void setUpNofication(String title,String text,int icon) {
        title="Started Timer";
        text="Study Time";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_001");
        Intent ii = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(title);
        bigText.setBigContentTitle(text);
        bigText.setSummaryText("");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());  nM.setBackground(context.getDrawable(android.R.drawable.ic_lock_silent_mode_off));
        notify=false;
        setAlarm(max);

    }

    private void disableNotification() {
        nM.setBackground(context.getDrawable(android.R.drawable.ic_lock_silent_mode));
        notify=true;
        if(isNotificationActive){
            nnotificationManger.cancel(notificationId);
        }
    }


    private  long max;
    private long mTimeLeftInMillis=max;
    private CountDownTimer mCountDownTimer;
    private void startTimer(Double dur) {
        ////System.out.println()("£££ "+mTimeLeftInMillis);
        anim.removeAllListeners();
        anim.cancel();
        timer.setAlpha(1f);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText(dur);
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_play));
                startTime.setVisibility(View.INVISIBLE);
                resetTime.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_pause));
        resetTime.setVisibility(View.INVISIBLE);
    }
    private boolean notify=true;
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
       anim.start();
        startTime.setBackground(context.getDrawable(android.R.drawable.ic_media_play));
        resetTime.setVisibility(View.VISIBLE);
    }
    private AnimatorSet anim;
    private void resetTimer(Double dur) {
         max=(long)(dur*60*60*1000);
        timer.setAlpha(1f);
        mTimeLeftInMillis=max;
        updateCountDownText(dur);

        anim.removeAllListeners();
        anim.cancel();

        resetTime.setVisibility(View.INVISIBLE);
        startTime.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText(Double dur) {
         max=(long)(dur*60*60*1000);
        int rst = (int) (100 - (((max-mTimeLeftInMillis) * 100) / max));
        pb.setProgress((rst));
        ////System.out.println()(rst);
        //  //System.out.println()(milliUntilFinish);

        int hr = (int) (TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis) % 24);
        int min = (int) (TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis) % 60);
        int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis) % 60);
        ////System.out.println()(hr+":"+min+":"+sec);
        timer.setText(hr + ":" + min + ":" + sec);
        if (rst >= 50) {
            pb.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

        } else if (rst >= 30 && rst < 50) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pb.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.lava_primary)));
            }
        } else if (rst >= 10 && rst < 80) {
            pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }
    public void setAlarm(long timeinMillis)
    {
        long alertTime=timeinMillis;
        //System.out.println()(alertTime);
        Intent alertIntent=new Intent(context,AlarmReceiver.class);

        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,alertTime,PendingIntent.getBroadcast(context,1,alertIntent,PendingIntent.FLAG_UPDATE_CURRENT));
    }
private GymSchAdapter mAdapter;
    private ImageView history;

    private void GymSchedule(String heading,ArrayList<Workout>workouts,int index) {
        ArrayList<Workout>temp=workouts;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater =LayoutInflater.from(context);//getLayoutInflater();
        View v = inflater.inflate(R.layout.main_gym_display, null);
        dialogBuilder.setView(v);
        history=v.findViewById(R.id.history);
        rview=v.findViewById(R.id.rcv);
        rview.setLayoutManager(new LinearLayoutManager(context));
        TextView hd=v.findViewById(R.id.textView47);
        Button done=v.findViewById(R.id.button12);
        hd.setText(heading);

        mAdapter=new GymSchAdapter(context,workouts);
        rview.setAdapter(mAdapter);
        AlertDialog adialog=dialogBuilder.create();
        adialog.show();

        done.setOnClickListener(e-> {
             adialog.dismiss();
             workouts.addAll(wp.get(index).getWorkouts());

        });
        history.setOnClickListener(e->{
            adialog.dismiss();
            workouts.clear();
            workouts.addAll(wp.get(index).getWorkouts());
        });
      

    }
    private RecyclerView recyclerView;
    private Button restore;
    private EditText newDur;
    private void startPopUP(View v) {

        View popupContentView = LayoutInflater.from(context).inflate(R.layout.changetimer, null);


        restore=popupContentView.findViewById(R.id.button14);
        newDur=popupContentView.findViewById(R.id.newDur);
        newDur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(TextUtils.isEmpty(charSequence))
            {
                newDur.setError(context.getString(R.string.emptyField));
                restore.setEnabled(false);
            }
            else{
                newDur.setError(null);
                restore.setEnabled(true);
            }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(popupContentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_sign_out);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(v,4,-10);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.update();
        restore.setOnClickListener(e->{
            popupWindow.dismiss();
            resetTimer(Double.parseDouble(newDur.getText().toString()));
        });

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
