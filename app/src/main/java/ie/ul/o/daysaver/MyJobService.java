package ie.ul.o.daysaver;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Ollie on 28/03/2018.
 */
public class MyJobService extends JobService {
    BackgroundTask backgroundTask;
    @Override
    public boolean onStartJob(JobParameters job) {
        // Do some work here
        backgroundTask=new BackgroundTask()
        {
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(),"Finish Background task.."+s,Toast.LENGTH_SHORT).show();
                jobFinished(job,false);
                super.onPostExecute(s);
            }
        };
        backgroundTask.execute();
        return false; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false; // Answers the question: "Should this job be retried?"
    }

    public static class BackgroundTask extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids)
        {
            return "Runnning...";
        }
    }
}