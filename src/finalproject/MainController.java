package finalproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController implements Initializable, ControlledScreen {

    ScreensController myController;
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    
    
    @FXML
    private Label label;

    

    @FXML
    private void handleButtonAction(ActionEvent event) {
 
    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleMnuClient(ActionEvent event) {
        myController.setScreen("Clients");
    }

   

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    public void handleMnuMain(ActionEvent event) {
        myController.setScreen("Main");
    }
     @FXML
    public void handleMnuClients(ActionEvent event) {
        myController.setScreen("Clients");
    }
     @FXML
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

}
