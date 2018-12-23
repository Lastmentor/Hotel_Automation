package com.example.rza.socialmedialogins;

public class EtkinlikWebApi {
    private int etkinlikno;
    private String etkinlikAdi;
    private String etkinlikAciklama;
    private String etkinlikTarihi;
    private int etkinlikLimit;
    private boolean gorunur;

    public EtkinlikWebApi(int etkinlikno, String etkinlikAdi, String etkinlikAciklama, String etkinlikTarihi, int etkinlikLimit, boolean gorunur) {
        this.etkinlikno = etkinlikno;
        this.etkinlikAdi = etkinlikAdi;
        this.etkinlikAciklama = etkinlikAciklama;
        this.etkinlikTarihi = etkinlikTarihi;
        this.etkinlikLimit = etkinlikLimit;
        this.gorunur = gorunur;
    }

    public int getEtkinlikno() {
        return etkinlikno;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public String getEtkinlikAciklama() {
        return etkinlikAciklama;
    }

    public String getEtkinlikTarihi() {
        return etkinlikTarihi;
    }

    public int getEtkinlikLimit() {
        return etkinlikLimit;
    }

    public boolean getGorunur() {
        return gorunur;
    }
}
