package com.example.abdelysf.mymanuelnavigationdrawar.model;

/**
 * Created by abdel ysf on 14/01/2018.
 */

public class User {

   private String nom;
    private String prenom;

    public User(String nom, String prenom, String email, String pwd) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.pwd = pwd;
    }

    public User() {
    }

    @Override
    public String toString() {
        return  nom ;

    }

    public User(String nom, String pwd) {
        this.nom = nom;
        this.pwd = pwd;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private String email;
    private String pwd;

}
