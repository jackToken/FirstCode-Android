package com.firstcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/7.
 */
public class AntherFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.anthor_right_layout, null);
    }
}
