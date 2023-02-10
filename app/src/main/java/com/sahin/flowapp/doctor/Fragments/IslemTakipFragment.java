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

import com.sahin.flowapp.doctor.Adapters.PatientIslemTakipAdapter;
import com.sahin.flowapp.doctor.Models.PatientIslemTakipModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;
import com.sahin.flowapp.doctor.Utils.Warnings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IslemTakipFragment extends Fragment {
    private View view;
    private DateFormat format;
    private Date date;
    private String today;
    private ChangeFragments changeFragments;
    private RecyclerView islemTakipRecylerView;
    private List<PatientIslemTakipModel> list;
    private PatientIslemTakipAdapter patientIslemTakipAdapter;
    private ImageView islemTakipBackImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_islem_takip, container, false);
        tanimlama();
        click();
        istek_At(today);
        return view;
    }

    public void tanimlama() {
        format = new SimpleDateFormat("dd/MM/yyyy");
        date = Calendar.getInstance().getTime();
        today = format.format(date);
        // Log.i("bugününtarih",today);
        islemTakipRecylerView = view.findViewById(R.id.islemTakipRecylerView);
        islemTakipBackImage = view.findViewById(R.id.islemTakipBackImage);
        islemTakipRecylerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        changeFragments = new ChangeFragments(getContext());
        list = new ArrayList<>();

    }
    public void click()
    {
        islemTakipBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void istek_At(String tarih) {
        Call<List<PatientIslemTakipModel>> req = ManagerAll.getInstance().getPatientIslemTakip(tarih);
        req.enqueue(new Callback<List<PatientIslemTakipModel>>() {
            @Override
            public void onResponse(Call<List<PatientIslemTakipModel>> call, Response<List<PatientIslemTakipModel>> response) {
                if (response.body().get(0).isTf()) {
                    Toast.makeText(getContext(), "Bugün " + response.body().size() + " hastanıza işlem yapılacaktır.", Toast.LENGTH_LONG).show();
                    list = response.body();
                    patientIslemTakipAdapter = new PatientIslemTakipAdapter(list, getContext(), getActivity());
                    islemTakipRecylerView.setAdapter(patientIslemTakipAdapter);
                    //Log.i("hasta",response.body().toString());
                } else {
                    Toast.makeText(getContext(), "Bugün işlem yapılacak hasta yoktur.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<PatientIslemTakipModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });

    }


}
