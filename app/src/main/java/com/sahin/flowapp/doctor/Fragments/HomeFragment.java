package com.sahin.flowapp.doctor.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;


public class HomeFragment extends Fragment {
    private LinearLayout duyuruLayout, islemTakipLayout, sormaLayout, kullanicilarLayout;
    private View view;
    public ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimlama();
        click_Layout();
        return view;
    }

    public void tanimlama() {

        duyuruLayout = view.findViewById(R.id.duyuruLayout);
        islemTakipLayout = view.findViewById(R.id.islemTakipLayout);
        sormaLayout = view.findViewById(R.id.sormaLayout);
        kullanicilarLayout = view.findViewById(R.id.kullanicilarLayout);
        changeFragments = new ChangeFragments(getContext());
    }

    public void click_Layout() {

        duyuruLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new DuyuruFragment());

            }
        });
        islemTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new IslemTakipFragment());
            }
        });
        sormaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new SormaFragment());
            }
        });
        kullanicilarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new KullanicilarFragment());
            }
        });
    }

}
