package com.butilov.mathmodel;

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
    public FXTestBean getBean() {
        return new FXTestBean();
    }
}