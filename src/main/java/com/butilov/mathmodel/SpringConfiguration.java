package com.butilov.mathmodel;

import com.butilov.mathmodel.localization.I18N;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Dmitry Butilov
 * on 10.01.18.
 */
@Configuration
@ComponentScan("com.butilov.mathmodel")
public class SpringConfiguration {
    @Bean
    public Solver getSolverBean() {
        return new Solver();
    }

    @Bean
    public I18N getI18NBean() {
        return new I18N();
    }
}