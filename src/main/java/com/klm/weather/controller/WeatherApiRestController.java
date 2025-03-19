package com.klm.weather.controller;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {

    private final WeatherRepository weatherRepository;

    public WeatherApiRestController(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @PostMapping
    public ResponseEntity<Weather> addWeather(@RequestBody Weather weather) {
        Weather record = weatherRepository.save(weather);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable(value = "id") int id) {
        Optional<Weather> record = weatherRepository.findById(id);
        return record.map(weather -> new ResponseEntity<>(weather, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

}
