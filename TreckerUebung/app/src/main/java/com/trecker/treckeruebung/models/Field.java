package com.trecker.treckeruebung.models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.trecker.treckeruebung.database.TreckerContract;

import org.json.JSONObject;

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class Field implements Parcelable{

    private Integer businessPartner;
    private Integer field;
    private String fieldName;
    private Double latitude;
    private Double longitude;


    public Field(){}

    private Field(Parcel in)
    {
        this.businessPartner = in.readInt();
        this.field = in.readInt();
        this.fieldName = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(Integer businessPartner) {
        this.businessPartner = businessPartner;
    }

    public Integer getField() {
        return field;
    }

    public void setField(Integer field) {
        this.field = field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public static final Parcelable.Creator<Field> CREATOR = new Parcelable.Creator<Field>() {
        public Field createFromParcel(Parcel source) {
            return new Field(source);
        }

        public Field[] newArray(int size) {
            return new Field[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.businessPartner);
        parcel.writeInt(this.field);
        parcel.writeString(this.fieldName);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
    }


    public static Field parseFromJSON(JSONObject obj) {
        if(obj == null)
        {
            return null;
        }
        final Field field = new Field();
        field.businessPartner = obj.optInt("business_partner");
        field.field = obj.optInt("field");
        field.fieldName = obj.optString("field_name");
        field.latitude = obj.optDouble("latitude");
        field.longitude = obj.optDouble("longitude");
        return field;
    }


    public ContentValues toContentValues() {
        final ContentValues item = new ContentValues();
        item.put(TreckerContract.Fields.FIELD_ID, this.field);
        item.put(TreckerContract.Fields.FIELD_BUSINESS_PARTNER, this.businessPartner);
        item.put(TreckerContract.Fields.FIELD_NAME, this.fieldName);
        item.put(TreckerContract.Fields.FIELD_LATITUDE, this.latitude);
        item.put(TreckerContract.Fields.FIELD_LONGITUDE, this.longitude);
        return item;
    }
}
