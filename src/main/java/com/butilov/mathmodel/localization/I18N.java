package com.butilov.mathmodel.localization;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by Dmitry Butilov
 * on 20.01.18.
 */
public final class I18N {

    private final ObjectProperty<Locale> locale;

    private final Locale LOCALE_RU = new Locale("ru", "RU");
    private final Locale LOCALE_EN = Locale.ENGLISH;

    public I18N() {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    private List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(LOCALE_EN, LOCALE_RU));
    }

    private Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : LOCALE_RU;
    }

    private Locale getLocale() {
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

    private ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    private String get(final String key, final Object... args) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("locale_" + getLocale().getLanguage() + ".properties");
        Properties properties = new Properties();
        try (Reader reader = new InputStreamReader(url.openStream(), Charset.forName("utf-8"))) {
            properties.load(reader);
        } catch (IOException ignored) {
        }
        String string = properties.getProperty(key);
        return MessageFormat.format(string, args);
    }

    public StringBinding createStringBinding(final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }

    public StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }

    private Image getImage() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("theory/theory_" + getLocale().getLanguage() + ".png");
        return new Image(url.toString());
    }

    public ObjectBinding<Image> createImageBinding() {
        return Bindings.createObjectBinding(this::getImage, locale);
    }
}