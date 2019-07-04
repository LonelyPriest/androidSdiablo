package com.sdiablo.dt.sdiablo.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.sdiablo.dt.sdiablo.R;

import java.lang.ref.WeakReference;

import client.LoginClient;
import db.DiabloDBManager;
import entity.DiabloProfile;
import entity.DiabloUser;
import response.LoginResponse;
import rest.LoginInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.DiabloEnum;
import utils.DiabloError;
import utils.DiabloUtils;

/**
 * Created by buxianhui on 2019/7/4.
 */

public class LoginActivity extends AppCompatActivity {
    private final static String LOG_TAG = "LOGIN:";

    BootstrapButton mBtnLogin;
    TextInputLayout mLoginWrap;
    TextInputLayout mPasswordWrap;
    Spinner mViewServer;
    Context mContext;

    String mName;
    String mPassword;

    // private ProgressDialog mLoadingDialog;
    private Dialog mLoadingDialog;

    private LoginInterface mFace;

    private final LoginHandler mLoginHandler = new LoginHandler(this);

    private LoginListener createLoginListener(final DiabloUser user) {
        return new LoginListener() {
            @Override
            public void onLogin() {
                Call<LoginResponse> call = mFace.login(mName, mPassword, DiabloEnum.TABLET, DiabloEnum.DIABLO_TRUE);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse body = response.body();
                        Integer code = body.getCode();
                        switch (code) {
                            case 0:
                                startLogin(user, body.getToken());
                                break;
                            default:
                                loginError(code);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        mBtnLogin.setClickable(true);
                        loginError(9009);
                    }
                });
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init db
        DiabloDBManager.instance().init(this);

        mContext = this;

        DiabloProfile.instance().setServer(getString(R.string.diablo_production_server));
        DiabloProfile.instance().setResource(getResources());
        // mFace = WLoginClient.getClient().create(WLoginInterface.class);
        // DiabloProfile.instance().setContext(this.getApplicationContext());

        mLoginWrap = (TextInputLayout) findViewById(R.id.login_name_holder);
        mPasswordWrap = (TextInputLayout) findViewById(R.id.login_password_holder);
        mViewServer = (Spinner)findViewById(R.id.spinner_select_server);

        final DiabloUser user = DiabloDBManager.instance().getFirstLoginUser();
        if (null != user) {
            if (null != mLoginWrap.getEditText())
                mLoginWrap.getEditText().setText(user.getName());
            if (null != mPasswordWrap.getEditText())
                mPasswordWrap.getEditText().setText(user.getPassword());
        }

        // InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //
        mBtnLogin = (BootstrapButton) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mLoginWrap.getEditText()) {
                    mName = mLoginWrap.getEditText().getText().toString();
                }

                if (null != mPasswordWrap.getEditText()) {
                    mPassword = mPasswordWrap.getEditText().getText().toString();
                }

                if (mName.trim().equals("")) {
                    if (null != mLoginWrap.getEditText()) {
                        mLoginWrap.getEditText().setError(getResources().getString(R.string.login_name));
                    }
                }
                else if (mPassword.trim().equals("")) {
                    if (null != mPasswordWrap.getEditText()) {
                        mPasswordWrap.getEditText().setError(getResources().getString(R.string.login_password));
                    }
                }
                else {
                    // login
                    mBtnLogin.setClickable(false);
                    LoginClient.resetClient();
                    mFace = LoginClient.getClient().create(LoginInterface.class);
                    Call<LoginResponse> call = mFace.login(mName, mPassword, DiabloEnum.TABLET, DiabloEnum.DIABLO_FALSE);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            LoginResponse body = response.body();
                            Integer code = body.getCode();
                            switch (code){
                                case 0:
                                    startLogin(user, body.getToken());
                                    break;
                                case 1105:
                                    loginError(1105, createLoginListener(user));
                                    break;
                                case 1106:
                                    loginError(1106, createLoginListener(user));
                                    break;
                                default:
                                    loginError(code);
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            mBtnLogin.setClickable(true);
                            loginError(9009);
                        }
                    });
                }
            }
        });

        BootstrapButton btnClear = (BootstrapButton) findViewById(R.id.btn_clear_login);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mLoginWrap.getEditText())
                    mLoginWrap.getEditText().setText("");
                if (null != mPasswordWrap.getEditText())
                    mPasswordWrap.getEditText().setText("");

                DiabloDBManager.instance().clearUser();
            }
        });
    }

    private void startLogin(DiabloUser user, String token) {
        DiabloProfile.instance().setToken(token);
        if (null == user) {
            DiabloDBManager.instance().addUser(mName, mPassword);
        }
        else {
            if(!user.getName().equals(mName)){
                DiabloDBManager.instance().addUser(mName, mPassword);
            } else {
                if (!user.getPassword().equals(mPassword)) {
                    DiabloDBManager.instance().updateUser(mName, mPassword);
                }
            }
        }

        mLoadingDialog = DiabloUtils.createLoadingDialog(mContext);
        mLoadingDialog.show();
    }

    public void gotoMain(){
        DiabloDBManager.instance().close();
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        mLoadingDialog.dismiss();
    }

    public void loginError(Integer code) {
        loginError(code, null);
    }

    public void loginError(Integer code, final LoginListener listener){
        if (null != mLoadingDialog){
            mLoadingDialog.dismiss();
        }

        mBtnLogin.setClickable(true);

        String error = DiabloError.getError(code);
        new MaterialDialog.Builder(mContext)
            .title(R.string.user_login)
            .content(error)
            // .contentColor(mContext.getResources().getColor(R.color.colorPrimaryDark))
            .positiveText(getString(R.string.login_ok))
            .positiveColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark))
            .onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (null != listener) {
                        listener.onLogin();
                    }
                }
            })
            .negativeText(getString(R.string.login_cancel))
            .negativeColor(ContextCompat.getColor(mContext, R.color.colorGray))
            .onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            })
            .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private static class LoginHandler extends Handler {
        private final WeakReference<LoginActivity> mActivity;

        private LoginHandler(LoginActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mActivity.get();
            if (activity != null) {
                // ...
                switch (msg.what){
                    case 10:
                        activity.gotoMain();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // DiabloDBManager.instance().close();
    }

    private interface LoginListener {
        void onLogin();
    }
}
