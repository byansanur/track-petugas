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

import com.byandev.trackpetugas.Main.DetailSosActivity;
import com.byandev.trackpetugas.Model.SosListModel;
import com.byandev.trackpetugas.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterSosList extends RecyclerView.Adapter<AdapterSosList.Holder> {
  private Context context;
  private ArrayList<SosListModel.Sos>list;
  public AdapterSosList(Context context, List<SosListModel.Sos>ls){
    this.context = context;
    this.list = (ArrayList<SosListModel.Sos>) ls;

  }
  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sos,null);

    return new Holder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    final SosListModel.Sos soslist = list.get(position);
    holder.nama.setText(soslist.getNama());
    holder.tgl.setText(soslist.getCreatedAt());
    holder.cardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent a = new Intent(context, DetailSosActivity.class);
        a.putExtra("idSos", soslist.getId());
        context.startActivity(a);
      }
    });

  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class Holder extends RecyclerView.ViewHolder {
    TextView nama,tgl;
    CardView cardView;
    public Holder(@NonNull View itemView) {
      super(itemView);
      nama = itemView.findViewById(R.id.tvNama);
      tgl = itemView.findViewById(R.id.tvCreatedAt);
      cardView = itemView.findViewById(R.id.card);
    }
  }
}
