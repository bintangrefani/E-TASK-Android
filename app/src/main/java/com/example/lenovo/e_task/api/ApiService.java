package com.example.lenovo.e_task.api;

import com.example.lenovo.e_task.model.DaftarSemuaKelas.DaftarSemuaKelas;
import com.example.lenovo.e_task.model.DetailMyClass.DetailMyClassModel;
import com.example.lenovo.e_task.model.EnrollClass.EnrollClassModel;
import com.example.lenovo.e_task.model.LoginRegister.LoginModel;
import com.example.lenovo.e_task.model.LoginRegister.RegisterModel;
import com.example.lenovo.e_task.model.MyClass.Kelas;
import com.example.lenovo.e_task.model.MyClass.MyClassModel;
import com.example.lenovo.e_task.model.ToEnroll.ToEnroll;
import com.example.lenovo.e_task.model.UploadFile.UploadFileModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class ApiService {
    //Untuk menyambungkan android dengan web server. android dan webserver harus adala satu jaringan
    public static String BASE_URL = "http://192.168.43.156/webmaster/api/";
    //192.168.3.136
    //hpkuu 192.168.43.156

    //Untuk mengirim data
    public static PostService service_post = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.PostService.class);

    public static GetService service_get = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.GetService.class);

    public interface GetService{
        @GET("class")
        Call<ArrayList<DaftarSemuaKelas>> getDaftarKelas(@Header ("Accept") String header);

    }

    public interface PostService {

        @FormUrlEncoded
        @POST("user/login")
        Call<LoginModel> postLogin(@Header("Accept") String header,
                                   @Field("email") String email,
                                   @Field("password") String password);


        @POST("user/register")
        @FormUrlEncoded
        Call<RegisterModel> postRegister(@Header ("Accept") String header,
                                         @Field("name") String name,
                                         @Field("email") String username,
                                         @Field("password") String password);

        @POST("user/myclass")
        @FormUrlEncoded
        Call<MyClassModel> postMyClass(@Header ("Accept") String header,
                                       @Field("user_id") String user_id);

        @POST("user/enroll-class")
        @FormUrlEncoded
        Call<EnrollClassModel> postEnroll(@Header ("Accept") String header,
                                          @Field("user_id") String user_id,
                                          @Field("class_id") String class_id);

        @POST("user/class")
        @FormUrlEncoded
        Call<DetailMyClassModel> postDetailmyClass(@Header ("Accept") String header,
                                                   @Field("user_id") String user_id,
                                                   @Field("class_id") String class_id);

        @POST("user/class2")
        @FormUrlEncoded
        Call<ToEnroll> postToEnroll(@Header ("Accept") String header,
                                         @Field("user_id") String user_id,
                                         @Field("class_id") String class_id);

        @POST("upload-file/submit")
        @Multipart
        Call<UploadFileModel> postUploadFile (@Header("Accept") String header,
                                              @Part("title") RequestBody title,
                                              @Part("task_id") RequestBody task_id,
                                              @Part("user_id") RequestBody user_id,
//                                              @Part("file") RequestBody file);
                                              @Part MultipartBody.Part file);

    }
}

