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

import com.sahin.flowapp.doctor.Adapters.SormaAdapter;
import com.sahin.flowapp.doctor.Models.SormaModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SormaFragment extends Fragment {
    private View view;
    private RecyclerView sormaRecyView;
    private List<SormaModel> liste;
    private SormaAdapter sormaAdapter;
    private ImageView sormaBackImage;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sorma, container, false);
        tanimlama();
        click();
        istek_At();
        return view;
    }

    public void tanimlama() {
        sormaRecyView = view.findViewById(R.id.sormaRecyView);
        sormaBackImage = view.findViewById(R.id.sormaBackImage);
        sormaRecyView.setLayoutManager(new GridLayoutManager(getContext(),1));
        liste = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());
    }
    public void click(){
        sormaBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void istek_At() {
        Call<List<SormaModel>> req = ManagerAll.getInstance().getSorma();
        req.enqueue(new Callback<List<SormaModel>>() {
            @Override
            public void onResponse(Call<List<SormaModel>> call, Response<List<SormaModel>> response) {
                if (response.body().get(0).isTf()) {
                    liste = response.body();
                    sormaAdapter = new SormaAdapter(liste, getContext(), getActivity());
                    sormaRecyView.setAdapter(sormaAdapter);
                } else {
                    Toast.makeText(getContext(), "hekime hiç soru sorulmamıştır.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<SormaModel>> call, Throwable t) {

            }
        });
    }

}
