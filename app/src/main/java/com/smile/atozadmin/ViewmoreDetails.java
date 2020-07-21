package com.smile.atozadmin;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.parameters.AddressParameters;
import com.smile.atozadmin.parameters.BillingParameters;
import com.smile.atozadmin.parameters.DeliveryParameters;
import com.smile.atozadmin.parameters.EmployeParameters;
import com.smile.atozadmin.parameters.OrderPatameters;

public class ViewmoreDetails extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    TextView itemlisttxt, nodelisttxt, pricelisttxt, amount, csname, csno, csaddress, odate, dbname, dbno, ddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmore_details);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.vmore_map);
        mapFragment.getMapAsync(this);

        itemlisttxt = findViewById(R.id.vmore_itemlist);
        nodelisttxt = findViewById(R.id.vmore_nodelist);
        pricelisttxt = findViewById(R.id.vmore_pricelist);
        amount = findViewById(R.id.vmore_amount);
        csname = findViewById(R.id.vmore_csname);
        csno = findViewById(R.id.vmore_mobno);
        csaddress = findViewById(R.id.vmore_address);
        odate = findViewById(R.id.vmore_orderdate);
        dbname = findViewById(R.id.vmore_dbname);
        dbno = findViewById(R.id.vmore_dbmobno);
        ddate = findViewById(R.id.vmore_ddate);

        AppUtill.ORDERURl.child(getIntent().getStringExtra("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    OrderPatameters o = dataSnapshot.getValue(OrderPatameters.class);
                    csno.setText(o.getUid());
                    if (o.getOdate() != null) {
                        odate.setText(o.getOdate()); }
                    String name1 = o.getName();
                    String qnt1 = o.getQnt();
                    String size1 = o.getSize();
                    String am1 = o.getAm();

                    final String[] nametemp = name1.split(",");
                    final String[] qnttemp = qnt1.split(",");
                    final String[] sizetemp = size1.split(",");
                    final String[] amtemp = am1.split(",");

                    final StringBuilder servlist1 = new StringBuilder();
                    final StringBuilder qntlist1 = new StringBuilder();
                    final StringBuilder amlist1 = new StringBuilder();

                    for (int i = 0; i < nametemp.length; i++) {
                        servlist1.append(nametemp[i] + "  " + sizetemp[i] + "\n" + "\n");
                        qntlist1.append(qnttemp[i] + "\n" + "\n");
                        amlist1.append((Integer.parseInt(qnttemp[i]) * Integer.parseInt(amtemp[i])) + "\n" + "\n");
                    }

                    itemlisttxt.setText(servlist1.toString());
                    nodelisttxt.setText(qntlist1.toString());
                    pricelisttxt.setText(amlist1.toString());

                    getaddressdetails(o.getUid(), o.getAddres());
                    getusername(o.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AppUtill.DELIVERYURl.child(getIntent().getStringExtra("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    DeliveryParameters d = dataSnapshot.getValue(DeliveryParameters.class);
                    dbname.setText(d.getName());
                    dbno.setText(d.getPhone());
                    ddate.setText(d.getDdate());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AppUtill.BILLINGURl.child(getIntent().getStringExtra("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    BillingParameters b = dataSnapshot.getValue(BillingParameters.class);
                    amount.setText(b.getTotal_amount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void getaddressdetails(String uid, String id) {
        AppUtill.ADDRESURL.child(uid).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    AddressParameters a = dataSnapshot.getValue(AddressParameters.class);
                    csaddress.setText(a.getAddress());
                    LatLng userpoint = new LatLng(Double.parseDouble(a.getLat()), Double.parseDouble(a.getLang()));
                    mMap.addMarker(new MarkerOptions().position(userpoint).title("USER").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.usloc_icon)));
                    mMap.setIndoorEnabled(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userpoint, 15f));
                } else {
                    csaddress.setText("none");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void getusername(String id) {
        AppUtill.REGURL.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    csname.setText(dataSnapshot.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (checkLocationPermission()) {
            mMap.setMyLocationEnabled(true);
        }

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
