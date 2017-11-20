package finalproject;

import static finalproject.EncryptFunction.cipher;
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
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ClientsController implements Initializable, ControlledScreen {

    static Cipher cipher;

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //clearClients();
      
        try {
            Test();
        } catch (Exception ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
         loadClients();
    }

    List<Clients> myClient = new ArrayList<>();

    //Client Screen , Search Tab
    @FXML
    private TextField txtFindClientSearch;
    @FXML
    private TextArea txtFindClientResult;

    //Client Screen , Add Tab
    @FXML
    private TextField txtAddClientID;
    @FXML
    private TextField txtAddClientLastName;
    @FXML
    private TextField txtAddClientFirstName;
    @FXML
    private TextField txtAddClientAddress;
    @FXML
    private DatePicker txtAddClientDOB;

    //Client Screen , Modify Tab
    @FXML
    private TextField txtModifyClientSearch;
    @FXML
    private TextField txtModifyClientID;
    @FXML
    private TextField txtModifyClientFirstName;
    @FXML
    private TextField txtModifyClientLastName;
    @FXML
    private TextField txtModifyClientAddress;
    @FXML
    private DatePicker txtModifyClientDOB;

    //Client Screen , Delete Tab
    @FXML
    private TextField txtDeleteClientSearch;
    @FXML
    private TextArea txtDeleteClientResult;

    // status fields
    @FXML
    private TextArea txtCAddStatus;
    @FXML
    private TextArea txtCModifyStatus;
    @FXML
    private TextArea txtCDeleteStatus;

    @FXML
    public void clearClients() {
        txtFindClientSearch.clear();
        txtFindClientResult.clear();
        txtAddClientID.clear();
        txtAddClientLastName.clear();
        txtAddClientFirstName.clear();
        txtAddClientAddress.clear();
        txtModifyClientSearch.clear();
        txtModifyClientID.clear();
        txtModifyClientFirstName.clear();
        txtModifyClientLastName.clear();
        txtModifyClientAddress.clear();
        txtDeleteClientSearch.clear();
        txtDeleteClientResult.clear();
        txtModifyClientDOB.getEditor().clear();
        txtAddClientDOB.setValue(null);
        txtCDeleteStatus.clear();
        txtCModifyStatus.clear();
        txtCAddStatus.clear();
    }

    public void Test() throws Exception{
        String ecrypted = encryptThis("Hello Hello");
        System.out.println(" ecrypted : " + ecrypted);
        String decrypted = decryptThis(ecrypted);
        System.out.println(" decrypted : " + decrypted);
        String ecrypted1 = encryptThis(decrypted);
        System.out.println(" ecrypted again : " + ecrypted1);
        String decrypted1 = decryptThis(ecrypted1);
        System.out.println(" decrypted again : " + decrypted1);
    }
    
    
    @FXML
    public void loadClients() {

        Path file = Paths.get("Clients.dat");
        InputStream input = null;
        String s = null;
        String inDate;
        StringBuilder inString = new StringBuilder();
        try {
            input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            s = reader.readLine();
            while (s != null) {
            inString.append(s);
            s = reader.readLine();
   
            }
            String decrypted = decryptThis(inString.toString());
            System.out.println("decrypted : " + decrypted);
             /*
            while (s != null) {
                inDate = null;
                String[] clientImport = s.split("\t");
                String finalDate = formatDate(clientImport[4]);
//                inDate = clientImport[4];
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//                LocalDate date = LocalDate.parse(inDate, formatter);
//                System.out.println("date" + date.toString());

                myClient.add(new Clients(clientImport[0], clientImport[1], clientImport[2], clientImport[3],finalDate ));
                s = reader.readLine();
          
            
            */
            input.close();
            } catch (Exception e) {
            System.out.println(" The following error happened : " + e.getLocalizedMessage());
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
    public void handleBtnClientSearch(ActionEvent event) {
        String search = txtFindClientSearch.getText();
        txtFindClientResult.clear();
        boolean found = false;
        for (Clients temp : myClient) {
            if (temp.getID().equals(search) || temp.getFirstName().equals(search) || temp.getLastName().equals(search)) {
                found = true;
                //  txtFindClientResult.appendText(temp);
                txtFindClientResult.setText(temp.toString());
            }
        }
        if (!found) {
            txtFindClientResult.setText("No result found for this search.");

        }
    }

    @FXML
    public void handleBtnClientSearchClear(ActionEvent event) {
        txtFindClientSearch.clear();
        txtFindClientResult.clear();
    }

    @FXML
    public void handleBtnClientAdd(ActionEvent event) {
        if (txtAddClientID.getText().isEmpty() || txtAddClientLastName.getText().isEmpty() || txtAddClientFirstName.getText().isEmpty() || txtAddClientAddress.getText().isEmpty()) {
            txtCAddStatus.setText("Invalid Data Input. Client has not been entered. Please try again. ");

        } else {
            String date = txtAddClientDOB.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            System.out.println(" date " + date);
            myClient.add(new Clients(txtAddClientID.getText(), txtAddClientLastName.getText(), txtAddClientFirstName.getText(), txtAddClientAddress.getText(), date));
            txtCAddStatus.setText("Client entry has been completed successfully.");
        }
    }

    @FXML
    public void handleBtnClientDeleteSearch(ActionEvent event) {
        String search = txtDeleteClientSearch.getText();
        txtDeleteClientResult.clear();
        txtCDeleteStatus.clear();
        boolean found = false;
        for (Clients temp : myClient) {
            if (temp.getID().equals(search)) {
                found = true;
                txtCDeleteStatus.appendText("The results matching search are shown. Press delete to delete the record." + "\n\n");
                txtDeleteClientResult.appendText(temp.toString() + "\n");
            }
        }
        if (!found) {
            txtCDeleteStatus.appendText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnClientDeleteSubmit(ActionEvent event) {
        String search = txtDeleteClientSearch.getText();
        txtCDeleteStatus.clear();

        Iterator<Clients> iter = myClient.iterator();
        while (iter.hasNext()) {
            Clients temp = iter.next();
            if (temp.getID().equals(search)) {
                iter.remove();
            }
        }
        txtCDeleteStatus.setText("The deletion is completed successfully.");
    }

    @FXML
    public void handleBtnClientModifySearch(ActionEvent event) {

        String search = txtModifyClientSearch.getText();
        boolean found = false;
        for (Clients temp : myClient) {
            if (temp.getID().equals(search)) {
                found = true;
                txtModifyClientID.setText(temp.getID());
                txtModifyClientFirstName.setText(temp.getFirstName());
                txtModifyClientLastName.setText(temp.getLastName());
                txtModifyClientAddress.setText(temp.getAdress());
                String inDate = temp.getDOB();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate date = LocalDate.parse(inDate, formatter);
                txtModifyClientDOB.setValue(date);
                try {
                } catch (Exception ex) {
                    System.out.println("This error happened : " + ex.getMessage());
                }
            }
        }
        if (!found) {
            txtCModifyStatus.setText("No result found for this search.");
        }
    }

    @FXML
    public void handleBtnClientModifySubmit(ActionEvent event) {
        String search = txtModifyClientSearch.getText();
        if (txtModifyClientID.getText().isEmpty() || txtModifyClientFirstName.getText().isEmpty() || txtModifyClientLastName.getText().isEmpty() || txtModifyClientAddress.getText().isEmpty()) {
            txtCModifyStatus.setText("Invalid Input. Please try again. ");
        } else {
            for (Clients temp : myClient) {
                if (temp.getID().equals(search)) {
                    temp.setID(txtModifyClientID.getText());
                    temp.setFirstName(txtModifyClientFirstName.getText());
                    temp.setLastName(txtModifyClientLastName.getText());
                    temp.setAdress(txtModifyClientAddress.getText());
                    String date = txtModifyClientDOB.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")); // .toString(); // 
                    temp.setDOB(date);
                }
                txtCModifyStatus.setText("Modification is completed successfully.");
            }
        }
    }

    public void writeClients() {
        try {
            Path newFile = Paths.get("Clients.dat");

            OutputStream output = new BufferedOutputStream(Files.newOutputStream(newFile, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            StringBuilder wc = new StringBuilder();
            for (Clients temp : myClient) {
                wc.setLength(0);
                wc.append(temp.getID()).append("\t");
                wc.append(temp.getLastName()).append("\t");
                wc.append(temp.getFirstName()).append("\t");
                wc.append(temp.getAdress()).append("\t");
                wc.append(temp.getDOB()).append("\n");
                writer.write(encryptThis(wc.toString()));
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("This error occured : " + e.getMessage());
        }
    }

    public void handleBtnExit(ActionEvent event) {
        writeClients();
        myController.setScreen("Main");
    }

    @FXML
    public void handleMnuMain(ActionEvent event) {
        writeClients();
        myController.setScreen("Main");
    }

    public void handleMnuClients(ActionEvent event) {
        writeClients();
        myController.setScreen("Clients");
    }

    @FXML
    public void handleMnuBanking(ActionEvent event) {
        writeClients();
        myController.setScreen("Banking");
    }

    @FXML
    public void handleMnuBankingTransaction(ActionEvent event) {
        writeClients();
        myController.setScreen("BankingTransaction");
    }

    @FXML
    public void handleMnuMortgage(ActionEvent event) {
        writeClients();
        myController.setScreen("Mortgage");
    }

    @FXML
    public void handleMnuMortgageTransaction(ActionEvent event) {
        writeClients();
        myController.setScreen("MortgageTransaction");
    }

    @FXML
    public void handleMnuPortfolio(ActionEvent event) {
        writeClients();
        myController.setScreen("Portfolio");
    }

    @FXML
    public void handleMnuAbout(ActionEvent event) {
        writeClients();
        myController.setScreen("About");
    }

    public static String encryptThis(String text)
            throws Exception {
       
    
     //   KeyGenerator keyMaker = KeyGenerator.getInstance("AES");
    //    keyMaker.init(128); //
        String keyy = "A9z7fj48JDl3js1W";
        byte keyByte[] = keyy.getBytes();
        SecretKey key = new SecretKeySpec(keyByte, "AES");
       
        cipher = Cipher.getInstance("AES");

        byte[] textBytes = text.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(textBytes);
        Base64.Encoder byteEncoder = Base64.getEncoder();
        String encryptedText = byteEncoder.encodeToString(encryptedBytes);
        return encryptedText;
    }

    public static String decryptThis(String text)
            throws Exception {
      //  KeyGenerator keyMaker = KeyGenerator.getInstance("AES");
      //  keyMaker.init(128); //
        String keyy = "A9z7fj48JDl3js1W";
        byte keyByte[] = keyy.getBytes();
        SecretKey key = new SecretKeySpec(keyByte, "AES");
        cipher = Cipher.getInstance("AES");
        
        Base64.Decoder byteDecoder = Base64.getDecoder();
        byte[] encryptedBytes = byteDecoder.decode(text);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedText = new String(decryptedBytes);
        return decryptedText;
    }
}
