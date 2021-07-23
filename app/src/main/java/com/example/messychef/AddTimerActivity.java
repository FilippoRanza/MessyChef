package com.example.messychef;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.text_manager.TextField;
import com.example.messychef.time_stop_controller.GroupController;
import com.example.messychef.time_stop_controller.TimeSelectionFragment;
import com.example.messychef.utils.FragmentInstaller;

public class AddTimerActivity extends AppCompatActivity {

    private static class RadioButtonGroupController {
        //private
    }


    private static final int RADIO_BUTTON_COUNT = 12;

    private final FragmentInstaller installer;

    private TextField nameField;
    private CharSequence name;

    private TimeSelectionFragment picker;

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
        picker = new TimeSelectionFragment(this).setTimeUpdateListener(this::timePickerCallback);
        installer.installFragment(R.id.time_selector_fragment, picker);
    }


    private void initNameField() {
        nameField = new TextField(this, R.string.timer_step_name)
                .addUpdateListener((cs) -> name = cs);
        installer.installFragment(R.id.time_step_name_field, nameField);
    }

    public void addTimerStep(View view) {
        if (validate()) {
            createTimerStep();
            finish();
        }
    }

    private void createTimerStep() {
        RecipeFactory factory = RecipeFactory.getInstance();
        Integer stepTime = controller.getSelectedTime();
        factory.addTimerStep(name.toString(), getMinutes(), stepTime);
    }

    private void timePickerCallback(int hour, int minute, int second) {
        //picker.validateInput();
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
        return (h * 60) + m;
    }


    private boolean validate() {
        return (!nameField.isEmpty()) && (!validateTime());
    }


    private boolean validateTime() {
        return (getMinutes() == 0);
    }

}