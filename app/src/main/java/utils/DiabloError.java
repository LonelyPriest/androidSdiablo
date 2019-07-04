package utils;

import android.content.res.Resources;
import android.util.SparseArray;

import com.sdiablo.dt.sdiablo.R;

import entity.DiabloProfile;

/**
 * Created by buxianhui on 2019/7/4.
 */

public class DiabloError {
    private static SparseError mSparseError;

    private DiabloError() {

    }

    private static SparseError instance() {
        if (null == mSparseError) {
            mSparseError = new SparseError();
        }

        return mSparseError;
    }

    private static class SparseError {
        private SparseArray<String> mErrors = new SparseArray<>();

        SparseError() {
            Resources r = DiabloProfile.instance().getResource();
            mErrors.put(99, r.getString(R.string.network_invalid));
            mErrors.put(500, r.getString(R.string.internal_error));
            mErrors.put(598, r.getString(R.string.authen_error));
            mErrors.put(1101, r.getString(R.string.invalid_name_password));
            mErrors.put(9009, r.getString(R.string.network_unreachable));
            mErrors.put(9001, r.getString(R.string.database_error));

            mErrors.put(1105, r.getString(R.string.login_user_active));
            mErrors.put(1106, r.getString(R.string.login_exceed_user));
            mErrors.put(1107, r.getString(R.string.login_no_user_fire));
            mErrors.put(1199, r.getString(R.string.login_out_of_service));
        }
    }

    public static String getError(Integer code){
        return instance().mErrors.get(code);
    }
}
