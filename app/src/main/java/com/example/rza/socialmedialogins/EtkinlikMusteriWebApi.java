package com.example.rza.socialmedialogins;

public class EtkinlikMusteriWebApi {

    private int etkinlikno;
    private String musteriKullanici;
    private String isJoined;
    private int kisiSayisi;

    public EtkinlikMusteriWebApi(int etkinlikno, String musteriKullanici, String isJoined, int kisiSayisi) {
        this.etkinlikno = etkinlikno;
        this.musteriKullanici = musteriKullanici;
        this.isJoined = isJoined;
        this.kisiSayisi = kisiSayisi;
    }

    public int getEtkinlikno() {
        return etkinlikno;
    }

    public String getMusteriKullanici() {
        return musteriKullanici;
    }

    public String getIsJoined() {
        return isJoined;
    }

    public int getKisiSayisi() {
        return kisiSayisi;
    }

}
