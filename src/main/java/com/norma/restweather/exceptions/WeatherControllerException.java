package com.norma.restweather.exceptions;

public class WeatherControllerException extends Exception{

    public WeatherControllerException(){

    }
    public WeatherControllerException(String message){
        super(message);
    }

    public WeatherControllerException(String message,Throwable cause){
        super(message,cause);
    }
}
