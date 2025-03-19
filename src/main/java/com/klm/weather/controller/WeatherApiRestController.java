package com.klm.weather.controller;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherApiRestController {
    private static final String PATH = "/weather";

    private final WeatherRepository weatherRepository;

    public WeatherApiRestController(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @PostMapping(PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public Weather addWeather(@RequestBody Weather weather) {
            return weatherRepository.save(weather);
    }

}
