package com.jisa.stepintwoit.models;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jisajose on 2018-01-17.
 */

public class Product implements Parcelable {
    String name;
    String description;
    String image;
    String phone;
    String imgFavorate = "0";
    String productId;

    public String getImgFavorate() {
        return imgFavorate;
    }

    public void setImgFavorate(String imgFavorate) {
        this.imgFavorate = imgFavorate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeString(this.phone);
        dest.writeString(this.imgFavorate);
        dest.writeString(this.productId);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.phone = in.readString();
        this.imgFavorate = in.readString();
        this.productId = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}