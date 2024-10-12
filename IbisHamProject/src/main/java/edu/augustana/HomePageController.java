package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class HomePageController {

    //button action method to switch to practice mode: one of 3 planned possible choices currently
    @FXML
    private void switchToPracticeMode() throws IOException {
        App.setRoot("practiceMode");
    }

    //eventually add the other two modes: Online and AI


}
