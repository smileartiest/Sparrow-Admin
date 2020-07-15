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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smile.atozadmin.R;
import com.smile.atozadmin.controller.AppUtill;
import com.smile.atozadmin.controller.TempAddPic;
import com.smile.atozadmin.parameters.DressParameters;

public class AddDRessPage extends AppCompatActivity {

    ImageView pic;
    TextView addimg1,addimg2;
    TextInputLayout name, type, size, amount, offer;
    Button addbtn;
    Uri picurl;
    ProgressDialog pd;
    StorageReference sr, sr1;
    String dbkey;
    ConstraintLayout screen;
    String[] categlist = {" - select - ", "Mens", "Womens", "Kids", "Bag"};
    Spinner categspin;
    TempAddPic temppicurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dress_page);

        pic = findViewById(R.id.add_dress_pic);
        name = findViewById(R.id.add_dress_name);
        type = findViewById(R.id.add_dress_type);
        size = findViewById(R.id.add_dress_size);
        amount = findViewById(R.id.add_dress_amount);
        offer = findViewById(R.id.add_dress_offer);
        addbtn = findViewById(R.id.add_dress_addbtn);
        screen = findViewById(R.id.add_dress_screen);
        categspin = findViewById(R.id.add_dress_category);
        addimg1 = findViewById(R.id.add_dress_pic_1);
        addimg2 = findViewById(R.id.add_dress_pic_2);

        temppicurl = new TempAddPic(AddDRessPage.this);

        ArrayAdapter<String> ad = new ArrayAdapter<>(getApplicationContext(), R.layout.spinlist, categlist);
        categspin.setAdapter(ad);

        sr = FirebaseStorage.getInstance().getReference("dresspic");

        pd = new ProgressDialog(AddDRessPage.this);
        pd.setTitle("Uploading");
        pd.setMessage("Loading Please Wait....");

        generatekey();

    }

    public void generatekey() {
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("dress");
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
        else if (requestCode == 1001 && resultCode == Activity.RESULT_OK && data != null) {
            //Image Successfully Selected
            picurl = data.getData();
            pic.setImageURI(picurl);
            pd.show();
            uploadpic1();
        }else if(requestCode == 1002 && resultCode == Activity.RESULT_OK && data != null){
            picurl = data.getData();
            pic.setImageURI(picurl);
            pd.show();
            uploadpic1();
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

                                temppicurl.addpicurl(uri.toString());
                                pd.dismiss();
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

    public void uploadpic1() {
        sr1 = sr.child(System.currentTimeMillis() + "." + imgextenstion(picurl));

        sr1.putFile(picurl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        sr1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String key = AppUtill.DRESSPICURL.push().getKey();
                                AppUtill.DRESSPICURL.child(dbkey).child(key).setValue(uri.toString());

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Upload succesfull", Toast.LENGTH_SHORT).show();

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

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getEditText().getText().toString();
                String type1 = type.getEditText().getText().toString();
                String size1 = size.getEditText().getText().toString();
                String am1 = amount.getEditText().getText().toString();
                String off1 = offer.getEditText().getText().toString();
                if (name1.length() != 0) {
                    if (type1.length() != 0) {
                        if (size1.length() != 0) {
                            if (am1.length() != 0) {
                                if (off1.length() != 0) {
                                    DatabaseReference df = AppUtill.DRESSURL;
                                    DressParameters d = new DressParameters(dbkey ,name1 ,type1 ,categspin.getSelectedItem().toString(),size.getEditText().getText().toString(),am1,off1 , temppicurl.getpicurl() , "instock" );
                                    df.child(dbkey).setValue(d);
                                    Snackbar snackbar = Snackbar.make(screen, "add succesfull", Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                    temppicurl.clearpic();

                                } else {
                                    offer.getEditText().setError("enter valid offer");
                                }
                            } else {
                                amount.getEditText().setError("enter valid amount");
                            }
                        }else { size.getEditText().setError("enter valid size"); }
                    } else {
                        type.getEditText().setError("enter valid type");
                    }
                } else {
                    name.getEditText().setError("enter valid name");
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

        addimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addimg1.setBackgroundResource(R.drawable.bordergreen);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1001);
            }
        });

        addimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addimg1.setBackgroundResource(R.drawable.bordergreen);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1002);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
