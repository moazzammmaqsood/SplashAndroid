package com.nouveau.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nouveau.splash.Api.interfaces.VendorApi;
import com.nouveau.splash.Api.modal.SplashUser;
import com.nouveau.splash.Api.modal.vendor.UserClient;
import com.nouveau.splash.R;
import com.nouveau.splash.adapters.AllClientAdapter;
import com.nouveau.splash.utils.ApplicationInstance;
import com.nouveau.splash.utils.SessionManagement;
import com.nouveau.splash.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActivity extends AppCompatActivity {


    private static final String TAG = "CLIENTACTIVITY";
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    Activity activity;
    private SearchView searchView;
    private static AllClientAdapter adapter;
    List<UserClient> list=new ArrayList<UserClient>();
    ImageView back;
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        progressbar=findViewById(R.id.progressbar);
        activity=this;
        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();

        if(user==null){
            Log.e(TAG, "onCreate: user null" );
            SessionManagement.getSessionManagement(this).logoutUser(this);
        }

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this ,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        String token= Utils.getToken(user.getToken());
        Call<List<UserClient>> vendor= vendorApi.v1getclients(token);
        showProgress();
        vendor.enqueue(new Callback<List<UserClient>>() {
            @Override
            public void onResponse(Call<List<UserClient>> call, Response<List<UserClient>> response) {
                switch(response.code())  {
                    case 200:
                        Log.e(TAG, "onResponse: "+response.message() );
                        list=response.body();
                        List<UserClient> enabledClients=new ArrayList<>();
                        for (UserClient clients:
                             list) {

                            if(clients.getStatus().equals("E")){
                                enabledClients.add(clients);
                            }

                        }
                        adapter = new AllClientAdapter(enabledClients,activity);
                        recyclerView.setAdapter(adapter);
                        hideProgress();
                        break;

                    case 401 :
                        Utils.Message(response.message(),activity);
                        SessionManagement.getSessionManagement(activity).logoutUser(activity);
                        break;
                    default:
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ClientActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                         hideProgress();
                }
            }

            @Override
            public void onFailure(Call<List<UserClient>> call, Throwable t) {
                Utils.Message("512"+t.getMessage(),activity);
                hideProgress();
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
//                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if(adapter!=null)        adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgress(){
        progressbar.setVisibility(View.VISIBLE);

    }
    public void hideProgress(){
        progressbar.setVisibility(View.GONE);
    }
}
