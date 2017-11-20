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
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PortfolioController implements Initializable, ControlledScreen {

    ScreensController myController;
    @FXML
    private TextArea txtPFindResult;
    @FXML
    private DatePicker txtPAddPurchasedDate;
    @FXML
    private TextArea txtPAddStatus;
    @FXML
    private TextField txtPAddSharesPurchased;
    @FXML
    private TextField txtPAddPricePerShare;
    @FXML
    private TextField txtPAddStockSymbol;
    @FXML
    private TextField txtPAddClientID;
    @FXML
    private TextField txtPAddShareLastPrice;
    @FXML
    private TextArea txtMTModifyStatus;
    @FXML
    private TextField txtPModifySearchClientID;
    @FXML
    private DatePicker txtPModifyPurchasedDate;
    @FXML
    private TextField txtPModifySharesPurchased;
    @FXML
    private TextField txtPModifyPricePerShare;
    @FXML
    private TextField txtPModifyStockSymbol;
    @FXML
    private TextField txtPModifyClientID;
    @FXML
    private TextField txtPModifyShareLastPrice;
    @FXML
    private TextField txtPModifySearchStockSymbol;
    @FXML
    private TextField txtPModifySearchSharesPurchased;
    @FXML
    private TextArea txtPDeleteResult;
    @FXML
    private TextArea txtPDeleteStatus;
    @FXML
    private TextField txtPDeleteSearchClientID;
    @FXML
    private TextField txtPDeleteSearchStockSymbol1;
    @FXML
    private TextField txtPDeleteSearchSharesPurchased;
    @FXML
    private TextField txtPSearch;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadPortfolio();
    }

    public void exitScreen() {
        clearPortfolio();
        writePortfolio();
    }

    @FXML
    public void clearPortfolio() {
        txtPSearch.clear();
        txtPFindResult.clear();
        txtPAddPurchasedDate.setValue(null);
        txtPAddStatus.clear();
        txtPAddSharesPurchased.clear();
        txtPAddPricePerShare.clear();
        txtPAddStockSymbol.clear();
        txtPAddClientID.clear();
        txtPAddShareLastPrice.clear();
        txtMTModifyStatus.clear();
        txtPModifySearchClientID.clear();
        txtPModifyPurchasedDate.setValue(null);
        txtPModifySharesPurchased.clear();
        txtPModifyPricePerShare.clear();
        txtPModifyStockSymbol.clear();
        txtPModifyClientID.clear();
        txtPModifyShareLastPrice.clear();
        txtPModifySearchStockSymbol.clear();
        txtPModifySearchSharesPurchased.clear();
        txtPDeleteResult.clear();
        txtPDeleteStatus.clear();
        txtPDeleteSearchClientID.clear();
        txtPDeleteSearchStockSymbol1.clear();
        txtPDeleteSearchSharesPurchased.clear();
    }

    List<Portfolio> myPortfolio = new ArrayList<>();

    public void loadPortfolio() {
        Path file = Paths.get("Portfolio.dat");
        InputStream input = null;
        String s = null;
        try {
            input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            s = reader.readLine();
            int sharesPurchased = 0;
            Double tempPPS = 0.00, tempSLP = 0.00;
            while (s != null) {
                String[] pImport = s.split("\t");
                String finalDate = formatDate(pImport[3]);
                sharesPurchased = Integer.parseInt(pImport[2]);
                tempPPS = Double.parseDouble(pImport[4]);
                tempSLP = Double.parseDouble(pImport[5]);
                myPortfolio.add(new Portfolio(pImport[0], pImport[1], sharesPurchased, finalDate, tempPPS, tempSLP));
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
    public void handleBtnPortfolioSearch(ActionEvent event) {
        String search = txtPSearch.getText();
        txtPFindResult.clear();
        boolean found = false;
        for (Portfolio temp : myPortfolio) {
            if (temp.getClientID().equals(search)) {
                found = true;
                txtPFindResult.appendText(temp.toString() + "\n");
            }
        }
        if (!found) {
            txtPFindResult.setText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnPortfolioAdd(ActionEvent event) {
        try {
            if (txtPAddClientID.getText().isEmpty() || txtPAddStockSymbol.getText().isEmpty()
                    || txtPAddPricePerShare.getText().isEmpty() || txtPAddShareLastPrice.getText().isEmpty()
                    || txtPAddSharesPurchased.getText().isEmpty()) {
                txtPAddStatus.setText("Invalid Input. The entry has not been completed. Please try again.");
            } else {
                int SharesPurchased = Integer.parseInt(txtPAddSharesPurchased.getText());
                double PricePerShare = Double.parseDouble(txtPAddPricePerShare.getText());
                double ShareLastPrice = Double.parseDouble(txtPAddShareLastPrice.getText());
                String PurchasedDate = txtPAddPurchasedDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                myPortfolio.add(new Portfolio(txtPAddClientID.getText(), txtPAddStockSymbol.getText(), SharesPurchased, PurchasedDate, PricePerShare, ShareLastPrice));
                txtPAddStatus.setText("The entry has been done.");
            }

        } catch (Exception e) {
            System.out.println("The following error happened : " + e.getLocalizedMessage());
        }
    }

    @FXML
    public void handleBtnPortfolioDeleteSearch(ActionEvent event) {
        String searchID = txtPDeleteSearchClientID.getText();
        String searchSS = txtPDeleteSearchStockSymbol1.getText();
        int searchSP = Integer.parseInt(txtPDeleteSearchSharesPurchased.getText());
        txtPDeleteResult.clear();
        txtPDeleteStatus.clear();
        boolean found = false;
        for (Portfolio temp : myPortfolio) {
            if (temp.getClientID().equals(searchID) && temp.getStockSymbol().equals(searchSS) && temp.getSharesPurchased() == searchSP) {
                found = true;
                txtPDeleteResult.appendText("The results matching the search are shown." + "\n\n");
                txtPDeleteResult.appendText(temp + "\n");
            }
        }
        if (!found) {
            txtPDeleteStatus.appendText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnPortfolioDeleteSubmit(ActionEvent event) {
        try {
            String deleteID = txtPDeleteSearchClientID.getText();
            String deleteSS = txtPDeleteSearchStockSymbol1.getText();
            int deleteSP = Integer.parseInt(txtPDeleteSearchSharesPurchased.getText());
            txtPDeleteResult.clear();
            txtPDeleteStatus.clear();
            Iterator<Portfolio> iter = myPortfolio.iterator();
            while (iter.hasNext()) {
                Portfolio temp = iter.next();
                if (temp.getClientID().equals(deleteID) && temp.getStockSymbol().equals(deleteSS) && temp.getSharesPurchased() == deleteSP) {
                    iter.remove();
                }
            }
            txtPDeleteStatus.setText("The deletion is completed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleBtnPortfolioModifySearch(ActionEvent event) {
        if (txtPModifySearchClientID.getText().isEmpty() || txtPModifySearchStockSymbol.getText().isEmpty() || txtPModifySearchSharesPurchased.getText().isEmpty()) {
            txtMTModifyStatus.setText("No result found for this search.");
        }
        else {
        String modifyID = txtPModifySearchClientID.getText();
        String modifySS = txtPModifySearchStockSymbol.getText();
        int modifySP = Integer.parseInt(txtPModifySearchSharesPurchased.getText());
        txtMTModifyStatus.clear();
        boolean found = false;
        for (Portfolio temp : myPortfolio) {
            if (temp.getClientID().equals(modifyID) && temp.getStockSymbol().equals(modifySS) && temp.getSharesPurchased() == modifySP) {
                found = true;
                txtPModifyClientID.setText(temp.getClientID());
                txtPModifyStockSymbol.setText(temp.getStockSymbol());
                int SharesPurchased = temp.getSharesPurchased();
                txtPModifySharesPurchased.setText(Integer.toString(SharesPurchased));
                txtPModifyPricePerShare .setText(temp.getPricePerShare().toString());
                txtPModifyShareLastPrice.setText(temp.getShareLastPrice().toString());
                String inDate = temp.getPurchasedDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate tempDate = LocalDate.parse(inDate, formatter);
                txtPModifyPurchasedDate.setValue(tempDate);
            }
        }
        if (!found) {
            txtMTModifyStatus.appendText("No result found for this search.");
        }
        }
    }

    
    public void handleBtnPortfolioModifySubmit(ActionEvent event) {
        if (txtPModifySearchClientID.getText().isEmpty() || txtPModifySearchStockSymbol.getText().isEmpty() || txtPModifySearchSharesPurchased.getText().isEmpty()) {
            txtMTModifyStatus.setText("No result found for this search. Modification is unsuccessful");
        }
        else {
        String modifyID = txtPModifySearchClientID.getText();
        String modifySS = txtPModifySearchStockSymbol.getText();
        int modifySP = Integer.parseInt(txtPModifySearchSharesPurchased.getText());
        txtMTModifyStatus.clear();
        for (Portfolio temp : myPortfolio) {
            if (temp.getClientID().equals(modifyID) && temp.getStockSymbol().equals(modifySS) && temp.getSharesPurchased() == modifySP) {
                temp.setClientID(txtPModifyClientID.getText());
                temp.setStockSymbol(txtPModifyStockSymbol.getText());
                String tempDate = txtPModifyPurchasedDate.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                temp.setPurchasedDate(tempDate);
                int setSharesPurchased = Integer.parseInt(txtPModifySharesPurchased.getText());
                temp.setSharesPurchased(setSharesPurchased);
                Double PricePerShare = Double.parseDouble(txtPModifyPricePerShare.getText());
                temp.setPricePerShare(PricePerShare);
                Double ShareLastPrice = Double.parseDouble(txtPModifyShareLastPrice.getText());
                temp.setShareLastPrice(ShareLastPrice);
                  
                }
            }
            txtMTModifyStatus.setText("Modification is completed successfully.");
        }
        }
    
    public void writePortfolio() {
        Path file = Paths.get("Portfolio.dat");
        try {
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            StringBuilder wb = new StringBuilder();
            for (Portfolio temp : myPortfolio) {
                wb.setLength(0);
                wb.append(temp.getClientID()).append("\t");
                wb.append(temp.getStockSymbol()).append("\t");
                wb.append(temp.getSharesPurchased()).append("\t");
                wb.append(temp.getPurchasedDate()).append("\t");
                wb.append(temp.getPricePerShare()).append("\t");
                wb.append(temp.getShareLastPrice()).append("\n");
                writer.write(wb.toString());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("The following error happened : " + e.getMessage());
        }
    }

    public void handlebtnReturn(ActionEvent event) {
        exitScreen();
        myController.setScreen("Main");
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

    @FXML
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
