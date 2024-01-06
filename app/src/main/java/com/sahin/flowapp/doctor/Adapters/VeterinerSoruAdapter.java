package com.sahin.flowapp.doctor.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sahin.flowapp.doctor.Models.CevaplaModel;
import com.sahin.flowapp.doctor.Models.SoruModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.Warnings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VeterinerSoruAdapter extends RecyclerView.Adapter<VeterinerSoruAdapter.ViewHolder> {

    List<SoruModel> list;
    Context context;
    Activity activity;

    public VeterinerSoruAdapter(List<SoruModel> list, Context context, Activity activitiy) {
        this.list = list;
        this.context = context;
        this.activity = activitiy;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.sorularitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.soruKullaniciText.setText(list.get(position).getKadi());
        holder.soruSoruText.setText(list.get(position).getSoru());
        holder.soruAramaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ara(list.get(position).getTelefon());
            }
        });
        holder.soruCevaplaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cevaplaAlert(list.get(position).getMusid(), list.get(position).getSoruid(), position,
                        list.get(position).getSoru());
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView soruKullaniciText, soruSoruText;
        ImageView soruCevaplaButon, soruAramaButon;
        // CardView duyuruCardView;

        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            soruKullaniciText = itemView.findViewById(R.id.soruKullaniciText);
            soruSoruText = itemView.findViewById(R.id.soruSoruText);
            soruCevaplaButon = itemView.findViewById(R.id.soruCevaplaButon);
            soruAramaButon = itemView.findViewById(R.id.soruAramaButon);

        }
    }


    public void deleteToList(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();//silindikden sonra itemlerin indexlerinin yeniden düzenlenmesi yani listenin yenilenmesi için kullandık


    }

    public void ara(String numara) {
        Intent ıntent = new Intent(Intent.ACTION_VIEW);
        ıntent.setData(Uri.parse("tel:" + numara));
        activity.startActivity(ıntent);
    }

    public void cevaplaAlert(final String musid, final String soruid, final int position, String soru) {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = activity.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.cevaplaalertlayout, null);

        final EditText cevaplaEditText = view.findViewById(R.id.cevaplaEditText);
        MaterialButton cevaplaButon = (MaterialButton) view.findViewById(R.id.cevaplaButon);
        TextView cevaplanacakSoruText = view.findViewById(R.id.cevaplanacakSoruText);
        cevaplanacakSoruText.setText(soru);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();

        cevaplaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cevap = cevaplaEditText.getText().toString();
                cevaplaEditText.setText("");

                alertDialog.cancel();
                cevapla(musid, soruid, cevap, alertDialog, position);
            }
        });
        alertDialog.show();

    }

    public void cevapla(String musid, String soruid, String text, final AlertDialog alertDialog, final int position) {
        Call<CevaplaModel> req = ManagerAll.getInstance().cevapla(musid, soruid, text);
        req.enqueue(new Callback<CevaplaModel>() {
            @Override
            public void onResponse(Call<CevaplaModel> call, Response<CevaplaModel> response) {

                if (response.body().isTf()) {
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                    deleteToList(position);
                } else {
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<CevaplaModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }


}
