package com.butilov.mathmodel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Dmitry Butilov
 * on 10.01.18.
 */
@Configuration
@ComponentScan("com.butilov.mathmodel")
@Import(FXConfiguration.class) // todo почему работает без этой строки?
public class SpringConfiguration
{
    @Bean
    public FXTestBean getBean()
    {
        return new FXTestBean();
    }
}