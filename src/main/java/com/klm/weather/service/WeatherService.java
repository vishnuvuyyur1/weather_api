package com.klm.weather.service;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather addWeather(@RequestBody Weather weather) {
        return weatherRepository.save(weather);
    }

    public Optional<Weather> getWeatherById(@PathVariable(value = "id") int id) {
        return weatherRepository.findById(id);
    }

    public List<Weather> getWeathers(Map<String, String> paramsMap) throws ParseException {
        if (paramsMap == null || paramsMap.isEmpty())
            return fetchSortedWeathers(Direction.ASC, "id");
        else return getWeathersByLookUpParam(paramsMap);
    }

    private List<Weather> getWeathersByLookUpParam(Map<String, String> paramsMap) throws ParseException {
        String dateInput = paramsMap.get("date");
        String city = paramsMap.get("city");
        String sort = paramsMap.get("sort");

        if (dateInput != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(dateInput);
            return weatherRepository.findByDate(date);
        }
        if (city != null) {
            List<String> cities = Arrays.asList(city.split(","));
            List<String> citiesLowerCase = cities.stream().map(String::toLowerCase).toList();
            return weatherRepository.fetchWeatherByCities(citiesLowerCase);
        }
        if (sort != null) {
            if (sort.equals("date")) {
                return fetchSortedWeathers(Direction.ASC, "date");
            } else if (sort.equals("-date")) {
                return fetchSortedWeathers(Direction.DESC, "date");
            }
        }
        return null;
    }

    private List<Weather> fetchSortedWeathers(Direction direction, String column) {
        return weatherRepository.findAll(Sort.by(direction, column));
    }
}
