package com.example.rza.socialmedialogins;

public class DuyuruWebApi {
    private String baslik;
    private String duyuruGrubu;
    private String icerik;
    private String kriter;
    private String odaGrubu;
    private String musteriAdi;
    private String personelGrubu;
    private String duyuruTarihi;
    private boolean gorunur;

    public DuyuruWebApi(String baslik, String duyuruGrubu, String icerik, String kriter, String odaGrubu, String musteriAdi, String personelGrubu, String duyuruTarihi, boolean gorunur) {
        this.baslik = baslik;
        this.duyuruGrubu = duyuruGrubu;
        this.icerik = icerik;
        this.kriter = kriter;
        this.odaGrubu = odaGrubu;
        this.musteriAdi = musteriAdi;
        this.personelGrubu = personelGrubu;
        this.duyuruTarihi = duyuruTarihi;
        this.gorunur = gorunur;
    }

    public String getBaslik() {
        return baslik;
    }

    public String getIcerik() {
        return icerik;
    }

    public String getDuyuruTarihi() {
        return duyuruTarihi;
    }

    public String getKriter() {
        return kriter;
    }

    public String getOdaGrubu() {
        return odaGrubu;
    }

    public String getMusteriAdi() {
        return musteriAdi;
    }

    public boolean getGorunur() {
        return gorunur;
    }

    public String getDuyuruGrubu() {
        return duyuruGrubu;
    }

    public String getPersonelGrubu() {
        return personelGrubu;
    }
}
