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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CountributeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference("Uploads");

    private static int RESULT_LOAD_IMAGE = 1;
    private Button SendRequestBtn;
    private Button Choosefilebtn;
    private Button Uploadbtn;
    private Uri mimageUri;
    private ImageView mImageView1;
    private EditText Placename;
    private EditText Telephonenum;
    private EditText Crn;
    private EditText Email;
    private EditText DoP;
    public Boolean Approved = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countribute_fragment);


        SendRequestBtn = findViewById(R.id.Send_Request);
        Choosefilebtn = findViewById(R.id.ChooseImg);
        mImageView1 = findViewById(R.id.Img1);
        Uploadbtn = findViewById(R.id.Upload);


        Placename = findViewById(R.id.PlaceName);
        Telephonenum = findViewById(R.id.PlaceTelephone);
        Crn = findViewById(R.id.commercial_registration_number);
        Email = findViewById(R.id.PlaceEmail);
        DoP = findViewById(R.id.DescriptionOfServices);

        Choosefilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFile();
            }
        });

        Uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uploadfile();
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
            }
        });

    }

    private void OpenFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                    && data != null && data.getData() != null)
                mimageUri = data.getData();

            mImageView1.setImageURI(mimageUri);
        }
    }
     private String getFileExtension(Uri uri){
         ContentResolver cR = getContentResolver() ;
         MimeTypeMap mime = MimeTypeMap.getSingleton();
         return mime.getExtensionFromMimeType(cR.getType(uri));

     }
    private void Uploadfile(){
          if(mimageUri  != null){
              StorageReference fileReference = mStorage.child(System.currentTimeMillis()
                      + "." + getFileExtension(mimageUri));

              fileReference.putFile(mimageUri)
                      .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                          @Override
                          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                              Toast.makeText(CountributeActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                              Upload upload = new Upload(taskSnapshot.getUploadSessionUri().toString());
                              String uploadid = mDatabase.push().getKey();
                              mDatabase.child("Uploads").child(uploadid).setValue(upload);
                          }
                      })
                      .addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Toast.makeText(CountributeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                          }
                      });

          }
          else {
              Toast.makeText(this, "Please Choose a pictur to upload", Toast.LENGTH_SHORT).show();
          }
    }

}





