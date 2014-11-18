package com.trecker.treckeruebung.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.trecker.treckeruebung.R;
import com.trecker.treckeruebung.database.TreckerContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class CustomerAdapter extends CursorAdapter {

    private final LayoutInflater mInflater;

    public CustomerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View v = mInflater.inflate(R.layout.item_customer, parent, false);
        CustomerViewHolder vh = new CustomerViewHolder(v);
        v.setTag(vh);
        return v;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CustomerViewHolder vh = null;
        if(view.getTag() != null)
        {
            vh = (CustomerViewHolder) view.getTag();
        } else {
            vh = new CustomerViewHolder(view);
        }
        vh.customerText.setText(cursor.getString(cursor.getColumnIndex(TreckerContract.Customers.CUSTOMER_NAME)));
        vh.customerId = (cursor.getInt(cursor.getColumnIndex(TreckerContract.Customers.CUSTOMER_ID)));
        view.setTag(vh);
    }


    public static  class CustomerViewHolder{
        @InjectView(R.id.txt_customer) TextView customerText;
        public int customerId;

        public CustomerViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
