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
            eventsTextArea.textProperty().bind(mI18N.createStringBinding("events.example1"));
            initChartData();
        });
        exampleButton2.setOnAction(e -> {
            P0TextField.textProperty().setValue("50000");
            N0TextField.textProperty().setValue("20000");
            PrTextField.textProperty().setValue("40000");
            NrTextField.textProperty().setValue("20000");
            eventsTextArea.textProperty().bind(mI18N.createStringBinding("events.example2"));
            initChartData();
        });
        exampleButton3.setOnAction(e -> {
            a1TextField.textProperty().setValue("2");
            a2TextField.textProperty().setValue("2");
            N0TextField.textProperty().setValue("6000");
            NrTextField.textProperty().setValue("6000");
            P0TextField.textProperty().setValue("90000");
            PrTextField.textProperty().setValue("87000");
            TTextField.textProperty().setValue("6");
            NTextField.textProperty().setValue("12");
            eventsTextArea.textProperty().bind(mI18N.createStringBinding("events.example3"));
            initChartData();
        });
        localeButton.setOnAction(event -> mI18N.switchLocale());
    }

    private void initTooltips() {
        initTooltip(a1TextField, a1Label, "tooltip.a1");
        initTooltip(a2TextField, a2Label, "tooltip.a2");
        initTooltip(P0TextField, P0Label, "tooltip.P0");
        initTooltip(N0TextField, N0Label, "tooltip.N0");
        initTooltip(PrTextField, PrLabel, "tooltip.Pr");
        initTooltip(NrTextField, NrLabel, "tooltip.Nr");
        initTooltip(TTextField, TLabel, "tooltip.T");
        initTooltip(NTextField, NLabel, "tooltip.N");
    }

    private void initTooltip(TextField textField, Label label, String tooltipStringKey) {
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(mI18N.createStringBinding(tooltipStringKey));
        textField.setTooltip(tooltip);
        label.setTooltip(tooltip);
    }

    private void initSliders() {
        initSliderControl(a1Slider, a1TextField, 3, 1, 500, "events.inc.a1", "events.dec.a1");
        initSliderControl(a2Slider, a2TextField, 3, 1, 500, "events.inc.a2", "events.dec.a2");
        initSliderControl(N0Slider, N0TextField, 40000, 2000, 150000, "events.inc.N0", "events.dec.N0");
        initSliderControl(NrSlider, NrTextField, 30000, 2000, 150000, "events.inc.Nr", "events.dec.Nr");
        initSliderControl(P0Slider, P0TextField, 60000, 20000, 150000, "events.inc.P0", "events.dec.P0");
        initSliderControl(PrSlider, PrTextField, 50000, 20000, 150000, "events.inc.Pr", "events.dec.Pr");
        initSliderControl(TSlider, TTextField, 11, 0, 500, "events.inc.T", "events.dec.T");
        initSliderControl(NSlider, NTextField, 250, 0, 500, "events.inc.N", "events.dec.N");
    }

    private void initSliderControl(Slider s, TextField tf, int start, int min, int max, String incKey, String decKey) {
        tf.setText(String.valueOf(start));
        s.setMin(min);
        s.setMax(max);
        s.setValue(start);
        s.setBlockIncrement((max - min) / 20);
        // todo попробовать прикрутить bind  textField.textProperty().bind(Bindings.convert(slider.valueProperty()));
        s.valueProperty().addListener((o, oldValue, newVal) -> {
            tf.setText(String.valueOf(newVal.intValue()));
            if (newVal.intValue() > oldValue.intValue()) {
                eventsTextArea.textProperty().bind(mI18N.createStringBinding(incKey));
            }
            if (newVal.intValue() < oldValue.intValue()) {
                eventsTextArea.textProperty().bind(mI18N.createStringBinding(decKey));
            }
        });
        s.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> initChartData());
        s.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                initChartData();
            }
        });

        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            int value = newValue.isEmpty() ? 0 : Double.valueOf(newValue).intValue();
            if (value >= min && value <= max) {
                s.setValue(value);
                try {
                    if (value > Integer.valueOf(oldValue)) {
                        eventsTextArea.textProperty().bind(mI18N.createStringBinding(incKey));
                    }
                    if (value < Integer.valueOf(oldValue)) {
                        eventsTextArea.textProperty().bind(mI18N.createStringBinding(decKey));
                    }
                } catch (NumberFormatException ignored) {

                }
            }
        });
        tf.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                final String stringValue = tf.textProperty().getValue();
                int value = stringValue.isEmpty() ? 0 : Double.valueOf(stringValue).intValue();
                if (value >= min && value <= max) {
                    s.setValue(value);
                } else if (value < min) {
                    s.setValue(min);
                    tf.textProperty().setValue(String.valueOf(min));
                } else {
                    s.setValue(max);
                    tf.textProperty().setValue(String.valueOf(max));
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
        parametersHeader.textProperty().bind(mI18N.createStringBinding("parameters.header"));
        // График
        lineChart.titleProperty().bind(mI18N.createStringBinding("linechart.title"));
        xAxis.labelProperty().bind(mI18N.createStringBinding("linechart.xaxis"));
        yAxis.labelProperty().bind(mI18N.createStringBinding("linechart.yaxis"));
        // События
        eventsHeader.textProperty().bind(mI18N.createStringBinding("events.header"));
        eventsTextArea.textProperty().bind(mI18N.createStringBinding("events.text"));
        // Кнопки
        exampleButton1.textProperty().bind(mI18N.createStringBinding("button.example1"));
        exampleButton2.textProperty().bind(mI18N.createStringBinding("button.example2"));
        exampleButton3.textProperty().bind(mI18N.createStringBinding("button.example3"));
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
        mSolver.solveEquations(a1, a2, n0, nr, p0, pr, T, N);

        IntStream.range(0, (int) N).forEach(i -> {
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
    private JFXButton exampleButton3;
    @FXML
    private JFXButton localeButton;

    @FXML
    private Label eventsHeader;
    @FXML
    private Label parametersHeader;
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