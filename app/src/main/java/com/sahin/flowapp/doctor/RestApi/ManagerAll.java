package com.sahin.flowapp.doctor.RestApi;


import com.sahin.flowapp.doctor.Models.AsiEkleModel;
import com.sahin.flowapp.doctor.Models.IslemOnaylaModel;
import com.sahin.flowapp.doctor.Models.CevaplamaModel;
import com.sahin.flowapp.doctor.Models.DuyuruEkleModel;
import com.sahin.flowapp.doctor.Models.DuyuruModel;
import com.sahin.flowapp.doctor.Models.DuyuruSilModel;
import com.sahin.flowapp.doctor.Models.KullaniciSilModel;
import com.sahin.flowapp.doctor.Models.KullanicilarModel;
import com.sahin.flowapp.doctor.Models.PatientIslemTakipModel;
import com.sahin.flowapp.doctor.Models.PetEkleModel;
import com.sahin.flowapp.doctor.Models.PetModel;
import com.sahin.flowapp.doctor.Models.PetSilModel;
import com.sahin.flowapp.doctor.Models.SormaModel;

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

    public Call<IslemOnaylaModel> islemOnayla(String id) {
        Call<IslemOnaylaModel> x = getRestApi().islemOnayla(id);
        return x;
    }

    public Call<IslemOnaylaModel> islemIptal(String id) {
        Call<IslemOnaylaModel> x = getRestApi().islemIptal(id);
        return x;
    }

    public Call<List<SormaModel>> getSorma() {
        Call<List<SormaModel>> x = getRestApi().getSorma();
        return x;
    }

    public Call<CevaplamaModel> cevaplama(String hemid, String soruid, String text) {
        Call<CevaplamaModel> x = getRestApi().cevaplama(hemid, soruid, text);
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
