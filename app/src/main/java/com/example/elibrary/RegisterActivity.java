package com.example.elibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class RegisterActivity extends AppCompatActivity  {

    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ProfileFragment myFragment = new ProfileFragment();

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


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user=username.getText().toString();
                String emailid=email.getText().toString();
                String pass=password.getText().toString();
                String cmpd=confirmpassword.getText().toString();

                if(user.equals("") || emailid.equals("") || pass.equals("") || cmpd.equals(""))
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
                }
                Bundle b = new Bundle();
                b.putString("Username",user);
                myFragment.setArguments(b);
                fragmentTransaction.add(R.id.fragment_container, myFragment).commit();

            }
        });


    }


}

