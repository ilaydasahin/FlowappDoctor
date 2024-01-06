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

public class ManagerAll extends BaseManager {

    private static final ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance() {
        return ourInstance;
    }

    public Call<List<DuyuruModel>> getDuyuru() {
        Call<List<DuyuruModel>> x = getRestApi().getDuyuru();
        return x;
    }

    public Call<DuyuruEkleModel> addDuyuru(String baslik, String icerik, String imageString) {
        Call<DuyuruEkleModel> x = getRestApi().addDuyuru(baslik, icerik, imageString);
        return x;
    }

    public Call<DuyuruSilModel> duyuruSil(String id) {
        Call<DuyuruSilModel> x = getRestApi().duyuruSil(id);
        return x;
    }

    public Call<List<PatientIslemTakipModel>> getPatientIslemTakip(String tarih) {
        Call<List<PatientIslemTakipModel>> x = getRestApi().getPatientIslemTakip(tarih);
        return x;
    }

    public Call<AsiOnaylaModel> asiOnayla(String id) {
        Call<AsiOnaylaModel> x = getRestApi().asiOnayla(id);
        return x;
    }

    public Call<AsiOnaylaModel> asiIptal(String id) {
        Call<AsiOnaylaModel> x = getRestApi().asiIptal(id);
        return x;
    }

    public Call<List<SoruModel>> getSoru() {
        Call<List<SoruModel>> x = getRestApi().getSoru();
        return x;
    }

    public Call<CevaplaModel> cevapla(String musid, String soruid, String text) {
        Call<CevaplaModel> x = getRestApi().cevapla(musid, soruid, text);
        return x;
    }

    public Call<List<KullanicilarModel>> getKullanicilar() {
        Call<List<KullanicilarModel>> x = getRestApi().getKullanicilar();
        return x;
    }

    public Call<List<PetModel>> getPets(String musid) {
        Call<List<PetModel>> x = getRestApi().getPets(musid);
        return x;
    }

    public Call<PetEkleModel> petEkle(String musid, String isim, String tur, String cins, String resim) {
        Call<PetEkleModel> x = getRestApi().petEkle(musid, isim, tur, cins, resim);
        return x;
    }

    public Call<AsiEkleModel> asiEkle(String musid, String petid, String name, String tarih) {
        Call<AsiEkleModel> x = getRestApi().asiEkle(musid, petid, name, tarih);
        return x;
    }

    public Call<KullaniciSilModel> kadiSil(String id) {
        Call<KullaniciSilModel> x = getRestApi().kadiSil(id);
        return x;
    }

    public Call<PetSilModel> petSil(String id) {
        Call<PetSilModel> x = getRestApi().petSil(id);
        return x;
    }

}
