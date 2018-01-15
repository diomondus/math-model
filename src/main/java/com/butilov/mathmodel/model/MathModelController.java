package com.butilov.mathmodel.model;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Butilov
 * on 11.01.18.
 */
@Component
public class MathModelController {
    @FXML
    public void initialize() {
        initSliderControl(a1Slider, a1TextField, 2, 0, 500);
        initSliderControl(a2Slider, a2TextField, 2, 0, 500);
        initSliderControl(N0Slider, N0TextField, 30000, 15000, 150000);
        initSliderControl(NrSlider, NrTextField, 20000, 15000, 150000);
        initSliderControl(P0Slider, P0TextField, 50000, 15000, 150000);
        initSliderControl(PrSlider, PrTextField, 40000, 15000, 150000);
        initSliderControl(TSlider, TTextField, 20, 0, 500);
        initSliderControl(NSlider, NTextField, 200, 0, 500);

        a1TextField.setTooltip(new Tooltip("Коэффициент изменения зарплаты"));
        a2TextField.setTooltip(new Tooltip("Коэффициент изменения занятых"));
        P0TextField.setTooltip(new Tooltip("Значение зарплаты в начале исследования"));
        N0TextField.setTooltip(new Tooltip("Значение числа занятых мест в начале исследования"));
        TTextField.setTooltip(new Tooltip("Время наблюдения за реакцией"));
        NTextField.setTooltip(new Tooltip("Количество узлов сетки"));
        NrTextField.setTooltip(new Tooltip("Значение числа занятых мест в начале исследования (равновесие)"));
        PrTextField.setTooltip(new Tooltip("Значение зарплаты в начале исследования (равновесие)"));

        initChartData();
    }

    private void initSliderControl(Slider slider, TextField textField, int defaultValue, int minValue, int maxValue) {
        textField.setText(String.valueOf(defaultValue));
        slider.setMin(minValue);
        slider.setValue(defaultValue);
        slider.setMax(maxValue);
        slider.setBlockIncrement(1);
        // todo попробовать прикрутить bind  textField.textProperty().bind(Bindings.convert(slider.valueProperty()));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(String.valueOf(newValue.intValue()));
            initChartData();
        });
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            int value = newValue.isEmpty() ? 0 : Double.valueOf(newValue).intValue();
            if (value >= 0 && value <= maxValue) {
                slider.setValue(value);
            } else {
                textField.textProperty().setValue(oldValue);
            }
        });
    }

    private void initChartData() {
        lineChart.setTitle("Модель зависимости зарплат и занятости");
        xAxis.setLabel("Время");
        yAxis.setLabel("Величина зарплаты/занятости");
        lineChart.getData().clear();
        executeChartSeries(
                getDoubleValueFromTF(a1TextField),
                getDoubleValueFromTF(a2TextField),
                getDoubleValueFromTF(N0TextField),
                getDoubleValueFromTF(NrTextField),
                getDoubleValueFromTF(P0TextField),
                getDoubleValueFromTF(PrTextField),
                getDoubleValueFromTF(TTextField),
                getDoubleValueFromTF(NTextField)
        );
    }

    private Double getDoubleValueFromTF(TextField aTextField) {
        String value = aTextField.textProperty().getValue();
        return value.isEmpty() ? 0 : Double.valueOf(value);
    }

    // todo перенести метод
    private void executeChartSeries(double a1, double a2, double n0, double nr, double p0, double pr, double T, double N) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        XYChart.Series<Double, Double> series1 = new XYChart.Series<>();
        int shift = 1;
        if (a1 == 0) {
            a1 = 0.01; // костыль!
        }
        double h = T / N / shift;
        double b = Math.sqrt(a1);
        double c = Math.sqrt(a2);
        double a = b * c;
        for (int i = 1; i < N * shift; i++) {
            double t = i * h;
            double p = (Math.sin(a * t) * c * (nr - n0)) / b;
            p = p + Math.cos(a * t) * (-pr + p0) + pr;

            double n = Math.sin(a * t) * b * (p0 - pr) + nr * c;
            n = n - Math.cos(a * t) * c * (nr - n0);
            n = n / b;

            series.getData().add(new XYChart.Data<>(t, n));
            series1.getData().add(new XYChart.Data<>(t, p));
        }
        series.setName("Количество занятых");
        series1.setName("Размер зарплаты");
        lineChart.getData().add(series);
        lineChart.getData().add(series1);
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
    XYChart<Double, Double> lineChart;
}