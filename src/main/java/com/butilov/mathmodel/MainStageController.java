package com.butilov.mathmodel;

import com.butilov.mathmodel.localization.I18N;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
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

    @Autowired
    void setI18N(I18N aI18N) {
        mI18N = aI18N;
    }

    @FXML
    public void initialize() {
        initTabPane();
        initText();
    }

    private void initText() {
        modelTab.textProperty().bind(mI18N.createStringBinding("tab.model"));
        theoryTab.textProperty().bind(mI18N.createStringBinding("tab.theory"));
        aboutTab.textProperty().bind(mI18N.createStringBinding("tab.about"));
    }

    private void initTabPane() {
        modelAnchorPane.getChildren().add(mModelPane);
        theoryAnchorPane.getChildren().add(mTheoryPane);
        aboutAnchorPane.getChildren().add(mAboutPane);
    }

    public void onApplicationExit() {
        // todo если понадобится
    }

    @FXML
    private AnchorPane modelAnchorPane;
    @FXML
    private AnchorPane theoryAnchorPane;
    @FXML
    private AnchorPane aboutAnchorPane;

    @FXML
    private Tab modelTab;
    @FXML
    private Tab theoryTab;
    @FXML
    private Tab aboutTab;

    private Node mModelPane;
    private Node mTheoryPane;
    private Node mAboutPane;
    private I18N mI18N;
}
