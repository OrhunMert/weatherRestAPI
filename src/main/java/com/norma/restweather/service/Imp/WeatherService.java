package com.norma.restweather.service.Imp;

import com.norma.restweather.exceptions.RequestParameterException;
import com.norma.restweather.service.IWeatherService;
import com.norma.restweather.model.WeatherModel;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

@Service
@Slf4j
public class WeatherService implements IWeatherService {

    @Override
    public WeatherModel createWeather(String cityName) throws IOException, RequestParameterException, ParseException {

        //get current Date of User
        Date date = new Date();

        URL url = new URL("http://api.weatherstack.com/current?access_key=030d5660a7cb727f52faa182ed332a8b&query=" + cityName);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        //Check if connect is made
        int responseCode = conn.getResponseCode();

        // 200 OK
        if (responseCode != 200) {
            throw new RequestParameterException("Runtime Exception!!! Error Code 200");
        }

        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        //Close the scanner
        scanner.close();

        log.info("{}", stringBuilder);

        //JSON simple library Setup with Maven is used to convert strings to JSON
        JSONParser parse = new JSONParser();
        JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(stringBuilder));

        JSONObject locationWeather = (JSONObject) dataObject.get("location");
        JSONObject currentWeather = (JSONObject) dataObject.get("current");

        //We are using for type casting. Because There is a problem for type casting of Jsonobject's Temperature.
        Long tempLong = (Long) currentWeather.get("temperature");
        double temperature = (double) tempLong;

        JSONArray currentWeatherstate = (JSONArray) currentWeather.get("weather_descriptions");

        return   WeatherModel.of()
                .cityName(cityName)
                .countryName(String.valueOf(locationWeather.get("country")))
                .weatherTemperature(temperature)
                .currentWeatherState(String.valueOf(currentWeatherstate.get(0)))
                .date(date)
                .build();


    }

    @Override
    public WeatherModel createForecastWeather(String cityName, int dayNumber) throws IOException, RequestParameterException, ParseException {

        String urlLink = "http://api.weatherapi.com/v1/forecast.json?key=ee3c37ab31c8462d93d74016221404&q=" +cityName
                + "&days=" +dayNumber+"&aqi=no&alerts=no";

        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        URL url = new URL(urlLink);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        //Check if connect is made
        int responseCode = conn.getResponseCode();

        // 200 OK
        if (responseCode != 200) {
            throw new RequestParameterException("Runtime Exception!!! Error Code 200");
        }

        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        //Close the scanner
        scanner.close();

        log.info("{}", stringBuilder);

        //JSON simple library Setup with Maven is used to convert strings to JSON
        JSONParser parse = new JSONParser();
        JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(stringBuilder));

        JSONObject location = (JSONObject) dataObject.get("location");

        JSONObject locationWeather = (JSONObject) dataObject.get("forecast");
        log.info("forecast--> {}",locationWeather);

        JSONArray Weathers = (JSONArray) locationWeather.get("forecastday");
        log.info("forecastday--> {}",Weathers);

        String weatherState="";
        JSONObject temp_day;
        JSONObject day = new JSONObject();
        JSONObject condition;

        for(int i = 0 ;i<Weathers.size();i++){

            temp_day = (JSONObject) Weathers.get(i);
            log.info("day--> {}",temp_day);
            day = (JSONObject) temp_day.get("day");
            condition = (JSONObject) day.get("condition");
            log.info("condition--> {}",condition);
            weatherState = weatherState+","+condition.get("text");
        }

        /*
        //We are using for type casting. Because There is a problem for type casting of Jsonobject's Temperature.
        Long tempLong = (Long) currentWeather.get("temperature");
        double temperature = (double) tempLong;
        */

        return WeatherModel.of()
                .cityName(cityName)
                .countryName(String.valueOf(location.get("country")))
                .currentWeatherState(weatherState)
                .weatherTemperature((Double) day.get("avgtemp_c"))
                .date(date)
                .build();
    }

}
