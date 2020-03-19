package response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buxianhui on 2019/7/4.
 */

public class LoginResponse extends Response {
    @SerializedName("token")
    private String token;
    @SerializedName("path")
    private String path;

    LoginResponse(){
        super();
    }

    public String getToken() {
        return this.token;
    }

    public String getPath() {
        return this.path;
    }

    public void setToken(String mToken) {
        this.token = token;
    }
}
