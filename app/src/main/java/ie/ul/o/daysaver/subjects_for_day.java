package ie.ul.o.daysaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ollie on 26/03/2018.
 */

public class subjects_for_day extends RecyclerView.Adapter<subjects_for_day.ViewHolder> {
    private static Study STUDIES;
    private String[] mDataset,mDescSets;
    private Bitmap[] mImgSets;
    private  LayoutInflater inflater;
    private Context context;
    private static ArrayList<Subjects>SUBJECTS=new ArrayList<>();
  //  private ArrayList<Study>studies=new ArrayList<>();

    private String dayy;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public static CheckBox mTextView;
        public Button done;
        public static TextView aTextView;

        public ViewHolder(View v)
        {
            super(v);
            mTextView=v.findViewById(R.id.cbsub);



        }
    }

    String days;
    public subjects_for_day(Context context, ArrayList<String>myDataset,String day)
    {
        this.context=context;

        inflater=LayoutInflater.from(context);
        if(myDataset.size()==0)
        {
            mDataset=new String[]{"Empty List"};

        }
        else{
            mDataset=new String[myDataset.size()];

            for(int i=0;i<myDataset.size();i++)
            {
                mDataset[i]=myDataset.get(i);
            }
            //System.out.println()("£$TG£%GG%$^UJ%J%Y "+mDataset[0]);
        }
        study.setDay(day);
        days=day;
        study.setName("Study Plan For: "+day.toUpperCase());
        STUDIES=study;




    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.subjects_res,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    private Study study=new Study();
    private ArrayList<Subjects>subjects=new ArrayList<>();
     static ArrayList<Subjects>mSubs=new ArrayList<>();
     static ArrayList<Subjects>tSubs=new ArrayList<>();
     static ArrayList<Subjects>wSubs=new ArrayList<>();
     static ArrayList<Subjects>thSubs=new ArrayList<>();
     static ArrayList<Subjects>fSubs=new ArrayList<>();
     static ArrayList<Subjects>sSubs=new ArrayList<>();
     static ArrayList<Subjects>sunSub=new ArrayList<>();
     static int x;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {x=position;
        ViewHolder.mTextView.setText(mDataset[position]);

    ArrayList<Subjects>temp=new ArrayList<>();
    mSubs.clear();
    tSubs.clear();
    wSubs.clear();
    thSubs.clear();
    fSubs.clear();
    sSubs.clear();
    sunSub.clear();
        ViewHolder.mTextView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(compoundButton.isChecked())
                {
                    //System.out.println()("day? "+days);
                    if(days.equalsIgnoreCase("Monday"))
                    {
                        mSubs.add(new Subjects(compoundButton.getText().toString()));
                        //System.out.println()("********"+mSubs);
                    }
                    else  if(days.equalsIgnoreCase("Tuesday"))
                    {
                        tSubs.add(new Subjects(compoundButton.getText().toString()));
                        //System.out.println()("********"+tSubs);
                    }
                    else  if(days.equalsIgnoreCase("Wednesday"))
                    {
                        wSubs.add(new Subjects(compoundButton.getText().toString()));
                    }
                    else  if(days.equalsIgnoreCase("Thursday"))
                    {
                        thSubs.add(new Subjects(compoundButton.getText().toString()));
                    }
                    else  if(days.equalsIgnoreCase("Friday"))
                    {
                        fSubs.add(new Subjects(compoundButton.getText().toString()));
                    }
                    else  if(days.equalsIgnoreCase("Saturday"))
                    {
                         sSubs.add(new Subjects(compoundButton.getText().toString()));
                    }
                    else  if(days.equalsIgnoreCase("Sunday"))
                    {
                        sunSub.add(new Subjects(compoundButton.getText().toString()));
                    }

                }
                else
                {
                    if(days.equalsIgnoreCase("Monday"))
                    {
                        mSubs=refreshSubjects(mSubs,compoundButton.getText().toString());
                    }
                    else  if(days.equalsIgnoreCase("Tuesday"))
                    {
                        tSubs=refreshSubjects(tSubs,compoundButton.getText().toString());
                    }
                    else  if(days.equalsIgnoreCase("Wednesday"))
                    {
                        wSubs=refreshSubjects(wSubs,compoundButton.getText().toString());
                    }
                    else  if(days.equalsIgnoreCase("Thursday"))
                    {
                        thSubs=refreshSubjects(thSubs,compoundButton.getText().toString());
                    }
                    else  if(days.equalsIgnoreCase("Friday"))
                    {
                        fSubs=refreshSubjects(fSubs,compoundButton.getText().toString());
                    }
                    else  if(days.equalsIgnoreCase("Saturday"))
                    {
                        sSubs=refreshSubjects(sSubs,compoundButton.getText().toString());
                    }
                    else  if(days.equalsIgnoreCase("Sunday"))
                    {
                        sunSub=refreshSubjects(sunSub,compoundButton.getText().toString());
                    }

                }
            }
        });
        //System.out.println()("Day of Subject: "+days+"temp: "+temp);

    }
    public void saveToDatabase()
    {

    }
    public static ArrayList<Subjects> GETDAYSUBJECTS()
    {
        return SUBJECTS;
    }
    private Subjects gatherInfo(String name) {
        //System.out.println()(new Subjects(name));
        return new Subjects(name);
    }
    public static void clearListFor(String day)
    {
        //System.out.println()("Pos: "+x);
        if(day.equalsIgnoreCase("Monday"))
        {
            mSubs.clear();
        }
        else  if(day.equalsIgnoreCase("Tuesday"))
        {
            tSubs.clear();
        }
        else  if(day.equalsIgnoreCase("Wednesday"))
        {
            wSubs.clear();
        }
        else  if(day.equalsIgnoreCase("Friday"))
        {
            thSubs.clear();
        }
        else  if(day.equalsIgnoreCase("Saturday"))
        {
            fSubs.clear();
        }
        else  if(day.equalsIgnoreCase("Monday"))
        {
            sSubs.getClass();
        }
        else  if(day.equalsIgnoreCase("Sunday"))
        {
            sunSub.clear();
        }

    }
    public ArrayList<Subjects> refreshSubjects(ArrayList<Subjects> subjects1,String s)
    {
        //System.out.println()("Checking if: "+s+" is among: "+subjects1);
        ArrayList<Subjects> temp=new ArrayList<>();
        temp.addAll(subjects1);
        subjects1.clear();
        for(Subjects sub: temp)
        {
            if(sub.getName().equals(s))
            {
                //subjects1.remove(s);
                //System.out.println()("Removed: "+s+" from: "+temp);
            }
            else
                subjects1.add(sub);

        }
        return  subjects1;

    }
    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }

}
