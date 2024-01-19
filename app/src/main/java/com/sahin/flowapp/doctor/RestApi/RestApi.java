package com.sahin.flowapp.doctor.RestApi;


import com.sahin.flowapp.doctor.Models.IslemEkleModel;
import com.sahin.flowapp.doctor.Models.IslemOnaylaModel;
import com.sahin.flowapp.doctor.Models.CevaplamaModel;
import com.sahin.flowapp.doctor.Models.DuyuruEkleModel;
import com.sahin.flowapp.doctor.Models.DuyuruModel;
import com.sahin.flowapp.doctor.Models.DuyuruSilModel;
import com.sahin.flowapp.doctor.Models.KullaniciModel;
import com.sahin.flowapp.doctor.Models.KullaniciSilModel;
import com.sahin.flowapp.doctor.Models.PatientIslemTakipModel;
import com.sahin.flowapp.doctor.Models.PatientModel;
import com.sahin.flowapp.doctor.Models.PatientEkleModel;
import com.sahin.flowapp.doctor.Models.PatientSilModel;
import com.sahin.flowapp.doctor.Models.SormaModel;

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
    Call<DuyuruSilModel> duyuruSil(@Field("id") String duyid);

    @FormUrlEncoded
    @POST("/flowservis/doktorislemtakip.php")
    Call<List<PatientIslemTakipModel>> getPatientIslemTakip(@Field("tarih") String tarih);

    @FormUrlEncoded
    @POST("/flowservis/islemonayla.php")
    Call<IslemOnaylaModel> islemOnayla(@Field("id") String id);

    @FormUrlEncoded
    @POST("/flowservis/islemiptal.php")
    Call<IslemOnaylaModel> islemIptal(@Field("id") String id);

    @GET("/flowservis/sorma.php")
    Call<List<SormaModel>> getSorma();

    @FormUrlEncoded
    @POST("/flowservis/cevaplama.php")
    Call<CevaplamaModel> cevaplama(@Field("hemid") String hemid, @Field("soruid") String soruid, @Field("text") String text);

    @GET("/flowservis/kullanici.php")
    Call<List<KullaniciModel>> getKullanicilar();


    @FormUrlEncoded
    @POST("/flowservis/hastalarim.php")
    Call<List<PatientModel>> getHasta(@Field("hemid") String id);




    //
    @FormUrlEncoded
    @POST("/flowservis/patientekle.php")
    Call<PatientEkleModel> patientEkle(@Field("hemid") String hemid,@Field("isim") String isim, @Field("tur") String tur, @Field("cins") String cins, @Field("resim") String resim);

//
    @FormUrlEncoded
    @POST("/flowservis/islemekle.php")
    Call<IslemEkleModel> islemEkle(@Field("hemid") String hemid, @Field("hasid") String hasid, @Field("name") String name, @Field("tarih") String tarih);

//
    //
    //
    @FormUrlEncoded
    @POST("/flowservis/kullanicisil.php")
    Call<KullaniciSilModel> kadiSil(@Field("id") String id);

    @FormUrlEncoded
    @POST("/flowservis/patientsil.php")
    Call<PatientSilModel> patientSil(@Field("id") String id);


}
