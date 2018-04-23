package ie.ul.o.daysaver;

/**
 * Created by Ollie on 28/03/2018.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements Filterable {
    private ArrayList<SubNotes> mArrayList;
    private ArrayList<SubNotes> mFilteredList;
    private Context context;
    private View v;
    private  String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Long eT;
    private int mHour,mMinute;

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView noteTitle,dateCreated,desc;
        private ImageButton deleteBgtn,editBtn;
        // private Button useBtn;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            noteTitle = (TextView)view.findViewById(R.id.noteTitle);
            dateCreated = (TextView)view.findViewById(R.id.notesCretedOn);
            desc = (TextView)view.findViewById(R.id.noteDesc);
            deleteBgtn=(ImageButton)view.findViewById(R.id.deleNote);
            editBtn=view.findViewById(R.id.editNote);


        }

        @Override
        public void onClick(View view) {

        }
    }


    public NotesAdapter(ArrayList<SubNotes> arrayList,Context context,View v) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        this.context=context;
        this.v=v;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MM yyyy ", Locale.getDefault());
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd MM yyyy ", Locale.getDefault());
    SimpleDateFormat byMonth = new SimpleDateFormat("MMM", Locale.getDefault());
    SimpleDateFormat byDay = new SimpleDateFormat("E", Locale.getDefault());





    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listofnotes, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder viewHolder, int i) {

        viewHolder.noteTitle.setText(mFilteredList.get(i).getTitle());
        viewHolder.dateCreated.setText(mFilteredList.get(i).getDateCreated());
        System.out.println("Amount "+i);
       viewHolder.desc.setText(mFilteredList.get(i).getNotes());
       viewHolder.deleteBgtn.setOnClickListener(e->showconfirmationBox2(context,"Delete Note",context.getString(R.string.dele),i));
       viewHolder.editBtn.setOnClickListener(e->{editNote(mFilteredList.get(i));});
       viewHolder.itemView.setOnLongClickListener(e->{showInfo(context,mFilteredList.get(i).getTitle(),mFilteredList.get(i).getNotes());return true;});




    }
    private Long convertFromTimeToLong(String date) {
        long milliseconds = System.currentTimeMillis();
        System.out.println("system Time: " + milliseconds);
        SimpleDateFormat f = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
            System.out.println("Date Time: " + milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public void showInfo(Context context,String title,String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg).setTitle(title);

        builder.setNegativeButton(R.string.nope, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                adialog.dismiss();

            }
        });

        builder.setPositiveButton(R.string.remindme, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            final static int RQS_1 = 1;

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                String dayTime = hourOfDay + ":" + minute;
                                System.out.println(dayTime);
                                //dateView.setText(day + " " + dayTime);
                                //dayTimeToLong(day+" "+dayTime);
                                eT = convertFromTimeToLong(dayTime);
                                System.out.println(eT + "¢");
                                scheduleNotification(getNotification(msg,title),eT);

                            }
                            }, mHour, mMinute, false);
                timePickerDialog.show();

        }
        });
        AlertDialog adialog=builder.create();
        adialog.show();
    }
    private void scheduleNotification(Notification notification, long delay) {



        Intent notificationIntent = new Intent(context, AlarmManager.class);

        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 1);

        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        long futureInMillis = delay;

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

    }



    private Notification getNotification(String content,String Title) {

        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentTitle(Title);

        builder.setContentText(content);

        builder.setSmallIcon(R.drawable.ic_launcher);

        return builder.build();

    }
    public void showconfirmationBox2(Context context,String title,String msg,int pos)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(msg).setTitle(title);

        builder.setNegativeButton(R.string.nope, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface adialog, int i) {
                adialog.dismiss();

            }
        });

        
            builder.setPositiveButton(R.string.yeah, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface adialog, int i) {
                    //adialog.dismiss();

                    SubNotes deletedItem=mFilteredList.get(pos);
                    Snackbar snackbar = Snackbar
                            .make(v,title+"removed from plan!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // undo is selected, restore the deleted item
                            restoreItem(deletedItem, pos);
                            //  workoutList.add(deletedIndex,deletedItem);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                    deleteNote(pos);
                   

                }
            });
       
        AlertDialog adialog=builder.create();
        adialog.show();

    }
    private DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users/"+UID).child("Notes");

    private void deleteNote(int pos) {
        deleteInDatabase(mFilteredList.get(pos));
        mFilteredList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,mFilteredList.size());

    }

    private void deleteInDatabase(SubNotes SubNotes) {
        if(ref!=null)
        {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        System.out.println(ds+"1552");
                        SubNotes n=ds.getValue(SubNotes.class);
                        if(n.getTitle().equals(SubNotes.getTitle())&&n.getNotes().equals(SubNotes.getNotes())&&n.getDateCreated().equals(SubNotes.getDateCreated()))
                        {
                            ds.getRef().removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    boolean found=false;
    private SubNotes getInDatabase(SubNotes SubNotes) {
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Retrieving SubNotes...");
        if(ref!=null)
        {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        SubNotes n=ds.getValue(SubNotes.class);
                        if(n.getTitle().equals(SubNotes.getTitle())&&n.getNotes().equals(SubNotes.getNotes())&&n.getDateCreated().equals(SubNotes.getDateCreated()))
                        {
                          found=true;
                        }
                    }
                    pd.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        System.out.println("Found: "+found+"\n"+SubNotes);

        if(found)return SubNotes;
        else return null;
    }
    Spinner sizes;
    Spinner color;
    TextInputEditText title;
    EditText text;
    Button finish;

    private void editNote(SubNotes note)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater =LayoutInflater.from(context);//getLayoutInflater();
        View vi = inflater.inflate(R.layout.creatednotes, null);
        dialogBuilder.setView(vi);
         sizes=vi.findViewById(R.id.fontsize);
         color=vi.findViewById(R.id.fontcolor);
        title=vi.findViewById(R.id.note_Title);
         text=vi.findViewById(R.id.note_text);
         finish=vi.findViewById(R.id.saveNote);
        SubNotes nts=getInDatabase(note);
        if(nts!=null)
        {
            text.setText(nts.getNotes());
            title.setText(nts.getTitle());
        }


        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,Float.parseFloat(sizes.getItemAtPosition(0)+""));
        text.setTextColor(TextEditorProperty.BLACK);

        sizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP,Float.parseFloat(sizes.getItemAtPosition(0)+""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text.setTextColor(TextEditorProperty.CHOSSENCOLOR(adapterView.getSelectedItem()+""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        AlertDialog adialog=dialogBuilder.create();
        adialog.show();
        finish.setOnClickListener(e->{
            SubNotes n=new SubNotes();
            if(TextUtils.isEmpty(text.getText().toString()))
            {
                n.setNotes(text.getText().toString());
            }
            else n.setNotes("");
            deleteInDatabase(n);
            n.setTitle(title.getText().toString());
            storeInDatabase(n);
            adialog.dismiss();
        });




    }
    private ArrayList<SubNotes> notes=new ArrayList<>();
    Notes ns;
    private void storeInDatabase(SubNotes nn) {
      //  deleteInDatabase();
        notes.add(nn);
        ns=new Notes(notes);
        if(ref!=null) {
            ref.setValue(ns.getNotes());
        }
    }


    public void restoreItem(SubNotes w, int position) {
        storeInDatabase(w);
        mFilteredList.add(position, w);
        // notify item added by position
        notifyItemInserted(position);
        notifyItemRangeChanged(position,mFilteredList.size());

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<SubNotes> filteredList = new ArrayList<>();

                    for (SubNotes n : mArrayList ) {

                        if (n.getTitle().toLowerCase().contains(charString))
                        {

                            filteredList.add(n);
                        }else
                        {

                        }/** After filter by is made fore more option*/

                    }


                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<SubNotes>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}


