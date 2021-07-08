package com.example.messychef;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

class TextField {

    Activity owner;
    TextInputLayout layout;
    TextInputEditText input;
    int emptyErrorId = R.string.empty_field_error_msg;
    boolean emptyStatus = false;

    TextField(Activity owner, TextInputLayout layout, TextInputEditText input) {
        this.owner = owner;
        this.input = input;
        this.layout = layout;
    }

    static TextField fromIds(Activity a, int layoutId, int editId) {
        TextInputEditText tiet = a.findViewById(editId);
        TextInputLayout til = a.findViewById(layoutId);
        return new TextField(a, til, tiet);
    }

    boolean isEmpty() {
        Editable editable = input.getText();
        if (editable != null) {
            emptyStatus = editable.length() == 0;
        } else {
            emptyStatus = true;
        }
        emptyErrorMessage();
        return emptyStatus;
    }

    TextField setErrorMessageId(int id) {
        this.emptyErrorId = id;
        return this;
    }


    TextField addUpdateListener(TextChangeRunner r) {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emptyStatus = s.length() == 0;
                System.out.println("AAAAA" + emptyStatus);
                r.callback(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                emptyErrorMessage();
            }
        });
        return this;
    }

    private void emptyErrorMessage() {
        if (emptyErrorId != -1) {
            String msg = (emptyStatus) ? owner.getString(emptyErrorId) : null;
            layout.setError(msg);
        }
    }
}
