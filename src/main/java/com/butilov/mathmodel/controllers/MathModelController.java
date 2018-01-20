package com.butilov.mathmodel.controllers;

import com.butilov.mathmodel.Solver;
import com.butilov.mathmodel.localization.I18N;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

/**
 * Created by Dmitry Butilov
 * on 11.01.18.
 */
@Component
public class MathModelController {

    @Autowired
    public MathModelController(Solver aSolver, I18N aI18N) {
        mSolver = aSolver;
        mI18N = aI18N;
    }

    @FXML
    public void initialize() {
        initSliders();
        initButtonActions();
        initChartData();
        initTextArea();
        initText();
    }

    private void initTextArea() {
        eventsTextArea.setEditable(false);
    }

    private void initButtonActions() {
        exampleButton1.setOnAction(e -> {
            P0TextField.textProperty().setValue("60000");
            N0TextField.textProperty().setValue("25000");
            PrTextField.textProperty().setValue("60000");
            NrTextField.textProperty().setValue("25000");
            initChartData();
        });
        exampleButton2.setOnAction(e -> {
            P0TextField.textProperty().setValue("50000");
            N0TextField.textProperty().setValue("20000");
            PrTextField.textProperty().setValue("40000");
            NrTextField.textProperty().setValue("20000");
            initChartData();
        });
        localeButton.setOnAction(event -> mI18N.switchLocale());
    }

    private void initTooltips() {
        initTooltip(a1TextField, a1Label, "Коэффициент изменения зарплаты");
        initTooltip(a2TextField, a2Label, "Коэффициент изменения занятых");
        initTooltip(P0TextField, P0Label, "Значение зарплаты в начале исследования");
        initTooltip(N0TextField, N0Label, "Значение числа занятых мест в начале исследования");
        initTooltip(PrTextField, PrLabel, "Значение зарплаты в начале исследования (равновесие)");
        initTooltip(NrTextField, NrLabel, "Значение числа занятых мест в начале исследования (равновесие)");
        initTooltip(TTextField, TLabel, "Время наблюдения");
        initTooltip(NTextField, NLabel, "Количество разбиений по времени");
    }

    private void initTooltip(TextField textField, Label label, String tooltipString) {
        Tooltip tooltip = new Tooltip(tooltipString);
        textField.setTooltip(tooltip);
        label.setTooltip(tooltip);
    }

    private void initSliders() {
        initSliderControl(a1Slider, a1TextField, 3, 0, 500);
        initSliderControl(a2Slider, a2TextField, 3, 0, 500);
        initSliderControl(N0Slider, N0TextField, 40000, 20000, 100000);
        initSliderControl(NrSlider, NrTextField, 30000, 20000, 100000);
        initSliderControl(P0Slider, P0TextField, 60000, 20000, 100000);
        initSliderControl(PrSlider, PrTextField, 50000, 20000, 100000);
        initSliderControl(TSlider, TTextField, 11, 0, 500);
        initSliderControl(NSlider, NTextField, 250, 0, 500);
    }

    private void initSliderControl(Slider slider, TextField textField, int defaultValue, int minValue, int maxValue) {
        textField.setText(String.valueOf(defaultValue));
        slider.setMin(minValue);
        slider.setMax(maxValue);
        slider.setValue(defaultValue);
        slider.setBlockIncrement((maxValue - minValue) / 20);
        // todo попробовать прикрутить bind  textField.textProperty().bind(Bindings.convert(slider.valueProperty()));
        slider.valueProperty().addListener((o, old, newVal) -> textField.setText(String.valueOf(newVal.intValue())));
        slider.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> initChartData());
        slider.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                initChartData();
            }
        });

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

    private void initText() {
        // График
        lineChart.titleProperty().bind(mI18N.createStringBinding("linechart.title"));
        xAxis.labelProperty().bind(mI18N.createStringBinding("linechart.xaxis"));
        yAxis.labelProperty().bind(mI18N.createStringBinding("linechart.yaxis"));
        // События
        eventsHeader.textProperty().bind(mI18N.createStringBinding("events.header"));
        eventsTextArea.textProperty().bind(mI18N.createStringBinding("events.text"));
        // Кнопки
        localeButton.textProperty().bind(mI18N.createStringBinding("button.change.lang"));
        // Подсказки
        initTooltips();
    }

    private Double getDoubleValueFromTF(TextField aTextField) {
        String value = aTextField.textProperty().getValue();
        return value.isEmpty() ? 0 : Double.valueOf(value);
    }

    private void executeChartSeries(double a1, double a2, double n0, double nr, double p0, double pr, double T, double N) {
        lineChart.getData().clear();
        XYChart.Series<Double, Double> nData = new XYChart.Series<>();
        XYChart.Series<Double, Double> pData = new XYChart.Series<>();
        if (a1 == 0) {
            a1 = 0.01; // костыль!
        }
        mSolver.solveEquations(a1, a2, n0, nr, p0, pr, T, N);

        IntStream.range(1, (int) N).forEach(i -> {
            nData.getData().add(new XYChart.Data<>(mSolver.getT()[i], mSolver.getN()[i]));
            pData.getData().add(new XYChart.Data<>(mSolver.getT()[i], mSolver.getP()[i]));
        });
        pData.nameProperty().bind(mI18N.createStringBinding("linechart.pdata"));
        nData.nameProperty().bind(mI18N.createStringBinding("linechart.ndata"));
        lineChart.getData().add(pData);
        lineChart.getData().add(nData);
    }

    @FXML
    private JFXTextArea eventsTextArea;
    @FXML
    private JFXButton exampleButton1;
    @FXML
    private JFXButton exampleButton2;
    @FXML
    private JFXButton localeButton;

    @FXML
    private Label eventsHeader;
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
    private XYChart<Double, Double> lineChart;

    private Solver mSolver;

    private I18N mI18N;
}