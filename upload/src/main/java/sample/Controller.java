package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Initializable {
    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label nameLabel;

    private StringProperty fileName = new SimpleStringProperty("Plik");

    private final FileChooser fileChooser = new FileChooser();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private File file = null;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        fileChooser.setTitle("Wybierz plik");
        nameLabel.textProperty().bind(fileName);
    }


    @FXML
    private void openFile(){
        file = fileChooser.showOpenDialog(null);
        if (file != null) fileName.set(file.getName());
    }

    @FXML
    private void sendFile(){
        if (file != null) {
            System.out.println("Sending");
            Task<Void> sendFileTask = new SendFileTask(file); //klasa zadania
            statusLabel.textProperty().bind(sendFileTask.messageProperty());
            progressBar.progressProperty().bind(sendFileTask.progressProperty());
            executor.submit(sendFileTask); //uruchomienie zadania w tle
        }
    }



}
