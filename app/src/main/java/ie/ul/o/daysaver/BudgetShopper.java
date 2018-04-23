package ie.ul.o.daysaver;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class BudgetShopper extends socialActivity implements  shopRecyclerItemTouchHelper.shopRecyclerItemTouchHelperListener {


    private Button addshoppingLists;
    private TextInputEditText listName;
    private RecyclerView recyclerView;
    private EditText priceView;
    private EditText itemView;
    private Button priceplusBtn,itemplusBtn,minusPrice,minusItem,saveShoppingLists,addItem;
    private String status="public";
    private CreateList listMan;
    private CreateList newList;
    private AddItems itemMan;
    private String user,listname;
    private FileManager man;
    private ArrayList<Item> itemsOnList,itemList;
    private TextView cur,added;
    private AutoCompleteTextView item;
    private LinearLayout holder;
    private Wallet wal;
    private Savings save;
    private static    final String p1="(([0-9]+)|(([0-9]+)*[.])|([-]*([0-9]+))|(([-]*([0-9]+))*[.]))+";

    private  TextView currentWalletLbl,currentSavings,c1,c2,c3,comment,totalPrice,sizeofB;
    private Switch switcher;
    private String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users/"+UID);
    private shoppingListAdapter listadapter;
    private boolean viewer=true;
     Currency us=Currency.getInstance(Locale.ITALY);

     String currency;
    private double fb;
    private FrameLayout frameLayout;
    private String author;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_shopper);
        recyclerView=findViewById(R.id.listView);
        itemsOnList=new ArrayList<>();

        itemList=new ArrayList<>();
        listName=findViewById(R.id.shoppinglistname);
        Toolbar toolbar=findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        switcher=findViewById(R.id.switch2);
        priceView=findViewById(R.id.priceText);
        priceplusBtn=findViewById(R.id.plusBtn);
        minusPrice=findViewById(R.id.minusBtn);
        minusItem=findViewById(R.id.minusItem);
        itemplusBtn=findViewById(R.id.plusItem);
        itemView=findViewById(R.id.amountText);
        item=findViewById(R.id.autoCompleteTextView);
        addItem=findViewById(R.id.done);
        currentWalletLbl=findViewById(R.id.wLabel);
        currentSavings=findViewById(R.id.sLabel);
        c1=findViewById(R.id.currency);
        added=findViewById(R.id.added);
        c2=findViewById(R.id.currency2);
        saveShoppingLists=findViewById(R.id.addShoppingList);
        comment=findViewById(R.id.comment);
        totalPrice=findViewById(R.id.total);
        sizeofB=findViewById(R.id.cart);
        c3=findViewById(R.id.c3);
        frameLayout=findViewById(R.id.fl);
        handleStuff();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new shopRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation info=dataSnapshot.getValue(UserInformation.class);
                System.out.println(dataSnapshot+"<<<<");
                user=info.getUsername();
                author=user;
                try {
                    wal=new Wallet(info.getUsername(),getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    save=new Savings(info.getUsername(),getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

               if (compoundButton.isChecked()) {
                    switcher.setText(getString(R.string.Public));
                            status=getString(R.string.Public);
                            System.out.println("Public list");
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       switcher.setThumbTintList(ColorStateList.valueOf(Color.GREEN));
                   }
               }else{
                   switcher.setText(getString(R.string.Private));
                   status=getString(R.string.Private);
                   System.out.println("Private list");
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       switcher.setThumbTintList(ColorStateList.valueOf(Color.LTGRAY));
                   }
               }


                    }
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currency=us.getSymbol(Locale.getDefault(Locale.Category.DISPLAY));
            System.out.printf("Locale: %s\nLocale Currency: %s\nCurrency: %s \n",Locale.getDefault(),us,currency);
                c1.setText(currency);c2.setText(currency);c3.setText(currency);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //System.out.println("Create workoutsSays.../."+AddNewWorkout.getOldWorkouts());




    }
    Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.budget, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case  R.id.save:
            {
                try {
                    save.SavingsDialog(currency,currentSavings,wal.unWrapBal(currentWalletLbl.getText().toString()),this,getLayoutInflater());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//saveWorkout();}
            break;
            case R.id.wallet: {
                try {
                    wal.walletDialog(currency,currentWalletLbl,this,getLayoutInflater());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }break;


        }
        return true;
    }
    int amnt=0;
    private ShoppingList shoppingList;
    public void handleStuff()
    {
        priceplusBtn.setOnClickListener(e->
        {
            if(priceView.getText().toString().matches(p1))
            {

                double interval=.10;
               // priceView.setStyle("-fx-border-color:green;");
                double price=Double.parseDouble(priceView.getText().toString());
                if(price<0.00)
                    minusPrice.setEnabled(false);
                else
                    minusPrice.setEnabled(true);
                price+=interval;
                NumberFormat nf=new DecimalFormat("##.##");
                String y=nf.format(price);
                priceView.setText(y);
                priceView.setError(null);


            }
            else
            {
                priceView.setError("Invalid entry");
                priceView.getText().clear();
                priceView.setText("0.00");

            }
        });

        //when minus is clicked
        minusPrice.setOnClickListener(e->
        {
            if(priceView.getText().toString().matches(p1))
            {
                double interval=.10;
               // priceView.setStyle("-fx-border-color:green;");
                double price=Double.parseDouble(priceView.getText().toString());

                //
                price-=interval;
                NumberFormat nf=new DecimalFormat("##.##");
                String y=nf.format(price);
                priceView.setText(y);
                priceView.setError(null);



            }
            else
            {
                //priceView.setStyle("-fx-border-color:red;");
                priceView.getText().clear();
                priceView.setText("0.00");
                priceView.setError("Invalid entry");

            }
        });

        // when plus is clicked
        minusItem.setEnabled(false);minusPrice.setEnabled(false);
        itemplusBtn.setOnClickListener(e->
        {
            if(itemView.getText().toString().matches(p1))
            {
                int interval=1;
                //itemView.setStyle("-fx-border-color:green;");
                int item=Integer.parseInt(itemView.getText().toString());
                if(item<0)
                    minusItem.setEnabled(false);
                else
                    minusItem.setEnabled(true);
                item+=interval;
                NumberFormat nf=new DecimalFormat("##.##");
                String y=nf.format(item);
                itemView.setText(y); itemView.setError(null);





            }
            else
            {

                itemView.getText().clear();
                itemView.setText("0");
                itemView.setError("Invalid entry");

            }
        });
        itemView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addItem.setEnabled(!itemView.equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //when minus is clicked
        minusItem.setOnClickListener(e->
        {
            if(itemView.getText().toString().matches(p1))
            {
                int interval=1;
                //itemView.setStyle("-fx-border-color:green;");
                int item=Integer.parseInt(itemView.getText().toString());



                //minusItem.setDisable(false);
                item-=interval;
                NumberFormat nf=new DecimalFormat("##.##");
                String y=nf.format(item);
                itemView.setText(y);
                itemView.setError(null);


            }
            else
            {
                //itemView.setStyle("-fx-border-color:red;");
                itemView.getText().clear();
                itemView.setError("Invalid Amount");
                itemView.setText("0");

            }
        });
        item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!(item.getText().toString().equals("")) || item.getText().toString() != null) {
                   // item.setStyle("-fx-border-color:green;");
                    addItem.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //when confirm is clicked
        //VBox library=itemViewer();
        addItem.setEnabled(!(item.getText().toString().toString().equals("")||item.getText().toString().toString()!=null));
        addItem.setOnClickListener(e->
        {
            try
            {
                if(!(item.getText().toString().toString().equals(""))&&item.getText().toString()!=null)
                {
                    added.setText("\u2713 "+item.getText().toString().toString()+" was added to "+listname);


                    ObjectAnimator rotate=ObjectAnimator.ofFloat(added,"alpha",1.0f,0F);
                    rotate.setDuration(1000);
                    AnimatorSet animatorSet=new AnimatorSet();
                    animatorSet.playTogether(rotate);
                    animatorSet.start();
                    MediaPlayer ring= MediaPlayer.create(this,R.raw.budget_shopper_add_item_sound);
                    ring.start();
                    ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                    });

                    // added.setTextFill(Color.GREEN);
                    Item newItem=new Item(item.getText().toString(),
                            Double.parseDouble(priceView.getText().toString()), Integer.parseInt(itemView.getText().toString()));
                    System.out.println("List of items: "+itemList+"\nNew Item: "+newItem);
                    int FLAG=0;
                    if(itemList.isEmpty())
                    {
                        itemList.add(newItem);
                    }else{
                        for(Item it: itemList)
                        {
                            System.out.println("Checking if "+newItem+" is Similar to: "+it);
                            if(it.getItemName().equals(newItem.getItemName()))
                            {
                                System.out.println("Amount Before: "+it.getAmount());
                                it.setAmount(it.getAmount()+newItem.getAmount());
                                System.out.println("Amount After: "+it.getAmount());
                                FLAG=1;

                            }


                        }
                        if(FLAG!=1)
                        {
                            itemList.add(newItem);
                            amnt=+newItem.getAmount();
                            sizeofB.setText(amnt+"");
                        }

                    }
                    listadapter = new shoppingListAdapter(this,itemList);
                    recyclerView.setAdapter(listadapter);

                    System.out.println("Lists?????"+shoppingListAdapter.ITEMLISTS);
                    int amt=0;
                    for(Item it:shoppingListAdapter.ITEMLISTS)
                    {
                        amt+=it.getAmount();
                        sizeofB.setText(amt+"");
                    }
                   /* if(listadapter!=null)
                        listadapter.notifyDataSetChanged();
                        */


                        // adding item touch helper
                        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
                        // if you want both Right -> Left and Left -> Right
                        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param


                        // making http call and fetching menu json


                        prepareList();//AddItemsTOList();
                        updateBal(wal.unWrapBal(currentSavings.getText().toString()),wal.unWrapBal(currentWalletLbl.getText().toString()),itemList,priceplusBtn,itemplusBtn);

                    }
                    else{
                        added.setText("X Oops Your Wallet is empty!!\n"+item.getText().toString()+" was not added to "+listname);
                        added.setTextColor(Color.RED);
                    }
                }

            catch(IOException ee){}
        });
        saveShoppingLists.setOnClickListener(e->{saveListToDatabase();finish();startActivity(new Intent(this,socialActivity.class));});
    }



private void displayComments(int str)
{
 comment.setText(getString(str));


    ObjectAnimator show=ObjectAnimator.ofPropertyValuesHolder(comment,
            PropertyValuesHolder.ofFloat("alpha",1.0f,0.0f));
    //PropertyValuesHolder.ofFloat("y",0.0f)););
    show.setDuration(800);

    AnimatorSet animatorSet=new AnimatorSet();
    animatorSet.playTogether(show);
    //animatorSet.setDuration(200);
    animatorSet.start();
}

    private FirebaseFirestore mFstore=FirebaseFirestore.getInstance();

private void saveListToDatabase()
{
    shoppingList=new ShoppingList(listName.getText().toString(),shoppingListAdapter.ITEMLISTS,status,UID,user);
    if(status.equalsIgnoreCase("private"))
    {
        mFstore.collection("Private_shopping").add(shoppingList);
    }
    else
    {
        mFstore.collection("Public_shopping").add(shoppingList);
    }
}
    private void prepareList() {
        if(itemList!=null) {
           // itemsOnList.clear();
          // itemsOnList.addAll(shoppingListAdapter.getLists());
            listadapter.notifyDataSetChanged();
            //  AddNewWorkout.setWORKOUTS(workoutList);

        }//refreshListOfWorkouts();
        System.out.println("0000000**"+listadapter);
       // itemList.addAll(itemsOnList);

    }

    public void AddItemsTOList()throws IOException
    {
        itemMan=new AddItems(user,newList.getName(),this);
       wal=new Wallet(user,this);
       //wal.walletDialog(currency,currentWalletLbl,);
    }

    public void SaveList()
    {

        try {
            itemMan=new AddItems(user,newList.getName(),this);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void updateBal(double limit,double budget,ArrayList<Item>items,Button addPrice,Button addItem)throws IOException
    {
       
       // System.out.println("Bal=" +this.change+",Save Bal="+limit);
        double amountSaved=budget-limit;
        System.out.println("amountsaved="+amountSaved);
        double total=0;System.out.println("total befor loop:"+total);
        ArrayList<String>temp=new ArrayList<String>();
        System.out.println(items);
        for(int i=0;i<items.size();i++)
        {
            //if(!(temp.contains(itemsOnList.get(i).getItemName())))
            total+=((items.get(i).getPrice())*(items.get(i).getAmount()));
            //temp.add(itemsOnList.get(i).getItemName());
        }

        System.out.println("total="+total);
        ObjectAnimator grow=ObjectAnimator.ofPropertyValuesHolder(totalPrice,
                PropertyValuesHolder.ofFloat("scaleY",2f),
        PropertyValuesHolder.ofFloat("scaleX",2f));

        grow.setDuration(200);
        ObjectAnimator shrink=ObjectAnimator.ofPropertyValuesHolder(totalPrice,
                PropertyValuesHolder.ofFloat("scaleY",1f),
        PropertyValuesHolder.ofFloat("scaleX",1f));
        grow.setRepeatMode(ObjectAnimator.RESTART);

        AnimatorSet animatorSet2=new AnimatorSet();
        animatorSet2.playSequentially(grow,shrink);
        animatorSet2.setDuration(200);
        animatorSet2.start();
        NumberFormat nf=new DecimalFormat("##.##");
        totalPrice.setText(nf.format(total)+"");
        
        double change=budget-total;
        System.out.println("Change="+change);
        //Wallet wallet=new Wallet(user,change);
        if(change>=limit)
        {
            currentWalletLbl.setError(null);

            ObjectAnimator up=ObjectAnimator.ofPropertyValuesHolder(currentWalletLbl,
            PropertyValuesHolder.ofFloat("y",50f));
                    //PropertyValuesHolder.ofFloat("Y",0.2f));
            
            grow.setDuration(200);
            ObjectAnimator down=ObjectAnimator.ofPropertyValuesHolder(currentWalletLbl,
                    PropertyValuesHolder.ofFloat("y",70f));
                    //PropertyValuesHolder.ofFloat("y",0.0f));
            grow.setRepeatMode(ObjectAnimator.RESTART);

            AnimatorSet animatorSet=new AnimatorSet();
            animatorSet.playSequentially(up,down);
            animatorSet.setDuration(200);
            animatorSet.start();

            currentWalletLbl.setText(wal.formatbal(change));}
        else
           currentWalletLbl.setError("Limit Reached");


        String newWal=currentWalletLbl.getText().toString();
        System.out.println("newWal="+newWal);
        System.out.println(currentWalletLbl.getText().toString());
        //currentWalletLbl.setText(change);

        if(budget==limit)
        {
            System.out.println("wal==save");
              //addPrice.setEnabled(true);addItem.setEnabled(true);
            currentWalletLbl.setTextColor(Color.parseColor("#cc0000"));
            displayComments(R.string.warning5);
            addPrice.setEnabled(false);addItem.setEnabled(false);
            currentWalletLbl.setError("Limit Reached");
            /**Animate shake*/
        }
        else if(change>=limit&&change<(limit*0.2)+limit)
        {
            System.out.println("wal>=save");
            displayComments(R.string.warning4);
            
              //addPrice.setEnabled(true);addItem.setEnabled(true);
            currentWalletLbl.setTextColor(Color.parseColor("#ff0000"));
            addPrice.setEnabled(true);addItem.setEnabled(true);
        }
        else if(change>=(limit*0.2)&&change<(limit*0.5)+limit)
        {
            displayComments(R.string.warning3);
            System.out.println("wal>=save"+30);
              addPrice.setEnabled(true);addItem.setEnabled(true);
            currentWalletLbl.setTextColor(Color.parseColor("#ff9933"));
        }
        else if(change>=(limit*0.5)&&change<(limit*0.8)+limit)
        {
            displayComments(R.string.warning2);
            System.out.println("wal>=save"+0);
              addPrice.setEnabled(true);addItem.setEnabled(true);
            currentWalletLbl.setTextColor(Color.parseColor("#ffff66"));
        }
        else if(change>=(limit*0.8)&&change<(limit*0.9)+limit)
        {
            displayComments(R.string.warning1);
            System.out.println("wal>=save"+30);
              addPrice.setEnabled(true);addItem.setEnabled(true);
            currentWalletLbl.setTextColor(Color.parseColor("#88ff4d"));
        }
        else if(change>=(limit*0.90)&&change<(limit*1))
        {
            displayComments(R.string.warning0);
            System.out.println("wal>=save"+40);
              addPrice.setEnabled(true);addItem.setEnabled(true);
            currentWalletLbl.setTextColor(Color.parseColor("#88ff4d"));
        }
        else
        {displayComments(R.string.warning);
              addPrice.setEnabled(true);addItem.setEnabled(true);
            currentWalletLbl.setTextColor(Color.parseColor("#66ff66"));
        }
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof shoppingListAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name =itemList.get(viewHolder.getAdapterPosition()).getItemName();
            System.out.println(name+"<<<><>");

            // backup of removed item for undo purpose
            final Item deletedItem = itemList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();


            // remove the item from recycler view
            listadapter.removeItem(viewHolder.getAdapterPosition());

            //itemsOnList.remove(itemsOnList.indexOf(deletedItem));

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(frameLayout, name + " removed from plan!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    listadapter.restoreItem(deletedItem, deletedIndex);


                    //  workoutList.add(deletedIndex,deletedItem);

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

            //  new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        }
    }
}
