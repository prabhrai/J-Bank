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

public class MortgageController implements Initializable, ControlledScreen {

    ScreensController myController;

    // Search
    @FXML
    private TextField txtMSearch;
    @FXML
    private TextArea txtMSearchResult;
    // Add
    @FXML
    private TextField txtMAddClientID;
    @FXML
    private TextField txtMAddBankName;
    @FXML
    private DatePicker txtBTAddLoanDate;
    @FXML
    private TextArea txtMAddStatus;
    @FXML
    private TextField txtMAddAccountNumber;
    @FXML
    private TextField txtMAddOriginalLoanAmount;

    // Modify
    @FXML
    private TextField txtMModifyClientIDSearch;
    @FXML
    private TextField txtMModifyAccoutNumberSearch;

    @FXML
    private TextField txtMModifyCiientID;
    @FXML
    private TextField txtMModifyBankName;
    @FXML
    private DatePicker txtMModifyLoanDate;
    @FXML
    private TextArea txtMModifyStatus;
    @FXML
    private TextField txtMModifyAccountNumber;
    @FXML
    private TextField txtMModifyOriginalLoanAmount;

    // Delete
    @FXML
    private TextField txtMDeleteClientIDSearch;
    @FXML
    private TextArea txtMDeleteResult;
    @FXML
    private TextArea txtMDeleteStatus;
    @FXML
    private TextField txtMDeleteAccountNumberSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadBankingTransaction();
        clearMortgage();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void exitScreen() {
        clearMortgage();
        writeMortgage();
    }

    @FXML
    public void clearMortgage() {
        txtMSearch.clear();
        txtMSearchResult.clear();
        txtMAddClientID.clear();
        txtMAddBankName.clear();
        txtBTAddLoanDate.setValue(null);
        txtMAddStatus.clear();
        txtMAddAccountNumber.clear();
        txtMAddOriginalLoanAmount.clear();
        txtMModifyClientIDSearch.clear();
        txtMModifyAccoutNumberSearch.clear();
        txtMModifyCiientID.clear();
        txtMModifyBankName.clear();
        txtMModifyLoanDate.setValue(null);
        txtMModifyStatus.clear();
        txtMModifyAccountNumber.clear();
        txtMModifyOriginalLoanAmount.clear();
        txtMDeleteClientIDSearch.clear();
        txtMDeleteResult.clear();
        txtMDeleteStatus.clear();
        txtMDeleteAccountNumberSearch.clear();
    }

    List<Mortgage> myMortgage = new ArrayList<>();

    public void loadBankingTransaction() {
        Path file = Paths.get("Mortgage.dat");
        InputStream input = null;
        String s = null;
        try {
            input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            s = reader.readLine();
            double temp;
            while (s != null) {
                String[] mortgageImport = s.split("\t");
                temp = Double.parseDouble(mortgageImport[3]);
                String finalDate = formatDate(mortgageImport[4]);
                myMortgage.add(new Mortgage(mortgageImport[0], mortgageImport[1], mortgageImport[2], temp, finalDate));
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
    public void handleBtnMortgageSearch(ActionEvent event) {
        String search = txtMSearch.getText();
        txtMSearchResult.clear();
        boolean found = false;
        for (Mortgage temp : myMortgage) {
            if (temp.getAccountNumber().equals(search) || temp.getClientID().equals(search)) {
                found = true;
                txtMSearchResult.appendText(temp.toString() + "\n");
            }
        }
        if (!found) {
            txtMSearchResult.setText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnMortgageAdd(ActionEvent event) {
        try {
            if (txtMAddClientID.getText().isEmpty() || txtMAddBankName.getText().isEmpty() || txtMAddAccountNumber.getText().isEmpty() || txtMAddOriginalLoanAmount.getText().isEmpty() || txtBTAddLoanDate.getValue().toString().isEmpty()) {
                txtMAddStatus.setText("Invalid Input. The entry has not been completed. Please try again.");
            } else {
                double temp = Double.parseDouble(txtMAddOriginalLoanAmount.getText());
                String date = txtBTAddLoanDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                myMortgage.add(new Mortgage(txtMAddClientID.getText(), txtMAddBankName.getText(), txtMAddAccountNumber.getText(), temp, date));
                txtMAddStatus.setText("The entry has been done.");
            }
        } catch (Exception e) {
            System.out.println("The following error happened : " + e.getMessage());
        }
    }

    
    public void handleBtnMortgageDeleteSearch(ActionEvent event) {
        String searchAcct = txtMDeleteAccountNumberSearch.getText();
        String searchID = txtMDeleteClientIDSearch.getText();
        txtMDeleteResult.clear();
        txtMDeleteStatus.clear();
        boolean found = false;
        for (Mortgage temp : myMortgage) {
            if (temp.getClientID().equals(searchID) && temp.getAccountNumber().equals(searchAcct)) {
                found = true;
                txtMDeleteResult.appendText("The results matching the search are shown." + "\n\n");
                txtMDeleteResult.appendText(temp + "\n");
            }
        }
        if (!found) {
            txtMDeleteStatus.appendText("No result found for this search.");
        }
    }

    public void handleBtnMortgageDeleteSubmit(ActionEvent event) {
        try {
            String searchAcct = txtMDeleteAccountNumberSearch.getText();
            String searchID = txtMDeleteClientIDSearch.getText();
            txtMDeleteResult.clear();
            txtMDeleteStatus.clear();
            Iterator<Mortgage> iter = myMortgage.iterator();
            while (iter.hasNext()) {
                Mortgage temp = iter.next();
                if (temp.getClientID().equals(searchID) && temp.getAccountNumber().equals(searchAcct)) {
                    iter.remove();
                }
            }
            txtMDeleteStatus.setText("The deletion is completed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void handleBtnMortgageModifySearch(ActionEvent event) {
        String searchAcct = txtMModifyAccoutNumberSearch.getText();
        String searchID = txtMModifyClientIDSearch.getText();
        txtMModifyStatus.clear();
        boolean found = false;
        for (Mortgage temp : myMortgage) {
            if (temp.getClientID().equals(searchID) && temp.getAccountNumber().equals(searchAcct)) {
                found = true;
                String inDate = temp.getLoanDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate tempDate = LocalDate.parse(inDate, formatter);
                txtMModifyCiientID.setText(temp.getClientID());
                txtMModifyBankName.setText(temp.getBankName());
                txtMModifyAccountNumber.setText(temp.getAccountNumber());
                txtMModifyOriginalLoanAmount.setText(temp.getOriginalLoanAmount().toString());
                txtMModifyLoanDate.setValue(tempDate);
            }
        }
        if (!found) {
            txtMModifyStatus.appendText("No result found for this search.");
        }
    }

    public void handleBtnMortgageModifySubmit(ActionEvent event) {
       String searchAcct = txtMModifyAccoutNumberSearch.getText();
        String searchID = txtMModifyClientIDSearch.getText();
        txtMModifyStatus.clear();

        if (txtMModifyCiientID.getText().isEmpty() || txtMModifyBankName.getText().isEmpty() || txtMModifyAccountNumber.getText().isEmpty() || 
            txtMModifyOriginalLoanAmount.getText().isEmpty() || txtMModifyLoanDate.getValue().toString().isEmpty() ) {
        txtMModifyStatus.setText("Invalid Input. Please try again.");
        } else {
            for (Mortgage temp : myMortgage) {
                if (temp.getClientID().equals(searchID) && temp.getAccountNumber().equals(searchAcct)) {
                    temp.setClientID(txtMModifyCiientID.getText());
                    temp.setBankName(txtMModifyBankName.getText());
                    temp.setAccountNumber(txtMModifyAccountNumber.getText());
                    Double loanAmount = Double.parseDouble(txtMModifyOriginalLoanAmount.getText());
                    temp.setOriginalLoanAmount(loanAmount);
                    String tempDate = txtMModifyLoanDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    temp.setLoanDate(tempDate);
                }
            }
            txtMModifyStatus.setText("Modification is completed successfully.");
        }
    }
     
    public void writeMortgage() {
        Path file = Paths.get("Mortgage.dat");
        try {
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            StringBuilder wb = new StringBuilder();

            for (Mortgage temp : myMortgage) {
                wb.setLength(0);
                wb.append(temp.getClientID()).append("\t");
                wb.append(temp.getBankName()).append("\t");
                wb.append(temp.getAccountNumber()).append("\t");
                wb.append(temp.getOriginalLoanAmount()).append("\t");
                wb.append(temp.getLoanDate()).append("\n");
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
