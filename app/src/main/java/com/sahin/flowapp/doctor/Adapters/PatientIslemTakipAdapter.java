package com.sahin.flowapp.doctor.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sahin.flowapp.doctor.Models.IslemOnaylaModel;
import com.sahin.flowapp.doctor.Models.PatientIslemTakipModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientIslemTakipAdapter extends RecyclerView.Adapter<PatientIslemTakipAdapter.ViewHolder> {

    List<PatientIslemTakipModel> list;
    Context context;
    Activity activity;

    public PatientIslemTakipAdapter(List<PatientIslemTakipModel> list, Context context, Activity activitiy) {
        this.list = list;
        this.context = context;
        this.activity = activitiy;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.islemtakiplayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.islemTakipPatientName.setText(list.get(position).getHasisim());
        holder.islemTakipBilgiText.setText(list.get(position).getKadi() + " isimli kullanıcının " + list.get(position).getHasisim() + " isimli hastanızın "
                + list.get(position).getIslemisim() + " işlemi yapılacaktır.");

        Picasso.get().load(list.get(position).getHasresim()).resize(200, 200).into(holder.islemTakipImage);
        holder.islemTakipQrButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arama(list.get(position).getTelefon());
            }
        });
        holder.islemTakipOkButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                islemOnayla(list.get(position).getIslemid(), position);
            }
        });
        holder.islemTakipCancelButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onaylama(list.get(position).getIslemid(), position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView islemTakipPatientName, islemTakipBilgiText;
        ImageView islemTakipOkButon, islemTakipCancelButon, islemTakipQrButon, islemTakipImage;
        // CardView duyuruCardView;

        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            islemTakipPatientName = itemView.findViewById(R.id.islemTakipPatientName);
            islemTakipBilgiText = itemView.findViewById(R.id.islemTakipBilgiText);
            islemTakipOkButon = itemView.findViewById(R.id.islemTakipOkButon);
            islemTakipCancelButon = itemView.findViewById(R.id.islemTakipCancelButon);
            islemTakipQrButon = itemView.findViewById(R.id.islemTakipQrButon);
            islemTakipImage = itemView.findViewById(R.id.islemTakipImage);
        }
    }


    public void deleteToList(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();//silindikden sonra itemlerin indexlerinin yeniden düzenlenmesi yani listenin yenilenmesi için kullandık


    }

    public void arama(String tel) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + tel));
        activity.startActivity(intent);
    }

    public void islemOnayla(String id, final int position) {
        Call<IslemOnaylaModel> req = ManagerAll.getInstance().islemOnayla(id);
        req.enqueue(new Callback<IslemOnaylaModel>() {
            @Override
            public void onResponse(Call<IslemOnaylaModel> call, Response<IslemOnaylaModel> response) {
                Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                deleteToList(position);
            }

            @Override
            public void onFailure(Call<IslemOnaylaModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onaylama(String id, final int position) {
        Call<IslemOnaylaModel> req = ManagerAll.getInstance().islemIptal(id);
        req.enqueue(new Callback<IslemOnaylaModel>() {
            @Override
            public void onResponse(Call<IslemOnaylaModel> call, Response<IslemOnaylaModel> response) {
                Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                deleteToList(position);
            }

            @Override
            public void onFailure(Call<IslemOnaylaModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

}
