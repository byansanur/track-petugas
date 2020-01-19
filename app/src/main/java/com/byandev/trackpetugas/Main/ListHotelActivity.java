package com.byandev.trackpetugas.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.byandev.trackpetugas.MainFragmnet.FragmentAppbarHotel;
import com.byandev.trackpetugas.R;


public class ListHotelActivity extends AppCompatActivity {

  private Toolbar toolbar;
  private Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_hotel);

    context = getApplicationContext();

    loadFragment(new FragmentAppbarHotel());
  }

  @Override
  public void onBackPressed(){
    super.onBackPressed();
    Intent a = new Intent(context, KategoriActivity.class);
    startActivity(a);
    finish();
  }

  private void loadFragment(FragmentAppbarHotel listHotel) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.frame_container, listHotel);
    ft.addToBackStack(null);
    ft.commitAllowingStateLoss();
  }
}
