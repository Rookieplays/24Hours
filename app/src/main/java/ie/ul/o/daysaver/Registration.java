package ie.ul.o.daysaver;

/**
 * Created by Ollie on 03/02/2018.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
public class Registration extends LoginActivity
{
    static ArrayList<ArrayList<String>>loginCredentials=new ArrayList<ArrayList<String>>();
    private String userName="",password="";
    private double wallet,savings;
    private LocalDate date;
    private LocalTime time;
    private Context context;
    private String filePath;
    //  static Encryptor encript;



    public Registration(Context context)throws IOException
    {
        this.context=context;
        loginCredentials.clear();
        filePath = context.getFilesDir().getPath() + "/";loadUsers();

        userName="username";
        password="password";
        wallet=0.00;
        savings=0.00;
       // date=date.now();
        //time=time.now();

        // encript=new Encryptor(this.password,this.userName);


    }
    // @RequiresApi(api = Build.VERSION_CODES.O)
    public Registration(String userName, String password,Context context)throws IOException
    {
        loginCredentials.clear();
        this.context=context;
        filePath = context.getFilesDir().getPath() + "/";
        loadUsers();
        this.userName=userName;
        this.password=password;
        this.wallet=0.00;
        this.savings=0.00;
        /*this.date=date.now();
        this.time=time.now();
        //encript=new Encryptor(this.password,this.userName);*/
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Registration(String userName, String password, int wallet,Context context)throws IOException
    {
        loginCredentials.clear();
        this.context=context;
        filePath = context.getFilesDir().getPath() + "/";
          loadUsers();
        this.userName=userName;
        this.password=password;
        this.wallet=wallet;
        this.savings=0.00;
        this.date= LocalDate.now();
        this.time= LocalTime.now();
        // encript=new Encryptor(this.password,this.userName);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Registration(String userName, String password, int wallet, int savings)throws IOException
    {
        loginCredentials.clear();
        this.context=context;
         loadUsers();
        filePath = context.getFilesDir().getPath() + "/";
        this.userName=userName;
        this.password=password;
        this.wallet=wallet;
        this.savings=savings;
        this.date= LocalDate.now();
        this.time= LocalTime.now();
        // encript=new Encryptor(this.password,this.userName);
    }
    public ArrayList<ArrayList<String>> listOfUsers()
    {
        return loginCredentials;
    }
    public String getUsername()
    {

        return userName;
    }
    public String getPassword()
    {
        return password;
    }
    public void setUsername(String userName)
    {
        this.userName=userName;

    }
    public void setPassword(String password)
    {
        this.password=password;
    }
    public void setWallet(double wallet)
    {
        this.wallet=wallet;
    }
    public void setSavings(double savings)
    {
        this.savings=savings;
    }
    public static boolean validemail(String mail, EditText mailField)//String userName)
    {


        mail = mail.replaceAll(" ", "");
        String pattern = "[a-zA-Z0-9]+";
        String[] eName;
        String[] address;
        String mail2 = mail;
        //userDetails.add(new ArrayList<String>());
        boolean emailMatched = true;
        eName = mail.split("@");
        address = mail.split("\\.");
        //System.out.println()("Analyzing " + mail + "....");
        //System.out.println()("checking email for @...");
        if (mail.contains("@"))

        {
            //System.out.println()("checking if email is valid ..");
            if (eName[0].length() >= 4) {
                //System.out.println()("Analyzing Extention.");
                //System.out.println()(eName.length);
                return true;
            }else {
                mailField.setError(eName[0] + " is not a valid email name.");
                return false;
            }
        }
        else {mailField.setError("Email is Incorrect");
            return false;}

    }
    public boolean validUsername()

    {
        //System.out.println()("Username: "+userName);
        String format="[(([A-Z]+)*([a-z]+)*([0-9]+))]";
        return userName.length() >= 3 || !(userName.contains("->"));
    }
    public boolean validPassword(EditText et)
    {

        //System.out.println()(password);
        password=password.trim();
        password=password.replaceAll(" ","");
        // userDetails.add(new ArrayList<String>());
        int T=0,F=0;

        if (password.length()>=6)
        {
            //System.out.println()("Test 1 complete.. Password: "+password+" length: "+password.length()+" >= 6");
            if (password.matches(".*[A-Z]+.*"))
            {
                //System.out.println()("Test 2 complete.. Password: "+password+" has uppercase letters.");

                if(password.matches(".*[a-z]+.*"))
                {
                    //System.out.println()("Test 3 complete.. Password: "+password+" has lowerCase letters.");

                    if(password.matches(".*[0-9].*"))
                    {
                        //System.out.println()("Test 4 complete.. Password: "+password+" has  numbers.");
                        //System.out.println()("Good Password");
                        return true;
                    }else {et.setError("Must have a Number");return false;}
                }else {et.setError("Lowercase Letter missing");return false;}
            }else {et.setError("An UPPERCASE letter is a must");return false;}

        }
        else
        {
           et.setError("Password is too short min (6)");return false;
        }
    }
    public String printErrorMsg(char type)
    {
        if(type=='U')
            return "Oops Username must be more than three charcters";
        else if (type=='P')
            return "The password must reach the following criteria:\nMust be 8 digits Long. \n Must have at Least 1 or more uppercase characters.(A-Z)\n 1 or more lowerCase characters.(a-z)\n Base 10 digits(0-9)";
        else
            return "Error!!";
    }
    /*public double getSaving()
    {
        int userNameIndex=loginCredentials.get(0).indexOf(userName);
       //System.out.println()(userName+":"+userNameIndex);
       savings=Double.parseDouble(loginCredentials.get(3).get(userNameIndex));
       return savings;
    }*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getDate()
    {
        return LocalDate.now();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime getTime()
    {
        return LocalTime.now();
    }
    public boolean saveDetails()throws IOException
    {
        if (!(loginCredentials.get(0).contains(userName)&&loginCredentials.get(1).contains(password)))
        {
            String info=userName+"->"+password+"->"+date+" at "+time+"\n";
            String info2=userName+":"+0.00+":"+date+" at "+time+"\n";
            String info3=userName+":"+0.00+":"+date+" at "+time+"\n";

            writeToFile(filePath+"logBook.txt",info);
            writeToFile(filePath+"Wallet.txt",info2);
            writeToFile(filePath+"Savings.txt",info3);

            return true;
        }
        else return false;

    }

    /*public Double getWallet()
    {

        int userNameIndex=loginCredentials.get(0).indexOf(userName);
        //System.out.println()(userName+":"+userNameIndex);
        wallet=Double.parseDouble(loginCredentials.get(2).get(userNameIndex));
        return wallet;
    }*/
    public boolean validInput(String pattern,String input)
    {
        if(input==null)
            return true;
        else if (input!=null&&input.equals(""))
            return false;
        else return input.matches(pattern);
    }
    public boolean existingAccount()
    {
        //System.out.println()(userName+"-"+password);
        if (loginCredentials.get(0).contains(userName))
        {
            //System.out.println()(userName+" Was Last seen on: "+loginCredentials.get(2).get((loginCredentials.get(0).indexOf(userName))));
            return true;
        }

        else
            return false;
    }
    private void loadUsers()throws IOException
    {
        String temp[];
        Scanner filereader;
        loginCredentials.add(new ArrayList<String>());
        loginCredentials.add(new ArrayList<String>());
        loginCredentials.add(new ArrayList<String>());
        File file=new File(filePath+"logBook.txt");
        File file1=new File(filePath+"Wallet.txt");
        File file2=new File(filePath+"Savings.txt");

        if (file.exists()&&file1.exists()&&file2.exists())
        {
            filereader=new Scanner(file);
            while(filereader.hasNext())
            {
                temp=filereader.nextLine().split("->");
                for (int i=1;i<loginCredentials.size();i++ )
                {
                    loginCredentials.get(i).add(temp[i]);////System.out.println()(loginCredentials);
                }
            }filereader.close();
        }
        else
        {
            writeToFile(filePath+"logBook.txt","Account Name->Password->dateLoged\n");
            writeToFile(filePath+" Wallet.txt","Account Name:Balance:Date Last Modified\n");
            writeToFile(filePath+" Savings.txt","Account Name:Balance:Date Last Modified\n");

        }

    }
    public String toString()
    {
        return "Username: "+userName +" | Password: "+password+" | Wallet: "+wallet+" | Savings: "+savings+" | Date Last Logged in: "+date+" at "+time;
    }

    private void writeToFile(String filename,String info)throws IOException
    {
        FileWriter writer=new FileWriter(filename,true);
        writer.write(info);
        writer.close();
    }


}
