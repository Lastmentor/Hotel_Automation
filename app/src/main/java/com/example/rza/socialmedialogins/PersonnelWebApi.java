package com.example.rza.socialmedialogins;

public class PersonnelWebApi {

    private String personelMail;
    private String personelAdi;
    private String personelSoyadi;
    private String personelKullaniciAdi;
    private String personelSifre;
    private String personelDepartman;
    private Boolean sil;

    public PersonnelWebApi(String personelMail, String personelAdi, String personelSoyadi, String personelKullaniciAdi, String personelSifre, Boolean sil, String personelDepartman) {
        this.personelMail = personelMail;
        this.personelAdi = personelAdi;
        this.personelSoyadi = personelSoyadi;
        this.personelKullaniciAdi = personelKullaniciAdi;
        this.personelSifre = personelSifre;
        this.personelDepartman = personelDepartman;
        this.sil = sil;
    }

    public String getPersonelMail() {
        return personelMail;
    }

    public String getPersonelAdSoyad() {
        return personelAdi + " " + personelSoyadi;
    }

    public String getUsernamePassword() { return personelKullaniciAdi+personelSifre;}

    public String getPersonelDepartman() { return personelDepartman;}

    public Boolean getSil() {
        return sil;
    }
}
