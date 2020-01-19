package com.byandev.trackpetugas.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.byandev.trackpetugas.MainFragmnet.FragmentAppBarKuliner;
import com.byandev.trackpetugas.R;


public class ListKulinerActivity extends AppCompatActivity {

  private Context context;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_kuliner);
    context = getApplicationContext();
    loadFragment(new FragmentAppBarKuliner());
  }
  @Override
  public void onBackPressed(){
    super.onBackPressed();
    Intent a = new Intent(context, KategoriActivity.class);
    startActivity(a);
    finish();
  }

  private void loadFragment(FragmentAppBarKuliner listKuliner) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.frame_container, listKuliner);
    ft.addToBackStack(null);
    ft.commitAllowingStateLoss();
  }
}
