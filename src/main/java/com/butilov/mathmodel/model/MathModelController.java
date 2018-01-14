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
        initSliderControl(a1Slider, a1TextField, 2, 500);
        initSliderControl(a2Slider, a2TextField, 2, 500);
        initSliderControl(N0Slider, N0TextField, 30000, 150000);
        initSliderControl(NrSlider, NrTextField, 20000, 150000);
        initSliderControl(P0Slider, P0TextField, 50000, 150000);
        initSliderControl(PrSlider, PrTextField, 40000, 150000);
        initSliderControl(TSlider, TTextField, 20, 500);
        initSliderControl(NSlider, NTextField, 200, 500);

        initChartData();
    }

    private void initSliderControl(Slider slider, TextField textField, int defaultValue, int maxValue) {
        textField.setText(String.valueOf(defaultValue));
        slider.setMin(0);
        slider.setValue(defaultValue);
        slider.setMax(maxValue);
        slider.setBlockIncrement(1);
        // todo попробовать прикрутить bind  textField.textProperty().bind(Bindings.convert(slider.valueProperty()));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(String.valueOf(newValue.intValue()));
            initChartData();
        });
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            int value = Double.valueOf(newValue).intValue();
            if (value >= 0 && value <= maxValue) {
                slider.setValue(value);
                initChartData();
            } else {
                textField.textProperty().setValue(oldValue);
            }
        });
    }

    private void initChartData() {
        lineChart.setTitle("Модель зависимости зарплат и занятости");
        xAxis.setLabel("Занятость");
        yAxis.setLabel("Зарплата");
        lineChart.getData().clear();
        lineChart.getData()
                .add(executeChartSeries(
                        getDoubleValueFromTF(a1TextField),
                        getDoubleValueFromTF(a2TextField),
                        getDoubleValueFromTF(N0TextField),
                        getDoubleValueFromTF(NrTextField),
                        getDoubleValueFromTF(P0TextField),
                        getDoubleValueFromTF(PrTextField),
                        getDoubleValueFromTF(TTextField),
                        getDoubleValueFromTF(NTextField)
                ));
    }

    private Double getDoubleValueFromTF(TextField aTextField) {
        String value = aTextField.textProperty().getValue();
        return value.isEmpty() ? 0 : Double.valueOf(value);
    }

    // todo перенести метод
    private XYChart.Series<Double, Double> executeChartSeries(double a1, double a2, double n0, double nr, double p0, double pr, double T, double N) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(n0, p0));
        double h = T / N;
        double b = Math.sqrt(a1);
        double c = Math.sqrt(a2);
        double a = b * c;
        for (int i = 1; i < N; i++) {
            double t = i * h;
            double p = (Math.sin(a * t) * c * (nr - n0)) / b;
            p = p + Math.cos(a * t) * (-pr + p0) + pr;

            double n = Math.sin(a * t) * b * (p0 - pr) + nr * c;
            n = n - Math.cos(a * t) * c * (nr - n0);
            n = n / b;

            series.getData().add(new XYChart.Data<>(n, p));
        }
        return series;
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
    LineChart<Double, Double> lineChart;
//   todo ScatterChart
}