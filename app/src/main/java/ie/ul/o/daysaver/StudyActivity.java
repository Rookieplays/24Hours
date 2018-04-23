package ie.ul.o.daysaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudyActivity extends MainActivity {
private Toolbar toolbar;
private Button saveSubjects;
private LinearLayout studyField,cSub,nextPage;
private RecyclerView dayChooser;
private Button addView,removeView;
private ArrayList<String> days=new ArrayList<>();
private ArrayList<String> subjects=new ArrayList<>();
private day_adapter da;
private EditText sub1;
private Button saveAll;
private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");
        setContentView(R.layout.activity_study);

       // toolbar=findViewById(R.id.toolbar4);
        //setActionBar(toolbar);
        saveSubjects=findViewById(R.id.saveSubjects);
        studyField=findViewById(R.id.studyField);
        nextPage=findViewById(R.id.nextPage);
        addView=findViewById(R.id.addSub);
        removeView=findViewById(R.id.remSub);
        addMoreItems(studyField,addView,removeView);
        cSub=findViewById(R.id.createSubjects);
        dayChooser=findViewById(R.id.dayPicker);
       // viewFlipper=findViewById(R.id.viewFLipper);


        cSub.setVisibility(View.VISIBLE);
        nextPage.setVisibility(View.GONE);

        sub1=findViewById(R.id.sub1);
        saveAll=findViewById(R.id.saveAll);
        //saveAll.setVisibility(View.GONE);

        dayChooser.setLayoutManager(new GridLayoutManager(this,2));
        saveSubjects.setOnClickListener(e->{
            if(!TextUtils.isEmpty(sub1.getText().toString()))
            {subjects.add(sub1.getText().toString());}
            if(subjectViews!=null)
            {
                for(int i=0;i<subjectViews.length;i++)
                {
                    if(!TextUtils.isEmpty(subjectViews[i].getText().toString()))
                    {
                        subjects.add(subjectViews[i].getText().toString());
                        System.out.println("*****"+subjects);
                    }
                }
            }
            System.out.println("DAYS: "+days+"SUBS: "+subjects);
            da=new day_adapter(this,days,subjects);
            dayChooser.setAdapter(da);
                    //viewFlipper.showNext();
            cSub.setVisibility(View.GONE);
            nextPage.setVisibility(View.VISIBLE);

          //  saveAll.setVisibility(View.VISIBLE);
        });
        saveAll.setOnClickListener(e->{
            for(Study s: day_adapter.GETPLANFOREVERYDAYSELECTED())
            {
                System.out.printf("Studyplan name: %s\nDay: %s\nSubjects: %s\nID: %s\nDuration: %f\n",s.getName(),s.getDay(),s.getSubjects().toString(),s.getId(),s.getDuration());
                if(s.getSubjects().size()>0)
                {
                    daysSelected.add(s.getDay());
                    allStudyPlans.add(s);
                }
            }
            System.out.println("here it is..."+allStudyPlans.toString());
            //save to data base....
            saveAllTODatabase(2018);

        });


        
    }
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();


    private ArrayList<String>daysSelected=new ArrayList<>();


    public void saveAllTODatabase(int year) {
        ArrayList<String> daysImMonths = new ArrayList<String>();

        List<String> temp = new ArrayList<>();
        ProgressDialog pd=new ProgressDialog(this);
    pd.setMessage(getString(R.string.wait5));
    pd.show();
        for (int i = 0; i < daysSelected.size(); i++) {
            for (String s:getAllMondaysInAyear(year, i + 1, daysSelected.get(i))) {
                allStudyPlans.get(i).setDate(s);

               fstore.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).add(allStudyPlans.get(i)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Study Plan","Done...");
                    pd.dismiss();
                        finish();
                    }
                });
                System.out.println();

            }

        }

    }
    public List<String> getAllMondaysInAMonth(int year, int day, int mon) {


        int days = 0, monthNumber = 1, mondays = 0;

        // Converting String to uppercase



        int dayAndMonthNumber[];
        String months[] = new String[]{
                "JAN", "FEB", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEP", "OCTOBER", "NOV", "DEC"
        };

        final String pattern = "dd/MM/yyyy";
        List<String> dates = new ArrayList<>();
        Date date = null;

        String myDate = null;

        DateFormat format = new SimpleDateFormat(pattern);


        dayAndMonthNumber = getNumberofDaysAndMonthNumberByMonthName(months[mon], year + "");

        days = dayAndMonthNumber[0];

        monthNumber = dayAndMonthNumber[1];


        Calendar cal = Calendar.getInstance();


        cal.set(Calendar.MONTH, monthNumber);

        cal.set(Calendar.YEAR, year);


        for (int i = 1; i <= days; i++) {



            cal.set(Calendar.DAY_OF_MONTH, i);



            if (cal.get(Calendar.DAY_OF_WEEK) == day) {

                date = cal.getTime();

                myDate = format.format(date);

                dates.add(myDate);

                mondays++;


            }



            System.out.println("Number of x : " + mondays);


        }
        return dates;
    }

    public List<String> getAllMondaysInAyear(int year, int day, String x) {


        int days = 0, monthNumber = 1, mondays = 0;

        // Converting String to uppercase

        if (x.equalsIgnoreCase("monday"))
            day = 2;
        else if (x.equalsIgnoreCase("tuesday"))
            day = 3;
        else if (x.equalsIgnoreCase("Wednesday"))
            day = 4;
        else if (x.equalsIgnoreCase("thursday"))
            day = 5;
        else if (x.equalsIgnoreCase("friday"))
            day = 6;
        else if (x.equalsIgnoreCase("saturday"))
            day = 7;
        else if (x.equalsIgnoreCase("sunday"))
            day = 1;

        int dayAndMonthNumber[];
        String months[] = new String[]{
                "JAN", "FEB", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEP", "OCTOBER", "NOV", "DEC"
        };

        final String pattern = "dd/MM/yyyy";
        List<String> dates = new ArrayList<>();
        Date date = null;

        String myDate = null;

        DateFormat format = new SimpleDateFormat(pattern);
        for (int m = 0; m < months.length; m++) {


            dayAndMonthNumber = getNumberofDaysAndMonthNumberByMonthName(months[m], year + "");

            days = dayAndMonthNumber[0];

            monthNumber = dayAndMonthNumber[1];

            // Converting String to int.


            // Creating Calendar class instance.

            Calendar cal = Calendar.getInstance();

            // Adding given month and year in Calendar.

            cal.set(Calendar.MONTH, monthNumber);

            cal.set(Calendar.YEAR, year);


            for (int i = 1; i <= days; i++) {


                // Adding day of month in Calendar.

                cal.set(Calendar.DAY_OF_MONTH, i);

					/*

					 * cal.get(Calendar.DAY_OF_WEEK) = 1 (Sunday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 2 (Monday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 3 (Tuesday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 4 (Wednesday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 5 (Thursday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 6 (Friday)

					 * cal.get(Calendar.DAY_OF_WEEK) = 7 (Saturday)

					 */

                if (cal.get(Calendar.DAY_OF_WEEK) == day) {

                    date = cal.getTime();

                    myDate = format.format(date);

                    dates.add(myDate);

                    mondays++;


                }

            }

            System.out.println("Number of x : " + mondays);


        }
        return dates;
    }

    private int[] getNumberofDaysAndMonthNumberByMonthName(String monthName, String year) {


        int dayAndMonthNumber[] = new int[2];

        int days = 0;

        int monthNumber = 0;

        switch (monthName) {

            case "JANUARY":

            case "JAN":


                days = 31;

                monthNumber = 0;

                break;

            case "FEBRUARY":

            case "FEB":

                if (isLeapYear(year)) {

                    days = 29;

                } else {

                    days = 28;

                }

                monthNumber = 1;

                break;

            case "MARCH":

            case "MAR":

                days = 31;

                monthNumber = 2;

                break;

            case "APRIL":

            case "APR":

                days = 30;

                monthNumber = 3;

                break;

            case "MAY":

                days = 31;

                monthNumber = 4;

                break;

            case "JUNE":

                days = 30;

                monthNumber = 5;

                break;

            case "JULY":

                days = 31;

                monthNumber = 6;

                break;

            case "AUGUST":

            case "AUG":

                days = 31;

                monthNumber = 7;

                break;

            case "SEPTEMBER":

            case "SEP":

                days = 30;

                monthNumber = 8;

                break;

            case "OCTOBER":

            case "OCT":

                days = 31;

                monthNumber = 9;

                break;

            case "NOVEMBER":

            case "NOV":

                days = 30;

                monthNumber = 10;

                break;

            case "DECEMBER":

            case "DEC":

                days = 31;

                monthNumber = 11;

                break;

        }

        dayAndMonthNumber[0] = days;

        dayAndMonthNumber[1] = monthNumber;

        return dayAndMonthNumber;

    }


    private boolean isLeapYear(String year) {


        boolean result = false;

        // Converting String to int.

        int myYear = Integer.parseInt(year);

        if ((myYear % 4 == 0 && myYear % 100 != 0) || myYear % 400 == 0) {

            result = true;

        }

        return result;

    }
    private ArrayList<Study>allStudyPlans=new ArrayList<>();
    int count=0;
    String[]subs=new String[100];

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        dayChooser.removeAllViews();

        if(nextPage.getVisibility()==View.VISIBLE)
        {
            finish();
            startActivity(new Intent(this,StudyActivity.class));
           // viewFlipper.showPrevious();
        }
        else {
            finish();
        }
    }
    EditText subjectViews[];
    private void addMoreItems(LinearLayout ll, Button add, Button remove)
    {



        subjectViews=new EditText[100];
        for(int i=0;i<subjectViews.length;i++)
        {
            subjectViews[i]=new EditText(this);
            subjectViews[i].setHint("New Subject");
            subjectViews[i].setTextColor(Color.WHITE);
          subjectViews[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // G2P =et.getText().toString();

                    if(!TextUtils.isEmpty(subjectViews[0].getText().toString()))
                      saveSubjects.setEnabled(true);
                    else saveSubjects.setEnabled(false);

                }
                @Override
                public void afterTextChanged(Editable editable) {


                }
            });
        }
      
        add.setOnClickListener(e->{
            remove.setEnabled(true);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if(count<subjectViews.length)
            {
                ll.addView(subjectViews[count], lp);
                // toastMessage((count+1)+"/"+max,Toast.LENGTH_SHORT);
                count++;
                System.out.println("count more "+count);

            }
            else {
                add.setEnabled(false);
            }
        });
        remove.setOnClickListener(e->{
            add.setEnabled(true);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if(count>0)
            {
                System.out.println(count);
                ll.removeView(subjectViews[count-1]);
                subjectViews[count-1].setText("");

                //toastMessage((count+1)+"/"+max,Toast.LENGTH_SHORT);
                count--;
                System.out.println("Count less"+count);
                remove.setBackgroundColor(getResources().getColor(R.color.save_red));
                remove.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_light));


            }
            else {

                remove.setEnabled(false);
                remove.setBackgroundColor(Color.rgb(179,0,0));
                remove.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
            }
        });


    }
}
