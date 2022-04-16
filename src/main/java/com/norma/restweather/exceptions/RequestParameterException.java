package com.norma.restweather.exceptions;

public class RequestParameterException extends WeatherControllerException{

    public RequestParameterException() {
        super();
    }

    public RequestParameterException(String message) {
        super(message);
    }

    public RequestParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
