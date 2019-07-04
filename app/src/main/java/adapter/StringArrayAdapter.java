package adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by buxianhui on 2019/7/4.
 */

public class StringArrayAdapter extends ArrayAdapter<String> {
    private OnAdjustDropDownViewListener mDropDownViewListener;

    public StringArrayAdapter(Context context, int resource, String[] items) {
        super(context, resource, items);
    }

    public void setDropDownViewListener(OnAdjustDropDownViewListener listener) {
        this.mDropDownViewListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextSize(18);
        view.setTextColor(Color.BLACK);
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTextSize(18);
        view.setTextColor(Color.BLACK);

        if (null != mDropDownViewListener) {
            mDropDownViewListener.setDropDownVerticalOffset();
        }
        return view;
    }
}
