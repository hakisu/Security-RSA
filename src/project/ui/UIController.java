package project.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import project.rsa.RSA;
import project.utilities.URLDataExtractor;

public class UIController {

    @FXML
    private TextArea messageTextArea;
    @FXML
    private TextField fileAddressTextField;

    public UIController() {
    }

    @FXML
    private void calculateRSA() {
        String urlAddressString = fileAddressTextField.getText();
        urlAddressString = urlAddressString.trim();
        URLDataExtractor urlDataExtractor = new URLDataExtractor(urlAddressString);
        RSA rsa = new RSA(urlDataExtractor.getPublicKey());

        messageTextArea.setText(rsa.decodeEncryptedValues(urlDataExtractor.getCodedValues()));
    }
}