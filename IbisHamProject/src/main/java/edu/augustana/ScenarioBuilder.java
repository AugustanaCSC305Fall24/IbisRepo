package edu.augustana;

import javafx.application.Application;
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

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    //this block of code will throw a NullPointerException when it tries to get the text from the boxes
    @FXML
    public void getData(){
        if (NameBox.getText() != null && CharacterBox.getText() != null && GoalBox.getText() != null && ObstacleBox.getText() != null){
            String name = NameBox.getText();
            String profession = CharacterBox.getText();
            String greeting = GoalBox.getText();
            String response = ObstacleBox.getText();
            System.out.println(name + " " + profession + " " + greeting + " " + response);
        } else {
            System.out.println("Please fill out ALL fields.");
        }

    }

    public static void main(String[] args) {launch();}

}

