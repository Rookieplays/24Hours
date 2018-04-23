package ie.ul.o.daysaver;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Ollie on 02/04/2018.
 */


class Savings
{
    /*
        user enters a balance
    */
    private String user;
    private long date;
    //private LocalTime time;
    private int indexOfUser;
    private double balance;
    private String filePath;
    Context context;
    private ArrayList<ArrayList<String>> SavingsList=new ArrayList<ArrayList<String>>();
    public Savings(Context context)throws IOException
    {
        this.user="unknown";
        this.context=context;
      
        this.date=new Date().getTime();
        filePath = context.getFilesDir().getPath() + "/";
        this.balance=0.00;
        loadSavings();
    }
    public Savings(String user,Context context)throws IOException
    {
    
        //System.out.println()("__>"+user);
        this.user=user;
        filePath = context.getFilesDir().getPath() + "/";
        this.date=new Date().getTime();
        this.balance=0.00;
        loadSavings();
        indexOfUser=SavingsList.get(0).indexOf(user);
    }
    public Savings(String user,double balance,Context context)throws IOException
    {
        //System.out.println()("__>"+user);
        this.user=user;
        filePath = context.getFilesDir().getPath() + "/";
        
        this.date=new Date().getTime();
        this.balance=balance;
        loadSavings();
        indexOfUser=SavingsList.get(0).indexOf(user);
    }
    public void setBalnce(String balance,String s)
    {
        this.balance=Double.parseDouble(balance);

    }
    public void setBalance(double balance)
    {
        this.balance=balance;
    }
    public void loadSavings()throws IOException
    {
        String[]temp;
        Scanner fileReader;
        for(int i=0;i<3;i++)
            SavingsList.add(new ArrayList<String>());
        File file=new File(filePath+"Savings.txt");
        if(file.exists())
        {
            fileReader=new Scanner(file);
            while(fileReader.hasNext())
            {
                temp=fileReader.nextLine().split(":");
                for (int i=0; i<SavingsList.size();i++ )
                {
                    SavingsList.get(i).add(temp[i]);
                }
            }fileReader.close();
        }
        else
            writeToSavingsFile(filePath+"Savings.txt","User:0.00:last updated");
    }
    public void writeToSavingsFile(String filename,String info)throws IOException
    {
        FileWriter writer=new FileWriter(filename,true);
        writer.write(info);
        writer.close();

    }
    public void addNewUserToSavings()throws IOException
    {
        if(!(SavingsList.get(0).contains(this.user)))
        {
            writeToSavingsFile(filePath+"Savings.txt",this.user+":"+this.balance+":"+"Last Updated on "+date);
        }
    }
    public void changeBalance()throws IOException
    {
        int i=0;
        PrintWriter write=new PrintWriter(filePath+"Savings.txt");
        if(SavingsList.get(0).contains(this.user))
        {
            SavingsList.get(1).set(indexOfUser,this.balance+"");
            while(i < SavingsList.get(0).size())
            {
                write.println(SavingsList.get(0).get(i)+":"+SavingsList.get(1).get(i)+":"+"Last Updated on "+date); //overriding the files previous information with updated information
                i++;
            }write.close();

        }
    }public String getsBalance()throws IOException
{

    double bal=0;
    NumberFormat nf=new DecimalFormat("##.##");

    String finalbal="";
    bal=getBalance();
    if(bal<1000&&bal>=0||(bal<-1000&&bal>-1000))
    {
        finalbal=nf.format(bal);
    }
    else if(bal>=1000&&bal<1000000||(bal<=-1000&&bal>-1000000))
    {
        finalbal=nf.format(bal/1000)+"K";
        //System.out.println()(finalbal);
    }
    else if(bal>=1000000&&bal<1000000000||(bal<=-1000000&&bal>-1000000000))
    {
        finalbal=nf.format(bal/1000000)+"M";
        //System.out.println()(finalbal);
    }
    else
        finalbal=nf.format(bal/1000000000)+"B";//System.out.println()(finalbal);
    return finalbal;
}
    public String formatBal(double bal)throws IOException
{


    NumberFormat nf=new DecimalFormat("##.##");

    String finalbal="";
    bal=getBalance();
    if(bal<1000&&bal>=0||(bal<-1000&&bal>-1000))
    {
        finalbal=nf.format(bal);
    }
    else if(bal>=1000&&bal<1000000||(bal<=-1000&&bal>-1000000))
    {
        finalbal=nf.format(bal/1000)+"K";
        //System.out.println()(finalbal);
    }
    else if(bal>=1000000&&bal<1000000000||(bal<=-1000000&&bal>-1000000000))
    {
        finalbal=nf.format(bal/1000000)+"M";
        //System.out.println()(finalbal);
    }
    else
        finalbal=nf.format(bal/1000000000)+"B";//System.out.println()(finalbal);
    return finalbal;
}
    public double getBalance()throws IOException
    {
        SavingsList.clear();
        loadSavings();
        if(SavingsList.get(0).contains(this.user))
        {
            balance=Double.parseDouble(SavingsList.get(1).get(indexOfUser));
            return Double.parseDouble(SavingsList.get(1).get(indexOfUser));
        }
        else return balance;
    }

    public void SavingsDialog(String curency, TextView oldBal1,double wallet, Context context, LayoutInflater inflater)throws IOException {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.savings_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView cW = dialogView.findViewById(R.id.currentSave);
        TextView cWamount = dialogView.findViewById(R.id.sWallet);
        Button confirmButton = dialogView.findViewById(R.id.b3);
        Button cancel = dialogView.findViewById(R.id.b4);
        TextInputEditText newBalance = dialogView.findViewById(R.id.nS);
        ViewPager vp = dialogView.findViewById(R.id.newSavings);


        TextView header=dialogView.findViewById(R.id.textView28);
        final String p1="(([0-9]+)|(([0-9]+)*[.])|([-]*([0-9]+))|(([-]*([0-9]+))*[.]))+";
        header.setText(context.getString(R.string.savingDialog,user));
        cWamount.setText(curency+oldBal1.getText().toString());
        newBalance.setHint(context.getString(R.string.new_Balance,curency));
        TextInputLayout til=dialogView.findViewById(R.id.tils);
        til.setHint(curency);

        confirmButton.setEnabled(false);
        newBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                double limit=0.0;if(!TextUtils.isEmpty(newBalance.getText().toString()))
                   limit =Double.parseDouble(newBalance.getText().toString().trim());
                confirmButton.setEnabled((newBalance.getText().toString().trim().matches(p1)));
                confirmButton.setEnabled(limit<=wallet);



                if(!(newBalance.getText().toString().trim().matches(p1)))
                {
                    vp.setBackgroundColor(Color.RED);
                    newBalance.getText().clear();
                    newBalance.setError("Inavid entry");

                }
                else
                { newBalance.setError(null);vp.setBackgroundColor(Color.GREEN);
                    if(limit>wallet)
                    {
                        vp.setBackgroundColor(Color.RED);
                        newBalance.setError(context.getString(R.string.limit_err));
                    }
                    else
                    {
                        newBalance.setError(null);vp.setBackgroundColor(Color.GREEN);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        cancel.setOnClickListener(e->{alertDialog.dismiss();});


        confirmButton.setOnClickListener(e->{
            alertDialog.dismiss();
            this.balance=Double.parseDouble(newBalance.getText().toString().trim());
            try {
                changeBalance();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            try {
                oldBal1.setText(formatBal(this.balance));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //oldBal2.setText(getsBalance());

        });





    }


}

