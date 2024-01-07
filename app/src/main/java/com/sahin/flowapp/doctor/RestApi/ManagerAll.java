package com.sahin.flowapp.doctor.RestApi;


import com.sahin.flowapp.doctor.Models.IslemEkleModel;
import com.sahin.flowapp.doctor.Models.IslemOnaylaModel;
import com.sahin.flowapp.doctor.Models.CevaplamaModel;
import com.sahin.flowapp.doctor.Models.DuyuruEkleModel;
import com.sahin.flowapp.doctor.Models.DuyuruModel;
import com.sahin.flowapp.doctor.Models.DuyuruSilModel;
import com.sahin.flowapp.doctor.Models.KullaniciSilModel;
import com.sahin.flowapp.doctor.Models.KullaniciModel;
import com.sahin.flowapp.doctor.Models.PatientIslemTakipModel;
import com.sahin.flowapp.doctor.Models.PetEkleModel;
import com.sahin.flowapp.doctor.Models.PatientModel;
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

    public Call<List<KullaniciModel>> getKullanicilar() {
        Call<List<KullaniciModel>> x = getRestApi().getKullanicilar();
        return x;
    }
    public Call<List<PatientModel>> getHasta(String id) {
        Call<List<PatientModel>> x = getRestApi().getHasta(id);
        return x;
    }

    public Call<PetEkleModel> petEkle(String musid, String isim, String tur, String cins, String resim) {
        Call<PetEkleModel> x = getRestApi().petEkle(musid, isim, tur, cins, resim);
        return x;
    }

    public Call<IslemEkleModel> asiEkle(String hemid, String hasid, String name, String tarih) {
        Call<IslemEkleModel> x = getRestApi().asiEkle(hemid, hasid, name, tarih);
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
