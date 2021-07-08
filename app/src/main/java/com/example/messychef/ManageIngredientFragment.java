package com.example.messychef;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

public class ManageIngredientFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String name;
    private int quantity;

    public ManageIngredientFragment() {

    }

    public static ManageIngredientFragment modifyIngredientInstance(String name, int quantity) {
        ManageIngredientFragment fragment = new ManageIngredientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putInt(ARG_PARAM2, quantity);
        fragment.setArguments(args);
        return fragment;
    }

    public static ManageIngredientFragment createIngredientInstance() {
        return new ManageIngredientFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            quantity = getArguments().getInt(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manage_ingredient, container, false);
        initializeIngredientNameField(v);
        initializeIngredientQuantityField(v);
        return v;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }


    private void initializeIngredientNameField(View v) {
        TextInputEditText input = v.findViewById(R.id.ingredient_input_name);
        if(name != null) {
            input.setText(name);
        }
        ManageIngredientFragment me = this;
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                me.name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initializeIngredientQuantityField(View v) {
        TextInputEditText input = v.findViewById(R.id.ingredient_input_quantity);

        if(quantity != 0) {
            input.setText("5");
        }
        ManageIngredientFragment me = this;
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                me.quantity = Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}