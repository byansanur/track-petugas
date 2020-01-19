package com.byandev.trackpetugas.MainFragmnet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.byandev.trackpetugas.Adapter.JamaahAdapter;
import com.byandev.trackpetugas.Api.ApiEndPoint;
import com.byandev.trackpetugas.Api.SharedPrefManager;
import com.byandev.trackpetugas.Api.UtilsApi;
import com.byandev.trackpetugas.Model.JamaahListModel;
import com.byandev.trackpetugas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListJamaah extends Fragment {


  private Context context;
  private ApiEndPoint mApiServices;
  SharedPrefManager sharedPrefManager;
  private Integer offset = 15, limit;
  private boolean itShouldLoadMore = true;
  ProgressBar progress;

  private RecyclerView recyclerView;
  private ArrayList<JamaahListModel.Jamaah> arrayList;
  private JamaahAdapter adapter;

  public FragmentListJamaah() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s) {
    View view = inflater.inflate(R.layout.fragment_list_jamaah, container, false);

    limit = 15;
    context = getContext();
    sharedPrefManager = new SharedPrefManager(context);
    mApiServices = UtilsApi.getAPIService();
    recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true);

    progress = view.findViewById(R.id.progress);

    arrayList = new ArrayList<>();
    adapter = new JamaahAdapter(context, arrayList);

    LinearLayoutManager llm = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(llm);
    recyclerView.setAdapter(adapter);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
          if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
            if (itShouldLoadMore) {
              progress.setVisibility(View.VISIBLE);
              loadMore();
            }
          }
        }
      }
    });

    return view;
  }


  @Override
  public void onResume(){
    super.onResume();
    arrayList.clear();
    adapter.notifyDataSetChanged();
    progress.setVisibility(View.VISIBLE);
    firstLoad();

  }

  @Override
  public void onStart(){
    super.onStart();
  }

  private void firstLoad() {
    itShouldLoadMore = false;
    mApiServices.jamaah(3, limit, 0).enqueue(new Callback<JamaahListModel>() {
      @Override
      public void onResponse(Call<JamaahListModel> call, Response<JamaahListModel> response) {
        if (response.isSuccessful()){
          itShouldLoadMore = true;
          if (response.body().getApiStatus() == 1) {
            List<JamaahListModel.Jamaah> jamaahs = response.body().getData();
            arrayList.addAll(jamaahs);
            if (arrayList.size() >= 1) {
              progress.setVisibility(View.INVISIBLE);
            } else {
              progress.setVisibility(View.VISIBLE);
            }
            progress.setVisibility(View.GONE);
          }
        }
      }

      @Override
      public void onFailure(Call<JamaahListModel> call, Throwable t) {
        itShouldLoadMore = true;
        progress.setVisibility(View.GONE);
        Toast.makeText(context, "message " + t.getMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }

  private void loadMore() {
    itShouldLoadMore = false;
    mApiServices.jamaah(3, limit, offset).enqueue(new Callback<JamaahListModel>() {
      @Override
      public void onResponse(Call<JamaahListModel> call, Response<JamaahListModel> response) {
        if (response.isSuccessful()) {
          itShouldLoadMore = true;
          if (response.body().getData() != null) {
            List<JamaahListModel.Jamaah> jamaahList = response.body().getData();
            arrayList.addAll(jamaahList);
            adapter.notifyDataSetChanged();
            int index = arrayList.size();
            offset = index;
            progress.setVisibility(View.GONE);

          }
        }
      }

      @Override
      public void onFailure(Call<JamaahListModel> call, Throwable t) {
        itShouldLoadMore = true;
        progress.setVisibility(View.GONE);
        Toast.makeText(context, "message " + t.getMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }
}
