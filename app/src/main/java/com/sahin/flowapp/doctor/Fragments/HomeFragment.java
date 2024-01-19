package com.sahin.flowapp.doctor.Fragments;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;


public class HomeFragment extends Fragment {
    private LinearLayout duyuruLayout, islemTakipLayout, sormaLayout, kullaniciLayout;
    private View view;
    public ChangeFragments changeFragments;

    private Button btnBarkod;
    private TextView txtbarKodu;

    private ActivityResultLauncher<Intent> launcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimlama();
        click_Layout();
        return view;
    }

    public void tanimlama() {
        Scan_Barcode();
        duyuruLayout = view.findViewById(R.id.duyuruLayout);
        islemTakipLayout = view.findViewById(R.id.islemTakipLayout);
        sormaLayout = view.findViewById(R.id.sormaLayout);
        kullaniciLayout = view.findViewById(R.id.kullaniciLayout);
        changeFragments = new ChangeFragments(getContext());
        btnBarkod=view.findViewById(R.id.btnBarkod);
        txtbarKodu=view.findViewById(R.id.txtBarkodKodu);
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
        kullaniciLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new KullaniciFragment());
            }
        });

        btnBarkod.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Barkod_Izin();
            }
        });
    }

    private void Barkod_Izin(){
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Kullanıcıdan kamera iznini iste
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA}, 10);
        } else {
            Barkod_Oku();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Barkod_Oku();
            } else {
                Toast.makeText(getActivity(), "Kamera izni verilnedi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Barkod_Oku(){
        try {
            // ZXing uygulamasını başlatmak için intent oluştur
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            launcher.launch(intent);

        } catch (Exception e) {
            Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

            Log.d("İŞ",e.getLocalizedMessage());
        }
    }

    private void Scan_Barcode(){

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                // Burada verileri işleyebilirsiniz
                String scannedData = data.getStringExtra("SCAN_RESULT");
                txtbarKodu.setText(scannedData);
            } else {
                Toast.makeText(getActivity(), "Tarama iptal edildi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11) {
            // ZXing uygulamasından dönen veriler
            String scanResult = data.getStringExtra("SCAN_RESULT");
            txtbarKodu.setText(scanResult);
        }
    }

}
