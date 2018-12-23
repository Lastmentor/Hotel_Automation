package com.example.rza.socialmedialogins;

public class OdaTemizlikWebApi {

    private String odaNo;
    private String temizlikTarihi;
    private String blok;
    private String katAdi;

    public OdaTemizlikWebApi(String odaNo, String temizlikTarihi, String blok, String katAdi) {
        this.odaNo = odaNo;
        this.temizlikTarihi = temizlikTarihi;
        this.blok = blok;
        this.katAdi = katAdi;
    }

    public String getOdaNo() {
        return odaNo;
    }

    public String getTemizlikTarihi() {
        return temizlikTarihi;
    }

    public String getBlok() {
        return blok;
    }

    public String getKatAdi() {
        return katAdi;
    }
}
