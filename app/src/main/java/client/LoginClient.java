package client;

import entity.DiabloProfile;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by buxianhui on 2019/7/4.
 */

public class LoginClient {
    private static Retrofit retrofit;
    // private static final  String mUrl = "";

    private LoginClient(){

    }

    public static Retrofit getClient() {
        String baseUrl = DiabloProfile.instance().getServer();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }

    public static void resetClient() {
        retrofit = null;
    }
}
