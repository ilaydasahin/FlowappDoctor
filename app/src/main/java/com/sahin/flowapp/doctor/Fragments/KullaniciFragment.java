package com.sahin.flowapp.doctor.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sahin.flowapp.doctor.Adapters.KullaniciAdapter;
import com.sahin.flowapp.doctor.Models.KullaniciModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;
import com.sahin.flowapp.doctor.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KullaniciFragment extends Fragment {
    private View view;
    private ChangeFragments changeFragments;
    private RecyclerView kullanicilarinRecyView;
    private List<KullaniciModel> liste;
    private ImageView kullanicilarBackImage;
    private KullaniciAdapter kullaniciAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kullanicilar, container, false);
        tanimlama();
        click();
        getKullanicilar();
        return view;
    }

    public void tanimlama() {
        changeFragments = new ChangeFragments(getContext());
        kullanicilarinRecyView = view.findViewById(R.id.kullanicilarinRecyView);
        kullanicilarBackImage = view.findViewById(R.id.kullanicilarBackImage);
        kullanicilarinRecyView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        liste = new ArrayList<>();
    }
    public void click()
    {
        kullanicilarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void getKullanicilar() {
        Call<List<KullaniciModel>> req = ManagerAll.getInstance().getKullanicilar();
        req.enqueue(new Callback<List<KullaniciModel>>() {
            @Override
            public void onResponse(Call<List<KullaniciModel>> call, Response<List<KullaniciModel>> response) {
                if (response.body().get(0).isTf()) {

                    //Log.i("kullanici",response.body().toString());
                    liste = response.body();
                    kullaniciAdapter = new KullaniciAdapter(liste, getContext(), getActivity());
                    kullanicilarinRecyView.setAdapter(kullaniciAdapter);


                } else {
                    Toast.makeText(getContext(), "Sisteme kayıtlı kullanıcı yoktur.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KullaniciModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

}