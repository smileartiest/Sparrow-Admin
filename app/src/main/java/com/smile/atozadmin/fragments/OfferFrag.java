package com.smile.atozadmin.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smile.atozadmin.R;
import com.smile.atozadmin.models.OfferHold;
import com.smile.atozadmin.parameters.OfferParameters;

public class OfferFrag extends Fragment {

    View v;
    RecyclerView offerlist;
    FloatingActionButton addoffer;
    DatabaseReference df;
    StorageReference sr1,sr;
    ProgressDialog pd;
    ConstraintLayout screen;

    Uri picurl;

    public OfferFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_offer, container , false);
        offerlist = v.findViewById(R.id.offer_list);
        addoffer = v.findViewById(R.id.offer_addoffer);
        screen = v.findViewById(R.id.offer_screen);

        offerlist.setHasFixedSize(true);
        offerlist.setLayoutManager(new LinearLayoutManager(getContext()));

        sr = FirebaseStorage.getInstance().getReference("offerpic");
        df = FirebaseDatabase.getInstance().getReference("offer");

        pd = new ProgressDialog(getContext());
        pd.setTitle("Uploading");
        pd.setMessage("Loading Please Wait....");

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<OfferParameters, OfferHold> frecyle = new FirebaseRecyclerAdapter<OfferParameters, OfferHold>(
                OfferParameters.class , R.layout.row_offer, OfferHold.class , df
        ) {
            @Override
            protected void populateViewHolder(OfferHold offerHold, OfferParameters offerParameters, int i) {
                offerHold.setdetails(getContext() , offerParameters.getId() , offerParameters.getPicurl());
            }
        };
        offerlist.setAdapter(frecyle);

    }

    @Override
    public void onResume() {
        super.onResume();

        addoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            //Image Successfully Selected

            picurl = data.getData();

            pd.show();

            uploadpic();

        }
    }

    private String imgextenstion(Uri uri)
    {
        ContentResolver cr = getActivity().getContentResolver();
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

                                String key = df.push().getKey();
                                OfferParameters o = new OfferParameters(key , uri.toString());
                                df.child(key).setValue(o);

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
                        Snackbar snackbar = Snackbar.make(screen , "faild" , Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });
    }


}
