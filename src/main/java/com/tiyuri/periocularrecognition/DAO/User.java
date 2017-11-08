package com.tiyuri.periocularrecognition.DAO;

import android.graphics.Bitmap;

public class User {
    private long _id;
    private String name;
    private String photo1Lbp;
    private String photo2Lbp;
    private String photo3Lbp;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto1Lbp() {
        return photo1Lbp;
    }

    public void setPhoto1Lbp(String photo1Lbp) {
        this.photo1Lbp = photo1Lbp;
    }

    public String getPhoto2Lbp() {
        return photo2Lbp;
    }

    public void setPhoto2Lbp(String photo2Lbp) {
        this.photo2Lbp = photo2Lbp;
    }

    public String getPhoto3Lbp() {
        return photo3Lbp;
    }

    public void setPhoto3Lbp(String photo3Lbp) {
        this.photo3Lbp = photo3Lbp;
    }

    @Override
    public String toString() {
        return get_id() + " - " + getName();
    }
}