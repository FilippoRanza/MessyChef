package com.example.messychef;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.messychef.storage_facility.ImportExportManager;

import java.io.IOException;

public class ImportExportService extends Service {

    public class ImportExportBinder extends Binder {

        public void exportRecipes(Uri uri) {
            ImportExportService.this.exportRecipes(uri);
        }

        public void importRecipes(Uri uri) {
            ImportExportService.this.importRecipes(uri);
        }

        public void setClientBinder(AbstractMenuActivity.ClientBinder binder) {
            clientBinder = binder;
        }

    }

    private final ImportExportBinder serviceBinder;
    private AbstractMenuActivity.ClientBinder clientBinder;

    public ImportExportService() {
        serviceBinder = new ImportExportBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }


    private void exportRecipes(Uri uri) {
        showMessage(R.string.export_start);
        startThread(() -> {
            try {
                ImportExportManager.exportRecipes(uri, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        showMessage(R.string.export_done);
        clientBinder.onExportDone();
        stopSelf();
    }

    private void importRecipes(Uri uri) {
        showMessage(R.string.import_start);
        startThread(() -> {
            try {
                ImportExportManager.importRecipes(uri, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        showMessage(R.string.import_done);
        clientBinder.onImportDone();
        stopSelf();
    }

    private void showMessage(int msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }


    private void startThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}