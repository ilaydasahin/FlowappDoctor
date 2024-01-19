package com.sahin.flowapp.doctor.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sahin.flowapp.doctor.Models.IslemEkleModel;
import com.sahin.flowapp.doctor.Models.PatientModel;
import com.sahin.flowapp.doctor.Models.PatientSilModel;
import com.sahin.flowapp.doctor.R;
import com.sahin.flowapp.doctor.RestApi.ManagerAll;
import com.sahin.flowapp.doctor.Utils.ChangeFragments;
import com.sahin.flowapp.doctor.Utils.Warnings;
import com.squareup.picasso.Picasso;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    List<PatientModel> list;
    Context context;
    Activity activity;
    private final ChangeFragments changeFragments;
    private final String hemid;
    private String tarih = "", formatliTarih = "";

    public PatientAdapter(List<PatientModel> list, Context context, Activity activitiy, String hemid) {
        this.list = list;
        this.context = context;
        this.activity = activitiy;
        this.hemid = hemid;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.userpatientitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.patientNameText.setText(list.get(position).getHasisim().toString());
        holder.patientBilgiText.setText(list.get(position).getHasisim()+" hastanızı silmek için buraya tıklayın");
        holder.patientBilgiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petSilOpenAlert(position);
            }
        });
        holder.patientIslemEkleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIslemEkle(list.get(position).getHasid().toString());
            }
        });

        Picasso.get().load(list.get(position).getHasresim().toString()).resize(200, 200).into(holder.patientImage);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientBilgiText, patientNameText;
        LinearLayout patientIslemEkleLayout;
        ImageView patientImage;


        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            patientBilgiText = itemView.findViewById(R.id.patientBilgiText);
            patientNameText = itemView.findViewById(R.id.patientNameText);
            patientIslemEkleLayout = itemView.findViewById(R.id.patientIslemEkleLayout);
            patientImage = itemView.findViewById(R.id.patientImage);
        }
    }

    public void addIslemEkle(final String hasid) {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = activity.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.islemeklelayout, null);

        CalendarView calendarView = view.findViewById(R.id.takvim);
        final EditText islemEkleIslemName = view.findViewById(R.id.islemEkleIslemName);

        Button islemEkleButon = view.findViewById(R.id.islemEkleButon);


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                tarih = i2 + "/" + i1 + "/" + i;

                try {
                    Date date = inputFormat.parse(tarih);//ilk string i date e sonra date i tekrar string e cevirdik!
                    formatliTarih = format.format(date);//başlara 0 eklenmesi için db ile eşleşme dogru olsun diye yapıyoruz
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });
        islemEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!formatliTarih.equals("") && !islemEkleIslemName.getText().toString().equals("")) {
                    addIslem(hemid, hasid, islemEkleIslemName.getText().toString(), formatliTarih, alertDialog);
                } else {
                    Toast.makeText(context, "Tarih seçiniz veya işlem ismini giriniz", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.show();

    }

    public void addIslem(String hemid, String hasid, String name, String tarih, final AlertDialog alertDialog) {

        Call<IslemEkleModel> req = ManagerAll.getInstance().islemEkle(hemid, hasid, name, tarih);
        req.enqueue(new Callback<IslemEkleModel>() {
            @Override
            public void onResponse(Call<IslemEkleModel> call, Response<IslemEkleModel> response) {
                if (response.body().isTf()) {

                    alertDialog.cancel();
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                } else {
                    alertDialog.cancel();
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<IslemEkleModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void petSilOpenAlert(final int position) {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = activity.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.patientsillayout, null);

        Button patientSilIptalButon = view.findViewById(R.id.patientSilIptalButon);
        Button patientSilButon = view.findViewById(R.id.patientSilButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();


        patientSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                patientSil(list.get(position).getHasid().toString(), position);
                alertDialog.cancel();
            }
        });
        patientSilIptalButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void patientSil(String id, final int position) {
        Call<PatientSilModel> req = ManagerAll.getInstance().patientSil(id);
        req.enqueue(new Callback<PatientSilModel>() {
            @Override
            public void onResponse(Call<PatientSilModel> call, Response<PatientSilModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                    deleteToList(position);
                } else {
                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<PatientSilModel> call, Throwable t) {
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