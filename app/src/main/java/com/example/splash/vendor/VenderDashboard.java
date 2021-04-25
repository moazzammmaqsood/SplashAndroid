package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splash.R;
import com.example.splash.adapters.VendorDashboardAdapter;
import com.example.splash.data.model.ClientUserModel;

import java.util.ArrayList;
import java.util.List;

public class VenderDashboard extends AppCompatActivity {

    TextView vendorname ;
    TextView date;
    TextView Deliverybutton;
    TextView Requestedbutton;
    RecyclerView  recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_dashboard);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        vendorname =  findViewById(R.id.vendorname);
        date= findViewById(R.id.date);
        Deliverybutton = findViewById(R.id.deliverybtn);
        Requestedbutton = findViewById(R.id.requestedbtn);
        recyclerView =findViewById(R.id.dashboardrev);


        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<ClientUserModel> list=new ArrayList<>();
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));
        list.add(new ClientUserModel(1,"Moazzam", "87A",60,2,2));


        adapter = new VendorDashboardAdapter(list);
        recyclerView.setAdapter(adapter);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboardmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.payments) {
            Intent i=new Intent(this,Payments.class);
            startActivity(i);

        } else if(id == R.id.earnings) {
            Intent i=new Intent(this,Payments.class);
            startActivity(i);

        } else if(id == R.id.addclient) {
            Intent i=new Intent(this,AddClient.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
