package response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by buxianhui on 2019/7/4.
 */

public class Response {
    @SerializedName("ecode")
    private Integer code;
    @SerializedName("einfo")
    private String error;

    public Response(){

    }

    public Integer getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
