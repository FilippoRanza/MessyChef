package com.example.messychef.utils.view_find_adapter;

import android.view.View;

class ViewFindViewAdapter<T extends View> extends AbstractFindViewAdapter<T> {

    private final View view;

    ViewFindViewAdapter(View view) {
        this.view = view;
    }


    @Override
    public T findViewById(int id) {
        return view.findViewById(id);
    }
}
