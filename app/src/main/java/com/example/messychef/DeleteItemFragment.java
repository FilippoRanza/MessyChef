package com.example.messychef;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class DeleteItemFragment extends Fragment {


    Button button;
    Runnable runnable;

    public DeleteItemFragment(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_item, container, false);
        button = view.findViewById(R.id.delete_item_button);
        button.setOnClickListener(v -> runnable.run());
        button.setText(R.string.delete_ingredient);
        return view;
    }
}