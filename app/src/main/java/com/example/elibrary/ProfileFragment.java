package com.example.elibrary;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView tv1,tv2;
    ImageView userPhoto;
    private Spinner gender;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile,container,false);

        Button btn=(Button)v.findViewById(R.id.btn_save);
        btn.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        tv1=v.findViewById(R.id.txt_username);
        tv2=v.findViewById(R.id.txt_email);
        userPhoto=v.findViewById(R.id.userphoto);
        gender=v.findViewById(R.id.gender);

        updateUserProfile();

        return v;
    }

    private void updateUserProfile() {

        TextView Username= tv1.findViewById(R.id.txt_username);
        TextView Email= tv2.findViewById(R.id.txt_email);
        ImageView navUserPhoto =userPhoto.findViewById(R.id.userphoto);

        Username.setText(currentUser.getDisplayName());
        Email.setText(currentUser.getEmail());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhoto);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();

        }
    }

}
