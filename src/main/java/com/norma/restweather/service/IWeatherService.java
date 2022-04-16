package com.norma.restweather.service;

import com.norma.restweather.exceptions.RequestParameterException;
import com.norma.restweather.model.WeatherModel;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface IWeatherService {

    WeatherModel createWeather(String cityName) throws IOException, RequestParameterException, ParseException;
    WeatherModel createForecastWeather(String cityName , int dayNumber) throws IOException, RequestParameterException, ParseException;
}
