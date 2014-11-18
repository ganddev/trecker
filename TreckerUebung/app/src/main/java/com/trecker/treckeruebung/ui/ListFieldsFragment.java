package com.trecker.treckeruebung.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trecker.treckeruebung.R;
import com.trecker.treckeruebung.database.TreckerContract;
import com.trecker.treckeruebung.database.TreckerDatabase;
import com.trecker.treckeruebung.models.Customer;
import com.trecker.treckeruebung.ui.adapters.FieldAdapter;
import com.trecker.treckeruebung.utils.Constants;


public class ListFieldsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String CUSTOMER_PARAM = "customer" ;

    private Integer mCustomer;

    private FieldAdapter mAdapter;

    private String[] projection = {TreckerContract.Fields.FIELD_ID,
            TreckerContract.Fields.FIELD_BUSINESS_PARTNER,
            TreckerContract.Fields.FIELD_NAME,
            TreckerContract.Fields.FIELD_LATITUDE,
            TreckerContract.Fields.FIELD_LONGITUDE};

    public static ListFieldsFragment newInstance(final int customerId) {
        ListFieldsFragment fragment = new ListFieldsFragment();
        Bundle args = new Bundle();
        args.putInt(CUSTOMER_PARAM, customerId);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFieldsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getLoaderManager().initLoader(Constants.FIELDS_LOADER_ID, getArguments(), this);
        }
        mAdapter = new FieldAdapter(getActivity(), null, 0);
        setListAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_fields, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String selection = TreckerDatabase.Tables.FIELDS+"."+ TreckerContract.Fields.FIELD_BUSINESS_PARTNER+"=?";
        String[] selectionArgs = {((Integer)bundle.getInt(CUSTOMER_PARAM)).toString()};
        return new CursorLoader(getActivity().getApplicationContext(),
                TreckerContract.Fields.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

}
