package com.firstcode.vo;

/**
 * Created by wangjinliang on 2016/3/4.
 */
public class ListViewItem {
    private String imageName;
    private int imageId;

    public ListViewItem(){}

    public ListViewItem(String imageName, int imageId) {
        this.imageName = imageName;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageName() {

        return imageName;
    }
}
