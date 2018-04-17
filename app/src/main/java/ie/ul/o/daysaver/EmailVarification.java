package ie.ul.o.daysaver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ollie on 10/02/2018.
 */
public class EmailVarification extends RegisterActivity{
    Button resendBtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.popup_layout);
        resendBtn=(Button)findViewById(R.id.resendBtn);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Waiting for conformation....");
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }
    public void onResend(View view)
    {



                this.user.sendEmailVerification();
        System.out.println("Resending Email...");
        Toast.makeText(EmailVarification.this,"Resending Email...",Toast.LENGTH_LONG).show();



    }
    public void showProgress()
    {
        try {
            // Simulate network access.
            Thread.sleep(6000);
        } catch (InterruptedException e) {

        }
        progressDialog.show();
    }
    public void onConfirm(View view) {


        final FirebaseUser currentUser;
        currentUser = this.user;
        //showProgress();
        System.out.println("Waiting for " + this.user.getEmail());


                    System.out.println(currentUser.isEmailVerified());
                    Intent intentA = null;
                   /* if(currentUser!=null)
                    {
                        System.out.println(currentUser.getEmail()+" Has been sent a Verification email");
                        fAuthTask.getInstance().getCurrentUser().reload().addOnCompleteListener(t->{System.out.println("****Ststus: "+currentUser.isEmailVerified());
                       //  if (currentUser.isEmailVerified()) {
                             Toast.makeText(EmailVarification.this,"Email Verified "+getString(R.string.ok),Toast.LENGTH_LONG).show();
                             startActivity(new Intent("ie.ul.o.daysaver.FullscreenActivity"));
                        // intentA=new Intent("ie.ul.o.daysaver.MainActivity");
                        finish();

                       // } else {
                             /**Currently not working 100%*///<---------------------------
                            /***fixed */
                          //  intentA = new Intent("ie.ul.o.daysaver.EmailVarification");8
                            //finish();
                             //Toast.makeText(EmailVarification.this,"Email not verified",Toast.LENGTH_LONG).show();

                       // }
                       // });
                        startActivity(new Intent("ie.ul.o.daysaver.FullscreenActivity"));
                        System.out.println("Emailed has been confirmed");
                    //}

            //startActivity(new Intent("ie.ul.o.daysaver.MainActivity"));

    }






}
