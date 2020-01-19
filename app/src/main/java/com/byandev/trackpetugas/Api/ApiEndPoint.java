package com.byandev.trackpetugas.Api;

import com.byandev.trackpetugas.Model.DetailRekomModel;
import com.byandev.trackpetugas.Model.DetailSosModel;
import com.byandev.trackpetugas.Model.JamaahDetailModel;
import com.byandev.trackpetugas.Model.JamaahListModel;
import com.byandev.trackpetugas.Model.LocationUsersModel;
import com.byandev.trackpetugas.Model.Login;
import com.byandev.trackpetugas.Model.ProfileModel;
import com.byandev.trackpetugas.Model.RekomModel;

import com.byandev.trackpetugas.Model.SosListModel;
import com.byandev.trackpetugas.Model.UpdateLocationModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiEndPoint {
  @FormUrlEncoded
  @POST("login_petugas")
  Call<Login> requestLogin(@Field("username") String username,
                           @Field("password") String password);

  @FormUrlEncoded
  @PUT("update_location")
  Call<UpdateLocationModel> updateLocation(@Field("id") Integer id,
                                           @Field("lat") String lat,
                                           @Field("lng") String lng);


  @GET("rekom_list")
  Call<RekomModel> rekomList(@Query("id_type") Integer idType,
                             @Query("limit") Integer limit,
                             @Query("offset") Integer offset);

  @GET("sos/detail")
  Call<DetailSosModel> detailSos(@Query("id") Integer id);

  @GET("sos/list")
  Call<SosListModel> listSos(@Query("limit") Integer limit,
                             @Query("offset") Integer offset);

  @GET("rekom_list")
  Call<RekomModel> rekomkategori(@Query("id_type") Integer idType);

  @GET("rekom_list")
  Call<RekomModel> rekomListMap();

  @GET("rekom_list")
  Call<RekomModel> search(@Query("id_type") Integer idType,
                          @Query("nama") String nama);

  @GET("rekom_detail")
  Call<DetailRekomModel> detail(@Query("id") Integer id);

  @GET("location/users")
  Call<LocationUsersModel> loc(@Query("id_privileges") Integer idPrivileges);

  @GET("fetch_users/list")
  Call<JamaahListModel> jamaah(@Query("id_privileges") Integer idPrivileges,
                               @Query("limit") Integer limit,
                               @Query("offset") Integer offset);

  @GET("fetch_users")
  Call<JamaahDetailModel> jamaahDetail(@Query("id") Integer id,
                                       @Query("id_privileges") Integer idPrivileges);

  @GET("fetch_users")
  Call<ProfileModel> profile(@Query("id") Integer id,
                             @Query("id_privileges") Integer idPrivileges);

}
