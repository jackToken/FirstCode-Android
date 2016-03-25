package com.firstcode.vo;

/**
 * Created by wangjinliang on 2016/3/21.
 */
public class Game {
    private String name;
    private String pic;
    private String packageName;

    public String getName() {
        return name;
    }

    public String getPic() {
        return pic;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", packageName='" + packageName + '\'' +
                '}' + "\n";
    }
}
