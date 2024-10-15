package com.example.tutorial01;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    int imageRes;
    String title;
    String price;
    int quantity;

    public Item(int imageRes, String title, String price) {
        this.imageRes = imageRes;
        this.title = title;
        this.price = price;
        this.quantity = 1; // Default quantity is 1
    }

    protected Item(Parcel in) {
        imageRes = in.readInt();
        title = in.readString();
        price = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getImageRes() {
        return imageRes;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageRes);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeInt(quantity);
    }
}
