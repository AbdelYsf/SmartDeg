package com.example.abdelysf.mymanuelnavigationdrawar.model;

/**
 * Created by abdel ysf on 27/01/2018.
 */

public class Rdv {
    private String doctorName;
    private String MedLabels;
    private String dateRdv;
    private String timeRdv;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMedLabels() {
        return MedLabels;
    }

    public void setMedLabels(String medLabels) {
        MedLabels = medLabels;
    }

    public String getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(String dateRdv) {
        this.dateRdv = dateRdv;
    }

    @Override
    public String toString() {
        return "Rdv{" +
                "doctorName='" + doctorName + '\'' +
                ", MedLabels='" + MedLabels + '\'' +
                ", dateRdv='" + dateRdv + '\'' +
                ", timeRdv='" + timeRdv + '\'' +
                '}';
    }

    public String getTimeRdv() {
        return timeRdv;
    }

    public void setTimeRdv(String timeRdv) {
        this.timeRdv = timeRdv;
    }
}

