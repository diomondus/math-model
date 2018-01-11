package com.butilov.mathmodel;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("modelPane")
    void setNodePane(Node modelPane) {
        mModelPane = modelPane;
    }

    @FXML
    public void initialize() {
        modelTab.getChildren().add(mModelPane);
    }

    public void onApplicationExit() {
        // todo если понадобится
    }

    @FXML
    private AnchorPane modelTab;

    private Node mModelPane;

    private FXTestBean mFXTestBean;
}
