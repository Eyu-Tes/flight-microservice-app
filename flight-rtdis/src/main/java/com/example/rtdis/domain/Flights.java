package com.example.rtdis.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Flights {
    private List<Flight> flights;

    public Flights() {
        flights = new ArrayList<>();
    }
}
