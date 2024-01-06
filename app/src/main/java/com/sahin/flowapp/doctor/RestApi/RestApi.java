package com.sahin.flowapp.doctor.RestApi;


import com.sahin.flowapp.doctor.Models.AsiEkleModel;
import com.sahin.flowapp.doctor.Models.AsiOnaylaModel;
import com.sahin.flowapp.doctor.Models.CevaplaModel;
import com.sahin.flowapp.doctor.Models.DuyuruEkleModel;
import com.sahin.flowapp.doctor.Models.DuyuruModel;
import com.sahin.flowapp.doctor.Models.DuyuruSilModel;
import com.sahin.flowapp.doctor.Models.KullaniciSilModel;
import com.sahin.flowapp.doctor.Models.KullanicilarModel;
import com.sahin.flowapp.doctor.Models.PatientIslemTakipModel;
import com.sahin.flowapp.doctor.Models.PetEkleModel;
import com.sahin.flowapp.doctor.Models.PetModel;
import com.sahin.flowapp.doctor.Models.PetSilModel;
import com.sahin.flowapp.doctor.Models.SoruModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @GET("/flowservis/duyuruIdi.php")
    Call<List<DuyuruModel>> getDuyuru();


    @FormUrlEncoded
    @POST("/flowservis/duyuruekle.php")
    Call<DuyuruEkleModel> addDuyuru(@Field("baslik") String baslik, @Field("text") String text, @Field("resim") String resim);


    @FormUrlEncoded
    @POST("/flowservis/duyurusil.php")
    Call<DuyuruSilModel> duyuruSil(@Field("id") String id);

    @FormUrlEncoded
    @POST("/flowservis/doktorislemtakip.php")
    Call<List<PatientIslemTakipModel>> getPatientIslemTakip(@Field("tarih") String tarih);

    @FormUrlEncoded
    @POST("/flowservis/asionayla.php")
    Call<AsiOnaylaModel> asiOnayla(@Field("id") String id);

    @FormUrlEncoded
    @POST("/flowservis/asiiptal.php")
    Call<AsiOnaylaModel> asiIptal(@Field("id") String id);

    @GET("/flowservis/sorular.php")
    Call<List<SoruModel>> getSoru();

    @FormUrlEncoded
    @POST("/flowservis/cevapla.php")
    Call<CevaplaModel> cevapla(@Field("musid") String musid, @Field("soruid") String soruid, @Field("text") String text);

    @GET("/flowservis/kullanicilar.php")
    Call<List<KullanicilarModel>> getKullanicilar();

    @FormUrlEncoded
    @POST("/veterinerservis/petlerim.php")
    Call<List<PetModel>> getPets(@Field("musid") String musid);

    @FormUrlEncoded
    @POST("/flowservis/petekle.php")
    Call<PetEkleModel> petEkle(@Field("musid") String musid, @Field("isim") String isim, @Field("tur") String tur, @Field("cins") String cins, @Field("resim") String resim);


    @FormUrlEncoded
    @POST("/flowservis/asiekle.php")
    Call<AsiEkleModel> asiEkle(@Field("musid") String musid, @Field("petid") String petid, @Field("name") String name, @Field("tarih") String tarih);


    @FormUrlEncoded
    @POST("/flowservis/kullanicisil.php")
    Call<KullaniciSilModel> kadiSil(@Field("id") String id);

    @FormUrlEncoded
    @POST("/flowservis/petsil.php")
    Call<PetSilModel> petSil(@Field("id") String id);


}
