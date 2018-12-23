package com.example.rza.socialmedialogins;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface Api {

    String BASE_URL = "http://hotelieu.azurewebsites.net/api/";

    // Deneme Müşteriler
    @GET("musteriler")
    Call<List<DenemeMusteriler>> getMusteriler();
    @POST("musteriler")
    Call<DenemeMusteriler> createMusteriler(@Body DenemeMusteriler denemeMusteriler);

    // Müşteriler
    @GET("sifrelimusteriler")
    Call<List<SifreliMusterilerWebApi>> getSifreliMusteriler();

    // Duyurular
    @GET("duyuru")
    Call<List<DuyuruWebApi>> getDuyurular();
    @POST("duyuru")
    Call<DuyuruWebApi> createDuyuru(@Body DuyuruWebApi denemeDuyurular);

    // Oda Hareket
    @GET("odahareket")
    Call<List<OdaHareketWebApi>> getOdaHareketInfo();
    @POST("odahareket")
    Call<OdaHareketWebApi> createOdaHareket(@Body OdaHareketWebApi denemeOdaHareket);

    // Etkinlikler
    @GET("etkinlik")
    Call<List<EtkinlikWebApi>> getEtkinlik();

    // Etkinlik Müşteri
    @GET("etkinlikmusteri")
    Call<List<EtkinlikMusteriWebApi>> getEtkinlikMusteri();
    @POST("etkinlikmusteri")
    Call<EtkinlikMusteriWebApi> createEtkinlikMusteriler(@Body EtkinlikMusteriWebApi denemeEtkinlikMusteriler);

    // Oda Temizlik
    @GET("oda")
    Call<List<OdaTemizlikWebApi>> getOdaTemizlik();

    // Personnel Info
    @GET("personel")
    Call<List<PersonnelWebApi>> getPersonnelInfo();

    // Personnel Toplantı
    @GET("toplanti")
    Call<List<PersonnelToplantiWebApi>> getPersonnelToplanti();

    // Kat Sorumlu Personeller
    @GET("katsorumlular")
    Call<List<KatSorumluPersonellerWebApi>> getSorumluPersoneller();

    // Oda Temizlik Request
    @GET("temizlikrequest")
    Call<List<OdaTemizlikRequestWebApi>> getOdaTemizlikRequest();
    @POST("temizlikrequest")
    Call<OdaTemizlikRequestWebApi> createOdaTemizlikRequest(@Body OdaTemizlikRequestWebApi denemeOdaTemizlikRequestWebApi);

    // Yemekler
    @GET("yemekler")
    Call<List<YemeklerWebApi>> getYemekler();

    // Kapi
    @GET("kapi")
    Call<List<KapiWebApi>> getKapiInfo();
    @POST("kapi")
    Call<KapiWebApi> createKapiKod(@Body KapiWebApi denemeKapiWebApi);
}
