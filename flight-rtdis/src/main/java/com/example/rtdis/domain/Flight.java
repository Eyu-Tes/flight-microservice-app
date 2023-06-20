package com.example.rtdis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String hex;
      private String reg_number;
      private String flag;
      private Double lat;
      private Double lng;
      private Integer alt;
      private Integer dir;
      private Integer speed;
      private Integer v_speed;
      private String squawk;
      private String flight_number;
      private String flight_icao;
      private String flight_iata;
      private String dep_icao;
      private String dep_iata;
      private String arr_icao;
      private String arr_iata;
      private String airline_icao;
      private String airline_iat;
      private String aircraft_icao;
      private Long updated;
      private String status;
}
