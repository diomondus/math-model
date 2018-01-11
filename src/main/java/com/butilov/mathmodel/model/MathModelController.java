package com.butilov.mathmodel.model;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Butilov
 * on 11.01.18.
 */
@Component
public class MathModelController {
    @FXML
    public void initialize() {
        initSliderControl(a1Slider, a1TextField, 0, 500);
        initSliderControl(a2Slider, a2TextField, 0, 500);
        initSliderControl(N0Slider, N0TextField, 15000, 150000);
        initSliderControl(NrSlider, NrTextField, 15000, 150000);
        initSliderControl(P0Slider, P0TextField, 15000, 150000);
        initSliderControl(PrSlider, PrTextField, 15000, 150000);
        initSliderControl(TSlider, TTextField, 0, 500);
        initSliderControl(NSlider, NTextField, 0, 500);
        initChartData();
    }

    private void initSliderControl(Slider slider, TextField textField, int minValue, int maxValue) {
        slider.setMin(minValue);
        slider.setValue(minValue);
        slider.setMax(maxValue);
        slider.setBlockIncrement(1);
        slider.valueProperty()
                .addListener((observable, old, newValew) -> textField.setText(String.valueOf(newValew.intValue())));
        textField.setText(String.valueOf(minValue));
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
    private JFXSlider a1Slider;
    @FXML
    private JFXSlider a2Slider;
    @FXML
    private JFXSlider N0Slider;
    @FXML
    private JFXSlider NrSlider;
    @FXML
    private JFXSlider P0Slider;
    @FXML
    private JFXSlider PrSlider;
    @FXML
    private JFXSlider TSlider;
    @FXML
    private JFXSlider NSlider;
    @FXML
    private JFXTextField a1TextField;
    @FXML
    private JFXTextField a2TextField;
    @FXML
    private JFXTextField N0TextField;
    @FXML
    private JFXTextField NrTextField;
    @FXML
    private JFXTextField P0TextField;
    @FXML
    private JFXTextField PrTextField;
    @FXML
    private JFXTextField TTextField;
    @FXML
    private JFXTextField NTextField;

    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    LineChart<Number, Number> lineChart;
}