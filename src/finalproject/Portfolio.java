
package finalproject;

public class Portfolio {
    
    private String ClientID;
    private String StockSymbol;
    private int SharesPurchased;
    private String PurchasedDate;
    private Double PricePerShare;
    private Double ShareLastPrice;

    public Portfolio(String ClientID, String StockSymbol, int SharesPurchased, String PurchasedDate, Double PricePerShare, Double ShareLastPrice) {
        this.ClientID = ClientID;
        this.StockSymbol = StockSymbol;
        this.SharesPurchased = SharesPurchased;
        this.PurchasedDate = PurchasedDate;
        this.PricePerShare = PricePerShare;
        this.ShareLastPrice = ShareLastPrice;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getStockSymbol() {
        return StockSymbol;
    }

    public void setStockSymbol(String StockSymbol) {
        this.StockSymbol = StockSymbol;
    }

    public int getSharesPurchased() {
        return SharesPurchased;
    }

    public void setSharesPurchased(int SharesPurchased) {
        this.SharesPurchased = SharesPurchased;
    }

    public String getPurchasedDate() {
        return PurchasedDate;
    }

    public void setPurchasedDate(String PurchasedDate) {
        this.PurchasedDate = PurchasedDate;
    }

    public Double getPricePerShare() {
        return PricePerShare;
    }

    public void setPricePerShare(Double PricePerShare) {
        this.PricePerShare = PricePerShare;
    }

    public Double getShareLastPrice() {
        return ShareLastPrice;
    }

    public void setShareLastPrice(Double ShareLastPrice) {
        this.ShareLastPrice = ShareLastPrice;
    }
    
     @Override
    public String toString(){
        String temp; 
        temp = "Client ID : " + this.ClientID + "\n"
                + "Stock Symbol : " + this.StockSymbol + "\n"
                + "Shares Purchased : " + this.SharesPurchased + "\n"
                + "Purchased Date : " + this.PurchasedDate + "\n"
                + "Price Per Share : " + this.PricePerShare + "\n"
                + "Share Last Price : " + this.ShareLastPrice + "\n";
        return temp;
    }
    
}
