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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public void saveData(){
        if (NameBox.getText().equals("") || CharacterBox.getText().equals("") || GoalBox.getText().equals("") || ObstacleBox.getText().equals("")){
            showAlert("Please fill out ALL fields!", AlertType.ERROR);
        } else {
            String name = NameBox.getText();
            String character = CharacterBox.getText();
            String goal = GoalBox.getText();
            String obstacles = ObstacleBox.getText();

            try {
                writeDataToFile(name, character, goal, obstacles);
            } catch (IOException e) {
                System.out.println("The following error has occurred: " + e.toString());
            }

            showAlert("Your data has been saved.", AlertType.INFORMATION);
        }

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void showAlert(String message, AlertType Alert) {

        Alert alert = new Alert(Alert);
        alert.setTitle("ATTENTION!");
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void writeDataToFile(String name, String character, String goal, String obstacles) throws IOException {

            Map<String, Object> data = new HashMap<>();
            data.put("name", name);
            data.put("character", character);
            data.put("goal", goal);
            data.put("obstacles", obstacles);

            //TODO tamper with conversion to make it work. or maybe it already works.
            //Gson data_gson = new Gson();
            //String data_json = data_gson.toJson(data);

            File scenario = new File("H:\\git\\IbisRepo\\scenarioData.json");
            FileWriter author = new FileWriter(scenario);
            author.write(data.toString());
            author.close();

    }

    public static void main(String[] args) {launch();}

}

