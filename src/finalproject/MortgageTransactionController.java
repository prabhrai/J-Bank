/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class MortgageTransactionController implements Initializable, ControlledScreen {

    ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMortgageTransaction();
        clearMT();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    private TextField txtMTSearch;
    @FXML
    private TextArea txtMTSearchResult;
    @FXML
    private DatePicker txtMTAddPaymentDate;
    @FXML
    private TextArea txtMTAddStatus;
    @FXML
    private TextField txtMTAddMortgageAccount;
    @FXML
    private TextField txtMTAddAmountPaid;
    @FXML
    private TextField txtMTAddTransactionID;
    @FXML
    private DatePicker txtMTModifyPaymentDate;
    @FXML
    private TextArea txtMTModifyStatus;
    @FXML
    private TextField txtMTModifyMortgageAccount;
    @FXML
    private TextField txtMTModifyAmountPaid;
    @FXML
    private TextField txtMTModifyTransactionIDSearch;
    @FXML
    private TextField txtMTModifyTransactionID;
    @FXML
    private TextArea txtMTDeleteResult;
    @FXML
    private TextArea txtMTDeleteStatus;
    @FXML
    private TextField txtMTDeleteTransactionID;

    public void exitScreen() {
        clearMT();
        writeMT();
    }

    @FXML
    public void clearMT() {
        txtMTSearch.clear();
        txtMTSearchResult.clear();
        txtMTAddPaymentDate.setValue(null);
        txtMTAddStatus.clear();
        txtMTAddMortgageAccount.clear();
        txtMTAddTransactionID.clear();
        txtMTAddAmountPaid.clear();
        txtMTModifyPaymentDate.setValue(null);
        txtMTModifyStatus.clear();
        txtMTModifyMortgageAccount.clear();
        txtMTModifyAmountPaid.clear();
        txtMTDeleteTransactionID.clear();
        txtMTDeleteResult.clear();
        txtMTDeleteStatus.clear();
        txtMTModifyTransactionIDSearch.clear();
        txtMTModifyTransactionID.clear();
    }

    List<MortgageTransaction> myMortgageTransaction = new ArrayList<>();

    public void loadMortgageTransaction() {
        Path file = Paths.get("MortgageTrans.dat");
        InputStream input = null;
        String s = null;
        try {
            input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            s = reader.readLine();
            double tempAP = 0.00, tempTP = 0.00;
            while (s != null) {
                String[] mtImport = s.split("\t");

                String finalDate = formatDate(mtImport[3]);
                tempAP = Double.parseDouble(mtImport[2]);
                tempTP = Double.parseDouble(mtImport[4]);
                myMortgageTransaction.add(new MortgageTransaction(mtImport[0], mtImport[1], tempAP, finalDate, tempTP));
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
    public void handleBtnMTSearch(ActionEvent event) {
        String search = txtMTSearch.getText();
        txtMTSearchResult.clear();
        boolean found = false;
        for (MortgageTransaction temp : myMortgageTransaction) {
            if (temp.getTransactionID().equals(search) || temp.getMortgageAccount().equals(search)) {
                found = true;
                txtMTSearchResult.appendText(temp.toString() + "\n");
            }
        }
        if (!found) {
            txtMTSearchResult.setText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnMTAdd(ActionEvent event) {
        boolean transValid = true;
        try {
            if (txtMTAddTransactionID.getText().isEmpty() || txtMTAddMortgageAccount.getText().isEmpty() || txtMTAddAmountPaid.getText().isEmpty() || txtMTAddPaymentDate.getValue().toString().isEmpty()) {
                txtMTAddStatus.setText("Invalid Input. The entry has not been completed. Please try again.");
            } else {
                for (MortgageTransaction tempM : myMortgageTransaction) {
                    if (tempM.getTransactionID().equals(txtMTAddTransactionID.getText())) {
                        txtMTAddStatus.setText("The Transaction ID already exists. Please try again.");
                        txtMTAddTransactionID.clear();
                        transValid = false;
                    }
                }
                if (transValid) {
                    double amountPaid = Double.parseDouble(txtMTAddAmountPaid.getText());
                    String date = txtMTAddPaymentDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    Double TotalPaid = 0.00;
                    for (MortgageTransaction tempM : myMortgageTransaction) {
                        if (tempM.getMortgageAccount().equals(txtMTAddMortgageAccount.getText())) {
                            TotalPaid += tempM.getAmountPaid();
                        }
                    }
                    TotalPaid += amountPaid;
                    myMortgageTransaction.add(new MortgageTransaction(txtMTAddTransactionID.getText(), txtMTAddMortgageAccount.getText(), amountPaid, date, TotalPaid));
                    txtMTAddStatus.setText("The entry has been done.");
                }
            }
        } catch (Exception e) {
            System.out.println("The following error happened : " + e.getMessage());
        }
    }

    @FXML
    public void handleBtnMTDeleteSearch(ActionEvent event) {
        String searchID = txtMTDeleteTransactionID.getText();
        txtMTDeleteResult.clear();
        txtMTDeleteStatus.clear();
        boolean found = false;
        for (MortgageTransaction temp : myMortgageTransaction) {
            if (temp.getTransactionID().equals(searchID)) {
                found = true;
                txtMTDeleteResult.appendText("The results matching the search are shown." + "\n\n");
                txtMTDeleteResult.appendText(temp + "\n");
            }
        }
        if (!found) {
            txtMTDeleteStatus.appendText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnMTDeleteSubmit(ActionEvent event) {
        try {
            String searchID = txtMTDeleteTransactionID.getText();
            txtMTDeleteResult.clear();
            txtMTDeleteStatus.clear();
            Iterator<MortgageTransaction> iter = myMortgageTransaction.iterator();
            while (iter.hasNext()) {
                MortgageTransaction temp = iter.next();
                if (temp.getTransactionID().equals(searchID)) {
                    iter.remove();
                }
            }
            txtMTDeleteStatus.setText("The deletion is completed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleBtnMTModifySearch(ActionEvent event) {
        String searchID = txtMTModifyTransactionIDSearch.getText();
        txtMTModifyStatus.clear();
        boolean found = false;
        for (MortgageTransaction temp : myMortgageTransaction) {
            if (temp.getTransactionID().equals(searchID)) {
                found = true;
//                String inDate = temp.getPaymentDate();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//                LocalDate tempDate = LocalDate.parse(inDate, formatter);
//                txtMTModifyPaymentDate.setValue(tempDate);

                txtMTModifyTransactionID.setText(temp.getTransactionID());
                txtMTModifyMortgageAccount.setText(temp.getMortgageAccount());
                txtMTModifyAmountPaid.setText(temp.getAmountPaid().toString());
                String inDate = temp.getPaymentDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate tempDate = LocalDate.parse(inDate, formatter);
                txtMTModifyPaymentDate.setValue(tempDate);
            }
        }
        if (!found) {
            txtMTModifyStatus.appendText("No result found for this search.");
        }
    }

    public void handleBtnMTModifySubmit(ActionEvent event) {
        boolean transValid = true;
        String searchID = txtMTModifyTransactionIDSearch.getText();
        txtMTModifyStatus.clear();

        if (txtMTModifyTransactionID.getText().isEmpty() || txtMTModifyMortgageAccount.getText().isEmpty()
                || txtMTModifyAmountPaid.getText().isEmpty() || txtMTModifyPaymentDate.getValue().toString().isEmpty()) {
            txtMTModifyStatus.setText("Invalid Input. Please try again.");
        } else if (!searchID.equals(txtMTModifyTransactionID.getText())) {
            txtMTModifyStatus.setText("The Transaction ID can not be changed. Please try again.");
            transValid = false;
        }
        if (transValid) {

            for (MortgageTransaction temp : myMortgageTransaction) {
                if (temp.getTransactionID().equals(searchID)) {

                    String tempDate = txtMTModifyPaymentDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    temp.setPaymentDate(tempDate);
                    temp.setTransactionID(txtMTModifyTransactionID.getText());
                    temp.setMortgageAccount(txtMTModifyMortgageAccount.getText());
                    Double payAmount = Double.parseDouble(txtMTModifyAmountPaid.getText());
                    if (temp.getAmountPaid() == payAmount) {
                        temp.setTotalPaid(temp.getTotalPaid());
                    } else {
                        Double payment = temp.getTotalPaid() - temp.getAmountPaid() + payAmount;
                        temp.setTotalPaid(payment);
                    }
                    temp.setAmountPaid(payAmount);
                }
            }
            txtMTModifyStatus.setText("Modification is completed successfully.");
        }
    
}

public void writeMT() {
        Path file = Paths.get("MortgageTrans.dat");
        try {
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            StringBuilder wb = new StringBuilder();

            for (MortgageTransaction temp : myMortgageTransaction) {
                wb.setLength(0);
                wb.append(temp.getTransactionID()).append("\t");
                wb.append(temp.getMortgageAccount()).append("\t");
                wb.append(temp.getAmountPaid()).append("\t");
                wb.append(temp.getPaymentDate()).append("\t");
                wb.append(temp.getTotalPaid()).append("\n");
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

    public void handleBtnExit(ActionEvent event) {
        exitScreen();
        myController.setScreen("Main");
    }
}
