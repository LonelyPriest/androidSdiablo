package rest;

import response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by buxianhui on 2019/7/4.
 */

public interface LoginInterface {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("user_name") String name,
                              @Field("user_password") String password,
                              @Field("tablet") Integer tablet,
                              @Field("force") Integer force);
}
