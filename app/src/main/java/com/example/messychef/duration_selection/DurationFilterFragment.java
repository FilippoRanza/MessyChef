package com.example.messychef.duration_selection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.messychef.R;
import com.example.messychef.recipe.load_store.RecipeLoadStore;
import com.google.android.material.slider.RangeSlider;

import java.util.List;

public class DurationFilterFragment extends Fragment {


    private OnIntervalChange intervalChange;


    public DurationFilterFragment() {
        RecipeLoadStore loadStore = RecipeLoadStore.getInstance();
        loadStore.startGetMaxRecipeDuration();
        loadStore.startGetMinRecipeDuration();
    }

    public DurationFilterFragment setOnIntervalChange(OnIntervalChange intervalChange) {
        this.intervalChange = intervalChange;
        return this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duration_filter, container, false);
        initTimeSelectionSlider(view);
        return view;
    }

    private void initTimeSelectionSlider(View view) {
        RangeSlider slider = view.findViewById(R.id.time_selection_slider);
        RecipeLoadStore loadStore = RecipeLoadStore.getInstance();
        int min = loadStore.commitGetMinRecipeDuration();
        int max = loadStore.commitGetMaxRecipeDuration();

        if (min == max) {
            min--;
            max++;
        }

        slider.setValueFrom(min);
        slider.setValueTo(max);
        slider.setValues((float) min, (float) max);

        slider.setStepSize(30.0f);

        slider.setLabelFormatter(this::makeLabel);

        if (intervalChange != null)
            applyOnIntervalChange(slider);

    }

    private void applyOnIntervalChange(RangeSlider slider) {
        slider.addOnChangeListener((slider1, value, fromUser) -> {
            List<Float> values = slider1.getValues();
            float min = values.get(0);
            float max = values.get(1);
            intervalChange.onIntervalChange((int) min, (int) max);
        });
    }

    private String makeLabel(float value) {
        int seconds = (int) value;
        int s = seconds % 60;
        int minutes = seconds / 60;
        int m = minutes % 60;
        int h = minutes / 60;
        return (s != 0) ? String.format("%02d:%02d:%02d", h, m, s) :
                String.format("%02d:%02d", h, m);
    }

}