package edu.augustana;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ScenarioBuilder extends Application {

    private static Scene scene;

    @FXML private TextField NameBox;
    @FXML private TextField CharacterBox;
    @FXML private TextField GoalBox;
    @FXML private TextField ObstacleBox;
    @FXML private Button SaveButton;

    // sets builder screen
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("scenarioBuilder"), 640, 680);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void getData(){
        if (NameBox.getText().equals("") || CharacterBox.getText().equals("") || GoalBox.getText().equals("") || ObstacleBox.getText().equals("")){
            showAlert("Please fill out ALL fields!");
        } else {
            String name = NameBox.getText();
            String profession = CharacterBox.getText();
            String greeting = GoalBox.getText();
            String response = ObstacleBox.getText();
            System.out.println(name + " " + profession + " " + greeting + " " + response);
        }

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void showAlert(String message) {
        // Create an Alert of type ERROR
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("WARNING!");
        alert.setHeaderText("Incomplete Information");
        alert.setContentText(message);

        // Show the alert and wait for a response
        alert.showAndWait();
    }

    public static void main(String[] args) {launch();}

}

