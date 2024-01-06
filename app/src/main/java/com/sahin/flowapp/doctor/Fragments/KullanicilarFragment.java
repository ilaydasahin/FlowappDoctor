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

import com.sahin.flowapp.doctor.Adapters.UserAdapter;
import com.sahin.flowapp.doctor.Models.KullanicilarModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;
import com.sahin.flowapp.doctor.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KullanicilarFragment extends Fragment {
    private View view;
    private ChangeFragments changeFragments;
    private RecyclerView kullanicilarRecyView;
    private List<KullanicilarModel> list;
    private ImageView kullanicilarBackImage;
    private UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kullanicilar, container, false);
        tanimla();
        click();
        getKullanicilar();
        return view;
    }

    public void tanimla() {
        changeFragments = new ChangeFragments(getContext());
        kullanicilarRecyView = view.findViewById(R.id.kullanicilarRecyView);
        kullanicilarBackImage = view.findViewById(R.id.kullanicilarBackImage);
        kullanicilarRecyView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        list = new ArrayList<>();
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
        Call<List<KullanicilarModel>> req = ManagerAll.getInstance().getKullanicilar();
        req.enqueue(new Callback<List<KullanicilarModel>>() {
            @Override
            public void onResponse(Call<List<KullanicilarModel>> call, Response<List<KullanicilarModel>> response) {
                if (response.body().get(0).isTf()) {
                    list = response.body();
                    userAdapter = new UserAdapter(list, getContext(), getActivity());
                    kullanicilarRecyView.setAdapter(userAdapter);


                } else {
                    Toast.makeText(getContext(), "Sisteme kayıtlı kullanıcı yoktur.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KullanicilarModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

}
