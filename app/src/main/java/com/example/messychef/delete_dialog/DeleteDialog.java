package com.example.messychef.delete_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.example.messychef.R;

public class DeleteDialog<T> {

    Activity owner;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    T info;

    public DeleteDialog(Activity owner, T info) {
        this.owner = owner;
        this.info = info;
        builder = new AlertDialog.Builder(owner);
        builder.setNegativeButton(R.string.cancel_delete, (dialog, which) -> {
            dialog.cancel();
        });
    }

    public DeleteDialog<T> setTitle(int id) {
        builder.setTitle(id);
        return this;
    }

    public DeleteDialog<T> setMessage(int id) {
        builder.setMessage(id);
        return this;
    }


    public DeleteDialog<T> setDeleteMessageAction(DeleteAction<T> action) {
        builder.setPositiveButton(R.string.confirm_delete, (dialog, which) -> action.runDelete(info));
        return this;
    }

    public void start() {
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
