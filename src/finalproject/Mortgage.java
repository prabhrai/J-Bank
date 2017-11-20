package finalproject;

public class Mortgage {

    private String ClientID;
    private String BankName;
    private String AccountNumber;
    private Double OriginalLoanAmount;
    private String LoanDate;

    public Mortgage(String ClientID, String BankName, String AccountNumber, Double OriginalLoanAmount, String LoanDate) {
        this.ClientID = ClientID;
        this.BankName = BankName;
        this.AccountNumber = AccountNumber;
        this.OriginalLoanAmount = OriginalLoanAmount;
        this.LoanDate = LoanDate;
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

    public Double getOriginalLoanAmount() {
        return OriginalLoanAmount;
    }

    public void setOriginalLoanAmount(Double OriginalLoanAmount) {
        this.OriginalLoanAmount = OriginalLoanAmount;
    }

    public String getLoanDate() {
        return LoanDate;
    }

    public void setLoanDate(String LoanDate) {
        this.LoanDate = LoanDate;
    }
    
    
    @Override
    public String toString(){
        String temp; 
        temp = "Client ID : " + this.ClientID + "\n"
                + "BankName : " + this.BankName + "\n"
                + "Account Number : " + this.AccountNumber + "\n"
                + "Original Loan Amount : " + this.OriginalLoanAmount.toString() + "\n"
                + "Loan Date : " + this.LoanDate + "\n";
        return temp;
    }
    
}
