package com.trecker.treckeruebung;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class TreckerApplication extends Application {

    private static final String TAG = TreckerApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static TreckerApplication mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized TreckerApplication getInstance() {
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
}
