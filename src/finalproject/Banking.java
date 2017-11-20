
package finalproject;

public class Banking {
    private String ClientID;
    private String BankName;
    private String AccountNumber;
    private String AccountType;
    private double Balance;

    public Banking(String ClientID, String BankName, String AccountNumber, String AccountType, double Balance) {
        this.ClientID = ClientID;
        this.BankName = BankName;
        this.AccountNumber = AccountNumber;
        this.AccountType = AccountType;
        this.Balance = Balance;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String AccountType) {
        this.AccountType = AccountType;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }
    
    
    @Override
    public String toString(){
        String banking;
        banking = "Client ID : " + this.ClientID + "\n"
                + "Bank Name : " + this.BankName + "\n"
                + "Account Number : " + this.AccountNumber + "\n"
                + "Account Type : " + this.AccountType + "\n"
                + "Balance : " + this.Balance + "\n";
        return banking;
    }
    
    
}
