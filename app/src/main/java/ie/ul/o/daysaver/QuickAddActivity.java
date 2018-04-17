package ie.ul.o.daysaver;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ali on 17/04/2018.
 */

public class QuickAddActivity extends calenderActivity{
    private EditText eventTitle,eventDescription;
    private Button date,startTime,endTime,finished;
    private CheckBox startAlarm;
    private TextView startTimeView,endTimeView,dateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_add);
        eventTitle=findViewById(R.id.quickEventtitle);
        eventDescription=findViewById(R.id.quickEventDescription);
        date=findViewById(R.id.quickDateBtn);
        startTime=findViewById(R.id.quickStartTime);
        endTime=findViewById(R.id.quickEndTime);
        finished=findViewById(R.id.quickFinishedBtn);
        startAlarm=findViewById(R.id.setUpAlarm);
        startTimeView=findViewById(R.id.textView36);
        endTimeView=findViewById(R.id.textView38);
        dateView=findViewById(R.id.quickDateView);
        initListeners();


    }
    private Long convertFromDateToLong(String date) {
        long milliseconds = System.currentTimeMillis();
        System.out.println("system Time: " + milliseconds);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
            System.out.println("Date Time: " + milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
    private Long convertFromTimeToLong(String date) {
        long milliseconds = System.currentTimeMillis();
        System.out.println("system Time: " + milliseconds);
        SimpleDateFormat f = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
            System.out.println("Date Time: " + milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
    public QuickAdd qA;
    public long dayToLong(String dayditText,long timelong)
    {
        String td;
        long dateInLong=System.currentTimeMillis();


        SimpleDateFormat sdf = new SimpleDateFormat("E", Locale.getDefault());
        td=sdf.format(dateInLong);
        System.out.println("Today is "+td);

        String dayz[][]={{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"},{"Mon","Tue","Wed","Thur"+"Fri"+"Sat"+"Sun"}};
        /** for(int i=0;i<dayz.length;i++)
         {
         for(int j=0;j<dayz.length;j++)
         {
         if(td.equalsIgnoreCase(dayz[i][j]&&dayditText.equals(dayz[]))
         {
         dateInLong=c.getTimeInMillis();
         }
         }
         }**/
        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Monday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Tuesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }

        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Wednesday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }

        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Thursday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }


        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("friday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }



        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis();
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("Saturday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }



        if(td.equalsIgnoreCase("Mon")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
        }
        else if(td.equalsIgnoreCase("Tue")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
        }
        else if(td.equalsIgnoreCase("Wed")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
        }
        else if(td.equalsIgnoreCase("thur")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
        }
        else if(td.equalsIgnoreCase("Fri")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
        }
        else if(td.equalsIgnoreCase("Sat")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
        }
        else if(td.equalsIgnoreCase("Sun")&&dayditText.equals("sunday"))
        {
            dateInLong=System.currentTimeMillis();
        }



        return dateInLong+timelong;
    }
    String day;
    long sT,eT;
    String dayTime,tit,desc;
    private DatePickerDialog datePickerDialog;
    public void initListeners()
    {
        eventTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence))
                {
                    tit=charSequence.toString();
                    eventTitle.setError(null);
                    finished.setEnabled(true);
                    finished.setAlpha(1f);
                }
                else{
                    finished.setEnabled(false);
                    eventTitle.setError(getString(R.string.emptyField));
                    finished.setAlpha(0.4f);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        eventDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence))
                {
                    desc=charSequence.toString();
                    eventDescription.setError(null);
                    finished.setEnabled(true);
                    finished.setAlpha(1f);

                }  else{
                    finished.setEnabled(false);
                    eventDescription.setError(getString(R.string.emptyField));
                    finished.setAlpha(0.4f);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        finished.setOnClickListener(e->{
         qA=new QuickAdd("QUICKADD",tit,desc,sT,eT,day);
            System.out.println(qA);


         saveToDataBase(qA);
         if(startAlarm.isChecked())
         {
           start(cal);
         }
            finish();
        });

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        date.setOnClickListener(e->{datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        try {
                            Date d = new SimpleDateFormat("dd/mm/yyyy").parse("01/01/2018");
                            view.setMinDate(d.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        day = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        System.out.println(day);
                        //dateView.setText(day + " " + dayTime);
                        //  dayTimeToLong(day+" "+dayTime);
                        dateView.setText(day);

                    }
                }, mYear, mMonth, mDay);datePickerDialog.show();});

       startTime.setOnClickListener(e->{TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        dayTime = hourOfDay + ":" + minute;
                        System.out.println(dayTime);
                        //dateView.setText(day + " " + dayTime);
                        //dayTimeToLong(day+" "+dayTime);
                        System.out.println(day);
                       sT=convertFromTimeToLong(dayTime);
                        startTimeView.setText(dayTime);


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();});

    endTime.setOnClickListener(e->{timePickerDialog = new TimePickerDialog(this,
            new TimePickerDialog.OnTimeSetListener() {

                final static int RQS_1 = 1;
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay,
                                      int minute) {

                    dayTime = hourOfDay + ":" + minute;
                    System.out.println(dayTime);
                    //dateView.setText(day + " " + dayTime);
                    //dayTimeToLong(day+" "+dayTime);
                    eT=convertFromTimeToLong(dayTime);

                    endTimeView.setText(dayTime);
                    cal.set(datePickerDialog.getDatePicker().getYear(),
                            datePickerDialog.getDatePicker().getMonth(),
                            datePickerDialog.getDatePicker().getDayOfMonth(),
                            view.getCurrentHour(),
                            view.getCurrentMinute(),
                            00);

                }
            }, mHour, mMinute, false);
        timePickerDialog.show();});


    }

    TimePickerDialog timePickerDialog;
    String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
    final static int RQS_1 = 1;
    public void start(Calendar ms) {

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, ms.getTimeInMillis(), pendingIntent);

    }
    Calendar cal=Calendar.getInstance();
    private void saveToDataBase(QuickAdd q) {
        System.out.println("QA"+q);;
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection(UID)
                .add(q)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                           /* Toast.mak5eText(getActivity(),
                                    "Event document has been added",
                                    Toast.LENGTH_SHORT).show();*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding event documtent", e);
                           /* Toast.makeText(getActivity(),
                                    "Event document could not be added",
                                    Toast.LENGTH_SHORT).show();*/
                    }
                });

    }


    private int mYear, mMonth, mDay, mHour, mMinute;

}
