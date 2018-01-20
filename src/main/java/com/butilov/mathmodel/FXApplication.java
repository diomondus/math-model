package com.butilov.mathmodel;

import com.butilov.mathmodel.localization.I18N;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Butilov
 * on 10.01.18.
 */
@Component
public class FXApplication
        extends Application {

    private ConfigurableApplicationContext context;
    @Autowired
    private I18N mI18N;
    @Autowired
    private Scene mainScene;
    @Autowired
    private MainStageController mMainStageController;

    @Override
    public void init()
            throws Exception {
        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void start(Stage primaryStage)
            throws Exception {
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.titleProperty().bind(mI18N.createStringBinding("window.title"));
    }

    @Override
    public void stop()
            throws Exception {
        mMainStageController.onApplicationExit();
    }
}

