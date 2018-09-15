package com.chuan_sir.retrofitstudy.internet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chuan_sir.retrofitstudy.bean.ResponseModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ikidou.reflect.TypeBuilder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRequest {

    private static final String TAG = "GetRequest";
    private static volatile GetRequest getRequest;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private ApiService apiService;
    private Context context;
    private Gson gson = new Gson();
    private ExecutorService singleService = Executors.newSingleThreadExecutor();

    private GetRequest() {

    }


    public static GetRequest getInstance() {
        if (getRequest == null) {
            synchronized (GetRequest.class) {
                if (getRequest == null)
                    getRequest = new GetRequest();
            }
        }
        return getRequest;
    }

    public GetRequest init(Context context) {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://xxxxxx/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        return this;
    }

    /**
     * get方式，返回JsonObject
     */
    public <T> void getBackObject(String url, Map<String, Object> map, final Class<T> tClass, final MCallback<T> mCallback) {

        Call<ResponseBody> call;
        if (map == null || map.size() == 0) {
            call = apiService.get(url);
        } else {
            call = apiService.get(url, map);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            String responseBody = response.body().string();
                            ResponseModel<T> responseModel;
                            responseModel = fromJsonObject(responseBody, tClass);
                            if (responseModel.getState() == 0) {
                                mCallback.onSuccess(responseModel.getData());
                            } else {
                                mCallback.onFailed(responseModel.getMessage());
                            }
                        } else {
                            mCallback.onFailed("responseBody is null");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        mCallback.onFailed(response.errorBody().string());
                    } catch (IOException e) {
                        mCallback.onFailed(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mCallback.onFailed(t.getMessage());
            }
        });

    }

    /**
     * get方式，返回JsonArray
     */
    public <T> void getBackArray(String url, Map<String, Object> map, final Class<T> tClass, final MCallback<List<T>> mCallback) {

        Call<ResponseBody> call;
        if (map == null || map.size() == 0) {
            call = apiService.get(url);
        } else {
            call = apiService.get(url, map);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            String responseBody = response.body().string();
                            ResponseModel<List<T>> responseModel;
                            responseModel = fromJsonArray(responseBody, tClass);
                            if (responseModel.getState() == 0) {
                                mCallback.onSuccess(responseModel.getData());
                            } else {
                                mCallback.onFailed(responseModel.getMessage());
                            }
                        } else {
                            mCallback.onFailed("responseBody is null");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        mCallback.onFailed(response.errorBody().string());
                    } catch (IOException e) {
                        mCallback.onFailed(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mCallback.onFailed(t.getMessage());
            }
        });

    }

    /**
     * Post方式，返回JsonObject
     */
    public <T> void postBackObject(String url, Map<String, Object> map, final Class<T> tClass, final MCallback<T> mCallback) {

        Call<ResponseBody> call;
        if (map == null || map.size() == 0) {
            return;
        } else {
            call = apiService.post(url, map);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            String responseBody = response.body().string();
                            ResponseModel<T> responseModel;
                            responseModel = fromJsonObject(responseBody, tClass);
                            if (responseModel.getState() == 1) {
                                mCallback.onSuccess(responseModel.getData());
                            } else {
                                mCallback.onFailed(responseModel.getMessage());
                            }
                        } else {
                            mCallback.onFailed("responseBody is null");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        mCallback.onFailed(response.errorBody().string());
                    } catch (IOException e) {
                        mCallback.onFailed(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mCallback.onFailed(t.getMessage());
            }
        });

    }

    /**
     * Post方式，返回JsonArray
     */
    public <T> void postBackArray(String url, Map<String, Object> map, final Class<T> tClass, final MCallback<List<T>> mCallback) {

        Call<ResponseBody> call;
        if (map == null || map.size() == 0) {
            return;
        } else {
            call = apiService.post(url, map);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            String responseBody = response.body().string();
                            ResponseModel<List<T>> responseModel;
                            responseModel = fromJsonArray(responseBody, tClass);
                            if (responseModel.getState() == 1) {
                                mCallback.onSuccess(responseModel.getData());
                            } else {
                                mCallback.onFailed(responseModel.getMessage());
                            }
                        } else {
                            mCallback.onFailed("responseBody is null");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        mCallback.onFailed(response.errorBody().string());
                    } catch (IOException e) {
                        mCallback.onFailed(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mCallback.onFailed(t.getMessage());
            }
        });

    }

    /**
     * 上传文件
     */
    public <T> void uploadFile(String url, Map<String, Object> map, final Class<T> tClass, final UploadCallback<T> mCallback, File... files) {

        Call<ResponseBody> call;
        if (map == null || map.size() == 0) {
            call = apiService.post(url, createPartFile(mCallback, files));
        } else {
            call = apiService.post(url, createRequestBody(map), createPartFile(mCallback, files));
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            String responseBody = response.body().string();
                            ResponseModel<T> responseModel;
                            responseModel = fromJsonObject(responseBody, tClass);
                            if (responseModel.getState() == 1) {
                                mCallback.onSuccess(responseModel.getData());
                            } else {
                                mCallback.onFailed(responseModel.getMessage());
                            }
                        } else {
                            mCallback.onFailed("responseBody is null");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        mCallback.onFailed(response.errorBody().string());
                    } catch (IOException e) {
                        mCallback.onFailed(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mCallback.onFailed(t.getMessage());
            }
        });

    }

    /**
     * 下载文件
     */
    public void downloadFile(String url, Map<String, Object> map, final String target, final DownloadCallback downloadCallback) {
        Call<ResponseBody> call;
        if (map == null || map.size() == 0) {
            call = apiService.download(url);
        } else {
            call = apiService.download(url, map);
        }
        downloadCallback.onStart();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        singleService.execute(new Runnable() {
                            @Override
                            public void run() {
                                writeFileToLocal(response.body(), target, downloadCallback);
                            }
                        });
                    } else {
                        downloadCallback.onFailed("responseBody is null");
                    }
                } else {
                    try {
                        downloadCallback.onFailed(response.errorBody().string());
                    } catch (IOException e) {
                        downloadCallback.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                downloadCallback.onFailed(t.getMessage());
            }
        });
    }

    /**
     * 下载文件具体逻辑
     */
    private void writeFileToLocal(ResponseBody responseBody, String target, DownloadCallback downloadCallback) {
        try {

            File targetFile = new File(target);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            byte[] fileReader = new byte[1024];
            try {
                long fileSize = responseBody.contentLength();
                long fileDownloadedSize = 0;
                inputStream = responseBody.byteStream();
                outputStream = new FileOutputStream(targetFile);
                int read;
                while ((read = inputStream.read(fileReader)) != -1) {
                    outputStream.write(fileReader, 0, read);
                    fileDownloadedSize += read;
                    downloadCallback.onLoading((int) (fileDownloadedSize * 1.0 / fileSize * 100));
                }
                outputStream.flush();
                downloadCallback.onFinish();
            } catch (IOException e) {
                downloadCallback.onFailed(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            downloadCallback.onFailed(e.getMessage());
        }

    }


    public interface MCallback<T> {

        void onSuccess(T t);

        void onFailed(String msg);
    }

    public interface DownloadCallback {
        void onStart();

        void onLoading(int percent);

        void onFailed(String msg);

        void onFinish();
    }

    public interface UploadCallback<T> {
        void onStart();

        void onLoading(int percent);

        void onSuccess(T t);

        void onFailed(String msg);
    }

    /**
     * 解析JsonObject
     */
    private <T> ResponseModel<T> fromJsonObject(String string, Class<T> tClass) {
        Type type = TypeBuilder
                .newInstance(ResponseModel.class)
                .addTypeParam(tClass)
                .build();
        return gson.fromJson(string, type);
    }

    /**
     * 解析JsonArray
     */
    private <T> ResponseModel<List<T>> fromJsonArray(String string, Class<T> tClass) {
        Type type = TypeBuilder
                .newInstance(ResponseModel.class)
                .beginSubType(List.class)
                .addTypeParam(tClass)
                .endSubType()
                .build();
        return gson.fromJson(string, type);
    }


    private Map createRequestBody(Map<String, Object> map) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            requestBodyMap.put(entry.getKey(), RequestBody.create(MediaType.parse("text/plain"), entry.getValue().toString()));
        }
        return requestBodyMap;
    }

    private List<MultipartBody.Part> createPartFile(UploadCallback uploadCallback, File... files) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        for (File file : files) {
            MultipartBody.Part part;
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            FileRequestBody fileRequestBody = new FileRequestBody(requestBody, uploadCallback);
            part = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
            partList.add(part);
        }
        return partList;
    }

    /**
     * 扩展requestBody,得到上传文件进度
     */
    private class FileRequestBody extends RequestBody {

        private RequestBody requestBody;
        private UploadCallback uploadCallback;
        private BufferedSink bufferedSink;

        FileRequestBody(RequestBody requestBody, UploadCallback uploadCallback) {
            this.requestBody = requestBody;
            this.uploadCallback = uploadCallback;
        }

        @Nullable
        @Override
        public MediaType contentType() {
            return requestBody.contentType();
        }

        @Override
        public long contentLength() throws IOException {
            return requestBody.contentLength();
        }

        @Override
        public void writeTo(@NonNull BufferedSink sink) throws IOException {
            bufferedSink = Okio.buffer(sink(sink));
            uploadCallback.onStart();
            requestBody.writeTo(bufferedSink);
            bufferedSink.flush();
        }

        private Sink sink(Sink sink) {
            return new ForwardingSink(sink) {
                long byteWirtten = 0;
                long contentLength = 0;

                @Override
                public void write(@NonNull Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (contentLength == 0) {
                        contentLength = contentLength();
                    }
                    byteWirtten += byteCount;
                    uploadCallback.onLoading((int) (byteWirtten * 1.0 / contentLength * 100));
                }
            };
        }
    }

}
