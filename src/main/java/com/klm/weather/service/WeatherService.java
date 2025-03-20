package com.klm.weather.service;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";

    public Weather addWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    public Optional<Weather> getWeatherById(int id) {
        return weatherRepository.findById(id);
    }

    public List<Weather> getWeathers(Map<String, String> paramsMap) throws ParseException {
        if (paramsMap == null || paramsMap.isEmpty())
            return fetchSortedWeathers(Direction.ASC, COLUMN_ID);
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
                return fetchSortedWeathers(Direction.ASC, COLUMN_DATE);
            } else if (sort.equals("-date")) {
                return fetchSortedWeathers(Direction.DESC, COLUMN_DATE);
            }
        }
        return null;
    }

    private List<Weather> fetchSortedWeathers(Direction direction, String column) {
        return weatherRepository.findAll(Sort.by(direction, column));
    }
}
