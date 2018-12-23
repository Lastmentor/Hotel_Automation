package com.example.rza.socialmedialogins;

public class OdaTemizlikRequestWebApi {

    private String requestDateTime;
    private String odaNo;
    private String blok;
    private String katno;

    public OdaTemizlikRequestWebApi(String requestDateTime, String odaNo, String blok, String katno) {
        this.requestDateTime = requestDateTime;
        this.odaNo = odaNo;
        this.blok = blok;
        this.katno = katno;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public String getOdaNo() {
        return odaNo;
    }

    public String getBlok() {
        return blok;
    }

    public String getKatno() {
        return katno;
    }
}
