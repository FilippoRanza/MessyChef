package com.example.messychef;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messychef.storage_facility.StoreData;

import java.net.URI;


abstract public class AbstractMenuActivity extends AppCompatActivity {


    private static final int GROUP_ID = 0;
    private static final int EXPORT_ID = 0;
    private static final int EXPORT_ORDER = 0;
    private static final int IMPORT_ID = 1;
    private static final int IMPORT_ORDER = 1;

    private static final int EXPORT_REQUEST = 0;
    private static final int IMPORT_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    final public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        initMenu(menu);
        return true;
    }


    @Override
    final public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        handleOptionSelection(item);
        return true;
    }

    private void runImport() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, IMPORT_REQUEST);
    }

    private void runExport() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, EXPORT_REQUEST);
    }


    private void initMenu(Menu menu) {
        menu.add(GROUP_ID, EXPORT_ID, EXPORT_ORDER, R.string.export_menu);
        menu.add(GROUP_ID, IMPORT_ID, IMPORT_ORDER, R.string.import_menu);
    }


    private void handleOptionSelection(MenuItem item) {
        switch (item.getItemId()) {
            case EXPORT_ID:
                runExport();
                break;
            case IMPORT_ID:
                runImport();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;

        Uri uri = data.getData();

        switch (requestCode) {
            case EXPORT_REQUEST:
                handleExport(uri);
                break;
            case IMPORT_REQUEST:
                handleImport(uri);
                break;
        }
    }

    private void handleImport(Uri uri) {
        System.out.println("Import");
    }

    private void handleExport(Uri uri) {
        System.out.println("Export");
    }

}
