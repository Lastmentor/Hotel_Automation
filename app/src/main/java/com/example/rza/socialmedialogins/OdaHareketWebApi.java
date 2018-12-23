package com.example.rza.socialmedialogins;

public class OdaHareketWebApi {

    private String odaNo;
    private String islemTarihi1;
    private String islemTarihii2;
    private String musteriPersonel;
    private String islemTipi;
    private Boolean onay;
    private String mail;
    private String odaTip;
    private String blok;
    private String katno;

    public OdaHareketWebApi(String odaNo, String islemTarihi1, String islemTarihii2, String musteriPersonel, String islemTipi, Boolean onay, String mail, String odaTip, String blok, String katno) {
        this.odaNo = odaNo;
        this.islemTarihi1 = islemTarihi1;
        this.islemTarihii2 = islemTarihii2;
        this.musteriPersonel = musteriPersonel;
        this.islemTipi = islemTipi;
        this.onay = onay;
        this.mail = mail;
        this.odaTip = odaTip;
        this.blok = blok;
        this.katno = katno;
    }

    public String getOdaNo() {
        return odaNo;
    }

    public String getIslemTarihi1() {
        return islemTarihi1;
    }

    public String getIslemTarihii2() {
        return islemTarihii2;
    }

    public String getMusteriPersonel() {
        return musteriPersonel;
    }

    public String getMail() {
        return mail;
    }

    public String getOdaTip() {
        return odaTip;
    }

    public String getBlok() {
        return blok;
    }

    public String getKatno() {
        return katno;
    }

    public String getIslemTipi() {
        return islemTipi;
    }

    public Boolean getOnay() {
        return onay;
    }
}
