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

import com.sahin.flowapp.doctor.Adapters.VeterinerSoruAdapter;
import com.sahin.flowapp.doctor.Models.SoruModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SorularFragment extends Fragment {
    private View view;
    private RecyclerView soruRecyView;
    private List<SoruModel> list;
    private VeterinerSoruAdapter veterinerSoruAdapter;
    private ImageView soruBackImage;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sorular, container, false);
        tanimla();
        click();
        istekAt();
        return view;
    }

    public void tanimla() {
        soruRecyView = view.findViewById(R.id.soruRecyView);
        soruBackImage = view.findViewById(R.id.soruBackImage);
        soruRecyView.setLayoutManager(new GridLayoutManager(getContext(),1));
        list = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());
    }
    public void click(){
        soruBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void istekAt() {
        Call<List<SoruModel>> req = ManagerAll.getInstance().getSoru();
        req.enqueue(new Callback<List<SoruModel>>() {
            @Override
            public void onResponse(Call<List<SoruModel>> call, Response<List<SoruModel>> response) {
                if (response.body().get(0).isTf()) {
                    list = response.body();
                    veterinerSoruAdapter = new VeterinerSoruAdapter(list, getContext(), getActivity());
                    soruRecyView.setAdapter(veterinerSoruAdapter);
                } else {
                    Toast.makeText(getContext(), "Veteriner hekime hiç soru sorulmamıştır.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<SoruModel>> call, Throwable t) {

            }
        });
    }

}
