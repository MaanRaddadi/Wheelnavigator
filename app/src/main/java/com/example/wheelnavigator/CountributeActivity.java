package com.example.wheelnavigator;

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

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class CountributeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference("Uploads");
    private FirebaseAuth Auth = FirebaseAuth.getInstance();


    private int uploads = 0;
    private static int RESULT_LOAD_IMAGE = 1;
    private Button SendRequestBtn;
    private Button Choosefilebtn;
    private Button Uploadbtn;
    private EditText Placename;
    private EditText Telephonenum;
    private EditText Crn;
    private EditText Email;
    private EditText DoP;
    public Boolean Approved = false;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private TextView ChooseImgtxt;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countribute_fragment);


        SendRequestBtn = findViewById(R.id.Send_Request);
        Choosefilebtn = findViewById(R.id.ChooseImg);

        Uploadbtn = findViewById(R.id.Upload);


        ChooseImgtxt = findViewById(R.id.imageCountxt);

        Placename = findViewById(R.id.PlaceName);
        Telephonenum = findViewById(R.id.PlaceTelephone);
        Crn = findViewById(R.id.commercial_registration_number);
        Email = findViewById(R.id.PlaceEmail);
        DoP = findViewById(R.id.DescriptionOfServices);


        Choosefilebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(Crn.getText().toString().isEmpty() == true){
                    Toast.makeText(CountributeActivity.this, "Please fill out all the field before choosing images", Toast.LENGTH_LONG).show();
                }
                else {
                    OpenFile();
                }
            }
        });

        Uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(Crn.getText().toString());
            }
        });






        SendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String PlaceNameTxt = Placename.getText().toString();
                final String PlaceTeleTxt = Telephonenum.getText().toString();
                final String CrnTxt = Crn.getText().toString();
                final String EmailTxt = Email.getText().toString();
                final String DoPTxt = DoP.getText().toString();

                mDatabase.child("Place Requests").child(CrnTxt).child("Name").setValue(PlaceNameTxt);
                mDatabase.child("Place Requests").child(CrnTxt).child("Telephone Number").setValue(PlaceTeleTxt);
                mDatabase.child("Place Requests").child(CrnTxt).child("Crn").setValue(CrnTxt);
                mDatabase.child("Place Requests").child(CrnTxt).child("Email").setValue(EmailTxt);
                mDatabase.child("Place Requests").child(CrnTxt).child("Details of Services").setValue(DoPTxt);
                mDatabase.child("Place Requests").child(CrnTxt).child("Approved :").setValue(Approved);

                Toast.makeText(CountributeActivity.this, "Request has been Sent", Toast.LENGTH_LONG).show();
                startActivity(new Intent (CountributeActivity.this, MainActivity.class));

            }
        });

    }

    private void OpenFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    int CurrentImageSelect = 0;

                    while (CurrentImageSelect < count) {
                        Uri imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                        ImageList.add(imageuri);
                        CurrentImageSelect = CurrentImageSelect + 1;
                    }
                    ChooseImgtxt.setVisibility(View.VISIBLE);
                    ChooseImgtxt.setText("You Have Selected " + ImageList.size() + " Pictures");

                    Choosefilebtn.setVisibility(View.GONE);
                }

            }

        }

    }



    public void upload(String Crn) {



        final StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child(Crn).child("ImageFolder");
        for (uploads = 0; uploads < ImageList.size(); uploads++) {
            Uri Image = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("images/");

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            SendLink(url, Crn);
                        }
                    });

                }
            });


        }


    }





    private void SendLink(String url, String Crn) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("link", url);
        mDatabase.child("Place Requests").child(Crn).child("Pictures").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                ChooseImgtxt.setText("Image Uploaded Successfully");

                ImageList.clear();
            }
        });


    }

    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = Auth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(CountributeActivity.this, Login.class));
        }


    }

}





