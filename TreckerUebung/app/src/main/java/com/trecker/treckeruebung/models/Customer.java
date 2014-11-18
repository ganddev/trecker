package com.trecker.treckeruebung.models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.trecker.treckeruebung.database.TreckerContract;

import org.json.JSONObject;

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class Customer implements Parcelable {

    private Integer id;
    private String name;
    private String city;
    private String address;
    private String fon;

    public Customer(){}

    private Customer(Parcel in)
    {
        this.id = in.readInt();
        this.name = in.readString();
        this.city = in.readString();
        this.address = in.readString();
        this.fon = in.readString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFon() {
        return fon;
    }

    public void setFon(String fon) {
        this.fon = fon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.city);
        parcel.writeString(this.address);
        parcel.writeString(this.fon);
    }

    public static Customer parseFromJSON(JSONObject obj)
    {
        if(obj == null)
        {
            return null;
        }
        final Customer customer = new Customer();
        customer.id = obj.optInt("id");
        customer.name = obj.optString("name");
        customer.address = obj.optString("address");
        customer.city = obj.optString("city");
        customer.fon = obj.optString("fon");

        return customer;
    }

    public ContentValues toContentValues(){
        final ContentValues item = new ContentValues();
        item.put(TreckerContract.Customers.CUSTOMER_ID, this.id);
        item.put(TreckerContract.Customers.CUSTOMER_NAME, this.name);
        item.put(TreckerContract.Customers.CUSTOMER_FON, this.fon);
        item.put(TreckerContract.Customers.CUSTOMER_ADDRESS, this.address);
        item.put(TreckerContract.Customers.CUSTOMER_CITY, this.city);
        return item;
    }
}
