package ie.ul.o.daysaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class createNote extends MainActivity {
    private NotesAdapter mAdapter;
    private RecyclerView rView;
    private TextInputEditText title;
    private EditText text;
    private Spinner sizes,color;
    private Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar24);
        setSupportActionBar(toolbar);
        rView=findViewById(R.id.listOfNotes);
        rView.setLayoutManager(new LinearLayoutManager(this));
        cl=findViewById(R.id.cl);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabb);
        getAllInDatabase();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              createNotes();
            }
        });

    }
    private void createNotes()
    {
        System.out.println("click");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(createNote.this).inflate(R.layout.creatednotes, null);
        System.out.println(v);
        dialogBuilder.setView(v);
        sizes=(Spinner)v.findViewById(R.id.fontsize);
        color=(Spinner)v.findViewById(R.id.fontcolor);
         title=v.findViewById(R.id.note_Title);
         text=v.findViewById(R.id.note_text);
        finish=v.findViewById(R.id.saveNote);



        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        sizes.setSelection(2);
        text.setTextColor(TextEditorProperty.BLACK);

       sizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                    text.setTextSize(TypedValue.COMPLEX_UNIT_SP,Float.parseFloat(adapterView.getSelectedItem()+""));
                }catch(NullPointerException n){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try{text.setTextColor(TextEditorProperty.CHOSSENCOLOR(adapterView.getSelectedItem()+""));
            }catch(NullPointerException n){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        AlertDialog adialog=dialogBuilder.create();
        adialog.show();
       finish.setOnClickListener(e->{
            SubNotes n=new SubNotes();
            if(!TextUtils.isEmpty(text.getText().toString()))
            {
                n.setNotes(text.getText().toString());
            }
            else n.setNotes("");
            n.setTitle(title.getText().toString());
            notes.add(n);
            storeInDatabase();
           finish();startActivity(new Intent(this, createNote.class));
            adialog.dismiss();

        });




    }
    private  String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users/"+UID).child("Notes");

    private ArrayList<SubNotes> notes=new ArrayList<>();
    Notes ns;
    private void storeInDatabase() {
        ns=new Notes(notes);
        if(ref!=null) {
            ref.setValue(ns.getNotes());
        }
    }
    private ConstraintLayout cl;
    private  final Context context=this;
    private FirebaseFirestore ff=FirebaseFirestore.getInstance();
    private void getAllInDatabase() {
        notes.clear();
        ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Retrieving Notes...");
        ff.collection(UID).document("Notes");

        if(ref!=null)
        {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        System.out.println(ds.getValue());
                        SubNotes n=ds.getValue(SubNotes.class);
                       notes.add(n);
                    }

                    System.out.println("notes: "+notes);
                    if(notes!=null)
                    {
                        System.out.println("------>"+notes);
                        mAdapter=new NotesAdapter(notes,context,cl);
                        rView.setAdapter(mAdapter);
                    }
                    else
                    {
                        rView.setAdapter(null);
                        notes.add(new SubNotes("Nothing Found",""));
                        mAdapter=new NotesAdapter(notes,context,cl);

                        rView.setAdapter(mAdapter);
                    }
                    pd.dismiss();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else
        {
            notes.add(new SubNotes("Nothing Found",""));
            mAdapter=new NotesAdapter(notes,context,cl);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searh_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            ThemeSwither.changeToTheme(this,ThemeSwither.MIDNIGHT);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            search(searchView);
            //   headerView.setBackground(getResources().getDrawable(R.drawable.midnight_header));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private  void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (mAdapter != null) mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}
