package com.dozz3s.invmoveac;

public class LocationData {

    private double x;
    private double z;
    private double speed;

    public LocationData(double x, double z) {
        this.x = x;
        this.z = z;
        this.speed = 0;
    }

    public void update(double x, double z, double speed) {
        this.x = x;
        this.z = z;
        this.speed = speed;
    }

    public double getX() {
        return x;
    }

    public double getZ() {
        return z;
    }

    public double getSpeed() {
        return speed;
    }
}