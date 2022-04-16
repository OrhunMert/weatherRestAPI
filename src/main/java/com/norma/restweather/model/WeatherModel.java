package com.norma.restweather.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder(builderMethodName = "of")
public class WeatherModel {

    @NotNull(message = "City name mustn't be equals null.")
    String cityName;

    @NotEmpty(message = "country name mustn't be empty.")
    String countryName;

    @NotEmpty(message = "currentWeatherState mustn't be empty.")
    String currentWeatherState;

    Date date;//y-m-d

    @NotNull(message = "temperature is null!!!")
    double weatherTemperature;


    @Override
    public String toString() {
        return "WeatherModel{" +
                "cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", currentState='" + currentWeatherState + '\'' +
                ", weatherTemperature=" + weatherTemperature +
                ", date='" + date + '\'' +
                '}';
    }



}
