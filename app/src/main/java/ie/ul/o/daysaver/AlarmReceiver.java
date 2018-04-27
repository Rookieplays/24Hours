package ie.ul.o.daysaver;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class AlarmReceiver extends BroadcastReceiver {




    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";
    public static String STUDYMSG="Event Alert!";
    public static String STUDYHEADING="";
    public static int ICON=R.drawable.ic_launcher;

    public static int getICON() {
        return ICON;
    }

    public static void setICON(int ICON) {
        AlarmReceiver.ICON = ICON;
    }

    public static String getNotificationId() {
        return NOTIFICATION_ID;
    }

    public static void setNotificationId(String notificationId) {
        NOTIFICATION_ID = notificationId;
    }

    public static String getNOTIFICATION() {
        return NOTIFICATION;
    }

    public static void setNOTIFICATION(String NOTIFICATION) {
        AlarmReceiver.NOTIFICATION = NOTIFICATION;
    }

    public static String getSTUDYMSG() {
        return STUDYMSG;
    }

    public static void setSTUDYMSG(String STUDYMSG) {
        AlarmReceiver.STUDYMSG = STUDYMSG;
    }

    public static String getSTUDYHEADING() {
        return STUDYHEADING;
    }
    private Context context;

    public static void setSTUDYHEADING(String STUDYHEADING) {
        AlarmReceiver.STUDYHEADING = STUDYHEADING;
    }

    public void onReceive(Context context, Intent intent) {

        this.context=context;
       updateAlarm();



    }
    private void updateAlarm() {
        String date=new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
        String time=new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
        Log.w("If Loop","MainActivity.EVENTS: "+MainActivity.EVENTS.isEmpty());
        if(!(MainActivity.EVENTS.isEmpty()&&MainActivity.ET.isEmpty()&&MainActivity.ICL.isEmpty()&&MainActivity.EVENTS.isEmpty()))
        {

            for(int i=0;i<MainActivity.EVENTS.size();i++)
            {
               /* try {
                    //Log.e("For Loop","MainActivity.EVENTS: "+MainActivity.EVENTS.get(i)+"\nNow Time: "+(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()));
                    //Log.w("For Loop","MainActivity.ET: "+MainActivity.ET.get(i)+"\nNow Time: "+(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()));

                } catch (ParseException e) {
                    e.printStackTrace();
                };*/
                try {
                    if(MainActivity.ST.get(i)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        setNotification("","",MainActivity.EVENTS.get(0).get(i),"...",MainActivity.EVENTS.get(1).get(i),MainActivity.ICL.get(i));

                    }
                    else  if(MainActivity.ST.get(i)+(15*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        MainActivity.EVENTS.get(2).set(i,"Event Starting in 15 mins");

                        setNotification("","",MainActivity.EVENTS.get(0).get(i),"",MainActivity.EVENTS.get(1).get(i),MainActivity.ICL.get(i));

                    }
                  

                    else  if(MainActivity.ET.get(i)-(15*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        MainActivity.EVENTS.get(2).set(i,"Event ending in 15 mins");

                        setNotification("","",MainActivity.EVENTS.get(2).get(i),"",MainActivity.EVENTS.get(3).get(i),MainActivity.ICL.get(i));

                    }
                    else  if(MainActivity.ET.get(i)-(10*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        MainActivity.EVENTS.get(2).set(i,"Event ending in 10 mins");
                        setNotification("","",MainActivity.EVENTS.get(2).get(i),"",MainActivity.EVENTS.get(3).get(i),MainActivity.ICL.get(i));

                    }
                    else  if(MainActivity.ET.get(i)-(5*60*1000)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        MainActivity.EVENTS.get(2).set(i,"Event ending in 5 mins");

                        setNotification("","",MainActivity.EVENTS.get(2).get(i),"",MainActivity.EVENTS.get(3).get(i),MainActivity.ICL.get(i));

                    }
                    else  if(MainActivity.ET.get(i)==(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()+new SimpleDateFormat("HH:mm:ss").parse(time).getTime()))
                    {
                        MainActivity.EVENTS.get(2).set(i,"Event is Over!!");

                        setNotification("","",MainActivity.EVENTS.get(2).get(i),"",MainActivity.EVENTS.get(3).get(i),MainActivity.ICL.get(i));

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setNotification(String heading,String msg,String BT,String sumText, String BCT,int ic)
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(BT);
        bigText.setBigContentTitle(BCT);
        bigText.setSummaryText(sumText);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(ic);
        mBuilder.setContentTitle(heading);
        mBuilder.setContentText(msg);
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

        mNotificationManager.notify(0, mBuilder.build());
    }


}