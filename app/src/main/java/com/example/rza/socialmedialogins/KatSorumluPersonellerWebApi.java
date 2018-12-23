package com.example.rza.socialmedialogins;

public class KatSorumluPersonellerWebApi {

    private String personelAdi;
    private String personelSoyadi;
    private String blok;
    private String katNo;

    public KatSorumluPersonellerWebApi(String personelAdi, String personelSoyadi, String blok, String katNo) {
        this.personelAdi = personelAdi;
        this.personelSoyadi = personelSoyadi;
        this.blok = blok;
        this.katNo = katNo;
    }

    public String getPersonelAdiSoyAdi() {
        return personelAdi + " " + personelSoyadi;
    }

    public String getBlok() {
        return blok;
    }

    public String getKatNo() {
        return katNo;
    }
}
