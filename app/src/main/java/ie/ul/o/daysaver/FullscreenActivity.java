package ie.ul.o.daysaver;/**Project to be  rename 24Hours*/

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends RegisterActivity implements LoaderCallbacks<Cursor>{

    private static final int CHOOSE_IMAGE = 101;
    /**Layout*/
    private AutoCompleteTextView fn,ln,age;
    private EditText phoneNumber;
    protected LinearLayout pageOne;
    Button saveB;
    private ProgressBar pic_upd;
    private LinearLayout usernamePage;
    private Button nextBtn;
    private RadioButton m,f,o;
    private ProgressDialog progressDialog;
    private View mProgressView;
    private Toast toast;
    private ImageButton profileBtn;
    Uri uriProfileImage;
    RoundImage roundedImage,ri1,ri2,ri3;

    private TextView usernameText;
    private Button createUsername,skip;
    List<String> list;
    /**Global variables*/
    private String first_name;
    private String second_name;
    private String profileImgURL;
    private int age_;
    private int pageNo=1;

    private final Context context=this;

    private String gender="";
    protected String userID="";
    private  String TAG;
    private String phone_number="";
    private String mEmail="";
    protected String username="You";
    private File picDir;
    private String picPath;

   /**Firebase*/
    protected FirebaseAuth firebaseAuth;
    protected FirebaseDatabase twentyFourHoursDatabase;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected DatabaseReference myRef;

    /**Other Objects*/
    private UserInformation userInformation;
    private ImageView profilePicView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        setTitle(getString(R.string.User_info_Screen_title));
        fn=(AutoCompleteTextView)findViewById(R.id.firstName);
        ln=(AutoCompleteTextView)findViewById(R.id.lastName);
        age=(AutoCompleteTextView)findViewById(R.id.age);
        phoneNumber=(EditText)findViewById(R.id.phoneNumber);
        list=new ArrayList<>();
        usernameText=(TextView)findViewById(R.id.username);
        createUsername=(Button)findViewById(R.id.createUsername);
      ///  skip=(Button)findViewById(R.id.skipOn);
        profileBtn=(ImageButton)findViewById(R.id.profilePic);


        pic_upd=(ProgressBar)findViewById(R.id.pic_upload);

        pageOne=(LinearLayout)findViewById(R.id.PageOne);

        usernamePage=(LinearLayout)findViewById(R.id.UsernamePage);

        usernamePage.setVisibility(View.VISIBLE);
        pageOne.setVisibility(View.GONE);
        nextBtn=(Button)findViewById(R.id.next);

        m=(RadioButton)findViewById(R.id.maleRadioBtn);
        f=(RadioButton)findViewById(R.id.femaleRadioBtn);
        o=(RadioButton)findViewById(R.id.otherRadiobtn);

        mProgressView = findViewById(R.id.login_progress);
        //TODO Initiate All Database/Firebase  antributes
        /**Perform Task Above*/
        FirebaseUser user;
        firebaseAuth=FirebaseAuth.getInstance();
        twentyFourHoursDatabase=FirebaseDatabase.getInstance();
        myRef=twentyFourHoursDatabase.getReference();
        user=firebaseAuth.getCurrentUser();
        userID=user.getUid();
        mEmail=user.getEmail();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Log.d(TAG,"onAuthStateChanged(signed_in)"+user.getUid());
                    //toastMessage("Successfully signed in"+user.getEmail());

                }
                else{
                    Log.d(TAG,"onAuthStateChanged(signed_out)");
                    if(user!=null)
                    toastMessage("Successfully signed out"+user.getEmail());
                }
            }
        };


    AddToDatabase();
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                age.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Reset errors.



                // Store values at the time of the login attempt.
              String myAge=age.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (!TextUtils.isEmpty(myAge) && !isAgeValid(myAge)) {
                    age.setError(getString(R.string.error_invalid_age));
                    focusView = age;
                    cancel = true;
                }




            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        profileBtn.setOnClickListener(e->{profile_pic_dialog();});

        // Set up the user interaction to manually show or hide the system UI.

    }
    @Override
    public void onStart()
    {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop()
    {
        super.onStop();
        if(mAuthListener!=null)
        {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap imageBitmap2 = (Bitmap) extras.get("data");
           // profilePicView.setImageBitmap(imageBitmap);
            uriProfileImage=data.getData();
            roundedImage = new RoundImage(imageBitmap);
            roundedImage= new RoundImage(imageBitmap2);

            profilePicView.setImageDrawable(roundedImage);
            profileBtn.setImageDrawable(roundedImage);

        }
        if(requestCode==CHOOSE_IMAGE && resultCode== RESULT_OK&& data != null && data.getData()!=null)
        {
            uriProfileImage=data.getData();

            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);

                int area=bitmap.getHeight()*bitmap.getWidth();
                int h=bitmap.getHeight();int w= bitmap.getWidth();
                if(area>700000)
                {toastMessage("Pic's Too Big Hint: Pic A File >100kb");saveB.setEnabled(false);}
                else{  ri1= new RoundImage(bitmap);

                    profilePicView.setImageDrawable(ri1);
                    ri2=new RoundImage(bitmap);
                    profileBtn.setImageDrawable(ri2);
                    saveB.setEnabled(true);



               }
                System.out.println("w*h=Area: "+w+" * "+h+" = "+area);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef= FirebaseStorage.getInstance().getReference("profile_pic/"+System.currentTimeMillis()+".jpg");
        if(uriProfileImage !=null)
        {
            pic_upd.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                     pic_upd.setVisibility(View.GONE);
                        toastMessage("pic uploaded..");
                        profileImgURL=taskSnapshot.getDownloadUrl().toString();


                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pic_upd.setVisibility(View.GONE);
                            toastMessage(e.getMessage());
                        }
                    });


        }
    }

    String mCurrentPhotoPath;
    public Bitmap ShrinkBitmap(String file, int width, int height)
    {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if(heightRatio > 1 || widthRatio > 1)
        {
            if(heightRatio > widthRatio)
            {
                bmpFactoryOptions.inSampleSize = heightRatio;
            }
            else
            {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }
    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void setPic(ImageButton mImageView) {
        // Get the dimensions of the View
        mImageView=profileBtn;
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        //RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);

       // mDrawable.setCircular(true);

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
    private void setPic(ImageView mImageView) {
        // Get the dimensions of the View

        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        //RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        // mDrawable.setCircular(true);

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
    public  void profile_pic_dialog()
    {
        android.app.AlertDialog.Builder dialogBuilder=new android.app.AlertDialog.Builder(context);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.set_profilepic_dialog,null);
        Button takepic=(Button)dialogView.findViewById(R.id.take_pic);
        Button choosepic=(Button)dialogView.findViewById(R.id.Add_pic);
        profilePicView=(ImageView)dialogView.findViewById(R.id.Gym);
        saveB=(Button)dialogView.findViewById(R.id.button5);//save button
        takepic.setOnClickListener(e->{dispatchTakePictureIntent();
            try {
                picDir=createImageFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            setPic(profilePicView);

        });
       // choosepic.setOnClickListener(e->{showImageChooser();});


        dialogBuilder.setView(dialogView);
        android.app.AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
        saveB.setOnClickListener(e->{alertDialog.dismiss(); uploadImageToFirebaseStorage();});
        choosepic.setOnClickListener(e->{showImageChooser();});


    }

    private void selectImageFrom() {
    }


    private  void AddToDatabase()
    {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,"onDatachanged, Added Information to database");
                //showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void checkIfUsernameIsTaken(View view) {
        //TODO: check if username is taken
        // System.out.println("Username: "+this.username)

        if(TextUtils.isEmpty(usernameText.getText().toString()))
            usernameText.setError("Cant be empty!");
        else
        {

                username=usernameText.getText().toString();
            usernamePage.setVisibility(View.GONE);
            pageOne.setVisibility(View.VISIBLE);
        }
        // AnimateHbot();



    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        if(pageOne.getVisibility()==View.VISIBLE)
        {
            usernamePage.setVisibility(View.VISIBLE);
            pageOne.setVisibility(View.GONE);
        }
        else
        {
            finish();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User account deleted.");
                            }
                        }
                    });

        }
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            UserInformation uInfo=new UserInformation();
            uInfo.setUsername(ds.child("Users").child(userID).getValue(UserInformation.class).getUsername());

            list.add(uInfo.getUsername());
        }
    }
    protected boolean usernameTaken(String u)
    {
        if(list.contains(u))
            return true;
        else return false;
    }

    protected void toastMessage(String s) {
        toast.makeText(this,s,Toast.LENGTH_LONG).show();
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

            pageOne.setVisibility(show ? View.GONE : View.VISIBLE);
            pageOne.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pageOne.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mPasswordView.setText(getString(R.string.wait));
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
            pageOne.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }



        // TODO: Values to add go here.
      return true;
    }

    public void  goToNextPage(View view) {

        String myAge = age.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if(pageNo<=5)
        {
            pageNo++;
        }
        else pageNo=pageNo;
        if (!TextUtils.isEmpty(myAge) && !isAgeValid(myAge)) {
            //mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = age;
            cancel = true;
        } else
        {
            if(gender==null||gender.equals(""))
            {
                //Do Nothing
            }
            else
            {
              age_ = Integer.parseInt(age.getText().toString());
                first_name=fn.getText().toString();
                first_name=first_name.substring(0,1)+first_name.substring(1,first_name.length());
                second_name=ln.getText().toString();
                if(second_name!=null)
                second_name=second_name.substring(0,1)+second_name.substring(1,second_name.length());
                phone_number=phoneNumber.getText().toString();

                //TODO:Save info
                /**Saving Basic  information*/
                if(TextUtils.isEmpty(first_name))
                {
                    fn.setError("Required Field");
                }
                else if(TextUtils.isEmpty(age.getText().toString()))
                {
                    age.setError("Required Field");
                }
                else {
                    userInformation = new UserInformation(username, first_name, second_name, phone_number, mEmail, age_, gender, profileImgURL);
                    System.out.println(userID.toString() + "|" + username);
                    Log.d(TAG, "Saving:  " + userInformation.toString());
                    myRef.child("Users").child(userID).child("UserInformation").setValue(userInformation);
                    myRef.child("Users").child(userID).child("GYM").setValue(new Gym(new ArrayList<WorkoutPlan>(), false));
                    myRef.child("Users").child(userID).child("SOCIAL").setValue(new Shopping());
                    myRef.child("Users").child(userID).child("SOCIAL").setValue(new Social());


                    toastMessage("New info Added");
                    finish();
                    finish();
                    startActivity(new Intent(this,getttingStartedActivity.class));
                }



            }
        }

    }

    public void onSkinon(View view)
    {}
    public  void showImageChooser()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile image"),CHOOSE_IMAGE);
        setPic(profilePicView);
    }

    public  void  getSelectectedRadioButton(View view)
    {
        //TODO: Get gender
        Log.w("getSelectedRadioButton","initiating method @  line 70_Fullscree...");
        final RadioGroup sexes=(RadioGroup)findViewById(R.id.sexes);
        int radioID=sexes.getCheckedRadioButtonId();
        RadioButton singleButton=(RadioButton)findViewById(radioID);
        gender=singleButton.getText().toString();
        System.out.println(radioID);
    }
    private boolean isAgeValid(String name)
    {
        String pattern="[0-9]+";
        return name.matches(pattern);
    }
    /**Create autocomplete function*/
    private void addNamesToAutoComplete(List<String> nameCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(FullscreenActivity.this,
                        android.R.layout.simple_dropdown_item_1line, nameCollection);

        fn.setAdapter(adapter);
        ln.setAdapter(adapter);
    }
    private void populateAutoComplete() {

        getLoaderManager().initLoader(0, null, this);
    }




    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), FullscreenActivity.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> names = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            names.add(cursor.getString(FullscreenActivity.ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addNamesToAutoComplete(names);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


}
