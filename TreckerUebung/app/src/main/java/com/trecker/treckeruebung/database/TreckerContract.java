package com.trecker.treckeruebung.database;

import android.net.Uri;

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class TreckerContract {

    interface CustomerColumns {
        String CUSTOMER_ID = "_id";
        String CUSTOMER_NAME = "customer_name";
        String CUSTOMER_CITY = "customer_city";
        String CUSTOMER_ADDRESS = "customer_address";
        String CUSTOMER_FON = "customer_fon";

    }

    interface FieldColumns {
        String FIELD_ID = "_id";
        String FIELD_BUSINESS_PARTNER = "business_partner";
        String FIELD_NAME = "field_name";
        String FIELD_LATITUDE = "field_latitude";
        String FIELD_LONGITUDE = "field_longitude";
    }

    private static final String PATH_CUSTOMERS = "customers";
    private static final String PATH_FIELDS = "fields";


    public static class Customers implements CustomerColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CUSTOMERS).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.trecker.customers";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.trecker.customers";

        public static Uri buildLocationUri(String locationId) {
            return CONTENT_URI.buildUpon().appendPath(locationId).build();
        }

    }

    public static class Fields implements FieldColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FIELDS).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.trecker.fields";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.trecker.fields";

        public static Uri buildDateUri(String dateId) {
            return CONTENT_URI.buildUpon().appendPath(dateId).build();
        }
    }

    public static final String CONTENT_AUTHORITY = "com.trecker.treckeruebung";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
            + CONTENT_AUTHORITY);

    private TreckerContract() {

    }

}
