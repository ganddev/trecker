package com.trecker.treckeruebung.api;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.trecker.treckeruebung.R;
import com.trecker.treckeruebung.TreckerApplication;
import com.trecker.treckeruebung.database.TreckerContract;
import com.trecker.treckeruebung.models.Customer;
import com.trecker.treckeruebung.models.Field;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.trecker.treckeruebung.R.string.api_customers;

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class ApiService {

    private static final String TAG = ApiService.class.getSimpleName();

    public static void getCustomers(final Context ctx)
    {
        JsonArrayRequest req = new JsonArrayRequest(Endpoints.CUSTOMERS,
                new Response.Listener<JSONArray>()
                {
                    @Override public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        final int arraylength = response.length();
                        ContentValues[] values = new ContentValues[arraylength];
                        for(int i = 0; i < arraylength; i++)
                        {
                            values[i] = Customer.parseFromJSON(response.optJSONObject(i)).toContentValues();
                        }
                        final ContentResolver cr = ctx.getContentResolver();
                        cr.bulkInsert(TreckerContract.Customers.CONTENT_URI, values);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        TreckerApplication.getInstance().addToRequestQueue(req);
    }

    public static void getFields(final Context ctx){
        JsonArrayRequest req = new JsonArrayRequest(Endpoints.FIELDS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response", response.toString());
                final int arraylength = response.length();
                ContentValues[] values = new ContentValues[arraylength];
                for(int i = 0; i < arraylength; i++)
                {
                    values[i] = Field.parseFromJSON(response.optJSONObject(i)).toContentValues();
                    Log.d(TAG, "Field id: " +values[i].getAsInteger(TreckerContract.Fields.FIELD_ID));
                }
                final ContentResolver cr = ctx.getContentResolver();
                cr.bulkInsert(TreckerContract.Fields.CONTENT_URI, values);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null && error.getMessage() != null)
                {
                    Log.e(TAG, error.getMessage());
                }
            }
        });
        TreckerApplication.getInstance().addToRequestQueue(req);
    }
}
