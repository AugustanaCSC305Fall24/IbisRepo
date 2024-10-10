package edu.augustana;


import javafx.fxml.FXML;
import javafx.scene.control.Slider;

import java.io.IOException;

public class AccessibilityController {

    @FXML private Slider qualitySlider;
    @FXML private Slider amountSlider;
    @FXML private Slider speedSlider;

    private int getSliderVal(Slider slider){
        return (int) slider.getValue();
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("");
    }

}
