package ie.ul.o.daysaver;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

/**
 * A login screen that offers login via email/password.
 */
public class ResetPasswordActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private final Context context=this;
    private EditText mPasswordView;
    private EditText mCPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private LinearLayout mEmailBox;
    private Button resetButton;
    private AutoCompleteTextView emailView;
    private FirebaseAuth fAuthTask;
    private FirebaseUser user;
    private Registration reg;
    private String TAG;
    private String passwordnew;
    boolean emailExists=false;
    private LinearLayout currentView,previousView;
    private LinearLayout mPasswordsView;
    Button mEmailSignInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        // Set up the login form.
        mPasswordView = (EditText) findViewById(R.id.password);
        resetButton=(Button)findViewById(R.id.reset) ;
        emailView=(AutoCompleteTextView) findViewById(R.id.emailView);
        fAuthTask=FirebaseAuth.getInstance();
        mCPasswordView = (EditText) findViewById(R.id.cPassword);
        user=fAuthTask.getCurrentUser();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEmailBox=findViewById(R.id.mEmailBox);
        mPasswordsView=findViewById(R.id.email_login_form);
        currentView=mEmailBox;
        previousView=mPasswordsView;
        if(user!=null)
        emailView.setText(user.getEmail());
    }







    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mPasswordView.setError(null);
        mCPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String password = mPasswordView.getText().toString();
        String cPassword = mCPasswordView.getText().toString();
        String email=emailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if(TextUtils.isEmpty(mCPasswordView.getText()))
        {
            mCPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if(!isPasswordMatching(password,mCPasswordView.getText().toString()))
        {
            mCPasswordView.setError(getString(R.string.error_match_failed));
            focusView = mPasswordView;
            cancel = true;
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }//check if user exists
        /*else if () {
            mPasswordView.setError(getString(R.string.error_invalid_email));
            focusView = mPasswordView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordMatching(String password,String password2) {
        //TODO: Replace this with your own logic
        return password2.equals(password);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        try {
            reg=new Registration(emailView.getText().toString(),password,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        passwordnew=password;
        return reg.validPassword(mPasswordView);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            currentView=mPasswordsView;
            previousView=mEmailBox;
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    currentView=mPasswordsView;
                    previousView=mEmailBox;
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            currentView=mPasswordsView;
            previousView=mEmailBox;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    private void showProgressB(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mEmailBox.setVisibility(show ? View.GONE : View.VISIBLE);
            previousView=mPasswordsView;
            currentView=mEmailBox;
            mEmailBox.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mEmailBox.setVisibility(show ? View.GONE : View.VISIBLE);
                    previousView=mPasswordsView;
                    currentView=mEmailBox;
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
    }
    /** show the password field*/
    private void showPasswordField(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

       //TODO: (Error:RPA1)Getting  an Error from the password field */
                            /**Fixed (Error:  RPA1) 15/02/18 15:47*/
                            if(isPasswordValid(mPasswordView.getText().toString())&&isPasswordMatching(mPasswordView.getText().toString(),mCPasswordView.getText().toString()))
                            {

                                user.updatePassword(passwordnew);
                            Intent intent=new Intent(ResetPasswordActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);


                            // updateUI(user);
                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure");

                            Toast.makeText(ResetPasswordActivity.this,getString(R.string.user_not_found),
                                    Toast.LENGTH_LONG).show();

                            Intent intent=new Intent("ie.ul.o.daysaver.ResetPasswordActivity");
                            startActivity(intent);
                            System.out.println("User Doesn't Exists");
                            //updateUI(null);
                        }

                        // ...







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public void goToPasswords(View view) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email =emailView.getText().toString();
                auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(ResetPasswordActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                            // do something when mail was sent successfully.
                        } else {
                            // ...
                        }
                    }
                });
                /**Alternate Version...*/
       /* previousView=mEmailBox;
        currentView=mPasswordsView;

        System.out.println(user.getEmail());
        if (user.getEmail()!=null) {
            emailExists = true;

            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithEmail:success");

        } else {
            emailExists=false;

        }
            System.out.println("The Email is: "+emailExists);
        if (emailExists) {
            mLoginFormView.setVisibility(View.VISIBLE);
            mEmailBox.setVisibility(View.GONE);



        } else if(emailExists==false){
            showProgressB(false);
            emailView.setError(getString(R.string.user_not_found));
            mLoginFormView.setVisibility(View.GONE);
            mEmailBox.setVisibility(View.VISIBLE);
            previousView=mPasswordsView;
            currentView=mEmailBox;

        }*/
    }

    public void onCreatenew(View view) {
        passwordnew=mPasswordView.getText().toString();
        showPasswordField(true);
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            showPasswordField(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
            showPasswordField(false);
        }
    }
}

