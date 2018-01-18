package com.butilov.mathmodel.model;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Butilov
 * on 11.01.18.
 */
@Component
public class MathModelController {
    @FXML
    public void initialize() {
        initSliders();
        initTooltips();
        initChartData();
    }

    private void initTooltips() {
        initTooltip(a1TextField, a1Label, "Коэффициент изменения зарплаты");
        initTooltip(a2TextField, a2Label, "Коэффициент изменения занятых");
        initTooltip(P0TextField, P0Label, "Значение зарплаты в начале исследования");
        initTooltip(N0TextField, N0Label, "Значение числа занятых мест в начале исследования");
        initTooltip(PrTextField, PrLabel, "Значение зарплаты в начале исследования (равновесие)");
        initTooltip(NrTextField, NrLabel, "Значение числа занятых мест в начале исследования (равновесие)");
        initTooltip(TTextField, TLabel, "Время наблюдения за реакцией");
        initTooltip(NTextField, NLabel, "Количество узлов сетки");
    }

    private void initTooltip(TextField textField, Label label, String tooltipString) {
        Tooltip tooltip = new Tooltip(tooltipString);
        textField.setTooltip(tooltip);
        label.setTooltip(tooltip);
    }

    private void initSliders() {
        initSliderControl(a1Slider, a1TextField, 2, 0, 500);
        initSliderControl(a2Slider, a2TextField, 2, 0, 500);
        initSliderControl(N0Slider, N0TextField, 30000, 15000, 150000);
        initSliderControl(NrSlider, NrTextField, 20000, 15000, 150000);
        initSliderControl(P0Slider, P0TextField, 50000, 15000, 150000);
        initSliderControl(PrSlider, PrTextField, 40000, 15000, 150000);
        initSliderControl(TSlider, TTextField, 20, 0, 500);
        initSliderControl(NSlider, NTextField, 200, 0, 500);
    }

    private void initSliderControl(Slider slider, TextField textField, int defaultValue, int minValue, int maxValue) {
        textField.setText(String.valueOf(defaultValue));
        slider.setMin(minValue);
        slider.setValue(defaultValue);
        slider.setMax(maxValue);
        // todo попробовать прикрутить bind  textField.textProperty().bind(Bindings.convert(slider.valueProperty()));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(String.valueOf(newValue.intValue()));
        });
        slider.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> initChartData());

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            int value = newValue.isEmpty() ? 0 : Double.valueOf(newValue).intValue();
            if (value >= minValue && value <= maxValue) {
                slider.setValue(value);
            }
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                final String stringValue = textField.textProperty().getValue();
                int value = stringValue.isEmpty() ? 0 : Double.valueOf(stringValue).intValue();
                if (value >= minValue && value <= maxValue) {
                    slider.setValue(value);
                } else if (value < minValue) {
                    slider.setValue(minValue);
                    textField.textProperty().setValue(String.valueOf(minValue));
                } else {
                    slider.setValue(maxValue);
                    textField.textProperty().setValue(String.valueOf(maxValue));
                }
                initChartData();
            }
        });
    }

    private void initChartData() {
        lineChart.setTitle("Модель зависимости зарплат и занятости");
        xAxis.setLabel("Время, t");
        yAxis.setLabel("Занятость, N   и   зарплата, P");
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
        XYChart.Series<Double, Double> nData = new XYChart.Series<>();
        XYChart.Series<Double, Double> pData = new XYChart.Series<>();
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

            nData.getData().add(new XYChart.Data<>(t, n));
            pData.getData().add(new XYChart.Data<>(t, p));
        }
        pData.setName("Размер зарплаты");
        nData.setName("Количество занятых");
        lineChart.getData().add(pData);
        lineChart.getData().add(nData);
    }

    @FXML
    private Label headerLabel;
    @FXML
    private Label a1Label;
    @FXML
    private Label a2Label;
    @FXML
    private Label P0Label;
    @FXML
    private Label N0Label;
    @FXML
    private Label PrLabel;
    @FXML
    private Label NrLabel;
    @FXML
    private Label TLabel;
    @FXML
    private Label NLabel;

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