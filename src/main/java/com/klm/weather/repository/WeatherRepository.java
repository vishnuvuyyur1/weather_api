package com.klm.weather.repository;

import com.klm.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    List<Weather> findByDate(Date date);
}
