package com.example.rza.socialmedialogins;

public class KapiWebApi {

    private String kapiNo;
    private String kapiKod;
    private String blok;
    private String kat;

    public KapiWebApi(String kapiNo, String kapiKod, String blok, String kat) {
        this.kapiNo = kapiNo;
        this.kapiKod = kapiKod;
        this.blok = blok;
        this.kat = kat;
    }

    public String getKapiNo() {
        return kapiNo;
    }

    public String getKapiKod() {
        return kapiKod;
    }

    public String getBlok() {
        return blok;
    }

    public String getKat() {
        return kat;
    }
}
