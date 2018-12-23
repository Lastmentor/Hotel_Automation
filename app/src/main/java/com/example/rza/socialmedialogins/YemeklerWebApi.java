package com.example.rza.socialmedialogins;

public class YemeklerWebApi {

    private String yemekAdi;
    private String aciklama;
    private int yemekTurID;

    public YemeklerWebApi(String YemekAdi, String Aciklama, int YemekTurID) {
        this.yemekAdi = YemekAdi;
        this.aciklama = Aciklama;
        this.yemekTurID = YemekTurID;
    }

    public String getYemekAdi() {
        return yemekAdi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Integer getYemekTurID() {
        return yemekTurID;
    }

}
