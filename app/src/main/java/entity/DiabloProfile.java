package entity;

import android.content.res.Resources;

import utils.DiabloEnum;

/**
 * Created by buxianhui on 2019/7/4.
 */

public class DiabloProfile {

    private static final String LOG_TAG = "Profile:";
    private static final String mSessionId = DiabloEnum.SESSION_ID;
    private static DiabloProfile mProfile;

    private String mToken;
    // server
    private String mServer;
    // resource handler
    private Resources mResource;

    private DiabloProfile() {

    }

    synchronized public static DiabloProfile instance() {
        if ( null == mProfile){
            mProfile = new DiabloProfile();
        }
        return mProfile;
    }


    public String getToken(){
        return this.mToken;
    }

    public void setServer(final String server) {
        this.mServer = server;
    }
    public final String getServer(){
        return mServer;
    }

    public void setResource (Resources resource) {
        this.mResource = resource;
    }

    public Resources getResource() {
        return mResource;
    }

    public void setToken(String token){
        mToken = DiabloProfile.mSessionId + "=" + token;
    }
}
