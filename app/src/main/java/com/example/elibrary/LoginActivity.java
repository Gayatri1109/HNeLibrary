package com.example.elibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends AppCompatActivity {

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DB=new DBHelper(this);//database variable

        //for SignUp textview

        TextView btn=findViewById(R.id.textViewSignUp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, com.example.elibrary.RegisterActivity.class));
            }
        });

        //For SignIn Button

        final EditText username=(EditText)findViewById(R.id.inputEmail);
        final EditText password=(EditText)findViewById(R.id.inputPassword);
        Button signin=(Button)findViewById(R.id.btnsignin);

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ProfileFragment myFragment = new ProfileFragment();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=username.getText().toString();
                String pass=password.getText().toString();

                if(email.equals("") || pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkemailpass = DB.checkuseremailpassword(email,pass);
                    if(checkemailpass==true){
                        Toast.makeText(LoginActivity.this, "SignIn Successfull", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                        Bundle b = new Bundle();
                        b.putString("message",email);
                        myFragment.setArguments(b);
                        fragmentTransaction.add(R.id.fragment_container, myFragment).commit();



                }

            }
        });


    }
}

