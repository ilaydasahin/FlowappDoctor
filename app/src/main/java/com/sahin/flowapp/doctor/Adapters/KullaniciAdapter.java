package com.sahin.flowapp.doctor.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sahin.flowapp.doctor.Fragments.KullaniciPatientFragment;
import com.sahin.flowapp.doctor.Models.KullaniciModel;
import com.sahin.flowapp.doctor.Models.KullaniciSilModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;
import com.sahin.flowapp.doctor.Utils.Warnings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KullaniciAdapter extends RecyclerView.Adapter<KullaniciAdapter.ViewHolder> {

    List<KullaniciModel> list;
    Context context;
    Activity activity;
    private ChangeFragments changeFragments;


    public KullaniciAdapter(List<KullaniciModel> list, Context context, Activity activitiy) {
        this.list = list;
        this.context = context;
        this.activity = activitiy;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.kullaniciitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.kullanicilarIsımText.setText(list.get(position).getKadi().toString());
        holder.userAramaYapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arama(list.get(position).getTelefon().toString());
            }
        });
        holder.userPatientListButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.changeWithParameter(new KullaniciPatientFragment(), list.get(position).getId().toString());
            }
        });
        holder.kullanicilarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kullaniciSilOpenAlert(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kullanicilarIsımText;
        Button userPatientListButon, userAramaYapButon;
        CardView usersCardView;
        LinearLayout kullanicilarLayout;

        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            kullanicilarIsımText = itemView.findViewById(R.id.kullanicilarIsımText);
            userPatientListButon = itemView.findViewById(R.id.userPatientListButon);
            userAramaYapButon = itemView.findViewById(R.id.userAramaYapButon);
            usersCardView = itemView.findViewById(R.id.usersCardView);
            kullanicilarLayout = itemView.findViewById(R.id.kullaniciLayout);
        }
    }

    public void arama(String numara) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("telefon:" + numara));
        activity.startActivity(intent);
    }

    public void kullaniciSilOpenAlert(final int position) {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = activity.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.kullanicisillayout, null);

        Button kullaniciSilIptalButon = view.findViewById(R.id.kullaniciSilIptalButon);
        Button kullaniciSilButon = view.findViewById(R.id.kullaniciSilButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();


        kullaniciSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kullaniciSil(list.get(position).getId().toString(), position);
                alertDialog.cancel();
            }
        });
        kullaniciSilIptalButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void kullaniciSil(String id, final int position) {
        Call<KullaniciSilModel> req = ManagerAll.getInstance().kadiSil(id);
        req.enqueue(new Callback<KullaniciSilModel>() {
            @Override
            public void onResponse(Call<KullaniciSilModel> call, Response<KullaniciSilModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                    deleteToList(position);
                } else {
                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<KullaniciSilModel> call, Throwable t) {
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