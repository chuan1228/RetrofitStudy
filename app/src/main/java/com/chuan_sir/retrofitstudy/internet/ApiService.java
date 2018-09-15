package com.chuan_sir.retrofitstudy.internet;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Call<ResponseBody> get(@Url String url);

    @GET
    Call<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> map);

    @POST
    @FormUrlEncoded
    Call<ResponseBody> post(@Url String url, @FieldMap Map<String, Object> map);

    @POST
    @Multipart
    Call<ResponseBody> post(@Url String url, @Part List<MultipartBody.Part> files);

    @POST
    @Multipart
    Call<ResponseBody> post(@Url String url, @PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> files);


    @GET
    @Streaming
    Call<ResponseBody> download(@Url String url);

    @POST
    @FormUrlEncoded
    @Streaming
    Call<ResponseBody> download(@Url String url, @FieldMap Map<String, Object> map);
}
