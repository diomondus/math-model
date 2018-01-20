package com.butilov.mathmodel.localization;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by Dmitry Butilov
 * on 20.01.18.
 */
public final class I18N {

    private final ObjectProperty<Locale> locale;

    public final Locale LOCALE_RU = new Locale("ru", "RU");
    public final Locale LOCALE_EN = Locale.ENGLISH;

    public I18N() {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    public List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(LOCALE_EN, LOCALE_RU));
    }

    public Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : LOCALE_RU;
    }

    public Locale getLocale() {
        return locale.get();
    }

    public void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }

    public void switchLocale() {
        Locale newLocale = getLocale().equals(LOCALE_RU) ? LOCALE_EN : LOCALE_RU;
        localeProperty().set(newLocale);
        Locale.setDefault(newLocale);
    }

    public ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    public String get(final String key, final Object... args) {

        Properties properties = new Properties();
        Path propFile = Paths.get("src/main/resources/locale_" + getLocale().getLanguage() + ".properties");
        String string;
        try {
            properties.load(Files.newBufferedReader(propFile));
            string = properties.getProperty(key);
        } catch (IOException ex) {
            string = properties.getProperty("text.not.found");
        }
        return MessageFormat.format(string, args);
    }

    public StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }

    public StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }

    public void labelForValue(Label label, Callable<String> func) {
        label.textProperty().bind(createStringBinding(func));
    }

    public void buttonForKey(Button button, final String key, final Object... args) {
        button.textProperty().bind(createStringBinding(key, args));
    }

    public void tooltipForKey(Tooltip tooltip, final String key, final Object... args) {
        tooltip.textProperty().bind(createStringBinding(key, args));
    }
}