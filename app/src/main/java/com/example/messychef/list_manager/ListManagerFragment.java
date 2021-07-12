package com.example.messychef.list_manager;

import android.app.Activity;
import android.os.Bundle;

import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.messychef.R;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ListManagerFragment extends Fragment {

    private TextView text;
    private AdapterView<ArrayAdapter<String>> list;
    private ArrayAdapter<String> adapter;

    private static final int TEXT_VIEW_ID = R.id.empty_list_message_text;
    private static final int LIST_VIEW_ID = R.id.item_list_view;
    private int emptyStringMessage;

    private final Activity owner;
    private ItemClickRunner clickRunner;
    private ItemClickRunner longClickRunner;


    public ListManagerFragment(Activity owner) {
        this.owner = owner;
    }

    public void updateList(Stream<String> s) {
        ArrayList<String> arrayList = s.collect(Collectors.toCollection(ArrayList::new));
        if (arrayList.size() > 0) {
            updateAdapter(arrayList);
        } else {
            adapter = null;
        }
    }

    public ListManagerFragment addItemClickListener(ItemClickRunner runner) {
        this.clickRunner = runner;
        return this;
    }

    public ListManagerFragment addItemLongClickListener(ItemClickRunner runner) {
        longClickRunner = runner;
        return this;
    }

    public ListManagerFragment setEmptyListMessage(int id) {
        emptyStringMessage = id;
        return this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_manager, container, false);
        initialize(v);
        updateFragment();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragment();
    }

    private void initialize(View v) {
        text = v.findViewById(TEXT_VIEW_ID);
        text.setText(emptyStringMessage);

        list = v.findViewById(LIST_VIEW_ID);
        if(clickRunner != null)
            list.setOnItemClickListener((parent, view, position, id) -> clickRunner.run(position));
        if(longClickRunner != null)
            list.setOnItemLongClickListener((parent, view, position, id) -> {
                longClickRunner.run(position);
                return false;
            });

    }


    private void updateAdapter(ArrayList<String> arrayList) {
        adapter = new ArrayAdapter<>(owner, R.layout.list_element, arrayList);
    }

    private void updateFragment() {
        if(adapter != null) {
            updateView();
        }
        else {
            showMessage();
        }
    }

    private void updateView() {
        list.setVisibility(View.VISIBLE);
        text.setVisibility(View.INVISIBLE);
        list.setAdapter(adapter);
    }

    private void showMessage() {

        list.setVisibility(View.INVISIBLE);
        text.setVisibility(View.VISIBLE);
    }


}