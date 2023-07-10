package com.example.miniwhatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.miniwhatsapp.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettingsLayout extends AppCompatActivity {
    ImageView userImage , qrCode;
    EditText userNameEditText , userStatusEditText;
    FirebaseAuth firebaseAuth;

    Button btn;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;


    HashMap<String , Object> updateData;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        setIds();

            try {
                // Generate QR code
                Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                BitMatrix bitMatrix = new QRCodeWriter().encode("https://www.linkedin.com/in/vishal0210/", BarcodeFormat.QR_CODE, 1024, 1024, hints);

                // Convert BitMatrix to Bitmap
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.GREEN : Color.WHITE);
                    }
                }

                // Set the generated QR code bitmap to the ImageView
                qrCode.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString();
                String status = userStatusEditText.getText().toString();

                updateData = new HashMap<>();

                updateData.put("userName" , userName);
                updateData.put("about" , status);

                firebaseDatabase.getReference().child("Users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(updateData);

            }
        });



        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if (snapshot.hasChild("profile picture")) {
                            String profilePictureUrl = snapshot.child("profile picture").getValue(String.class);
                            user.setProfilePicture(profilePictureUrl);
                        }

                        assert user != null;
                        Picasso.get()
                                .load(user.getProfilePicture())
                                .placeholder(R.drawable.whatsapp)
                                .into(userImage);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , 33);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null){
            Uri imageFile = data.getData();
            userImage.setImageURI(imageFile);


            final StorageReference storageReference = firebaseStorage.getReference().child("Profile Pictures")
                    .child(FirebaseAuth.getInstance().getUid());


            storageReference.putFile(imageFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profile picture").setValue(uri.toString());

                            Toast.makeText(SettingsLayout.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
    }

    public void setIds(){
        qrCode = findViewById(R.id.SettingLayoutQrImage);
        userImage = findViewById(R.id.SettingsLayoutSetUserImage);
        userNameEditText = findViewById(R.id.SettingsLayoutUserNameEditText);
        userStatusEditText = findViewById(R.id.SettingsLayoutStatusEditText);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        btn = findViewById(R.id.SettingsLayoutButton);
        firebaseStorage = FirebaseStorage.getInstance();
    }
}