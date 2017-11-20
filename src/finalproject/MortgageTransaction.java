package finalproject;

import java.util.Objects;

public class MortgageTransaction {

    private String TransactionID;
    private String MortgageAccount;
    private Double AmountPaid;
    private String PaymentDate;
    private Double TotalPaid;

    public MortgageTransaction(String TransactionID, String MortgageAccount, Double AmountPaid, String PaymentDate, Double TotalPaid) {
        this.TransactionID = TransactionID;
        this.MortgageAccount = MortgageAccount;
        this.AmountPaid = AmountPaid;
        this.PaymentDate = PaymentDate;
        this.TotalPaid = TotalPaid;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
    }

    public String getMortgageAccount() {
        return MortgageAccount;
    }

    public void setMortgageAccount(String MortgageAccount) {
        this.MortgageAccount = MortgageAccount;
    }

    public Double getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(Double AmountPaid) {
        this.AmountPaid = AmountPaid;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    public Double getTotalPaid() {
        return TotalPaid;
    }

    public void setTotalPaid(Double TotalPaid) {
        this.TotalPaid = TotalPaid;
    }

    @Override
    public String toString(){
        String temp; 
        temp = "Transaction ID : " + this.TransactionID + "\n"
                + "Mortgage Account : " + this.MortgageAccount + "\n"
                + "Amount Paid : " + this.AmountPaid + "\n"
                + "Payment Date : " + this.PaymentDate + "\n"
                + "Total Paid : " + this.TotalPaid + "\n";
        return temp;
    }
   
}
