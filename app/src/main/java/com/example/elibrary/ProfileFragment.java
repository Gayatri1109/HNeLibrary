package com.example.elibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile,container,false);

        Button btn=(Button)v.findViewById(R.id.btn_save);
        btn.setOnClickListener(this);

        textView=v.findViewById(R.id.txt_username);

        if (getArguments() != null) {
           // Bundle bundle=getArguments();
            //String message = bundle.getString("message");
            String mParam1 = getArguments().getString("message");
            textView.setText(mParam1);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
