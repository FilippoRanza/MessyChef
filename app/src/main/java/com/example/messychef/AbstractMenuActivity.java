package com.example.messychef;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


abstract public class AbstractMenuActivity extends AppCompatActivity {


    private class ConnectionHandler implements ServiceConnection {

        private final int requestCode;
        private final Uri uri;

        ConnectionHandler(Uri uri, int requestCode) {
            this.uri = uri;
            this.requestCode = requestCode;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ImportExportService.ImportExportBinder binder = (ImportExportService.ImportExportBinder) service;
            binder.setClientBinder(new ClientBinder());
            switch (requestCode) {
                case EXPORT_REQUEST:
                    binder.exportRecipes(uri);
                    break;
                case IMPORT_REQUEST:
                    binder.importRecipes(uri);
                    break;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }


    public class ClientBinder extends Binder {
        public void onImportDone() {
            importDone();
            unbindService(connection);
        }

        public void onExportDone() {
            unbindService(connection);
        }


    }


    private static final int GROUP_ID = 0;
    private static final int EXPORT_ID = 0;
    private static final int EXPORT_ORDER = 0;
    private static final int IMPORT_ID = 1;
    private static final int IMPORT_ORDER = 1;

    private static final int EXPORT_REQUEST = 0;
    private static final int IMPORT_REQUEST = 1;

    private ServiceConnection connection;


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


    protected void importDone() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;

        Uri uri = data.getData();
        startImportExportService(uri, requestCode);
    }


    private void startImportExportService(Uri uri, int requestCode) {
        Intent intent = new Intent(this, ImportExportService.class);
        startService(intent);
        connection = new ConnectionHandler(uri, requestCode);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


}
