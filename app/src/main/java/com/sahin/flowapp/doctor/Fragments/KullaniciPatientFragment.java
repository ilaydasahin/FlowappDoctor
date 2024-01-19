package com.sahin.flowapp.doctor.Fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sahin.flowapp.doctor.Adapters.PatientAdapter;
import com.sahin.flowapp.doctor.Models.PatientEkleModel;
import com.sahin.flowapp.doctor.Models.PatientModel;
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


public class KullaniciPatientFragment extends Fragment {
    private View view;
    private String hemid;
    private ChangeFragments changeFragments;
    private RecyclerView userPatientListeRecView;
    private ImageView patientEkleResimYok, patientEkleImageView,kullaniciBackImage;
    private Button userPatientEkle;
    private TextView patientEkleUyariText;
    private List<PatientModel> liste;
    private PatientAdapter patientAdapter;
    private Bitmap bitmap;
    private String imageString="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kullanici, container, false);
        tanimlama();
        getHasta(hemid);
        click();
        return view;
    }

    public void tanimlama() {
        hemid = getArguments().getString("userid").toString();
        changeFragments = new ChangeFragments(getContext());
        userPatientListeRecView = view.findViewById(R.id.userPatientListeRecView);
        userPatientListeRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        patientEkleResimYok = view.findViewById(R.id.patientEkleResimYok);
        patientEkleUyariText = view.findViewById(R.id.patientEkleUyariText);
        userPatientEkle = view.findViewById(R.id.userPatientEkle);
        liste = new ArrayList<>();
        bitmap = null;
        //Log.i("gelen",hemid):
    }

    public void click() {
        userPatientEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientEkleAlert();
            }
        });
        patientEkleUyariText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new KullaniciFragment());
            }
        });

    }

    public void getHasta(String id) {
        Call<List<PatientModel>> req = ManagerAll.getInstance().getHasta(id);
        req.enqueue(new Callback<List<PatientModel>>() {
            @Override
            public void onResponse(Call<List<PatientModel>> call, Response<List<PatientModel>> response) {
                if (response.body().get(0).isTf()) {
                    userPatientListeRecView.setVisibility(View.VISIBLE);
                    patientEkleResimYok.setVisibility(View.GONE);
                    patientEkleUyariText.setVisibility(View.GONE);
                    liste = response.body();
                    patientAdapter = new PatientAdapter(liste, getContext(), getActivity(), hemid);
                    userPatientListeRecView.setAdapter(patientAdapter);

                    Toast.makeText(getContext(), "Kullanıcıya ait " + response.body().size() + " tane hasta bulunmuştur.", Toast.LENGTH_LONG).show();


                } else {

                    Toast.makeText(getContext(), "Kullanıcıya ait hasta bulunamamıştır. ", Toast.LENGTH_LONG).show();
                    patientEkleResimYok.setVisibility(View.VISIBLE);
                    patientEkleUyariText.setVisibility(View.VISIBLE);
                    userPatientListeRecView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PatientModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });

    }



    public void patientEkleAlert() {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = this.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.patienteklelayout, null);
        Button patientEkleResimSecButon = view.findViewById(R.id.patientEkleResimSecButon);
        Button patientEkleEkleButon = view.findViewById(R.id.patientEkleEkleButon);
        patientEkleImageView = view.findViewById(R.id.patientEkleImageView);
        final EditText patientEkleNameEditText = view.findViewById(R.id.patientEkleNameEditText);
        final EditText patientEkleTurEditText = view.findViewById(R.id.patientEkleTurEditText);
        final EditText patientEkleCinsEditText = view.findViewById(R.id.patientEkleCinsEditText);


        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();

        patientEkleResimSecButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galeriAc();
            }
        });
        patientEkleEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!imageToString().equals("") && !patientEkleNameEditText.getText().toString().equals("") && !patientEkleTurEditText.getText().toString().equals("")
                        && !imageToString().equals("") && !patientEkleCinsEditText.getText().toString().equals("")) {

                    patientEkle(hemid, patientEkleNameEditText.getText().toString(), patientEkleTurEditText.getText().toString(),
                            patientEkleCinsEditText.getText().toString(), imageToString(), alertDialog);
                    patientEkleNameEditText.setText("");
                    patientEkleTurEditText.setText("");
                    patientEkleCinsEditText.setText("");

                } else {
                    Toast.makeText(getContext(), "Tüm alanların doldurulması ve resmin seçilmesi zorunludur", Toast.LENGTH_LONG).show();

                }
            }
        });

        alertDialog.show();

    }

    public void galeriAc() {

        Intent ıntent = new Intent();
        ıntent.setType("image/*");//fotoğraflsr açtırır daha sonra ıntent e action verelim
        ıntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(ıntent, 777);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && data != null) {


            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                patientEkleImageView.setImageBitmap(bitmap);
                patientEkleImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void patientEkle(final String hemid, String patientismi, String patienttur, String patientcins, String imageString, final AlertDialog alertDialog) {
        Call<PatientEkleModel> req = ManagerAll.getInstance().patientEkle(hemid, patientismi, patienttur, patientcins, imageString);
        req.enqueue(new Callback<PatientEkleModel>() {
            @Override
            public void onResponse(Call<PatientEkleModel> call, Response<PatientEkleModel> response) {

                if (response.body().isTf()) {
                    getHasta(hemid);
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                } else {
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();

                }
            }

            @Override
            public void onFailure(Call<PatientEkleModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

}
