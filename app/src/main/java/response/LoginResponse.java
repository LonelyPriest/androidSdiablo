package response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buxianhui on 2019/7/4.
 */

public class LoginResponse extends Response {
    @SerializedName("token")
    private String token;

    LoginResponse(){
        super();
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String mToken) {
        this.token = token;
    }
}
