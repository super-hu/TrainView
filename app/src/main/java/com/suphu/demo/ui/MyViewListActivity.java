package com.suphu.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.suphu.demo.R;

/**
 * Created by huchao on 2017/8/7.
 */

public class MyViewListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_list);


        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);






    }
}
