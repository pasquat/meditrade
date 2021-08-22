package com.example.tutorial;

public class listitem {
    String name;
    String mobile;
    String resId;
    String key;
    //생성
    public listitem(String name, String mobile, String resId, String key) {
        this.name = name;
        this.mobile = mobile;
        this.resId = resId;
        this.key = key;
    }

    //변수에 접근할 때 .OO 접근하기보다는 안전하게 getter, setter를 이용합니다.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getResId() {
        return resId;
    }

    public String getKey(){ return key; }

    public void setKey(String key) {this.key=key;}

    @Override
    public String toString() {
        return "SingerItem{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}