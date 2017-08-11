package com.suphu.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.suphu.demo.R;
import com.suphu.demo.widget.LeftDrawerLayout;

/**
 * Created by huchao on 2017/8/11.
 */

public class MenuActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        LeftDrawerLayout leftDrawerLayout= (LeftDrawerLayout) findViewById(R.id.activity_main);

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.menu_layout,MenuFragment.newInstance());
        fragmentTransaction.commit();
    }
}
