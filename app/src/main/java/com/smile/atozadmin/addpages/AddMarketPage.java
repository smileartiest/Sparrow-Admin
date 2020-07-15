package com.smile.atozadmin.addpages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smile.atozadmin.R;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.controller.TempAddPic;
import com.smile.atozadmin.parameters.MarketParameters;

import java.util.ArrayList;

public class AddMarketPage extends AppCompatActivity {

    TextView nameupdate, typeupdate, categupdate, qntyupdate, priceupdate;
    TextInputLayout name, type, qnty, price;
    Spinner categspin;
    ImageView pic;
    Button addbtn;
    ImageView instock,outstock;
    ConstraintLayout screen;

    String[] categlist = {"choose category", "Fruits & Vegetables", "Foodgrains , Oil & Masala", "Bakery , Cakes & Dairy", "Beverages",
            "Snaks & Branded Foods", "Beauty & Hygiene", "Cleaning & Household", "Eggs , Meat & Fish", "Baby Care & Personal Care"};

    StorageReference sr, sr1;

    ProgressDialog pd;
    String dbkey;
    Uri picurl;
    TempAddPic temppicurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_marketdetails_page);

        name = findViewById(R.id.add_veg_name);
        categspin = findViewById(R.id.add_veg_category);
        type = findViewById(R.id.add_veg_type);
        qnty = findViewById(R.id.add_veg_qnt);
        price = findViewById(R.id.add_veg_price);

        nameupdate = findViewById(R.id.add_veg_name_update);
        categupdate = findViewById(R.id.add_veg_category_update);
        typeupdate = findViewById(R.id.add_veg_type_update);
        qntyupdate = findViewById(R.id.add_veg_qnt_update);
        priceupdate = findViewById(R.id.add_veg_price_update);

        instock = findViewById(R.id.add_veg_instock);
        outstock = findViewById(R.id.add_veg_outstock);

        pic = findViewById(R.id.add_veg_pic);
        addbtn = findViewById(R.id.add_veg_addbtn);

        screen = findViewById(R.id.add_veg_screen);

        sr = FirebaseStorage.getInstance().getReference("market");
        temppicurl = new TempAddPic(AddMarketPage.this);

        ArrayAdapter<String> ad = new ArrayAdapter<>(getApplicationContext(), R.layout.spinlist, categlist);
        categspin.setAdapter(ad);

        pd = new ProgressDialog(AddMarketPage.this);
        pd.setTitle("Uploading");
        pd.setMessage("Loading Please Wait....");

        if (getIntent().getStringExtra("t").equals("new")) {
            nameupdate.setVisibility(View.GONE);
            categupdate.setVisibility(View.GONE);
            typeupdate.setVisibility(View.GONE);
            qntyupdate.setVisibility(View.GONE);
            priceupdate.setVisibility(View.GONE);
            instock.setVisibility(View.GONE);
            outstock.setVisibility(View.GONE);
            generatekey();
        } else {

            AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()!=null){
                        MarketParameters m = dataSnapshot.getValue(MarketParameters.class);
                        Glide.with(getApplicationContext()).load(m.getMpic()).into(pic);
                        name.getEditText().setText(m.getMname());
                        type.getEditText().setText(m.getMtype());
                        qnty.getEditText().setText(m.getMqnt());
                        price.getEditText().setText(m.getMam());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            addbtn.setText("DELETE");
        }

    }

    public void generatekey() {
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("veg");
        dbkey = df.push().getKey();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            //Image Successfully Selected

            picurl = data.getData();

            pic.setImageURI(picurl);

            pd.show();

            uploadpic();

        }
    }

    private String imgextenstion(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mmap = MimeTypeMap.getSingleton();
        return mmap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void uploadpic() {
        sr1 = sr.child(System.currentTimeMillis() + "." + imgextenstion(picurl));

        sr1.putFile(picurl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        sr1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (getIntent().getStringExtra("t").equals("new")) {
                                    temppicurl.addpicurl(uri.toString());
                                    pd.dismiss();
                                } else {
                                    AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).child("mpic").setValue(uri.toString());
                                    pd.dismiss();
                                }
                                Snackbar snackbar = Snackbar.make(screen, "Picture add succesfull", Snackbar.LENGTH_SHORT);
                                snackbar.show();

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "faild", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        nameupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).child("mname").setValue(name.getEditText().getText().toString());
            }
        });

        categupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).child("mcatg").setValue(categspin.getSelectedItem().toString());
            }
        });

        typeupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).child("mtype").setValue(type.getEditText().getText().toString());
            }
        });

        qntyupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).child("mqnt").setValue(qnty.getEditText().getText().toString());
            }
        });

        priceupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).child("mam").setValue(price.getEditText().getText().toString());
            }
        });

        instock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).child("stock").setValue("instock");
            }
        });

        outstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).child("stock").setValue("outstock");
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addbtn.getText().toString().equals("DELETE")) {
                    AppUtill.MARKETURL.child(getIntent().getStringExtra("id")).removeValue();
                    Snackbar snackbar = Snackbar.make(screen, "remove succesfull", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    finish();
                } else {
                    String name1 = name.getEditText().getText().toString();
                    String type1 = type.getEditText().getText().toString();
                    String am1 = price.getEditText().getText().toString();
                    String qnt1 = qnty.getEditText().getText().toString();
                    if (name1.length() != 0) {
                        if (type1.length() != 0) {
                            if (am1.length() != 0) {
                                if (qnt1.length() != 0) {
                                    DatabaseReference df = AppUtill.MARKETURL;
                                    MarketParameters m = new MarketParameters(dbkey , temppicurl.getpicurl() , name1 , type1 ,categspin.getSelectedItem().toString() ,qnt1 ,am1,"instock"  );
                                    df.child(dbkey).setValue(m);
                                    Snackbar snackbar = Snackbar.make(screen, "add succesfull", Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                    temppicurl.clearpic();
                                } else {
                                    qnty.getEditText().setError("enter valid quendity");
                                }
                            } else {
                                price.getEditText().setError("enter valid amount");
                            }
                        } else {
                            type.getEditText().setError("enter valid type");
                        }
                    } else {
                        name.getEditText().setError("enter valid name");
                    }
                }
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
