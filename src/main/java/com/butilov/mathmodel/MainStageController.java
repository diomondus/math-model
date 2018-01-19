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
    @Qualifier("modelPane")
    void setModelPane(Node modelPane) {
        mModelPane = modelPane;
    }

    @Autowired
    @Qualifier("theoryPane")
    void setTheoryPane(Node theoryPane) {
        mTheoryPane = theoryPane;
    }

    @Autowired
    @Qualifier("aboutPane")
    void setAboutPane(Node aboutPane) {
        mAboutPane = aboutPane;
    }

    @FXML
    public void initialize() {
        modelTab.getChildren().add(mModelPane);
        theoryTab.getChildren().add(mTheoryPane);
        aboutTab.getChildren().add(mAboutPane);
    }

    public void onApplicationExit() {
        // todo если понадобится
    }

    @FXML
    private AnchorPane modelTab;
    @FXML
    private AnchorPane theoryTab;
    @FXML
    private AnchorPane aboutTab;

    private Node mModelPane;
    private Node mTheoryPane;
    private Node mAboutPane;
}
