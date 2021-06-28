package com.example.elibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedbackFragment extends Fragment {
    TextView appFeedback;
    RatingBar rtStars;
    Button send;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_feedback,container,false);

        appFeedback = v.findViewById(R.id.appFeedback);
        rtStars = v.findViewById(R.id.ratingBar2);
        send=v.findViewById(R.id.btnSend);

        rtStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0)
                {
                    appFeedback.setText("Very Disatisfied");
                }
                else if (rating==1)
                {
                    appFeedback.setText("Disatisfied");
                }
                else if (rating==2||rating==3)
                {
                    appFeedback.setText("OK");
                }
                else if (rating==4)
                {
                    appFeedback.setText("Satisfied");
                }
                else if(rating==5)
                {
                    appFeedback.setText("Very Satisfied");
                }
                else
                {
                    appFeedback.setText("");
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Than you for sending feedback.", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });


        return v;
    }
}
