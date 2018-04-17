package ie.ul.o.daysaver;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
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

class Wallet
{
    /*
        user enters a balance
    */
    private String user;
    private Context context;
    private long date;
    private int time;
    private int indexOfUser;
    private double balance;
    private  String filePath;
    private ArrayList<ArrayList<String>> walletList=new ArrayList<ArrayList<String>>();
    public Wallet(Context context)throws IOException
    {
        this.context=context;
        this.user="unknown";
        this.date=new Date().getTime();
        this.balance=0.00;
        filePath = context.getFilesDir().getPath() + "/";
        loadWallets();
    }
    public Wallet(String user,Context context)throws IOException
    {
        this.context=context;

        System.out.println("__>"+user);
        this.user=user;
           this.date=new Date().getTime();
        filePath = context.getFilesDir().getPath() + "/";
        this.balance=0.00;
        loadWallets();
        indexOfUser=walletList.get(0).indexOf(user);
    }
    public Wallet(String user,double balance,Context context)throws IOException
    {
        this.context=context;
        System.out.println("__>"+user);
        this.user=user;
           this.date=new Date().getTime();
        filePath = context.getFilesDir().getPath() + "/";
        this.balance=balance;
        loadWallets();
        indexOfUser=walletList.get(0).indexOf(user);
    }
    public void setBalnce(String balance,String s)
    {
        this.balance=Double.parseDouble(balance);

    }
    public void setBalance(double balance)
    {
        System.out.println(balance);
        this.balance=balance;
    }
    public void loadWallets()throws IOException
    {
        String[]temp;
        Scanner fileReader;
        for(int i=0;i<3;i++)
            walletList.add(new ArrayList<String>());
        //String filePath = context.getFilesDir().getPath().toString() + "/";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        File f = new File(filePath);
        File file=File.createTempFile(
                "Wallet",  /* prefix */
                ".txt",         /* suffix */
                storageDir      /* directory */
        );
        if(file.exists())
        {
            fileReader=new Scanner(file);
            while(fileReader.hasNext())
            {
                temp=fileReader.nextLine().split(":");
                for (int i=0; i<walletList.size();i++ )
                {
                    walletList.get(i).add(temp[i]);
                    System.out.println(walletList);
                }
            }fileReader.close();
        }
        else
            writeToWalletFile(storageDir+"Wallet.txt","User:0.00:last updated");
    }
    public void writeToWalletFile(String filename,String info)throws IOException
    {
        FileWriter writer=new FileWriter(filePath+filename,true);
        writer.write(info);
        writer.close();

    }
    public void addNewUserToWallet()throws IOException
    {

        if(!(walletList.get(0).contains(this.user)))
        {
            writeToWalletFile(filePath+"Wallet.txt",this.user+":"+this.balance+":"+"Last Updated on "+date+" at "+time);
        }
    }
    public void changeBalance()throws IOException
    {
        int i=0;
        PrintWriter write=new PrintWriter(filePath+"Wallet.txt");
        if(walletList.get(0).contains(this.user))
        {
            walletList.get(1).set(indexOfUser,this.balance+"");
            while(i < walletList.get(0).size())
            {
                write.println(walletList.get(0).get(i)+":"+walletList.get(1).get(i)+":"+"Last Updated on "+date+" at "+time); //overriding the files previous information with updated information
                i++;
            }write.close();

        }
    }public String getsBalance()throws IOException
{

    double bal=0;
    NumberFormat nf=new DecimalFormat("##.##");

    String finalbal="";
    bal=getBalance();
    if(bal<1000&&bal>=0||(bal<0&&bal>-1000))
    {
        finalbal=nf.format(bal);
    }
    else if(bal>=1000&&bal<1000000||(bal<=-1000&&bal>-1000000))
    {
        finalbal=nf.format(bal/1000)+"K";
        System.out.println(finalbal);
    }
    else if(bal>=1000000&&bal<1000000000||(bal<=-1000000&&bal>-1000000000))
    {
        finalbal=nf.format(bal/1000000)+"M";
        System.out.println(finalbal);
    }
    else
        finalbal=nf.format(bal/1000000000)+"B";System.out.println(finalbal);
    return finalbal;
}
    public String formatbal(double bal)throws IOException
    {


        NumberFormat nf=new DecimalFormat("##.##");

        String finalbal="";

        if(bal<1000&&bal>=0||(bal<0&&bal>-1000))
        {
            finalbal=nf.format(bal);
        }
        else if(bal>=1000&&bal<1000000||(bal<=-1000&&bal>-1000000))
        {
            finalbal=nf.format(bal/1000)+"K";
            System.out.println(finalbal);
        }
        else if(bal>=1000000&&bal<1000000000||(bal<=-1000000&&bal>-1000000000))
        {
            finalbal=nf.format(bal/1000000)+"M";
            System.out.println(finalbal);
        }
        else
            finalbal=nf.format(bal/1000000000)+"B";System.out.println(finalbal);
        return finalbal;
    }
    public double unWrapBal(String bal)throws IOException
    {


        NumberFormat nf=new DecimalFormat("##.##");

        double finalbal=0;

        if(bal.contains("K"))
        {
            bal=bal.replace("K","");
            finalbal=Double.parseDouble(bal)*1000;
            System.out.println(finalbal);
        }
        else if(bal.contains("M"))
        {
            bal=bal.replace("M","");
            finalbal=Double.parseDouble(bal)*1000000;
            System.out.println(finalbal);
        }
        else if(bal.contains("B"))
        {
            bal=bal.replace("B","");
            finalbal=Double.parseDouble(bal)*1000000000;
            System.out.println(finalbal);
        }
        else
        {

            finalbal=Double.parseDouble(bal);
            System.out.println(finalbal);
        }
        return finalbal;
    }
    public double getBalance()throws IOException
    {
        walletList.clear();
        loadWallets();
        if(walletList.get(0).contains(this.user))
        {
            balance=Double.parseDouble(walletList.get(1).get(indexOfUser));
            return Double.parseDouble(walletList.get(1).get(indexOfUser));
        }
        else return balance;
    }

    public void walletDialog(String curency, TextView oldBal1, Context context,LayoutInflater inflater)throws IOException
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.wallet_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView cW=dialogView.findViewById(R.id.currentWal);
        TextView cWamount=dialogView.findViewById(R.id.cWallet);
        Button confirmButton=dialogView.findViewById(R.id.button3);
        Button cancel=dialogView.findViewById(R.id.button4);
        TextInputEditText newBalance=dialogView.findViewById(R.id.nW);
        ViewPager vp=dialogView.findViewById(R.id.newWallet);
        TextView header=dialogView.findViewById(R.id.textView27);
        final String p1="(([0-9]+)|(([0-9]+)*[.])|([-]*([0-9]+))|(([-]*([0-9]+))*[.]))+";
        header.setText(context.getString(R.string.wlletDialog,user));
        cWamount.setText(curency+oldBal1.getText().toString());
        newBalance.setHint(context.getString(R.string.new_Balance,curency));
        TextInputLayout til=dialogView.findViewById(R.id.tIL);
        til.setHint(curency);

        confirmButton.setEnabled(false);
        newBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmButton.setEnabled((newBalance.getText().toString().trim().matches(p1)));

                    if(!(newBalance.getText().toString().trim().matches(p1)))
                    {
                        vp.setBackgroundColor(Color.RED);
                        newBalance.getText().clear();
                        newBalance.setError("Inavid entry");

                    }
                    else
                    { newBalance.setError(null);vp.setBackgroundColor(Color.GREEN);
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
              oldBal1.setText(formatbal(this.balance));
          } catch (IOException e1) {
              e1.printStackTrace();
          }
          //oldBal2.setText(getsBalance());

            });




    }/*
    public void walletDialog(Label curency,Label oldBal1,Label tempBal)throws IOException
    {
        DropShadow ds= new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);

        Dialog dialog=new Dialog();
        dialog.setTitle(user+"'s Wallet");
        dialog.setHeaderText("Welcome to Your Wallet "+user+"\nHere you can set the Amount you want Budget Around");
        dialog.setGraphic(new ImageView(new Image("Assets/wallet.png")));
        //dialog.getStyleClass().add("wallet_Dialog");

        ButtonType done=new ButtonType("\u2713",ButtonData.OK_DONE);
        //done.getStyleClass().add("wallet_DialogBtn");
        dialog.getDialogPane().getButtonTypes().addAll(done,ButtonType.CANCEL);
        //dialog.setEffecf(ds);
        GridPane grid=new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,200,10,10));

        Label cW=new Label("Current Wallet");
        cW.getStyleClass().add("cW");
        cW.setFont(Font.font("Lagdon", FontWeight.BOLD,24));
        cW.setTextFill(Color.rgb(102,253,253));

        Label cWamount=new Label(curency.getText()+" "+getsBalance());
        cWamount.setFont(Font.font("Lagdon", FontWeight.BOLD,24));
        cWamount.setTextFill(Color.rgb(102,253,253));
        cW.setEffect(ds);
        cWamount.getStyleClass().add("cW");

        VBox main=new VBox();
        main.setStyle("-fx-background-color:#00394d;");

        Label nW=new Label("New Wallet:(in digits)");nW.setEffect(ds);
        nW.setFont(Font.font("Lagdon", FontWeight.BOLD,16));
        nW.setTextFill(Color.rgb(102,253,253));
        TextField newBalance=new TextField();
        newBalance.getStyleClass().add("newBal");
        newBalance.setStyle("-fx-background-color:transparent;-fx-min-width: 160px;-fx-min-height: 45px;  -fx-max-width: 270px; -fx-max-height: 45px;-fx-prompt-font-size:15px;-fx-border-width: 0 0 5 0;-fx-text-fill:white;-fx-border-color:#009900;-fx-font: normal bold 20 Langdon;");
        newBalance.setEffect(ds);
        newBalance.setPromptText(curency.getText()+" Enter new Balance");
        main.getChildren().addAll(cW,nW,newBalance);
        grid.add(cW,0,0);
        grid.add(cWamount,1,0);
        grid.add(nW,0,1);
        grid.add(newBalance,1,1);
        grid.setStyle("-fx-background-color:#00394d;");
        final String p1="(([0-9]+)|(([0-9]+)*[.])|([-]*([0-9]+))|(([-]*([0-9]+))*[.]))+";

        Node confirmButton=dialog.getDialogPane().lookupButton(done);
        confirmButton.setDisable(true);
        newBalance.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(!(newValue.trim().matches(p1)));
            newBalance.setOnAction(e->{
                if(!(newValue.trim().matches(p1)))
                {
                    newBalance.setStyle("-fx-background-color:transparent;-fx-min-width: 160px;-fx-min-height: 45px;  -fx-max-width: 270px;-fx-prompt-font-size:15px; -fx-max-height: 45px;-fx-border-width: 0 0 5 0;-fx-text-fill:white;-fx-border-color:red;-fx-font: normal bold 20 Langdon;");

                    newBalance.clear();
                }
                else
                    newBalance.setStyle("-fx-background-color:transparent;-fx-min-width: 160px;-fx-min-height: 45px;  -fx-max-width: 270px;-fx-prompt-font-size:15px; -fx-max-height: 45px;-fx-border-width: 0 0 5 0;-fx-text-fill:white;-fx-border-color:green;-fx-font: normal bold 20 Langdon;");

            });
        });

        dialog.getDialogPane().setContent(grid);
        //dialog.getButtonTypes().setAll(done,ButtonType.CANCEL);


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == done)
        {

            if(newBalance.getText().matches(p1))
            {
                this.balance=Double.parseDouble(newBalance.getText().trim());
                changeBalance();


                oldBal1.setText(getsBalance());
                tempBal.setText(getsBalance());
                //oldBal2.setText(getsBalance());

            }


            else{
                newBalance.clear();
                newBalance.setStyle("-fx-border-color:red;");
                confirmButton.setDisable(true);
            }
        }
        else {
            // ... user chose CANCEL or closed the dialog
        }
        DialogPane dialogPane=dialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("user.css").toExternalForm());
        dialogPane.getStyleClass().add("wallet_Dialog");
    }*/


}


