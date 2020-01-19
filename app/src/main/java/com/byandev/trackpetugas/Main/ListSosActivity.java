package com.byandev.trackpetugas.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.byandev.trackpetugas.MainFragmnet.FragmentAppbarSos;
import com.byandev.trackpetugas.R;


public class ListSosActivity extends AppCompatActivity {

  private Context context;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_sos);
    context = getApplicationContext();
    loadFragment(new FragmentAppbarSos());
  }
  @Override
  public void onBackPressed(){
    super.onBackPressed();
    Intent a = new Intent(context, HomeActivity.class);
    startActivity(a);
    finish();
  }

  private void loadFragment(FragmentAppbarSos listKuliner) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.frame_container, listKuliner);
    ft.addToBackStack(null);
    ft.commitAllowingStateLoss();
  }
}
