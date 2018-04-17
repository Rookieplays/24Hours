package ie.ul.o.daysaver;

import android.content.Context;



/**
 * Created by Ollie on 28/03/2018.
 */

public class HNotifications {
    private String NotificationMsg = "";
    private int icon;
    private String title="";

    public HNotifications(String notificationMsg, int icon, String title, Context context) {
        NotificationMsg = notificationMsg;
        this.icon = icon;
        this.title = title;
        this.context = context;
    }

    private  Context context;

   /* private void buildNotification(String NotificationMsg, String title,int icon,int progress) {
        this.icon = icon;
        this.NotificationMsg = NotificationMsg;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle(title)
                .setContentText(NotificationMsg)
                .setSmallIcon(icon)
                .setPriority(NotificationCompat.PRIORITY_LOW);

// Issue the initial notification with zero progress
        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = progress;
        mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        notificationManager.notify(notificationId, mBuilder.build());

// Do the job here that tracks the progress.
// Usually, this should be in a worker thread
// To show progress, update PROGRESS_CURRENT and update the notification with:
// mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
// notificationManager.notify(notificationId, mBuilder.build());

// When done, update the notification one more time to remove the progress bar

        mBuilder.setContentText("Download complete")
                .setProgress(0,0,false);
        notificationManager.notify(notificationId, mBuilder.build());
    }*/
}
