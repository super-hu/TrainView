package com.suphu.demo.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.suphu.demo.R;
import com.suphu.demo.model.PieModel;
import com.suphu.demo.widget.PieView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private PieView pieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        pieView= (PieView) findViewById(R.id.pie_view);
//
//        ArrayList<PieModel> lists=new ArrayList<>();
//        lists.add(new PieModel(1,"Java", Color.RED,40));
//        lists.add(new PieModel(2,"C", Color.YELLOW,20));
//        lists.add(new PieModel(3,"HTML/CSS", Color.WHITE,15));
//        lists.add(new PieModel(4,"Python", Color.BLACK,25));
//        pieView.setLists(270,lists);

    }
}
