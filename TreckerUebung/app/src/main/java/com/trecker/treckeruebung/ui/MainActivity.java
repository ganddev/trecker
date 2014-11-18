package com.trecker.treckeruebung.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.trecker.treckeruebung.R;
import com.trecker.treckeruebung.api.ApiService;


public class MainActivity extends ActionBarActivity implements ListCustomersFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,ListCustomersFragment.newInstance(),"customers").commit();
        //ApiService.getCustomers(this);
        //ApiService.getFields(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(final int customerId) {
        Log.d(TAG, "Clicked on customer: " +customerId);
        final FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        ListFieldsFragment lfm = ListFieldsFragment.newInstance(customerId);
        ft.replace(R.id.fragment_container,lfm,"fields");
        ft.addToBackStack("fields");
        ft.commit();
    }
}
