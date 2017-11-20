package finalproject;

public class Clients {

    private String ID;
    private String LastName;
    private String FirstName;
    private String Adress;
    private String DOB;

    public Clients(String ID, String LastName, String FirstName, String Adress, String DOB) {
        this.ID = ID;
        this.LastName = LastName;
        this.FirstName = FirstName;
        this.Adress = Adress;
        this.DOB = DOB;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
    
    @Override
    public String toString() {
        String clients;
        clients = "Client ID : " + this.ID + "\n"
                + "Client Last Name : " + this.LastName + "\n"
                + "Client First Name : " + this.FirstName + "\n"
                + "Client Address : " + this.Adress + "\n"
                + "Client DOB : " + this.DOB + "\n";
        return clients;

    }

}
