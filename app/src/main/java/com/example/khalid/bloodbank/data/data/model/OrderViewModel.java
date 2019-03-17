package com.example.khalid.bloodbank.data.data.model;

public class OrderViewModel {
    String bloodType;
    String patientName;
    String hospital;
    String city;

    public OrderViewModel() {
    }

    public OrderViewModel(String bloodType, String patientName, String hospital, String city) {
        this.bloodType = bloodType;
        this.patientName = patientName;
        this.hospital = hospital;
        this.city = city;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getHospital() {
        return hospital;
    }

    public String getCity() {
        return city;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
