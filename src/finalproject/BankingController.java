package finalproject;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BankingController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private ListView<String> lvAccountTypeAdd = new ListView<String>();

    @FXML
    private ListView<String> lvAccountTypeModify = new ListView<String>();

    List<Banking> myBanking = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> items = FXCollections.observableArrayList(
                "Savings", "Checking", "Certificate Deposit");
        lvAccountTypeAdd.setItems(items);
        lvAccountTypeModify.setItems(items);
        clearBanking();
        loadBanking();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    private TextArea txtBAddStatus;
    @FXML
    private TextArea txtBModifyStatus;
    @FXML
    private TextArea txtBDeleteStatus;

    // search
    @FXML
    private TextField txtBFindSearch;
    @FXML
    private TextArea txtBFindResult;

    //add
    @FXML
    private TextField txtAddClientID;
    @FXML
    private TextField txtAddBankName;
    @FXML
    private TextField txtAddAccountNumber;
    @FXML
    private TextField txtAddBalance;

    // modify
    @FXML
    private TextField txtModifySearch;
    @FXML
    private TextField txtModifyClientID;
    @FXML
    private TextField txtModifyBankName;
    @FXML
    private TextField txtModifyAccountNumber;
    @FXML
    private TextField txtModifyBalance;

    // delete
    @FXML
    private TextField txtDeleteClientSearch;
    @FXML
    private TextArea txtDeleteClientResult;

    public void clearBanking() {
        txtBAddStatus.clear();
        txtBModifyStatus.clear();
        txtBDeleteStatus.clear();
        txtBFindSearch.clear();
        txtBFindResult.clear();
        txtAddClientID.clear();
        txtAddBankName.clear();
        txtAddAccountNumber.clear();
        txtAddBalance.clear();
        txtModifySearch.clear();
        txtModifyClientID.clear();
        txtModifyBankName.clear();
        txtModifyAccountNumber.clear();
        txtModifyBalance.clear();
        txtDeleteClientSearch.clear();
        txtDeleteClientResult.clear();
    }

    public void loadBanking() {
        Path file = Paths.get("Banking.dat");
        InputStream input = null;
        String s = null;
        try {
            input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            s = reader.readLine();
            double temp;
            while (s != null) {
                String[] bankingImport = s.split("\t");
                temp = Double.parseDouble(bankingImport[4]);
                myBanking.add(new Banking(bankingImport[0], bankingImport[1], bankingImport[2], bankingImport[3], temp));
                s = reader.readLine();
            }
            input.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void handleBtnBankingSearch(ActionEvent event) {
        String search = txtBFindSearch.getText();
        txtBFindResult.clear();

        for (Banking temp : myBanking) {
            if (temp.getClientID().equals(search) || temp.getAccountNumber().equals(search)) {
                txtBFindResult.appendText(temp + "\n");
            }
        }

    }

    @FXML
    public void handleBtnBankingAdd(ActionEvent event) {
        if (txtAddClientID.getText().isEmpty() || txtAddBankName.getText().isEmpty() || txtAddAccountNumber.getText().isEmpty() || txtAddBalance.getText().isEmpty() || lvAccountTypeAdd.getSelectionModel().getSelectedItem().isEmpty()) {
            txtBAddStatus.setText("Invalid Input. The entry has not been completed. Please try again.");
            return;
        }
        double balance = Double.parseDouble(txtAddBalance.getText());
        String accountType = lvAccountTypeAdd.getSelectionModel().getSelectedItem();
        myBanking.add(new Banking(txtAddClientID.getText(), txtAddBankName.getText(), txtAddAccountNumber.getText(), accountType, balance));
        txtBAddStatus.setText("The entry has been done.");
    }

    @FXML
    public void handleBtnBankingDeleteSearch(ActionEvent event) {
        String search = txtDeleteClientSearch.getText();
        txtDeleteClientResult.clear();;
        boolean found = false;
        for (Banking temp : myBanking) {
            if (temp.getClientID().equals(search) || temp.getAccountNumber().equals(search)) {
                found = true;
                txtDeleteClientResult.appendText("The following results matched the search." + "\n\n");
                txtDeleteClientResult.appendText(temp + "\n");
            }
        }
        if (!found) {
            txtBDeleteStatus.appendText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnBankingDelete(ActionEvent event) {
        try {
            String search = txtDeleteClientSearch.getText();
            txtBDeleteStatus.clear();;

            Iterator<Banking> iter = myBanking.iterator();
            while (iter.hasNext()) {
                Banking temp = iter.next();
                if (temp.getClientID().equals(search) || temp.getAccountNumber().equals(search)) {
                    iter.remove();
                }
            }
            txtBDeleteStatus.setText("The deletion is completed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleBtnBankingModifySearch(ActionEvent event) {
        String search = txtModifySearch.getText();
        txtBFindResult.clear();
        String result = "";
        boolean found = false;
        for (Banking temp : myBanking) {
            if (temp.getAccountNumber().equals(search)) {
                txtModifyClientID.setText(temp.getClientID());
                txtModifyBankName.setText(temp.getBankName());
                txtModifyAccountNumber.setText(temp.getAccountNumber());
                String type = temp.getAccountType();
                lvAccountTypeModify.getSelectionModel().select(type);
                txtModifyBalance.setText(Double.toString(temp.getBalance()));

            }
        }

    }

    @FXML
    public void handleBtnBankingModifySubmit(ActionEvent event) {
        String search = txtModifySearch.getText();
        if (txtModifyClientID.getText().isEmpty() || txtModifyBankName.getText().isEmpty() || txtModifyAccountNumber.getText().isEmpty() || txtModifyBalance.getText().isEmpty() || lvAccountTypeModify.getSelectionModel().getSelectedItem().isEmpty()) {

        } else {
            for (Banking temp : myBanking) {
                if (temp.getAccountNumber().equals(search)) {
                    temp.setClientID(txtModifyClientID.getText());
                    temp.setBankName(txtModifyBankName.getText());
                    temp.setAccountNumber(txtModifyAccountNumber.getText());
                    temp.setAccountType(lvAccountTypeModify.getSelectionModel().getSelectedItem());
                    temp.setBalance(Double.parseDouble(txtModifyBalance.getText()));
                }
            }
            txtBModifyStatus.setText("Modification is completed successfully.");
        }

    }

    @FXML
    public void writeBanking() {
        Path file = Paths.get("Banking.dat");
        try {
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

            StringBuilder wb = new StringBuilder();

            for (Banking temp : myBanking) {
                wb.setLength(0);
                wb.append(temp.getClientID()).append("\t");
                wb.append(temp.getBankName()).append("\t");
                wb.append(temp.getAccountNumber()).append("\t");
                wb.append(temp.getAccountType()).append("\t");
                wb.append(temp.getBalance()).append("\n");
                writer.write(wb.toString());
            }
            writer.close();
        } catch (Exception e) {
        System.out.println("The following error happened : " + e.getMessage());
        }
    }


    @FXML
    public void handleMnuMain(ActionEvent event) {
        writeBanking();
        myController.setScreen("Main");
    }

    @FXML
    public void handleMnuClients(ActionEvent event) {
        writeBanking();
        myController.setScreen("Clients");
    }

    public void handleMnuBanking(ActionEvent event) {
        writeBanking();
        myController.setScreen("Banking");
    }

    @FXML
    public void handleMnuBankingTransaction(ActionEvent event) {
        writeBanking();
        myController.setScreen("BankingTransaction");
    }

    @FXML
    public void handleMnuMortgage(ActionEvent event) {
        writeBanking();
        myController.setScreen("Mortgage");
    }

    @FXML
    public void handleMnuMortgageTransaction(ActionEvent event) {
        writeBanking();
        myController.setScreen("MortgageTransaction");
    }
    
     @FXML
    public void handleMnuPortfolio(ActionEvent event) {
        myController.setScreen("Portfolio");
    }

    @FXML
    public void handleMnuAbout(ActionEvent event) {
        writeBanking();
        myController.setScreen("About");
    }
    

    public void handleBtnExit(ActionEvent event) {
        writeBanking();
        myController.setScreen("Main");
    }

}
