package com.ridho.skripsi.model;

public class CustomBluetoothDevice {

    String name;
    String address;
    double distance;

    public CustomBluetoothDevice() {
    }

    public CustomBluetoothDevice(String name, String address, double distance) {
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
