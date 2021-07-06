package com.example.elibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity {

    DBHelper DB;

    Button btnSignin;
    private FirebaseAuth mAuth;
    Intent HomeActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //for SignUp textview

        TextView btn=findViewById(R.id.textViewSignUp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, com.example.elibrary.RegisterActivity.class));
            }
        });

        final EditText username=(EditText)findViewById(R.id.inputEmail);
        final EditText password=(EditText)findViewById(R.id.inputPassword);
        btnSignin=(Button)findViewById(R.id.btnsignin);
        mAuth=FirebaseAuth.getInstance();
        HomeActivity=new Intent(this, com.example.elibrary.HomeActivity.class);

        //For SignIn Button

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final  String email=username.getText().toString();
               final  String pass=password.getText().toString();

                if(email.equals("") || pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    signIn(email,pass);
                }


                /*if(email.equals("") || pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkemailpass = DB.checkuseremailpassword(email,pass);
                    String Response= null;
                    try {
                        Response = httpClient.sendloginPOST("email="+email+"&password="+pass);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // if(checkemailpass==true)
                    if(Response.equals("Invalid")==false){
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "SignIn Successfull", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                    }*/



                    /*if(checkemailpass==true){
                        Toast.makeText(LoginActivity.this, "SignIn Successfull", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }*/

                }

            });
        }


    private void signIn(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    updateUI();
                }
                else
                    Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateUI() {
        Toast.makeText(this, "SignIn successfull", Toast.LENGTH_SHORT).show();
        startActivity(HomeActivity);
        finish();
    }

   /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();

        if(user!=null){
            updateUI();
        }
    }*/
}




