package finalproject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class BankingTransactionController implements Initializable , ControlledScreen{
    ScreensController myController;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       loadBankingTransaction();
       clearBT();
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    
    // Search
    @FXML
    private TextField txtBTSearch;
    @FXML
    private TextArea txtBTSearchResult;
    
    // Add
    @FXML
    private TextField txtBTAddAccountNumber;
    @FXML
    private TextField txtBTAddTransactionAmount;
    @FXML
    private DatePicker txtBTAddDate;
    @FXML
    private TextArea txtBTAddStatus;
    
    // Modify
    @FXML
    private TextField txtBTModifyAccountNumberSearch;
    @FXML
    private TextField txtBTModifyAccountNumber;
    @FXML
    private TextField txtBTModifyTransactionAmount;
    @FXML
    private DatePicker txtBTModifyDate;
    @FXML
    private TextArea txtBTModifyStatus;
    @FXML
    private DatePicker txtBTModifySearchDate;
    
    // Delete
    @FXML
    private TextField txtBTDeleteAccountNumber;
    @FXML
    private DatePicker txtBTDeleteDate;
    @FXML
    private TextArea txtBTDeleteStatus;
    @FXML
    private TextArea txtBTDeleteResult;
   
     public void exitScreen(){
         clearBT();
         writeBT();
     }
             
    @FXML
    public void clearBT() {
    txtBTSearch.clear();
    txtBTSearchResult.clear();
    // txtBTAddClientID.clear();
    txtBTAddAccountNumber.clear();
    txtBTAddTransactionAmount.clear();
    txtBTAddDate.setValue(null);
    txtBTAddStatus.clear();
    // txtBTModifyClientID.clear();
    txtBTModifyAccountNumberSearch.clear();
    txtBTModifyAccountNumber.clear();
    txtBTModifyTransactionAmount.clear();
    txtBTModifyDate.setValue(null);
    txtBTModifyStatus.clear();
    txtBTModifySearchDate.setValue(null);
    txtBTDeleteAccountNumber.clear();
    txtBTDeleteDate.setValue(null);
    txtBTDeleteStatus.clear();
    txtBTDeleteResult.clear();
    }
    
    List<BankingTransaction> myBankingTransc = new ArrayList<>();
    
   
    public void loadBankingTransaction() {
        Path file = Paths.get("BankingTrans.dat");
        InputStream input = null;
        String s = null;
        try {
            input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            s = reader.readLine();
            double temp;
            while (s != null) {
                String[] btImport = s.split("\t");
                temp = Double.parseDouble(btImport[1]);
                String finalDate = formatDate(btImport[2]);
                myBankingTransc.add(new BankingTransaction(btImport[0],temp, finalDate));
                s = reader.readLine();
            }
            input.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
    
    public String formatDate(String date) {
        String[] tempdate = date.split("/");
        // System.out.print(date + " " + tempdate[0].length() + " " + tempdate[1].length());
        if (tempdate[0].length() < 2) {
            tempdate[0] = "0" + tempdate[0];
        }
        if (tempdate[1].length() < 2) {
            tempdate[1] = "0" + tempdate[1];
        }
        String Date = tempdate[0] + "/" + tempdate[1] + "/" + tempdate[2];
        // System.out.println("  Date : " + Date);
        return Date;
    }
    
    @FXML
    public void handleBtnBTSearch(ActionEvent event) {
        String search = txtBTSearch.getText();
        txtBTSearchResult.clear();
        boolean found = false;
        for (BankingTransaction temp : myBankingTransc) {
            if (temp.getAccountNumber().equals(search)) {
                found = true;
                txtBTSearchResult.appendText(temp+ "\n"); //.toString() 
            }
        }
        if(!found){
            txtBTSearchResult.setText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnBTAdd(ActionEvent event) {
        try{
        if (txtBTAddAccountNumber.getText().isEmpty() || txtBTAddTransactionAmount.getText().isEmpty() || txtBTAddDate.getValue().toString().isEmpty() ) {
            txtBTAddStatus.setText("Invalid Input. The entry has not been completed. Please try again.");
        } else {
        double balance = Double.parseDouble(txtBTAddTransactionAmount.getText());
        String date = txtBTAddDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        myBankingTransc.add(new BankingTransaction(txtBTAddAccountNumber.getText(), balance, date));
        txtBTAddStatus.setText("The entry has been done.");
        }
        }
        catch(Exception e){
           System.out.println("The following error happened : " + e.getMessage());
        }
    }

    public void handleBtnBTDeleteSearch(ActionEvent event) {
        if (txtBTDeleteAccountNumber.getText().isEmpty()) {
         txtBTDeleteResult.appendText("Invalid Input. Please enter all fields for search.");
        }
        else {
        String searchAcct = txtBTDeleteAccountNumber.getText();
        String date = txtBTDeleteDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        txtBTDeleteResult.clear();;
        boolean found = false;
        for (BankingTransaction temp : myBankingTransc) {
            if (temp.getDate().equals(date) && temp.getAccountNumber().equals(searchAcct)) {
                found = true;
                txtBTDeleteResult.appendText("The results matching the search are shown." + "\n\n");
                txtBTDeleteResult.appendText(temp + "\n");
            }
        }
        if (!found) {
            txtBTDeleteResult.appendText("No result found for this search.");
        }
        }
    }

    public void handleBtnBTDelete(ActionEvent event) {
        try {
            String searchAcct = txtBTDeleteAccountNumber.getText();
            String date = txtBTDeleteDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            txtBTDeleteResult.clear();
            Iterator<BankingTransaction> iter = myBankingTransc.iterator();
            while (iter.hasNext()) {
                BankingTransaction temp = iter.next();
                if (temp.getDate().equals(date) && temp.getAccountNumber().equals(searchAcct)) {
                    iter.remove();
                }
            }
            txtBTDeleteStatus.setText("The deletion is completed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
   

    public void handleBtnBTModifySearch(ActionEvent event) {
        String searchAcct = txtBTModifyAccountNumberSearch.getText();
        String date = txtBTModifySearchDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        txtBTDeleteResult.clear();;
        boolean found = false;
        for (BankingTransaction temp : myBankingTransc) {
            if (temp.getDate().equals(date) && temp.getAccountNumber().equals(searchAcct)) {
                found = true;
                txtBTModifyAccountNumber.setText(temp.getAccountNumber());
                txtBTModifyTransactionAmount.setText(temp.getTransactionAmount().toString());
                String inDate = temp.getDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate tempDate = LocalDate.parse(inDate, formatter);
                txtBTModifyDate.setValue(tempDate);
            }
        }
        if (!found) {
            txtBTDeleteResult.appendText("No result found for this search.");
        }

    }

    public void handleBtnBTModifySubmit(ActionEvent event) {
       String searchAcct = txtBTModifyAccountNumberSearch.getText();
        String date = txtBTModifySearchDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        txtBTModifyStatus.clear();

        if (txtBTModifyAccountNumber.getText().isEmpty() || txtBTModifyTransactionAmount.getText().isEmpty() || txtBTModifyDate.getValue().toString().isEmpty() ) {
        txtBTModifyStatus.setText("Invalid Input. Please try again.");
        } else {
            for (BankingTransaction temp : myBankingTransc) {
                if (temp.getDate().equals(date) && temp.getAccountNumber().equals(searchAcct)) {
                    temp.setAccountNumber(txtBTModifyAccountNumber.getText());
                    Double tempBalance = Double.parseDouble(txtBTModifyTransactionAmount.getText());
                    temp.setTransactionAmount(tempBalance);
                    String tempDate = txtBTModifyDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    temp.setDate(tempDate);
                }
            }
            txtBTModifyStatus.setText("Modification is completed successfully.");
        }
    }

    public void writeBT() {
        Path file = Paths.get("BankingTrans.dat");
        try {
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            StringBuilder wb = new StringBuilder();

            for (BankingTransaction temp : myBankingTransc) {
                wb.setLength(0);
                wb.append(temp.getAccountNumber()).append("\t");
                wb.append(temp.getTransactionAmount()).append("\t");
                wb.append(temp.getDate()).append("\n");
                writer.write(wb.toString());
            }
            writer.close();
        } catch (Exception e) {
        System.out.println("The following error happened : " + e.getMessage());
        }
    }
 
    
    @FXML
    public void handleMnuMain(ActionEvent event) {
        exitScreen();
        myController.setScreen("Main");
    }
     @FXML
    public void handleMnuClients(ActionEvent event) {
        exitScreen();
        myController.setScreen("Clients");
    }
     @FXML
    public void handleMnuBanking(ActionEvent event) {
        exitScreen();
        myController.setScreen("Banking");
    }
    public void handleMnuBankingTransaction(ActionEvent event) {
        exitScreen();
        myController.setScreen("BankingTransaction");
    }
     @FXML
    public void handleMnuMortgage(ActionEvent event) {
        exitScreen();
        myController.setScreen("Mortgage");
    }
     @FXML
    public void handleMnuMortgageTransaction(ActionEvent event) {
        exitScreen();
        myController.setScreen("MortgageTransaction");
    }
    public void handleMnuPortfolio(ActionEvent event) {
        exitScreen();
        myController.setScreen("Portfolio");
    }
    @FXML
    public void handleMnuAbout(ActionEvent event) {
        exitScreen();
        myController.setScreen("About");
    }
}