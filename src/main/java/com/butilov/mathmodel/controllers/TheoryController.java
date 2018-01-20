package com.butilov.mathmodel.controllers;

import com.butilov.mathmodel.localization.I18N;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Butilov
 * on 15.01.18.
 */
@Component
public class TheoryController {

    @Autowired
    public TheoryController(I18N aI18N) {
        mI18N = aI18N;
    }

    @FXML
    public void initialize() {
        theoryHeader.textProperty().bind(mI18N.createStringBinding("theory.header"));
    }

    @FXML
    private Label theoryHeader;

    private I18N mI18N;
}
