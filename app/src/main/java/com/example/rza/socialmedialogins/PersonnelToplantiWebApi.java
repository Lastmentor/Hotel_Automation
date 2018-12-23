package com.example.rza.socialmedialogins;

public class PersonnelToplantiWebApi {

    private String toplantiKonusu;
    private String aciklama;
    private String toplantiTarihi;
    private String toplantiSalonu;
    private String toplantiPersonelKategori;

    public PersonnelToplantiWebApi(String toplantiKonusu, String aciklama, String toplantiTarihi, String toplantiSalonu, String toplantiPersonelKategori) {
        this.toplantiKonusu = toplantiKonusu;
        this.aciklama = aciklama;
        this.toplantiTarihi = toplantiTarihi;
        this.toplantiSalonu = toplantiSalonu;
        this.toplantiPersonelKategori = toplantiPersonelKategori;
    }

    public String getToplantiKonusu() {
        return toplantiKonusu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public String getToplantiTarihi() {
        return toplantiTarihi;
    }

    public String getToplantiSalonu() {
        return toplantiSalonu;
    }

    public String getToplantiPersonelKategori() {
        return toplantiPersonelKategori;
    }
}
