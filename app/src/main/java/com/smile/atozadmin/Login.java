package com.smile.atozadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.smile.atozadmin.controller.TempUser;

public class Login extends AppCompatActivity {

    TextInputLayout usname,pinno;
    Button login;

    TempUser temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usname = findViewById(R.id.login_usname);
        pinno = findViewById(R.id.login_pinnumber);
        login = findViewById(R.id.login_loginbtn);

        temp = new TempUser(Login.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(temp.getuid()!=null && temp.getpass()!=null){
            startActivity(new Intent(getApplicationContext() , LoginMain.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usname1 = usname.getEditText().getText().toString();
                String pinno1 = pinno.getEditText().getText().toString();

                if(usname1.length()!=0){
                    if(pinno1.length()!=0){
                        if(usname1.equals("Admin") && pinno1.equals("Admin123")){

                            temp.adduserdetails("Admin" , "Admin123");

                            startActivity(new Intent(getApplicationContext() , LoginMain.class));
                            finish();
                        }else {
                            AlertDialog.Builder ad = new AlertDialog.Builder(Login.this);
                            ad.setTitle("Oops !!");
                            ad.setMessage("Invalid User or Password");
                            ad.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ad.show();
                        }
                    }else { pinno.getEditText().setError("enterv valid pin number");}
                }else { usname.getEditText().setError("enter valid user name");}

            }
        });

    }
}
