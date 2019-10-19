package com.example.abdelysf.mymanuelnavigationdrawar.model;

/**
 * Created by abdel ysf on 20/01/2018.
 */

public class Medicament {
    private String label,speciality,description;

    public Medicament(String label, String speciality, String description) {
        this.label = label;
        this.speciality = speciality;
        this.description = description;
    }

    public Medicament() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return
                 label ;

    }
}
