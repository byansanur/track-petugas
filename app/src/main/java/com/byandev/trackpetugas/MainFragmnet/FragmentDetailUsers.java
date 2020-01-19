package com.byandev.trackpetugas.MainFragmnet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.byandev.trackpetugas.Api.ApiEndPoint;
import com.byandev.trackpetugas.Api.SharedPrefManager;
import com.byandev.trackpetugas.Api.UtilsApi;
import com.byandev.trackpetugas.Main.DetailSosActivity;
import com.byandev.trackpetugas.Main.DetailUsersActivity;
import com.byandev.trackpetugas.Model.DetailSosModel;
import com.byandev.trackpetugas.Model.JamaahDetailModel;
import com.byandev.trackpetugas.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDetailUsers extends Fragment {

  private TextView nama, username, tgllahir, noidentitas, nohp, novisa, nopasspor;
  String sNohp;
  private Button btCall;
  private Context context;
  private ApiEndPoint mApiServices;
  private SharedPrefManager sharedPrefManager;

  private Integer id;

  public FragmentDetailUsers(){

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s){
    View view = inflater.inflate(R.layout.fragment_detail_users, container, false);

    context = getContext();
    mApiServices = UtilsApi.getAPIService();
    sharedPrefManager = new SharedPrefManager(context);

   nama = view.findViewById(R.id.tvNama);
   username = view.findViewById(R.id.tvUserName);
   tgllahir = view.findViewById(R.id.tvTglLahir);
   noidentitas = view.findViewById(R.id.tvKtp);
   nohp = view.findViewById(R.id.tvNoHp);
   novisa = view.findViewById(R.id.tvNovisa);
   nopasspor = view.findViewById(R.id.tvNoPasspor);

    btCall = view.findViewById(R.id.callBtn);
    id = ((DetailUsersActivity) Objects.requireNonNull(getActivity())).getId();
    return view;
  }




  @Override
  public void onResume(){
    super.onResume();
    data();
  }

  @Override
  public void onStart(){
    super.onStart();
    btCall.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String phoneNumber = sNohp;
        Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(dialPhoneIntent);
      }
    });
  }


  private void data() {
    mApiServices.jamaahDetail(id, 3).enqueue(new Callback<JamaahDetailModel>() {
      @Override
      public void onResponse(Call<JamaahDetailModel> call, Response<JamaahDetailModel> response) {
        if (response.isSuccessful()) {
          if (response.body().getApiStatus() == 1) {
            nama.setText(response.body().getData().getNama());
            username.setText(response.body().getData().getUsername());
            tgllahir.setText(response.body().getData().getTglLahir());
            noidentitas.setText(response.body().getData().getNoKtp());
            sNohp = "+62 " + response.body().getData().getNoHp();
            initCallPhone();
            novisa.setText(response.body().getData().getNoVisa());
            nopasspor.setText(response.body().getData().getNoPasspor());
          } else {
            Toast.makeText(context, "Koneksi bermasalah", Toast.LENGTH_LONG).show();
          }
        } else {
          Toast.makeText(context, "Kesalahan server", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<JamaahDetailModel> call, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
      }
    });
//    mApiServices.detailSos(idSos).enqueue(new Callback<DetailSosModel>() {
//      @Override
//      public void onResponse(Call<DetailSosModel> call, Response<DetailSosModel> response) {
//        if (response.isSuccessful()) {
//          if (response.body().getApiStatus() == 1) {
//              nama.setText(response.body().getData().getNama());
//              pesan.setText(response.body().getData().getMessage());
//              noktp.setText(response.body().getData().getNoKtp());
//              sNohp = "+62 "+ response.body().getData().getNoHp();
//              novisa.setText(response.body().getData().getNoVisa());
//              nopasspor.setText(response.body().getData().getNoPasspor());
//              tanggal.setText(response.body().getData().getCreatedAt());
//
//              Log.d("Nomor hp", sNohp);
//            initNoHp();
//
//          } else {
//            Toast.makeText(context, "Koneksi bermasalah", Toast.LENGTH_LONG).show();
//          }
//        } else {
//          Toast.makeText(context, "Kesalahan server", Toast.LENGTH_LONG).show();
//        }
//      }
//
//      @Override
//      public void onFailure(Call<DetailSosModel> call, Throwable t) {
//        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
//      }
//    });
  }

  private void initCallPhone() {
    nohp.setText(sNohp);
  }
}
