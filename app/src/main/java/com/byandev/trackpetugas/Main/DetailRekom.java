package com.byandev.trackpetugas.Main;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.byandev.trackpetugas.Api.ApiEndPoint;
import com.byandev.trackpetugas.Api.SharedPrefManager;
import com.byandev.trackpetugas.Api.UtilsApi;
import com.byandev.trackpetugas.MainFragmnet.FragmentDetailRekom;
import com.byandev.trackpetugas.R;


public class DetailRekom extends AppCompatActivity {

  private Context context;
  ApiEndPoint mApiServices;
  SharedPrefManager sharedPrefManager;

  private Toolbar toolbar;

  private Integer id;
  public Integer getId() {
    return id;
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_rekom);

    context = this;
    mApiServices = UtilsApi.getAPIService();
    sharedPrefManager = new SharedPrefManager(context);

    toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("Detail");

    loadFragment(new FragmentDetailRekom());

    id = getIntent().getIntExtra("id", 0);
  }

  private void loadFragment(FragmentDetailRekom fragment) {
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
