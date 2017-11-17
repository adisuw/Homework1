package back.clientPack;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HangmanClientController implements Initializable {

    public ConnectionHandler hang;

    @FXML
    private Label label;
    @FXML
    private TextField txtStatus;
    @FXML
    private TextField txtScore;
    @FXML
    private TextField txtTry;
    @FXML
    private TextField txtInput;
    @FXML
    private TextField host;
    @FXML
    private TextField port;

    @FXML
    private void connectToServer(ActionEvent event) throws IOException {
        new ConnectButtunHandler().start();

    }

    @FXML
    private void disconnect(ActionEvent event) {
        //hang.closeConnection();
        disable();
    }

    @FXML
    private void sendWord(ActionEvent e) {
        new GameResultService().start();
    }

    private void disable() {
        label.setText("Cleaning up...");
        txtStatus.setText("Diconnected");
        txtStatus.setEditable(false);
        txtScore.setText("");
        txtScore.setEditable(false);
        txtTry.setEditable(false);
        //txtInput.setEditable(false);
    }

    private void setUpFields() {
        txtStatus.setText("Connected to Server");
        txtStatus.setEditable(false);
        txtScore.setText("0");
        txtScore.setEditable(false);
        txtTry.setEditable(false);
        //label.setText("Now Try to guess the word...");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private class ConnectButtunHandler extends Service<ConnectionHandler> {

        private ConnectButtunHandler() {
            setOnSucceeded((WorkerStateEvent event) -> {
                hang = getValue();
               setUpFields();
                //label.setText(hang.acceptFromServer());
            });

            setOnFailed((WorkerStateEvent e) -> {
                System.out.println("connection failed...");
            });
        }

        @Override
        protected Task<ConnectionHandler> createTask() {
            return new Task<ConnectionHandler>() {
                @Override
                protected ConnectionHandler call() throws Exception {
            //Integer.parseInt(port.getText())
                    return new ConnectionHandler("localhost", 4444);
                }

            };
        }
    }

    private class GameResultService extends Service<String> {

        private GameResultService() {
            setOnSucceeded((WorkerStateEvent e) -> {
                System.out.print("you are here..");
                label.setText((String) e.getSource().getValue());
                
                    
               
            });
            setOnFailed((WorkerStateEvent e) -> {
                System.out.println("failed...");
            });
        }
        
      

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws IOException, InterruptedException {
                    System.out.println("Calling process");
                    hang.process(txtInput.getText());
                    return hang.acceptFromServer();
                }
            };
        }
    }
}
