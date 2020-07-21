package com.smile.atozadmin.fulldetails;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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
import com.smile.atozadmin.parameters.DressParameters;

public class DressFullDetails extends AppCompatActivity {

    ImageView pic,instock,outstock;
    EditText name,type,price,offer,size;
    Button nameupdate,typeupdate,priceupdate,offerupdate,size_update,delete;
    TextView name_title,type_title,prize_title,size_title;
    DatabaseReference df;
    StorageReference sr,sr1;
    Uri picurl;
    ProgressDialog pd;
    ConstraintLayout screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dress_full_details);

        pic = findViewById(R.id.more_pic);
        name = findViewById(R.id.more_name);
        type = findViewById(R.id.more_type);
        price = findViewById(R.id.more_price);
        offer = findViewById(R.id.more_offer);
        size = findViewById(R.id.more_size);

        instock = findViewById(R.id.more_instock);
        outstock = findViewById(R.id.more_outstock);

        nameupdate = findViewById(R.id.more_name_update);
        typeupdate = findViewById(R.id.more_type_update);
        priceupdate = findViewById(R.id.more_price_update);
        offerupdate = findViewById(R.id.more_offer_update);
        size_update = findViewById(R.id.more_size_update);

        name_title = findViewById(R.id.more_name_title);
        type_title = findViewById(R.id.more_type_title);
        prize_title = findViewById(R.id.more_price_title);
        size_title = findViewById(R.id.more_size_title);

        delete = findViewById(R.id.more_delete_icon);

        screen = findViewById(R.id.dress_full_screen);

        sr = FirebaseStorage.getInstance().getReference("dresspic");
        if(getIntent().getStringExtra("f").equals("dress")){
            df = AppUtill.DRESSURL.child(getIntent().getStringExtra("id"));
        }else{
            df = AppUtill.ELECTRONICURL.child(getIntent().getStringExtra("id"));
            name_title.setText("Brand Name");
            type_title.setText("Type");
            prize_title.setText("Mobile Price");
            size_title.setText("Mobile Colour");
        }

        pd = new ProgressDialog(DressFullDetails.this);
        pd.setTitle("Uploading");
        pd.setMessage("Loading Please Wait....");

        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    DressParameters d = dataSnapshot.getValue(DressParameters.class);
                    name.setText(d.getDname());
                    type.setText(d.getDtype());
                    price.setText(d.getDam());
                    offer.setText(d.getDoff());
                    size.setText(d.getSize());
                    Glide.with(getApplicationContext()).load(dataSnapshot.child("dpic").getValue().toString()).into(pic);

                }else {
                    name.setText("");
                    type.setText("");
                    price.setText("");
                    offer.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
            }
        });

        nameupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.child("dname").setValue(name.getText().toString());
                Snackbar snackbar = Snackbar.make(screen , "Name add succesfull" , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        typeupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.child("dtype").setValue(type.getText().toString());
                Snackbar snackbar = Snackbar.make(screen , "Type add succesfull" , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        priceupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.child("dam").setValue(price.getText().toString());
                Snackbar snackbar = Snackbar.make(screen , "Price add succesfull" , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        offerupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.child("doff").setValue(offer.getText().toString());
                Snackbar snackbar = Snackbar.make(screen , "offer add succesfull" , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.removeValue();
                Snackbar snackbar = Snackbar.make(screen , "remove succesfull" , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        size_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                df.child("size").setValue(size.getText().toString());
                Snackbar snackbar = Snackbar.make(screen , "size add succesfull" , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        instock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.DRESSURL.child(getIntent().getStringExtra("id")).child("stock").setValue("instock");
            }
        });

        outstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtill.DRESSURL.child(getIntent().getStringExtra("id")).child("stock").setValue("outstock");
            }
        });

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

    private String imgextenstion(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mmap = MimeTypeMap.getSingleton();
        return mmap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void uploadpic()
    {
        sr1 = sr.child(System.currentTimeMillis() + "." + imgextenstion(picurl));

        sr1.putFile(picurl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        sr1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                df.child("dpic").setValue(uri.toString());
                                pd.dismiss();

                                Snackbar snackbar = Snackbar.make(screen , "Picture add succesfull" , Snackbar.LENGTH_SHORT);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
