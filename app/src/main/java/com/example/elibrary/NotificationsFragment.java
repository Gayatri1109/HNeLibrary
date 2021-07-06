package com.example.elibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotificationsFragment extends Fragment {
    private final String[] items = new String[] { "Chemistry", "Social Science", "Physics",
            "Technology", "Chemistry", "C++", "Programming", "Database", "Operating System", "Javascript" };
    ListView ls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_notifications,container,false);

       ls=(ListView)v.findViewById(R.id.list);



       return v;
    }
}
