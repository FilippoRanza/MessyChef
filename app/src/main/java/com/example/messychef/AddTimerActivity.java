package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.FragmentInstaller;
import com.google.android.material.slider.Slider;

public class AddTimerActivity extends AppCompatActivity {

    private FragmentInstaller installer;

    private TextField nameField;
    private CharSequence name;

    private TimePicker picker;
    private Slider slider;

    public AddTimerActivity() {
        this.installer = new FragmentInstaller(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);
        initNameField();
        initTimePicker();
        initStopSlider();
    }

    private void initStopSlider() {
        slider = findViewById(R.id.stop_timer_slider);
        slider.setEnabled(false);
    }

    private void initTimePicker() {
        picker = findViewById(R.id.time_picker);
        picker.setIs24HourView(true);
        picker.setHour(0);
        picker.setMinute(0);
        picker.setOnTimeChangedListener(this::timePickerCallback);
    }

    private void initNameField() { }

    public void addTimerStep(View view) {
    }

    private void timePickerCallback(View v, int hour, int minute) {
        picker.validateInput();
        updateSlider(hour, minute);
    }

    private void updateSlider(int hour, int minute) {
        int minutes = hour * 60 + minute;
        minutes /= 2;
        if(minutes > 2) {
            minutes--;
            slider.setEnabled(true);
            float value = slider.getValue();
            float newValue = (value > minutes) ? minutes : value;
            slider.setValue(newValue);
            slider.setValueTo(minutes);
        }
        else {
            slider.setEnabled(false);
        }
    }


}