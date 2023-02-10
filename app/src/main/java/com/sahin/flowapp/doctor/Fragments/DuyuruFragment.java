package com.sahin.flowapp.doctor.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sahin.flowapp.doctor.Adapters.DuyuruAdapter;
import com.sahin.flowapp.doctor.Models.DuyuruEkleModel;
import com.sahin.flowapp.doctor.Models.DuyuruModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;
import com.sahin.flowapp.doctor.Utils.Warnings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DuyuruFragment extends Fragment {
    private View view;
    private RecyclerView duyuruRecView;
    private List<DuyuruModel> duyuruList;
    private DuyuruAdapter duyuruAdapter;
    private ChangeFragments changeFragments;
    private Button duyuruEkle;
    private ImageView duyuruEkleImageView, duyuruBackImage;
    private Bitmap bitmap;
    private String imageString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_duyuru, container, false);
        tanimlama();
        getDuyuru();
        click();
        return view;
    }

    public void tanimlama() {
       // duyuruRecView =(RecyclerView) view.findViewById(R.id.duyuruRecView);
      //  duyuruRecView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        duyuruRecView =(RecyclerView) view.findViewById(R.id.duyuruRecView);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        duyuruRecView.setLayoutManager(mng);
        duyuruEkle = view.findViewById(R.id.duyuruEkle);
        duyuruBackImage = view.findViewById(R.id.duyuruBackImage);
        duyuruList = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());
        bitmap = null;
        imageString = "";

    }

    public void click() {
        duyuruEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDuyuru();
            }
        });
        duyuruBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });

    }

    public void getDuyuru() {

        Call<List<DuyuruModel>> req = ManagerAll.getInstance().getDuyuru();
        req.enqueue(new Callback<List<DuyuruModel>>() {
            @Override
            public void onResponse(Call<List<DuyuruModel>> call, Response<List<DuyuruModel>> response) {
                if (response.body().get(0).isTf()) {
                    duyuruList = response.body();
                    duyuruAdapter = new DuyuruAdapter(duyuruList, getContext(),getActivity());
                    duyuruRecView.setAdapter(duyuruAdapter);

                } else {
                    Toast.makeText(getContext(), "Herhangi bir duyuru bulunmamaktadır...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<DuyuruModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void addDuyuru() {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = this.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.duyurueklelayout, null);
        Button duyuruEkleButon = view.findViewById(R.id.duyuruEkleButon);
        Button duyuruImageEkleButon = view.findViewById(R.id.duyuruImageEkleButon);
        duyuruEkleImageView = view.findViewById(R.id.duyuruEkleImageView);
        final EditText duyuruIcerikEditText = view.findViewById(R.id.duyuruIcerikEditText);
        final EditText duyurubaslikEditText = view.findViewById(R.id.duyurubaslikEditText);


        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();

        duyuruImageEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resimleriAc();
            }
        });
        duyuruEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!imageToString().equals("") && !duyurubaslikEditText.getText().toString().equals("") && !duyuruIcerikEditText.getText().toString().equals("")) {

                    duyuruEkle(duyurubaslikEditText.getText().toString(), duyuruIcerikEditText.getText().toString(),
                            imageToString(), alertDialog);
                    duyurubaslikEditText.setText("");
                    duyuruIcerikEditText.setText("");

                } else {
                    Toast.makeText(getContext(), "Tüm alanların doldurulması ve resmin seçilmesi zorunludur", Toast.LENGTH_LONG).show();

                }
            }
        });

        alertDialog.show();

    }

    public void resimleriAc() {

        Intent intent = new Intent();
        intent.setType("image/*");//galeri açtırır daha sonra intent e action verelim
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 777);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 &&data != null) {


            Uri path = data.getData();
            try {
               // Log.i("sonuc",""+bitmap);
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                duyuruEkleImageView.setImageBitmap(bitmap);
                duyuruEkleImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String imageToString() {
        if (bitmap != null) {


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byt = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(byt, Base64.DEFAULT);
            return imageString;
        } else
            return imageString;
    }

    public void duyuruEkle(String baslik, String icerik, String imageString, final AlertDialog alertDialog) {
        Call<DuyuruEkleModel> req = ManagerAll.getInstance().addDuyuru(baslik, icerik, imageString);
        req.enqueue(new Callback<DuyuruEkleModel>() {
            @Override
            public void onResponse(Call<DuyuruEkleModel> call, Response<DuyuruEkleModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(getContext(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                    getDuyuru();
                    alertDialog.cancel();
                } else {
                    Toast.makeText(getContext(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<DuyuruEkleModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });

    }
}
