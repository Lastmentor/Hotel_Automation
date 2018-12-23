package com.example.rza.socialmedialogins;

public class SifreliMusterilerWebApi {
    private String musteriMail;
    private String musteriAdi;
    private String musteriSoyadi;
    private String musteriKullaniciAdi;
    private String musteriSifre;
    private String musteriActive;

    public SifreliMusterilerWebApi(String musteriMail, String musteriAdi, String musteriSoyadi, String musteriKullaniciAdi, String musteriSifre, String musteriActive) {
        this.musteriMail = musteriMail;
        this.musteriAdi = musteriAdi;
        this.musteriSoyadi = musteriSoyadi;
        this.musteriKullaniciAdi = musteriKullaniciAdi;
        this.musteriSifre = musteriSifre;
        this.musteriActive = musteriActive;
    }

    public String getMusteriMail() {
        return musteriMail;
    }

    public String getKullaniciSifre() {return musteriKullaniciAdi+musteriSifre;}

    public String getMusteriActive() {
        return musteriActive;
    }
}
