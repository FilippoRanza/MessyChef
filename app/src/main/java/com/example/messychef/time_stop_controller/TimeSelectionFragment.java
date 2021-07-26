package com.example.messychef.time_stop_controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.messychef.R;

import java.util.ArrayList;


public class TimeSelectionFragment extends Fragment {

    private static final int NO_CACHE = -1;

    private ListView hourList;
    private ListView minuteList;
    private ListView secondList;
    private TextView textView;

    private int cacheMinute;
    private int cacheSecond;
    private int cacheHour;


    private TimeUpdateListener listener;


    private boolean used;

    final private Activity owner;

    private static class LoopAdapter extends ArrayAdapter<String> {

        private final ArrayList<String> items;
        private final Activity owner;
        private int current;

        public LoopAdapter(Activity context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            items = objects;
            owner = context;
            current = initCurrent() - 2;
        }

        public void setCurrent(int current) {
            System.out.println(current);
            this.current += current;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            position = position % items.size();
            if (convertView == null) {
                LayoutInflater inflater = owner.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_element, parent, false);
                initializeTextView(position, view);
                convertView = view;
            } else {
                initializeTextView(position, convertView);
            }
            return convertView;
        }

        private void initializeTextView(int position, View view) {
            TextView tv = view.findViewById(R.id.text_view_list_element);
            tv.setText(items.get(position));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        public int getCurrent() {
            return current;
        }

        private int initCurrent() {
            int middle = Integer.MAX_VALUE / 2;
            int mod = middle % items.size();
            return middle - mod;
        }

        public int getCount() {
            return Integer.MAX_VALUE;
        }

    }


    public TimeSelectionFragment(Activity owner) {
        this.owner = owner;
        cacheSecond = NO_CACHE;
        cacheMinute = NO_CACHE;
        cacheHour = NO_CACHE;
        used = false;
    }

    public TimeSelectionFragment setTimeUpdateListener(TimeUpdateListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_selection, container, false);
        hourList = initializeListView(view, R.id.hour_dial, 24, cacheHour);
        minuteList = initializeListView(view, R.id.minute_dial, 60, cacheMinute);
        secondList = initializeListView(view, R.id.seconds_dial, 60, cacheSecond);
        textView = view.findViewById(R.id.selected_time_value);
        updateTextView();
        listener.callback(cacheHour, cacheMinute, cacheSecond);
        return view;
    }


    private ArrayList<String> makeDialList(int count) {
        ArrayList<String> output = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String value = String.valueOf(i);
            output.add(value);
        }
        return output;
    }

    private ListView initializeListView(View v, int id, int count, int cache) {
        ListView lv = v.findViewById(id);
        ArrayList<String> valueList = makeDialList(count);
        LoopAdapter stringArrayAdapter = new LoopAdapter(owner, R.layout.list_element, valueList);
        if (cache != NO_CACHE)
            stringArrayAdapter.setCurrent(cache);

        lv.setAdapter(stringArrayAdapter);
        lv.setSelection(stringArrayAdapter.getCurrent());
        lv.setDivider(null);
        lv.setItemsCanFocus(false);

        AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    lv.setSelection(lv.getFirstVisiblePosition());
                    updateTextView();
                    listener.callback(getHour(), getMinute(), getSecond());
                    used = true;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        };
        lv.setOnScrollListener(onScrollListener);


        return lv;
    }

    private void updateTextView() {
        updateTextView(getHour(), getMinute(), getSecond());
    }

    private void updateTextView(int h, int m, int s) {
        String text = String.format("%02d:%02d:%02d", h, m, s);
        textView.setText(text);
    }


    public int getHour() {
        return (used) ?
        getSelection(hourList) % 24 : (cacheHour == NO_CACHE) ? 0 : cacheHour;
    }

    public int getMinute() {
        return (used) ?
                getSelection(minuteList) % 60 : (cacheMinute == NO_CACHE) ? 0 : cacheMinute;
    }

    public int getSecond() {
        return (used) ?
                getSelection(secondList) % 60 : (cacheSecond == NO_CACHE) ? 0 : cacheSecond;
    }

    public void setHour(int h) {
        cacheHour = h;
    }

    public void setMinute(int m) {
        cacheMinute = m;
    }

    public void setSecond(int s) {
        cacheSecond = s;
    }


    private int getSelection(ListView lv) {
        return lv.getFirstVisiblePosition() + 2;
    }


}