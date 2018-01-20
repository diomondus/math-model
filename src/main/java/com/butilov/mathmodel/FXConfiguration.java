package com.butilov.mathmodel;

import com.butilov.mathmodel.controllers.MathModelController;
import com.butilov.mathmodel.controllers.TheoryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by Dmitry Butilov
 * on 10.01.18.
 */
@Configuration
public class FXConfiguration {
    @Bean
    public Scene getMainScene(MainStageController aMainStageController) // todo сделать по @Qualifier
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
        fxmlLoader.setController(aMainStageController);
        Parent root = fxmlLoader.load();
        return new Scene(root);
    }

    @Bean
    @Qualifier("modelAnchorPane")
    public Node modelPane(MathModelController aMathModelController)
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/model/Model.fxml"));
        fxmlLoader.setController(aMathModelController);
        return fxmlLoader.load();
    }

    @Bean
    @Qualifier("theoryAnchorPane")
    public Node theoryPane(TheoryController aTheoryController)
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/theory/Theory.fxml"));
        fxmlLoader.setController(aTheoryController);
        return fxmlLoader.load();
    }

    @Bean
    @Qualifier("aboutAnchorPane")
    public Node aboutPane()
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/about/About.fxml"));
        return fxmlLoader.load();
    }
}

