package com.example.messychef.text_manager;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.fragment.app.Fragment;

import com.example.messychef.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class TextField extends Fragment {

    public static final int FIELD_ID = R.id.text_input_fragment_field;
    public static final int LAYOUT_ID = R.id.text_input_fragment_layout;

    public static final int NONE_TYPE = -1;
    public static final int NUMBER_INPUT = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL ;


    Activity owner;
    TextInputLayout layout;
    TextInputEditText input;
    int emptyErrorId = R.string.empty_field_error_msg;
    boolean emptyStatus = false;
    CharSequence tmpBuff;
    int placeholderID ;

    TextChangeRunner textChangeRunner;
    int inputType;

    public TextField(Activity owner, int placeholderID) {
        this.owner = owner;
        this.placeholderID = placeholderID;
        inputType = NONE_TYPE;
    }

    public TextField addUpdateListener(TextChangeRunner r) {
        textChangeRunner = r;
        return this;
    }

    public TextField setInputType(int i) {
        inputType = i;
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
        View view = inflater.inflate(R.layout.text_input_fragment, container, false);
        input = view.findViewById(FIELD_ID);
        layout = view.findViewById(LAYOUT_ID);
        initializeInput();
        layout.setHint(placeholderID);

        return view;
    }


    public boolean isEmpty() {
        Editable editable = input.getText();
        if (editable != null) {
            emptyStatus = editable.length() == 0;
        } else {
            emptyStatus = true;
        }
        emptyErrorMessage();
        return emptyStatus;
    }

    public TextField setErrorMessageId(int id) {
        this.emptyErrorId = id;
        return this;
    }


    private void applyListener() {
        if(textChangeRunner != null) {
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emptyStatus = s.length() == 0;
                    textChangeRunner.callback(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    emptyErrorMessage();
                }
            });
        }
    }


    private void initializeInput() {

        if(inputType != NONE_TYPE) {
            input.setInputType(inputType);
        }
        if(tmpBuff != null) {
            input.setText(tmpBuff);
        }
        applyListener();
    }

    public void setText(CharSequence cs) {
        if(input == null)
            tmpBuff = cs;
        else
            input.setText(cs);
    }

    private void emptyErrorMessage() {
        if (emptyErrorId != -1) {
            String msg = (emptyStatus) ? owner.getString(emptyErrorId) : null;
            layout.setError(msg);
        }
    }
}
