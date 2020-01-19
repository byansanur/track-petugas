package com.byandev.trackpetugas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.byandev.trackpetugas.Api.ApiEndPoint;
import com.byandev.trackpetugas.Api.SharedPrefManager;
import com.byandev.trackpetugas.Api.UtilsApi;
import com.byandev.trackpetugas.Main.DetailRekom;
import com.byandev.trackpetugas.Model.RekomModel;
import com.byandev.trackpetugas.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterRekomListHotel extends RecyclerView.Adapter<AdapterRekomListHotel.ViewHolder> {

  private ArrayList<RekomModel.Rekom> list;
  private Context context;
  ApiEndPoint mApiService;
  SharedPrefManager sharedPrefManager;

  public AdapterRekomListHotel(Context context, List<RekomModel.Rekom> rekomList){
    this.context = context;
    this.list = (ArrayList<RekomModel.Rekom>) rekomList;
  }

  @NonNull
  @Override
  public AdapterRekomListHotel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rekom, null);
    mApiService = UtilsApi.getAPIService();
    sharedPrefManager = new SharedPrefManager(context);
    return new AdapterRekomListHotel.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull AdapterRekomListHotel.ViewHolder holder, int position) {
    final RekomModel.Rekom rekom = list.get(position);
    holder.nama.setText(rekom.getNama());
//    holder.alamat.setText(rekom.getAlamat());
    holder.rating.setText(rekom.getRating());
    holder.kategori.setText(rekom.getTypeRekom());
    holder.cardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //intent detail
        Intent a = new Intent(context, DetailRekom.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        a.putExtra("id", rekom.getId());
        context.startActivity(a);
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView nama, rating, kategori;
    CardView cardView;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      cardView = itemView.findViewById(R.id.card);
      nama = itemView.findViewById(R.id.tvNama);
//      alamat = itemView.findViewById(R.id.tvAlamat);
      rating = itemView.findViewById(R.id.tvRating);
      kategori = itemView.findViewById(R.id.kategori);
    }
  }
}
