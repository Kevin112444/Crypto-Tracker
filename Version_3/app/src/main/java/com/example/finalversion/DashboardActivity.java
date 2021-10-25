package com.example.finalversion;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    String[] title;
    String[] description;
    int[] icon;
    ArrayList<Model> arrayList = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Crypto Tracker");
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#72bed4"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        title = new String[]{"Bitcoin","Ethereum","Tether","Binance Coin"};
        description =  new String[]{"Bitcoin Description...","Ethereum Description...","Tether Description...","Binance Coin Description..."};
        icon = new int[]{R.drawable.btc,R.drawable.eth,R.drawable.tether,R.drawable.bnb};

        listView = findViewById(R.id.listView);

        for (int i = 0; i <title.length; i++){
            Model model = new Model(title[i],description[i],icon[i]);
            //bind all strings in an array
            arrayList.add(model);
        }
        //pass results to listViewAdapter class
        adapter = new ListViewAdapter(this,arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("" );
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}