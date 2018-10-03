package nodebase.tech.akshaynexus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nodebase.org.nodebasewallet.R;
import nodebase.org.nodebasewallet.ui.base.BaseDrawerActivity;
import nodebase.org.nodebasewallet.ui.contacts_activity.ContactsActivity;
import nodebase.org.nodebasewallet.ui.donate.DonateActivity;
import nodebase.org.nodebasewallet.ui.settings_activity.SettingsActivity;
import nodebase.org.nodebasewallet.ui.wallet_activity.WalletActivity;

public class MasternodeListView extends BaseDrawerActivity implements MyRecyclerViewAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    MyRecyclerViewAdapter adapter;
    private DrawerLayout drawer;
    ArrayList<HashMap<String, String>> masternodeapilist;
    List<JsonData> mJsonDataList;

    TextView totalcount;    private NavigationView navigationView;
    @Override
    protected void onCreateView(Bundle savedInstanceState, ViewGroup container) {
       getLayoutInflater().inflate(R.layout.fragment_masternode,container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Masternodes");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        totalcount = (TextView) findViewById(R.id.mncountlabel);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mJsonDataList = Utility.getJsonData();
        RecyclerView recyclerView = findViewById(R.id.rvMasternodeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, mJsonDataList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        setNavigationMenuItemChecked(3);

        // data to populate the RecyclerView with
        Fuel.get("http://24.190.71.106:81/url", null).responseString(new Handler<String>() {
            @Override
            public void failure(Request request, Response response, FuelError error) {
                //do something when it is failure
            }

            @Override
            public void success(Request request, Response response, String data) {
                //do something when it is successful
                try {
                    String inputString = data;
                    inputString = inputString.replace("\\\\n", "");
                    inputString = inputString.replace("\\\\\\", "");
                    inputString = inputString.substring(3);
                    System.out.println(inputString);
                    JSONArray jsonArray = new JSONArray(inputString);
                    mJsonDataList = Utility.jsonToList(adapter, jsonArray);
                    totalcount.setText(mJsonDataList.size() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

    }
    protected void setNavigationMenuItemChecked(int pos){
        navigationView.getMenu().getItem(pos).setChecked(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //to prevent current item select over and over
        if (item.isChecked()){
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

        if (id == R.id.nav_wallet) {
            Intent intent = new Intent(this,WalletActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_address) {
            startActivity(new Intent(this, ContactsActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_donations){
            startActivity(new Intent(this, DonateActivity.class));
        }
        else if (id == R.id.nav_masternodelist){
            startActivity(new Intent(this, MasternodeListView.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
