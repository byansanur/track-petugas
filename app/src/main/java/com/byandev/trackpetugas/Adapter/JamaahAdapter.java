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

import com.byandev.trackpetugas.Main.DetailUsersActivity;
import com.byandev.trackpetugas.Model.JamaahListModel;
import com.byandev.trackpetugas.R;

import java.util.ArrayList;
import java.util.List;

public class JamaahAdapter extends RecyclerView.Adapter<JamaahAdapter.Holder> {
  Context context;
  private ArrayList<JamaahListModel.Jamaah> ls;

  public JamaahAdapter(Context context, List<JamaahListModel.Jamaah> list) {
    this.context = context;
    this.ls = (ArrayList<JamaahListModel.Jamaah>) list;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jamaah, null);
    return new JamaahAdapter.Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    final JamaahListModel.Jamaah list = ls.get(position);
    holder.nama.setText(list.getNama());
    holder.nohp.setText("+62 "+list.getNoHp());
    holder.cardList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // intent detail
        Intent a  = new Intent(context, DetailUsersActivity.class);
        a.putExtra("id", list.getId());
        context.startActivity(a);
      }
    });
  }

  @Override
  public int getItemCount() {
    return ls.size();
  }

  public class Holder extends RecyclerView.ViewHolder {
    TextView nama, nohp;
    CardView cardList;
    public Holder(@NonNull View itemView) {
      super(itemView);
      nama = itemView.findViewById(R.id.tvNama);
      nohp = itemView.findViewById(R.id.tvNoHp);
      cardList = itemView.findViewById(R.id.cardList);
    }
  }
}
