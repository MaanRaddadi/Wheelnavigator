package com.example.wheelnavigator;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wheelnavigator.Admin.ImgModel;
import com.example.wheelnavigator.Registration.Login;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


public class CountributeActivity extends AppCompatActivity {
        private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Place Requests");
        private DatabaseReference mDatabaseImg ;
        private DatabaseReference mDatabaseLogo ; ;
        private StorageReference mStorage = FirebaseStorage.getInstance().getReference("Uploads");
        private FirebaseAuth Auth = FirebaseAuth.getInstance();



        private Button SendRequestBtn;
        private ImageView imageView , ChooseLogo;
        private Button Uploadbtn , UploadLogo , ChooseLocation;

        private EditText Placename;
        private EditText Telephonenum;
        private EditText Crn;
        private EditText Email;
        private EditText DoP;
        public Boolean Approved = false;


        private Uri imageUri , logoUri;



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.countribute_fragment);


            SendRequestBtn = findViewById(R.id.Send_Request);

            imageView = findViewById(R.id.choose);
            ChooseLogo = findViewById(R.id.LogoChoose);

            Uploadbtn = findViewById(R.id.Upload);
            UploadLogo= findViewById(R.id.LogoUpload);
            ChooseLocation = findViewById(R.id.ChooseLocation);



            Placename = findViewById(R.id.PlaceName);
            Telephonenum = findViewById(R.id.PlaceTelephone);
            Crn = findViewById(R.id.commercial_registration_number);
            Email = findViewById(R.id.PlaceEmail);
            DoP = findViewById(R.id.DescriptionOfServices);



           ChooseLocation.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(CountributeActivity.this , MapsActivity.class));
               }
           });


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent , 1);
                }
            });


            ChooseLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent , 2);
                }
            });


            UploadLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        if(Crn.getText().toString().isEmpty()){
                            Toast.makeText(CountributeActivity.this, "Please fill out all the fields", Toast.LENGTH_LONG).show();
                        }
                        else
                        if (logoUri != null){
                            uploadLogoToFirebase(logoUri, Crn.getText().toString());
                        }else{
                            Toast.makeText(CountributeActivity.this, "Please Select a Logo", Toast.LENGTH_SHORT).show();
                        }
                }
            });







            Uploadbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(Crn.getText().toString().isEmpty()){
                        Toast.makeText(CountributeActivity.this, "Please fill out all the fields", Toast.LENGTH_LONG).show();
                    }
                    else
                    if (imageUri != null){
                        uploadToFirebase(imageUri, Crn.getText().toString());
                    }else{
                        Toast.makeText(CountributeActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            Spinner Spinner = (Spinner) findViewById(R.id.dynamic_spinner);

            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                    .createFromResource(this, R.array.PlaceTypeArray,
                            android.R.layout.simple_spinner_item);

            staticAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            Spinner.setAdapter(staticAdapter);





            SendRequestBtn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    final String PlaceNameTxt = Placename.getText().toString();
                    final String PlaceTeleTxt = Telephonenum.getText().toString();
                    final String CrnTxt = Crn.getText().toString();
                    final String EmailTxt = Email.getText().toString();
                    final String DoPTxt = DoP.getText().toString();

                    if (PlaceNameTxt.isEmpty() || PlaceTeleTxt.isEmpty() || CrnTxt.isEmpty() || EmailTxt.isEmpty() || DoPTxt.isEmpty() || Spinner.getSelectedItem() == null) {
                        Toast.makeText(CountributeActivity.this, "Please fill out all the fields", Toast.LENGTH_LONG).show();
                    } else {
                        mDatabase.child(CrnTxt).child("Name").setValue(PlaceNameTxt);
                        mDatabase.child(CrnTxt).child("Telephone Number").setValue(PlaceTeleTxt);
                        mDatabase.child(CrnTxt).child("Crn").setValue(CrnTxt);
                        mDatabase.child(CrnTxt).child("Email").setValue(EmailTxt);
                        mDatabase.child(CrnTxt).child("Details of Services").setValue(DoPTxt);
                        mDatabase.child(CrnTxt).child("Approved").setValue(Approved);
                        mDatabase.child(CrnTxt).child("PlaceType").setValue(Spinner.getSelectedItem().toString());



                        Toast.makeText(CountributeActivity.this, "Request has been Sent", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CountributeActivity.this, MainActivity.class));

                    }
                }
            });





        }



        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode ==1 && resultCode == RESULT_OK && data != null){

                imageUri = data.getData();
                imageView.setImageURI(imageUri);

            }
            if (requestCode ==2 && resultCode == RESULT_OK && data != null){

                logoUri = data.getData();
                ChooseLogo.setImageURI(logoUri);

            }


        }



        private void uploadToFirebase(Uri uri, String Crn){

            mDatabaseImg = mDatabase.child(Crn).child("Pictures");
            final StorageReference fileRef = mStorage.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            ImgModel model = new ImgModel(uri.toString());
                            String modelId = mDatabaseImg.push().getKey();
                            mDatabaseImg.child(modelId).setValue(model);
                            Toast.makeText(CountributeActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            imageView.setImageResource(R.drawable.ic_baseline_image_24);
                        }
                    });
                }
            });
        }
    private void uploadLogoToFirebase(Uri uri, String Crn){

        mDatabaseLogo = FirebaseDatabase.getInstance().getReference("Place Requests").child(Crn);
        final StorageReference fileRef = mStorage.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        ImgModel model = new ImgModel(uri.toString());
                        String modelId = mDatabaseLogo.push().getKey();
                        mDatabaseLogo.setValue(model);
                        Toast.makeText(CountributeActivity.this, "Logo Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        ChooseLogo.setImageResource(R.drawable.ic_baseline_image_24);
                    }
                });
            }
        });
    }

        private String getFileExtension(Uri mUri){

            ContentResolver cr = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(mUri));

        }




        public void onStart() {
            super.onStart();

            FirebaseUser currentUser = Auth.getCurrentUser();
            if(currentUser == null){
                startActivity(new Intent(CountributeActivity.this, Login.class));
            }


        }

    }





