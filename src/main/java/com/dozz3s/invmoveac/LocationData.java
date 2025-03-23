package com.dozz3s.invmoveac;

public class LocationData {
    double x, z;
    double speed;

    LocationData(double x, double z) {
        this.x = x;
        this.z = z;
        this.speed = 0;
    }

    void update(double x, double z, double speed) {
        this.x = x;
        this.z = z;
        this.speed = speed;
    }
}