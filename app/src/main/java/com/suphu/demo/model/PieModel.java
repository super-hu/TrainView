package com.suphu.demo.model;

/**
 * Created by Administrator on 2017-7-16.
 * 绘制饼状图  根据后台返回的参数来绘制
 */

public class PieModel {

    private int id;

    private String name;//名称

    private int color;//颜色

    private float progress;//进度

    public PieModel(int id, String name, int color, float progress) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
