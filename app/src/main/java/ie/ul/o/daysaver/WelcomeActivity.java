package ie.ul.o.daysaver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class WelcomeActivity extends FullscreenActivity implements gym_info.OnFragmentInteractionListener {
    private Bitmap[]hbot;
    private float x,y;
    private final int FRAME_W=358;
    private final int FRAME_H=354;
    private final int NO_FRAMES=44;
    protected ScrollView pageTwo;

    private int checkCount=0;
    private  final int COUNT_X=4;
    private final int COUNT_Y=11;
    private final int FRAME_DURATION=300;
    private final int SCALE_FACTOR=1;
    private ImageView img;

    private  final Context context=this;

   // Sprite sprite;
  //  private OurView sv;

    private TextView usernameText;
    private Button createUsername,start,skip;
    ImageButton gym,social,study,chill,meeting,timetable;
    private ProgressDialog progressDialog;
    private LinearLayout usernamePage,welcomePage;
    private CheckBox gymBox, studyBox, meetingBox, socialBox,chillBox,timetableBox;
    private TextView welcomeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
       // hbot= BitmapFactory.decodeResource(getResources(),R.drawable.hbot_waving_spritesheet);
       // sv=(OurView) findViewById(R.id.surfaceView);


        start= findViewById(R.id.startButton);
        skip= findViewById(R.id.skip);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.saving_username));
        welcomeDialog= findViewById(R.id.welcomeDialog);
        welcomeDialog.setText(getString(R.string.welcome, this.username));

        gymBox= findViewById(R.id.selectGym);
        studyBox= findViewById(R.id.selectStudy);
        meetingBox= findViewById(R.id.selectMeeting);
        socialBox= findViewById(R.id.selectSocial);
        chillBox= findViewById(R.id.selectChill);
        timetableBox= findViewById(R.id.selectTimetable);

        pageTwo= findViewById(R.id.PageTwo);

        welcomePage= findViewById(R.id.WelcomPage);

        img= findViewById(R.id.HbotFrame);
        gym= findViewById(R.id.gymBtn);
        social= findViewById(R.id.social);
        study= findViewById(R.id.study);
        chill= findViewById(R.id.chill);
        meeting= findViewById(R.id.meeting);
        timetable= findViewById(R.id.timetable);


        gym.setOnClickListener(e->{showGym_info();});
        social.setOnClickListener(e->{showSocial_info();});





    }

    private void showSocial_info() {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.social_info,null);
        Button default_si= dialogView.findViewById(R.id.skip);
        Button si_setupnow= dialogView.findViewById(R.id.socail_addEvent);
        si_setupnow.setOnClickListener(e->{
            startActivity(new Intent("ie.ul.o.daysaver.GymActivity"));});

        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
        default_si.setOnClickListener(e->{alertDialog.hide();});
    }

    private void AnimateHbot() {
        Bitmap hbotMap= BitmapFactory.decodeResource(getResources(), R.drawable.hbot_waving_spritesheet);
        if(hbotMap!=null)
        {
            hbot=new Bitmap[NO_FRAMES];
            int currentFrame=0;


                    hbot[0]=Bitmap.createBitmap(hbotMap,96,6,FRAME_W,FRAME_H);
                    hbot[1]=Bitmap.createBitmap(hbotMap,424,6,FRAME_W,FRAME_H);
                    hbot[2]=Bitmap.createBitmap(hbotMap,772,6,FRAME_W,FRAME_H);
                    hbot[3]=Bitmap.createBitmap(hbotMap,1138,6,FRAME_W,FRAME_H);

                    hbot[4]=Bitmap.createBitmap(hbotMap,96,370,FRAME_W,FRAME_H);
                    hbot[5]=Bitmap.createBitmap(hbotMap,424,370,FRAME_W,FRAME_H);
                    hbot[6]=Bitmap.createBitmap(hbotMap,772,370,FRAME_W,FRAME_H);
                    hbot[7]=Bitmap.createBitmap(hbotMap,1138,370,FRAME_W,FRAME_H);

                    hbot[8]=Bitmap.createBitmap(hbotMap,96,732,FRAME_W,FRAME_H);
                    hbot[9]=Bitmap.createBitmap(hbotMap,424,732,FRAME_W,FRAME_H);
                    hbot[10]=Bitmap.createBitmap(hbotMap,772,732,FRAME_W,FRAME_H);
                    hbot[11]=Bitmap.createBitmap(hbotMap,1138,732,FRAME_W,FRAME_H);

                    hbot[12]=Bitmap.createBitmap(hbotMap,96,1094,FRAME_W,FRAME_H);
                    hbot[13]=Bitmap.createBitmap(hbotMap,424,1094,FRAME_W,FRAME_H);
                    hbot[14]=Bitmap.createBitmap(hbotMap,772,1094,FRAME_W,FRAME_H);
                    hbot[15]=Bitmap.createBitmap(hbotMap,1138,1094,FRAME_W,FRAME_H);

                    hbot[16]=Bitmap.createBitmap(hbotMap,96,1460,FRAME_W,FRAME_H);
                    hbot[17]=Bitmap.createBitmap(hbotMap,424,1460,FRAME_W,FRAME_H);
                    hbot[18]=Bitmap.createBitmap(hbotMap,772,1460,FRAME_W,FRAME_H);
                    hbot[19]=Bitmap.createBitmap(hbotMap,1138,1460,FRAME_W,FRAME_H);

                    hbot[20]=Bitmap.createBitmap(hbotMap,96,1836,FRAME_W,FRAME_H);
                    hbot[21]=Bitmap.createBitmap(hbotMap,424,1836,FRAME_W,FRAME_H);
                    hbot[22]=Bitmap.createBitmap(hbotMap,772,1836,FRAME_W,FRAME_H);
                    hbot[23]=Bitmap.createBitmap(hbotMap,1138,1836,FRAME_W,FRAME_H);

                    hbot[24]=Bitmap.createBitmap(hbotMap,96,2192,FRAME_W,FRAME_H);
                    hbot[25]=Bitmap.createBitmap(hbotMap,424,2192,FRAME_W,FRAME_H);
                    hbot[26]=Bitmap.createBitmap(hbotMap,772,2192,FRAME_W,FRAME_H);
                    hbot[27]=Bitmap.createBitmap(hbotMap,1138,2192,FRAME_W,FRAME_H);

                    hbot[28]=Bitmap.createBitmap(hbotMap,96,2552,FRAME_W,FRAME_H);
                    hbot[29]=Bitmap.createBitmap(hbotMap,424,2552,FRAME_W,FRAME_H);
                    hbot[30]=Bitmap.createBitmap(hbotMap,772,2552,FRAME_W,FRAME_H);
                    hbot[31]=Bitmap.createBitmap(hbotMap,1138,2552,FRAME_W,FRAME_H);

                    hbot[32]=Bitmap.createBitmap(hbotMap,96,2912,FRAME_W,FRAME_H);
                    hbot[33]=Bitmap.createBitmap(hbotMap,424,2912,FRAME_W,FRAME_H);
                    hbot[34]=Bitmap.createBitmap(hbotMap,772,2912,FRAME_W,FRAME_H);
                    hbot[35]=Bitmap.createBitmap(hbotMap,1138,2912,FRAME_W,FRAME_H);

                    hbot[37]=Bitmap.createBitmap(hbotMap,96,3272,FRAME_W,FRAME_H);
                    hbot[38]=Bitmap.createBitmap(hbotMap,424,3272,FRAME_W,FRAME_H);
                    hbot[39]=Bitmap.createBitmap(hbotMap,772,3272,FRAME_W,FRAME_H);
                    hbot[40]=Bitmap.createBitmap(hbotMap,1138,3272,FRAME_W,FRAME_H);

                    hbot[41]=Bitmap.createBitmap(hbotMap,96,3642,FRAME_W,FRAME_H);
                    hbot[42]=Bitmap.createBitmap(hbotMap,424,3642,FRAME_W,FRAME_H);
                    hbot[43]=Bitmap.createBitmap(hbotMap,772,3642,FRAME_W,FRAME_H);


                  //hbot[currentFrame]=Bitmap.createScaledBitmap(hbot[currentFrame],FRAME_W*SCALE_FACTOR,FRAME_H*SCALE_FACTOR,true);


            final AnimationDrawable animation=new AnimationDrawable();
            animation.setOneShot(false);
            for(int i=0;i<NO_FRAMES;i++)
            {
                animation.addFrame(new BitmapDrawable(getResources(),hbot[i]),FRAME_DURATION);
            }
            if(Build.VERSION.SDK_INT<16)
            {
                img.setBackgroundDrawable(animation);

            }
            else img.setBackground(animation);
            img.post(new Runnable() {
                         @Override
                         public void run() {
                             animation.start();
                         }


            });

        }
    }


    public void showProgress()
    {
        try {
            // Simulate network access.
            Thread.sleep(900);
        } catch (InterruptedException e) {

        }
        progressDialog.show();
    }



    public void onStartIntro(View view) {

        /**sTART tUTORIAL*/
      welcomePage.setVisibility(View.GONE);
      pageTwo.setVisibility(View.VISIBLE);
    }

    public void onSkip(View view) {
        finish();

    }
    public  void showGym_info()
    {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.gym_information,null);/**Crash here*/
        Button default_gi= dialogView.findViewById(R.id.gi_Default);
        Button gi_setupnow= dialogView.findViewById(R.id.gi_setupnow);
        gi_setupnow.setOnClickListener(e->{startActivity(new Intent("ie.ul.o.daysaver.GymActivity"));});

        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
        default_gi.setOnClickListener(e->{alertDialog.hide();});
    }


    public void onChkChill(View view) {
        if(chillBox.isChecked())
        {
            if(checkCount>6)
                checkCount++;
           toastMessage(checkCount+"/6");

        }
        else
        {
            if(checkCount<1)
                checkCount--;
            toastMessage(checkCount+"/6");
        }
    }

    public void onChkTt(View view) {
    }

    public void onChkSoc(View view) {
    }

    public void onChkMtns(View view) {
    }

    public void onChkStudy(View view) {
    }

    public void onChkGym(View view) {
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

