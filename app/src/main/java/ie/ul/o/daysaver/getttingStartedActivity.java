package ie.ul.o.daysaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;

public class getttingStartedActivity extends AppCompatActivity {

    private LinearLayout mDotLayout;
    private ViewPager mSlidePager;
    private slideAdapter mslideAdapter;
    private TextView[] mDots;
    private Button prev,next;
    private int mCurrentPage;
    private ImageView profilePic;
    private Button fin;
    private RelativeLayout doneSetup;
    private RelativeLayout welcom;
    private String name="";
    private ProgressBar pb3;
    private TextView tv;
    private Context context=this;
    int i=82,r=255,b=255,g=222;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gettting_started);
        mSlidePager=findViewById(R.id.slideViewPager);
        mDotLayout=findViewById(R.id.dotLayout);
        prev=findViewById(R.id.prevB);
        next=findViewById(R.id.nextB);
        profilePic=findViewById(R.id.ppic);
        fin=findViewById(R.id.btn);
        doneSetup=findViewById(R.id.donesetup);
        welcom=findViewById(R.id.welcome);
        welcom.setVisibility(View.VISIBLE);
        doneSetup.setVisibility(View.GONE);
        TextView msg=findViewById(R.id.welmsg);
        tv=findViewById(R.id.textView37);
        pb3=findViewById(R.id.progressBar3);
        msg.setText(getString(R.string.setu));



        mslideAdapter=new slideAdapter(this);
        mSlidePager.setAdapter(mslideAdapter);
        addDotIndicator(0);
        mSlidePager.addOnPageChangeListener(viewListener);
        fin.setEnabled(false);
        fin.setAlpha(0.6f);
        next.setOnClickListener(e->{

            //System.out.println()("**click");
            mSlidePager.setCurrentItem(mCurrentPage+1);
            if(next.getText().toString().equalsIgnoreCase(getString(R.string.finish)))
            {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    DatabaseReference dref= FirebaseDatabase.getInstance().getReference("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

                    dref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserInformation uinfo=dataSnapshot.getValue(UserInformation.class);
                            RoundImage ri=new RoundImage(covertLinkToImg(uinfo.getImage()));
                            profilePic.setImageDrawable(ri);
                            name=uinfo.getUsername();




               // startAnimation2(msg,'o');
                new CountDownTimer(10000,1000)
                {
                    public  void  onTick(long milliUntilFinish)
                    {

                        //System.out.println()(milliUntilFinish);
                        r=r-20;

                        fin.setVisibility(View.VISIBLE);
                        tv.setText((i++)+"%");
                        pb3.setProgress(i++);

                        doneSetup.setBackgroundColor(Color.rgb(r,g,b));
                        if(pb3.getProgress()==100)
                        {
                            pb3.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                            fin.setEnabled(true);
                            fin.setAlpha(1f);
                        }
                    }
                    public void onFinish()
                    {


                    }
                }.start();
                welcom.setVisibility(View.GONE);
                doneSetup.setVisibility(View.VISIBLE);
                fin.setOnClickListener(v->{
                    msg.setText(getString(R.string.wait3,name));
                    startAnimation2(msg,'i');
                    // fin.setVisibility(View.VISIBLE);
                    startAnimation(fin,'g');
                    startAnimation(fin,'s');
                    pb3.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                    fin.setVisibility(View.GONE);

                    new CountDownTimer(10000,1000)
                    {
                    public  void  onTick(long milliUntilFinish)
                    {

                    }
                public void onFinish()
                {
                    finish();
                    finish();
                    startActivity(new Intent(context,MainActivity.class));

                }
            }.start();

                });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }
            }
        });
        prev.setOnClickListener(e->{
            mSlidePager.setCurrentItem(mCurrentPage-1);
        });

    }
    public Bitmap covertLinkToImg(String imgUrl)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.hbot_wave_lava_sml);
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
    public void startAnimation(View v,char type)
    {

        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1.5f),
                PropertyValuesHolder.ofFloat("scaleX",1.5f));

        grow.setDuration(10000);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1f),
                PropertyValuesHolder.ofFloat("scaleX",1f));
        grow.setRepeatMode(ObjectAnimator.RESTART);

        AnimatorSet animatorSet2=new AnimatorSet();
        if(type=='g')
        animatorSet2.play(grow);
        else
            animatorSet2.play(shrink);
        animatorSet2.setDuration(10000);
        animatorSet2.start();
    }
    public void startAnimation3(View v,char type)
    {

        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1.5f),
                PropertyValuesHolder.ofFloat("scaleX",1.5f));

        grow.setDuration(10000);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("scaleY",1f),
                PropertyValuesHolder.ofFloat("scaleX",1f));
        grow.setRepeatMode(ObjectAnimator.RESTART);
        grow.setRepeatCount(ObjectAnimator.INFINITE);

        AnimatorSet animatorSet2=new AnimatorSet();
        animatorSet2.playSequentially(grow,shrink);
        animatorSet2.setDuration(600);
        animatorSet2.start();
    }
    public void startAnimation2(View v,char type)
    {

        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("alpha",1f,0f));

        grow.setDuration(10000);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat("alpha",0f,1f));
        grow.setRepeatMode(ObjectAnimator.RESTART);
        shrink.setDuration(10000);
        AnimatorSet animatorSet2=new AnimatorSet();
        if(type=='o')
            animatorSet2.play(grow);
        else
            animatorSet2.play(shrink);
        animatorSet2.setDuration(10000);
        animatorSet2.start();
    }
    public void addDotIndicator(int pos)
    {
        mDots=new TextView[slideAdapter.getPages()];
        mDotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++)
        {
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextColor(Color.parseColor("#ececec"));
            mDots[i].setTextSize(20);
            mDotLayout.addView(mDots[i]); //System.out.println()(pos+"----"+mDots.length);


        }
        if(mDots.length>0)
        {
            //System.out.println()(mDots[pos]);
            mDots[pos].setTextColor(Color.parseColor("#adadad"));
           /* if(mDots[pos]==mDots[mDots.length-1])
            {
                finish();
            }*/
        }
    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position>slideAdapter.getPages())
                position=slideAdapter.getPages();
            addDotIndicator(position);
            mCurrentPage = position;
            if (position == 0) {
                prev.setEnabled(false);
                prev.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                next.setEnabled(true);
                startAnimation(next,'s');
                next.setText(getString(R.string.next));

            } else if (position == slideAdapter.getPages()-1) {
                prev.setEnabled(true);
                prev.setVisibility(View.VISIBLE);
                next.setEnabled(true);
                startAnimation(next,'g');
                next.setText(getString(R.string.finish));

            } else {
                prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                next.setEnabled(true);
                prev.setEnabled(true);
                startAnimation(next,'s');
                next.setText(getString(R.string.next));


            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
