package com.butilov.mathmodel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class FXConfiguration
{
    @Bean
    public Scene getMainScene(MainStageController aMainStageController)
            throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
        Properties properties = new Properties();
        Path propFile = Paths.get("application.properties");
        properties.load(Files.newBufferedReader(propFile));

        properties.stringPropertyNames()
                .forEach(key -> fxmlLoader.getNamespace().put(key, properties.getProperty(key)));

        fxmlLoader.setController(aMainStageController);
        Parent root = fxmlLoader.load();
        return new Scene(root);
    }
}

