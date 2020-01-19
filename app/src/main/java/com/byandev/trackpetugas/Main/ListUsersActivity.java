package com.byandev.trackpetugas.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.byandev.trackpetugas.Adapter.JamaahAdapter;
import com.byandev.trackpetugas.Api.ApiEndPoint;
import com.byandev.trackpetugas.Api.SharedPrefManager;
import com.byandev.trackpetugas.Api.UtilsApi;
import com.byandev.trackpetugas.MainFragmnet.FragmentAppBarUsers;
import com.byandev.trackpetugas.MainFragmnet.FragmentListJamaah;
import com.byandev.trackpetugas.Model.JamaahListModel;
import com.byandev.trackpetugas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUsersActivity extends AppCompatActivity {

  private Context context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_users);
    context =this;

    loadFragment(new FragmentAppBarUsers());
  }
  private void loadFragment(FragmentAppBarUsers listHotel) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.frame_container, listHotel);
    ft.addToBackStack(null);
    ft.commitAllowingStateLoss();
  }

  @Override
  public void onBackPressed() {
    Intent a  = new Intent(context, HomeActivity.class);
    startActivity(a);
    finish();
  }
}
