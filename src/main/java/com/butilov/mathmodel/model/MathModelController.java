package com.butilov.mathmodel.model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Butilov
 * on 11.01.18.
 */
@Component
public class MathModelController {
    @FXML
    public void initialize() {
        testButton.setOnAction(e -> {
            eventTextField.appendText("hello button pressed \n");
        });

        initChartData();
    }

    private void initChartData() {
        xAxis.setLabel("Number of Month");
        lineChart.setTitle("Stock Monitoring, 2018");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        lineChart.getData().add(series);
    }

    @FXML
    private JFXButton testButton;
    @FXML
    private JFXTextArea eventTextField;
    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    LineChart<Number, Number> lineChart;
}