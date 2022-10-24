package com.example.demo.configuration;

import com.example.demo.dto.Garage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class GarageConfiguration {

  @Bean
  public Garage garage() {
    Garage garage = new Garage();
    garage.setGarageMap(new HashMap<>());
    return garage;
  }
}
