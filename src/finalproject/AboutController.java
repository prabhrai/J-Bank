
package finalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class AboutController implements Initializable ,ControlledScreen{

    ScreensController myController;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    
    @FXML
    public void handlebtnReturn(ActionEvent event) {
        myController.setScreen("Main");
    }
    
    @FXML
    public void handleMnuMain(ActionEvent event) {
        myController.setScreen("Main");
    }

    @FXML
    public void handleMnuClients(ActionEvent event) {
        myController.setScreen("Clients");
    }

    public void handleMnuBanking(ActionEvent event) {
        myController.setScreen("Banking");
    }

    @FXML
    public void handleMnuBankingTransaction(ActionEvent event) {
        myController.setScreen("BankingTransaction");
    }

    @FXML
    public void handleMnuMortgage(ActionEvent event) {
        myController.setScreen("Mortgage");
    }

    @FXML
    public void handleMnuMortgageTransaction(ActionEvent event) {
        myController.setScreen("MortgageTransaction");
    }
    
     @FXML
    public void handleMnuPortfolio(ActionEvent event) {
        myController.setScreen("Portfolio");
    }

    @FXML
    public void handleMnuAbout(ActionEvent event) {
        myController.setScreen("About");
    }
    

    public void handleBtnExit(ActionEvent event) {;
        myController.setScreen("Main");
    }
}
