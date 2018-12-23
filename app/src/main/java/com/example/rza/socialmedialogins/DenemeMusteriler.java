package com.example.rza.socialmedialogins;

public class DenemeMusteriler {

    private String musteriMail;
    private String musteriAdi;
    private String musteriSoyadi;
    private String musteriActive;

    public DenemeMusteriler(String musteriMail, String musteriAdi, String musteriSoyadi, String musteriActive) {
        this.musteriMail = musteriMail;
        this.musteriAdi = musteriAdi;
        this.musteriSoyadi = musteriSoyadi;
        this.musteriActive = musteriActive;
    }

    public String getMusteriMail() {
        return musteriMail;
    }

    public String getMusteriAdi() {
        return musteriAdi;
    }

    public String getMusteriSoyadi() {
        return musteriSoyadi;
    }

    public String getMusteriActive() {
        return musteriActive;
    }
}
