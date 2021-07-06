package com.example.elibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity  {

    DBHelper DB;

    CircleImageView userphoto;
    static int PReqCode=1;
    static int REQUESTCODE=1;
    Uri pickedImgUri;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       /* FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ProfileFragment myFragment = new ProfileFragment();*/

        DB=new DBHelper(this);

        //For alreadyHaveAccount textview

        TextView btn=findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        //For Register Button

        Button register=findViewById(R.id.btnRegister);
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText email=(EditText)findViewById(R.id.email);
        final EditText password=findViewById(R.id.password);
        final EditText confirmpassword=findViewById(R.id.confirmpd);
         userphoto=findViewById(R.id.userphoto);


        userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermission();
                }
                else{
                    openGallery();
                }

            }
        });

        mAuth=FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user=username.getText().toString();
                String emailid=email.getText().toString();
                String pass=password.getText().toString();
                String cmpd=confirmpassword.getText().toString();

                //Firebased register

                if(user.equals("") || emailid.equals("") || pass.equals("") || cmpd.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    CreateUserAccout(emailid,user,pass);
                }



                /*if(user.equals("") || emailid.equals("") || pass.equals("") || cmpd.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(cmpd)){
                        Boolean checkuser = DB.checkuseremail(emailid);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user,emailid,pass);
                            if(insert==true){
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                               //i.putExtra("data_user",user);
                               // i.putExtra("data_email",emailid);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User already exists! please SignIn", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Passwords not matching.", Toast.LENGTH_SHORT).show();
                    }
                }*/
               /* Bundle b = new Bundle();
                b.putString("Username",user);
                myFragment.setArguments(b);
                fragmentTransaction.add(R.id.fragment_container, myFragment).commit();*/

            }
        });


    }

    //User Registration code

    private void CreateUserAccout(String emailid, final String username, String password) {

        mAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user account created successfully
                    Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    updateUserInfo(username,pickedImgUri,mAuth.getCurrentUser());
                }
                else{
                    //account creation failed
                    Toast.makeText(RegisterActivity.this, "Account Creation failed", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    //update user photo and name
    private void updateUserInfo(final String username, Uri pickedImgUri, final FirebaseUser currentUser){
        //first we need to upload user photo to firebase storage and get uri

        StorageReference mStorage= FirebaseStorage.getInstance().getReference().child("users photos");
        final StorageReference imageFilePath=mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //image uploaded successfully
                //now we can get our image uri
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //uri contain user img uri
                        UserProfileChangeRequest profileUpdate=new UserProfileChangeRequest.Builder().setDisplayName(username).setPhotoUri(uri).build();

                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //user info updated successfully
                                    Toast.makeText(RegisterActivity.this, "Register Complete", Toast.LENGTH_SHORT).show();
                                    updateUI();
                                }
                            }
                        });
                    }
                });
            }
        });

    }

    private void updateUI() {
        Intent homeActivity=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(homeActivity);
        finish();

    }


    //Code for inserting profile picture of user

    private void openGallery() {
        //TODO:Open Gallery Intent
        Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESTCODE);

    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterActivity.this, "Please accept for required permission ", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }
        }
        else
            openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data!=null){
            //the user has successfully picked an image
            pickedImgUri=data.getData();
            userphoto.setImageURI(pickedImgUri);

        }
    }
}

