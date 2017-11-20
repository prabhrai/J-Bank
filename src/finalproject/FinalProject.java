package finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FinalProject extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen("Main", "Main.fxml");
        mainContainer.loadScreen("Clients", "Clients.fxml");
        mainContainer.loadScreen("Banking", "Banking.fxml");
        mainContainer.loadScreen("BankingTransaction", "BankingTransaction.fxml");
        mainContainer.loadScreen("Mortgage", "Mortgage.fxml");
        mainContainer.loadScreen("MortgageTransaction", "MortgageTransaction.fxml");
        mainContainer.loadScreen("Portfolio", "Portfolio.fxml");
        mainContainer.loadScreen("About", "About.fxml");

        mainContainer.setScreen("Main");
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

 
    public static void main(String[] args) {
        launch(args);
    }

}
