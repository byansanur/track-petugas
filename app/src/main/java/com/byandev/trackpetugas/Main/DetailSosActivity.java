package com.byandev.trackpetugas.Main;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.byandev.trackpetugas.Api.ApiEndPoint;
import com.byandev.trackpetugas.Api.SharedPrefManager;
import com.byandev.trackpetugas.Api.UtilsApi;
import com.byandev.trackpetugas.MainFragmnet.FragmentDetailSos;
import com.byandev.trackpetugas.R;

public class DetailSosActivity extends AppCompatActivity {

  private Context context;
  private ApiEndPoint mApiServices;
  SharedPrefManager sharedPrefManager;

  public Integer getIdSos() {
    return idSos;
  }

  private Integer idSos;


  private Toolbar toolbar;
//  private TextView tanggal, nama, pesan, noktp, nohp, novisa, nopasspor;

//  private DetailSosModel detailSosModel = new DetailSosModel();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_sos);

    context = this;
    mApiServices = UtilsApi.getAPIService();
    sharedPrefManager = new SharedPrefManager(context);

    toolbar = findViewById(R.id.toolbar);
    idSos = getIntent().getIntExtra("idSos", 0);

    toolbar.setTitle("Detail");

    loadFragment(new FragmentDetailSos());

  }

  private void loadFragment(FragmentDetailSos fragment) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.frame_container, fragment);
    ft.addToBackStack(null);
    ft.commitAllowingStateLoss();
  }


  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

}
