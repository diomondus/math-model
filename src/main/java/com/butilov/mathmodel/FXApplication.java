package com.butilov.mathmodel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    }

    @Override
    public void stop()
            throws Exception {
        mMainStageController.onApplicationExit();
    }
}

