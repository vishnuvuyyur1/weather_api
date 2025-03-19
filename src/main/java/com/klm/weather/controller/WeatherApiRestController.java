package com.klm.weather.controller;

import com.klm.weather.model.Weather;
import com.klm.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherApiRestController {

    private final WeatherService weatherService;

    @PostMapping
    public ResponseEntity<Weather> addWeather(@RequestBody Weather weather) {
        Weather record = weatherService.addWeather(weather);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable(value = "id") int id) {
        Optional<Weather> record = weatherService.getWeatherById(id);
        return record.map(weather -> new ResponseEntity<>(weather, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Weather>> getWeathers(@RequestParam Map<String, String> paramsMap) throws ParseException {
        List<Weather> records = weatherService.getWeathers(paramsMap);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

}
