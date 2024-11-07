package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

import java.io.IOException;

public class ScenarioBuilder extends Application {

    private static Scene scene;

    @FXML private TextField NameBox;
    @FXML private TextField ProfessionBox;

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

    public static void main(String[] args) {
        launch();
    }

}

