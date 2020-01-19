package com.byandev.trackpetugas.Main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.byandev.trackpetugas.Api.ApiEndPoint;
import com.byandev.trackpetugas.Api.SharedPrefManager;
import com.byandev.trackpetugas.Api.UtilsApi;
import com.byandev.trackpetugas.Model.UpdateLocationModel;
import com.byandev.trackpetugas.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
  Context context;
  SharedPrefManager sharedPrefManager;
  ApiEndPoint mApiService;
  private Handler handler = new Handler();
  private Integer intVersion = 0, xmlVersion;
  private Boolean logoutcheck = false;
  private Runnable r;
  private TextView greeting, tanggal;
  private FloatingActionButton fab;
  androidx.appcompat.widget.Toolbar toolbar;
  private CardView cardJamaah, cardViewRek, cardListSos, cardMap;

  Location location;
  private GoogleApiClient mGoogleApiClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    context = this;
    sharedPrefManager = new SharedPrefManager(context);
    mApiService = UtilsApi.getAPIService();

    xmlVersion = Integer.parseInt(getResources().getString(R.string.version));
    toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("Tju App Petugas");
    toolbar.inflateMenu(R.menu.menu_main);
    toolbar.setOnMenuItemClickListener(this);

    fab = findViewById(R.id.fab);
    greeting = findViewById(R.id.tvGreating);
    tanggal = findViewById(R.id.tvTanggal);

    cardJamaah = findViewById(R.id.cardJamaah);
    cardListSos = findViewById(R.id.cardActivitySos);
    cardMap = findViewById(R.id.cardMapUserSos);
    cardViewRek = findViewById(R.id.cardrek);

    Calendar startDate = Calendar.getInstance();
    startDate.add(Calendar.MONTH, -1);
    Calendar endDate = Calendar.getInstance();
    endDate.add(Calendar.MONTH, 1);
    inisialisasiSalam();
    setUpGeoCode();
    checkRequestPermision();
  }

  @Override
  public void onStart(){
    super.onStart();
    mGoogleApiClient.connect();
  }

  private void setUpGeoCode() {
    mGoogleApiClient = new GoogleApiClient
        .Builder(this)
        .addApi(LocationServices.API)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build();
  }

  @Override
  public void onResume() {
    super.onResume();
    listener();

  }

  private void listener() {
    cardMap.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(context, ActivityMapUser.class));
        finish();
      }
    });
    cardJamaah.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(context, ListUsersActivity.class));
        finish();
      }
    });
    cardViewRek.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(context,KategoriActivity.class));
        finish();
      }
    });
    cardListSos.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(context,ListSosActivity.class));
        finish();
      }
    });
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(context,ProfileActivity.class));
        finish();
      }
    });
  }

  private void inisialisasiSalam() {
    Date date =new Date();
    @SuppressLint("SimpleDateFormat")
    DateFormat df = new SimpleDateFormat("dd MMMM, yyyy");
    String hariini = df.format(Calendar.getInstance().getTime());
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    String salam = null;
    if (hour >= 12 && hour < 17) {
      salam = "Selamat siang";
    } else if (hour >= 17 && hour < 21) {
      salam = "Selamat sore";
    } else if (hour >= 21 && hour < 24) {
      salam = "Selamat malam";
    } else {
      salam = "Selamat pagi";
    }
    greeting.setText(salam +", "+ sharedPrefManager.getSPNama());
//    fab.setImageIcon(textAsBitmap(""+sharedPrefManager.getSPNama().charAt(0), 40, Color.WHITE));
    tanggal.setText(hariini);
  }



  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.sos:
        return true;
      case R.id.tentang :
        Intent i = new Intent(this, TentangActivity.class);
        startActivity(i);
        return true;
      case R.id.logout:
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("Logout ?").setCancelable(true)
            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
              }
            })
            .setPositiveButton("Ya, Logout", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                sharedPrefManager.clearSharedPreferences();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
              }
            })
            .show();
        return true;
    }
    return false;
  }

  @Override
  protected void onPause() {
    super.onPause();
    logoutcheck = false;
    handler.removeCallbacks(r);
  }
  @Override
  public void onBackPressed() {
    super.onBackPressed();

  }

  @Override
  protected void onStop() {
    super.onStop();
    logoutcheck = false;
    handler.removeCallbacks(r);
    mGoogleApiClient.disconnect();
  }

  private boolean checkRequestPermision() {
    int telpon = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
    int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
    int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    int storageRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    List<String> listPermissionsNeeded = new ArrayList<>();

    if (telpon != PackageManager.PERMISSION_GRANTED) {
      listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
    }
    if (storage != PackageManager.PERMISSION_GRANTED) {
      listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    if (location != PackageManager.PERMISSION_GRANTED) {
      listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
    }
    if (storageRead != PackageManager.PERMISSION_GRANTED) {
      listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    if (!listPermissionsNeeded.isEmpty()) {
      ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
      return false;
    }
    return true;
  }

  private void updateLocationUsers() {
    mApiService.updateLocation(
        sharedPrefManager.getSpId(),
        String.valueOf(location.getLatitude()),
        String.valueOf(location.getLongitude())
    ).enqueue(new Callback<UpdateLocationModel>() {
      @Override
      public void onResponse(Call<UpdateLocationModel> call, Response<UpdateLocationModel> response) {
        if (response.isSuccessful()) {
          if (response.body().getApiStatus() == 1) {
            Toast.makeText(context, "Berhasil koneksi google maps Api", Toast.LENGTH_LONG).show();
          }else {
            Toast.makeText(context, "Gagal kesalahan ip", Toast.LENGTH_SHORT).show();
          }
        }else {
          Toast.makeText(context, "Gagal kesalahan server", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<UpdateLocationModel> call, Throwable t) {
        Toast.makeText(context, "Gagal kesalahan server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    location = LocationServices.FusedLocationApi.getLastLocation(
        mGoogleApiClient);
    if (location != null) {
      Toast.makeText(this," Connected to Google Location API", Toast.LENGTH_LONG).show();
      updateLocationUsers();
    }

  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }
}
