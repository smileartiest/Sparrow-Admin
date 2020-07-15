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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.smile.atozadmin.parameters.EmployeParameters;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static com.theartofdev.edmodo.cropper.CropImageView.*;
import static java.security.AccessController.getContext;

public class AddEmployeePage extends AppCompatActivity {

    ImageView pic;
    TextInputLayout name, usname, pass;
    Button addbtn;
    Uri picurl;
    ProgressDialog pd;
    StorageReference sr, sr1;
    String dbkey;
    ConstraintLayout screen;
    TempAddPic temppicurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee_page);

        pic = findViewById(R.id.add_emp_pic);
        name = findViewById(R.id.add_emp_name);
        usname = findViewById(R.id.add_emp_usname);
        pass = findViewById(R.id.add_emp_pass);
        addbtn = findViewById(R.id.add_emp_addbtn);
        screen = findViewById(R.id.add_emp_screen);

        sr = FirebaseStorage.getInstance().getReference("emppic");
        temppicurl = new TempAddPic(AddEmployeePage.this);

        pd = new ProgressDialog(AddEmployeePage.this);
        pd.setTitle("Uploading");
        pd.setMessage("Loading Please Wait....");

        generatekey();

    }

    public void generatekey() {
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("employe");
        dbkey = df.push().getKey();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            //Image Successfully Selected
            Uri imagepath = data.getData();

            CropImage.activity(imagepath).setGuidelines(Guidelines.ON).start(this);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                picurl = result.getUri();

                pic.setImageURI(picurl);
                uploadpic();
            }
        }
    }

    private String imgextenstion(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mmap = MimeTypeMap.getSingleton();
        return mmap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void uploadpic() {
        pd.show();
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

    @Override
    protected void onResume() {
        super.onResume();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getEditText().getText().toString();
                String usname1 = usname.getEditText().getText().toString();
                String pass1 = pass.getEditText().getText().toString();
                if (name1.length() != 0) {
                    if (usname1.length() != 0) {
                        if (pass1.length() != 0) {
                            DatabaseReference df = AppUtill.EMPURL;
                            EmployeParameters e = new EmployeParameters(dbkey , temppicurl.getpicurl() , name1 , usname1 , pass1 , "new");
                            df.child(dbkey).setValue(e);
                            Snackbar snackbar = Snackbar.make(screen, "add succesfull", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            temppicurl.clearpic();
                        } else {
                            pass.getEditText().setError("enter valid password");
                        }
                    } else {
                        usname.getEditText().setError("enter valid user name");
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
