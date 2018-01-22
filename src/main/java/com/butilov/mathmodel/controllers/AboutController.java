package com.butilov.mathmodel.controllers;

import com.butilov.mathmodel.localization.I18N;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by Dmitry Butilov
 * on 19.01.18.
 */
@Component
public class AboutController {
    @Autowired
    void setI18N(I18N aI18N) {
        mI18N = aI18N;
    }

    @FXML
    public void initialize() {
        aboutTheoryHeader.textProperty().bind(mI18N.createStringBinding("about.header.theory"));
        aboutModelHeader.textProperty().bind(mI18N.createStringBinding("about.header.model"));
        aboutTheoryTextArea.textProperty().bind(mI18N.createStringBinding("about.textarea.theory"));
        aboutModelTextArea.textProperty().bind(mI18N.createStringBinding("about.textarea.model"));
    }

    @FXML
    private Label aboutTheoryHeader;
    @FXML
    private Label aboutModelHeader;
    @FXML
    private JFXTextArea aboutTheoryTextArea;
    @FXML
    private JFXTextArea aboutModelTextArea;

    private I18N mI18N;
}
