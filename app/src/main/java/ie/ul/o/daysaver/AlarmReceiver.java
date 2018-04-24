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


public class AlarmReceiver extends BroadcastReceiver {




    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";
    public static String STUDYMSG="Event Alert!";
    public static String STUDYHEADING="Click to launch";
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

    public static void setSTUDYHEADING(String STUDYHEADING) {
        AlarmReceiver.STUDYHEADING = STUDYHEADING;
    }

    public void onReceive(Context context, Intent intent) {


        createNotification(context,STUDYHEADING,STUDYMSG,"Alert");


    }

    private void createNotification(Context context, String title, String text, String alert) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notify_001");
        Intent ii = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("");
        bigText.setBigContentTitle(STUDYHEADING);
        bigText.setSummaryText(STUDYMSG);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.study_small);
        mBuilder.setContentTitle(STUDYHEADING);
        mBuilder.setContentText(STUDYMSG);
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
       // mBuilder.build();
    }

}