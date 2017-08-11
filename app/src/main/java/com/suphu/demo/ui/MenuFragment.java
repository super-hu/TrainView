package com.suphu.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suphu.demo.R;

/**
 * Created by huchao on 2017/8/10.
 */

public class MenuFragment extends Fragment {


    public static MenuFragment newInstance() {

        Bundle args = new Bundle();

        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Fragment","onCreateView");
        return inflater.inflate(R.layout.fragment_menu,container,false);
    }




}
