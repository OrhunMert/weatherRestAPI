package com.norma.restweather.controller;

import com.norma.restweather.exceptions.RequestParameterException;
import com.norma.restweather.service.Imp.WeatherService;
import com.norma.restweather.model.WeatherModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor//We used this because we need to instance of WeatherService Class
@Slf4j
@Validated
public class WeatherController {

    /*
    @Autowired
    RestTemplate restTemplate;
    */

    private final WeatherService weatherService;

    @PostMapping()
    public ResponseEntity<?> createWeather(@Valid @RequestBody WeatherModel weatherModel) {

        return new ResponseEntity<>(weatherModel, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getWeather(@RequestBody WeatherModel weatherModel){

        log.info("get Weather and city name is "+weatherModel.getCityName());
        return new ResponseEntity<>(weatherModel,HttpStatus.OK);
    }

    @GetMapping("/currentweather/")
    public ResponseEntity<?> getCurrentWeather(@RequestParam @NotBlank(message = "city name must be") String cityName) throws IOException, ParseException, RequestParameterException {

        log.info("get current Weather and city name is "+cityName);
        WeatherModel weatherModel = weatherService.createWeather(cityName);
        return new ResponseEntity<>(weatherModel,HttpStatus.OK);

    }

    @GetMapping("/forecastweather/")
    public ResponseEntity<?> getHistoricalWeather(@RequestParam @NotBlank String cityName,
                                                  @RequestParam @Min(1) @Max(3) int dayNumber) throws RequestParameterException, IOException, ParseException {

        log.info("get forcast Weather and city name is "+cityName+" days:"+dayNumber);
        WeatherModel weatherModel = weatherService.createForecastWeather(cityName , dayNumber);
        return new ResponseEntity<>(weatherModel , HttpStatus.OK);
    }

}
