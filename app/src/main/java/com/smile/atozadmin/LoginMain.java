package com.smile.atozadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smile.atozadmin.fragments.DressFrag;
import com.smile.atozadmin.fragments.EmployeeFrag;
import com.smile.atozadmin.fragments.HomeFrag;
import com.smile.atozadmin.fragments.LocationFrag;
import com.smile.atozadmin.fragments.NotificationFrag;
import com.smile.atozadmin.fragments.OfferFrag;
import com.smile.atozadmin.fragments.OrderFrag;
import com.smile.atozadmin.fragments.PaymentFrag;
import com.smile.atozadmin.fragments.MarketFrag;
import com.smile.atozadmin.retrofit.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMain extends AppCompatActivity {

    Toolbar myactionbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mnavigationlisener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_home:
                    loadfrag(new HomeFrag());
                    getSupportActionBar().show();
                    return true;
                case R.id.nav_drees:
                    loadfrag(new DressFrag());
                    getSupportActionBar().hide();
                    return true;
                case R.id.nav_market:
                    loadfrag(new MarketFrag());
                    getSupportActionBar().hide();
                    return true;
                case R.id.nav_offer:
                    loadfrag(new OfferFrag());
                    getSupportActionBar().hide();
                    return true;
                case R.id.nav_Employees:
                    loadfrag(new EmployeeFrag());
                    getSupportActionBar().hide();
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        myactionbar = findViewById(R.id.toolbar);
        setSupportActionBar(myactionbar);

        BottomNavigationView mbBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mbBottomNavigationView.setOnNavigationItemSelectedListener(mnavigationlisener);

        loadfrag(new HomeFrag());

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM ID ", "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("FCM ID ", token);
                        addtoken(token);
                    }
                });
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    void addtoken(String token) {
        Call<String> call = ApiUtil.getServiceClass().updateid("admin" , token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body().equals("1")){
                        Toast.makeText(LoginMain.this, "Update successfull", Toast.LENGTH_SHORT).show();
                    }else if(response.body().equals("10")){
                        Toast.makeText(LoginMain.this, "Update error", Toast.LENGTH_SHORT).show();
                    }else if(response.body().equals("2")){
                        Toast.makeText(LoginMain.this, "insert successfull", Toast.LENGTH_SHORT).show();
                    }else if(response.body().equals("20")){
                        Toast.makeText(LoginMain.this, "insert error", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginMain.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginMain.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadfrag(Fragment frag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout , frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.loginmenu , menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_location:
                loadfrag(new LocationFrag());
                getSupportActionBar().hide();
                break;
            case R.id.menu_myorders:
                loadfrag(new OrderFrag());
                getSupportActionBar().hide();
                break;
            case R.id.menu_exit:
                finish();
            case R.id.menu_notification:
                loadfrag(new NotificationFrag());
                getSupportActionBar().hide();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ad = new AlertDialog.Builder(LoginMain.this);
        ad.setTitle("Exit !!");
        ad.setMessage("are you conform to exit");
        ad.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        ad.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }
}
