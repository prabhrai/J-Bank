package finalproject;

public class BankingTransaction {


    private String AccountNumber;
    private Double TransactionAmount;
    private String Date;

    public BankingTransaction( String AccountNumber, Double TransactionAmount, String Date) {
        this.AccountNumber = AccountNumber;
        this.TransactionAmount = TransactionAmount;
        this.Date = Date;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public Double getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(Double TransactionAmount) {
        this.TransactionAmount = TransactionAmount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    
     @Override
    public String toString(){
        String bt;
        bt = "Account Number : " + this.AccountNumber + "\n"
                + "Transaction Amount : " + this.TransactionAmount.toString() + "\n"
                + "Date : " + this.Date + "\n";
        return bt;
    }
    
}
