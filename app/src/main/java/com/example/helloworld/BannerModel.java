package com.example.helloworld;

public class BannerModel {
    String uuid;
    String banner_url;

    public BannerModel(String uuid, String banner_url) {
        this.uuid = uuid;
        this.banner_url = banner_url;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }
}
