package com.butilov.mathmodel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Butilov
 * on 10.01.18.
 */
@Component
public class MainStageController {
    @Autowired
    public MainStageController(FXTestBean aFXTestBean) {
        mFXTestBean = aFXTestBean;
    }

    @FXML
    public void initialize() {
        testButton.setOnAction(e -> {
            eventTextField.appendText("hello button pressed \n");
        });
    }

    @FXML
    private Button testButton;
    @FXML
    private TextArea eventTextField;

    private FXTestBean mFXTestBean;

    public void onApplicationExit() {
        // todo
    }
}
