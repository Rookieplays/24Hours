package ie.ul.o.daysaver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class socialActivity extends MainActivity {


    private final Context context=this;
    private long daTime,daTime2;
    private String date,startTime,endTime,occurance;
    private  String tags[];
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy ");
    private final long todaysDate=System.currentTimeMillis();
    private final String TodaysDate=simpleDateFormat.format(System.currentTimeMillis());
    private long MINs;
    private int timeVersion,dayPick,monthPick,yearPick,max=5,min=1;
    private RecyclerView rv;
    long printedDate;
    View focusView = null;
    boolean done=true;
    private final String TAG="Social";
    private Button cancl;
    int count=0;
    private String G2P="";
    private ArrayList<Social>Allevents=new ArrayList<>();
    private  String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
   private  DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User/UID");


    private shoppingListAdapter mAdapoter;
    CreatedEvents CE;

    String nW;
    /**Create Widgets*/
    Spinner c_spinner,s_spinner,b_spinner,g_spinner,x_spinner,sO_spinner,w_spinner,d_spinner,a_spinner,custom_spinner,club_spinner,drink_spinner;
            ImageButton sportBtn,bDayBtn,cineBtn,sleepOBtn,clubBtn,gamingBtn,ACBtn,datingBtn,wedBtn,XmasBtn,dringBtn,asportBtn,abDayBtn,acineBtn,adatingBtn,aXmasBtn,defaultBtn,aDefaultBtn;
    ImageButton bballBtn,soccerBtn,rugbyBtn,mmaBtn,boxingBtn;
    AutoCompleteTextView sportnameField,cinemanameField,birthdaynameField,gamingnameField,shoppingnameField,xmasnameField,awardnameField,ddatenameField,weddingnameField,customnameField,drinknameField,clubnameField;
    EditText s_loc,s_d,s_sT,s_eT,s_n;
    EditText c_loc,c_d,c_sT,c_eT,c_n,c_dur;
    EditText d_loc,d_d,d_sT,d_eT,d_n,d_dur;
    EditText a_loc,a_d,a_sT,a_eT,a_n,a_dur;
    EditText w_loc,w_d,w_sT,w_eT,w_n,w_dur;
    EditText custom_loc,custom_d,custom_sT,custom_eT,custom_n,custom_dur;
    EditText clb_loc,clb_d,clb_sT,clb_eT,clb_n,clb_dur;
    EditText dnk_loc,dnk_d,dnk_sT,dnk_eT,dnk_n,dnk_dur;

    EditText b_loc,b_d,b_sT,b_eT,b_n;
    EditText g_loc;
    EditText g_d;
    EditText g_sT;
    EditText g_eT;
    EditText g_n,g2,g3,g4,g5;
    EditText g_dur;
    EditText gameOne;

    EditText x_loc,x_d,x_sT,x_eT,x_n;
    EditText sO_loc,sO_d,sO_sT,sO_eT,sO_n;
Button addG,removeG;
    ImageButton b_invite_front, getB_invite_inside, x_invite_front, getX_invite_inside, sO_invite_front, getsO_invite_inside, w_invite_front, getW_invite_inside;
    TextView c_eTime,b_tag,c_tag,s_tag,g_tag,g_eTime,x_tag,sOtag,dtag,atag,wtag,clb_eTime,clb_tag,dnk_eTime,dnk_tag;
    TimePicker timeSelector,timeSelector2,ctp,ctp2,btp,btp2,gtp,gtp2,xtp,xtp2,sOtp,sOtp2,dtp,dtp2,wtp,wtp2,atp,atp2,customtp,customtp2,dnktp,dnktp2,clbtp,clbtp2;
    DatePicker datePicker,cdp,cdp2,bdp,gdp,xdp,sOdp,ddp,wdp,adp,customdp,dnkdp,clbdp;
    Button selectTime,selecttTme2,selectDate,save,seteTime,setsTime,setDate,csetDate,bsetDate,xsetDate,sOsetdate,drnksetdate,wsetdate,customsetdate,dnkdate,clbdate,
            csetStartingTime,csetEndingTime,cDatePicker,cTimePicker1,cTimepicker2,
            bsetStartingTime,bsetEndingTime,bDatePicker,bTimePicker1,bTimepicker2,
            gsetStartingTime,gsetEndingTime,gDatePicker,gTimePicker1,gTimepicker2,gsetDate,
            xsetStartingTime,xsetEndingTime,xDatePicker,xTimePicker1,xTimepicker2,
            sOsetStartingTime,sOsetEndingTime,sODatePicker,sOTimePicker1,sOTimepicker2,sOsetDate,
            dsetStartingTime,dsetEndingTime,dDatePicker,dTimePicker1,dTimepicker2,dsetDate,
            dnksetStartingTime,dnksetEndingTime,dnkDatePicker,dnkTimePicker1,dnkTimepicker2,dnksetDate,
            clbsetStartingTime,clbsetEndingTime,clbDatePicker,clbTimePicker1,clbTimepicker2,clbsetDate,

    wsetStartingTime,wsetEndingTime,wDatePicker,wTimePicker1,wTimepicker2,wsetDate,
    asetStartingTime,asetEndingTime,aDatePicker,aTimePicker1,aTimepicker2,asetDate,
            customsetStartingTime,customsetEndingTime,customDatePicker,customTimePicker1,customTimepicker2,customsetDate;

    private LinearLayout setupPage;
    private LinearLayout customPage;
    private LinearLayout itemBox;
    private LinearLayout timeClockField;
    private LinearLayout durationBox;
    private LinearLayout inviteBox;

    private RadioGroup timeSetting;
    private RadioButton r1;
    private RadioButton r2;
    private CheckBox invite;
    private CheckBox addItems;
    private Button begin;


    private TextView s_m,s_t,s_w,s_th,s_f,s_st,s_sd;
    private TextView c_m,c_t,c_w,c_th,c_f,c_st,c_sd;
    private TextView b_m,b_t,b_w,b_th,b_f,b_st,b_sd;
    private TextView g_m,g_t,g_w,g_th,g_f,g_st,g_sd;
    private TextView x_m,x_t,x_w,x_th,x_f,x_st,x_sd;
    private TextView sO_m,sO_t,sO_w,sO_th,sO_f,sO_st,sO_sd;
    private TextView d_m,d_t,d_w,d_th,d_f,d_st,d_sd;
    private TextView clb_m,clb_t,clb_w,clb_th,clb_f,clb_st,clb_sd;
    private TextView dnk_m,dnk_t,dnk_w,dnk_th,dnk_f,dnk_st,dnk_sd;
    private TextView a_m,a_t,a_w,a_th,a_f,a_st,a_sd;
    private TextView w_m,w_t,w_w,w_th,w_f,w_st,w_sd;
    private TextView custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd;
   // private TextView d_m,d_t,d_w,d_th,d_f,d_st,d_sd;

    private final String today="Today";



    /**Create Layouts*/
    LinearLayout adolecentTemplates;
    LinearLayout adultTemplates,cinemaField,birthdayField;
    LinearLayout DateSelector,cTimeSelector,cDateSelector,cTimeSelector2,bTimeSelector,bDateSelector,bTimeSelector2,aTimeSelector,aDateSelector,aTimeSelector2,
            dTimeSelector,dDateSelector,dTimeSelector2,
            dnkTimeSelector,dnkDateSelector,dnkTimeSelector2,
            clbTimeSelector,clbDateSelector,clbTimeSelector2,
            wTimeSelector,wDateSelector,wTimeSelector2,
            customTimeSelector,customDateSelector,customTimeSelector2,
            gTimeSelector,gDateSelector,gTimeSelector2,xTimeSelector,xDateSelector,xTimeSelector2,
            sOTimeSelector,sODateSelector,sOTimeSelector2;

    LinearLayout TimeSelector,TimeSelector2,sportField,gamingField,xmasField,sOverField,gamesField,awardField,ddateField,adateField,weddingField,customField,clbField,dnkField;

    /**social Objects*/
    Social cinema,birthday,sport,gaming,xmas,shopping,dating,wedding,award,custom,clubing,drink;
    private char type;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        Toolbar toolbar = findViewById(R.id.toolbar24);
        setSupportActionBar(toolbar);
        toolbar.setTitle("24h:Social");
        pdd=new ProgressDialog(context);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              customEventCreationCenter();
            }
        });




        sportBtn= findViewById(R.id.sportBtn);
        bDayBtn= findViewById(R.id.bdayBtn);
        cineBtn= findViewById(R.id.Cinemabtn);
        sleepOBtn= findViewById(R.id.shoppingBtn);
       gamingBtn= findViewById(R.id.gameBtn);
        ACBtn= findViewById(R.id.aDrinkBtn);
        datingBtn= findViewById(R.id.dateBtn);
        XmasBtn= findViewById(R.id.xmasBtn);
        //defaultBtn=(ImageButton)findViewById(R.id.defautBtn);

        asportBtn= findViewById(R.id.asportBtn);
        abDayBtn= findViewById(R.id.aBirthdayBtn);
        acineBtn= findViewById(R.id.aCinemaBtn);
        clubBtn= findViewById(R.id.aClub);
        wedBtn= findViewById(R.id.aWedBtn);
        dringBtn= findViewById(R.id.aDrinkBtn);
      //  aDefaultBtn=(ImageButton)findViewById(R.id.aNewBtn);
        aXmasBtn= findViewById(R.id.aXmasBtn);
        adatingBtn= findViewById(R.id.aDateBtn);

        adultTemplates= findViewById(R.id.Adult_Default);
        adolecentTemplates= findViewById(R.id.Adolecent_Defaults);
        checkAge();


        sportBtn.setOnClickListener(e->{show_templates(R.layout.social_sport_template,1);type='x';});
        asportBtn.setOnClickListener(e->{show_templates(R.layout.social_sport_template,1);type='x';});

        bDayBtn.setOnClickListener(e->{show_templates(R.layout.social_birthday_template,2);type='x';});
        abDayBtn.setOnClickListener(e->{show_templates(R.layout.social_birthday_template,2);type='x';});

        cineBtn.setOnClickListener(e->{show_templates(R.layout.social_cinema_template,3);type='x';});
        acineBtn.setOnClickListener(e->{show_templates(R.layout.social_cinema_template,3);type='x';});

        gamingBtn.setOnClickListener(e->{show_templates(R.layout.social_gaming_template,4);type='x';});
        gamingBtn.setOnClickListener(e->{show_templates(R.layout.social_gaming_template,4);type='x';});

        XmasBtn.setOnClickListener(e->{show_templates(R.layout.social_xmas_template,5);type='x';});
        aXmasBtn.setOnClickListener(e->{show_templates(R.layout.social_xmas_template,5);type='x';});

        sleepOBtn.setOnClickListener(e->{showconfirmationBox2();type='O';});



        datingBtn.setOnClickListener(e->{show_templates(R.layout.social_ddate_template,7);type='x';});
        adatingBtn.setOnClickListener(e->{show_templates(R.layout.social_ddate_template,7);type='x';});

        ACBtn.setOnClickListener(e->{show_templates(R.layout.social_award_template,8);type='x';});
       wedBtn.setOnClickListener(e->{show_templates(R.layout.social_wedding_template,9);type='x';});

        clubBtn.setOnClickListener(e->{show_templates(R.layout.social_clubing_template,10);type='x';});












    }
private SListAdapterAlpha mAdapter;
    private ArrayList<ShoppingList>shoppingLists=new ArrayList<>();
    private FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    private void initRV() {
        mAdapter=new SListAdapterAlpha(context,shoppingLists);
        rv.setAdapter(mAdapter);
    }
    private ProgressDialog pdd;
    private void LoadPublicLists()
    {

        fstore.collection("Public_shopping").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<ShoppingList> lists = new ArrayList<>();

                    for (DocumentSnapshot doc : task.getResult()) {
                        //System.out.println()("#123xx" + doc.getData());
                        ShoppingList sl = doc.toObject(ShoppingList.class);
                        //  //System.out.println()(wp+"***********"+wp.getUID());
                        shoppingLists.add(sl);
                    }
                    initRV();

                }
            }
        });
    }
    private void LoadMyLists()
    {
        pdd.setMessage("Loading...");
        pdd.show();
        fstore.collection("Public_shopping").whereEqualTo("uid",UID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<ShoppingList> lists = new ArrayList<>();

                    for (DocumentSnapshot doc : task.getResult()) {
                        //System.out.println()("#123xx" + doc.getData());
                        ShoppingList sl = doc.toObject(ShoppingList.class);
                        //  //System.out.println()(wp+"***********"+wp.getUID());
                        shoppingLists.add(sl);
                    }
                    initRV();
                }
            }
        });
        fstore.collection("Private_shopping").whereEqualTo("uid",UID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<ShoppingList> lists = new ArrayList<>();

                    for (DocumentSnapshot doc : task.getResult()) {
                        //System.out.println()("#123xx" + doc.getData());
                        ShoppingList sl = doc.toObject(ShoppingList.class);
                        //  //System.out.println()(wp+"***********"+wp.getUID());
                        shoppingLists.add(sl);
                    }
                    initRV();
                    pdd.dismiss();
                }
            }
        });
    }

    boolean duration=true;
    private EditText itemOne;
    private EditText custom_eTime;
    private TextView custom_tag;
    private Button addC,removeC,customTimePicker3;
    private ImageButton customInside,customOutside;
    private TextView cus_et;

    private void customEventCreationCenter() {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.socail_custom,null);


        dialogBuilder.setView(v);
        setupPage=v.findViewById(R.id.setupPage);
        customPage=v.findViewById(R.id.customPage);
        itemBox=v.findViewById(R.id.customItemField);
        timeClockField=v.findViewById(R.id.timeClockBox);
        durationBox=v.findViewById(R.id.durationBox);
        inviteBox=v.findViewById(R.id.invitesBox);



        timeSetting=v.findViewById(R.id.timeSelected);
        invite=v.findViewById(R.id.invites);
        addItems=v.findViewById(R.id.addItems);
        begin=v.findViewById(R.id.begin);
        customnameField= v.findViewById(R.id.custom_name_field);
       custom_loc= v.findViewById(R.id.custom_location);
       custom_d= v.findViewById(R.id.custom_date);
       itemOne= v.findViewById(R.id.il);
       custom_sT= v.findViewById(R.id.custom_stime);
       custom_n= v.findViewById(R.id.custom_notes);
       custom_dur= v.findViewById(R.id.customDuration);
       custom_eTime=v.findViewById(R.id.custom_etime);
       cus_et=v.findViewById(R.id.c_etime);


       customtp= v.findViewById(R.id.customtp);
       customtp2= v.findViewById(R.id.customtp2);
       customdp= v.findViewById(R.id.customdp);
       customsetStartingTime= v.findViewById(R.id.customsetStartingTime);
       customsetEndingTime= v.findViewById(R.id.customsetStartingTime2);
       customsetDate= v.findViewById(R.id.customsetDate);
       customDatePicker= v.findViewById(R.id.customdatePicker);
       customTimePicker1= v.findViewById(R.id.customtimepicker1);
       customTimepicker2= v.findViewById(R.id.customtimepicker2);
        customTimePicker3= v.findViewById(R.id.customtimepicker2_plus);
        customField= v.findViewById(R.id.custom_Field);
       customTimeSelector= v.findViewById(R.id.customTimeSelector);
       customDateSelector= v.findViewById(R.id.customDateSelector);
       customTimeSelector2= v.findViewById(R.id.customTimeSelector2);
       custom_tag= v.findViewById(R.id.customEvent_tag);
       custom_spinner= v.findViewById(R.id.custom_spinner);
        customInside= v.findViewById(R.id.coverOfcustom_invite);
        customOutside= v.findViewById(R.id.insideOfcustomInvite);
      // customamesField=(LinearLayout) v.findViewById(R.id.gamesField);
        addC= v.findViewById(R.id.addItem);
        removeC= v.findViewById(R.id.removeItem);


        custom_m= v.findViewById(R.id.custom_mon);
        custom_t= v.findViewById(R.id.custom_tue);
        custom_w= v.findViewById(R.id.custom_wed);
        custom_th= v.findViewById(R.id.custom_thurs);
        custom_f= v.findViewById(R.id.custom_fri);
        custom_st= v.findViewById(R.id.custom_sat);
        custom_sd= v.findViewById(R.id.custom_sun);

        /**Shopppingb lists*/


        setupPage.setVisibility(View.VISIBLE);
        customPage.setVisibility(View.GONE);


        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
        begin.setOnClickListener(e->{
         if(duration==true)
         {
             durationBox.setVisibility(View.VISIBLE);
             timeClockField.setVisibility(View.GONE);

         }
         else
         {
             durationBox.setVisibility(View.GONE);
             timeClockField.setVisibility(View.VISIBLE);

         }
         if(invite.isChecked())
         {
             inviteBox.setVisibility(View.VISIBLE);
         }
         else
             inviteBox.setVisibility(View.GONE);
         if(addItems.isChecked())
         {
             itemBox.setVisibility(View.VISIBLE);
         }
         else
         {
             itemBox.setVisibility(View.GONE);
         }
            startCustom(v,alertDialog);
        });




    }


    private void startCustom(View v,AlertDialog alertDialog) {
        if(itemBox.getVisibility()==View.VISIBLE&&durationBox.getVisibility()==View.VISIBLE&&inviteBox.getVisibility()==View.GONE&&timeClockField.getVisibility()==View.GONE){
        setupPage.setVisibility(View.GONE);
        customPage.setVisibility(View.VISIBLE);
        save= v.findViewById(R.id.social_save_custom);
        cancl= v.findViewById(R.id.cancel10);
        save.setEnabled(isCompleted(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,save,"Unknown name"));
        save.setOnClickListener(e->{handleSaveButtonForAll(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,cus_et,null,null,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customD",custom_spinner);alertDialog.dismiss();});
        handleTemplatestuff(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,cus_et,null,null,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customD",custom_spinner);
        selectDay(custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd,custom_d);
            addMoreItems(itemBox,addC,removeC,custom_tag);
      //  setInvites(customOutside,customInside);
            setUpAGeneralEventHandlerForTheTimeSelection(customDatePicker,customTimePicker1,customTimePicker3,customsetDate,customsetStartingTime,customsetEndingTime,customtp,customField,customDateSelector,customTimeSelector,customTimeSelector2,customtp2,customdp,custom_d,custom_sT,custom_dur,cus_et,save);

        }
        else   if(itemBox.getVisibility()==View.GONE&&durationBox.getVisibility()==View.VISIBLE&&inviteBox.getVisibility()==View.VISIBLE&&timeClockField.getVisibility()==View.GONE){

            setupPage.setVisibility(View.GONE);
            customPage.setVisibility(View.VISIBLE);
            save= v.findViewById(R.id.social_save_custom);
            cancl= v.findViewById(R.id.cancel10);
            save.setEnabled(isCompleted(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,save,"Unknown name"));
            save.setOnClickListener(e->{handleSaveButtonForAll(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,cus_et,customInside,customOutside,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customD",custom_spinner);alertDialog.dismiss();});
            handleTemplatestuff(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,cus_et,customInside,customOutside,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customD",custom_spinner);
            selectDay(custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd,custom_d);
          //  addMoreItems(customField,addC,removeC,custom_tag);
            setInvites(customOutside,customInside);
            setUpAGeneralEventHandlerForTheTimeSelection(customDatePicker,customTimePicker1,customTimePicker3,customsetDate,customsetStartingTime,customsetEndingTime,customtp,customField,customDateSelector,customTimeSelector,customTimeSelector2,customtp2,customdp,custom_d,custom_sT,custom_dur,cus_et,save);
        }
        else  if(itemBox.getVisibility()==View.GONE&&durationBox.getVisibility()==View.VISIBLE&&inviteBox.getVisibility()==View.GONE&&timeClockField.getVisibility()==View.GONE){

            setupPage.setVisibility(View.GONE);
            customPage.setVisibility(View.VISIBLE);
            save= v.findViewById(R.id.social_save_custom);
            cancl= v.findViewById(R.id.cancel10);
            save.setEnabled(isCompleted(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,save,"Unknown name"));
            save.setOnClickListener(e->{handleSaveButtonForAll(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,cus_et,null,null,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customD",custom_spinner);alertDialog.dismiss();});
            handleTemplatestuff(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,cus_et,null,null,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customD",custom_spinner);
            selectDay(custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd,custom_d);
            setUpAGeneralEventHandlerForTheTimeSelection(customDatePicker,customTimePicker1,customTimePicker3,customsetDate,customsetStartingTime,customsetEndingTime,customtp,customField,customDateSelector,customTimeSelector,customTimeSelector2,customtp2,customdp,custom_d,custom_sT,custom_dur,cus_et,save);
          //  addMoreItems(customField,addC,removeC,custom_tag);
           // setInvites(customOutside,customInside);
        }
        else  if(itemBox.getVisibility()==View.VISIBLE&&durationBox.getVisibility()==View.VISIBLE&&inviteBox.getVisibility()==View.VISIBLE&&timeClockField.getVisibility()==View.GONE){

            setupPage.setVisibility(View.GONE);
            customPage.setVisibility(View.VISIBLE);
            save= v.findViewById(R.id.social_save_custom);
            cancl= v.findViewById(R.id.cancel10);
            save.setEnabled(isCompleted(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,save,"Unknown name"));
            save.setOnClickListener(e->{handleSaveButtonForAll(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,cus_et,customInside,customOutside,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customDl",custom_spinner);alertDialog.dismiss();});
            handleTemplatestuff(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,cus_et,customInside,customOutside,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customDl",custom_spinner);
            selectDay(custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd,custom_d);
            addMoreItems(itemBox,addC,removeC,custom_tag);
            setInvites(customOutside,customInside);
            setUpAGeneralEventHandlerForTheTimeSelection(customDatePicker,customTimePicker1,customTimePicker3,customsetDate,customsetStartingTime,customsetEndingTime,customtp,customField,customDateSelector,customTimeSelector,customTimeSelector2,customtp2,customdp,custom_d,custom_sT,custom_dur,cus_et,save);
        }
        else  if(itemBox.getVisibility()==View.VISIBLE&&durationBox.getVisibility()==View.GONE&&inviteBox.getVisibility()==View.GONE&&timeClockField.getVisibility()==View.VISIBLE){
            setupPage.setVisibility(View.GONE);
            customPage.setVisibility(View.VISIBLE);
            save= v.findViewById(R.id.social_save_custom);
            cancl= v.findViewById(R.id.cancel10);
            save.setEnabled(isCompleted(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,save,"Unknown name"));
            save.setOnClickListener(e->{handleSaveButtonForAll(customnameField,custom_loc,custom_d,custom_sT,custom_eTime,custom_n,null,null,null,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customL",custom_spinner);alertDialog.dismiss();});
            handleTemplatestuff(customnameField,custom_loc,custom_d,custom_sT,custom_eTime,custom_n,null,null,null,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"customL",custom_spinner);
            selectDay(custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd,custom_d);
            addMoreItems(itemBox,addC,removeC,custom_tag);
            setUpAGeneralEventHandlerForTheTimeSelection(customDatePicker,customTimePicker1,customTimepicker2,customsetDate,customsetStartingTime,customsetEndingTime,customtp,customField,customDateSelector,customTimeSelector,customTimeSelector2,customtp2,customdp,custom_d,custom_sT,custom_eTime,null,save);
          //  setInvites(customOutside,customInside);
        }
        else   if(itemBox.getVisibility()==View.GONE&&durationBox.getVisibility()==View.GONE&&inviteBox.getVisibility()==View.VISIBLE&&timeClockField.getVisibility()==View.VISIBLE){

            setupPage.setVisibility(View.GONE);
            customPage.setVisibility(View.VISIBLE);
            save= v.findViewById(R.id.social_save_custom);
            cancl= v.findViewById(R.id.cancel10);
            save.setEnabled(isCompleted(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,save,"Unknown name"));
            save.setOnClickListener(e->{handleSaveButtonForAll(customnameField,custom_loc,custom_d,custom_sT,custom_eTime,custom_n,null,customInside,customOutside,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"custom",custom_spinner);alertDialog.dismiss();});
            handleTemplatestuff(customnameField,custom_loc,custom_d,custom_sT,custom_eTime,custom_n,null,customInside,customOutside,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"custom",custom_spinner);
            selectDay(custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd,custom_d);
                     //  addMoreItems(customField,addC,removeC,custom_tag);
            setInvites(customOutside,customInside);
            setUpAGeneralEventHandlerForTheTimeSelection(customDatePicker,customTimePicker1,customTimepicker2,customsetDate,customsetStartingTime,customsetEndingTime,customtp,customField,customDateSelector,customTimeSelector,customTimeSelector2,customtp2,customdp,custom_d,custom_sT,custom_eTime,null,save);

        }
        else  if(itemBox.getVisibility()==View.GONE&&durationBox.getVisibility()==View.GONE&&inviteBox.getVisibility()==View.GONE&&timeClockField.getVisibility()==View.VISIBLE){

            setupPage.setVisibility(View.GONE);
            customPage.setVisibility(View.VISIBLE);
            save= v.findViewById(R.id.social_save_custom);
            cancl= v.findViewById(R.id.cancel10);
            save.setEnabled(isCompleted(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,save,"Unknown name"));
            save.setOnClickListener(e->{handleSaveButtonForAll(customnameField,custom_loc,custom_d,custom_sT,custom_eTime,custom_n,null,null,null,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"custom",custom_spinner);alertDialog.dismiss();});
            handleTemplatestuff(customnameField,custom_loc,custom_d,custom_sT,custom_eTime,custom_n,null,null,null,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"custom",custom_spinner);
            selectDay(custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd,custom_d);
            setUpAGeneralEventHandlerForTheTimeSelection(customDatePicker,customTimePicker1,customTimepicker2,customsetDate,customsetStartingTime,customsetEndingTime,customtp,customField,customDateSelector,customTimeSelector,customTimeSelector2,customtp2,customdp,custom_d,custom_sT,custom_eTime,null,save);
           // addMoreItems(customField,addC,removeC,custom_tag);
           // setInvites(customOutside,customInside);
        }
        else  if(itemBox.getVisibility()==View.VISIBLE&&durationBox.getVisibility()==View.GONE&&inviteBox.getVisibility()==View.VISIBLE&&timeClockField.getVisibility()==View.VISIBLE){

            setupPage.setVisibility(View.GONE);
            customPage.setVisibility(View.VISIBLE);
            save= v.findViewById(R.id.social_save_custom);
            cancl= v.findViewById(R.id.cancel10);
            save.setEnabled(isCompleted(customnameField,custom_loc,custom_d,custom_sT,custom_dur,custom_n,save,"Unknown name"));
            save.setOnClickListener(e->{handleSaveButtonForAll(customnameField,custom_loc,custom_d,custom_sT,custom_eTime,custom_n,null,customInside,customOutside,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"custom",custom_spinner);alertDialog.dismiss();});
            handleTemplatestuff(customnameField,custom_loc,custom_d,custom_sT,custom_eTime,custom_n,null,customInside,customOutside,customDateSelector,customTimeSelector,customTimeSelector2,customtp,customtp2,customdp,custom,custom_tag,"custom",custom_spinner);
            selectDay(custom_m,custom_t,custom_w,custom_th,custom_f,custom_st,custom_sd,custom_d);
            addMoreItems(itemBox,addC,removeC,custom_tag);
            setInvites(customOutside,customInside);
            setUpAGeneralEventHandlerForTheTimeSelection(customDatePicker,customTimePicker1,customTimepicker2,customsetDate,customsetStartingTime,customsetEndingTime,customtp,customField,customDateSelector,customTimeSelector,customTimeSelector2,customtp2,customdp,custom_d,custom_sT,custom_eTime,null,save);
        }
    }

    public void insertText(AutoCompleteTextView et,String wordToInsert)
    {
        et.getText().clear();
        et.setText(wordToInsert);
    }
    public void selectDay(TextView m,TextView t,TextView w,TextView th,TextView f,TextView s, TextView sd,EditText datefield)
    {
        String td;
        long dte=System.currentTimeMillis();


        SimpleDateFormat sdf = new SimpleDateFormat("E",Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        td=sdf.format(dte);
        //System.out.println()("Today is "+td);
        if(td.equalsIgnoreCase("Mon"))
        {m.setText("Today");m.setBackgroundColor(Color.WHITE);m.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);m.setTextColor(getResources().getColor(R.color.colorPrimary));}
        else if(td.equalsIgnoreCase("Tue"))
        {t.setText("Today");t.setBackgroundColor(Color.WHITE);t.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);t.setTextColor(getResources().getColor(R.color.colorPrimary));}
        else if(td.equalsIgnoreCase("Wed"))
        {w.setText("Today");w.setBackgroundColor(Color.WHITE);w.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);w.setTextColor(getResources().getColor(R.color.colorPrimary));}
        else if(td.equalsIgnoreCase("Thur")||td.equals("Thurs"))
        {th.setText("Today");th.setBackgroundColor(Color.WHITE);th.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);th.setTextColor(getResources().getColor(R.color.colorPrimary));}
        else if(td.equalsIgnoreCase("Fri"))
        {f.setText("Today");f.setBackgroundColor(Color.WHITE);f.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);f.setTextColor(getResources().getColor(R.color.colorPrimary));}
        else if(td.equalsIgnoreCase("Sat"))
        {s.setText("Today");s.setBackgroundColor(Color.WHITE);s.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);s.setTextColor(getResources().getColor(R.color.colorPrimary));}
        else if(td.equalsIgnoreCase("Sun"))
        {sd.setText("Today");sd.setBackgroundColor(Color.WHITE);sd.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);sd.setTextColor(getResources().getColor(R.color.colorPrimary));}


        /**Monday*/
        m.setOnClickListener(e->{
            if(m.getText().toString().equalsIgnoreCase(today))
                    nW=sdf2.format(dte);
            else if(t.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
                nW=sdf2.format(printedDate);
            }
            else if(w.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
                nW=sdf2.format(printedDate);
            }
            else if(th.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
                nW=sdf2.format(printedDate);
            }
            else if(f.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
                nW=sdf2.format(printedDate);
            }
            else if(s.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
                nW=sdf2.format(printedDate);
            }
            else if(sd.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
                nW=sdf2.format(printedDate);
            }

            datefield.setText(nW);

        });

        /**Tuesday*/
        t.setOnClickListener(e->{
            if(t.getText().toString().equalsIgnoreCase(today))
                nW=sdf2.format(dte);
            else if(w.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
                nW=sdf2.format(printedDate);
            }
            else if(th.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
                nW=sdf2.format(printedDate);
            }
            else if(f.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
                nW=sdf2.format(printedDate);
            }
            else if(s.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
                nW=sdf2.format(printedDate);
            }
            else if(sd.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
                nW=sdf2.format(printedDate);
            }
            else if(m.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
                nW=sdf2.format(printedDate);
            }
            datefield.setText(nW);

        });

        /**Wednesday*/
        w.setOnClickListener(e->{
            if(w.getText().toString().equalsIgnoreCase(today))
                nW=sdf2.format(dte);
            else if(th.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
                nW=sdf2.format(printedDate);
            }
            else if(f.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
                nW=sdf2.format(printedDate);
            }
            else if(s.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
                nW=sdf2.format(printedDate);
            }
            else if(sd.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
                nW=sdf2.format(printedDate);
            }
            else if(m.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
                nW=sdf2.format(printedDate);
            }
            else if(t.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
                nW=sdf2.format(printedDate);
            }
            datefield.setText(nW);
        });

        /**Thursday*/
        th.setOnClickListener(e->{
            if(th.getText().toString().equalsIgnoreCase(today))
                nW=sdf2.format(dte);
            else if(f.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
                nW=sdf2.format(printedDate);
            }
            else if(s.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
                nW=sdf2.format(printedDate);
            }
            else if(sd.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
                nW=sdf2.format(printedDate);
            }
            else if(m.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
                nW=sdf2.format(printedDate);
            }
            else if(t.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
                nW=sdf2.format(printedDate);
            }
            else if(w.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
                nW=sdf2.format(printedDate);
            }
            datefield.setText(nW);
        });

        /**friday*/
        f.setOnClickListener(e->{
            if(f.getText().toString().equalsIgnoreCase(today))
                nW=sdf2.format(dte);
            else if(s.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
                nW=sdf2.format(printedDate);
            }
            else if(sd.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
                nW=sdf2.format(printedDate);
            }
            else if(m.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
                nW=sdf2.format(printedDate);
            }
            else if(t.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
                nW=sdf2.format(printedDate);
            }
            else if(w.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
                nW=sdf2.format(printedDate);
            }
            else if(th.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
                nW=sdf2.format(printedDate);
            }
            datefield.setText(nW);
        });

        /**Ssturday*/
        s.setOnClickListener(e->{
            if(s.getText().toString().equalsIgnoreCase(today))
                nW=sdf2.format(dte);
            else if(sd.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
                nW=sdf2.format(printedDate);
            }
            else if(m.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
                nW=sdf2.format(printedDate);
            }
            else if(t.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
                nW=sdf2.format(printedDate);
            }
            else if(w.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
                nW=sdf2.format(printedDate);
            }
            else if(th.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
                nW=sdf2.format(printedDate);
            }
            else if(f.getText().toString().equalsIgnoreCase(today))
            {
                printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
                nW=sdf2.format(printedDate);
            }
            datefield.setText(nW);
        });
        /**Sunday*/
        sd.setOnClickListener(e->{
        if(sd.getText().toString().equalsIgnoreCase(today))
            nW=sdf2.format(dte);
        else if(m.getText().toString().equalsIgnoreCase(today))
        {
            printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 6);
            nW=sdf2.format(printedDate);
        }
        else if(t.getText().toString().equalsIgnoreCase(today))
        {
            printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 5);
            nW=sdf2.format(printedDate);
        }
        else if(w.getText().toString().equalsIgnoreCase(today))
        {
            printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 4);
            nW=sdf2.format(printedDate);
        }
        else if(th.getText().toString().equalsIgnoreCase(today))
        {
            printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 3);
            nW=sdf2.format(printedDate);
        }
        else if(f.getText().toString().equalsIgnoreCase(today))
        {
            printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 2);
            nW=sdf2.format(printedDate);
        }
        else if(s.getText().toString().equalsIgnoreCase(today))
        {
            printedDate=System.currentTimeMillis()+(1000 * 60 * 60 * 24 * 1);
            nW=sdf2.format(printedDate);
        }
            datefield.setText(nW);
    });

    }
    public void insertText(EditText et,String wordToInsert)
    {
        et.getText().clear();
        et.setText(wordToInsert);
    }
    public  void show_templates(int resource,int key)
    {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(resource,null);
       /// Button default_gi=(Button)dialogView.findViewById(R.id.gi_Default);
        ///Button gi_setupnow=(Button)dialogView.findViewById(R.id.gi_setupnow);
       // gi_setupnow.setOnClickListener(e->{startActivity(new Intent("ie.ul.o.daysaver.GymActivity"));});


        bballBtn= dialogView.findViewById(R.id.bballlBtn);
        soccerBtn= dialogView.findViewById(R.id.soccerBall);
        rugbyBtn= dialogView.findViewById(R.id.rugbyBtn);
        mmaBtn= dialogView.findViewById(R.id.mmaBtn);
        boxingBtn= dialogView.findViewById(R.id.boxingBtn);
        sportnameField= dialogView.findViewById(R.id.sport_name_field);
        s_loc= dialogView.findViewById(R.id.sport_location);
        s_d= dialogView.findViewById(R.id.sport_date);
        s_sT= dialogView.findViewById(R.id.sport_stime);
        s_eT= dialogView.findViewById(R.id.sport_etime);
        s_n= dialogView.findViewById(R.id.sport_notes);
        timeSelector= dialogView.findViewById(R.id.tp);
        timeSelector2= dialogView.findViewById(R.id.tp1);
        datePicker= dialogView.findViewById(R.id.dp);
       selectTime= dialogView.findViewById(R.id.timepicker1);
        selecttTme2= dialogView.findViewById(R.id.timepicker2);
        selectDate= dialogView.findViewById(R.id.datePicker);
        s_spinner= dialogView.findViewById(R.id.s_spinner);

        s_m= dialogView.findViewById(R.id.s_mon);
        s_t= dialogView.findViewById(R.id.s_tue);
        s_w= dialogView.findViewById(R.id.s_wed);
        s_th= dialogView.findViewById(R.id.s_thurs);
        s_f= dialogView.findViewById(R.id.s_fri);
        s_st= dialogView.findViewById(R.id.s_sat);
        s_sd= dialogView.findViewById(R.id.s_sun);


        c_m= dialogView.findViewById(R.id.c_mon);
        c_t= dialogView.findViewById(R.id.c_tue);
        c_w= dialogView.findViewById(R.id.c_wed);
        c_th= dialogView.findViewById(R.id.c_thurs);
        c_f= dialogView.findViewById(R.id.c_fri);
        c_st= dialogView.findViewById(R.id.c_sat);
        c_sd= dialogView.findViewById(R.id.c_sun);


        b_m= dialogView.findViewById(R.id.b_mon);
        b_t= dialogView.findViewById(R.id.b_tue);
        b_w= dialogView.findViewById(R.id.b_wed);
        b_th= dialogView.findViewById(R.id.b_thurs);
        b_f= dialogView.findViewById(R.id.b_fri);
        b_st= dialogView.findViewById(R.id.b_sat);
        b_sd= dialogView.findViewById(R.id.b_sun);

        g_m= dialogView.findViewById(R.id.g_mon);
        g_t= dialogView.findViewById(R.id.g_tue);
        g_w= dialogView.findViewById(R.id.g_wed);
        g_th= dialogView.findViewById(R.id.g_thurs);
        g_f= dialogView.findViewById(R.id.g_fri);
        g_st= dialogView.findViewById(R.id.g_sat);
        g_sd= dialogView.findViewById(R.id.g_sun);

        x_m= dialogView.findViewById(R.id.x_mon);
        x_t= dialogView.findViewById(R.id.x_tue);
        x_w= dialogView.findViewById(R.id.x_wed);
        x_th= dialogView.findViewById(R.id.x_thurs);
        x_f= dialogView.findViewById(R.id.x_fri);
        x_st= dialogView.findViewById(R.id.x_sat);
        x_sd= dialogView.findViewById(R.id.x_sun);

        sO_m= dialogView.findViewById(R.id.sO_mon);
        sO_t= dialogView.findViewById(R.id.sO_tue);
        sO_w= dialogView.findViewById(R.id.sO_wed);
        sO_th= dialogView.findViewById(R.id.sO_thurs);
        sO_f= dialogView.findViewById(R.id.sO_fri);
        sO_st= dialogView.findViewById(R.id.sO_sat);
        sO_sd= dialogView.findViewById(R.id.sO_sun);

        a_m= dialogView.findViewById(R.id.a_mon);
        a_t= dialogView.findViewById(R.id.a_tue);
        a_w= dialogView.findViewById(R.id.a_wed);
        a_th= dialogView.findViewById(R.id.a_thurs);
        a_f= dialogView.findViewById(R.id.a_fri);
        a_st= dialogView.findViewById(R.id.a_sat);
        a_sd= dialogView.findViewById(R.id.a_sun);

        d_m= dialogView.findViewById(R.id.d_mon);
        d_t= dialogView.findViewById(R.id.d_tue);
        d_w= dialogView.findViewById(R.id.d_wed);
        d_th= dialogView.findViewById(R.id.d_thurs);
        d_f= dialogView.findViewById(R.id.d_fri);
        d_st= dialogView.findViewById(R.id.d_sat);
        d_sd= dialogView.findViewById(R.id.d_sun);

        w_m= dialogView.findViewById(R.id.w_mon);
        w_t= dialogView.findViewById(R.id.w_tue);
        w_w= dialogView.findViewById(R.id.w_wed);
        w_th= dialogView.findViewById(R.id.w_thurs);
        w_f= dialogView.findViewById(R.id.w_fri);
        w_st= dialogView.findViewById(R.id.w_sat);
        w_sd= dialogView.findViewById(R.id.w_sun);

        clb_m= dialogView.findViewById(R.id.clb_mon);
        clb_t= dialogView.findViewById(R.id.clb_tue);
        clb_w= dialogView.findViewById(R.id.clb_wed);
        clb_th= dialogView.findViewById(R.id.clb_thurs);
        clb_f= dialogView.findViewById(R.id.clb_fri);
        clb_st= dialogView.findViewById(R.id.clb_sat);
        clb_sd= dialogView.findViewById(R.id.clb_sun);
/*
        dnk_m=(TextView)dialogView.findViewById(R.id.dnk_mon);
        dnk_t=(TextView)dialogView.findViewById(R.id.dnk_tue);
        dnk_w=(TextView)dialogView.findViewById(R.id.dnk_wed);
        dnk_th=(TextView)dialogView.findViewById(R.id.dnk_thurs);
        dnk_f=(TextView)dialogView.findViewById(R.id.dnk_fri);
        dnk_st=(TextView)dialogView.findViewById(R.id.dnk_sat);
        dnk_sd=(TextView)dialogView.findViewById(R.id.dnk_sun);
*/



        DateSelector= dialogView.findViewById(R.id.DateSelector);
        TimeSelector= dialogView.findViewById(R.id.TimeSelector);
        TimeSelector2= dialogView.findViewById(R.id.TimeSelector2);
        sportField= dialogView.findViewById(R.id.sportField);
        seteTime= dialogView.findViewById(R.id.setEndingTime);
        setsTime= dialogView.findViewById(R.id.setStartTime);
        setDate= dialogView.findViewById(R.id.setDate);
     //  selectTime.setOnClickListener(e->{sportField.setVisibility(View.GONE);DateSelector.setVisibility(View.GONE);TimeSelector.setVisibility(View.VISIBLE);TimeSelector2.setVisibility(View.GONE);});
       // selecttTme2.setOnClickListener(e->{sportField.setVisibility(View.GONE);DateSelector.setVisibility(View.GONE);TimeSelector.setVisibility(View.GONE);TimeSelector2.setVisibility(View.VISIBLE);});
       // selectDate.setOnClickListener(e->{sportField.setVisibility(View.GONE);DateSelector.setVisibility(View.VISIBLE);TimeSelector.setVisibility(View.GONE);TimeSelector2.setVisibility(View.GONE);});
        s_tag= dialogView.findViewById(R.id.sportEvent_tag);

        cinemanameField= dialogView.findViewById(R.id.cinema_name_field);
        c_loc= dialogView.findViewById(R.id.cinema_location);
        c_d= dialogView.findViewById(R.id.cinema_date);
       // c_eT=(EditText) dialogView.findViewById(R.id.cine);
        c_sT= dialogView.findViewById(R.id.cinema_stime);
        c_n= dialogView.findViewById(R.id.cinema_notes);
        c_dur= dialogView.findViewById(R.id.cDuration);
        c_eTime= dialogView.findViewById(R.id.cinema_etime);
        ctp= dialogView.findViewById(R.id.ctp);
        ctp2= dialogView.findViewById(R.id.ctp2);
        cdp= dialogView.findViewById(R.id.cdp);
        csetStartingTime= dialogView.findViewById(R.id.csetStartingTime);
        csetEndingTime= dialogView.findViewById(R.id.csetStartingTime2);
        csetDate= dialogView.findViewById(R.id.csetDate);
        cDatePicker= dialogView.findViewById(R.id.cdatePicker);
        cTimePicker1= dialogView.findViewById(R.id.ctimepicker1);
        cTimepicker2= dialogView.findViewById(R.id.ctimepicker2);
        c_spinner= dialogView.findViewById(R.id.c_spinner);

        cinemaField= dialogView.findViewById(R.id.cinemaField);
        cTimeSelector= dialogView.findViewById(R.id.cTimeSelector);
        cDateSelector= dialogView.findViewById(R.id.cDateSelector);
        cTimeSelector2= dialogView.findViewById(R.id.cTimeSelector2);
        c_tag= dialogView.findViewById(R.id.cinemaEvent_tag);


       birthdaynameField= dialogView.findViewById(R.id.birthday_name_field);
        b_loc= dialogView.findViewById(R.id.birthday_location);
        b_d= dialogView.findViewById(R.id.birthday_date);
        b_eT= dialogView.findViewById(R.id.birthday_etime);
        b_sT= dialogView.findViewById(R.id.birthday_stime);
        b_n= dialogView.findViewById(R.id.birthday_notes);
        //b_dur=(EditText)dialogView.findViewById(R.id.bDuration);
       // b_eTime=(TextView) dialogView.findViewById(R.id.birthday_etime);
        btp= dialogView.findViewById(R.id.btp);
        btp2= dialogView.findViewById(R.id.btp2);
        bdp= dialogView.findViewById(R.id.bdp);
        bsetStartingTime= dialogView.findViewById(R.id.bsetStartingTime);
        bsetEndingTime= dialogView.findViewById(R.id.bsetStartingTime2);
        bsetDate= dialogView.findViewById(R.id.bsetDate);
        bDatePicker= dialogView.findViewById(R.id.bdatePicker);
        bTimePicker1= dialogView.findViewById(R.id.btimepicker1);
        bTimepicker2= dialogView.findViewById(R.id.btimepicker2);
        birthdayField= dialogView.findViewById(R.id.birthdayField);
        bTimeSelector= dialogView.findViewById(R.id.bTimeSelector);
        bDateSelector= dialogView.findViewById(R.id.bDateSelector);
        bTimeSelector2= dialogView.findViewById(R.id.bTimeSelector2);
          b_invite_front= dialogView.findViewById(R.id.coverOfb_invite);
        getB_invite_inside= dialogView.findViewById(R.id.insideOfbInvite);
       b_tag= dialogView.findViewById(R.id.birthdayEvent_tag);
        b_spinner= dialogView.findViewById(R.id.b_spinner);

        gamingnameField= dialogView.findViewById(R.id.gaming_name_field);
       g_loc= dialogView.findViewById(R.id.gaming_location);
       g_d= dialogView.findViewById(R.id.gaming_date);
      gameOne= dialogView.findViewById(R.id.g1);
       g_sT= dialogView.findViewById(R.id.gaming_stime);
       g_n= dialogView.findViewById(R.id.gaming_notes);
        g_dur= dialogView.findViewById(R.id.gDuration);
        g_eTime= dialogView.findViewById(R.id.gaming_etime);
       gtp= dialogView.findViewById(R.id.gtp);
       gtp2= dialogView.findViewById(R.id.gtp2);
       gdp= dialogView.findViewById(R.id.gdp);
       gsetStartingTime= dialogView.findViewById(R.id.gsetStartingTime);
       gsetEndingTime= dialogView.findViewById(R.id.gsetStartingTime2);
        gsetDate= dialogView.findViewById(R.id.gsetDate);
       gDatePicker= dialogView.findViewById(R.id.gdatePicker);
       gTimePicker1= dialogView.findViewById(R.id.gtimepicker1);
       gTimepicker2= dialogView.findViewById(R.id.gtimepicker2);
        gamingField= dialogView.findViewById(R.id.gamingField);
       gTimeSelector= dialogView.findViewById(R.id.gTimeSelector);
       gDateSelector= dialogView.findViewById(R.id.gDateSelector);
       gTimeSelector2= dialogView.findViewById(R.id.gTimeSelector2);
        g_tag= dialogView.findViewById(R.id.gamingEvent_tag);
        g_spinner= dialogView.findViewById(R.id.g_spinner);
        gamesField= dialogView.findViewById(R.id.gamesField);
        addG= dialogView.findViewById(R.id.addGame);
        removeG= dialogView.findViewById(R.id.removeGame);

       /* drinknameField=(AutoCompleteTextView)dialogView.findViewById(R.id.drink_name_field);
        dnk_loc=(EditText)dialogView.findViewById(R.id.drink_location);
        dnk_d=(EditText)dialogView.findViewById(R.id.drink_date);
       // gameOne=(EditText) dialogView.findViewById(R.id.g1);
        dnk_sT=(EditText)dialogView.findViewById(R.id.drink_stime);
        dnk_n=(EditText)dialogView.findViewById(R.id.drink_notes);
        dnk_dur=(EditText)dialogView.findViewById(R.id.gDuration);
        dnk_eTime=(TextView) dialogView.findViewById(R.id.drink_etime);
        dnktp=(TimePicker) dialogView.findViewById(R.id.dnktp);
        dnktp2=(TimePicker)dialogView.findViewById(R.id.dnktp2);
        dnkdp=(DatePicker) dialogView.findViewById(R.id.dnkdp);
        dnksetStartingTime=(Button)dialogView.findViewById(R.id.dnksetStartingTime);
        dnksetEndingTime=(Button)dialogView.findViewById(R.id.dnksetStartingTime2);
        dnksetDate=(Button)dialogView.findViewById(R.id.dnksetDate);
        dnkDatePicker=(Button)dialogView.findViewById(R.id.dnkdatePicker);
        dnkTimePicker1=(Button)dialogView.findViewById(R.id.dnktimepicker1);
        dnkTimepicker2=(Button)dialogView.findViewById(R.id.dnktimepicker2);
        dnkField=(LinearLayout) dialogView.findViewById(R.id.drinkField);
        dnkTimeSelector=(LinearLayout)dialogView.findViewById(R.id.dnkTimeSelector);
        dnkDateSelector=(LinearLayout)dialogView.findViewById(R.id.dnkDateSelector);
        dnkTimeSelector2=(LinearLayout) dialogView.findViewById(R.id.dnkTimeSelector2);
        dnk_tag=(TextView)dialogView.findViewById(R.id.drinkEvent_tag);
        drink_spinner=(Spinner)dialogView.findViewById(R.id.drink_spinner);
       // gamesField=(LinearLayout) dialogView.findViewById(R.id.gamesField);*/

        xmasnameField= dialogView.findViewById(R.id.xmas_name_field);
        x_loc= dialogView.findViewById(R.id.xmas_location);
        x_d= dialogView.findViewById(R.id.xmas_date);
        x_eT= dialogView.findViewById(R.id.xmas_etime);
        x_sT= dialogView.findViewById(R.id.xmas_stime);
        x_n= dialogView.findViewById(R.id.xmas_notes);
        //x_dur=(EditText)dialogView.findViewById(R.id.xDuration);
       // x_eTime=(TextView) dialogView.findViewById(R.id.xirthday_etime);
        xtp= dialogView.findViewById(R.id.xtp);
        xtp2= dialogView.findViewById(R.id.xtp2);
        xdp= dialogView.findViewById(R.id.xdp);
        xsetStartingTime= dialogView.findViewById(R.id.xsetStartingTime);
        xsetEndingTime= dialogView.findViewById(R.id.xsetStartingTime2);
        xsetDate= dialogView.findViewById(R.id.xsetDate);
        xDatePicker= dialogView.findViewById(R.id.xdatePicker);
        xTimePicker1= dialogView.findViewById(R.id.xtimepicker1);
        xTimepicker2= dialogView.findViewById(R.id.xtimepicker2);
        xmasField= dialogView.findViewById(R.id.xmasField);
        xTimeSelector= dialogView.findViewById(R.id.xTimeSelector);
        xDateSelector= dialogView.findViewById(R.id.xDateSelector);
        xTimeSelector2= dialogView.findViewById(R.id.xTimeSelector2);
          x_invite_front= dialogView.findViewById(R.id.coverOfx_invite);
        getX_invite_inside= dialogView.findViewById(R.id.insideOfxInvite);
       x_tag= dialogView.findViewById(R.id.xmasEvent_tag);
        x_spinner= dialogView.findViewById(R.id.x_spinner);

        clubnameField= dialogView.findViewById(R.id.club_name_field);
        clb_loc= dialogView.findViewById(R.id.club_location);
        clb_d= dialogView.findViewById(R.id.club_date);
        // c_eT=(EditText) dialogView.findViewById(R.id.cine);
        clb_sT= dialogView.findViewById(R.id.club_stime);
        clb_n= dialogView.findViewById(R.id.club_notes);
        clb_dur= dialogView.findViewById(R.id.clbDuration);
        clb_eTime= dialogView.findViewById(R.id.club_etime);
        clbtp= dialogView.findViewById(R.id.clbtp);
        clbtp2= dialogView.findViewById(R.id.clbtp2);
        clbdp= dialogView.findViewById(R.id.clbdp);
        clbsetStartingTime= dialogView.findViewById(R.id.clbsetStartingTime);
        clbsetEndingTime= dialogView.findViewById(R.id.clbsetStartingTime2);
        clbsetDate= dialogView.findViewById(R.id.clbsetDate);
        clbDatePicker= dialogView.findViewById(R.id.clbdatePicker);
        clbTimePicker1= dialogView.findViewById(R.id.clbtimepicker1);
        clbTimepicker2= dialogView.findViewById(R.id.clbtimepicker2);
        club_spinner= dialogView.findViewById(R.id.clb_spinner);

        clbField= dialogView.findViewById(R.id.clubField);
        clbTimeSelector= dialogView.findViewById(R.id.clbTimeSelector);
        clbDateSelector= dialogView.findViewById(R.id.clbDateSelector);
        clbTimeSelector2= dialogView.findViewById(R.id.clbTimeSelector2);
        clb_tag= dialogView.findViewById(R.id.clubEvent_tag);

        shoppingnameField= dialogView.findViewById(R.id.shopping__name_field);
        sO_loc= dialogView.findViewById(R.id.shopping__location);
        sO_d= dialogView.findViewById(R.id.shopping_date);
        sO_eT= dialogView.findViewById(R.id.shopping__etime);
        sO_sT= dialogView.findViewById(R.id.shopping__stime);
        sO_n= dialogView.findViewById(R.id.shopping__notes);
        //x_dur=(EditText)dialogView.findViewById(R.id.xDuration);
        // x_eTime=(TextView) dialogView.findViewById(R.id.xirthday_etime);
        sOtp= dialogView.findViewById(R.id.sOtp);
        sOtp2= dialogView.findViewById(R.id.sOtp2);
        sOdp= dialogView.findViewById(R.id.sOdp);
        sOsetStartingTime= dialogView.findViewById(R.id.sOsetStartingTime);
        sOsetEndingTime= dialogView.findViewById(R.id.sOsetStartingTime2);
        sOsetDate= dialogView.findViewById(R.id.sOsetDate);
        sODatePicker= dialogView.findViewById(R.id.sOdatePicker);
        sOTimePicker1= dialogView.findViewById(R.id.sOtimepicker1);
        sOTimepicker2= dialogView.findViewById(R.id.sOtimepicker2);
        sOverField= dialogView.findViewById(R.id.shopping_Field);
        sOTimeSelector= dialogView.findViewById(R.id.sOTimeSelector);
        sODateSelector= dialogView.findViewById(R.id.sODateSelector);
        sOTimeSelector2= dialogView.findViewById(R.id.sOTimeSelector2);
        //=(ImageButton)dialogView.findViewById(R.id.coverOfsO_invite);
      //  getsO_invite_inside=(ImageButton)dialogView.findViewById(R.id.insideOfsOInvite);
        sOtag= dialogView.findViewById(R.id.shopping_Event_tag);
        sO_spinner= dialogView.findViewById(R.id.sO_spinner);
        if(type=='O'){
        rv= dialogView.findViewById(R.id.listviewer);
        rv.setLayoutManager(new LinearLayoutManager(this));
        LoadMyLists();}

        ddatenameField= dialogView.findViewById(R.id.ddate__name_field);
        d_loc= dialogView.findViewById(R.id.ddate__location);
        d_d= dialogView.findViewById(R.id.ddate_date);
        d_eT= dialogView.findViewById(R.id.ddate__etime);
        d_sT= dialogView.findViewById(R.id.ddate__stime);
        d_n= dialogView.findViewById(R.id.ddate__notes);
        //x_dur=(EditText)dialogView.findViewById(R.id.xDuration);
        // x_eTime=(TextView) dialogView.findViewById(R.id.xirthday_etime);
        dtp= dialogView.findViewById(R.id.dtp);
        dtp2= dialogView.findViewById(R.id.dtp2);
        ddp= dialogView.findViewById(R.id.ddp);
        dsetStartingTime= dialogView.findViewById(R.id.dsetStartingTime);
        dsetEndingTime= dialogView.findViewById(R.id.dsetStartingTime2);
        dsetDate= dialogView.findViewById(R.id.dsetDate);
        dDatePicker= dialogView.findViewById(R.id.ddatePicker);
        dTimePicker1= dialogView.findViewById(R.id.dtimepicker1);
        dTimepicker2= dialogView.findViewById(R.id.dtimepicker2);
        ddateField= dialogView.findViewById(R.id.ddate_Field);
        dTimeSelector= dialogView.findViewById(R.id.dTimeSelector);
        dDateSelector= dialogView.findViewById(R.id.dDateSelector);
        dTimeSelector2= dialogView.findViewById(R.id.dTimeSelector2);
        dtag= dialogView.findViewById(R.id.ddate_Event_tag);
        d_spinner= dialogView.findViewById(R.id.d_spinner);

        awardnameField= dialogView.findViewById(R.id.award_name_field);
        a_loc= dialogView.findViewById(R.id.award_location);
        a_d= dialogView.findViewById(R.id.a_date);
        a_eT= dialogView.findViewById(R.id.award_etime);
        a_sT= dialogView.findViewById(R.id.award_stime);
        a_n= dialogView.findViewById(R.id.award_notes);
        //x_dur=(EditText)dialogView.findViewById(R.id.xDuration);
        // x_eTime=(TextView) dialogView.findViewById(R.id.xirthday_etime);
       atp= dialogView.findViewById(R.id.atp);
       atp2= dialogView.findViewById(R.id.atp2);
       adp= dialogView.findViewById(R.id.adp);
       asetStartingTime= dialogView.findViewById(R.id.asetStartingTime);
       asetEndingTime= dialogView.findViewById(R.id.asetStartingTime2);
       asetDate= dialogView.findViewById(R.id.asetDate);
       aDatePicker= dialogView.findViewById(R.id.aPicker);
       aTimePicker1= dialogView.findViewById(R.id.atimepicker1);
       aTimepicker2= dialogView.findViewById(R.id.atimepicker2);
        awardField= dialogView.findViewById(R.id.a_Field);
       aTimeSelector= dialogView.findViewById(R.id.aTimeSelector);
       aDateSelector= dialogView.findViewById(R.id.aDateSelector);
       aTimeSelector2= dialogView.findViewById(R.id.aTimeSelector2);
       atag= dialogView.findViewById(R.id.a_Event_tag);
       a_spinner= dialogView.findViewById(R.id.a_spinner);

        weddingnameField= dialogView.findViewById(R.id.wedding_name_field);
        w_loc= dialogView.findViewById(R.id.w_location);
        w_d= dialogView.findViewById(R.id.w_date);
        w_eT= dialogView.findViewById(R.id.w_etime);
        w_sT= dialogView.findViewById(R.id.w_stime);
        w_n= dialogView.findViewById(R.id.w_notes);
        //x_dur=(EditText)dialogView.findViewById(R.id.xDuration);
        // x_eTime=(TextView) dialogView.findViewById(R.id.xirthday_etime);
        wtp= dialogView.findViewById(R.id.wtp);
        wtp2= dialogView.findViewById(R.id.wtp2);
        wdp= dialogView.findViewById(R.id.wdp);
        wsetStartingTime= dialogView.findViewById(R.id.wsetStartingTime);
        wsetEndingTime= dialogView.findViewById(R.id.wsetStartingTime2);
        wsetDate= dialogView.findViewById(R.id.wsetDate);
        wDatePicker= dialogView.findViewById(R.id.wPicker);
        wTimePicker1= dialogView.findViewById(R.id.wtimepicker1);
        wTimepicker2= dialogView.findViewById(R.id.wtimepicker2);
        weddingField= dialogView.findViewById(R.id.w_Field);
        wTimeSelector= dialogView.findViewById(R.id.wTimeSelector);
        wDateSelector= dialogView.findViewById(R.id.wDateSelector);
        wTimeSelector2= dialogView.findViewById(R.id.wTimeSelector2);
        wtag= dialogView.findViewById(R.id.w_Event_tag);
        w_spinner= dialogView.findViewById(R.id.w_spinner);
        w_invite_front= dialogView.findViewById(R.id.coverOfw_invite);
        getW_invite_inside= dialogView.findViewById(R.id.insideOfwInvite);

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timeSelector.setOnTimeChangedListener((timePicker, i, i1) ->

            {setsTime.setText(getString(R.string.setstarttime,timeSelector.getHour()+":"+timeSelector.getMinute()));startTime=timeSelector.getHour()+":"+timeSelector.getMinute();});
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timeSelector2.setOnTimeChangedListener((timePicker, i, i1) ->

            {seteTime.setText(getString(R.string.setTime,timeSelector2.getHour()+":"+timeSelector2.getMinute()));endTime=timeSelector2.getHour()+":"+timeSelector2.getMinute();});
        }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                datePicker.setOnDateChangedListener((d, i, i1, i2) ->
                {setDate.setText(getString(R.string.setDate,datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear()));date=datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear();});
            }*/


        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

            if(key==1)
            {
                bballBtn.setOnClickListener(e->{insertText(sportnameField,"Basketball");});
                soccerBtn.setOnClickListener(e->{insertText(sportnameField,"Soccer");});
                rugbyBtn.setOnClickListener(e->{insertText(sportnameField,"Rugby");});
                mmaBtn.setOnClickListener(e->{insertText(sportnameField,"M.M.A");});
                boxingBtn.setOnClickListener(e->{insertText(sportnameField,"Boxing");});
                cancl= dialogView.findViewById(R.id.cancel);
                save= dialogView.findViewById(R.id.social_save_s);
                save.setEnabled(isCompleted(sportnameField,s_loc,s_d,s_sT,s_eT,s_n,save,"Unknown Sport"));
                save.setOnClickListener(e->{handleSaveButtonForAll(sportnameField,s_loc,s_d,s_sT,s_eT,s_n,null,null,null,DateSelector,TimeSelector,TimeSelector2,timeSelector,timeSelector2,datePicker,sport,s_tag,"Sport",s_spinner);alertDialog.dismiss();});
                handleTemplatestuff(sportnameField,s_loc,s_d,s_sT,s_eT,s_n,null,null,null,DateSelector,TimeSelector,TimeSelector2,timeSelector,timeSelector2,datePicker,sport,s_tag,"Sport",s_spinner);
                selectDay(s_m,s_t,s_w,s_th,s_f,s_st,s_sd,s_d);
            }
            else if(key==2)
            {
                save= dialogView.findViewById(R.id.social_save_b);
                save.setEnabled(isCompleted(birthdaynameField,b_loc,b_d,b_sT,b_eT,b_n,save,"Unknown Birthday"));
                cancl= dialogView.findViewById(R.id.cancel3);
                save.setOnClickListener(e->{handleSaveButtonForAll(birthdaynameField,b_loc,b_d,b_sT,b_eT,b_n,null,b_invite_front,getB_invite_inside,bDateSelector,bTimeSelector,bTimeSelector2,btp,btp2,bdp,birthday,b_tag,"Birthday",b_spinner);alertDialog.dismiss();});
                handleTemplatestuff(birthdaynameField,b_loc,b_d,b_sT,b_eT,b_n,null,b_invite_front,getB_invite_inside,bDateSelector,bTimeSelector,bTimeSelector2,btp,btp2,bdp,birthday,b_tag,"Birthday",b_spinner);
                selectDay(b_m,b_t,b_w,b_th,b_f,b_st,b_sd,b_d);
                setInvites(b_invite_front,getB_invite_inside);
            }
            else if(key==3)
            {
                save= dialogView.findViewById(R.id.social_save_c);
                cancl= dialogView.findViewById(R.id.cancel2);
                save.setEnabled(isCompleted(cinemanameField,c_loc,c_d,c_sT,c_dur,c_n,save,"Unknown Movie"));
                save.setOnClickListener(e->{handleSaveButtonForAll(cinemanameField,c_loc,c_d,c_sT,c_eT,c_n,c_dur,null,null,cDateSelector,cTimeSelector,cTimeSelector2,ctp,ctp2,cdp,cinema,c_tag,"Cinema",c_spinner);alertDialog.dismiss();});
                handleTemplatestuff(cinemanameField,c_loc,c_d,c_sT,c_dur,c_n,c_eTime,null,null,cDateSelector,cTimeSelector,cTimeSelector2,ctp,ctp2,cdp,cinema,c_tag,"Cinema",c_spinner);
                selectDay(c_m,c_t,c_w,c_th,c_f,c_st,c_sd,c_d);
            }
            else if(key==4)
            {
                save= dialogView.findViewById(R.id.social_save_g);
                cancl= dialogView.findViewById(R.id.cancel4);
                save.setEnabled(isCompleted(gamingnameField,g_loc,g_d,g_sT,g_dur,g_n,save,"Unknown Game"));
                save.setOnClickListener(e->{handleSaveButtonForAll(gamingnameField,g_loc,g_d,g_sT,g_eT,g_n,g_dur,null,null,gDateSelector,gTimeSelector,gTimeSelector2,gtp,gtp2,gdp,gaming,g_tag,"Gaming",g_spinner);alertDialog.dismiss();});
                handleTemplatestuff(gamingnameField,g_loc,g_d,g_sT,g_dur,g_n,g_eTime,null,null,gDateSelector,gTimeSelector,gTimeSelector2,gtp,gtp2,gdp,gaming,g_tag,"Gaming",g_spinner);
                selectDay(g_m,g_t,g_w,g_th,g_f,g_st,g_sd,g_d);
                addMoreItems(gamesField,addG,removeG,"Game",g_tag);
            }
            else if(key==5)
            {
                save= dialogView.findViewById(R.id.social_save_x);
                save.setEnabled(isCompleted(xmasnameField,x_loc,x_d,x_sT,x_eT,x_n,save,"Christmas"));
                cancl= dialogView.findViewById(R.id.cancel3);
                save.setOnClickListener(e->{handleSaveButtonForAll(xmasnameField,x_loc,x_d,x_sT,x_eT,x_n,null,x_invite_front,getX_invite_inside,xDateSelector,xTimeSelector,xTimeSelector2,xtp,xtp2,xdp,xmas,x_tag,"Christmas",x_spinner);alertDialog.dismiss();});
                handleTemplatestuff(xmasnameField,x_loc,x_d,x_sT,x_eT,x_n,null,x_invite_front,getX_invite_inside,xDateSelector,xTimeSelector,xTimeSelector2,xtp,xtp2,xdp,xmas,x_tag,"Christmas",x_spinner);
                selectDay(x_m,x_t,x_w,x_th,x_f,x_st,x_sd,x_d);
                setInvites(x_invite_front,getX_invite_inside);
            }
            else if(key==6)
            {
                save= dialogView.findViewById(R.id.social_save_sO);
                save.setEnabled(isCompleted(shoppingnameField,sO_loc,sO_d,sO_sT,sO_eT,sO_n,save,"Sleep-over"));
                cancl= dialogView.findViewById(R.id.cancel3);
                save.setOnClickListener(e->{handleSaveButtonForAll(shoppingnameField,sO_loc,sO_d,sO_sT,sO_eT,sO_n,null,null,null,sODateSelector,sOTimeSelector,sOTimeSelector2,sOtp,sOtp2,sOdp,shopping,sOtag,"Shopping",sO_spinner);alertDialog.dismiss();});
                handleTemplatestuff(shoppingnameField,sO_loc,sO_d,sO_sT,sO_eT,sO_n,null,null,null,sODateSelector,sOTimeSelector,sOTimeSelector2,sOtp,sOtp2,sOdp,shopping,sOtag,"Shopping",sO_spinner);
                selectDay(sO_m,sO_t,sO_w,sO_th,sO_f,sO_st,sO_sd,sO_d);
                //setInvites(null,null);
            }
            else if(key==7)
            {
                save= dialogView.findViewById(R.id.social_save_d);
                save.setEnabled(isCompleted(ddatenameField,d_loc,d_d,d_sT,d_eT,d_n,save,"Date"));
                cancl= dialogView.findViewById(R.id.cancel3);
                save.setOnClickListener(e->{handleSaveButtonForAll(ddatenameField,d_loc,d_d,d_sT,d_eT,d_n,null,null,null,dDateSelector,dTimeSelector,dTimeSelector2,dtp,dtp2,ddp,dating,dtag,"Dating",d_spinner);alertDialog.dismiss();});
                handleTemplatestuff(ddatenameField,d_loc,d_d,d_sT,d_eT,d_n,null,null,null,dDateSelector,dTimeSelector,dTimeSelector2,dtp,dtp2,ddp,shopping,dtag,"Dating",d_spinner);
                selectDay(d_m,d_t,d_w,d_th,d_f,d_st,d_sd,d_d);
               // setInvites(d_invite_front,getd_invite_inside);
            }
            else if(key==8)
            {
                save= dialogView.findViewById(R.id.social_save_a);
                save.setEnabled(isCompleted(awardnameField,a_loc,a_d,a_sT,a_eT,a_n,save,"Awards ceremony"));
                cancl= dialogView.findViewById(R.id.cancel3);
                save.setOnClickListener(e->{handleSaveButtonForAll(awardnameField,a_loc,a_d,a_sT,a_eT,a_n,null,null,null,aDateSelector,aTimeSelector,aTimeSelector2,atp,atp2,adp,award,atag,"Awards ceremony",a_spinner);alertDialog.dismiss();});
                handleSaveButtonForAll(awardnameField,a_loc,a_d,a_sT,a_eT,a_n,null,null,null,aDateSelector,aTimeSelector,aTimeSelector2,atp,atp2,adp,shopping,atag,"Awards ceremony",a_spinner);
                selectDay(a_m,a_t,a_w,a_th,a_f,a_st,a_sd,a_d);
                // setInvites(d_invite_front,getd_invite_inside);
            }
            else if(key==9)
            {
                save= dialogView.findViewById(R.id.social_save_w);
                save.setEnabled(isCompleted(weddingnameField,w_loc,w_d,w_sT,w_eT,w_n,save,"Wedding"));
                cancl= dialogView.findViewById(R.id.cancel3);
                save.setOnClickListener(e->{handleSaveButtonForAll(weddingnameField,w_loc,w_d,w_sT,w_eT,w_n,null,b_invite_front,getB_invite_inside,wDateSelector,wTimeSelector,wTimeSelector2,wtp,wtp2,wdp,wedding,wtag,"Wedding",w_spinner);alertDialog.dismiss();});
                handleSaveButtonForAll(weddingnameField,w_loc,w_d,w_sT,w_eT,w_n,null,b_invite_front,getB_invite_inside,wDateSelector,wTimeSelector,wTimeSelector2,wtp,wtp2,wdp,shopping,wtag,"Wedding",w_spinner);
                selectDay(w_m,w_t,w_w,w_th,w_f,w_st,w_sd,w_d);
                 setInvites(b_invite_front,getW_invite_inside);
            }
            else if(key==10)
            {
                save= dialogView.findViewById(R.id.social_save_clb);
                cancl= dialogView.findViewById(R.id.cancel4);
                save.setEnabled(isCompleted(clubnameField,clb_loc,clb_d,clb_sT,clb_dur,clb_n,save,"Unknown Event"));
                save.setOnClickListener(e->{handleSaveButtonForAll(clubnameField,clb_loc,clb_d,clb_sT,clb_eT,clb_n,clb_dur,null,null,clbDateSelector,clbTimeSelector,clbTimeSelector2,clbtp,clbtp2,clbdp,clubing,clb_tag,"Clubing",club_spinner);alertDialog.dismiss();});
                handleSaveButtonForAll(clubnameField,clb_loc,clb_d,clb_sT,clb_eT,clb_n,clb_dur,null,null,clbDateSelector,clbTimeSelector,clbTimeSelector2,clbtp,clbtp2,clbdp,clubing,clb_tag,"Clubing",club_spinner);
                selectDay(clb_m,clb_t,clb_w,clb_th,clb_f,clb_st,clb_sd,clb_d);
            }
           /* else if(key==11)
            {
                save=(Button)dialogView.findViewById(R.id.social_save_dnk);
                cancl=(Button)dialogView.findViewById(R.id.cancel2);
                save.setEnabled(isCompleted(drinknameField,dnk_loc,dnk_d,dnk_sT,dnk_dur,dnk_n,save,"Unknown Event"));
                save.setOnClickListener(e->{handleSaveButtonForAll(drinknameField,dnk_loc,dnk_d,dnk_sT,dnk_eT,dnk_n,dnk_dur,null,null,dnkDateSelector,dnkTimeSelector,dnkTimeSelector2,dnktp,dnktp2,dnkdp,drink,dnk_tag,"Drink",dnk_spinner);alertDialog.dismiss();});
                handleSaveButtonForAll(drinknameField,dnk_loc,dnk_d,dnk_sT,dnk_eT,dnk_n,dnk_dur,null,null,dnkDateSelector,dnkTimeSelector,dnkTimeSelector2,dnktp,dnktp2,dnkdp,drink,dnk_tag,"Drinking",dnk_spinner);         selectDay(c_m,c_t,c_w,c_th,c_f,c_st,c_sd,c_d);
                selectDay(dnk_m,dnk_t,dnk_w,dnk_th,dnk_f,dnk_st,dnk_sd,dnk_d);
            }*/

       if (key==1)
               setUpAGeneralEventHandlerForTheTimeSelection(selectDate,selectTime,selecttTme2,setDate,setsTime,seteTime,timeSelector,sportField,DateSelector,TimeSelector,TimeSelector2,timeSelector2,datePicker,s_d,s_sT,s_eT,null,save);
       if(key==2)
            setUpAGeneralEventHandlerForTheTimeSelection(bDatePicker,bTimePicker1,bTimepicker2,bsetDate,bsetStartingTime,bsetEndingTime,btp,birthdayField,bDateSelector,bTimeSelector,bTimeSelector2,btp2,bdp,b_d,b_sT,b_eT,null,save);
            else if(key==3)
                setUpAGeneralEventHandlerForTheTimeSelection(cDatePicker,cTimePicker1,cTimepicker2,csetDate,csetStartingTime,csetEndingTime,ctp,cinemaField,cDateSelector,cTimeSelector,cTimeSelector2,ctp2,cdp,c_d,c_sT,c_dur,c_eTime,save);
       else if(key==4)
           setUpAGeneralEventHandlerForTheTimeSelection(gDatePicker,gTimePicker1,gTimepicker2,gsetDate,gsetStartingTime,gsetEndingTime,gtp,gamingField,gDateSelector,gTimeSelector,gTimeSelector2,gtp2,gdp,g_d,g_sT,g_dur,g_eTime,save);
        if(key==5)
            setUpAGeneralEventHandlerForTheTimeSelection(xDatePicker,xTimePicker1,xTimepicker2,xsetDate,xsetStartingTime,xsetEndingTime,xtp,xmasField,xDateSelector,xTimeSelector,xTimeSelector2,xtp2,xdp,x_d,x_sT,x_eT,null,save);
        if(key==6)
            setUpAGeneralEventHandlerForTheTimeSelection(sODatePicker,sOTimePicker1,sOTimepicker2,sOsetDate,sOsetStartingTime,sOsetEndingTime,sOtp,sOverField,sODateSelector,sOTimeSelector,sOTimeSelector2,sOtp2,sOdp,sO_d,sO_sT,sO_eT,null,save);

        if(key==7)
            setUpAGeneralEventHandlerForTheTimeSelection(dDatePicker,dTimePicker1,dTimepicker2,dsetDate,dsetStartingTime,dsetEndingTime,dtp,ddateField,dDateSelector,dTimeSelector,dTimeSelector2,dtp2,ddp,d_d,d_sT,d_eT,null,save);
        if(key==8)
            setUpAGeneralEventHandlerForTheTimeSelection(aDatePicker,aTimePicker1,aTimepicker2,asetDate,asetStartingTime,asetEndingTime,atp,awardField,aDateSelector,aTimeSelector,aTimeSelector2,atp2,adp,a_d,a_sT,a_eT,null,save);
        if(key==9)
            setUpAGeneralEventHandlerForTheTimeSelection(wDatePicker,wTimePicker1,wTimepicker2,wsetDate,wsetStartingTime,wsetEndingTime,wtp,weddingField,wDateSelector,wTimeSelector,wTimeSelector2,wtp2,wdp,clb_d,w_sT,w_eT,null,save);
        if(key==10)
            setUpAGeneralEventHandlerForTheTimeSelection(clbDatePicker,clbTimePicker1,clbTimepicker2,clbsetDate,clbsetStartingTime,clbsetEndingTime,clbtp,clbField,clbDateSelector,clbTimeSelector,clbTimeSelector2,clbtp2,clbdp,clb_d,clb_sT,clb_dur,clb_eTime,save);
        if(key==11)
            setUpAGeneralEventHandlerForTheTimeSelection(dnkDatePicker,dnkTimePicker1,dnkTimepicker2,dnksetDate,dnksetStartingTime,dnksetEndingTime,dnktp,dnkField,dnkDateSelector,dnkTimeSelector,dnkTimeSelector2,dnktp2,dnkdp,clb_d,dnk_sT,dnk_eT,null,save);


        cancl.setOnClickListener(e->{alertDialog.dismiss();});

       /// default_gi.setOnClickListener(e->{alertDialog.hide();});
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton imgB;
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
            imgB.setImageBitmap(imageBitmap);
        }
    }
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Invite_" + timeStamp + "_";
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
    public  void getAll(int year, long date,Social soc) {
        SimpleDateFormat sd1= new SimpleDateFormat("EE");
        String day=sd1.format(date);
        String p1 = "MON|MONDAY|M", p2 = "TUES|TUESDAY|TU", p3 = "WED|WED|W", p4 = "THURS|THUR|TH|THURSDAY", p5 = "FRI|FRIDAY|F", p6 = "SAT|SATURDAY|ST", p7 = "SUN,SUNDAY,SN";
        if(day.toUpperCase().matches(p1))
        {
            for (String s : getAllMondaysInAyear(year,  1, "monday")) {
                Allevents.add(soc);

            }
        }
        else if(day.toUpperCase().matches(p2))
        {
            for (String s : getAllMondaysInAyear(year,  2, "tuesday")) {
                Allevents.add(soc);

            }
        }
        else if(day.toUpperCase().matches(p3))
        {
            for (String s : getAllMondaysInAyear(year,  3, "wednesday")) {
                Allevents.add(soc);

            }
        }
        else if(day.toUpperCase().matches(p4))
        {
            for (String s : getAllMondaysInAyear(year,  4, "thursday")) {
                Allevents.add(soc);

            }
        }
        else if(day.toUpperCase().matches(p5))
        {
            for (String s : getAllMondaysInAyear(year,  5, "friday")) {
                Allevents.add(soc);

            }
        }
        else if(day.toUpperCase().matches(p6))
        {
            for (String s : getAllMondaysInAyear(year,  6, "saturday")) {
                Allevents.add(soc);

            }
        }
        else if(day.toUpperCase().matches(p7))
        {
            for (String s : getAllMondaysInAyear(year,  7, "sunday")) {
                Allevents.add(soc);

            }
        }


        //  ArrayList<String> daysImMonths = new ArrayList<String>();

        // String versions2[]=new String[]{"3-4","1-2","6-8","8-10"};
        // String version3[]=new String[]{"Version 1.0","Version 2.0","Version 3.0"};

    }


    public  void getAllDays(int year, long date,Social soc) {
        SimpleDateFormat sd1= new SimpleDateFormat("EE");
        SimpleDateFormat sd2= new SimpleDateFormat("MMM");


        String day=sd1.format(date);
        String month=sd2.format(date);
        String p1 = "MON|MONDAY|M", p2 = "TUES|TUESDAY|TU", p3 = "WED|WED|W", p4 = "THURS|THUR|TH|THURSDAY", p5 = "FRI|FRIDAY|F", p6 = "SAT|SATURDAY|ST", p7 = "SUN|SUNDAY|SN";
        String  m1="JAN|JANUARY|J",m2="FEB|FEBRUARY|F",m3="MARCH|MAR",m4="APRIL|APR",m5="MAY",m6="JUNE|JUN",m7="JULY|JUL",m8="AUGUST|AUG",m9="SEPTEMBER|SEP",m10="OCTOBER|OCT",m11="NOVEMBER|NOV",m12="DECEMBER|DEC";
        //System.out.println()(day);//System.out.println()(month);
        String days[]=new String[7];String months[]=new String[12];
        days[0]=p1;
        days[1]=p2;
        days[2]=p3;
        days[3]=p4;
        days[4]=p5;
        days[5]=p6;
        days[6]=p7;

        months[0]=m1;
        months[1]=m2;
        months[2]=m3;
        months[3]=m4;
        months[4]=m5;
        months[5]=m6;
        months[6]=m7;
        months[7]=m8;
        months[8]=m9;
        months[9]=m10;
        months[10]=m11;
        months[11]=m12;


        month=month.toUpperCase();
        day=day.toUpperCase();
        //System.out.println()("Month: "+month+"\nDay: "+day);

        for(int i=0; i<months.length;i++)
        {
            //System.out.println()("Current Month: " +months[i]);
            //for(int k=0;k<71;k++)
            for(int j=0;j<days.length;j++)
            {
                //System.out.println()("Current Day: " +days[j]);
                if(month.toUpperCase().matches(months[i]))
                {
                    if(day.toUpperCase().matches(days[j]))
                    {
                        for (String s : getAllMondaysInAMonth(year, j+1, i+1)) {
                            Allevents.add(soc);

                        }
                    }
                }
            }


        }
    }



    public List<String> getAllMondaysInAMonth(int year, int day,int mon) {


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



            //System.out.println()("Number of x : " + mondays);


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

            //System.out.println()("Number of x : " + mondays);


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
    private void saveToDatabase(Social event)
    {
        CreatedEvents ev;
         ref.setValue(event);
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        if(event.getOccurance().equalsIgnoreCase("Monthly"))
        {
            getAll(2018,ConvertFromDateToLong(event.getDate()),event);
            for (int i = 0; i < Allevents.size(); i++)

            {

                ev = new CreatedEvents(Allevents.get(i).getDate(), Allevents.get(i).getType(), Allevents.get(i).getLocation(), ConvertFromDateToLong(Allevents.get(i).getStartTime()), ConvertFromDateToLong(Allevents.get(i).getEndTime()),Allevents.get(i).getVal8(),"SOCIAL", Allevents.get(i).getDiscription());

                firestore.collection(UID)
                        .add(Allevents.get(i))
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
                                Log.w(TAG, "Error adding event documtent", e);
                           /* Toast.makeText(getActivity(),
                                    "Event document could not be added",
                                    Toast.LENGTH_SHORT).show();*/
                            }
                        });


            }
        }
        else if(event.getOccurance().equalsIgnoreCase("Weekly"))
        {

                getAll(2018,ConvertFromDateToLong(event.getDate()),event);


            for (int i = 0; i < Allevents.size(); i++)

            {

                    ev = new CreatedEvents(Allevents.get(i).getDate(), Allevents.get(i).getType(), Allevents.get(i).getLocation(), ConvertFromDateToLong(Allevents.get(i).getStartTime()), ConvertFromDateToLong(Allevents.get(i).getEndTime()),Allevents.get(i).getVal8(),"SOCIAL", Allevents.get(i));

            firestore.collection(UID)
                    .add(Allevents.get(i))
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
                            Log.w(TAG, "Error adding event documtent", e);
                           /* Toast.makeText(getActivity(),
                                    "Event document could not be added",
                                    Toast.LENGTH_SHORT).show();*/
                        }
                    });


        }
        }
        else if(event.getOccurance().equalsIgnoreCase("Once"))
        {
            ev = new CreatedEvents(event.getDate(), event.getType(), event.getLocation(), ConvertFromDateToLong(event.getStartTime()), ConvertFromDateToLong(event.getEndTime()),event.getVal8(),"SOCIAL", event);

            firestore.collection(UID)
                    .add(event)
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
                            Log.w(TAG, "Error adding event documtent", e);
                           /* Toast.makeText(getActivity(),
                                    "Event document could not be added",
                                    Toast.LENGTH_SHORT).show();*/
                        }
                    });
        }



    }
    private Long ConvertFromDateToLong(String date) {
        long milliseconds = System.currentTimeMillis();
        //System.out.println()("system Time: " + milliseconds);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
            //System.out.println()("Date Time: " + milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
    private void setPic(ImageButton mImageView) {
        // Get the dimensions of the View
        mImageView=imgB;
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
    EditText[]itemViews = new EditText[20];
    int j;
    private void addMoreItems(LinearLayout ll, Button add, Button remove,TextView tag) {



        for (j = 0; j < itemViews.length; j++) {
            itemViews[j] = new EditText(this);
            itemViews[j].setHint("New Subject");
            itemViews[j].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            itemViews[j].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // G2P =et.getText().toString();

                    //System.out.println()("J:"+j);
                    if (!TextUtils.isEmpty(charSequence))
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4] + "\n" + charSequence+ " | ");


                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });
        }

        add.setOnClickListener(e -> {
            remove.setEnabled(true);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if (count < itemViews.length) {
                ll.addView(itemViews[count], lp);
                // //toastMessage((count+1)+"/"+max,Toast.LENGTH_SHORT);
                count++;
                //System.out.println()("count more " + count);

            } else {
                add.setEnabled(false);
            }
        });
        remove.setOnClickListener(e -> {
            add.setEnabled(true);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if (count > 0) {
                //System.out.println()(count);
                ll.removeView(itemViews[count - 1]);
                itemViews[count - 1].setText("");

                ////toastMessage((count+1)+"/"+max,Toast.LENGTH_SHORT);
                count--;
                //System.out.println()("Count less" + count);
                remove.setBackgroundColor(getResources().getColor(R.color.save_red));
                remove.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_light));


            } else {

                remove.setEnabled(false);
                remove.setBackgroundColor(Color.rgb(179, 0, 0));
                remove.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
            }
        });
    }
    private void addMoreItems(LinearLayout ll,Button add, Button remove, String atag,TextView tag)
    {
        Button myButton = new Button(this);
        //Typeface type = Typeface.createFromAsset(getAssets(),"fonts/aldrich.ttf");

        myButton.setText("New");
        EditText et=new EditText(this);
        et.setHint(atag+" "+2);
        et.setTextColor(getResources().getColor(R.color.colorPrimary));
        //et.setTypeface(type);
        //et.setPadding(0,4,0,0);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // G2P =et.getText().toString();

                if(!TextUtils.isEmpty(et.getText().toString()))
                    tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]+"\n"+gameOne.getText().toString()+" | "+g2.getText().toString()+" | "+g3.getText().toString()+" | "+g4.getText().toString()+" | "+g5.getText().toString());

            }
            @Override
            public void afterTextChanged(Editable editable) {
                //t.getText().toString()+"-";

            }
        });

        myButton.setText("New");
        EditText et2=new EditText(this);
        et2.setHint(atag+" "+3);
        et2.setTextColor(getResources().getColor(R.color.colorPrimary));
       // et2.setTypeface(type);.
        //et2.setPadding(0,4,0,0);
        gameOne.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


           

            if(!TextUtils.isEmpty(gameOne.getText().toString()))
                tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]+"\n"+gameOne.getText().toString()+" | "+g2.getText().toString()+" | "+g3.getText().toString()+" | "+g4.getText().toString()+" | "+g5.getText().toString());

        }

        @Override
        public void afterTextChanged(Editable editable) {
            G2P +=gameOne.getText().toString()+"-";

        }
    });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               
                if(!TextUtils.isEmpty(et2.getText().toString()))
                    tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]+"\n"+gameOne.getText().toString()+" | "+g2.getText().toString()+" | "+g3.getText().toString()+" | "+g4.getText().toString()+" | "+g5.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //t2.getText().toString()+"-";


            }
        });


        myButton.setText("New");
        EditText et3=new EditText(this);
        et3.setHint(atag+" "+4);
        et3.setTextColor(getResources().getColor(R.color.colorPrimary));
       //// et3.setTypeface(type);
       // et3.setPadding(0,4,0,0);
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              
                if(!TextUtils.isEmpty(et3.getText().toString()))
                    tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]+"\n"+gameOne.getText().toString()+" | "+g2.getText().toString()+" | "+g3.getText().toString()+" | "+g4.getText().toString()+" | "+g5.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //t3.getText().toString()+"-";

            }
        });


        myButton.setText("New");
        EditText et4=new EditText(this);
        et4.setHint(atag+" "+5);
        et4.setTextColor(getResources().getColor(R.color.colorPrimary));
       // et4.setTypeface(type);
        //et4.setPadding(0,4,0,0);
       et4.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //G2P =et2.getText().toString();

            if(!TextUtils.isEmpty(et4.getText().toString()))
                tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]+"\n"+gameOne.getText().toString()+" | "+g2.getText().toString()+" | "+g3.getText().toString()+" | "+g4.getText().toString()+" | "+g5.getText().toString());

        }

        @Override
        public void afterTextChanged(Editable editable) {
            ////t.getText().toString()+"-";

        }
    });

        myButton.setText("New");
        EditText et5=new EditText(this);
        et5.setHint(atag+" "+5);
        et5.setTextColor(getResources().getColor(R.color.colorPrimary));
       // et5.setTypeface(type);
        et5.setPadding(0,4,0,0);
        g2=et;
        g3=et2;
        g4=et3;
        g5=et4;
        EditText []ets=new EditText[4];
        ets[0]=et;
        ets[1]=et2;
        ets[2]=et3;
        ets[3]=et4;
        add.setOnClickListener(e->{
            remove.setEnabled(true);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if(count<ets.length)
            {
                ll.addView(ets[count], lp);
               // //toastMessage((count+1)+"/"+max,Toast.LENGTH_SHORT);
                count++;
                //System.out.println()("count more "+count);

            }
            else {
                //toastMessage("Max Reached!",Toast.LENGTH_SHORT);
                add.setEnabled(false);
            }
        });
        remove.setOnClickListener(e->{
            add.setEnabled(true);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if(count>0)
            {
                //System.out.println()(count);
                ll.removeView(ets[count-1]);
                ets[count-1].setText("");
                tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]+"\n"+gameOne.getText().toString()+" | "+g2.getText().toString()+" | "+g3.getText().toString()+" | "+g4.getText().toString()+" | "+g5.getText().toString());

                ////toastMessage((count+1)+"/"+max,Toast.LENGTH_SHORT);
                count--;
                //System.out.println()("Count less"+count);
                remove.setBackgroundColor(getResources().getColor(R.color.save_red));
                remove.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_light));


            }
            else {
                //toastMessage("Min Reached!",Toast.LENGTH_SHORT);
                remove.setEnabled(false);
                remove.setBackgroundColor(Color.rgb(179,0,0));
                remove.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
            }
        });


    }
    private void setInvites(ImageButton ib,ImageButton ib2)
    {
        ib.setOnClickListener(e->{dispatchTakePictureIntent();imgB=ib;setPic(ib);});
        ib2.setOnClickListener(e->{dispatchTakePictureIntent();imgB=ib2;setPic(ib2);});
    }
    private boolean checkIfCompleted(AutoCompleteTextView et, EditText et2, EditText et3, EditText et4, EditText et5, EditText et6,Button saveBtn,String x)
    {


        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               done= isCompleted(et,et2,et3,et4,et5,et6,saveBtn,x);

               if(!(et.getText().toString().equals("")||et==null))
                   et3.setText(TodaysDate);
                   //et3.setError("If Title is Entered a Date is required");
                else et3.getText().clear() ;


               
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               done=isCompleted(et,et2,et3,et4,et5,et6,saveBtn,x);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return  done;
    }
    private boolean isCompleted(AutoCompleteTextView et, EditText et2, EditText et3, EditText et4, EditText et5, EditText et6,Button saveBtn,String x) {
        if (TextUtils.isEmpty(et.getText().toString()) &&
                TextUtils.isEmpty(et2.getText().toString()) &&
                TextUtils.isEmpty(et3.getText().toString()) &&
                TextUtils.isEmpty(et4.getText().toString()) &&
                TextUtils.isEmpty(et5.getText().toString()) &&
                TextUtils.isEmpty(et6.getText().toString())) {
            saveBtn.setBackground(getResources().getDrawable(R.color.save_green));
            saveBtn.setEnabled(true);
           saveBtn.setOnClickListener(e->toastMessage("No Data Saved ", Toast.LENGTH_SHORT));
            return true;
        } else {
            if (!TextUtils.isEmpty(et.getText().toString())) {
                if (!TextUtils.isEmpty(et3.getText().toString())) {
                    saveBtn.setBackground(getResources().getDrawable(R.color.save_green));

                    saveBtn.setOnClickListener(e->toastMessage("Saved and Stored " + getString(R.string.ok), Toast.LENGTH_SHORT));

                    return true;
                } else {

                    saveBtn.setBackgroundColor(Color.RED);
                    saveBtn.setBackground(getResources().getDrawable(R.color.save_red));
                    saveBtn.setEnabled(false);
                    return false;
                }
            } else {
                if (TextUtils.isEmpty(et.getText().toString())&&(!TextUtils.isEmpty(et2.getText().toString())||
                        !TextUtils.isEmpty(et3.getText().toString())||
                        !TextUtils.isEmpty(et4.getText().toString())||
                        !TextUtils.isEmpty(et5.getText().toString())||
                        !TextUtils.isEmpty(et6.getText().toString()))) {

                    saveBtn.setBackground(getResources().getDrawable(R.color.save_green));
                    et.setHint(x);
                    saveBtn.setOnClickListener(e->{toastMessage("Saved and Stored " + getString(R.string.ok), Toast.LENGTH_SHORT);});

                    return true;
                }
                else {return true;}
            }


        }
    }

    public String getOccurance(Spinner sp)
    {
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                occurance=adapterView.getSelectedItem().toString();
                //System.out.println()(">>"+occurance);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return sp.getSelectedItem().toString();
    }
    public Date dateFormat(EditText dte)
    {
        String myDate =dte.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        Date d=null;
        try {
            d = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println()("Date is: "+d);


        return  d;
    }
    public Calendar calendarFormat(EditText dte)
    {
        String myDate =dte.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        Calendar d=Calendar.getInstance();
        try {
            d.setTime(sdf.parse(myDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println()("Date is: "+d);


        return  d;
    }
    public void validateTimeSelected(long sT,long eT,EditText d,EditText s, EditText e,Button save, Button set,TextView tv)
    {
      if(eT<sT)
      {
                    //save.setEnabled(false);
          //set.setEnabled(false);
          //TODO:Change day +1;
          //long m=Long.parseLong(d.getText().toString());
          SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          Calendar c = Calendar.getInstance();
          c.setTime(dateFormat(d)); // Now use today date.
          //c.add(Calendar.DATE, 1); // Adding 1 day
          String newDate = sdf.format(c.getTime());
          //System.out.println()(newDate);

          //Expected to return date a day after the entered

           d.setText(newDate);
          //System.out.println()("Date After Addition: "+newDate);
         // //toastMessage("Date is now, "+d.getText().toString(),Toast.LENGTH_SHORT);
            if(tv!=null)
          e.setText(MINs*(-1)+"");


      }
      else
      {
          save.setEnabled(true);
          set.setEnabled(true);
      }
    }
    public void retrieveshopppingListCreated()
    {
        /**Get List From File*/

    }
    public void showconfirmationBox2()
    {
        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.shopingDialog).setTitle(R.string.social_shopping_hint);

        builder.setPositiveButton(R.string.create_budget_shopper, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
              startActivity(new Intent(context,BudgetShopper.class));
              ref=FirebaseDatabase.getInstance().getReference("Users/"+UID+"/").child("SOCIAL").child("Shopping");
              ref.setValue(new Shopping());



            }
        });
        builder.setNegativeButton(R.string.no_thanks, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                show_templates(R.layout.social_sleepover_template,6);
                adialog.dismiss();

            }
        });
        android.support.v7.app.AlertDialog adialog=builder.create();
        adialog.show();

    }
    public void handleSaveButtonForAll(AutoCompleteTextView et,
                                        EditText et2,
                                        EditText et3,
                                        EditText et4,
                                        EditText et5,
                                        EditText et6,
                                        TextView tv, ImageButton ib,ImageButton ib2,LinearLayout LL1,LinearLayout LL2,LinearLayout LL3,TimePicker tp,TimePicker tp2,DatePicker dp,Social key,TextView tag,String x,Spinner sp)
    {
         handleTemplatestuff(et,et2,et3,et4,et5,et6,tv,ib,ib2,LL1,LL2,LL3,tp,tp2,dp,key,tag,x,sp);


    }
   // private String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
    int age;
    public void checkAge()
    {
        /**If user age is > 18 the adultTemplate is used otherwise the adolecent one is used*/
        //System.out.println()("User is "+age);
       /* DatabaseReference ref=FirebaseDatabase.getInstance().getReference("UserInformation/"+UID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation ui=dataSnapshot.getValue(UserInformation.class);
                if(ui.getAge()!=0)
                age=ui.getAge();
                else age=10;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
       age=16;
        if(age<5)
        {
            adolecentTemplates.setVisibility(View.VISIBLE);
            adultTemplates.setVisibility(View.GONE);
        }
        else if(age<=5)
        {
            adultTemplates.setVisibility(View.VISIBLE);
            adolecentTemplates.setVisibility(View.GONE);
        }
    }
    public  void durationToActualTime(EditText dur,EditText startTime, TextView time)
    {
        int sec,min,hr;
        long ms;
        String duration,startTm,EndTime;
        duration=dur.getText().toString();
        if(duration.equals("")||duration==null)duration="0";
        startTm=startTime.getText().toString();

        ms=Long.parseLong(duration)*60000;
        //System.out.println()(duration+"(min) X 60s X 1000ms = "+ms);

        long finalTime=daTime+ms;
        //System.out.println()(daTime+"<>"+finalTime);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a",Locale.getDefault());
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss",Locale.getDefault());

        String dateString = sdf.format(finalTime);
        String dateString2 = sdf1.format(finalTime);

        //System.out.println()(dateString);
        EndTime = String.format(Locale.getDefault(),"%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(finalTime),
                TimeUnit.MILLISECONDS.toMinutes(finalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(finalTime)),
                TimeUnit.MILLISECONDS.toSeconds(finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime)));
        String EndTime2 = String.format(Locale.getDefault(),"%02d:%02d", TimeUnit.MILLISECONDS.toHours(finalTime),
                TimeUnit.MILLISECONDS.toMinutes(finalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(finalTime)),
                TimeUnit.MILLISECONDS.toSeconds(finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime)));

        time.setText(EndTime2);
        timeVersion=1;

        time.setOnLongClickListener(e->{if(timeVersion==1){
            time.setText(EndTime);
            timeVersion=2;
        }else if(timeVersion==2){
            time.setText(EndTime2);
            timeVersion=1;
        }return  true;
        });


    }
    public String durationToTime(EditText dur,EditText startTime)
    {

        long ms=0,ms2=0;
        try {
            ms=new SimpleDateFormat("HH:mm").parse(startTime.getText().toString()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dur.getText().toString().equals("")||dur.getText().toString()==null)dur.setText("0");
        ms2=Long.parseLong(dur.getText().toString())*60000;
        //System.out.println()(dur.getText().toString()+"(min) X 60s X 1000ms = "+ms2);

        long finalTime=ms2+ms;
        return new SimpleDateFormat("HH:mm").format(finalTime);

    }/**Done*/
    String I2M;
    private void handleTemplatestuff(AutoCompleteTextView et,
                                     EditText et2,
                                     EditText et3,
                                     EditText et4,
                                     EditText et5,
                                     EditText et6,
                                     TextView tv,
                                     ImageButton ib,
                                     ImageButton ib2,
                                     LinearLayout LL1,
                                     LinearLayout LL2,
                                     LinearLayout LL3,
                                     TimePicker tp,
                                     TimePicker tp2,DatePicker dp,Social key,TextView tag,String x,Spinner sp)

    {
        tags=tag.getText().toString().split(",");
        tags[0]=et.getText().toString();
        tags[1]=et2.getText().toString();
        tags[2]=et3.getText().toString();

        tags[3]=et4.getText().toString();
        if(x.equals("Cinema")||x.equals("Gaming")||x.equals("Clubing"))
            tags[4]=tv.getText().toString();
        else
        tags[4]=et5.getText().toString();

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tags[0]=et.getText().toString();
                if(x.equals("Gaming")) {
                    if (!TextUtils.isEmpty(et.getText().toString())) {
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4]+"\n"+gameOne.getText().toString()+" | " + g2.getText().toString() + " | " + g3.getText().toString() + " | " + g4.getText().toString() + " | " + g5.getText().toString());
                    }
                }  else  if (!TextUtils.isEmpty(et.getText().toString()))
                {tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]);}
                else   if(x.equals("customD")||x.equals("customDl")) {
                    if (!TextUtils.isEmpty(et.getText().toString()))
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4] + "\n" + itemViews[j].getText().toString() + " | ");

                }
                // if (!TextUtils.isEmpty(et.getText().toString()))

                //System.out.println()(tag.getText().toString());
                    if(x.equals("Birthday")||x.equals("Christmas")||x.equals("Shopping"))
                        tag.setText(tags[0]+ "\'s, "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]);


            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tags[1] = et2.getText().toString();
                if (x.equals("Gaming")) {
                    if (!TextUtils.isEmpty(et2.getText().toString())) {
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4]+"\n"+gameOne.getText().toString()+" | " + g2.getText().toString() + " | " + g3.getText().toString() + " | " + g4.getText().toString() + " | " + g5.getText().toString());
                    }
                } else if (!TextUtils.isEmpty(et.getText().toString())) {
                    tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4]);
                }
                else   if(x.equals("customD")) {
                    if (!TextUtils.isEmpty(et2.getText().toString()))
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4] + "\n" + itemViews[j].getText().toString() + " | ");

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tags[2]=et3.getText().toString();
                if (x.equals("Gaming")){
                    if (!TextUtils.isEmpty(et3.getText().toString())) {
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4]+"\n"+gameOne.getText().toString()+" | " + g2.getText().toString() + " | " + g3.getText().toString() + " | " + g4.getText().toString() + " | " + g5.getText().toString());
                    }
                } else if(!TextUtils.isEmpty(et3.getText().toString()))
                    {tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]);}
                else   if(x.equals("customD")) {
                    if (!TextUtils.isEmpty(et3.getText().toString()))
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4] + "\n" + itemViews[j].getText().toString() + " | ");

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tags[3]=et4.getText().toString();
                if (x.equals("Gaming")) {
                    if (!TextUtils.isEmpty(et4.getText().toString())) {
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4]+"\n"+gameOne.getText().toString()+" | " + g2.getText().toString() + " | " + g3.getText().toString() + " | " + g4.getText().toString() + " | " + g5.getText().toString());
                    }
                }else if(!TextUtils.isEmpty(et4.getText().toString()))
                    {tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]);}
                else   if(x.equals("customD")) {
                    if (!TextUtils.isEmpty(et4.getText().toString()))
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4] + "\n" + itemViews[j].getText().toString() + " | ");

                }

        }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if(x.equals("Cinema")||x.equals("Gaming")||x.equals("Clubing"))
            tags[4]=tv.getText().toString();
        else{
        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tags[4]=et5.getText().toString();
                if (x.equals("Gaming")){
                    if (!TextUtils.isEmpty(et5.getText().toString())) {
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4]+"\n"+gameOne.getText().toString()+" | " + g2.getText().toString() + " | " + g3.getText().toString() + " | " + g4.getText().toString() + " | " + g5.getText().toString());
                    }
                }else if(!TextUtils.isEmpty(et5.getText().toString()))
                   {tag.setText(tags[0]+ ", "+tags[1]+", "+ tags[2]+", "+ tags[3]+", "+tags[4]);}
                else   if(x.equals("customD")) {
                    if (!TextUtils.isEmpty(et5.getText().toString()))
                        tag.setText(tags[0] + ", " + tags[1] + ", " + tags[2] + ", " + tags[3] + ", " + tags[4] + "\n" + itemViews[j].getText().toString() + " | ");

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        }
        key=new Social();
        String heading,location,date,startTime,endTime,val6,val7,val8,occ;
        heading=et.getText().toString();
        location=et2.getText().toString();
        date=et3.getText().toString();
        startTime=et4.getText().toString();
       // endTime=et5.getText().toString();
        val7="";
        String[]temp;
        if(heading==null||heading.equals(""))
        {
            heading=x+"_"+TodaysDate;
            if(date==null||date.equals(""))
                date=TodaysDate;
        }


        for (int i=0;i<itemViews.length;i++)
        {
            if(itemViews[i]!=null)
            {
                I2M+=itemViews[i].getText().toString()+"■";
            }
        }
        if(gameOne!=null&&g2!=null&&g3!=null&&g4!=null&&g5!=null)
        G2P=gameOne.getText().toString()+","+g2.getText().toString()+","+g3.getText().toString()+","+g4.getText().toString()+","+g5.getText().toString();
       occ=getOccurance(sp);
       if(TextUtils.isEmpty(occ))
           occ="Once";
       //System.out.println()("OCC: "+occ);

//        System.out.printf("%s\n%s\n%s\n%s\n%s\n",et2.getText().toString(),et3.getText().toString(),et4.getText().toString(),et5.getText().toString(),et6.getText().toString());
        if(tv!=null)
        endTime=tv.getText().toString();
        else
            endTime=et5.getText().toString();

        //System.out.println()("_+_+_+_+_"+endTime);

        if(ib!=null)
            val6=ib.toString();
        else val6="";
        if(ib2!=null)
                val7 = ib2.toString();
        else if(x.equals("Gaming"))
            val7=G2P;
        else if(x.equals("customL"))
            val7=I2M;
        else val7="";
        if(x.equals("Shopping"))
        {
            if(!shoppingLists.isEmpty())
            key.setObj(shoppingLists);
        }
        
        val8=et6.getText().toString();
        key.setHeading(heading);
        key.setType(x);
        key.setLocation(location);
        key.setDate(date);
        key.setStartTime(startTime);
        key.setEndTime(endTime);
        key.setVal6(val6);
        key.setVal7(val7);
        key.setVal8(val8);
        key.setOccurance(occ);
        saveToDatabase(key);

        Log.d(TAG,key.toString());


    }/**Done*/
    private void setUpAGeneralEventHandlerForTheTimeSelection(Button dateBtn,
                                                              Button sTimeBtn,
                                                              Button eTimeBtn,
                                                              Button doneDate,
                                                              Button DoneTime,
                                                              Button DoneTime2,
                                                              TimePicker tp,
                                                              LinearLayout main,
                                                              LinearLayout Date,
                                                              LinearLayout Time,
                                                              LinearLayout Time2, TimePicker tp2, DatePicker dp, EditText dateField, EditText sTimeField, EditText eTimeField,TextView eTimeView,Button saveBtn)
    {

        /**from main to....*/
        dateBtn.setOnClickListener(e->{
            main.setVisibility(View.GONE);
            Date.setVisibility(View.VISIBLE);
            Time.setVisibility(View.GONE);
            Time2.setVisibility(View.GONE);

        });
        sTimeBtn.setOnClickListener(e->{
            main.setVisibility(View.GONE);
            Date.setVisibility(View.GONE);
            Time.setVisibility(View.VISIBLE);
            Time2.setVisibility(View.GONE);

        });
        eTimeBtn.setOnClickListener(e->{
            main.setVisibility(View.GONE);
            Date.setVisibility(View.GONE);
            Time.setVisibility(View.GONE);
            Time2.setVisibility(View.VISIBLE);

        });
        /**From ... to main*/
        doneDate.setOnClickListener(e->{
            main.setVisibility(View.VISIBLE);
            Date.setVisibility(View.GONE);
            Time.setVisibility(View.GONE);
            Time2.setVisibility(View.GONE);

        });
        DoneTime.setOnClickListener(e->{
            main.setVisibility(View.VISIBLE);
            Date.setVisibility(View.GONE);
            Time.setVisibility(View.GONE);
            Time2.setVisibility(View.GONE);

        });
        DoneTime2.setOnClickListener(e->{
            main.setVisibility(View.VISIBLE);
            Date.setVisibility(View.GONE);
            Time.setVisibility(View.GONE);
            Time2.setVisibility(View.GONE);

        });

        /**Write The changed date or time to textField*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tp.setOnTimeChangedListener((timePicker, i, i1) ->

            {DoneTime.setText(getString(R.string.setstarttime,tp.getHour()+":"+tp.getMinute()));
            daTime=(tp.getCurrentMinute() * 60 + tp.getCurrentHour() * 60 * 60) * 1000;
            //System.out.println()("getting time from green: "+daTime);

            sTimeField.setText(tp.getHour()+":"+tp.getMinute());});
            sTimeField.setError(null);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tp2.setOnTimeChangedListener((timePicker, i, i1) ->

            {DoneTime2.setText(getString(R.string.setTime,tp2.getHour()+":"+tp2.getMinute()));if(eTimeView!=null)eTimeField.setText((((((tp2.getCurrentMinute()*60 + tp2.getCurrentHour() * 60*60)*1000 )-daTime)/1000)/60)+"");
            else{
                eTimeField.setText(tp2.getHour()+":"+tp2.getMinute());}
                daTime2=(tp2.getCurrentMinute() * 60 + tp2.getCurrentHour() * 60 * 60) * 1000;
                MINs=(((((tp2.getCurrentMinute()*60 + tp2.getCurrentHour() * 60*60)*1000 )-daTime)/1000)/60);
                validateTimeSelected(daTime,daTime2,dateField,sTimeField,eTimeField,saveBtn,DoneTime2,eTimeView);
                eTimeField.setError(null);
                if(eTimeView!=null){eTimeView.setText(tp2.getHour()+":"+tp2.getMinute());


            }});
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dp.setOnDateChangedListener((d, i, i1, i2) ->
            {doneDate.setText(getString(R.string.setDate,dp.getDayOfMonth()+"/"+(dp.getMonth()+1)+"/"+dp.getYear()));dateField.setText(dp.getDayOfMonth()+"/"+(dp.getMonth()+1)+"/"+dp.getYear());dayPick=dp.getDayOfMonth();monthPick=dp.getMonth();yearPick=dp.getYear();
                Calendar calendar = new GregorianCalendar(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
               isValidDay(doneDate, calendar.getTimeInMillis());});
            dateField.setError(null);
        }
        eTimeField.setEnabled(false);
        eTimeBtn.setEnabled(false);
        sTimeField.setEnabled(false);
        sTimeBtn.setEnabled(false);

        if(eTimeView==null) {
            eTimeField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(TextUtils.isEmpty(eTimeField.getText().toString())){

                        focusView=eTimeField;
                        eTimeField.setError(getString(R.string.emptyField));
                        saveBtn.setEnabled(false);
                    }
                          else if(!validateTimeInput(eTimeField))
                           {

                               saveBtn.setEnabled(false);
                               focusView=eTimeField;
                               eTimeField.setError("Incorrect Format!\nTry 00:00:00 or 00:00 as a format.");


                           }
                           else {
                        validateTimeSelected(daTime,daTime2,dateField,sTimeField,eTimeField,saveBtn,DoneTime2,eTimeView);
                        saveBtn.setEnabled(true);

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        dateField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(dateField.getText().toString())){
                    eTimeField.setEnabled(false);
                    eTimeBtn.setEnabled(false);
                    sTimeField.setEnabled(false);
                    sTimeBtn.setEnabled(false);
                    focusView=dateField;
                    dateField.setError(getString(R.string.emptyField));
                    saveBtn.setEnabled(false);
                }
                else if(!validateDateInput(dateField))
                {
                    eTimeField.setEnabled(false);
                    eTimeBtn.setEnabled(false);
                    sTimeField.setEnabled(false);
                    sTimeBtn.setEnabled(false);
                    saveBtn.setEnabled(false);
                }
                else if(!(isValidDay(dateField)))
                {
                    eTimeField.setEnabled(false);
                    eTimeBtn.setEnabled(false);
                    sTimeField.setEnabled(false);
                    sTimeBtn.setEnabled(false);
                    saveBtn.setEnabled(false);
                }
                else {
                    eTimeField.setEnabled(true);
                    eTimeBtn.setEnabled(true);
                    sTimeField.setEnabled(true);
                    sTimeBtn.setEnabled(true);
                    focusView=dateField;
                    saveBtn.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
           sTimeField.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   if(TextUtils.isEmpty(sTimeField.getText().toString())){
                                             eTimeField.setEnabled(false);
                       eTimeBtn.setEnabled(false);
                       focusView=sTimeField;
                       sTimeField.setError(getString(R.string.emptyField));
                       saveBtn.setEnabled(false);
                   }
                   else if(!(validateTimeInput(sTimeField))){
                       eTimeField.setEnabled(false);
                       eTimeBtn.setEnabled(false);
                       focusView=sTimeField;
                       saveBtn.setEnabled(false);
                       sTimeField.setError("Incorrect Format!\nTry 00:00:00 or 00:00 as a format.");

                       //sTimeField.setError(getString(R.string.emptyField));
                   }

                   else {
                       eTimeField.setEnabled(true);
                       eTimeBtn.setEnabled(true);
                       focusView=eTimeField;
                       saveBtn.setEnabled(true);

                   }
                   if(eTimeView!=null)
                   durationToActualTime(eTimeField, sTimeField, eTimeView);
                   //TODO: validate user time entry(change to sec then return time)

               }

               @Override
               public void afterTextChanged(Editable editable) {

               }
           });
                if(eTimeView!=null) {
                    eTimeField.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(eTimeField.getText().toString().contains(":"))
                            eTimeField.setText(eTimeField.getText().toString().replaceAll(":",""));
                            durationToActualTime(eTimeField, sTimeField, eTimeView);

                           // ////toastMessage("getting time...", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            // durationToActualTime(eTimeField,sTimeField,eTimeView);

                        }
                    });
                }

            //eTimeField.setError("No Start Time Selected");



    }

    private boolean isValidDay(EditText doneDate) {
        String dd=doneDate.getText().toString();
        //System.out.println()(dd+" < " +todaysDate);
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_WEEK);
        Calendar dte=calendarFormat(doneDate);
        //System.out.println()(dte+" < " );
        long timeMilli = calendar.getTimeInMillis();
        long chossenDate= dte.getTimeInMillis();
        int timeMilli2 = calendar.get(Calendar.DAY_OF_WEEK)+calendar.get(Calendar.MONTH)+calendar.get(Calendar.YEAR);
        int chossenDate2=dte.get(Calendar.DAY_OF_WEEK)+dte.get(Calendar.MONTH)+dte.get(Calendar.YEAR);

        //System.out.println()((chossenDate)+" vs "+timeMilli);
        //System.out.println()((chossenDate2)+" vs "+timeMilli2);

            if(timeMilli<=(chossenDate))return true;
            else {
                if(timeMilli2==(chossenDate2))
                    return  true;
                //toastMessage("Date selected is past sorry?", Toast.LENGTH_LONG);

                doneDate.setError("Date selected is past sorry?");
                return  false;
               // //toastMessage("Date selected is past sorry?", Toast.LENGTH_SHORT);
            }

    }

    private void isValidDay(Button doneDate, long drawingTime) {
        //System.out.println()(drawingTime+" < " +todaysDate);
        Calendar calendar = Calendar.getInstance();
         calendar.get(Calendar.DAY_OF_WEEK);

        int timeMilli = calendar.get(Calendar.DAY_OF_WEEK)+calendar.get(Calendar.MONTH)+calendar.get(Calendar.YEAR);
        int chossenDate=dayPick+monthPick+yearPick;
        //System.out.println()((chossenDate-10)+" vs "+timeMilli);


            if (drawingTime < todaysDate) {
                if(timeMilli==(chossenDate-10))
                    doneDate.setEnabled(true);
                else {
                    doneDate.setEnabled(false);
                    //toastMessage("Date selected is past sorry?", Toast.LENGTH_SHORT);
                }
                } else {
                doneDate.setEnabled(true);
            }

    }

    /**Done*/

    private boolean validateTimeInput(EditText eTimeField) {

        String pattern="(?:[01][0-9]|2[0-3]):([0-5][0-9]):[0-5][0-9]";
        String pattern3="([0-9]:([0-9]|([0-5][0-9])):([0-9]|([0-5][0-9])))";
        String pattern4="(([0-9]:[0]:[0])|([0-9]:[0-5][0-9])|([0-9]:[0]))";
        String pattern2="(?:[01][0-9]|2[0-3]):([0-5][0-9])";
        String pattern5="([0-1][0-9]:[0-9]|[2][0-9]:[0-9])";
        //System.out.println()(eTimeField.getText().toString());
        return eTimeField.getText().toString().matches(pattern) || (eTimeField.getText().toString().matches(pattern2)) ||
                eTimeField.getText().toString().matches(pattern3) || eTimeField.getText().toString().matches(pattern4) || eTimeField.getText().toString().matches(pattern5);



    }

    private boolean validateDateInput(EditText dateField) {

        String pattern="(([1-2][0-9])|()|([1-9])|([0][1-9])|([3][0-1]))/(([1][0-2])|([1-9])|([0][1-9]))/([1-9][0-9][0-9][0-9])";
       // String pattern="(([1-2][0-9])|([1-9])|([0][1-9])|([3][0-1]))/(([1][0-2])|([1-9])|([0][1-9]))/([1-9][0-9][0-9][0-9])";
       /* dateField.setText(dateField.getText().toString().replaceAll("-","/"));
        dateField.setText(dateField.getText().toString().replaceAll("\\.","/"));
        *///System.out.println()(dateField);
        if(dateField.getText().toString().matches(pattern))
        {return true;}
        else
        {

            dateField.setError("Incorrect Format!\nUse Only dd/mm/yyyy format.");
            return false;
        }



    }

    private void toastMessage(String msg,int duration)
    {
        Toast.makeText(this,msg,duration).show();
    }
    public boolean validateFields(AutoCompleteTextView et,EditText et2,EditText et3,EditText et4,EditText et5,EditText et6)
    {
        if(!TextUtils.isEmpty(et.getText().toString()))
        {
            if(!TextUtils.isEmpty(et2.getText().toString()))
            {
                if(!TextUtils.isEmpty(et3.getText().toString()))
                {
                    if(!TextUtils.isEmpty(et4.getText().toString()))
                    {
                        if(!TextUtils.isEmpty(et5.getText().toString()))
                        {
                            return !TextUtils.isEmpty(et6.getText().toString());
                        }  else return false;
                    }  else return false;
                }  else return false;
            }  else return false;
        }  else return false;
    }
    @Override
    public void onStart() {

        super.onStart();

    }

    public void timesRB(View view) {
        int radioID=timeSetting.getCheckedRadioButtonId();//2131362481
        RadioButton singleButton= findViewById(radioID);

        switch (radioID) {
            case R.id.r1: {
                duration=true;

            }
            break;
            case R.id.r2: {
                duration=false;

            }break;

        }
    }
}
