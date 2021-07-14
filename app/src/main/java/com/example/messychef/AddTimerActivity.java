package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.messychef.text_manager.TextField;
import com.example.messychef.time_stop_controller.GroupController;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class AddTimerActivity extends AppCompatActivity {

    private static class RadioButtonGroupController {
        //private
    }



    private static final int RADIO_BUTTON_COUNT = 12;

    private FragmentInstaller installer;

    private TextField nameField;
    private CharSequence name;

    private TimePicker picker;

    private GroupController controller;

    public AddTimerActivity() {
        this.installer = new FragmentInstaller(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);
        initRadioButtons();
        initNameField();
        initTimePicker();
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
        int minutes = getMinutes();
        controller.update(minutes);
    }


    private void initRadioButtons() {
        GridLayout radioGroup = findViewById(R.id.radio_button_group);
        controller = new GroupController(RADIO_BUTTON_COUNT, this, radioGroup);
    }


    private int getMinutes() {
        int h = picker.getHour();
        int m = picker.getMinute();
        return  (h * 60) + m;
    }


}