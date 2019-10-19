package com.example.abdelysf.mymanuelnavigationdrawar.model;

/**
 * Created by abdel ysf on 20/01/2018.
 */

public class Doctor {

    private String nom,prenom,tele,adresse,email;

    public Doctor(String nom, String prenom, String tele, String adresse, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.tele = tele;
        this.adresse = adresse;
        this.email = email;
    }

    public Doctor() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return  nom ;
    }
}
