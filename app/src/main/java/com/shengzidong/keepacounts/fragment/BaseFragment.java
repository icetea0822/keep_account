package com.shengzidong.keepacounts.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shengzidong.keepacounts.R;


public class BaseFragment extends Fragment {


    public BaseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
