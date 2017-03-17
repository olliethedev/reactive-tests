package com.beastpotato.reactivetests.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Configuration {

    @SerializedName("images")
    @Expose
    private Images images;

    @SerializedName("change_keys")
    @Expose
    private List<String> changeKeys;

    public void setImages(Images images) {
        this.images = images;
    }

    public Images getImages() {
        return images;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    @Override
    public String toString() {
        return
                "Configuration{" +
                        "images = '" + images + '\'' +
                        ",change_keys = '" + changeKeys + '\'' +
                        "}";
    }
}