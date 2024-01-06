package com.sahin.flowapp.doctor.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull; // Burada değişiklik
import androidx.cardview.widget.CardView; // Burada değişiklik
import androidx.recyclerview.widget.RecyclerView; // Burada değişiklik
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sahin.flowapp.doctor.Models.DuyuruModel;

import com.sahin.flowapp.doctor.Models.DuyuruSilModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuyuruAdapter extends RecyclerView.Adapter<DuyuruAdapter.ViewHolder> {

    List<DuyuruModel> list;
    Context context;
    Activity activity;

    public DuyuruAdapter(List<DuyuruModel> list, Context context, Activity activitiy) {
        this.list = list;
        this.context = context;
        this.activity = activitiy;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.duyuruitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.duyuruBaslikText.setText(list.get(position).getBaslik());
        holder.duyuruText.setText(list.get(position).getText());

        Picasso.get().load(list.get(position).getResim()).resize(200, 200).into(holder.duyuruImageView);

        holder.duyuruCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duyuruSil(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView duyuruBaslikText, duyuruText;
        ImageView duyuruImageView;
        CardView duyuruCardView;

        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            duyuruBaslikText = itemView.findViewById(R.id.duyuruBaslikText);
            duyuruText = itemView.findViewById(R.id.duyuruText);
            duyuruImageView = itemView.findViewById(R.id.duyuruImageView);
            duyuruCardView = itemView.findViewById(R.id.duyuruCardView);
        }
    }

    public void duyuruSil(final int position) {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = activity.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.duyurusillayout, null);

        Button duyuruSilTamamButon =  (Button)view.findViewById(R.id.duyuruSilTamamButon);
        Button duyuruSilIptalButon =  (Button)view.findViewById(R.id.duyuruSilIptalButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();


        duyuruSilTamamButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                duyuruSil(list.get(position).getId(), position);
                alertDialog.cancel();
            }
        });
        duyuruSilIptalButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void duyuruSil(String id, final int position) {
        Call<DuyuruSilModel> req = ManagerAll.getInstance().duyuruSil(id);
        req.enqueue(new Callback<DuyuruSilModel>() {
            @Override
            public void onResponse(Call<DuyuruSilModel> call, Response<DuyuruSilModel> response) {
                if (response.body().isTf()) {

                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                    deleteToList(position);
                } else {

                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<DuyuruSilModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void deleteToList(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();//silindikden sonra itemlerin indexlerinin yeniden düzenlenmesi yani listenin yenilenmesi için kullandık


    }

}
