package com.trecker.treckeruebung.ui;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.trecker.treckeruebung.R;
import com.trecker.treckeruebung.database.TreckerContract;
import com.trecker.treckeruebung.models.Customer;
import com.trecker.treckeruebung.ui.adapters.CustomerAdapter;
import com.trecker.treckeruebung.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListCustomersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListCustomersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListCustomersFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private String[] projection = {
            TreckerContract.Customers.CUSTOMER_ID,
            TreckerContract.Customers.CUSTOMER_NAME,
            TreckerContract.Customers.CUSTOMER_CITY,
    };

    private OnFragmentInteractionListener mListener;

    private CustomerAdapter mAdapter;

    public static ListCustomersFragment newInstance() {
        ListCustomersFragment fragment = new ListCustomersFragment();
        return fragment;
    }

    public ListCustomersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CustomerAdapter(getActivity(), null, 0);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(Constants.CUSTOMER_LOADER_ID,null,this).forceLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_customers, container, false);
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id){
        mListener.onFragmentInteraction(((CustomerAdapter.CustomerViewHolder)v.getTag()).customerId);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity().getApplicationContext(),
        TreckerContract.Customers.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(final int customerId);
    }

}
