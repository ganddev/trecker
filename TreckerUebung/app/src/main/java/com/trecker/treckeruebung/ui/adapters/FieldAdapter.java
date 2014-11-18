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

/**
 * Created by bjornahlfeld on 18.11.14.
 */
public class FieldAdapter extends CursorAdapter {

    private final LayoutInflater mInflater;

    public FieldAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View v =  mInflater.inflate(R.layout.item_field, parent, false);
        final FieldViewHolder vh = new FieldViewHolder();
        vh.fieldName = (TextView) v.findViewById(R.id.txt_field);
        v.setTag(vh);
        return v;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        FieldViewHolder vh = null;
        if(view.getTag() != null)
        {
            vh = (FieldViewHolder)view.getTag();
        } else {
            vh = new FieldViewHolder();
            vh.fieldName = (TextView) view.findViewById(R.id.txt_field);
        }
        vh.fieldName.setText(cursor.getString(cursor.getColumnIndex(TreckerContract.Fields.FIELD_NAME)));
        view.setTag(vh);
    }

    public static class FieldViewHolder{
        public TextView fieldName;
        public int fieldId;
    }
}
