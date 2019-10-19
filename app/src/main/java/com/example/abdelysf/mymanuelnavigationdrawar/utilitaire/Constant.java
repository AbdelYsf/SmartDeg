package com.example.abdelysf.mymanuelnavigationdrawar.utilitaire;

/**
 * Created by abdel ysf on 14/01/2018.
 */

public class Constant {
                                            // la base de donnees
    public static final String DB_NAME="delegue";
    public static final int DB_VERSION=1;
                                            //**********
                                            // les tables
                                            //***********
    public static final String USER_TABLE="User";
    public static final String DOCTOR_TABLE="doctor";
    public static final String MEDICAMENT_TABLE="medicament";
    public static final String RDV_TABLE="rdv";
                                            /**********
                                             * les clés des colones
                                             **********/

      // la table USER
    public static final String KEY_USER_NOM="nom";//primary key
    public static final String KEY_USER_PRENOM="prenom";
    public static final String KEY_USER_MAIL="email";
    public static final String KEY_USER_PASSWORD="pwd";

    // la table DOCTOR
    public static final String KEY_DOCTOR_NOM="nom";
    public static final String KEY_DOCTOR_PRENOM="prenom";
    public static final String KEY_DOCTOR_ADRESSE="adresse";
    public static final String KEY_DOCTOR_TELE="phone";
    public static final String KEY_DOCTOR_EMAIL="email";



    // la table Medicament

    public static final String KEY_MED_LIBELLE="libelle";
    public static final String KEY_MED_SPECIALITE="specialite";
    public static final String KEY_MED_DESCRIPTION="discription";

    //la table RDV
    public static final String KEY_RDV_DOCTOR_NAME="nomDcteur";
    public static final String KEY_RDV_MED_LABLES="libelleMedi";
    public static final String KEY_RDV_DATE="date";
    public static final String KEY_RDV_time="time";



                                    //****************************
                                    // pour le sharedPreferences**
                                    //****************************

    public  static final String KEY_sharedPre_rememberMe="remember";
    public static final String KEY_sharedPre_username="username";
    public  static final String KEY_sharedPre_password="password";





}
