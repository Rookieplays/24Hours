package ie.ul.o.daysaver;

/**
 * Created by ollie on 15/02/2018.
 */

public class UserInformation {
    /**GLobal Varibles*/
    private String First_Name="";
    private String Last_Name="";
    private String Email="";
    private String Phone="";
    private int Age=0;
    private String Gender="";
    private String Username="You";
    private String Image="";


    public UserInformation(){

    }
    public UserInformation(String Email)
    {
        this.Email=Email;
    }

    public UserInformation(String first_Name, String username, String image) {
        First_Name = first_Name;
        Username = username;
        Image = image;
    }

    public UserInformation(String Username, String First_Name, String Last_Name, String  Phone, int Age, String  Gender, String Image)
    {
        this.First_Name=First_Name;
        this.Last_Name=Last_Name;
        this.Phone=Phone;
        this.Age=Age;
        this.Gender=Gender;
        this.Username=Username;
        this.Image=Image;
    }
    public UserInformation(String Username,String First_Name,String Last_Name,String Phone,String Email,int Age,String Gender,String Image)
    {
        this.First_Name=First_Name;
        this.Last_Name=Last_Name;
        this.Phone=Phone;
        this.Email=Email;
        this.Age=Age;
        this.Username=Username;
        this.Gender=Gender;
        this.Image=Image;
    }

    public String getEmail() {
        return Email;
    }

    public String getLastName() {
        return Last_Name;
    }

    public String getPhone_number() {
        return Phone;
    }

    public String getFirstName() {
        return First_Name;
    }

    public int getAge() {
        return Age;
    }

    public String getGender() {
        return Gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setFirstName(String First_Name) {
        this.First_Name = First_Name;
    }

    public void setLastName(String Last_Name) {
        this.Last_Name = Last_Name;
    }

    public void setPhone_number(String Phone) {
        this.Phone = Phone;
    }
    public String getUsername(){return Username;}

    public void setUsername(String username) {
        Username = username;
    }

    public String toString()
    {
        return Username+"\n"+First_Name+"\n"+Last_Name+"\n"+Age+"\n"+Email+"\n"+Phone+"";
    }
}
